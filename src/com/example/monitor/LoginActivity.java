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
			// ��¼����
			if (getData()) {
				if (valiData()) {
					setAccount();
					keepStatus();
					// Toast.makeText(this, "��½�ɹ���", Toast.LENGTH_SHORT).show();
					if (isKeep)
						keepCode();
					else
						deleteCode();
					intent.setClass(this, MainActivity.class);
					this.startActivity(intent);
					finish();
				} else {
					deleteStatus();
					Toast.makeText(this, "��¼ʧ�ܣ�", Toast.LENGTH_SHORT).show();
				}
			} else
				return;
		} else if (v.getId() == R.id.login_regi) {
			// ע�Ṧ��
			intent.setClass(this, RegisterActivity.class);
			this.startActivity(intent);
			finish();
		} else if (v.getId() == R.id.login_setup) {
			// ���ù���
			// Toast.makeText(this, "����", Toast.LENGTH_SHORT).show();
			intent.setClass(this, SetupActivity.class);
			this.startActivity(intent);
		}
	}

	// ��ȡ�༭������
	private boolean getData() {
		username = edit_username.getText().toString();
		password = edit_password.getText().toString();

		if (username.length() == 0 || password.length() == 0) {
			Toast.makeText(this, "����д������", Toast.LENGTH_SHORT).show();
			return false;
		}

		return true;
	}

	// ��֤��¼
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

	// ��ס����
	private void keepCode() {
		SharedPreferences preferences = getSharedPreferences("USER",
				Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("username", username);
		editor.putString("password", password);
		editor.commit();
	}

	// ɾ���Ѿ���ס������
	private void deleteCode() {
		SharedPreferences preferences = getSharedPreferences("USER",
				Activity.MODE_PRIVATE);
		preferences.edit().clear().commit();
	}

	// ��ȡ��ס���û���������
	private void getCode() {
		SharedPreferences preferences = getSharedPreferences("USER",
				Activity.MODE_PRIVATE);
		String name = preferences.getString("username", "");
		String code = preferences.getString("password", "");

		edit_username.setText(name);
		edit_password.setText(code);
	}

	// ��ס��¼״̬
	private void keepStatus() {
		SharedPreferences preferences = getSharedPreferences("ISLOGINED",
				Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean("mark", true);
		editor.commit();
	}

	// ɾ����¼״̬
	private void deleteStatus() {
		SharedPreferences preferences = getSharedPreferences("ISLOGINED",
				Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.clear().commit();
	}

	// ��ס���ε�¼��Ϣ
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
