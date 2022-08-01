import java.io.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.Buffer;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.System.in;

public class Client {
    public static void main(String[] args) throws IOException {
        try {
            //code to connect the client's socket with the server socket
            System.out.println("Client Started");
            Socket clientSocket = new Socket("localhost", 7000);

            //read message from the server
            BufferedReader input = new BufferedReader(new InputStreamReader(in));


            // read the message the server has sent and display on the screen
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            //returning the output to the server : true statement is to flush the buffer otherwise
            //we have to do it manually
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

            // will read the message from the socket's input stream
            System.out.println(in.readLine());

            //taking the user input
            Scanner scanner = new Scanner(System.in);
            String userInput = null;
            String response;
            String ID = "empty";
            String IP = "empty";
            String Port = "empty";


            ClientThreads clientThreads = new ClientThreads(clientSocket);

            new Thread(clientThreads).start();
            //loop closes when user enters exit command

            do {
                // The client is expected to input its ID, Port and IP.
                // It then gets sent to the server that reads it
                // The user ID is then stored in the IDArrayList.
                if (ID.equals("empty")) {
                    System.out.println("Enter your name ");
                    userInput = scanner.nextLine();
                    ID = userInput;
                    output.println(userInput);
                }


                if (Port.equals("empty")) {
                    System.out.println("Enter your Port ");
                    userInput = scanner.nextLine();
                    Port = userInput;
                    output.print(userInput);
                }

                if (IP.equals("empty")) {
                    System.out.println("Enter your IP");
                    userInput = scanner.nextLine();
                    IP = userInput;
                    output.print(userInput);
                }

                if (userInput.equals("exit")) {
                    break;
                } else {
                    String message = ("(" + ID + ")" + " message : ");
                    System.out.println(message);
                    userInput = scanner.nextLine();
                    output.println(message + " " + in);
                    if (in.equals("exit")) {
                        //reading the input from server
                        break;

                    }
                }
            } while (!userInput.equals("exit"));


            //The initialisation of a Concurrent Hashmap is thread-safe. This means that it
            //allows for multiple threads to operate on a single object without the complications.
            //This is essential, as the activeClients (users connected) each have their own started
            //thread which needs to be working at the same time for the communication to start.

            ConcurrentHashMap<String, Socket> activeClients = new ConcurrentHashMap<String, Socket>();

            activeClients.put(clientSocket.getInetAddress().getHostAddress(), clientSocket);

            for (String clientHost : activeClients.keySet()) {


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
