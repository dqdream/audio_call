package com.dq.audio;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

public class SoundReceiver extends UDPReceiver implements Runnable {

	// private SourceDataLine line;
	private Thread thread;
	private boolean isStart;
	AudioTrack audioTrack;
	int bufferSizeInBytes;
	public SoundReceiver(int port, int bufferSizeInBytes) {
		super(port, bufferSizeInBytes);
		this.bufferSizeInBytes=bufferSizeInBytes;
		audioTrack = new AudioTrack(AudioManager.STREAM_VOICE_CALL, 8000,
				AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT,
				2 * bufferSizeInBytes, AudioTrack.MODE_STREAM);
		isStart = false;
	}
	
	public void run() {
		audioTrack = new AudioTrack(AudioManager.STREAM_VOICE_CALL, 8000,
				AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT,
				2 * bufferSizeInBytes, AudioTrack.MODE_STREAM);
		audioTrack.play();
		while (isStart && !thread.isInterrupted()) {
			byte[] data = super.receive();
			if (data==null) {
				continue;
			}
			int i=audioTrack.write(data, 0, data.length);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		audioTrack.stop();
		audioTrack.release();
		audioTrack = null;
	}

	public void start() {
		if (thread == null || !thread.isAlive()) {
			thread = new Thread(this);
			isStart = true;
//			contect();
			thread.start();
		}
	}

	public void stop() {
		isStart = false;
		close();
	}
}