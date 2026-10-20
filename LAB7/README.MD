1150080012 Bùi Hải Đường
LAB 7: XÂY DỰNG CHÍNH SÁCH WEB PROTECTION TRÊN pfSense + WEB PROXY
1. Thông tin bài thực hành
- Môn học: Thực hành An toàn hệ thống thông tin
- Bài lab: LAB 7 – Xây dựng chính sách Web Protection trên mô hình pfSense + Web Proxy
- Môi trường: VMware Workstation, pfSense CE 2.7.2, Windows Server 2025, máy ảo Windows 10, Ubuntu
- Phạm vi README: Ghi lại quá trình đã thực hiện trong buổi thực hành và các kết quả có minh chứng. Những hạng mục chưa thực hiện/kiểm chứng được ghi rõ.
2. Mục tiêu bài lab
Xây dựng mô hình trong đó pfSense đảm nhiệm firewall/gateway và chặn kết nối web trực tiếp từ client; Web Proxy trên Ubuntu sử dụng Squid, E2Guardian, ClamAV để lọc nội dung, cảnh báo, kiểm tra tải xuống. Bài thực hành cũng yêu cầu website mô phỏng trong DMZ, bản ghi DNS và kiểm thử các chính sách.
3. Môi trường và sơ đồ địa chỉ
Thành phần	Vai trò	Địa chỉ theo mô hình	Trạng thái xác minh
pfSense-Lab	Firewall/gateway	WAN: 192.168.250.128/24; LAN: 10.6.0.1/24; MGMT: 192.168.56.254/24; DMZ: 172.16.6.1/24	Đã kiểm tra console
Windows Server 2025 (DC01)	AD DS, DNS	10.6.0.10/24, GW 10.6.0.1, DNS 10.6.0.10	Đã cấu hình và kiểm tra
Windows 10	Client kiểm thử	Dự kiến 10.6.0.21/24	Có máy ảo, chưa xác minh IP/kiểm thử
Ubuntu 64-bit	Dự kiến DMZ-WEB	Dự kiến 172.16.6.10/24	Có máy ảo, chưa xác minh cấu hình
WEB-PROXY (Ubuntu 24.04 LTS)	Squid + E2Guardian + ClamAV	Dự kiến 10.6.0.30/24	Chưa có minh chứng đã dựng
Client-B	Client kiểm thử thứ hai	Dự kiến 10.6.0.22/24	Chưa có minh chứng đã dựng


Ghi chú: Có máy ảo tên sf_virtual, nhưng chưa xác định hệ điều hành/vai trò nên chưa đưa vào mô hình vận hành.
4. Quá trình thực hiện
Bước 1 – Khởi động và kiểm tra pfSense
- Khởi động máy ảo pfSense-Lab trên VMware Workstation.
- Quan sát console pfSense 2.7.2-RELEASE, xác nhận các interface:
  - em0 (WAN): 192.168.250.128/24 (DHCP)
  - em2 (LAN): 10.6.0.1/24
  - em1 (OPT1/MGMT): 192.168.56.254/24
  - em3 (OPT2/DMZ): 172.16.6.1/24
