Bùi Hải Đường 1150080012 11_ĐH_CNTT1
# LAB 4 – Khảo sát mạng bằng Nmap

## Môi trường thực hành

Bài lab sử dụng Windows 10/11 làm máy thật, Kali Linux làm máy quét và Metasploitable 2 làm máy đích. Hai máy ảo kết nối qua mạng VirtualBox Host-Only. Metasploitable 2 là máy đích được tạo cho mục đích thực hành trong mạng ảo.

| Thành phần | Vai trò | IP thực tế |
|---|---|---|
| Windows máy thật | Quản lý môi trường ảo | [IP Host-Only của Windows] |
| Kali Linux | Chạy Nmap | [IP Kali] |
| Metasploitable 2 | Máy đích | [IP Metasploitable 2] |

## Quá trình thực hiện

1. Cấu hình Kali Linux và Metasploitable 2 cùng mạng Host-Only; kiểm tra IP và kết nối giữa hai máy.
2. Kiểm tra hoặc cài Nmap trên Kali Linux, ghi lại phiên bản công cụ.
3. Thực hiện host discovery bằng `nmap -sn` để xác định các máy đang hoạt động.
4. Quét máy đích bằng các kỹ thuật TCP Connect, SYN, FIN, Xmas, NULL và ACK; so sánh trạng thái cổng giữa các kỹ thuật.
5. Quét một nhóm cổng UDP phổ biến, nhận diện dịch vụ bằng `-sV` và nhận diện hệ điều hành bằng `-O` hoặc `-A`.
6. Chạy NSE script theo hướng dẫn để thu thập thông tin dịch vụ và kiểm tra dấu hiệu rủi ro. Lưu kết quả quét dưới dạng văn bản, XML và grepable.
7. Thực hiện một thay đổi cấu hình phòng thủ hợp pháp, quét lại bằng cùng câu lệnh và so sánh kết quả trước/sau.

## Kết quả đạt được

Nmap phát hiện **[số host] host đang hoạt động** trong dải mạng Host-Only, bao gồm Kali Linux tại `[IP Kali]` và Metasploitable 2 tại `[IP Metasploitable 2]`.

Lần quét TCP ghi nhận **[số cổng] cổng `open`**, tiêu biểu là **[cổng và dịch vụ thực tế]**. Kết quả `-sV` nhận diện **[tên và phiên bản dịch vụ thực tế]**. Các trạng thái `closed`, `filtered` và `open|filtered` được ghi nhận, giải thích dựa trên phản hồi thực tế của từng kỹ thuật quét.

NSE script **[tên script]** trả về **[kết quả thực tế]**. Sau khi thay đổi cấu hình phòng thủ, cổng/dịch vụ **[tên cổng hoặc dịch vụ]** thay đổi từ **[trạng thái trước]** sang **[trạng thái sau]**. Các lệnh, ảnh màn hình và tệp kết quả được lưu để đối chiếu trong báo cáo.
