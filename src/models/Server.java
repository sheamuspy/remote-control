/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import views.Input;

/**
 *
 * @author sheamus
 */
public class Server {
    
    public Event keyEvent = null;
    public Input input;
    public KeyListener keyListener;
    
    
    public Server(Input input){
        this.input = input;
    }
    
    public void initialize(){
       keyListener = new KeyListener() {

           @Override
           public void keyTyped(KeyEvent e) {}

           @Override
           public void keyPressed(KeyEvent e) {
               Event event = new Event();
               int keycode;
               String OS = System.getProperty("os.name").toLowerCase();
               System.out.println(OS);
               
               if(OS.contains("mac")){
                   keycode = e.getKeyCode();
               }else{
                   keycode = e.getExtendedKeyCode();
               }
               event.keyCode = keycode;
               event.eventType = 2;
               keyEvent = event;
               
               System.out.println("The keypressed is: " + keycode);
//               System.out.println("The keypressed from key event is: " + keyEvent.keyCode);
           }

           @Override
           public void keyReleased(KeyEvent e) {}
       };
       
       input.getTextArea().addKeyListener(keyListener);
    }
    
        public void serve() {
            
            ServerSocket sock = null;
            InputStream is = null;
            Socket client = null;
            OutputStream os = null;
            ObjectOutputStream oos = null;
            ObjectInputStream ois = null;
            
            try{
                sock = new ServerSocket(6013);
            }catch(IOException e){
                System.out.println(e);
            }
        
            try {
                while(true) {
                client = sock.accept();
                
                os = client.getOutputStream();
                oos = new ObjectOutputStream(os);
                
                is = client.getInputStream();
                ois = new ObjectInputStream(is);
                                
                
                    
                    oos.writeObject(Mouse.getMouse());
                        System.out.println("The keypressed from key event is: " + keyEvent.keyCode);                    
//                    System.out.println(Mouse.getMouse());
//                    System.out.println(RemoteControl.kbc.event);
                    if(keyEvent!=null){

                        oos.writeObject(keyEvent);
                        keyEvent = null;
                    }
                    /*try{
                        int sz=(Integer)ois.readInt();
                        System.out.println ("Receving "+(sz/1024)+" Bytes From Sever");

                        byte b[]=new byte [sz];
                        int bytesRead = ois.read(b, 0, b.length);
                        for (int i = 0; i<sz; i++) {
                            System.out.print(b[i]);
                        }
                        
                        FileOutputStream fos=new FileOutputStream(new File("Desktop\\demo.jpg"));
                        
                        fos.write(b,0,b.length);
                        
                        System.out.println ("From Server : " + ois.readObject());
                        
                        
                    } catch(IOException e){
                        e.printStackTrace();
                    }*/
                    oos.close();
                    ois.close();
                    client.close();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
}
