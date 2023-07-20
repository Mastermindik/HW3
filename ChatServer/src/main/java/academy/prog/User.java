package academy.prog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class User {
    private String user;

    public User(String user) {
        this.user = user;
    }

    public static User fromJSON(String s) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(s, User.class);
    }

    @Override
    public String toString() {
        return user;
    }
}
