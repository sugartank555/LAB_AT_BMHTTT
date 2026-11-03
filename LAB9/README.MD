Bùi Hải Đường CNPM1 1150080012
## 1. Mục tiêu thực hành

Bài LAB 10 nhằm tìm hiểu cơ chế tấn công Remote DNS Cache Poisoning, nguyên lý giả mạo phản hồi DNS và vai trò của Transaction ID, UDP Source Port, NS Record và Glue Record.

Đồng thời, bài thực hành xây dựng môi trường mạng cô lập bằng pfSense, phân chia máy DNS Server, Victim và Attacker thành các vùng mạng khác nhau để phục vụ việc mô phỏng và phân tích các biện pháp bảo mật DNS.

## 2. Môi trường thực hiện

Bài lab được triển khai trên VMware Workstation với các máy ảo:

| Máy ảo | Vai trò | Địa chỉ IP dự kiến |
|---|---|---|
| pfSense 2.7.2 | Firewall và Router | Nhiều interface |
| Ubuntu 64-bit | DNS-SERVER | 10.9.0.10 |
| Windows 10 | VICTIM | 10.9.0.100 |
| Windows Server 2025 | AUTH-DNS và Web Server | 172.16.6.20 / 172.16.6.10 |
| Ubuntu Clone | ATTACKER, thay thế Kali Linux | 10.10.0.200 |

Môi trường sử dụng các mạng nội bộ VMware để tránh ảnh hưởng đến hệ thống mạng bên ngoài.

## 3. Quá trình thực hiện

### Bước 1: Chuẩn bị hệ thống máy ảo

- Kiểm tra các máy ảo đã cài đặt trong VMware Workstation.
- Sử dụng Ubuntu, Windows 10, Windows Server 2025 và pfSense-Lab có sẵn.
- Clone Ubuntu để tạo máy Attacker riêng, thay thế Kali Linux theo mô hình ban đầu.
- Thiết lập các card mạng ảo phục vụ việc phân chia subnet.

### Bước 2: Cấu hình mạng VMware

Sử dụng Virtual Network Editor để thiết lập những mạng riêng biệt:

- VMnet1: Mạng quản trị pfSense – 192.168.56.0/24.
- VMnet2: DNSLAB – 10.9.0.0/24.
- VMnet3: DMZ – 172.16.6.0/24.
- VMnet4: ATTACKNET – 10.10.0.0/24.
- VMnet5: Mạng LAN cũ – 10.6.0.0/24.
- VMnet8: NAT/WAN – 192.168.250.0/24.

Các mạng nội bộ được cấu hình riêng để phục vụ việc kiểm soát lưu lượng bằng pfSense.

### Bước 3: Cấu hình pfSense

Khởi động pfSense 2.7.2 và tiến hành kiểm tra, gán các interface mạng.

Các địa chỉ IP đã cấu hình và xác nhận trên pfSense:

| Interface | Card | Địa chỉ IP |
|---|---|---|
| LAN | em2 | 10.6.0.1/24 |
| OPT1 | em1 | 192.168.56.254/24 |
| OPT2 | em3 | 172.16.6.1/24 |
| ATTACKNET (OPT3) | em4 | 10.10.0.1/24 |
| DNSLAB (OPT4) | em5 | 10.9.0.1/24 |

Kiểm tra bằng lệnh `ifconfig -l`, xác nhận pfSense nhận đủ sáu card mạng từ em0 đến em5.

Truy cập thành công giao diện WebGUI của pfSense để tiếp tục quản trị firewall.

### Bước 4: Cấu hình Firewall Rules

Trên giao diện WebGUI của pfSense, tiến hành tạo các quy tắc cho DNSLAB và ATTACKNET.

**Nhóm DNSLAB:**

- Cho phép Victim gửi truy vấn DNS đến DNS-SERVER.
- Cho phép DNS-SERVER truy vấn AUTH-DNS qua UDP 53.
- Cho phép DNS-SERVER liên lạc với DNS mô phỏng của Attacker qua UDP 53.
- Cấu hình cho phép truy cập website mô phỏng qua TCP 80.
- Thiết lập quy tắc Block đối với lưu lượng không được phép.

