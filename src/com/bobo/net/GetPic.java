package com.bobo.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.bobo.data.IpAddress;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class GetPic extends Thread {

	private Socket clientScoket;
	private String ip = new IpAddress().getIpAddress();
	PIC pic = new PIC();

	@SuppressWarnings({ "static-access" })
	@Override
	public void run() {
		int flag = 0;
		while (true) {
			try {
				clientScoket = new Socket(ip, 6000);
				DataInputStream ins = new DataInputStream(
						clientScoket.getInputStream());
				DataOutputStream ous = new DataOutputStream(
						clientScoket.getOutputStream());

				// ��ȡ��ת��Ϊbitmap����
				Bitmap bitmap = null;
				bitmap = BitmapFactory.decodeStream(ins);
				// ���빲������
				pic.setBitmap(bitmap);
				pic.setIsreach(true);

				// �ر��������������
				ins.close();
				ous.close();
				clientScoket.close();
			} catch (UnknownHostException e) {

			} catch (IOException e) {
				if (flag == 3) {
					System.out.println("Cannot Connect To Server!");
					pic.setIsreach(false);
					break;
				}
				flag++;
			}
		}
	}
}
