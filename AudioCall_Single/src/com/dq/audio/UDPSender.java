package com.dq.audio;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPSender {

	protected InetAddress group;
	protected int port;
	protected DatagramPacket dgp;
	protected DatagramSocket datagramSocket;

	public UDPSender(String groupAddress, int port) {
		this.port = port;
		try {
			datagramSocket = new DatagramSocket();
			group = InetAddress.getByName(groupAddress);
		} catch (IOException e) {
			e.printStackTrace();
		}
		dgp = new DatagramPacket(new byte[0], 0, group, port);
	}

	public void close() {
		if (datagramSocket != null)
			datagramSocket.close();
	}

	protected void send(byte[] buffer) {
		dgp.setData(buffer);
		dgp.setLength(buffer.length);
		try {
			datagramSocket.send(dgp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}