**Nhóm ATTACKNET:**

- Cho phép Attacker truy vấn DNS-SERVER qua TCP/UDP 53.
- Cho phép Attacker giao tiếp với Race Relay qua UDP 5300.
- Thiết lập quy tắc Block để hạn chế lưu lượng ngoài phạm vi được phép.

Trong quá trình cấu hình, đã phát hiện và xóa quy tắc UDP 5300 bị trùng, đồng thời điều chỉnh thứ tự Firewall Rules để các quy tắc Pass được ưu tiên trước quy tắc Block.

### Bước 5: Kiểm tra Ubuntu DNS-SERVER

Khởi động Ubuntu 64-bit và sử dụng các lệnh:

```bash
ip -br addr
ip route
```

Kết quả xác nhận:

- Card mạng: ens33.
- Trạng thái: UP.
- Địa chỉ IPv4: 10.9.0.10/24.
- Máy đã nhận địa chỉ thuộc mạng DNSLAB.

Tiến hành bổ sung Default Gateway 10.9.0.1 bằng lệnh:

```bash
sudo ip route add default via 10.9.0.1 dev ens33
```

Tuy nhiên, hệ thống xuất hiện thông báo:

```text
Error: Nexthop has invalid gateway.
```

Vì vậy, bước cấu hình Default Gateway chưa được xác nhận thành công và cần tiếp tục kiểm tra.

## 4. Kết quả đạt được

Qua quá trình thực hành, đã đạt được các kết quả sau:

1. Chuẩn bị môi trường LAB 10 bằng VMware Workstation và những máy ảo có sẵn.
2. Tạo máy Ubuntu Clone phục vụ vai trò Attacker.
3. Thiết lập hệ thống mạng ảo gồm các subnet DNSLAB, ATTACKNET, DMZ và mạng quản trị.
4. pfSense nhận đủ sáu card mạng từ em0 đến em5.
5. Cấu hình thành công địa chỉ IP các interface pfSense, đặc biệt là DNSLAB 10.9.0.1/24 và ATTACKNET 10.10.0.1/24.
6. Truy cập thành công giao diện quản trị WebGUI pfSense.
7. Tạo các quy tắc Firewall cho hoạt động truy vấn DNS và Race Relay trong môi trường LAB 10.
8. Phát hiện và loại bỏ quy tắc Firewall bị trùng, thực hiện điều chỉnh thứ tự Pass/Block.
9. Xác nhận Ubuntu DNS-SERVER có địa chỉ IP 10.9.0.10/24 và card ens33 đang hoạt động.

**Các nội dung chưa hoàn thành hoặc chưa được xác minh:**

- Cấu hình Default Gateway của Ubuntu DNS-SERVER.
- Kiểm tra kết nối đầy đủ giữa các máy ảo.
- Chạy AUTH-DNS và xác nhận kết quả DNS Baseline.
- Kiểm chứng Attacker không sniff được lưu lượng DNS upstream.
- Chạy resolver mô phỏng và xác nhận log PENDING/HIT/CACHE.
- Kiểm chứng DNS Cache Poisoning làm thay đổi kết quả từ 172.16.6.10 sang 10.10.0.200.
- Bắt gói bằng Wireshark/tcpdump và phân tích phản hồi DNS giả mạo.
- Khôi phục BIND9 và kiểm tra các biện pháp hardening.

## 5. Kết luận

Quá trình thực hành đã xây dựng được phần nền tảng mạng cho LAB 10 bằng VMware Workstation và pfSense, bao gồm việc phân chia mạng nội bộ, cấu hình địa chỉ IP và xây dựng các quy tắc kiểm soát lưu lượng.

Bài thực hành giúp hiểu rõ vai trò của firewall, subnet và việc cô lập Attacker khỏi DNS-SERVER trong mô hình Remote DNS Cache Poisoning.

Tuy nhiên, quá trình hiện tại mới hoàn thành phần chuẩn bị môi trường và cấu hình mạng. Các bước mô phỏng tấn công, phân tích DNS Cache và khôi phục BIND9 cần tiếp tục thực hiện và kiểm chứng để hoàn thành đầy đủ mục tiêu LAB 10.
