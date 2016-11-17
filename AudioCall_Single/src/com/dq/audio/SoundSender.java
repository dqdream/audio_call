package com.dq.audio;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

public class SoundSender extends UDPSender implements Runnable {

//	private TargetDataLine line;
	private int bufferLength;
	private Thread thread;
	private boolean isStart;
	AudioRecord mAudioRecord;

	public SoundSender(String groupAddress, int port,int bufferLength) {
		super(groupAddress, port);
		this.bufferLength=bufferLength;
		mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, 8000,
				AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT,
				bufferLength);
		isStart = false;
	}

	public void run() {
		mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, 8000,
				AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT,
				bufferLength);
		mAudioRecord.startRecording();
		while (isStart && !thread.isInterrupted()) {
			byte[] buffer = new byte[bufferLength + 1];
//			line.read(buffer, 0, buffer.length);// 接受麦的数据写入buffer
			int i=mAudioRecord.read(buffer, 0, bufferLength + 1);
			send(buffer);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		mAudioRecord.stop();
		mAudioRecord.release();
		mAudioRecord = null;
	}

	public void start() {
		if (thread == null || !thread.isAlive()) {
			thread = new Thread(this);
//			line.start();
			thread.start();
			isStart = true;
		}
	}

	public void stop() {
//		line.stop();
		isStart = false;
		close();
	}

}