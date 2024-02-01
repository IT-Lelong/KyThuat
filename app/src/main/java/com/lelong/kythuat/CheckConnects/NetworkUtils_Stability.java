package com.lelong.kythuat.CheckConnects;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class NetworkUtils_Stability {
    // Phương thức để kiểm tra độ ổn định của kết nối bằng cách ping một trang web cụ thể
    public static String checkStability(String g_des) {
        String result = "";
        String website = g_des; // Trang web để ping, bạn có thể thay đổi tùy ý
        try {
            // Tạo một ProcessBuilder để thực hiện lệnh ping
            ProcessBuilder processBuilder = new ProcessBuilder("ping", "-c", "5", website);
            Process process = processBuilder.start();

            // Đọc dữ liệu đầu ra từ quá trình ping
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }

            // Chờ quá trình ping hoàn thành và lấy mã trả về
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                // Nếu ping thành công, trả về kết quả
                result = "Ổn định";
            } else {
                // Nếu ping không thành công, trả về thông báo lỗi
                result = "Không ổn định";
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            result = "Không thể kiểm tra";
        }
        return result;
    }
}
