package zxp.diytomcat;

import cn.hutool.core.util.NetUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Bootstrap {
    public static void main(String[] args) {
        try {
            int port = 7777;

            if (!NetUtil.isUsableLocalPort(port)) {
                System.out.println(port + "端口已经被占用");
                return;
            }
            ServerSocket serverSocket = new ServerSocket(port);

            while (true) {
                Socket socket = serverSocket.accept();
                InputStream inputStream = socket.getInputStream();
                int bufferSize = 1024;
                byte[] buffer = new byte[bufferSize];
                inputStream.read(buffer);
                String requestString = new String(buffer, "utf-8");
                System.out.println("浏览器输入信息：\r\n" + requestString);

                OutputStream outputStream = socket.getOutputStream();
                String responseHead = "HTTP/1.1 200 OK\r\n" + "Content-Type: text/html;charset=utf-8\r\n\r\n";
                String responseString = "Hello DIY Tomcat from how2j.cn";
                responseString = responseHead + responseString;
                outputStream.write(responseString.getBytes());
                outputStream.flush();
                socket.close();

            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
