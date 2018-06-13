package nnio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import bio.TimeServerHander;

public class TimeServer {
	public static void main(String[] args) {
		int port = 8081;
		if(args != null && args.length > 0) {
			try {
				port = Integer.valueOf(args[0]);
			} catch (Exception e) {
			}
		}
		
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("server start at port:" + port);
			
			Socket socket = null;
			
			TimeServerHandlerExecutePool singleExecture = new TimeServerHandlerExecutePool(50, 10000);
			
			while(true) {
			    socket = serverSocket.accept();
			    singleExecture.execute(new TimeServerHander(socket));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(serverSocket != null) {
				try {
				    System.out.println("the time server close");
					serverSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
