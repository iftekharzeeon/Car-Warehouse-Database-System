package sample;

import netUtil.NetworkUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class Server {
    public static void main(String[] args) {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(9992);
            if (!Datasource.getInstance().open()) {
                System.out.println("Failed to start the Database, server is closing.");
                return;
            }

            while (true) {
                Socket socket = serverSocket.accept();
                NetworkUtil networkUtil = new NetworkUtil(socket);
                new ReadServer(networkUtil);
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}
