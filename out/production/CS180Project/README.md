## Compile and Run:

To compile the project, run the following in the terminal:
"javac AccountInterface.java",
"javac Account.java",
"javac MediaDatabaseInterface.java",
"javac MediaDatabase.java",
"javac BadDataExcpetion.java",
"javac InvalidTargetException.java",
"javac InterfacesTestMain.java",
"javac InterfacesTestCase.java",
"javac MediaDatabaseTestCase.java",
"javac AccountTestCase.java",
"javac Main.java",

To run, type: “java Main” into the terminal.

## NOTES:

To add a blocked user as a friend or vice versa, first remove them from friends / blocked.
Starting a DM is a necessary operation to make sure the file saves properly. A message can be sent without starting one, but it is not recommended.

## Submission:

Submitted workspace on Vocareum by David Erb

# Classes:

AccountInterface:
	AccountInterface is an interface for the Account class that details the methods to be used in the class. These methods are: Get / set name, Get / set password, Get / set friends, Get / set friendsOnly, Add friend, Remove friend, Get / set blocked, Add blocked, Remove blocked, equals, toString. Testing was done using the InterfacesTestMain and InterfacesTestCase classes to ensure Account correctly implements AccountInterface.

Account:
	Account is a class that defines the Account object as an account for the social media program for use in the database. It has all the capabilities of the methods in its interface and includes the fields: String name, String password, Arraylist of friended Accounts, Arraylist of blocked Accounts, and boolean “friendsOnly” (specifying who can send messages to this person). The methods are meant for altering and defining all the aspects of an account. Testing was done using the AccountTestCase

MediaDatabaseInterface:
	MediaDatabaseInterface is an interface for the MediaDatabase class that details the methods to be used in the class. These methods are: addAccount, readAccountsSave, outputAccountsSave, logIntoAccount, findAccount, readDirectMessagesNames, outputDirectMessagesNames, readDirectMessages, outputDirectMessages, addMessage, removeMessage, createDirectMessage, alterAccount, getDirectMessageFileName, getAccounts, getAccountsSaveFile, getDirectMessageFileNamesFile, getDirectMessageFiles, and setAccounts. Testing was done using the InterfacesTestMain and InterfacesTestCase classes to ensure Account correctly implements AccountInterface.

MediaDatabase:
	MediaDatabase is a class that defines the storage and use of information in the social media program. It has all the capabilities of the methods in its interface with the purpose of reading, saving files, and altering files for the primary purpose of allowing direct messages to be exchanged between two accounts. Its fields are an ArrayList Accounts for all existing accounts, String accountsSaveFile for the name of the file containing account information, String DirectMessagesNames for the name of the file containing existing direct message file names, and ArrayList<String> directMessageFiles for all the existing direct message files. Testing was done using MediaDatabaseTestCase.


BadDataExcpetion:
	BadDataExcpetion is just an extension of Exception with the purpose of helping organize the project to know why exceptions are being made. BadDataExcpetion is meant for use when data does not follow the guidelines of the program (invalid character in name or password, etc.).

InvalidTargetException:
		InvalidTargetException is just an extension of Exception with the purpose of helping organize the project to know why exceptions are being made. InvalidTargetException is meant for use when one account tries to send a message to an account they are not allowed to (blocked or not friended).


InterfacesTestMain and InterfacesTestCase:
	These two classes go hand in hand with the purpose of testing if the interfaces of the class are implemented properly. The InterfacesTestMain class defines a main method that prints certain Strings depending on if the interfaces are correctly implemented. InterfacesTestCase uses InterfacesTestMain in a simple input/output test case to ensure that the correct string result is printed, indicating successful implementation. Ignore the error messages as part of the test case, they are a result of a purposeful input.

Main:
	This class contains the main method used to run the program as a social media. It prompts users to login or create an account and then allows the user to change their friends only status, change their friends, change their blocked, and read, send, delete, and start direct messages. This class / method utilized almost everything from MediaDatabase and Account to make the system work.

AccountTestCase: 
	This class tests the Account class, and makes sure that the methods in this class are working as intended. Each method is tested in a separate test, except for all the return methods and set methods, which have been combined into one test case per method type to conserve space. In this class, there is a static class named TestCase, in which all the test cases are written and performed. AccountTestCase imports Junit in order to properly test the Account class.

MediaDatabaseTestCase:
	This class tests the MediaDatabase class, and verifies that each method is working as intended. Each method creates new Account objects and runs the MediaDatabase methods, to ensure that the MediaDatabase methods are running properly, and giving the expected output. This class contains a static class named TestCase, which is used to test the MediaDatabase class. MediaDatabaseTestCase imports Junit, which it uses to test the class and make sure that each method is running correctly. 

All added ".txt" files exist for the purpose of testing and examples.

File formats are as follows:
accountsSave.txt -
	“Name,Password,FriendsOnly(true/false):Friends:Blocked”

directMessageFileNames.txt -
	“Name1,Name2”

Message files - 
	“(0) Direct Messages Started!”
	“(1) Name1: message”
	“(2) Name2: message”
