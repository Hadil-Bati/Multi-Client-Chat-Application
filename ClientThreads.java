import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientThreads implements Runnable{

    private Socket socket;
    private BufferedReader input;


    public ClientThreads(Socket clientSocket) throws IOException {
        this.socket = clientSocket;
        //We are using the input BufferedReader to get information from the Client.
        this.input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    @Override
    public void run() {

        try {
            while(true) {
                //Within this while loop, the ClientThreads class is listening to all responses from the server,
                //whilst still reading from the Scanner.
                String response = input.readLine();
                System.out.println(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

