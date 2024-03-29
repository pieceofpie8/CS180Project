I’m mostly basing the structure of this off of the Project 3 database. 
This is just something I put together as a quick overview of the project, I’m open to any changes. 
I have not yet accounted for Threads and concurrency.

AccountInterface:
Get / set name
Get / set password
Get / set friends
Get / set friendsOnly
Add friend
Remove friend
Get / set blocked
Add blocked
Remove blocked
equals
toString

Class Account implements AccountInterface:
Fields
Has a String name. This should be made unique so that accounts are differentiable
Has a String password
Has an Array or Arraylist of friended Accounts
Has an Array or Arraylist of blocked Accounts
Has boolean “friendsOnly” specifying who can send messages to this person.
Constructors
Account (String data): creates an account based off of a String of data from our database save file. I recommend something like
”Name,Password,friendsOnly(true/false):friendName,friendName:blockedName” if we do something like this we will need to make sure names and passwords cannot include “,” or “:”
Should probably use the split function for this
Methods
Get / set name
Get / set password
Get / set friends
Add friend (adds an account parameter to the end of friend array) [check to make sure not blocked first. If they are, run remove blocked first, then run add friend]
Remove friend (removes an account parameter from friend array)
Get / set blocked
Add blocked (adds an account parameter to the end of blocked array) [check to make sure not friended. If they are, run remove friend first, then run add blocked]
Remove blocked (removes an account parameter from blocked array)
equals (should probably only be based on name)
toString (should probably give the same format as constructor data)
Generally, all of these methods should only be used for ease of access in the database methods.

MediaDatabaseInterface:
I’m not sure why but the project says “Every program class must have a dedicated interface.” This could be referring to a graphical interface, but it seems more likely that every class just needs to have a corresponding interface, so here’s one for the main database class.
Add Account (make sure name is unique and both name and password do not have “,” or “:”. Parameter is the dataString to create a new Account)
Read accountsSaveFile (read the file to set Accounts arrayList)
Output accountsSaveFile (write the file based on Accounts arrayList)
Find Account (used for user search, parameter is a name, returns account object)


Read DirectMessagesNames (reads a file with all the filenames for all of the directMessages and makes ArrayList<String> directMessageFiles)
Output DirectMessagesNames (write the file based on  ArrayList<String> directMessageFiles)


Read DirectMessages (reads a direct messages file and returns an ArrayList<String> of the messages)
Output DirectMessages (writes the file based on an ArrayList<String> input).
addMessage 
(throw InvalidTargetException if the sender cannot send to the target (blocked, etc.). Adds new String index at the end of the ArrayList in the format of the Direct Message File (including index number))
removeMessage 
(removes the message at the given index if it was sent by the account removing it. Remakes all subsequent indexes the same but with one less in the "(#)". Returns the remade ArrayList)
createDirectMessage 
(throw InvalidTargetException if the sender cannot send to the target (blocked, etc.). Otherwise, write "(0) Direct Messages Started!\n" to a new file with a name made by the two Accounts. Add the filename to ArrayList<String> directMessageFiles. Return the filename)

Direct message file ex. File name in this case would be ”AccountName:OtherAccountName”
	(0) Direct Messages Started!
(1) From AccountName: blah blah blah…
(2) From OtherAccountName: blah blah blah…
(3) etc
Each message is a new line. What I think will happen is whenever someone sends a message it updates the DirectMessage file with a new line and then saves it so that when the other person reads it they see the message.


Class MediaDatabase implements MediaDatabaseInterface:
Fields
Has an Account ArrayList Accounts.
Has a String accountsSaveFile = “accountsSave.txt”
(this is read from to get the data, then written back to to save the data. For the purpose of testing we may want to replace it with another file when writing so that we can compare)

Has String DirectMessagesNames = “directMessageFileNames.txt” 
(this is a file with the names of all of the files containing direct message history. The names of the files are made by taking the names of the two accounts, putting them into an ArrayList and sorting them: https://www.programiz.com/java-programming/library/arraylist/sort)

Has ArrayList<String> directMessageFiles
(this is an arraylist of the file names of the files containing direct message history)



Constructors
mediaDatabase(String accountsSaveFile, String directMessageFileNamesFile): creates the Accounts ArrayList and DirectMessagesNames ArrayList from the saved information.
accountsSaveFile format example:
		John,Password123:Alice:Rand
		Alice,newPassword8:John,Amy:
		Rand,somethingHere:Amy,Tom:
		Amy,outOfIdeas:Alice,Rand:Tom,John
		Tom,NoClue:Rand:
directMessageFileNamesFile example:	(order of names decided by ArrayList sort function)
		Alice:John
		Amy:Rand
		etc…
I suspect that when constructing mediaDatabase, we will need to read through the file twice: first to set all of the accounts, second to add the friends and blocked to the accounts.

Methods
What is listed under interface databaseMethods



Exceptions:
	BadDataExcpetion - for invalid names, passwords, or unable to find Account
	InvalidTargetException - for if the target of a DM cannot be sent to
