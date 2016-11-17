package com.dq.audio;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import android.util.Log;

public class UDPReceiver{  
      
     
    protected byte[] buffer;  
    protected DatagramPacket dgp;  
    protected int port;  
    protected int bufferSize;  
    protected DatagramSocket datagramSocket;
    
    public UDPReceiver(int port, int bufferSize){  
        try {  
        	datagramSocket=new DatagramSocket(port);
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        buffer = new byte[bufferSize];  
        dgp = new DatagramPacket (buffer, bufferSize);  
        this.port = port;  
        this.bufferSize = bufferSize;  
    }  
      
    public void close(){  
        if(datagramSocket != null){  
            datagramSocket.close();  
        }  
    }  
      
//    public void contect(){  
//        if(isClose){  
//            try {  
//                s = new MulticastSocket(port);  
//                s.joinGroup (group);  
//            } catch (IOException e) {  
//                e.printStackTrace();  
//            }  
//            buffer = new byte[bufferSize];  
//            dgp = new DatagramPacket (buffer, bufferSize);  
//            isClose = false;  
//        }  
//    }  
      
    protected byte[] receive(){  
        try {  
        	datagramSocket.receive (dgp);
        	Log.d("vv", "dgp"+"--"+dgp.getAddress().getHostAddress()+"--"+dgp.getAddress().getHostName());
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return buffer;  
    }  
      
    protected int getDgpLength(){  
        return dgp.getLength();  
    }  
      
    protected String getIP(){  
        return dgp.getAddress().toString();  
    }  
      
}  