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
