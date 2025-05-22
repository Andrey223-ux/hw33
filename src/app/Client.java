import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 1234;

        try (
                Socket socket = new Socket(host, port);
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                Scanner scanner = new Scanner(System.in);
        ) {
            System.out.println(reader.readLine()); // привітання з серверу

            String input;
            while (true) {
                System.out.print("Введіть команду: ");
                input = scanner.nextLine();
                writer.write(input + "\n");
                writer.flush();

                String response = reader.readLine();
                System.out.println("Сервер: " + response);

                if ("exit".equalsIgnoreCase(input.trim())) {
                    break;

                }

            }

        } catch (IOException e) {
            System.out.println("Помилка з'єднання з сервером: " + e.getMessage());

        }

    }

}