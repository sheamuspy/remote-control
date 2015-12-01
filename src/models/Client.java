/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sheamus
 */
public class Client {
    
    public static Event e;
    public static Socket sock;
    public static File file = null;
    public static ObjectOutputStream oos;
    
        public static void main(String[] args) {
            while(true){
                try {

                    sock = new Socket("localhost", 6013);

        //          Input Streams  

                    InputStream in = sock.getInputStream();

                    ObjectInputStream ois = new ObjectInputStream(in);

        //            Output streams
                    OutputStream os = sock.getOutputStream();

                    oos = new ObjectOutputStream(os);

                    try {

                        if(file!=null){

                                FileInputStream fin = new FileInputStream(file);

                                int c;
                                int sz=(int)file.length();
                                byte b[]=new byte [sz];
                                oos.writeObject(new Integer(sz));
                                oos.flush();
                                int j=0;

                                while ((c = fin.read()) != -1) {
                                    b[j]=(byte)c;
                                    j++;
                                }
                            /*for (int i = 0; i<sz; i++)
                            {
                                System.out.print(b[i]);
                            }*/
                                fin.close();
                                oos.flush();
                                oos.write(b,0,b.length);
                                oos.flush();
                                System.out.println ("Size "+sz);
                                oos.writeObject(new String("Ok"));
                                oos.flush();
                                file = null;
                        }

                        e = (Event) ois.readObject();

                        if (e!=null){
                            switch(e.eventType){
                                case 0:
                                    moveMouse();
                                    break;
                                case 1:
                                    clickMouse();
                                    break;
                                case 2:
                                    typeKey();
                                    break;
                                case 3:
                                    executeCmd();
                                    break;
                                case 4:
                                  takeScreenshot();
                                    break;
                            }                    
                        }
                    } catch (ClassNotFoundException ex) {

                    }

                    ois.close();
                    in.close();
                    sock.close();
                }
                catch (IOException ioe) {
                    System.err.println(ioe);
                }
            }
    }
        
        
        public static void moveMouse(){
            
            //System.out.println("Mouse moved");
            
            try {
                new Robot().mouseMove(e.mouseX, e.mouseY);
            } catch (AWTException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        public static void clickMouse(){
            
            System.out.println("Mouse clicked");
            
            try {
                new Robot().keyPress(e.mouseCode);
            } catch (AWTException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        public static void typeKey(){
            
            System.out.println("Key type");
            
            try {
                new Robot().keyPress(e.keyCode);
            } catch (AWTException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        public static void executeCmd(){
            
            System.out.println("Got execute command" + e.terminalCmd);
            
            Runtime rt = Runtime.getRuntime();
            try {
                rt.getRuntime().exec(e.terminalCmd);
                System.out.println("Command executed " + e.terminalCmd);
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        public static void takeScreenshot(){
            
                                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                    try {
                                        JPEGImageEncoder encoder=JPEGCodec.createJPEGEncoder(baos);
                                        encoder.encode(new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize())));
                                        oos.writeObject(baos.toByteArray());
                                    } catch (AWTException | HeadlessException | ImageFormatException | IOException throwable) {}
}
}
