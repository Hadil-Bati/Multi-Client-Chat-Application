import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;


public class ServerThreads extends Thread{

    private final Socket clientSocket;
    private ArrayList<ServerThreads> threadList;
    public PrintWriter output;
    private final ArrayList IDArrayList;
    private final String readID;

    public ServerThreads(Socket clientSocket, ArrayList<ServerThreads> thread, ArrayList IDArrayList, String readID) {

        this.clientSocket = clientSocket;
        this.threadList = thread;
        this.IDArrayList = IDArrayList;
        this.readID = readID;
    }

    @Override
    public void run() {
        try {

            //Reading the input from Client
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            //returning the output to the client : true statement is to flush the buffer otherwise
            //we have to do it manually
            output = new PrintWriter(clientSocket.getOutputStream(), true);

            // This is the Coordinator code. The coordinator is assigned by taking the client at index 0 in our IDArrayList
            // and assigning them to be the coordinator.
            // Every time a new Client connects, they will be informed who the coordinator is.
            Object coord = IDArrayList.get(0);
            //Object firstcoord = coord;
            PrintWriter FirstCoordOut = new PrintWriter(new BufferedOutputStream(clientSocket.getOutputStream()), true);
            FirstCoordOut.println("The Coordinator is:" + coord);

            //if the client connects to the Server, then print to all connected users
            //that the newly conencted user is online
            if (clientSocket.isConnected()) {
                printToALlClients(readID + " is online");
            }



            while(true) {
                Object constantcoord = IDArrayList.get(0);
                // The variable 'outputString' allows continuous reading of input from the Client so that we can assess
                // their input into the chat client.
                String outputString = input.readLine();
                //A timestamp created using SimpleDateFormat to record the messages that are
                //sent between members of the Server.
                String time_pattern = "yyyy-MM-dd HH:mm:ss";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(time_pattern);
                String date = simpleDateFormat.format(new Date());

                // Fault Tolerance of abnormal termination (Ctrl+C command) has been resolved here.
                // If the Client has abnormally logged out of the chat service, for example pressing 'X' on the Command
                // Line Interface. The Client input will be received as 'null' and our 'if' statement will execute.
                // If the Client who abnormally terminated is the coordinator, we remove them from our ArrayList of active clients,
                // we inform all the active members that the Client has left and to update their lists, we assign the new
                // coordinator by retrieving the new Client at index 0 in our Client ArrayList, and finally we inform all
                // active members who the new coordinator is.
                // Otherwise if the Client abnormally terminated and is not the coordinator, we remove them from our active
                // Client ArrayList, and inform all the active members that they have left and to update their list of
                // active members.
                if(outputString == null){
                    if (readID.equals(constantcoord)){
                        IDArrayList.remove(readID);
                        System.out.println(IDArrayList);
                        printToALlClients(readID + " has left ");
                        printToALlClients("Please update your members list!");
                        Object newcoord = IDArrayList.get(0);
                        PrintWriter CoordOut = new PrintWriter(new BufferedOutputStream(clientSocket.getOutputStream()), true);
                        printToALlClients("The New Coordinator is:" + newcoord);
                        break;
                    }
                  else {
                        IDArrayList.remove(readID);
                        System.out.println(IDArrayList);
                        printToALlClients(readID + " has left ");
                        printToALlClients("Please update your members list!");
                        break;
                    }
                }

                // Normal termination of the Client is executed here. If the Client inputs 'exit' into the Command Line
                // Interface, we receive that message as them wanting to terminate their session.
                // We check if the Client who wants to terminate is the coordinator, if so, we remove them from our ArrayList of active clients,
                // we inform all the active members that the Client has left and to update their lists, we assign the new
                // coordinator by retrieving the new Client at index 0 in our Client ArrayList, and finally we inform all
                // active members who the new coordinator is.
                // Otherwise if the Client abnormally terminated and is not the coordinator, we remove them from our active
                // Client ArrayList, and inform all the active members that they have left and to update their list of
                // active members.
                else if (outputString.equals("exit") && readID.equals(constantcoord)) {
                    IDArrayList.remove(readID);
                    System.out.println(IDArrayList);
                    printToALlClients(readID + " has left ");
                    printToALlClients("Please update your members list!");
                    Object newcoord = IDArrayList.get(0);
                    PrintWriter CoordOut = new PrintWriter(new BufferedOutputStream(clientSocket.getOutputStream()), true);
                    printToALlClients("The New Coordinator is:" + newcoord);
                    break;
                } else if (outputString.equals("exit") ){
                    IDArrayList.remove(readID);
                    System.out.println(IDArrayList);
                    printToALlClients(readID + " has left ");
                    printToALlClients("Please update your members list!");
                    break;

                }

                // We called the method `PrintToAllClients` into the while loop
                //followed by the reading of any other input from the Client as a message to the other active Clients.
                printToALlClients(outputString);

                //For group state maintenance, we had the messages receieved by the Server be
                //printed onto the console.
                System.out.println("Server received " + outputString);

                //Calling the timestamp created within the PrintToAllClients method
                //so that the messages are recorded and outputted with timestamp on
                //the Server console.
                System.out.println(outputString + "\n" + date);



            }


        } catch ( IOException e) {
            e.printStackTrace();
            System.out.println("Error occured " +e.getStackTrace());


        }

    }
    //We created a method named PrintToAllClients which acts as a broadcasting function, sending all
    //outputs to the clients that are within the threadList. It therefore outputs the readID of the users,
    //followed by a colon and the string outputString.
    private void printToALlClients(String outputString) {
        for (ServerThreads sT: threadList) {
            sT.output.println(readID + " : " + outputString);



        }
    }
}





