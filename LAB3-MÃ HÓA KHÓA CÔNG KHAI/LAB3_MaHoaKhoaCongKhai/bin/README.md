# LAB 3 - Mã hóa khóa công khai

**Họ tên:** [Điền họ tên]  
**MSSV:** [Điền MSSV]  
**Lớp:** [Điền lớp]

## Nội dung theo LAB3_HuongDan.pdf

- **3.1 RSA Console:** `RSADemo.java`, RSA-OAEP SHA-256, khóa 2048/3072 bit.
- **3.2 RSA Swing:** `frm_RSA.java`.
- **3.3 ECC:** P-256, ECDH + HKDF-SHA256 + AES-256-GCM, `frm_ECC.java`.
- **3.4 RSA + AES:** hệ mã lai RSA-OAEP + AES-256-GCM, `frm_RSA_AES.java`.
- **3.5 ECDSA:** SHA256withECDSA ký và xác minh, `frm_ECC_Signature.java`.
- **SQL Server:** `dbo.History`, form kết nối và xem lịch sử.

Ứng dụng dùng Java 17 / Maven / Java Swing tạo bằng code để có thể chạy trên Eclipse; do đó không có tệp NetBeans `.form`. Các thuật toán sử dụng JCA/JCE có sẵn trong JDK.

## Import Eclipse

1. Giải nén ZIP. Eclipse > **File > Import > Maven > Existing Maven Projects**.
2. Browse đến thư mục `LAB3_MaHoaKhoaCongKhai` có `pom.xml` > Finish.
3. Chọn JDK 17 (hoặc JDK mới hơn hỗ trợ release 17), **Maven > Update Project**.
4. Chạy `week_03.frm_Menu` bằng **Run As > Java Application**.
5. Bài 3.1 chạy riêng `week_03.RSADemo` và nhập `2048`, `HELLO RSA` trong Console.

## Demo và kết quả mong đợi

| Bài | Dữ liệu | Kiểm tra |
|---|---|---|
| RSA | HELLO RSA, 2048/3072 bit | Giải mã đúng, ciphertext Base64 lần lượt 344/512 ký tự |
| ECC | HELLO ECC | Giải mã đúng, đổi khóa mới thì thất bại |
| RSA + AES | Đoạn văn hơn 1000 ký tự | Giải mã đúng; bấm Sửa 1 byte bản mã thì giải mã thất bại |
| ECDSA | THONG DIEP A | Sign > Verify: VALID; sửa thông điệp: INVALID |
| SQL | Encrypt/Decrypt/Sign/Verify | Có bản ghi mới trong `dbo.History` nếu đã kết nối |

## SQL Server instance SUGAR

Trong SSMS, dùng Windows Authentication kết nối `LAPTOP-1OLPGQ5K\SUGAR` hoặc instance thực tế của bạn. Chạy `sql/01_Tao_CSDL.sql`, tiếp theo **sửa mật khẩu placeholder** trong `sql/02_Tao_TaiKhoan_LAB.sql` rồi Execute. SQL Server cần bật Mixed Mode và TCP/IP 14333. Trong form chọn server `127.0.0.1`, port `14333`, database `LAB3_MaHoaKhoaCongKhai`, login `lab3_user`, nhập mật khẩu vừa tạo rồi Test Connection. Cuối cùng chạy `sql/03_KiemTra.sql` để xem lịch sử.

**An toàn:** không commit khóa riêng `PRIVATE=...`, mật khẩu, file chứa khóa thật hoặc ảnh chụp khóa bí mật. File khóa Save Keys chỉ dùng cục bộ, không upload GitHub. `trustServerCertificate=true` chỉ dành cho môi trường LAB.

## Nộp GitHub

Đưa lên `pom.xml`, thư mục `src/`, `sql/`, `README.md` và ảnh minh chứng thực tế vào `screenshots/`. Không đưa thư mục `target/`, khóa riêng và mật khẩu thật. Điền họ tên/MSSV trước khi nộp.
