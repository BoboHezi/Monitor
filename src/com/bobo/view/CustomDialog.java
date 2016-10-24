package com.bobo.view;

import com.example.monitor.R;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CustomDialog extends Dialog implements
		android.view.View.OnClickListener {

	private Button btn_logout;
	private Button btn_setup;
	private Button btn_revise;

	public interface OnCustomDialogListener {
		public void back(byte flag);
	}

	private OnCustomDialogListener listener;

	public CustomDialog(Context context, OnCustomDialogListener listener) {
		super(context);
		this.listener = listener;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.dialog);
		initView();
	}

	private void initView() {
		btn_logout = (Button) findViewById(R.id.dialog_logout);
		btn_setup = (Button) findViewById(R.id.dialog_setup);
		btn_revise = (Button) findViewById(R.id.dialog_revisecode);

		btn_logout.setOnClickListener(this);
		btn_setup.setOnClickListener(this);
		btn_revise.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.dialog_logout) {
			listener.back((byte) 1);
		} else if (v.getId() == R.id.dialog_setup) {
			listener.back((byte) 2);
		} else if (v.getId() == R.id.dialog_revisecode) {
			listener.back((byte) 3);
		}
		CustomDialog.this.dismiss();
	}

}
