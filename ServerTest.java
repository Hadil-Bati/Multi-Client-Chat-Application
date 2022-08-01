import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;


class ServerTest {

	@Test
	void test_inputs() {

	    // This test of Port Number will be rejected
		Server servertest = new Server();
        String TestPort = "8000";
        String actualResult = TestPort;
        String expectedResult = servertest.main();
        assertEquals(expectedResult, actualResult);

        // This test of IP address will be rejected
        String TestIP = "127.0.0.3";
        String actualResult1 = TestIP;
        String expectedResult1 = servertest.main();
        assertEquals(expectedResult1, actualResult1);

	}

	
}
