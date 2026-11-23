# LAB 3 – MÃ HÓA KHÓA CÔNG KHAI

## 1. Thông tin sinh viên

- **Họ và tên:** Bùi Hải Đường
- **MSSV:** 1150080012
- **Lớp:** 11CNPM1
- **Học phần:** An toàn và Bảo mật thông tin
- **Bài thực hành:** LAB 3 – Mã hóa khóa công khai
- **Môi trường phát triển:** Eclipse IDE, Java 17, Maven, SQL Server 2022 Express

---

## 2. Giới thiệu đề tài

LAB 3 tập trung nghiên cứu và xây dựng các chương trình mã hóa khóa công khai, sử dụng những thuật toán RSA, ECC, hệ mã lai RSA + AES và chữ ký số ECDSA.

Mục tiêu của bài thực hành:

- Tìm hiểu nguyên lý hoạt động của mật mã khóa công khai.
- Sinh và sử dụng cặp khóa Public Key và Private Key.
- Thực hiện mã hóa và giải mã dữ liệu bằng RSA.
- Ứng dụng ECC kết hợp ECDH, HKDF và AES-GCM.
- Xây dựng hệ mã lai RSA + AES để mã hóa văn bản dài.
- Tạo và xác minh chữ ký số bằng ECDSA.
- Kết nối Microsoft SQL Server để ghi nhận lịch sử thực hiện.
- Xây dựng giao diện Java Swing giúp người dùng thao tác thuận tiện.

Project được xây dựng phục vụ mục đích học tập và nghiên cứu các kỹ thuật mật mã.

---

## 3. Công nghệ sử dụng

| Công nghệ | Mục đích sử dụng |
|---|---|
| Java JDK 17 | Ngôn ngữ lập trình chính |
| Eclipse IDE | Phát triển và chạy ứng dụng |
| Apache Maven | Quản lý project và thư viện |
| Java Swing | Xây dựng giao diện desktop |
| Java Cryptography Architecture | Thực hiện các thuật toán mật mã |
| RSA-OAEP SHA-256 | Mã hóa khóa công khai |
| ECC secp256r1 | Mật mã đường cong elliptic |
| ECDH + HKDF-SHA256 | Tạo khóa AES từ bí mật chung |
| AES-256-GCM | Mã hóa và xác thực dữ liệu |
| ECDSA SHA-256 | Tạo và xác minh chữ ký số |
| Microsoft SQL Server 2022 Express | Lưu lịch sử thao tác |
| Microsoft JDBC Driver | Kết nối Java với SQL Server |

---

## 4. Cấu trúc thư mục project

```text
LAB3_MaHoaKhoaCongKhai/
│
├── pom.xml
├── README.md
│
├── src/
│   └── main/
│       └── java/
│           └── week_03/
│               ├── CryptoSupport.java
│               ├── FileTextIO.java
│               ├── DbConfig.java
│               ├── HistoryDao.java
│               │
│               ├── RSACipher.java
│               ├── RSADemo.java
│               ├── ECCCipher.java
│               ├── RSA_AES_Cipher.java
│               ├── ECCSignature.java
│               │
│               ├── frm_RSA.java
│               ├── frm_ECC.java
│               ├── frm_RSA_AES.java
│               ├── frm_ECC_Signature.java
│               ├── frm_KetNoiSQL.java
│               ├── frm_LichSu.java
│               └── frm_Menu.java
│
├── sql/
│   ├── 01_Tao_CSDL.sql
│   ├── 02_Tao_TaiKhoan_LAB.sql
│   └── 03_KiemTra_LichSu.sql
│
└── screenshots/
    ├── 00_Menu.png
    ├── 01_RSA_2048.png
    ├── 02_RSA_3072.png
    ├── 03_RSA_Swing.png
    ├── 04_ECC_ThanhCong.png
    ├── 05_ECC_SaiKhoa.png
    ├── 06_RSA_AES_ThanhCong.png
    ├── 07_RSA_AES_SaiBanMa.png
    ├── 08_ECDSA_VALID.png
    ├── 09_ECDSA_INVALID.png
    ├── 10_LichSu.png
    └── 11_SQL_Server.png
```

