package academy.prog;

import java.io.IOException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws IOException {
		Scanner scanner = new Scanner(System.in);
		try {
			System.out.println("Enter your login: ");
			String login = scanner.nextLine();

			//надсилаємо логін на сервлет юзерс де робиться список цих юзерів
			User user = new User(login);
			int res2 = user.sendUser();
	
			Thread th = new Thread(new GetThread());
			th.setDaemon(true);
			th.start();

            System.out.println("Enter your message: ");
			while (true) {
				String text = scanner.nextLine();
				String to = null;
				if (text.isEmpty()) break;

				if (text.equals("/users")) {
					user.getUsers();
					text = null;
				} else if (text.trim().startsWith("@")) {
					String[] arr = text.split(" ");
					to = arr[0].substring(1);
					text = arr[1];
				}

				if (text != null) {
					Message m = new Message(login, text, to);
					int res = m.send(Utils.getURL() + "/add");
					if (res != 200 || res2 != 200) { // 200 OK
						System.out.println("HTTP error occurred: " + res);
						return;
					}
				}

			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			scanner.close();
		}
	}
}
