import org.junit.Before;
import org.junit.Test;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerThreadsTest {

    private ServerThreads serverThreadsUnderTest;
    private Object Socket;
    private Object ArrayList;
    private Object String;

    @Before
    public void setUp() throws Exception {
        //This code will return successful as 'exit' input from the user indicates normal termination
        ServerThreads serverThreadsUnderTest = new ServerThreads(Socket, ArrayList, ArrayList, String);
        String outputString = "exit";
        String actualResult = "exit";
        String expectedResult = ServerThreads.run();
        assertEquals(expectedResult, actualResult);



    }

    @Test
    public void testRun() {
        // Setup

        // Run the test
        serverThreadsUnderTest.run();

        // Verify the results
    }
}
