package org.nwolfub.messengerauth.api.inner;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

public class Communication {
    private static ServerSocket serverSocket;
    public static final int port = 9710;


    private static HashMap<Source, String> connections; //list of all connections established by other services (no-redis). Format: source:reset code

    public static void initialize() throws IOException {
        serverSocket = new ServerSocket(port);
        new Thread(Communication::listen).start();
    }

    private static void talk(Socket socket) throws IOException {
        Scanner in = new Scanner(socket.getInputStream());
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        try {
            out.println("From:");
            out.flush();
            String from = "";
            String input = "";
            boolean authed = false;
            while (socket.isConnected() && !authed) {
                input = in.nextLine();
                try {
                    Source source = Source.valueOf(input);
                } catch (Exception e) {}
            }
        } catch (Exception e) {
            try {
                out.println("Exception " + e + " occurred on auth server side. Goodbye");
                socket.close();
                in.close();
                out.close();
            } catch (Exception ignored) {}
        }
    }

    private static void listen() {
        System.out.println("Started local listening on port " + port);
        while (true) {
            try {
                Socket s = serverSocket.accept();

            } catch (Exception e) {
                System.out.println("----------UNFATAL EXCEPTION----------");
                e.printStackTrace();
                System.out.println("------------END OF REPORT------------");
            }
        }
    }

    public enum Source {
        users,
        messages
    }
}
