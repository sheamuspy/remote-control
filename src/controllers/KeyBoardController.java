/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import javax.swing.event.MouseInputListener;
import models.Event;
import views.Input;

/**
 *
 * @author sheamus
 */
public class KeyBoardController{
    public static Input input;
    public static KeyListener keyListener;
    public static Event event;

    public KeyBoardController(Input in){
        this.input = in;
    }

    public void initialize(){
       keyListener = new KeyListener() {

           @Override
           public void keyTyped(KeyEvent e) {}

           @Override
           public void keyPressed(KeyEvent e) {
               event = new Event();
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
               
               System.out.println("The keypressed is: " + keycode);
           }

           @Override
           public void keyReleased(KeyEvent e) {}
       };
       
       input.getTextArea().addKeyListener(keyListener);
    }
    
    public static Event getKeyboard(){
        return event;
    }
}