*Thư mục screenshots chứa ảnh minh chứng được bổ sung sau khi chạy chương trình thực tế.*

---

## 5. Quá trình thực hiện

### Bước 1: Chuẩn bị môi trường

Sử dụng Eclipse IDE với Java JDK 17 và Apache Maven.

Kiểm tra phiên bản Java:

```bash
java -version
javac -version
```

Thông tin Maven Project:

```text
Project Name: LAB3_MaHoaKhoaCongKhai
Group ID: vn.edu.lab
Artifact ID: LAB3_MaHoaKhoaCongKhai
Version: 1.0-SNAPSHOT
Packaging: jar
Package: week_03
```

Các thuật toán RSA, ECC, AES và ECDSA sử dụng thư viện mật mã có sẵn trong Java. Project bổ sung Microsoft JDBC Driver để kết nối SQL Server.

### Bước 2: Xây dựng lớp dùng chung

**CryptoSupport.java**

Cung cấp các phương thức:

- Chuyển đổi Base64 và dữ liệu byte.
- Xử lý chuỗi UTF-8.
- Sinh dữ liệu ngẫu nhiên bằng SecureRandom.
- Mã hóa và giải mã AES-256-GCM.
- Tạo khóa AES bằng HKDF-SHA256.
- Ghép và tách các mảng byte.
- Chuyển đổi khóa công khai/khóa riêng sang định dạng lưu tệp.

**FileTextIO.java**

Hỗ trợ đọc và ghi tệp văn bản UTF-8, mở và lưu khóa mã hóa thông qua JFileChooser.

### Bước 3: Xây dựng các thuật toán

Các thuật toán được tách riêng thành lớp xử lý và giao diện Java Swing.

### Bước 4: Xây dựng database

Tạo cơ sở dữ liệu `LAB3_MaHoaKhoaCongKhai` trên SQL Server 2022 Express.

Tạo bảng `dbo.History` để ghi:

- Thuật toán sử dụng.
- Thao tác ENCRYPT, DECRYPT, SIGN hoặc VERIFY.
- Độ dài dữ liệu đầu vào.
- Trạng thái thành công hoặc thất bại.
- Thời điểm thực hiện.

Không lưu khóa riêng hoặc nội dung bản rõ vào lịch sử.

### Bước 5: Xây dựng giao diện

Các cửa sổ chính:

- `frm_RSA`
- `frm_ECC`
- `frm_RSA_AES`
- `frm_ECC_Signature`
- `frm_KetNoiSQL`
- `frm_LichSu`
- `frm_Menu`

Giao diện được xây dựng trực tiếp bằng Java Swing, phù hợp chạy trong Eclipse.

---

## 6. Nội dung các bài thực hành và kết quả kiểm thử

### Bài 3.1 – Mật mã RSA Console

**Thuật toán:** RSA/ECB/OAEPWithSHA-256AndMGF1Padding.

Chương trình thực hiện:

1. Nhập kích thước khóa 2048 hoặc 3072 bit.
2. Sinh cặp khóa RSA.
3. Nhập bản rõ.
4. Mã hóa bằng Public Key.
5. Giải mã bằng Private Key.
6. So sánh kết quả sau giải mã.

**Dữ liệu kiểm thử:**

```text
Key size: 2048
Plaintext: HELLO RSA
```

**Kết quả mong đợi:**

```text
Gioi han ban ro: 190 byte
Giai ma: HELLO RSA
Khoi phuc dung: true
```

**So sánh RSA 2048 và 3072 bit:**

| Tiêu chí | RSA-2048 | RSA-3072 |
|---|---|---|
| Kích thước khóa | 2048 bit | 3072 bit |
| Giới hạn bản rõ | 190 byte | 318 byte |
| Ciphertext Base64 | 344 ký tự | 512 ký tự |
| Giải mã đúng bản rõ | Có | Có |

Thời gian sinh khóa phụ thuộc vào cấu hình máy và cần được ghi lại khi demo.

**Ảnh minh chứng:**

![RSA 2048](screenshots/01_RSA_2048.png)

