/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.event.MouseInputListener;
import models.Event;
import views.Input;

/**
 *
 * @author sheamus
 */
public class MouseController {
    MouseMotionListener mouseMotionListener;
    MouseInputListener mouseInputListener;
    Event event;
    Input input;
    
    public MouseController(Input in){
    this.input = in;
}

public void initialize(){
   
   mouseInputListener = new MouseInputListener() {

       @Override
       public void mouseClicked(MouseEvent e) {
           event = new Event();
           int numClicks = e.getClickCount();
           event.mouseClickCount = numClicks;
           
           System.out.println("Number of clicks: " + numClicks);
       }

       @Override
       public void mousePressed(MouseEvent e) {
           event = new Event();
           int mouseCode = e.getButton();
           event.mouseCode = mouseCode;
           System.out.println("Button pressed: " + mouseCode);
       }

       @Override
       public void mouseReleased(MouseEvent e) {
           //event = new Event();
           int mouseCode = e.getButton();
           
           System.out.println("Button released: " + mouseCode);
       }

       @Override
       public void mouseEntered(MouseEvent e) {}

       @Override
       public void mouseExited(MouseEvent e) {}

       @Override
       public void mouseDragged(MouseEvent e) {}

       @Override
       public void mouseMoved(MouseEvent e) {}
   };
   
   mouseMotionListener = new MouseMotionListener() {

       @Override
       public void mouseDragged(MouseEvent e) {}

       @Override
       public void mouseMoved(MouseEvent e) {
           event = new Event();
           int x = e.getXOnScreen();
           int y = e.getYOnScreen();
           event.mouseX = x;
           event.mouseY = y;
           
           System.out.println("Button x: " + x);
           System.out.println("Button y: " + y);
       }
   };
   
   input.getTextArea().addMouseListener(mouseInputListener);
   input.getTextArea().addMouseMotionListener(mouseMotionListener);
}
    
}
