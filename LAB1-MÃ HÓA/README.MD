Bùi Hải Đường 1150080012 CNPM1

LAB 1 - MÃ HÓA ĐỐI XỨNG CỔ ĐIỂN
1. Giới thiệu
Tên project: LAB1_MaHoaCoDien
Ngôn ngữ: Java 17
IDE: Eclipse IDE 2025-12
Quản lý thư viện: Apache Maven 3.9.11 (Embedded)
Giao diện: Java Swing
Cơ sở dữ liệu: Microsoft SQL Server 2022 Express, instance SUGAR
JDBC: com.microsoft.sqlserver:mssql-jdbc:12.8.1.jre11

Project minh họa sáu thuật toán mật mã cổ điển, chức năng mã hóa/giải mã văn bản, mở/lưu tệp, tích hợp lưu lịch sử vào SQL Server, và ba kỹ thuật phân tích mật mã. Bài được triển khai bằng Eclipse với giao diện Swing viết bằng Java, thay vì NetBeans GUI Builder.
2. Mục tiêu
- Cài đặt sáu thuật toán: Caesar, Vigenère, Rail Fence, Playfair, Transposition và Autokey.
- Xây dựng giao diện riêng cho từng thuật toán và giao diện tổng hợp.
- Hỗ trợ đọc/ghi văn bản .txt.
- Lưu, hiển thị lịch sử thao tác mã hóa/giải mã trên SQL Server.
- Minh họa vét cạn Caesar, phân tích tần suất và suy ra khóa khi biết bản rõ.
- Kiểm thử các kết quả theo tài liệu LAB 1.
3. Quá trình thực hiện
Giai đoạn 1 - Chuẩn bị môi trường
1. Kiểm tra JDK bằng java -version và javac -version.
2. Xác nhận Eclipse có Maven Embedded và cài WindowBuilder (không bắt buộc khi viết Swing trực tiếp).
3. Tạo Maven Project LAB1_MaHoaCoDien, package week_01 trong src/main/java.
4. Cấu hình maven.compiler.release=17 và thêm dependency SQL Server JDBC trong pom.xml.
5. Chỉnh project sử dụng JDK 17 thay cho JavaSE-1.8.
6. Khắc phục lỗi cấu hình pom.xml và lỗi tên file Java không trùng tên public class.
Giai đoạn 2 - Thuật toán mã hóa
1. Tạo FileTextIO.java để đọc/ghi tệp văn bản UTF-8.
2. Tạo sáu lớp thuật toán *Cipher.java, mỗi lớp cung cấp phương thức encrypt() và decrypt().
3. Tạo các form frm_*.java bằng Swing để nhập Plaintext, Key, hiển thị Ciphertext và thực hiện Encrypt/Decrypt.
4. Kiểm tra đầu vào, báo lỗi khóa không hợp lệ và thử chức năng Open/Save.
Giai đoạn 3 - Giao diện tổng hợp
1. Tạo CipherTongHop.java để điều phối thuật toán được chọn.
2. Tạo frm_TongHop.java để người dùng chọn thuật toán và thao tác trên cùng một form.
3. Liên kết thao tác thành công với chức năng lưu lịch sử khi cơ sở dữ liệu đã kết nối.
Giai đoạn 4 - SQL Server
1. Sử dụng SQL Server 2022 Express, instance SUGAR trên máy, không cần tạo instance SQLEXPRESS mới.
2. Bật Mixed Mode (SQL Server and Windows Authentication).
3. Tạo database LAB1_MaHoaCoDien và bảng dbo.history.
4. Tạo tài khoản lab1_user với quyền SELECT, INSERT, DELETE trên bảng lịch sử.
5. Cấu hình TCP/IP và cổng kết nối thực tế, sau đó thử kết nối bằng SSMS và JDBC.
6. Tạo DbConfig.java, HistoryDao.java, frm_KetNoiSQL.java và frm_LichSu.java.
Lưu ý trạng thái: Kết nối SSMS bằng Windows Authentication đến instance SUGAR và Mixed Mode đã được xác nhận. Kết nối TCP tại 127.0.0.1:14333 từng báo lỗi từ chối kết nối; chưa có bằng chứng trong hội thoại cho thấy bước JDBC/ghi lịch sử SQL đã kiểm thử thành công. Cần xác nhận lại cổng trước khi đánh dấu hoàn thành giai đoạn này.
Giai đoạn 5 - Phân tích mật mã
1. AttackDemos.java: thử 26 khóa Caesar (brute force).
2. Thống kê tần suất xuất hiện của các ký tự A–Z.
3. Tính chuỗi khóa Vigenère từ cặp Plaintext/Ciphertext biết trước.
4. frm_TanCong.java: giao diện thao tác và hiển thị kết quả phân tích.
Giai đoạn 6 - Menu và kiểm thử
1. Tạo frm_Menu.java để mở các form thuật toán, tổng hợp, tấn công và kết nối SQL.
2. Chạy từng form bằng Run As > Java Application.
3. Kiểm thử dữ liệu theo tài liệu LAB 1; kiểm tra Encrypt, Decrypt, khóa lỗi và đọc/ghi tệp.
4. Kiểm tra bảng dbo.history trong SSMS sau khi cấu hình JDBC thành công.
4. Cấu trúc mã nguồn
LAB1_MaHoaCoDien/
├── pom.xml
└── src/main/java/week_01/
    ├── FileTextIO.java
    ├── CaesarCipher.java
    ├── VigenereCipher.java
    ├── RailFenceCipher.java
    ├── PlayfairCipher.java
    ├── TranspositionCipher.java
    ├── AutokeyCipher.java
    ├── frm_Caesar.java
    ├── frm_Vigenere.java
    ├── frm_RailFence.java
    ├── frm_Playfair.java
    ├── frm_Transposition.java
    ├── frm_Autokey.java
    ├── CipherTongHop.java
    ├── frm_TongHop.java
    ├── DbConfig.java
    ├── HistoryDao.java
    ├── frm_KetNoiSQL.java
    ├── frm_LichSu.java
    ├── AttackDemos.java
    ├── frm_TanCong.java
    └── frm_Menu.java
