1150080012-Bùi Hải Đường-CNPM1

LAB 6 – Xác thực Active Directory và chính sách truy cập trên pfSense
1. Mục tiêu
Triển khai mô hình mạng có pfSense CE, Windows Server (Active Directory/DNS), hai máy Client và máy chủ web DMZ; sau đó cấu hình LDAP, Captive Portal, Alias, Schedule và các chính sách Firewall theo tài liệu LAB 6.
> **Trạng thái báo cáo:** Ghi nhận tiến độ thực tế qua ảnh màn hình trong quá trình thực hành. Những chức năng chưa kiểm thử thành công được ghi rõ **Chưa hoàn thành**, không coi là kết quả thực nghiệm.
2. Môi trường
Nền tảng ảo hóa: VMware Workstation (thay VirtualBox trong tài liệu).
Firewall: pfSense CE 2.7.2, VM `pfSense-Lab` (2 GB RAM, 2 vCPU theo ảnh VMware).
Domain Controller dự kiến: Windows Server 2025, VM `Windows Server 2025`.
Client-A dự kiến: VM `Windows 10 and later x64`.
Client-B: chưa xác nhận máy ảo tương ứng.
DMZ-WEB dự kiến: VM `Ubuntu 64-bit`.
Máy thật Windows sử dụng VMware Network Adapter VMnet1 để quản trị.
Lưu ý: Tài liệu gốc dùng VirtualBox và đề xuất Windows Server 2019/2022, Windows 11; môi trường thực tế sử dụng VMware và Windows Server 2025. Cần xác nhận giảng viên chấp nhận thay thế này.
3. Sơ đồ mạng và địa chỉ
Thành phần	Interface/card	Mạng/IP	Trạng thái
pfSense WAN	em0	DHCP: `192.168.250.128/24` tại thời điểm chụp	Đã nhận IP
pfSense LAN	em2	`10.6.0.1/24`	Đã cấu hình, đã ping được từ Windows Server
pfSense MGMT (OPT1)	em1	`192.168.56.254/24`	Đã cấu hình, WebGUI từ máy thật chưa vào được
pfSense DMZ (OPT2)	em3	`172.16.6.1/24`	Đã cấu hình IP, chưa kiểm thử với Ubuntu
Máy thật	VMware VMnet1	`192.168.56.1/24`	Đã xác nhận
Windows Server 2025	Ethernet0	`10.6.0.10/24`, gateway `10.6.0.1`	Đã cấu hình và kiểm thử LAN
Client-A	Dự kiến LAN	`10.6.0.21/24`	Chưa cấu hình/kiểm thử
Client-B	Dự kiến LAN	`10.6.0.22/24`	Chưa cấu hình/kiểm thử
Ubuntu DMZ-WEB	Dự kiến DMZ	`172.16.6.10/24`; IP phụ `.20`, `.30`, `.40`	Chưa cấu hình/kiểm thử
Mạng ảo đã quan sát: VMnet1 Host-only `192.168.56.0/24` (host adapter bật, DHCP tắt); VMnet8 NAT `192.168.250.0/24`; VMnet2 và VMnet3 có trong VMware nhưng cần đối chiếu chính xác cấu hình LAN Segment giữa các máy. Windows Server đã liên lạc được với pfSense LAN nên đường LAN thực tế đang hoạt động.
4. Quá trình đã thực hiện và kết quả
4.1. Gán interface và IP pfSense
Khởi động pfSense CE 2.7.2 trên VMware.
Vào console `1) Assign Interfaces`, bỏ qua VLAN.
Gán `WAN=em0`, `LAN=em2`, `OPT1=em1`, `OPT2=em3`.
Vào `2) Set interface(s) IP address`, cấu hình IP cho LAN, OPT1 và OPT2 như bảng mục 3.
Kết quả: Console xác nhận đủ bốn interface và WAN nhận DHCP `192.168.250.128/24`.
4.2. Kiểm tra kết nối MGMT (máy thật ↔ pfSense)
Trên máy thật kiểm tra card VMnet1 có IP `192.168.56.1`.
Chạy `ping 192.168.56.254` → 4/4 gói mất.
Chạy `Test-NetConnection 192.168.56.254 -Port 443` → `TcpTestSucceeded: False`.
Trên pfSense chạy `ifconfig em1` → IP `192.168.56.254/24`, trạng thái `active`.
Chạy `ping -c 4 192.168.56.1` → 4 gói gửi, 0 gói nhận.
Chạy `arp -an` → có ánh xạ `192.168.56.1` sang MAC trên `em1`, chứng tỏ ARP hoạt động.
Chạy `pfctl -sr` → thấy rule cho LAN `em2`, chưa thấy rule Pass tương ứng cho MGMT `em1` trong phần kết quả đã xem.
Kết quả: IP và kết nối tầng liên kết MGMT đã được xác nhận; truy cập HTTPS/ICMP từ máy thật chưa thành công. Nghi vấn thiếu rule cho MGMT, chưa xác nhận hoặc khắc phục hoàn tất. Có tạo file thử nghiệm `/tmp/mgmt-access.conf` nhưng chưa áp dụng rule.
4.3. Cấu hình và kiểm thử LAN Windows Server ↔ pfSense
Kết nối VM Windows Server 2025 vào mạng LAN của pfSense.
Đặt Ethernet0: IP `10.6.0.10`, mask `255.255.255.0`, gateway `10.6.0.1`.
Trong CMD chạy `ipconfig` để kiểm tra IP.
Chạy `ping 10.6.0.1`.
Kết quả thực tế: `Sent = 4`, `Received = 4`, `Lost = 0 (0% loss)`, thời gian phản hồi `<1 ms`. Đã hoàn thành kiểm thử kết nối LAN.
4.4. Cài Active Directory Domain Services
Mở PowerShell Administrator trên Windows Server 2025.
Thử lệnh cài tính năng AD DS và DNS.
Màn hình ghi nhận lỗi gõ lệnh ban đầu (`CommandNotFoundException`); lần sau `Install-WindowsFeature` trả về `NameDoesNotExist`, `Success: False`, `Exit Code: InvalidArgs` vì tên feature đã nhập không hợp lệ.
Kết quả: Chưa cài thành công AD DS/DNS theo bằng chứng hiện có. Chưa có bằng chứng đã tạo domain `lab6.test`, OU `LAB6-Users` hay các tài khoản `sv_a`, `sv_b`, `svc_pfsense_ldap`.
Lệnh chuẩn cần thử sau khi kiểm tra phiên bản/tên feature:
```powershell
Get-WindowsFeature *AD-Domain*
Install-WindowsFeature -Name AD-Domain-Services,DNS -IncludeManagementTools
```
Chỉ chạy lệnh tạo forest sau khi cài tính năng thành công và xác nhận máy chưa là Domain Controller:
```powershell
Install-ADDSForest -DomainName "lab6.test" -DomainNetbiosName "LAB6" -InstallDNS
```
5. Các phần chưa thực hiện/hoàn thành
Nội dung tài liệu LAB 6	Trạng thái
Domain `lab6.test`, AD DS và DNS	Chưa hoàn thành
OU `LAB6-Users`; các user `sv_a`, `sv_b`, `svc_pfsense_ldap`	Chưa hoàn thành
Client-A, Client-B	Chưa hoàn thành
Ubuntu Nginx và 4 IP mô phỏng `.10/.20/.30/.40`	Chưa hoàn thành
Tích hợp LDAP trên pfSense; kiểm tra xác thực `sv_a`, `sv_b`	Chưa hoàn thành
Captive Portal LAN dùng AD	Chưa hoàn thành
Alias: CLIENT_A, CLIENT_B, POLICY_CLIENTS và các site	Chưa hoàn thành
Schedule: LUNCH_WINDOW, WORK_HOURS, TEST_NOW	Chưa hoàn thành
Kịch bản 1: Client-A bị chặn JOB_SITE	Chưa kiểm thử
Kịch bản 2: A/B bị chặn RESTRICTED_SITE	Chưa kiểm thử
Kịch bản 3: Client-B SOCIAL_SITE theo giờ nghỉ	Chưa kiểm thử
Kịch bản 4: Chỉ HTTP/HTTPS trong giờ làm việc	Chưa kiểm thử
Kịch bản 5: Cô lập DMZ khỏi LAN	Chưa kiểm thử
Kịch bản 6: So sánh Block và Reject	Chưa kiểm thử
Kịch bản 7: Firewall Log và State Table	Chưa kiểm thử
6. Kết luận hiện tại
Đã xây dựng được phần nền tảng pfSense với 4 interface và kiểm thử thành công kết nối Windows Server 2025 → pfSense LAN (10.6.0.1). Đường quản trị MGMT qua WebGUI chưa hoạt động; AD DS chưa cài thành công; các chức năng LDAP, Captive Portal và 7 kịch bản chính sách Firewall chưa có kết quả thực nghiệm.
7. Việc cần làm tiếp
Xác nhận tên tính năng AD DS bằng `Get-WindowsFeature *AD-Domain*`, cài AD DS/DNS thành công.
Tạo domain `lab6.test`, OU và các tài khoản phục vụ LDAP.
Hoàn thiện WebGUI trên MGMT bằng rule chính thức chỉ cho phép máy quản trị truy cập HTTPS.
Thiết lập Client-A, Client-B và Ubuntu DMZ-WEB.
Thực hành LDAP, Captive Portal, Alias, Schedule, 7 kịch bản và lưu ảnh minh chứng từng mục.
Hoàn thiện bảng kết quả thực tế và xuất file `config.xml` của pfSense theo yêu cầu nộp bài.
---
Tài liệu cơ sở: LAB6_pfSense_AD_Access_Policies_2026.pdf, mục B–K; trạng thái thực tế dựa trên các ảnh người thực hành đã cung cấp.