![RSA 3072](screenshots/02_RSA_3072.png)

### Bài 3.2 – RSA với giao diện Swing

Sử dụng lớp `RSACipher.java` và giao diện `frm_RSA.java`.

**Quy trình kiểm thử:**

1. Chọn kích thước khóa 2048 bit.
2. Nhấn Generate Keys.
3. Nhập `HELLO RSA`.
4. Nhấn Encrypt.
5. Xóa Plaintext.
6. Nhấn Decrypt.

**Kết quả mong đợi:**

- Sinh cặp khóa RSA thành công.
- Ciphertext hiển thị dưới dạng Base64.
- Decrypt khôi phục `HELLO RSA`.
- Mã hóa hai lần cùng bản rõ cho ra hai bản mã khác nhau do OAEP sử dụng tính ngẫu nhiên.
- Chương trình thông báo lỗi nếu bản rõ vượt giới hạn RSA.

**Ảnh minh chứng:**

![RSA Swing](screenshots/03_RSA_Swing.png)

### Bài 3.3 – Mật mã đường cong Elliptic (ECC)

**Thuật toán:** ECDH + HKDF-SHA256 + AES-256-GCM.

Sử dụng đường cong `secp256r1`.

**Nguyên lý hoạt động:**

1. Sinh cặp khóa ECC cho người nhận.
2. Sinh cặp khóa ECC tạm thời.
3. Sử dụng ECDH để tạo bí mật chung.
4. HKDF-SHA256 tạo khóa AES 256 bit.
5. AES-GCM mã hóa dữ liệu.
6. Người nhận sử dụng Private Key để khôi phục dữ liệu.

**Dữ liệu kiểm thử:**

```text
Plaintext: HELLO ECC
```

**Kết quả mong đợi:**

- Generate Keys thành công.
- Encrypt tạo Ciphertext Base64.
- Decrypt khôi phục chính xác `HELLO ECC`.
- Save Keys và Load Keys hoạt động.
- Giải mã bằng khóa riêng khác phải thất bại.
- Ciphertext bị sửa đổi không thể xác thực thành công.

**Ảnh minh chứng:**

![ECC thành công](screenshots/04_ECC_ThanhCong.png)

![ECC sai khóa](screenshots/05_ECC_SaiKhoa.png)

### Bài 3.4 – Hệ mã lai RSA + AES

**Thuật toán:** RSA-OAEP kết hợp AES-256-GCM.

RSA chỉ được sử dụng để bảo vệ khóa AES. Dữ liệu dài được mã hóa bằng AES-GCM.

**Quy trình thực hiện:**

1. Generate Keys RSA 2048 bit.
2. Sinh khóa AES ngẫu nhiên 32 byte.
3. Sinh nonce 12 byte.
4. AES-GCM mã hóa văn bản.
5. RSA-OAEP mã hóa khóa AES.
6. Ghép khóa AES đã mã hóa, nonce và ciphertext.
7. Giải mã bằng khóa riêng RSA và AES-GCM.

**Dữ liệu kiểm thử:**

```text
Plaintext: Văn bản dài trên 1000 ký tự.
```

**Kết quả mong đợi:**

- Encrypt thành công với văn bản trên 1.000 ký tự.
- Decrypt khôi phục toàn bộ nội dung.
- Ciphertext hiển thị Base64.
- Thay đổi một ký tự trong bản mã khiến giải mã thất bại.
- Cơ chế AES-GCM phát hiện dữ liệu bị chỉnh sửa.

**Ảnh minh chứng:**

![RSA AES thành công](screenshots/06_RSA_AES_ThanhCong.png)

![RSA AES bản mã bị sửa](screenshots/07_RSA_AES_SaiBanMa.png)

### Bài 3.5 – Chữ ký số ECDSA

**Thuật toán:** SHA256withECDSA.

Chương trình sử dụng khóa riêng để ký và khóa công khai để xác minh.

**Dữ liệu kiểm thử:**

```text
Message: THONG DIEP A
```

**Quy trình:**

1. Generate Keys.
2. Nhập Message.
3. Nhấn Sign.
4. Nhấn Verify.
5. Thay đổi nội dung Message.
6. Nhấn Verify lại.

