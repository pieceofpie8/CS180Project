import org.junit.Test;

import org.junit.After;
import org.junit.Before;
import java.io.*;
import java.util.ArrayList;

import static org.junit.Assert.*;
public class AccountTestCase {
    public static class TestCase {
        private final PrintStream originalOutput = System.out;
        private final InputStream originalSysin = System.in;

        private String name;
        private String password;
        private ArrayList<Account> friends;
        private ArrayList<Account> blocked;
        private boolean friendsOnly;



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

        @Test public void testAccountConstructor() {
            Account test  = new Account("John,Password123,true:Alice,Rand:Amy");
            assertNotNull(friends);
            assertNotNull(blocked);
            assertNotNull(password);
            assertEquals(true, friendsOnly);
            String[] testFriends = new String[1];
            testFriends[0] = "Alice";
            testFriends[1] = "Rand";
            assertEquals(testFriends, friends);
            assertEquals("Amy", blocked);
        }

        @Test public void testAddFriend() {
            Account test  = new Account("John,Password123,true:Alice,Rand:Amy");
            Account testerFriend = new Account("Candy,AwesomePassword23,true:Alice,Amy:Rand");
            test.addFriend(testerFriend);
            String[] testFriends = new String[2];
            testFriends[0] = "Alice";
            testFriends[1] = "Amy";
            testFriends[2] = "Candy";
            assertEquals(testFriends, friends);

            Account testBlocked = new Account("Amy,outOfIdeas:Alice,Rand:Tom,John");
            boolean block = test.addFriend(testBlocked);
            assertEquals(false, block);
        }

        @Test public void testAddBlocked() {
            Account test  = new Account("John,Password123,true:Alice,Rand:Amy");
            Account testBlocked = new Account("Rand,somethingHere,true:Amy,Tom:");
            test.addBlocked(testBlocked);
            String[] testerBlocked = new String[1];
            testerBlocked[0] = "Amy";
            testerBlocked[1] = "Rand";
            assertEquals(testerBlocked, blocked);

            String[] testFriends = new String[0];
            testFriends[0] = "Alice";
            assertEquals(testFriends, friends);

            Account alreadyBlock = new Account("Amy,outOfIdeas:Alice,Rand:Tom,John");
            boolean blocked = test.addBlocked(alreadyBlock);
            assertEquals(false, blocked);

        }

        @Test public void testRemoveFriend() {
            Account test  = new Account("John,Password123,true:Alice,Rand:Amy");
            Account removed = new Account("Rand,somethingHere,true:Amy,Tom:");
            test.removeFriend(removed);
            String[] newFriends = new String[0];
            newFriends[0] = "Alice";
            assertEquals(newFriends, friends);
        }

        @Test public void testRemoveBlocked() {
            Account test  = new Account("John,Password123,true:Alice,Rand:Amy");
            Account blockAccount = new Account("Amy,outOfIdeas:Alice,Rand:Tom,John");
            test.removeBlocked(blockAccount);
            String[] block = new String[0];
            block[0] = "";
            assertEquals(block, blocked);
        }

        @Test public void testAccountToString() {
            Account test  = new Account("John,Password123,true:Alice,Rand:Amy");
            String written = test.toString();
            assertEquals("John,Password123,true:Alice,Rand:Amy", written);
        }
    }
}
