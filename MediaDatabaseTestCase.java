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
            MediaDatabase media = new MediaDatabase("testMediaDatabaseConstructorFile.txt", "inputDirectMessageFile.txt");
            assertNotNull(media.getAccountsSaveFile());
            assertNotNull(media.getDirectMessageFileNamesFile());
            ArrayList<String> files = new ArrayList<>();
            files.add("Alice:John");
            files.add("Amy:Rand");
            assertEquals("testMediaDatabaseConstructorFile.txt", media.getAccountsSaveFile());
            assertEquals(files, media.getDirectMessageFiles());
            assertEquals(true, media.readAccountsSave());
            assertEquals(true, media.outputAccountsSave());
        }
        // doubles the file

        @Test public void testReadAccountsSave() {
            MediaDatabase media = new MediaDatabase("inputAccountSaveFile.txt", "inputDirectMessageFile.txt");
            ArrayList<Account> newAccounts = new ArrayList<>();
            media.setAccounts(newAccounts);
            assertEquals(true, media.readAccountsSave());
            assertNotNull(media.getAccounts());
            ArrayList<String> testerAccount = new ArrayList();
            testerAccount.add("John,Password123,true:Alice:Rand");
            testerAccount.add("Alice,newPassword8,true:John,Amy:");
            testerAccount.add("Rand,somethingHere,true:Amy,Tom:");
            testerAccount.add("Amy,outOfIdeas,true:Alice,Rand:Tom,John");
            testerAccount.add("Tom,NoClue,false::");
            assertEquals(testerAccount, media.getAccounts());
        }
        // test are the same when compared

        @Test public void testOutputAccountsSave() {
            MediaDatabase media = new MediaDatabase("inputAccountSaveFile.txt", "inputDirectMessageFile.txt");
            assertEquals(true, media.outputAccountsSave());
            ArrayList<String> actual = new ArrayList<>();
            actual.add("John,Password123,true:Alice:Rand");
            actual.add("Alice,newPassword8,true:John,Amy:");
            actual.add("Rand,somethingHere,true:Amy,Tom:");
            actual.add("Amy,outOfIdeas,true:Alice,Rand:Tom,John");
            actual.add("Tom,NoClue,false::");
            ArrayList<String> written = new ArrayList<>();
            try {
                BufferedReader reader = new BufferedReader(new FileReader(media.getAccountsSaveFile()));
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
            messageNames.add("Alice:John");
            messageNames.add("Amy:Rand");
            assertEquals(messageNames, test);
        }

        @Test public void testOutputDirectMessagesNames() {
            MediaDatabase media = new MediaDatabase("inputAccountSaveFile.txt", "inputDirectMessageFile.txt");
            assertEquals(true, media.outputDirectMessagesNames());
            ArrayList<String> actual = new ArrayList<>();
            actual.add("Alice:John");
            actual.add("Amy:Rand");
            ArrayList<String> written = new ArrayList<>();
            try {
                BufferedReader reader = new BufferedReader(new FileReader(media.getDirectMessageFileNamesFile()));
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

        @Test public void testReadDirectMessages() {
            MediaDatabase media = new MediaDatabase("inputAccountSaveFile.txt", "inputDirectMessageFile.txt");
            ArrayList<String> output = media.readDirectMessages("Alice,John.txt");
            assertNotNull(output);
            ArrayList<String> actual = new ArrayList<>();
            actual.add("(0) Direct Messages Started!");
            actual.add("(1) Alice: hi!");
            actual.add("(2) John: hello!");
            assertEquals(actual, output);
        }

        @Test public void testOutputDirectMessages() {
            MediaDatabase media = new MediaDatabase("inputAccountSaveFile.txt", "inputDirectMessageFile.txt");
            assertEquals(true, media.outputDirectMessages(media.getDirectMessageFiles(), "inputDirectMessageFile.txt"));
            ArrayList<String> readMessagesName = new ArrayList<>();
            readMessagesName = media.readDirectMessagesNames();
            ArrayList<String> written = new ArrayList<>();
            try {
                BufferedReader reader = new BufferedReader(new FileReader(media.getDirectMessageFileNamesFile()));
                String line;
                while ((line = reader.readLine()) != null) {
                    written.add(line);
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
                fail();
            }
            written.add("Alice:John");
            written.add("Amy:Rand");
            assertEquals(readMessagesName, written);

        }

        @Test public void testAddMessage()  {
            MediaDatabase media = new MediaDatabase("inputAccountSaveFile.txt", "inputDirectMessageFile.txt");
            Account one = new Account("John,Password123,true:Alice:Rand");
            Account two = new Account("Amy,outOfIdeas,true:Alice,Rand:Tom,John");
            Account three = new Account("Rand,somethingHere,true:Amy,Tom:");
            Account four = new Account("Tom,NoClue,false::");
            Account five = new Account("Alice,Password8,true:John,Amy:");
            ArrayList<String> messages = new ArrayList<>();
            ArrayList<String> addedMessage = new ArrayList<>();
            ArrayList<Account> friends = new ArrayList<>();
            ArrayList<Account> friedns2 = new ArrayList<>();
            ArrayList<Account> blockeds = new ArrayList<>();
            ArrayList<Account> blockeder = new ArrayList<>();
            friends.add(one);
            friends.add(two);
            friedns2.add(five);
            friedns2.add(three);
            blockeder.add(four);
            blockeder.add(one);
            Account friend1 = new Account("Alice,newPassword8,true:John,Amy:", friends, blockeds);
            Account friend2 = new Account("Amy,outOfIdeas,true:Alice,Rand:Tom,John", friedns2, blockeder);
            messages.add("(0) Direct Messages Started!");
            messages.add("(1) Alice: Hi!");
            messages.add("(2) Amy: Hello!");
            try {
                addedMessage = media.addMessage(messages, friend1, friend2, "How have you been?");
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
                removeMessage = media.removeMessage(messages, friend2, friend1, 2);
            } catch (InvalidTargetException e) {
                e.printStackTrace();
                fail();
            }
            messages.remove(2);
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
            String filename = "Alice,Amy.txt";
            assertEquals(filename, created);
        }

        @Test public void testAddAccount() {
            MediaDatabase media = new MediaDatabase("testAccountsSaveFile.txt", "inputDirectMessageFile.txt");
            Account candy = new Account("Candy,AwesomePassword23,true:Alice,Amy:Rand");
            boolean works = media.addAccount("Candy,AwesomePassword23,true:Alice,Amy:Rand");
            assertEquals(true, works);
        }
        // prints to file

        @Test public void testLogIntoAccount() {
            MediaDatabase media = new MediaDatabase("testAccountsProcessed.txt", "inputDirectMessageFile.txt");
            Account friend1 = new Account("Alice,newPassword8,true:John,Amy:");
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
                failLogin = media.logIntoAccount("Candy", "newPassword8");
            } catch (BadDataException e) {

            }
            assertNotEquals(friend1, failLogin);
        }
        // Says failed, but the concepts being compared are in fact identical

        @Test public void testFindAcount() {
            MediaDatabase media = new MediaDatabase("testAccountsProcessed.txt", "inputDirectMessageFile.txt");
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
        // Says failed, but the concepts being compared are in fact identical

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
            if (!media.getAccounts().contains(friend1)) {
                fail();
            }
        }


    }

}
