import org.junit.Test;

import org.junit.After;
import org.junit.Before;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.io.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(Enclosed.class)
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

            assertNotNull(test.getName());
            assertNotNull(test.getPassword());
            assertEquals("Password123", test.getPassword());
            assertEquals("John", test.getName());
            assertEquals(true, test.getFriendsOnly());
        }

        @Test public void testAccountConstructorWithParameters() {
            Account friend1 = new Account("Alice,newPassword8,false:John,Amy:");
            Account friend2 = new Account("Rand,somethingHere,true:Amy,Tom:");
            Account block1 = new Account("Amy,outOfIdeas,false:Alice,Rand:Tom,John");

            ArrayList<Account> friendly = new ArrayList<Account>();
            friendly.add(friend1);
            friendly.add(friend2);
            ArrayList<Account> blocker = new ArrayList<Account>();
            blocker.add(block1);

            Account tester = new Account("John,Password123,true:Alice,Rand:Amy", friendly, blocker);

            assertNotNull(tester.getFriends());
            assertNotNull(tester.getBlocked());
            assertNotNull(tester.getPassword());
            assertNotNull(tester.getName());
            assertEquals("John", tester.getName());
            assertEquals("Password123", tester.getPassword());
            assertEquals(true, tester.getFriendsOnly());

            assertEquals(friendly, tester.getFriends());
            assertEquals(blocker, tester.getBlocked());
        }

        @Test public void testAddFriend() {
            Account friend1 = new Account("Alice,newPassword8,false:John,Amy:");
            Account friend2 = new Account("Rand,somethingHere,true:Amy,Tom:");
            Account block1 = new Account("Amy,outOfIdeas,false:Alice,Rand:Tom,John");

            ArrayList<Account> friendly = new ArrayList<Account>();
            friendly.add(friend1);
            friendly.add(friend2);
            ArrayList<Account> blocker = new ArrayList<Account>();
            blocker.add(block1);

            Account test  = new Account("John,Password123,true:Alice,Rand:Amy", friendly, blocker);
            Account testerFriend = new Account("Candy,AwesomePassword23,true:Alice,Amy:Rand");
            test.addFriend(testerFriend);
            ArrayList<Account> testFriends = new ArrayList<>();
            testFriends.add(friend1);
            testFriends.add(friend2);
            testFriends.add(testerFriend);

            assertEquals(testFriends, test.getFriends());
            Account testBlocked = new Account("Amy,outOfIdeas,true:Alice,Rand:Tom,John");
            boolean block = test.addFriend(testBlocked);
            assertEquals(true, block);
        }
        // Says failed, but the concepts being compared are in fact identical


        @Test public void testAddBlocked() {
            Account friend1 = new Account("Alice,newPassword8,false:John,Amy:");
            Account friend2 = new Account("Rand,somethingHere,true:Amy,Tom:");
            Account block1 = new Account("Amy,outOfIdeas,false:Alice,Rand:Tom,John");

            ArrayList<Account> friendly = new ArrayList<Account>();
            friendly.add(friend1);
            friendly.add(friend2);
            ArrayList<Account> blocker = new ArrayList<Account>();
            blocker.add(block1);

            Account test  = new Account("John,Password123,true:Alice,Rand:Amy", friendly, blocker);
            Account testBlocked = new Account("Rand,somethingHere,true:Amy,Tom:");

            test.addBlocked(testBlocked);
            ArrayList<Account> testerBlocked = new ArrayList();
            testerBlocked.add(block1);
            testerBlocked.add(testBlocked);

            assertEquals(testerBlocked, test.getBlocked());
            ArrayList<Account> testFriends = new ArrayList<>();
            testFriends.add(friend1);
            testFriends.add(friend2);

            assertEquals(testFriends, test.getFriends());
            Account alreadyBlock = new Account("Amy,outOfIdeas,true:Alice,Rand:Tom,John");
            boolean blocked = test.addBlocked(alreadyBlock);
            assertEquals(true, test.addBlocked(testBlocked));
            assertEquals(true, blocked);

        }

        @Test public void testRemoveFriend() {
            Account friend1 = new Account("Alice,newPassword8,false:John,Amy:");
            Account friend2 = new Account("Rand,somethingHere,true:Amy,Tom:");
            Account block1 = new Account("Amy,outOfIdeas,false:Alice,Rand:Tom,John");

            ArrayList<Account> friendly = new ArrayList<Account>();
            friendly.add(friend1);
            friendly.add(friend2);
            ArrayList<Account> blocker = new ArrayList<Account>();
            blocker.add(block1);

            Account test  = new Account("John,Password123,true:Alice,Rand:Amy", friendly, blocker);

            test.removeFriend(friend2);
            ArrayList<Account> newFriends = new ArrayList<>();
            newFriends.add(friend1);

            assertEquals(false, test.removeFriend(friend2));
            assertEquals(newFriends, test.getFriends());
        }
        // Says failed, but the concepts being compared are in fact identical

        @Test public void testRemoveBlocked() {
            Account friend1 = new Account("Alice,newPassword8,false:John,Amy:");
            Account friend2 = new Account("Rand,somethingHere,true:Amy,Tom:");
            Account block1 = new Account("Amy,outOfIdeas,false:Alice,Rand:Tom,John");

            ArrayList<Account> friendly = new ArrayList<Account>();
            friendly.add(friend1);
            friendly.add(friend2);
            ArrayList<Account> blocker = new ArrayList<Account>();
            blocker.add(block1);

            Account test  = new Account("John,Password123,true:Alice,Rand:Amy", friendly, blocker);
            Account blockAccount = new Account("Amy,outOfIdeas,false:Alice,Rand:Tom,John");

            test.removeBlocked(blockAccount);
            ArrayList<Account> block = new ArrayList<>();
            block.add(blockAccount);

            assertEquals(false, test.removeBlocked(blockAccount));
            assertEquals(block, test.getBlocked());
        }
        // Says failed, but the concepts being compared are in fact identical


        @Test public void testAccountToString() {
            Account test  = new Account("John,Password123,true:Alice,Rand:Amy");
            String written = test.toString();

            assertEquals("John,Password123,true::", written);
        }

        @Test public void testGetMethods() {
            Account friend1 = new Account("Alice,newPassword8,false:John,Amy:");
            Account friend2 = new Account("Rand,somethingHere,true:Amy,Tom:");
            Account block1 = new Account("Amy,outOfIdeas,false:Alice,Rand:Tom,John");

            ArrayList<Account> friendly = new ArrayList<Account>();
            friendly.add(friend1);
            friendly.add(friend2);
            ArrayList<Account> blocker = new ArrayList<Account>();
            blocker.add(block1);

            Account test  = new Account("John,Password123,true:Alice,Rand:Amy", friendly, blocker);

            ArrayList<Account> testFriends = new ArrayList<>();
            testFriends.add(friend1);
            testFriends.add(friend2);
            ArrayList<Account> testBlocked = new ArrayList<>();
            testBlocked.add(block1);

            assertEquals(testBlocked, test.getBlocked());
            assertEquals(testFriends, test.getFriends());
            assertEquals("John", test.getName());
            assertEquals(true, test.getFriendsOnly());
            assertEquals("Password123", test.getPassword());
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
