package com.dq.audio;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioTrack;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dq.audiocall.R;

public class MainActivity extends Activity {
	EditText editText4;
	Button button1, button2,button0;
	private SoundReceiver soundReceiver;
	private SoundSender soundSender;
	private boolean isAvaile = false;
	public static int CALL_STATUS = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.audio_activity);
		init();
	}

	private void init() {
		final int bufferSizeInBytes = AudioTrack.getMinBufferSize(8000,
				AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);
		soundReceiver = new SoundReceiver(5000, bufferSizeInBytes);
		editText4 = (EditText) findViewById(R.id.editText4);
		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		button0 = (Button) findViewById(R.id.button0);
		button0.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					soundSender = new SoundSender(editText4.getText()
							.toString(), 5000, bufferSizeInBytes);
					isAvaile=true;
					Toast.makeText(MainActivity.this, "设置成功", 1000).show();
				} catch (Exception e) {
					Log.d("vv", e.getMessage()+"");
					Toast.makeText(MainActivity.this, "对方不在线", 1000).show();
				}
			}
		});
		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!isAvaile) {
					Toast.makeText(MainActivity.this, "对方不在线", 1000).show();
					return;
				}
				if (CALL_STATUS == AudioStatus.NORMAL) {
					soundSender.start();
					soundReceiver.start();
					CALL_STATUS = AudioStatus.CALLOUT;
				} else if (CALL_STATUS == AudioStatus.CALLOUT) {
					Toast.makeText(MainActivity.this, "正在通话", 1000).show();
				}

			}
		});
		button2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!isAvaile) {
					return;
				}
				if (CALL_STATUS == AudioStatus.CALLOUT) {
					soundSender.stop();
					soundReceiver.stop();
					CALL_STATUS=AudioStatus.NORMAL;
				} else {
					Toast.makeText(MainActivity.this, "未在通话", 1000).show();
				}
			}
		});

	}
}
