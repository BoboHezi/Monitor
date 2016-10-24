package com.bobo.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import com.bobo.data.IpAddress;
import com.bobo.data.mark_of_login;

public class Login extends Thread {

	Socket client_socket;
	DataInputStream inputStream = null;
	DataOutputStream outputStream = null;
	static String username;
	static String password;
	static int flag;
	String ip = IpAddress.getIpAddress();

	public Login(String username, String password, int flag) {
		Login.username = username;
		Login.password = password;
		Login.flag = flag;
	}

	@Override
	public void run() {
		try {
			client_socket = new Socket(ip, 3333);
			inputStream = new DataInputStream(client_socket.getInputStream());
			outputStream = new DataOutputStream(client_socket.getOutputStream());

			outputStream.writeInt(flag);
			outputStream.writeUTF(username);
			outputStream.writeUTF(password);

			boolean flag = inputStream.readBoolean();

			if (flag) {
				new mark_of_login();
				mark_of_login.setMark((byte) 1);
			} else
				mark_of_login.setMark((byte) 0);

			outputStream.close();
			inputStream.close();
			client_socket.close();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
