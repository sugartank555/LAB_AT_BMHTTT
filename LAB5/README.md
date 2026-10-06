LAB 3 – THIẾT LẬP TƯỜNG LỬA pfSense
1. Mục tiêu
Bài thực hành nhằm xây dựng mô hình mạng sử dụng tường lửa pfSense để bảo vệ mạng LAN và vùng DMZ. Nội dung gồm cài đặt pfSense, cấu hình các card mạng, đặt địa chỉ IP cho LAN, chuẩn bị vùng DMZ và kiểm tra khả năng kết nối giữa máy thật với giao diện quản trị pfSense.
2. Môi trường sử dụng
- VMware Workstation.
- pfSense CE 2.7.2-RELEASE (amd64).
- Windows Server 2025: dùng thay cho Windows Server 2019/2022 trong tài liệu.
- Ubuntu: dùng làm máy LAN-Test.
- Windows 10: có thể dùng làm máy DMZ-Web khi thực hiện các tình huống DMZ.
- Máy thật Windows dùng để quản trị pfSense.
3. Mô hình địa chỉ IP
Thiết bị	Interface	Địa chỉ IP	Subnet mask	Gateway
pfSense	WAN – em0	DHCP	Theo mạng WAN	DHCP
pfSense	LAN – em1	10.0.0.1	255.0.0.0	Không đặt
pfSense	DMZ – OPT1	172.16.0.1	255.255.0.0	Không đặt
Máy thật	VMware VMnet1	10.0.0.100	255.0.0.0	Không đặt
Windows Server 2025	LAN	10.0.0.2	255.0.0.0	10.0.0.1
Ubuntu LAN-Test	LAN	10.0.0.3	255.0.0.0	10.0.0.1
Máy DMZ-Web	DMZ	172.16.0.2	255.255.0.0	172.16.0.1


4. Quá trình thực hiện
Bước 1: Chuẩn bị bộ cài pfSense
Đã tải bộ cài pfSense CE 2.7.2, kiểm tra file không bị trùng, giải nén file .iso.gz để thu được file ISO và sử dụng ISO này để cài đặt máy ảo.
Bước 2: Tạo máy ảo pfSense
Máy ảo được tạo với cấu hình:
- 2 GB RAM.
- 2 CPU.
- Ổ cứng 20 GB.
- Hệ điều hành FreeBSD 64-bit.
- Gắn file ISO pfSense vào ổ CD/DVD.
Bước 3: Cấu hình ba card mạng
- Network Adapter 1: Bridged, dùng làm WAN.
- Network Adapter 2: Host-only/VMnet1, dùng làm LAN.
- Network Adapter 3: LAN Segment, dùng làm DMZ.
- Bật tùy chọn Connect at power on cho các card mạng.
Bước 4: Cài đặt pfSense
Khởi động máy ảo từ ISO, chọn cài đặt pfSense lên ổ cứng ảo. Sau khi cài xong, máy được khởi động lại và vào được menu console của pfSense CE 2.7.2-RELEASE.
Bước 5: Gán interface và cấu hình LAN
Các interface được xác định như sau:
- WAN: em0.
- LAN: em1.
Địa chỉ IPv4 của LAN được đặt là 10.0.0.1/8. DHCP trên LAN không được bật để chủ động cấu hình IP tĩnh cho từng máy trong bài lab.
Bước 6: Cấu hình card VMnet1 trên máy thật
Card VMware Network Adapter VMnet1 trên Windows được cấu hình:
- IP: 10.0.0.100.
- Subnet mask: 255.0.0.0.
- Default gateway: để trống.
- DNS: để trống.
Bước 7: Kiểm tra và xử lý kết nối
Khi ping từ máy thật đến 10.0.0.1, kết quả ban đầu là Request timed out. Việc kiểm tra trên pfSense cho thấy:
- Interface em1 có địa chỉ 10.0.0.1/8.
- Trạng thái đường truyền là status: active.
- pfSense không ping được 10.0.0.100.
- Bảng ARP hiển thị 10.0.0.100 at (incomplete) on em1.
Kết quả này chứng minh card LAN của pfSense đã hoạt động nhưng pfSense và card VMnet1 trên máy thật chưa trao đổi được ở lớp liên kết. Cách xử lý là tắt pfSense, chuyển Network Adapter 2 sang chế độ Host-only, giữ Connect at power on, sau đó bật lại máy và kiểm tra bằng lệnh:
ping 10.0.0.1
Khi ping thành công, truy cập giao diện quản trị bằng địa chỉ:
https://10.0.0.1
5. Kết quả đạt được
- Chuẩn bị và giải nén thành công bộ cài pfSense CE 2.7.2.
- Tạo thành công máy ảo pfSense trên VMware Workstation.
- Cấu hình đủ ba card mạng phục vụ WAN, LAN và DMZ.
- Cài đặt thành công pfSense và khởi động được vào menu console.
- Xác định đúng WAN là em0 và LAN là em1.
- Cấu hình thành công địa chỉ LAN 10.0.0.1/8.
- Cấu hình card VMnet1 trên máy thật với địa chỉ 10.0.0.100/8.
- Kiểm tra xác nhận interface LAN đang ở trạng thái active.
- Dùng lệnh ping, ifconfig và arp để xác định nguyên nhân kết nối bị lỗi ở mạng Host-only/VMnet1.
- Đưa ra phương án sửa bằng cách gắn Network Adapter 2 của pfSense trực tiếp vào mạng Host-only.
6. Kết luận
Phần cài đặt và cấu hình nền tảng của pfSense đã hoàn thành. Máy ảo nhận đúng interface LAN và địa chỉ IP theo mô hình. Quá trình kiểm tra cũng xác định được lỗi kết nối ban đầu nằm ở liên kết giữa card LAN của pfSense và VMnet1 trên máy thật, không phải do sai địa chỉ IP trên pfSense. Sau khi hoàn tất kết nối Host-only, có thể tiếp tục cấu hình WebGUI, DMZ, Outbound NAT và các tình huống firewall của bài lab.
