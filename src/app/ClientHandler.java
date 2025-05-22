import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
    private final String clientName;
    private final String connectionTime;
    private final Socket clientSocket;

    public ClientHandler(String clientName, String connectionTime, Socket clientSocket) {
        this.clientName = clientName;
        this.connectionTime = connectionTime;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        ) {
            writer.write("Ви підключені як " + clientName + "\n");
            writer.flush();

            String message;
            while ((message = reader.readLine()) != null) {
                System.out.println("[" + clientName + "] " + message);
                if ("exit".equalsIgnoreCase(message.trim())) {
                    writer.write("Відключення...\n");
                    writer.flush();
                    break;
                } else {
                    writer.write("Команда \"" + message + "\" не розпізнана\n");
                    writer.flush();

                }

            }

        } catch (IOException e) {
            System.out.println("[SERVER] Помилка з'єднання з " + clientName);
        } finally {
            try {
                clientSocket.close();
            } catch (IOException ignored) {}
            Server.removeClient(clientName);

        }

    }

}