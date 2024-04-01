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
        

        @Test public void testAddFriend() {
            Account test  = new Account("John,Password123,true:Alice,Rand:Amy");
            Account testerFriend = new Account("Candy,AwesomePassword23,true:Alice,Amy:Rand");
            Account friend1 = new Account("Alice,newPassword8,true:John,Amy:");
            Account friend2 = new Account("Amy,outOfIdeas,true:Alice,Rand:Tom,John");
            test.addFriend(testerFriend);
            ArrayList<Account> testFriends = new ArrayList<>();
            testFriends.add(friend1);
            testFriends.add(friend2);
            testFriends.add(testerFriend);

            assertEquals(testFriends, friends);
            Account testBlocked = new Account("Amy,outOfIdeas,true:Alice,Rand:Tom,John");
            boolean block = test.addFriend(testBlocked);
            assertEquals(false, block);
        }

        @Test public void testAddBlocked() {
            Account test  = new Account("John,Password123,true:Alice,Rand:Amy");
            Account testBlocked = new Account("Rand,somethingHere,true:Amy,Tom:");
            Account friend1 = new Account("Alice,newPassword8,true:John,Amy:");

            test.addBlocked(testBlocked);
            String[] testerBlocked = new String[1];
            testerBlocked[0] = "Amy";
            testerBlocked[1] = "Rand";
            assertEquals(testerBlocked, blocked);
            ArrayList<Account> testFriends = new ArrayList<>();
            testFriends.add(friend1);

            assertEquals(testFriends, friends);
            Account alreadyBlock = new Account("Amy,outOfIdeas,true:Alice,Rand:Tom,John");
            boolean blocked = test.addBlocked(alreadyBlock);
            assertEquals(true, test.addBlocked(testBlocked));
            assertEquals(false, blocked);

        }

        @Test public void testRemoveFriend() {
            Account test  = new Account("John,Password123,true:Alice,Rand:Amy");
            Account removed = new Account("Rand,somethingHere,true:Amy,Tom:");
            Account friend1 = new Account("Alice,newPassword8,true:John,Amy:");

            test.removeFriend(removed);
            ArrayList<Account> newFriends = new ArrayList<>();
            newFriends.add(friend1);

            assertEquals(true, test.removeFriend(removed));
            assertEquals(newFriends, friends);
        }

        @Test public void testRemoveBlocked() {
            Account test  = new Account("John,Password123,true:Alice,Rand:Amy");
            Account blockAccount = new Account("Amy,outOfIdeas,true:Alice,Rand:Tom,John");

            test.removeBlocked(blockAccount);
            ArrayList<Account> block = new ArrayList<>();

            assertEquals(true, test.removeBlocked(blockAccount));
            assertEquals(block, blocked);
        }

        @Test public void testAccountToString() {
            Account test  = new Account("John,Password123,true:Alice,Rand:Amy");
            String written = test.toString();

            assertEquals("John,Password123,true:Alice,Rand:Amy", written);
        }

        @Test public void testGetMethods() {
            Account test  = new Account("John,Password123,true:Alice,Rand:Amy");
            Account friend1 = new Account("Alice,newPassword8,true:John,Amy:");
            Account friend2 = new Account("Rand,somethingHere,true:Amy,Tom:");
            Account blocked1 = new Account("Amy,outOfIdeas,true:Alice,Rand:Tom,John");

            ArrayList<Account> testFriends = new ArrayList<>();
            testFriends.add(friend1);
            testFriends.add(friend2);
            ArrayList<Account> testBlocked = new ArrayList<>();
            testBlocked.add(blocked1);

            assertEquals(testBlocked, test.getBlocked());
            assertEquals(testFriends, test.getFriends());
            assertEquals("John", test.getName());
            assertEquals(true, test.getFriendsOnly());
            assertEquals("Passwords123", test.getPassword());
        }

        @Test public void testSetMethods() {
            Account test  = new Account("John,Password123,true:Alice,Rand:Amy");
            Account friend1 = new Account("Candy,AwesomePassword23,true:Alice,Amy:Rand");
            Account blocked1 = new Account("Alice,newPassword8,true:John,Amy:");

            ArrayList<Account> testFriends = new ArrayList<>();
            testFriends.add(friend1);
            ArrayList<Account> testBlocked = new ArrayList<>();
            testBlocked.add(blocked1);
            test.setBlocked(testBlocked);
            test.setFriends(testFriends);
            test.setName("Johnny");
            test.setFriendsOnly(false);
            test.setPassword("NewPassword42");

            assertEquals(testBlocked, test.getBlocked());
            assertEquals(testFriends, test.getFriends());
            assertEquals("Johnny", test.getName());
            assertEquals(false, test.getFriendsOnly());
            assertEquals("NewPassword42", test.getPassword());
        }

        @Test public void testEquals() {
            Account test  = new Account("John,Password123,true:Alice,Rand:Amy");
            Account test1 = new Account("Candy,AwesomePassword23,true:Alice,Amy:Rand");
            Account test2 = new Account("John,newPassword8,true:Candy,Amy:");

            assertEquals(true, test.equals(test2));
            assertEquals(false, test.equals(test1));

        }
    }
}
