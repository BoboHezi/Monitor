package com.example.monitor;

import com.bobo.data.IpAddress;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SetupActivity extends Activity implements OnClickListener {

	private EditText edit_ip;
	private Button button_conf;
	private String ip;
	private SharedPreferences preferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setuppage);

		initView();
	}

	private void initView() {
		edit_ip = (EditText) findViewById(R.id.setup_ip);
		button_conf = (Button) findViewById(R.id.setup_conf);

		button_conf.setOnClickListener(this);

		preferences = getSharedPreferences("IP", Activity.MODE_PRIVATE);
		ip = preferences.getString("ip", "");
		edit_ip.setText(ip);
	}

	@Override
	public void onClick(View arg0) {
		ip = edit_ip.getText().toString();

		preferences = getSharedPreferences("IP", Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("ip", ip);
		editor.commit();

		new IpAddress();
		IpAddress.setIpAddress(ip);

		Toast.makeText(this, "±£´æ³É¹¦£¡", Toast.LENGTH_SHORT).show();
	}

}