- Kết quả: pfSense khởi động thành công, các interface có IP phù hợp sơ đồ mạng LAB 7.
Bước 2 – Kiểm tra kết nối DC01 đến pfSense
- Trên Windows Server 2025, kiểm tra cấu hình bằng ipconfig /all.
- Xác nhận hostname DC01, IPv4 10.6.0.10, subnet 255.255.255.0, gateway 10.6.0.1, DNS 10.6.0.10.
- Kiểm tra kết nối:
ping 10.6.0.1
- Kết quả: 4 gói gửi, 4 gói nhận, mất gói 0%; kết nối DC01 → pfSense LAN hoạt động.
Bước 3 – Kiểm tra và cài dịch vụ DNS
- Ban đầu, truy vấn nslookup google.com tới DNS 10.6.0.10 báo No response from server.
- Kiểm tra dịch vụ DNS; ban đầu không tìm thấy dịch vụ.
- Sau quá trình cài/thiết lập, Get-WindowsFeature DNS, AD-Domain-Services cho thấy DNS Server Installed; ảnh sau đó ghi nhận dịch vụ DNS Running.
- Kiểm tra các DNS record của domain nội bộ và cổng UDP 53.
- Kết quả: DNS Server đã được cài và có lắng nghe UDP 53. Chưa có kết quả xác nhận phân giải tên Internet thành công.
Bước 4 – Cài đặt Active Directory Domain Services
- Kiểm tra vai trò AD-Domain-Services ban đầu ở trạng thái Available.
- Thử cài từ PowerShell gặp lỗi InvalidArgs.
- Chuyển sang Server Manager → Manage → Add Roles and Features để cài AD DS.
- Tiếp tục nâng cấp máy thành Domain Controller.
- Kết quả: Truy vấn thông tin domain trả về tên miền nội bộ buihaiduonglab7.local và Domain Controller DC01.buihaiduonglab7.local; thể hiện máy đã được cấu hình thành Domain Controller.
Bước 5 – Kiểm tra DNS nội bộ sau khi dựng AD
- Kiểm tra zone buihaiduonglab7.local: thấy các bản ghi A của @, dc01, DomainDnsZones, ForestDnsZones, trỏ tới 10.6.0.10.
- Kiểm tra endpoint UDP port 53: có listener trên 10.6.0.10, 127.0.0.1, ::1 và IPv6 link-local.
- Chạy lệnh:
Resolve-DnsName dc01.buihaiduonglab7.local -Server 10.6.0.10 -Type A -DnsOnly
- Kết quả: Trả về bản ghi A 10.6.0.10, xác nhận phân giải DNS nội bộ cho DC01 thành công.
- Tuy nhiên, truy vấn SRV _ldap._tcp.dc._msdcs.buihaiduonglab7.local báo DNS name does not exist ở thời điểm kiểm thử.
- Đánh giá: DNS A nội bộ hoạt động; chưa xác nhận đầy đủ khả năng khám phá Domain Controller qua bản ghi SRV. Cần kiểm tra Netlogon/đăng ký DNS và thử lại.
Bước 6 – Chuẩn bị chuyển sang Ubuntu DMZ-WEB
- Đã xác định máy ảo Ubuntu 64-bit có thể được xem xét sử dụng cho DMZ-WEB.
- Đã đề xuất kiểm tra cat /etc/os-release, ip -br addr, ip route và mapping VMnet với pfSense DMZ.
- Trạng thái: Chưa có ảnh/lệnh chứng minh đã gán 172.16.6.10, cài Nginx hoặc tạo website thử nghiệm.
5. Kết quả đạt được (đã có minh chứng)
Nội dung	Kết quả
pfSense 2.7.2 khởi động	Đạt
WAN/LAN/MGMT/DMZ có IP phù hợp sơ đồ	Đạt
DC01 dùng IP 10.6.0.10, gateway 10.6.0.1	Đạt
DC01 ping pfSense LAN 10.6.0.1	Đạt: 4/4, 0% loss
Cài và khởi chạy DNS Server	Đạt theo ảnh kiểm tra
Tạo Domain Controller	Đạt: buihaiduonglab7.local
DNS A cho dc01.buihaiduonglab7.local	Đạt: 10.6.0.10
DNS SRV phục vụ AD	Chưa đạt tại lần kiểm thử cuối
Phân giải DNS Internet	Chưa xác nhận


6. Hạng mục chưa có kết quả kiểm thử
Các mục sau là yêu cầu của tài liệu LAB 7, không phải kết quả đã thực hiện:
1. Cấu hình DMZ-WEB 172.16.6.10, cài Nginx và tạo 13 website mô phỏng *.lab7.test.
2. Tạo zone DNS lab7.test, các bản ghi A trỏ về 172.16.6.10.
3. Xây dựng WEB-PROXY Ubuntu 24.04 với E2Guardian 10.6.0.30:8080, Squid 127.0.0.1:3128, ClamAV.
4. Thiết lập proxy trên Client-A/Client-B và ngăn truy cập trực tiếp cổng 80/443 bằng pfSense rules.
5. Kiểm thử hard block, warning/temporary bypass, exception/whitelist và lịch giải trí 60 phút.
6. Kiểm thử EICAR, tải file 100 MB/110 MB, đọc log pfSense/E2Guardian/Squid.
7. HTTPS Inspection bằng CA riêng của lab (phần mở rộng, chỉ áp dụng trên máy ảo do người thực hành kiểm soát).
8. Chụp ảnh minh chứng thực tế cho từng kịch bản và hoàn thiện báo cáo.
7. Đánh giá và kết luận
Đến thời điểm lập README, đã xây dựng được nền tảng mạng và dịch vụ domain/DNS ban đầu cho LAB 7: pfSense hoạt động với đủ phân đoạn mạng; DC01 kết nối tới pfSense và phân giải được bản ghi A nội bộ. Chưa đủ minh chứng để kết luận hệ thống Web Protection hoàn chỉnh hoặc các chính sách lọc web, quét virus, giới hạn download đã hoạt động. Cần tiếp tục triển khai DMZ-WEB, WEB-PROXY, firewall rules và bộ kiểm thử theo tài liệu.
