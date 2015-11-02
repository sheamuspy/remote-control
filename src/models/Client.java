/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author sheamus
 */
public class Client {
    public static Event e;
    public static Socket sock;
        public static void main(String[] args) {
        try {
            sock = new Socket("localhost", 6013);
            InputStream in = sock.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(in);
            try {
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
            
//            BufferedReader bin = new BufferedReader(new InputStreamReader(in));
//            
//            String line;
//            while ( (line = bin.readLine()) != null)
//                System.out.println(line);
            ois.close();
            in.close();
            sock.close();
        }
        catch (IOException ioe) {
            System.err.println(ioe);
        }
    }
        
        
        public static void moveMouse(){
            System.out.println("Mouse moved");
        try {
            new Robot().mouseMove(e.mouseX, e.mouseY);
        } catch (AWTException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        
        public static void clickMouse(){
        try {
            new Robot().keyPress(e.mouseCode);
        } catch (AWTException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        
        public static void typeKey(){
        try {
            new Robot().keyPress(e.keyCode);
        } catch (AWTException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        }

        public static void executeCmd(){
            Runtime rt = Runtime.getRuntime();
        try {
            rt.getRuntime().exec(e.terminalCmd);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        public static void takeScreenshot(){
            
            
    Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
    BufferedImage capture = null;
    File file = null;
    boolean write = false;
    while(true){
        try {
            capture = new Robot().createScreenCapture(screenRect);
        
            
            file = File.createTempFile("pic", ".jpeg");
            
            write = ImageIO.write(capture, "bmp", file);
            System.out.println("Screenshot taken");
            } catch (AWTException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
     
            
            
            try {
//            ServerSocket ss=new ServerSocket(6013);
            ServerSocket ss=new ServerSocket(6013);               
            System.out.println ("Waiting for request");
//            Socket s=ss.accept();
            Socket s = sock;
            System.out.println ("Connected With"+s.getInetAddress().toString());
        //ObjectInputStream   ois = new ObjectInputStream(s.getInputStream());
    ObjectOutputStream  oos = new ObjectOutputStream(s.getOutputStream());
         
//        String req=(String)ois.readObject();
//        System.out.println (req);
         
//        File f=new File(req);
    if(file==null){
        return;
    }
    File f = file;
        FileInputStream fin = new FileInputStream(f);
         
        int c;
        int sz=(int)f.length();
        
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
        System.out.println ("buf size"+ss.getReceiveBufferSize());
        oos.writeObject(new String("Ok"));
         oos.flush();
         ss.close();
        }
        catch (Exception ex) {
            System.out.println ("Error"+ex);
        }
            
            
        }
        }
}
