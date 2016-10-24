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

				// 读取并转换为bitmap对象
				Bitmap bitmap = null;
				bitmap = BitmapFactory.decodeStream(ins);
				// 放入共享类中
				pic.setBitmap(bitmap);
				pic.setIsreach(true);

				// 关闭所有输入输出流
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
