import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

public class Server {
    private static int clientCount = 0;
    private static final Map<String, ClientHandler> activeClients = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        int port = 1234;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("[SERVER] Сервер запущено на порту " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                clientCount++;
                String clientName = "client-" + clientCount;
                String connectionTime = new SimpleDateFormat("HH:mm:ss").format(new Date());

                ClientHandler handler = new ClientHandler(clientName, connectionTime, clientSocket);
                activeClients.put(clientName, handler);

                System.out.println("[SERVER] " + clientName + " успішно підключився о " + connectionTime);

                new Thread(handler).start();

            }

        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    public static void removeClient(String clientName) {
        activeClients.remove(clientName);
        System.out.println("[SERVER] " + clientName + " відключено");

    }

}