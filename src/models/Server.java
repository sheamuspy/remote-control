/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sheamus
 */
public class Server {
    
//    public static Event curEvent;
    
        public static void main(String[] args) {
          while(true) {  
        try {
            
            ServerSocket sock = new ServerSocket(6013);
            
            
                
                Socket client = sock.accept();
                
//                Output streams.
                OutputStream os = client.getOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(os);
                
//                Input streams
                InputStream is = client.getInputStream();
                ObjectInputStream ois = new ObjectInputStream(is);
                                
                
                Event curEvent = new Event();
                curEvent.eventType = 4;
                
                oos.writeObject(curEvent);
                
                if(curEvent.eventType == 4){
                        int sz=(Integer)ois.readInt();
                    System.out.println ("Receving "+(sz/1024)+" Bytes From Sever");

                    byte b[]=new byte [sz];
                    int bytesRead = ois.read(b, 0, b.length);
                    for (int i = 0; i<sz; i++)
                    {
                        System.out.print(b[i]);
                    }

                    FileOutputStream fos=new FileOutputStream(new File("Desktop\\demo.jpg"));
    //                fos.write(b,0,b.length);

                    System.out.println ("From Server : "+ois.readObject());
                    ois.close();
                }
                
                oos.close();
                
                client.close();
                
            }
        
        catch (IOException ioe) {
            ioe.printStackTrace();
        }   catch (ClassNotFoundException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
        }
}
