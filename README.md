# Project Overview

I’m mostly basing the structure of this off of the Project 3 database. This is just something I put together as a quick overview of the project, I’m open to any changes. I have not yet accounted for Threads and concurrency.

## AccountInterface
- **Get / set name**: Get or set the name of the account.
- **Get / set password**: Get or set the password of the account.
- **Get / set friends**: Get or set the list of friends associated with the account.
- **Get / set friendsOnly**: Get or set the boolean indicating if the account allows messages only from friends.
- **Add friend**: Add a friend to the account's friend list. Checks for blocking status before adding.
- **Remove friend**: Remove a friend from the account's friend list.
- **Get / set blocked**: Get or set the list of blocked accounts.
- **Add blocked**: Add a blocked account to the account's block list. Checks for friend status before adding.
- **Remove blocked**: Remove a blocked account from the account's block list.
- **equals**: Compare two accounts based on their names.
- **toString**: Represent account data in a string format.

### Class Account implements AccountInterface
#### Fields
- **name**: A unique name identifying the account.
- **password**: The password associated with the account.
- **friends**: List of friends of the account.
- **blocked**: List of blocked accounts by the current account.
- **friendsOnly**: Boolean indicating messaging permissions.

#### Constructors
- **Account(String data)**: Create an account from a string of data. Data format: "Name,Password,FriendsOnly(true/false):FriendName,FriendName:BlockedName".

#### Methods
- Get / set name
- Get / set password
- Get / set friends
- Add friend
- Remove friend
- Get / set blocked
- Add blocked
- Remove blocked
- equals
- toString

## MediaDatabaseInterface
- **Add Account**: Add a new account ensuring uniqueness and valid name/password.
- **Read accountsSaveFile**: Read the file to set the list of accounts.
- **Output accountsSaveFile**: Write the accounts data to a file.
- **Find Account**: Find an account by name.

- **Read DirectMessagesNames**: Read a file containing filenames for all direct messages.
- **Output DirectMessagesNames**: Write direct message filenames to a file.
- **Read DirectMessages**: Read direct messages from a file.
- **Output DirectMessages**: Write direct messages to a file.
- **addMessage**: Add a message to the direct message file.
- **removeMessage**: Remove a message from the direct message file.
- **createDirectMessage**: Create a new direct message file.

### Class MediaDatabase implements MediaDatabaseInterface
#### Fields
- **Accounts**: List of accounts.
- **accountsSaveFile**: File storing account data.
- **DirectMessagesNames**: File containing filenames for direct messages.
- **directMessageFiles**: List of filenames for direct messages.

#### Constructors
- **MediaDatabase(String accountsSaveFile, String directMessageFileNamesFile)**: Initialize the media database with account and direct message data.

#### Methods
- All methods listed under `DatabaseMethods` interface.

## Exceptions
- **BadDataException**: For invalid names, passwords, or unable to find accounts.
- **InvalidTargetException**: For inability to send a direct message to the target account.



README:

Compile and Run:
To compile the project, run the following in the terminal:
	javac AccountInterface.java
	javac Account.java
	javac MediaDatabaseInterface.java
	javac MediaDatabase.java
	javac BadDataExcpetion.java
javac InvalidTargetException.java
	
javac InterfacesTestMain.java
javac InterfacesTestCase.java
javac ____ (fill in with main and other test case).

To run, type: “java Main” into the terminal.

Submission:
David Erb - Submitted Vocareum workspace

Classes:
AccountInterface:
	AccountInterface is an interface for the Account class that details the methods to be used in the class. These methods are: Get / set name, Get / set password, Get / set friends, Get / set friendsOnly, Add friend, Remove friend, Get / set blocked, Add blocked, Remove blocked, equals, toString. Testing was done using the InterfacesTestMain and InterfacesTestCase classes to ensure Account correctly implements AccountInterface.

Account:
	Account is a class that defines the Account object as an account for the social media program for use in the database. It has all the capabilities of the methods in its interface and includes the fields: String name, String password, Arraylist of friended Accounts, Arraylist of blocked Accounts, and boolean “friendsOnly” (specifying who can send messages to this person). The methods are meant for altering and defining all the aspects of an account. Testing was done using the _____

MediaDatabaseInterface:
	MediaDatabaseInterface is an interface for the MediaDatabase class that details the methods to be used in the class. These methods are: addAccount, readAccountsSave, outputAccountsSave, logIntoAccount, findAccount, readDirectMessagesNames, outputDirectMessagesNames, readDirectMessages, outputDirectMessages, addMessage, removeMessage, and createDirectMessage. Testing was done using the InterfacesTestMain and InterfacesTestCase classes to ensure Account correctly implements AccountInterface.

MediaDatabase:
	MediaDatabase is a class that defines the storage and use of information in the social media program. It has all the capabilities of the methods in its interface with the purpose of reading, saving files, and altering files for the primary purpose of allowing direct messages to be exchanged between two accounts. Its fields are an ArrayList Accounts for all existing accounts, String accountsSaveFile for the name of the file containing account information, String DirectMessagesNames for the name of the file containing existing direct message file names, and ArrayList<String> directMessageFiles for all the existing direct message files. Testing was done using the _____


BadDataExcpetion:
	BadDataExcpetion is just an extension of Exception with the purpose of helping organize the project to know why exceptions are being made. BadDataExcpetion is meant for use when data does not follow the guidelines of the program (invalid character in name or password, etc.).

InvalidTargetException:
		InvalidTargetException is just an extension of Exception with the purpose of helping organize the project to know why exceptions are being made. InvalidTargetException is meant for use when one account tries to send a message to an account they are not allowed to (blocked or not friended).


InterfacesTestMain and InterfacesTestCase:
	These two classes go hand in hand with the purpose of testing if the interfaces of the class are implemented properly. The InterfacesTestMain class defines a main method that prints certain Strings depending on if the interfaces are correctly implemented. InterfacesTestCase uses InterfacesTestMain in a simple input/output test case to ensure that the correct string result is printed, indicating successful implementation.

Main:
	This class contains the main method used to run the program as a social media. It prompts users to login or create an account and then allows the user to change their friends only status, change their friends, change their blocked, and read, send, delete, and start direct messages. This class / method utilized almost everything from MediaDatabase and Account to make the system work.

 AccountTestCase: 
	This class tests the Account class, and makes sure that the methods in this class are working as intended. Each method is tested in a separate test, except for all the return methods and set methods, which have been combined into one test case per method type to conserve space. In this class, there is a static class named TestCase, in which all the test cases are written and performed. AccountTestCase imports Junit in order to properly test the Account class. 

MediaDatabaseTestCase:
	This class tests the MediaDatabase class, and verifies that each method is working as intended. Each method creates new Account objects and runs the MediaDatabase methods, to ensure that the MediaDatabase methods are running properly, and giving the expected output. This class contains a static class named TestCase, which is used to test the MediaDatabase class. MediaDatabaseTestCase imports Junit, which it uses to test the class and make sure that each method is running correctly. 
