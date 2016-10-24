package com.example.monitor;

import com.bobo.net.GetPic;
import com.bobo.net.PIC;
import com.bobo.view.CustomDialog;
import com.bobo.view.CustomDialog.OnCustomDialogListener;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	private ImageView image;
	private Button start;
	private ImageButton btn_setup;

	private GetPic getPic = new GetPic();
	private Handler handler = new Handler();
	private boolean isfirst = true;
	PIC pic = new PIC();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main_frame);

		image = (ImageView) findViewById(R.id.image);
		start = (Button) findViewById(R.id.start);
		btn_setup = (ImageButton) findViewById(R.id.main_setup);
		btn_setup.setOnClickListener(this);

		start.setOnClickListener(this);
	}

	private Runnable draw = new Runnable() {
		@SuppressWarnings({ "static-access" })
		public void run() {

			if (!pic.isreach) {
				Toast.makeText(getApplicationContext(), "服务器未响应！",
						Toast.LENGTH_SHORT).show();
				start.setText("START");
				isfirst = true;
				return;
			}
			// 获取bitmap对象
			Bitmap bitmap = new PIC().getBitmap();
			handler.postDelayed(this, 100);
			// 绘制图像
			image.setImageBitmap(bitmap);
		}
	};

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.start) {
			if (start.getText().equals("START")) {

				if (isfirst) {
					// 启动线程
					getPic.start();
					isfirst = false;
				}
				start.setText("STOP");
				handler.postDelayed(draw, 100);

			} else {
				start.setText("START");
				handler.removeCallbacks(draw);
			}
		} else {
			CustomDialog customDialog = new CustomDialog(this,
					new OnCustomDialogListener() {
						public void back(byte flag) {
							Intent intent = new Intent();
							if (flag == 1) {
								intent.setClass(getApplicationContext(),
										LoginActivity.class);
							} else if (flag == 2) {
								intent.setClass(getApplicationContext(),
										SetupActivity.class);
							} else if (flag == 3) {
								intent.setClass(getApplicationContext(),
										ReviseActivity.class);
							}
							MainActivity.this.startActivity(intent);
						}
					});
			customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			customDialog.show();
		}

	}

}
