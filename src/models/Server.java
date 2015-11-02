/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author sheamus
 */
public class Server {
    
//    public static Event curEvent;
    
        public static void main(String[] args) {
            
        try {
            
            ServerSocket sock = new ServerSocket(6013);
            
            while(true) {
                
                Socket client = sock.accept();
                OutputStream os = client.getOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(os);
                Event curEvent = new Event();
                curEvent.eventType = 4;
                
                if(curEvent.eventType == 4){
                    getScreenshot();
                }
                oos.writeObject(curEvent);
                
                oos.close();
                
                client.close();
            }
        }
        catch (IOException ioe) {
            System.err.println(ioe);
        }
    }
        public static void getScreenshot(){
            ObjectInputStream ois = null;
            Socket socket = null;
            java.util.Date date = null;
            try {
                socket = new Socket("localhost", 6013);
                ois = new ObjectInputStream(socket.getInputStream());
        
                int sz=(Integer )ois.readObject();
                System.out.println ("Receving "+(sz/1024)+" Bytes From Sever");
         
                byte b[]=new byte [sz];
                int bytesRead = ois.read(b, 0, b.length);
                for (int i = 0; i<sz; i++)
                {
                    System.out.print(b[i]);
                }
                FileOutputStream fos=new FileOutputStream(new File("demo.jpg"));
                fos.write(b,0,b.length);
                System.out.println ("From Server : "+ois.readObject());
                ois.close();
            } catch(Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
}
