
## Quá trình thực hiện

Bài LAB 3 được thực hiện trên máy ảo Windows 11 bằng VMware Workstation. Máy ảo được cấu hình mạng Host-only và tạo snapshot sạch trước khi thực hành. Các công cụ được sử dụng gồm Microsoft Defender, PowerShell, Sysmon, Autoruns, Process Explorer, Wireshark và Python.

Quá trình thực hiện gồm các nội dung chính:

1. Thu thập trạng thái baseline của hệ điều hành, Microsoft Defender, Windows Firewall, cấu hình mạng và các tiến trình đang chạy.
2. Xây dựng bảng đánh giá rủi ro theo chuỗi: tài sản → lỗ hổng → mối đe dọa → rủi ro → biện pháp kiểm soát.
3. Sử dụng tệp kiểm thử EICAR để kiểm tra khả năng phát hiện và cách ly của Microsoft Defender.
4. Tạo tài khoản thử nghiệm `lab3user`, sinh các lần đăng nhập đúng và sai, sau đó phân tích Event ID 4624, 4625 và 4648.
5. Cài đặt Sysmon và sử dụng Autoruns để phát hiện Run value, Scheduled Task và các dấu hiệu persistence được tạo riêng cho bài LAB.
6. Chạy HTTP server trên `127.0.0.1:8080`, xác định cổng lắng nghe, PID và tiến trình tương ứng bằng PowerShell và Process Explorer.
7. Sử dụng Wireshark để so sánh lưu lượng HTTP không mã hóa với lưu lượng HTTPS/TLS.
8. Chạy thử nghiệm tải cục bộ giới hạn trên localhost và phân tích dữ liệu DDoS, mail bombing ở chế độ offline.
9. Phân tích mẫu email phishing và phân loại các tình huống Social Engineering.
10. Xóa tài khoản, Run value, Scheduled Task và HTTP listener được tạo trong LAB; sau đó kiểm tra lại trạng thái Microsoft Defender.
11. Tính SHA-256 cho các tệp bằng chứng nhằm hỗ trợ kiểm tra tính toàn vẹn.

Toàn bộ thao tác gây tải chỉ được thực hiện trên địa chỉ `127.0.0.1`. Không thực hiện DDoS, mail bombing, spoofing hoặc Man-in-the-Middle chủ động trên mạng bên ngoài.

## Kết quả đạt được

Sau khi hoàn thành bài LAB, các kết quả chính đạt được gồm:

* Microsoft Defender duy trì trạng thái bảo vệ thời gian thực và phát hiện được chuỗi kiểm thử EICAR.
* Thu thập được các sự kiện xác thực của tài khoản `lab3user`, bao gồm đăng nhập thành công, đăng nhập thất bại và sử dụng thông tin xác thực rõ ràng.
* Mật khẩu cũ không còn sử dụng được sau khi thực hiện credential rotation; mật khẩu mới đăng nhập thành công.
* Sysmon ghi nhận được sự kiện tạo tiến trình và các thay đổi liên quan đến persistence.
* Autoruns phát hiện được mục tự khởi động `LAB3_Run_Demo`.
* Scheduled Task `LAB3_Persistence_Demo` được tạo và ghi kết quả vào tệp bằng chứng.
* Process Explorer xác định đúng tiến trình `python.exe` sở hữu cổng `127.0.0.1:8080`.
* Wireshark đọc được chuỗi `TRAINING_ONLY` trong HTTP Request URI, trong khi nội dung ứng dụng của kết nối HTTPS không thể đọc trực tiếp.
* Kết quả local load test: `[requests]` request, `[workers]` worker, `[ok]` thành công, `[failures]` thất bại, thời gian `[elapsed_s]` giây và độ trễ trung bình `[avg_latency_s]` giây.
* Dataset DDoS cho thấy lưu lượng đến từ nhiều địa chỉ nguồn, chứng minh việc chặn một địa chỉ IP đơn lẻ không đủ để xử lý DDoS.
* Log mail bombing giúp xác định người gửi có số lượng thư bất thường, tổng dung lượng và dung lượng trung bình của email.
* Mẫu phishing chứa các dấu hiệu như tạo cảm giác khẩn cấp, giả mạo uy tín, domain cần xác minh, Reply-To khác From và yêu cầu cung cấp thông tin xác thực.
* Sau cleanup, không còn `LAB3_Run_Demo`, `LAB3_Persistence_Demo`, tài khoản `lab3user` hoặc listener trên cổng 8080.
* Microsoft Defender và các cơ chế bảo vệ vẫn hoạt động sau khi khôi phục.
* Các tệp bằng chứng đã được tính SHA-256 và lưu trong `evidence_sha256.csv`.

## Đánh giá kết quả

| Tình huống                           | Kết quả |
| ------------------------------------ | ------- |
| Baseline hệ thống                    | PASS    |
| TH1 – Risk Register                  | PASS    |
| TH2 – EICAR và Microsoft Defender    | PASS    |
| TH3 – Xác thực và keylogging         | PASS    |
| TH4 – Persistence và listener        | PASS    |
| TH5 – HTTP/HTTPS                     | PASS    |
| TH6 – DoS, DDoS và Mail Bombing      | PASS    |
| TH7 – Social Engineering và Phishing | PASS    |
| Cleanup và kiểm tra phục hồi         | PASS    |

## Kết luận

Bài LAB giúp hiểu rõ mối quan hệ giữa tài sản, lỗ hổng, mối đe dọa, rủi ro và tấn công. Qua các tình huống thực hành, người thực hiện đã biết cách thu thập và đối chiếu bằng chứng từ endpoint, Event Log và lưu lượng mạng; đồng thời áp dụng quy trình baseline, quan sát, phát hiện, cô lập, phục hồi và kiểm tra lại hệ thống.
