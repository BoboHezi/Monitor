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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class RegisterActivity extends Activity implements OnClickListener {
	private EditText edit_name;
	private EditText edit_code;
	private EditText edit_coderepeat;
	private Button btn_regi;
	private Button btn_canc;
	private ImageButton btn_setup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registerpage);

		initView();
	}

	private void initView() {
		edit_name = (EditText) findViewById(R.id.register_username);
		edit_code = (EditText) findViewById(R.id.register_password);
		edit_coderepeat = (EditText) findViewById(R.id.register_passwordrepeat);

		btn_regi = (Button) findViewById(R.id.register_regi);
		btn_canc = (Button) findViewById(R.id.register_canc);
		btn_setup = (ImageButton) findViewById(R.id.register_setup);

		btn_canc.setOnClickListener(this);
		btn_regi.setOnClickListener(this);
		btn_setup.setOnClickListener(this);

		Intent intent = getIntent();
		edit_name.setText(intent.getStringExtra("name"));
		edit_code.setText(intent.getStringExtra("code"));
	}

	@Override
	public void onClick(View v) {
		String name = edit_name.getText().toString();
		String code = edit_code.getText().toString();
		String coderepeat = edit_coderepeat.getText().toString();
		if (v.getId() == R.id.register_regi) {
			// ×¢²á
			if (name.length() == 0 || code.length() == 0
					|| coderepeat.length() == 0) {
				Toast.makeText(getApplicationContext(), "ÇëÌîÐ´ÍêÕû",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (code.equals(coderepeat)) {
				if (insert(name, code)) {
					setAccount(name, code);
					keepStatus();
					keepCode(name, code);
					Toast.makeText(getApplicationContext(), "×¢²á³É¹¦",
							Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(RegisterActivity.this,
							MainActivity.class);
					RegisterActivity.this.startActivity(intent);
					finish();
				} else
					Toast.makeText(getApplicationContext(), "×¢²áÊ§°Ü",
							Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getApplicationContext(), "ÃÜÂë´íÎó",
						Toast.LENGTH_SHORT).show();
			}
		} else if (v.getId() == R.id.register_canc) {
			// È¡Ïû
			RegisterActivity.this.finish();
		} else if (v.getId() == R.id.register_setup) {
			// ÉèÖÃ
			Intent intent = new Intent();
			intent.setClass(this, SetupActivity.class);
			this.startActivity(intent);
		}
	}

	private boolean insert(String name, String code) {
		Login login = new Login(name, code, 2);
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

	private void setAccount(String name, String code) {
		SharedPreferences preferences = getSharedPreferences("USERLAST",
				Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("username", name);
		editor.putString("password", code);
		editor.commit();

		new AccountNow();
		AccountNow.setUsername(name);
		AccountNow.setPassword(code);
	}

	// ¼Ç×¡µÇÂ¼×´Ì¬
	private void keepStatus() {
		SharedPreferences preferences = getSharedPreferences("ISLOGINED",
				Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean("mark", true);
		editor.commit();
	}

	// ¼Ç×¡ÃÜÂë
	private void keepCode(String username, String password) {
		SharedPreferences preferences = getSharedPreferences("USER",
				Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("username", username);
		editor.putString("password", password);
		editor.commit();
	}
}