Các tên lớp trên thể hiện cấu trúc mục tiêu của project đầy đủ; hãy đối chiếu với các file thực tế trước khi nộp.
5. Hướng dẫn chạy
1. Mở Eclipse, chọn project LAB1_MaHoaCoDien.
2. Kiểm tra JRE System Library đang dùng JDK 17.
3. Nhấp phải project, chọn Maven > Update Project.
4. Mở src/main/java/week_01/frm_Menu.java, chọn Run As > Java Application để chạy toàn bộ chương trình, sau khi các file đã được thêm đầy đủ.
5. Hoặc chạy riêng frm_Caesar.java, frm_Vigenere.java, ... để kiểm tra từng bài.
6. Với SQL Server, dùng form kết nối và nhập địa chỉ/cổng đang thực sự hoạt động trên instance SUGAR.
6. Bộ dữ liệu kiểm thử và kết quả mong đợi
Bài	Thuật toán	Plaintext	Key	Ciphertext mong đợi
1.1	Caesar	HELLO	3	KHOOR
1.2	Vigenère	HELLO	KEY	RIJVS
1.3	Rail Fence	HELLO WORLD	3	HOREL OLLWD
1.4	Playfair	HELLOWORLD	KEYWORD	GYIZSCOKCFBU
1.5	Transposition	BAOMAT	2,3,1	AAOTBM
1.6	Autokey	HELLO	KEY	RIJSS


Cách kiểm thử: nhập Plaintext và Key, nhấn Encrypt, đối chiếu Ciphertext; sau đó nhấn Decrypt, kiểm tra bản rõ khôi phục. Với Playfair, bản rõ sau giải mã có thể còn ký tự đệm X theo quy tắc chuẩn hóa.
Trường hợp khóa sai
- Caesar: nhập abc thay vì số nguyên.
- Vigenère/Autokey: nhập khóa chứa số, ví dụ K3Y.
- Rail Fence: nhập số hàng không hợp lệ, ví dụ 1.
- Transposition: nhập khóa không phải hoán vị, ví dụ 1,1,2.
Chương trình cần hiển thị thông báo phù hợp thay vì dừng đột ngột.
Kiểm thử phân tích mật mã
Kỹ thuật	Đầu vào	Kết quả mong đợi
Brute Force Caesar	KHOOR	Danh sách 26 khóa có k=3 cho HELLO
Frequency Analysis	PHHW PH DIWHU WKH WRJD SDUWB	Bảng số lần và tỷ lệ xuất hiện các chữ A–Z
Known Plaintext	Plaintext HELLO; Ciphertext RIJVS	Chuỗi khóa KEYKE


Lưu ý: xếp hạng bản rõ bằng tần suất chữ cái trên văn bản rất ngắn có thể không chính xác, dù khóa đúng xuất hiện trong danh sách thử.
Kiểm thử SQL Server
USE LAB1_MaHoaCoDien;
GO
SELECT TOP (20)
    id, created_at, algorithm, operation,
    input_text, result_text
FROM dbo.history
ORDER BY id DESC;
GO
SELECT COUNT(*) AS so_ban_ghi FROM dbo.history;
GO
Nếu database ban đầu trống và thực hiện thành công sáu lần Encrypt có lưu lịch sử, kết quả mong đợi là sáu bản ghi mới. Không coi đây là kết quả thực tế cho đến khi đã chạy và kiểm tra SSMS.
7. Kết quả thực tế đã xác nhận
Hạng mục	Trạng thái
Tạo Maven Project và package week_01	Đã thực hiện
Chuyển project sang JDK 17	Đã thực hiện
Giao diện frm_Caesar mở được trong Eclipse	Đã xác nhận qua ảnh
Giao diện frm_Vigenere mở được trong Eclipse	Đã xác nhận qua ảnh
Kiểm chứng kết quả Encrypt/Decrypt trên máy	Chưa có ảnh kết quả thực tế
Kiểm chứng bốn form thuật toán còn lại	Chưa có kết quả thực tế
Kết nối SSMS đến SQL Server instance SUGAR	Đã xác nhận
Bật Mixed Mode trên instance SUGAR	Đã xác nhận (WindowsOnly=0)
Kết nối JDBC và lưu/đọc lịch sử	Chưa xác nhận
Kiểm thử toàn bộ menu và bài nâng cao	Chưa xác nhận


8. Kết luận
LAB 1 giới thiệu các cơ chế thay thế và hoán vị trong mật mã cổ điển, cách xây dựng chương trình Java Swing và cách tích hợp lưu lịch sử bằng SQL Server. Các bộ dữ liệu ở mục 6 là tiêu chí kiểm thử theo tài liệu; báo cáo hoàn thành cuối cùng cần bổ sung ảnh chụp kết quả chạy thực tế của từng form, màn hình lịch sử SQL và menu chính.
