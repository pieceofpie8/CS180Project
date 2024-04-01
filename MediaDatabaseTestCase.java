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
            ArrayList<Account> testAccounts =  new ArrayList<Account>();
            Account friend1 = new Account("John,Password123:Alice:Rand");
            Account friend2 = new Account("Alice,newPassword8:John,Amy:");
            Account friend3 = new Account("Rand,somethingHere:Amy,Tom:");
            Account friend4 = new Account("Amy,outOfIdeas:Alice,Rand:Tom,John");
            testAccounts.add(friend1);
            testAccounts.add(friend2);
            testAccounts.add(friend3);
            testAccounts.add(friend4);
            assertEquals(testAccounts, accounts);
        }

        @Test public void testOutputAccountsSave() {
            MediaDatabase media = new MediaDatabase("inoutAccountSaveFile.txt", "inputDirectMessageFile.txt");
            assertEquals(true, media.outputAccountsSave());
            ArrayList<String> actual = new ArrayList<>();
            actual.add("John,Password123:Alice:Rand");
            actual.add("Alice,newPassword8:John,Amy:");
            actual.add("Rand,somethingHere:Amy,Tom:");
            actual.add("Amy,outOfIdeas:Alice,Rand:Tom,John");
            ArrayList<String> written = new ArrayList<>();
            try {
                BufferedReader reader = new BufferedReader(new FileReader(accountsSaveFile));
                String line;
                while ((line = reader.readLine()) != null) {
                    written.add(line);
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
                fail();
            }
            assertEquals(actual, written);
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

        @Test public void testOutputDirectMessages() {
            MediaDatabase media = new MediaDatabase("inputAccountSaveFile.txt", "inputDirectMessageFile.txt");
            assertEquals(true, media.outputDirectMessages(directMessageFiles, "inputDirectMessageFile.txt"));
            ArrayList<String> readMessagesName = new ArrayList<>();
            readMessagesName = media.readDirectMessagesNames();
            ArrayList<String> written = new ArrayList<>();
            try {
                BufferedReader reader = new BufferedReader(new FileReader(directMessageFileNamesFile));
                String line;
                while ((line = reader.readLine()) != null) {
                    written.add(line);
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
                fail();
            }
            assertEquals(readMessagesName, written);

        }

        @Test public void testAddMessage() throws InvalidTargetException {
            MediaDatabase media = new MediaDatabase("inputAccountSaveFile.txt", "inputDirectMessageFile.txt");
            ArrayList<String> messages = new ArrayList<>();
            ArrayList<String> addedMessage = new ArrayList<>();
            Account friend1 = new Account("Alice,newPassword8,true:John,Amy:");
            Account friend2 = new Account("Amy,outOfIdeas,true:Alice,Rand:Tom,John");
            messages.add("(0) Direct Messages Started!");
            messages.add("(1) Alice: Hi!");
            messages.add("(2) Amy: Hello!");
            try {
                addedMessage = media.addMessage(messages, friend2, friend1, "How have you been?");
            } catch (InvalidTargetException e) {
                e.printStackTrace();
                fail();
            }
            messages.add("(3) Alice: How have you been?");
            assertEquals(messages, addedMessage);
        }

        @Test public void testRemoveMessage() {
            MediaDatabase media = new MediaDatabase("inputAccountSaveFile.txt", "inputDirectMessageFile.txt");
            ArrayList<String> messages = new ArrayList<>();
            ArrayList<String> removeMessage = new ArrayList<>();
            Account friend1 = new Account("Alice,newPassword8,true:John,Amy:");
            Account friend2 = new Account("Amy,outOfIdeas,true:Alice,Rand:Tom,John");
            messages.add("(0) Direct Messages Started!");
            messages.add("(1) Alice: Hi!");
            messages.add("(2) Amy: Hello!");
            messages.add("(3) Alice: Hwo are you?");
            try {
                removeMessage = media.removeMessage(messages, friend1, friend2, 3);
            } catch (InvalidTargetException e) {
                e.printStackTrace();
                fail();
            }
            messages.remove(3);
            assertEquals(messages, removeMessage);
        }

        @Test public void testCreateDirectMessage() {
            MediaDatabase media = new MediaDatabase("inputAccountSaveFile.txt", "inputDirectMessageFile.txt");
            String created = "";
            Account friend1 = new Account("Alice,newPassword8,true:John,Amy:");
            Account friend2 = new Account("Amy,outOfIdeas,true:Alice,Rand:Tom,John");
            try {
                created = media.createDirectMessage(friend1, friend2);
            } catch (InvalidTargetException e) {
                e.printStackTrace();
                fail();
            }
            assertNotNull(created);
            String filename = media.getDirectMessageFileName(friend1, friend2);
            assertEquals(filename, created);
        }

        @Test public void testAddAcount() {
            MediaDatabase media = new MediaDatabase("inputAccountSaveFile.txt", "inputDirectMessageFile.txt");
            Account candy = new Account("Candy,AwesomePassword23,true:Alice,Amy:Rand");
            boolean works = media.addAccount("Candy,AwesomePassword23,true:Alice,Amy:Rand");
            if (!accounts.contains(candy)) {
                fail();
            }
            assertEquals(true, works);
        }

        @Test public void testLogIntoAccount() {
            MediaDatabase media = new MediaDatabase("inputAccountSaveFile.txt", "inputDirectMessageFile.txt");
            Account friend1 = new Account("Alice,newPassword8,true:John,Amy:");
            Account friend2 = new Account("Amy,outOfIdeas,true:Alice,Rand:Tom,John");
            Account login = null;
            Account failLogin = null;
            try {
                login = media.logIntoAccount("Alice", "newPassword8");
            } catch (BadDataException e) {
                e.printStackTrace();
                fail();
            }
            assertEquals(friend1, login);
            try {
                failLogin = media.logIntoAccount("Alice", "newPassword8");
            } catch (BadDataException e) {

            }
            assertNotEquals(friend1, failLogin);

        }

        @Test public void testFindAcount() {
            MediaDatabase media = new MediaDatabase("inputAccountSaveFile.txt", "inputDirectMessageFile.txt");
            Account friend1 = new Account("Alice,newPassword8,true:John,Amy:");
            Account returned = null;
            try {
                returned = media.findAccount("Alice");
            } catch (BadDataException e) {
                e.printStackTrace();
                fail();
            }
            assertEquals(friend1, returned);
        }

        @Test public void testGetDirectMessageFileName() {
            MediaDatabase media = new MediaDatabase("inputAccountSaveFile.txt", "inputDirectMessageFile.txt");
            Account friend1 = new Account("Alice,newPassword8,true:John,Amy:");
            Account friend2 = new Account("Amy,outOfIdeas,true:Alice,Rand:Tom,John");
            String correct = "Alice,Amy.txt";
            String filename = media.getDirectMessageFileName(friend1, friend2);
            assertEquals(correct, filename);

        }

        @Test public void testAlterAccount() {
            MediaDatabase media = new MediaDatabase("inputAccountSaveFile.txt", "inputDirectMessageFile.txt");
            Account friend1 = new Account("Alice,oldPassword7,true:Amy,Rand:John");
            media.alterAccount("Alice", friend1);
            if (!accounts.contains(friend1)) {
                fail();
            }
        }


    }

}
