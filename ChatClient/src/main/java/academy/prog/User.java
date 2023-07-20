package academy.prog;

import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class User {
    private String user;

    public User(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return user;
    }

    public int sendUser() throws IOException {
        URL obj = new URL(Utils.getURL() + "/users");
        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            String json = new GsonBuilder().create().toJson(this);
            os.write(json.getBytes(StandardCharsets.UTF_8));
            return conn.getResponseCode(); // 200?
        }
    }

    public  void getUsers() throws IOException {
        URL obj = new URL(Utils.getURL() + "/users");
        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

        try (InputStream is = conn.getInputStream()) {
            byte[] buffer = responseBodyToArray(is);
            String strBuffer = new String(buffer, StandardCharsets.UTF_8);
            User[] list = new GsonBuilder().create().fromJson(strBuffer, User[].class);
            List<User> userList = Arrays.asList(list);
            System.out.println("Online users:");
            for (User user: userList) {
                System.out.println(user);
            }
        }
    }

    private byte[] responseBodyToArray(InputStream is) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[10240];
        int r;

        do {
            r = is.read(buf);
            if (r > 0) bos.write(buf, 0, r);
        } while (r != -1);

        return bos.toByteArray();
    }
}
