/**
 * 
 */
package bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * TODO TimeClient
 * @author suns sontion@yeah.net
 * @since 2017年5月9日下午2:28:18
 */
public class TimeClient {
    public static void main(String[] args) {
        int port = 8081;
        if(args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (Exception e) {
            }
        }
    
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        
        try {
            socket = new Socket("127.0.0.1", port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            
            out.println("QUERY TIME ORDER");
            System.out.println("Send server succeed.");
            
            String resp = in.readLine();
            System.out.println("Now is " + resp);
        } catch (Exception e) {
        } finally {
            if(out != null) {
                out.close();
            }
            if(in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // TODO 自动生成的 catch 块
                    e.printStackTrace();
                }
            }
            if(socket != null) {
                try {
                    socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
