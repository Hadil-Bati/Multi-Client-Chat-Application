import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.ArrayList;


public class Server {
    private static String StringUtils;
    private Scanner scanner;
    private StringBuilder buffer;
    private boolean reading;
    private Thread t;

    public static void main(String[] args) {
        //TODO auto generated method sub

        ArrayList<ServerThreads> threadList = new ArrayList<>();
        try {
            // code to connect the server socket with the client socket
            // InetAddress allows us to create the local IP Address that our socket uses
            // ServerSocket contains the parameters of port, backlog, and address
            // port is the local port number of our server that is used by our clients to connect
            // backlog is the maximum amount of queued connections that are allowed simultaneously
            // address is our local IP that we have made for our Server
            InetAddress address = InetAddress.getByName("127.0.0.1");
            ServerSocket serverSocket = new ServerSocket(7000, 100, address);

            // Initialise an ArrayList to store all our Client ID's.
            // The IDArrayList is used to ensure no duplicate ID's are made, this is to ensure the uniqueness of each
            // Client connecting. The ArrayList is vital in choosing who the coordinator is and is used in our
            // coordinator code.
            ArrayList IDArrayList = new ArrayList();

            while (true) {
                //This will be displayed on the Server until the connection is established
                System.out.print("Waiting for client...");
                Socket clientSocket = serverSocket.accept();


                // The PrintWriter 'IDout' allows us to ask the specified client to enter their preferred ID
                // The Buffered 'ID' allows us the retrieve input that the client has put for their ID.
                // The 'readID' reads the input that the client has given from the BufferedReader 'ID'
                // and puts it into the 'readID' variable.
                PrintWriter IDout = new PrintWriter(new BufferedOutputStream(clientSocket.getOutputStream()), true);
                IDout.println("Enter your ID: ");
                BufferedReader ID = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                clientSocket.getInputStream();
                String readID = ID.readLine();

                // This while loop ensures that the ID the client has inputted has not already been used.
                // It checks the input from the client against the ID's in 'IDArrayList'. If there are duplicates,
                // we ask the client to input another ID. Otherwise we allow their preferred ID and add it to our IDArrayList.
                while(IDArrayList.contains(readID)) {
                    IDout.println("ID has already been used. enter another one: ");
                    clientSocket.getInputStream();
                    readID = ID.readLine();
                }
                System.out.println(readID);
                IDArrayList.add(readID);
                System.out.println(IDArrayList);

                // The PrintWriter 'PortOut' allows us to ask the specified client to enter the port number of the server
                // they wish to connect to.
                // The Buffered 'Port' allows us the retrieve input that the client has put for the Port.
                // The 'readPort' reads the input that the client has given from the BufferedReader 'Port'
                // and puts it into the 'readPort' variable.
                PrintWriter PortOut = new PrintWriter(new BufferedOutputStream(clientSocket.getOutputStream()), true);
                PortOut.println("Enter Port Number:");
                BufferedReader Port = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                clientSocket.getInputStream();
                String readPort = Port.readLine();

                // To ensure that they input the specified Port number of the server, we continuously check the user input
                // for the correct Port number.
                // If the wrong Port number is inputted by the Client, we inform them that they have inputted the wrong
                // Port number and ask them to try again until it is correct.
                String PortNumber = "7000";
                while(!readPort.equals(PortNumber)){
                    PortOut.println("Wrong Port Number inputted. Try Again: ");
                    clientSocket.getInputStream();
                    readPort = Port.readLine();
                }
                System.out.println(readPort);

                // The PrintWriter 'IPOut' allows us to ask the specified client to enter the IP address of the server
                // they wish to connect to.
                // The Buffered 'IPaddress' allows us the retrieve input that the client has put for the IP Address.
                // The 'readIP' reads the input that the client inputted from BufferedReader 'IPaddress'
                // and puts it into 'readIP' variable.
                PrintWriter IPOut = new PrintWriter(new BufferedOutputStream(clientSocket.getOutputStream()), true);
                IPOut.println("Enter IP address:");
                BufferedReader IPaddress = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                clientSocket.getInputStream();
                String readIP = IPaddress.readLine();

                // To ensure that they input the specified IP Address of the server, we continuously check the user input
                // for the correct IP Address.
                // If the wrong IP Address is inputted by the Client, we inform them that they have inputted the wrong
                // address and ask them to try again until it is correct.
                while(!readIP.equals("127.0.0.1")){
                    IPOut.println("Wrong IP address inputted. Try Again: ");
                    clientSocket.getInputStream();
                    readIP = IPaddress.readLine();
                }
                System.out.println(readIP);


                //Print a line Statement which infers on which client just connected.
                //This is displayed on the Server
                System.out.println("Connection Established from " + IDArrayList);


                //We initialised the ServerThreads within the Server class, for it to be extended
                //as an inherited class. We included the clientSocket, threadList, IDArrayList and the
                //readID which are all then instantiated within ServerThreads class for implementation
                //in other functions. The threadList essentially will add all the client threads within
                //the threadList. It then starts the ServerThreads.
                ServerThreads threads = new ServerThreads(clientSocket, threadList, IDArrayList, readID);
                threadList.add(threads);
                threads.start();


            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
}
