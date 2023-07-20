package academy.prog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class UsersList {
    private static final UsersList usersList = new UsersList();
    private final Gson gson = new Gson();
    private final List<User> list = new ArrayList<>();
    public static UsersList getInstance() {
        return usersList;
    }
    public synchronized void add(User user) {
        list.add(user);
    }

    public List<User> getList() {
        return list;
    }

    public synchronized String usersToJSON() {
        return gson.toJson(list);
    }

    @Override
    public String toString() {
        return "UsersList{" +
                "list=" + list +
                '}';
    }
}
