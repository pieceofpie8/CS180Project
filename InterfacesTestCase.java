import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

/**
 * CS 180 Group Project - InterfacesTestCase
 *
 * Defines InterfacesTestCase, which calls InterfacesTestMain and compares the output to an
 * expected output. Basically just tests to make sure interfaces are implemented correctly.
 *
 * IMPORTANT: must have an instance of SMServerFin running for the test case to work!!!
 */

public class InterfacesTestCase {

    private final PrintStream originalOutput = System.out;
    private final InputStream originalSysin = System.in;

    @SuppressWarnings("FieldCanBeLocal")
    private ByteArrayInputStream testIn;

    @SuppressWarnings("FieldCanBeLocal")
    private ByteArrayOutputStream testOut;

    @Before
    public void outputStart() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    @After
    public void restoreInputAndOutput() {
        System.setIn(originalSysin);
        System.setOut(originalOutput);
    }

    private String getOutput() {
        return testOut.toString();
    }

    @SuppressWarnings("SameParameterValue")
    private void receiveInput(String str) {
        testIn = new ByteArrayInputStream(str.getBytes());
        System.setIn(testIn);
    }

    @Test(timeout = 1000)
    public void testInterfaces() {
        String input = "";
        String expected = "Account correctly implements AccountInterface\n" +
                "MediaDatabase correctly implements MediaDatabaseInterface\n" +
                "SMServerFin correctly implements SMServerFinInterface\n" +
                "SMClientGUI correctly implements SMClientGUIInterface\n";

        receiveInput(input);
        InterfacesTestMain.main(new String[0]);

        String actual = getOutput();
        actual = actual.replace("\r\n", "\n");

        Assert.assertEquals("Verify that the output matches!", expected, actual);
    }
}
