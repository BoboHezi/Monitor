package com.example.monitor;

import com.bobo.data.AccountNow;
import com.bobo.data.mark_of_login;
import com.bobo.net.Login;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ReviseActivity extends Activity implements OnClickListener {

	private Button btn_conf;
	private Button btn_canc;
	private EditText edit_old;
	private EditText edit_new;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.revisepage);

		initView();
	}

	private void initView() {
		edit_old = (EditText) findViewById(R.id.revise_oldcode);
		edit_new = (EditText) findViewById(R.id.revise_newcode);
		btn_conf = (Button) findViewById(R.id.revise_conf);
		btn_canc = (Button) findViewById(R.id.revise_canc);

		btn_canc.setOnClickListener(this);
		btn_conf.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.revise_conf) {
			String oldcode = edit_old.getText().toString();
			String newcode = edit_new.getText().toString();
			if (oldcode.length() == 0 || newcode.length() == 0) {
				Toast.makeText(this, "请输入完整", Toast.LENGTH_SHORT).show();
				return;
			}
			if (oldcode.equals(newcode) || !(valiCode(oldcode))) {
				Toast.makeText(this, "密码有误", Toast.LENGTH_SHORT).show();
				return;
			}
			new AccountNow();
			if (!(reviseCode(AccountNow.getUsername(), oldcode))) {
				Toast.makeText(this, "密码更改失败", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "更改成功", Toast.LENGTH_SHORT).show();
				finish();
			}

		} else if (v.getId() == R.id.revise_canc)
			finish();
	}

	private boolean valiCode(String oldcode) {
		SharedPreferences preferences = getSharedPreferences("USERLAST",
				Activity.MODE_PRIVATE);
		return preferences.getString("password", "").equals(oldcode);
	}

	private boolean reviseCode(String username, String password) {
		Login login = new Login(username, password, 3);
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
}
