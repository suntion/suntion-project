package nnio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

public class TimeServerHander implements Runnable {
	private Socket socket;
	
	
	public TimeServerHander(Socket socket) {
		this.socket = socket;
	}
	

	@Override
	public void run() {
	    BufferedReader in = null;
	    PrintWriter out = null;
	    
	    try {
            in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            out = new PrintWriter(this.socket.getOutputStream(),true);
            
            String currytime = null;
            String body = null;
            
            while(true) {
                body = in.readLine();
                if(body == null) {
                    break;
                }
                System.out.println("the time server receive order:" + body);
                
                currytime = "QUERY TIME ORDER".equalsIgnoreCase(body)?new Date(System.currentTimeMillis()).toString():"BAD ORDER";
                out.println(currytime);
            }
            
            
        } catch (IOException e) {
            if(in != null) {
                try {
                    in.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if(out != null) {
                out.close();
                out = null;
            }
            if(this.socket != null) {
                try {
                    this.socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            e.printStackTrace();
        }
	    
	    
	}

}