**Kết quả mong đợi:**

| Thao tác | Kết quả |
|---|---|
| Ký `THONG DIEP A` | Sinh chữ ký Base64 |
| Verify thông điệp gốc | VALID |
| Đổi thành `THONG DIEP B` | INVALID |
| Đổi lại `THONG DIEP A` | VALID |
| Generate Keys mới rồi Verify chữ ký cũ | INVALID |

**Ý nghĩa:** ECDSA giúp phát hiện thông điệp bị chỉnh sửa và kiểm tra chữ ký có được tạo bởi khóa riêng tương ứng hay không.

**Ảnh minh chứng:**

![ECDSA VALID](screenshots/08_ECDSA_VALID.png)

![ECDSA INVALID](screenshots/09_ECDSA_INVALID.png)

---

## 7. SQL Server – Quản lý lịch sử thao tác

### Thông tin kết nối

| Thuộc tính | Giá trị |
|---|---|
| SQL Server | SQL Server 2022 Express |
| Instance | SUGAR |
| Server JDBC | 127.0.0.1 |
| Port | 14333 |
| Database | LAB3_MaHoaKhoaCongKhai |
| User | lab3_user |
| Authentication | SQL Server Authentication |

Mật khẩu SQL được nhập trong giao diện khi chạy chương trình, không lưu trực tiếp vào mã nguồn Java.

### Các script SQL

```text
sql/01_Tao_CSDL.sql
sql/02_Tao_TaiKhoan_LAB.sql
sql/03_KiemTra_LichSu.sql
```

### Kiểm tra lịch sử

```sql
USE LAB3_MaHoaKhoaCongKhai;
GO

SELECT TOP (30)
    Id,
    Algorithm,
    Operation,
    InputLength,
    Success,
    CreatedAt
FROM dbo.History
ORDER BY Id DESC;
GO

SELECT
    Algorithm,
    Operation,
    COUNT(*) AS SoLan
FROM dbo.History
GROUP BY Algorithm, Operation
ORDER BY Algorithm, Operation;
GO
```

**Kết quả mong đợi:**

- Lưu lịch sử các thao tác RSA, ECC, RSA-AES và ECDSA.
- Ghi lại các lần thực hiện thành công hoặc thất bại.
- Xem tối đa 50 bản ghi mới nhất bằng Java Swing.
- Kiểm tra và thống kê lịch sử bằng SQL Server Management Studio.

**Ảnh minh chứng:**

![Lịch sử Java](screenshots/10_LichSu.png)

![SQL Server](screenshots/11_SQL_Server.png)

---

## 8. Hướng dẫn chạy project

### 8.1. Import vào Eclipse IDE

**Bước 1:** Mở Eclipse IDE.

**Bước 2:** Chọn:

```text
File → Import → Maven → Existing Maven Projects
```

**Bước 3:** Chọn thư mục:

```text
LAB3_MaHoaKhoaCongKhai
```

**Bước 4:** Nhấn Finish.

**Bước 5:** Nhấp chuột phải project:

```text
Maven → Update Project
```

**Bước 6:** Mở file:

```text
src/main/java/week_03/frm_Menu.java
```

**Bước 7:** Chọn:

```text
Run As → Java Application
```

Chương trình hiển thị menu chính để mở các chức năng.

### 8.2. Chạy RSA Console

Mở:

```text
src/main/java/week_03/RSADemo.java
```

Chọn **Run As → Java Application**.

Nhập kích thước khóa và bản rõ trong Console.

### 8.3. Kết nối SQL Server

Trong SQL Server Management Studio, kết nối tới instance `SUGAR` bằng Windows Authentication rồi chạy các script:

```text
01_Tao_CSDL.sql
02_Tao_TaiKhoan_LAB.sql
```

Bật TCP/IP và bảo đảm SQL Server đang lắng nghe cổng cấu hình.

Trong menu Java, chọn **Kết nối SQL Server**, nhập thông tin kết nối và nhấn Test Connection.

Sau khi có dữ liệu, chạy `03_KiemTra_LichSu.sql` để kiểm tra lịch sử.

