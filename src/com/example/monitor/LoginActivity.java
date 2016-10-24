package com.example.monitor;

import com.bobo.data.AccountNow;
import com.bobo.data.mark_of_login;
import com.bobo.net.Login;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener {

	private EditText edit_username;
	private EditText edit_password;
	private Button button_conf;
	private Button button_regi;
	private ImageButton button_setup;
	private CheckBox box_keeppassword;

	private String username;
	private String password;
	private boolean isKeep = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loginpage);

		initView();
		getCode();
	}

	private void initView() {
		edit_username = (EditText) findViewById(R.id.login_username);
		edit_password = (EditText) findViewById(R.id.login_password);

		button_conf = (Button) findViewById(R.id.login_conf);
		button_regi = (Button) findViewById(R.id.login_regi);
		button_setup = (ImageButton) findViewById(R.id.login_setup);

		button_conf.setOnClickListener(this);
		button_regi.setOnClickListener(this);
		button_setup.setOnClickListener(this);

		box_keeppassword = (CheckBox) findViewById(R.id.login_keeppassword);
		box_keeppassword
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton arg0,
							boolean arg1) {
						isKeep = arg1;
					}
				});
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		if (v.getId() == R.id.login_conf) {
			// 登录功能
			if (getData()) {
				if (valiData()) {
					setAccount();
					keepStatus();
					// Toast.makeText(this, "登陆成功！", Toast.LENGTH_SHORT).show();
					if (isKeep)
						keepCode();
					else
						deleteCode();
					intent.setClass(this, MainActivity.class);
					this.startActivity(intent);
					finish();
				} else {
					deleteStatus();
					Toast.makeText(this, "登录失败！", Toast.LENGTH_SHORT).show();
				}
			} else
				return;
		} else if (v.getId() == R.id.login_regi) {
			// 注册功能
			intent.setClass(this, RegisterActivity.class);
			this.startActivity(intent);
			finish();
		} else if (v.getId() == R.id.login_setup) {
			// 设置功能
			// Toast.makeText(this, "设置", Toast.LENGTH_SHORT).show();
			intent.setClass(this, SetupActivity.class);
			this.startActivity(intent);
		}
	}

	// 获取编辑框数据
	private boolean getData() {
		username = edit_username.getText().toString();
		password = edit_password.getText().toString();

		if (username.length() == 0 || password.length() == 0) {
			Toast.makeText(this, "请填写完整！", Toast.LENGTH_SHORT).show();
			return false;
		}

		return true;
	}

	// 验证登录
	private boolean valiData() {

		Login login = new Login(username, password, 1);
		login.start();

		byte mark = -1;
		int count = 0;
		new mark_of_login();

		while (mark == -1) {
			mark = mark_of_login.getMark();
			count++;
			if (count == 2000000)
				return false;
		}
		mark_of_login.setMark((byte) -1);

		if (mark == 1)
			return true;
		else
			return false;
	}

	// 记住密码
	private void keepCode() {
		SharedPreferences preferences = getSharedPreferences("USER",
				Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("username", username);
		editor.putString("password", password);
		editor.commit();
	}

	// 删除已经记住的密码
	private void deleteCode() {
		SharedPreferences preferences = getSharedPreferences("USER",
				Activity.MODE_PRIVATE);
		preferences.edit().clear().commit();
	}

	// 获取记住的用户名和密码
	private void getCode() {
		SharedPreferences preferences = getSharedPreferences("USER",
				Activity.MODE_PRIVATE);
		String name = preferences.getString("username", "");
		String code = preferences.getString("password", "");

		edit_username.setText(name);
		edit_password.setText(code);
	}

	// 记住登录状态
	private void keepStatus() {
		SharedPreferences preferences = getSharedPreferences("ISLOGINED",
				Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean("mark", true);
		editor.commit();
	}

	// 删除登录状态
	private void deleteStatus() {
		SharedPreferences preferences = getSharedPreferences("ISLOGINED",
				Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.clear().commit();
	}

	// 记住本次登录信息
	private void setAccount() {
		SharedPreferences preferences = getSharedPreferences("USERLAST",
				Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("username", username);
		editor.putString("password", password);
		editor.commit();

		new AccountNow();
		AccountNow.setUsername(username);
		AccountNow.setPassword(password);
	}

}
