package com.example.lib_server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 * User: laomao
 * Date: 2020/4/27
 * Time: 11:20 AM
 */
public class TCPServerService extends Service {

    private boolean mIsServiceDestoryed = false;
    private String[] mDefinedMessages = new String[]{"你好啊", "今天天气不错", "请问你叫什么名字？"};

    @Override
    public void onCreate() {
        new Thread(new TcpServer()).start();
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        mIsServiceDestoryed = true;
        super.onDestroy();
    }

    private class TcpServer implements Runnable {

        @Override
        public void run() {
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(8688);
            } catch (IOException e) {
                e.printStackTrace();
            }

            while (!mIsServiceDestoryed) {
                try {
                    final Socket client = serverSocket.accept();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                responseClient(client);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void responseClient(Socket client) throws IOException {
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
            out.println("欢迎来到聊天室");

            while (!mIsServiceDestoryed) {
                String str = in.readLine();
                System.out.println("msg from client:" + str);

                if (str == null)
                    break;

                String msg = mDefinedMessages[new Random().nextInt(mDefinedMessages.length)];
                out.println(msg);
                System.out.println("send:" + msg);
            }

            System.out.println("client quit...");

            out.close();
            in.close();
            client.close();
        }
    }
}