---

## 9. Tổng hợp kết quả kiểm thử

| STT | Nội dung | Kết quả mong đợi | Trạng thái thực tế |
|---|---|---|---|
| 1 | RSA Console 2048 bit | HELLO RSA khôi phục đúng | Cần xác nhận |
| 2 | RSA Console 3072 bit | HELLO RSA khôi phục đúng | Cần xác nhận |
| 3 | RSA Swing | Encrypt/Decrypt thành công | Cần xác nhận |
| 4 | ECC | HELLO ECC khôi phục đúng | Cần xác nhận |
| 5 | ECC khóa sai | Giải mã thất bại | Cần xác nhận |
| 6 | RSA + AES | Văn bản trên 1000 ký tự khôi phục đúng | Cần xác nhận |
| 7 | RSA + AES sửa bản mã | Giải mã thất bại | Cần xác nhận |
| 8 | ECDSA | Thông điệp gốc VALID | Cần xác nhận |
| 9 | ECDSA sửa thông điệp | INVALID | Cần xác nhận |
| 10 | SQL Server | Lưu và xem lịch sử | Cần xác nhận |

Trạng thái thực tế cần được cập nhật theo kết quả chạy và ảnh chụp trên máy thực hành.

---

## 10. Nhận xét và đánh giá

Qua quá trình thực hiện LAB 3, các nội dung giúp củng cố kiến thức về mật mã khóa công khai và ứng dụng của chúng trong bảo vệ thông tin.

**Kiến thức thu được:**

- Phân biệt khóa công khai và khóa riêng.
- Hiểu giới hạn dữ liệu khi mã hóa trực tiếp bằng RSA.
- Sử dụng RSA-OAEP để mã hóa dữ liệu ngắn.
- Ứng dụng ECDH để tạo bí mật chung.
- Sử dụng HKDF để dẫn xuất khóa AES.
- Hiểu vai trò của AES-GCM trong mã hóa có xác thực.
- Kết hợp RSA và AES nhằm xử lý văn bản dài.
- Sử dụng ECDSA để tạo và xác minh chữ ký số.
- Kết nối JDBC với SQL Server để quản lý lịch sử thao tác.

### Hạn chế

- Ứng dụng chủ yếu phục vụ mục đích học tập.
- Chưa xây dựng cơ chế quản lý khóa chuyên nghiệp.
- Việc lưu khóa riêng vào tệp văn bản chỉ phục vụ demo; không phù hợp với hệ thống thực tế.
- Chưa tích hợp hệ thống chứng thư số hoặc cơ sở hạ tầng khóa công khai PKI.
- Giao diện được phát triển cho ứng dụng desktop, chưa triển khai trên web.

---

## 11. Kết luận

LAB 3 giúp tìm hiểu và thực hành các kỹ thuật mã hóa khóa công khai, gồm RSA, ECC, hệ mã lai RSA + AES và chữ ký số ECDSA.

Thông qua quá trình xây dựng chương trình Java Swing và tích hợp SQL Server, sinh viên củng cố kỹ năng phát triển ứng dụng Java, sử dụng Java Cryptography Architecture, xử lý khóa mật mã và quản lý lịch sử thao tác.

Bài thực hành cũng làm rõ sự khác biệt giữa mã hóa dữ liệu và chữ ký số, đồng thời cho thấy tầm quan trọng của việc bảo vệ khóa riêng và xác minh tính toàn vẹn của thông tin.

---

## 12. Tài liệu tham khảo

1. Tài liệu học phần An toàn và Bảo mật thông tin – LAB 3: Mã hóa khóa công khai.
2. Oracle Java Cryptography Architecture Documentation.
3. Oracle Java Security API Documentation.
4. Microsoft JDBC Driver for SQL Server Documentation.
5. RFC 5869 – HMAC-based Extract-and-Expand Key Derivation Function (HKDF).

---

**Sinh viên thực hiện:** Bùi Hải Dương  
**Mã số sinh viên:** 1150080012  
**Học phần:** An toàn và Bảo mật thông tin  
**Bài thực hành:** LAB 3 – Mã hóa khóa công khai
