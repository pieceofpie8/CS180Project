import org.junit.Test;

import org.junit.After;
import org.junit.Before;
import java.io.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class MediaDatabaseTestCase {
    public static class TestCase {
        private final PrintStream originalOutput = System.out;
        private final InputStream originalSysin = System.in;

        private ArrayList<Account> accounts;
        private String accountsSaveFile;
        private String directMessageFileNamesFile;
        private ArrayList<String> directMessageFiles;

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

        // The files I used are the example inputs taken from the google doc.

        @Test public void testMediaDatabaseConstructor() {
            MediaDatabase media = new MediaDatabase("inputAccountSaveFile.txt", "inputDirectMessageFile.txt");
            assertNotNull(accountsSaveFile);
            assertNotNull(directMessageFileNamesFile);
            assertEquals(true, media.readAccountsSave());
            assertEquals(true, media.outputAccountsSave());
        }

        @Test public void testReadAccountsSave() {
            MediaDatabase media = new MediaDatabase("inputAccountSaveFile.txt", "inputDirectMessageFile.txt");
            assertEquals(true, media.readAccountsSave());
            assertNotNull(accounts);
            ArrayList<String> testAccounts =  new ArrayList<String>();
            testAccounts.add("John");
            testAccounts.add("Alice");
            testAccounts.add("Rand");
            testAccounts.add("Amy");
            assertEquals(testAccounts, accounts);
        }

        @Test public void testOutputAccountsSave() {
            MediaDatabase media = new MediaDatabase("inoutAccountSaveFile.txt", "inputDirectMessageFile.txt");
            assertEquals(true, media.outputAccountsSave());
        }

        @Test public void testReadDirectMessagesNames() {
            MediaDatabase media = new MediaDatabase("inputAccountSaveFile.txt", "inputDirectMessageFile.txt");
            ArrayList<String> test = new ArrayList();
            ArrayList<String> messageNames = new ArrayList();
            test = media.readDirectMessagesNames();
            assertNotNull(test);
            messageNames.add("Alice:John");
            messageNames.add("Amy:Rand");
            assertEquals(messageNames, test);
        }


        // Will complete rest as the class is implemented.
    }

}
