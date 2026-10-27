
1150080012 Bùi Hải Đường 11CNPM1

# README – LAB 8: LOCAL DNS ATTACK

## 1. Thông tin bài thực hành

**Tên bài:** Local DNS Attack – DNS Spoofing và DNS Cache Poisoning trong mạng ảo cô lập.

**Môn học:** An toàn bảo mật hệ thống thông tin.

**Môi trường:** VMware Workstation.

**Mục tiêu:** Tìm hiểu cơ chế hoạt động của DNS, xây dựng môi trường DNS Server BIND9, kiểm tra phân giải tên miền và nghiên cứu các hình thức tấn công DNS Spoofing, DNS Cache Poisoning.

## 2. Môi trường thực hiện

Sử dụng các máy ảo có sẵn trên VMware Workstation, không sử dụng Kali Linux và sf_virtual.

| Máy ảo | Vai trò | Địa chỉ IP dự kiến |
|---|---|---|
| Ubuntu Desktop 25.04 | DNS Server BIND9 | 10.9.0.10 |
| Windows Server 2025 | Victim | 10.9.0.100 |
| Windows 10 | Máy thử nghiệm | 10.9.0.200 |
| pfSense-Lab | Firewall | 10.9.0.1 |

Công cụ sử dụng: Ubuntu Terminal, NetworkManager, Netplan, BIND9 Utilities, DNS Utilities, tcpdump và VMware Workstation.

## 3. Quá trình thực hiện

### Bước 1: Kiểm tra hệ điều hành Ubuntu

Sử dụng các lệnh:

```bash
cat /etc/os-release
ip -br addr
ip route
```

Xác định máy ảo sử dụng Ubuntu Desktop 25.04 với card mạng `ens33`.

Ban đầu, card mạng chưa được cấp IPv4 và chưa có tuyến đường mặc định.

### Bước 2: Cấu hình địa chỉ IP

Tiến hành cấu hình địa chỉ IP tĩnh cho Ubuntu bằng NetworkManager và Netplan.

Địa chỉ IP lab được sử dụng là `10.9.0.10/24`.

Kiểm tra bằng lệnh:

```bash
ip -br addr
```

**Kết quả:** Card `ens33` đã nhận địa chỉ `10.9.0.10/24`.

### Bước 3: Thiết lập kết nối Internet

Cấu hình kết nối NAT tạm thời để Ubuntu có thể tải các gói phần mềm phục vụ bài thực hành.

Kiểm tra kết nối:

```bash
ping -c 4 1.1.1.1
```

**Kết quả:** Gửi 4 gói tin, nhận đủ 4 gói, tỷ lệ mất gói 0%.

Máy Ubuntu có thể kết nối Internet để tải phần mềm.

### Bước 4: Cập nhật hệ thống

Thực hiện:

```bash
sudo apt update
```

**Kết quả:** Lệnh cập nhật danh sách gói hoàn thành thành công.

### Bước 5: Cài đặt công cụ phục vụ DNS

Thực hiện cài đặt các gói công cụ gồm:

- bind9-utils
- dnsutils
- tcpdump

Sau khi cài đặt, hệ thống hoàn thành quá trình giải nén và thiết lập các gói công cụ.

### Bước 6: Kiểm tra dịch vụ BIND9

Sử dụng:

```bash
sudo systemctl enable --now named
sudo systemctl status named --no-pager
```

Hệ thống trả về:

```text
Unit named.service could not be found.
```

**Kết quả:** Chưa khởi động được dịch vụ DNS Server BIND9. Cần kiểm tra và hoàn tất cài đặt gói `bind9` chính trước khi tiếp tục cấu hình.

## 4. Kết quả đạt được

| Nội dung | Trạng thái |
|---|---|
| Kiểm tra phiên bản Ubuntu 25.04 | Hoàn thành |
| Xác định card mạng ens33 | Hoàn thành |
| Cấu hình IP 10.9.0.10/24 | Hoàn thành |
| Kết nối Internet bằng NAT | Hoàn thành |
| Kiểm tra ping Internet | Thành công |
| Cập nhật danh sách gói APT | Hoàn thành |
| Cài đặt các công cụ hỗ trợ DNS | Hoàn thành |
| Khởi động DNS Server BIND9 | Chưa thành công |
| Cấu hình DNS zone | Chưa thực hiện |
| Kiểm tra phân giải DNS | Chưa thực hiện |
| Task 1 – HOSTS File Redirection | Chưa thực hiện |
| Task 2 – Direct DNS Spoofing | Chưa thực hiện |
| Task 3 – DNS Cache Poisoning | Chưa thực hiện |

## 5. Khó khăn trong quá trình thực hiện

Trong quá trình cấu hình mạng, Ubuntu gặp lỗi kích hoạt kết nối và chưa nhận được địa chỉ IPv4.

Sau khi điều chỉnh cấu hình, máy đã nhận địa chỉ IP tĩnh và kết nối Internet thành công.

Trong quá trình chuẩn bị DNS Server, hệ thống chưa tìm thấy dịch vụ `named.service`, do đó chưa thể chuyển sang bước cấu hình và kiểm tra DNS.

## 6. Kết luận

Đã xây dựng được một phần môi trường thực hành Local DNS Attack trên VMware Workstation bằng các máy ảo có sẵn.

Máy Ubuntu Desktop 25.04 đã được cấu hình địa chỉ IP tĩnh, kết nối Internet thành công và cài đặt các công cụ hỗ trợ DNS.

Tuy nhiên, bài thực hành hiện mới hoàn thành giai đoạn chuẩn bị môi trường. Các nhiệm vụ DNS Spoofing và DNS Cache Poisoning chưa được triển khai, cần tiếp tục hoàn thiện dịch vụ BIND9 và cấu hình các máy thử nghiệm để thực hiện đầy đủ yêu cầu của bài lab.
