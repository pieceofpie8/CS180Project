import java.util.ArrayList;

public class Account implements AccountInterface {
    private String name;
    private String password;
    private ArrayList<Account> friends;
    private ArrayList<Account> blocked;
    private boolean friendsOnly;

    public Account(String data) {
        String[] parts = data.split(":");
        String[] info = parts[0].split(",");

        this.name = info[0];
        this.password = info[1];
        this.friendsOnly = Boolean.parseBoolean(info[2]);
    }

    public Account(String data, ArrayList<Account> friends, ArrayList<Account> blocked) {
        String[] parts = data.split(":");
        String[] info = parts[0].split(",");

        this.name = info[0];
        this.password = info[1];
        this.friendsOnly = Boolean.parseBoolean(info[2]);

        this.friends = friends;
        this.blocked = blocked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFriends(ArrayList<Account> friends) {
        this.friends = friends;
    }

    public void setFriendsOnly(boolean friendsOnly) {
        this.friendsOnly = friendsOnly;
    }

    public boolean getFriendsOnly() {
        return friendsOnly;
    }

    public ArrayList<Account> getFriends() {
        return friends;
    }

    public boolean addFriend(Account friend) {
        if (friends.contains(friend)) {
            return true;
        }
        if (blocked.contains(friend)) {
            removeBlocked(friend);
        }
        return friends.add(friend);
    }

    public boolean removeFriend(Account friend) {
        return friends.remove(friend);
    }

    public ArrayList<Account> getBlocked() {
        return blocked;
    }

    public void setBlocked(ArrayList<Account> blocked) {
        this.blocked = blocked;
    }

    public boolean addBlocked(Account blocked) {
        if (this.blocked.contains(blocked)) {
            return true;
        }
        if (this.friends.contains(blocked)) {
            removeFriend(blocked);
        }
        return this.blocked.add(blocked);
    }

    public boolean removeBlocked(Account blocked) {
        return this.blocked.remove(blocked);
    }

    public boolean equals(Account account) {
        return this.name.equals(account.getName());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name).append(",").append(password).append(",").append(friendsOnly).append(":");

        for (int i = 0; i < friends.size(); i++) {
            stringBuilder.append(friends.get(i).getName());
            if (i < friends.size() - 1) {
                stringBuilder.append(",");
            }
        }
        stringBuilder.append(":");

        for (int i = 0; i < blocked.size(); i++) {
            stringBuilder.append(blocked.get(i).getName());
            if (i < blocked.size() - 1) {
                stringBuilder.append(",");
            }
        }
        return stringBuilder.toString();
    }
}