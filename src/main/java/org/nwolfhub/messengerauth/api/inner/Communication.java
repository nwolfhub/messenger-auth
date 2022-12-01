package org.nwolfhub.messengerauth.api.inner;

import org.nwolfhub.shared.DataUnit;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Communication {
    private static ServerSocket serverSocket;
    public static final int port = 9710;


    private static HashMap<Source, String> connections; //list of all connections established by other services (no-redis). Format: source:reset code

    public static void initialize() throws IOException {
        serverSocket = new ServerSocket(port);
        new Thread(Communication::listen).start();
    }

    private static void talk(Socket socket) throws IOException {
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        try {
            out.writeObject(new DataUnit().setRequest("From:"));
            out.flush();
            String from = "";
            DataUnit input = new DataUnit();
            boolean authed = false;
            while (socket.isConnected() && !authed) {
                input = (DataUnit) in.readObject(); //finish later
            }
        } catch (Exception e) {
            try {
                out.writeObject(new DataUnit().setRequest("Exception " + e + " occurred on auth server side. Goodbye"));
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
