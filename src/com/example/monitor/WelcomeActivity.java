package com.example.monitor;

import com.bobo.data.AccountNow;
import com.bobo.data.IpAddress;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class WelcomeActivity extends Activity {

	private int time = 0;
	private boolean flag = false;
	private SharedPreferences preferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcomepage);

		setIP();
		getAccount();
		flag = getStatus();
		handler.sendEmptyMessage(0);
	}

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (time == 2000) {
				jumpActivity();
			} else {
				handler.sendEmptyMessageDelayed(0, 100);
				time = time + 100;
			}
		};
	};

	private void jumpActivity() {

		Intent intent = new Intent();

		if (flag)
			intent.setClass(this, MainActivity.class);
		else
			intent.setClass(this, LoginActivity.class);

		this.startActivity(intent);
		finish();
	}

	private boolean getStatus() {
		preferences = getSharedPreferences("ISLOGINED", Activity.MODE_PRIVATE);
		if (preferences.getBoolean("mark", false)) {
			return true;
		}
		return false;
	}

	private void setIP() {
		SharedPreferences preferences = getSharedPreferences("IP",
				Activity.MODE_PRIVATE);
		String ip = preferences.getString("ip", "");
		new IpAddress();
		IpAddress.setIpAddress(ip);
	}

	private void getAccount() {
		preferences = getSharedPreferences("USERLAST", Activity.MODE_PRIVATE);
		String name = preferences.getString("username", "");
		String code = preferences.getString("password", "");

		new AccountNow();
		AccountNow.setUsername(name);
		AccountNow.setPassword(code);
	}
}
