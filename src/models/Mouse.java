/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.awt.MouseInfo;

/**
 *
 * @author sheamus
 */
public class Mouse {
    
    public static Event getMouse(){
        Event newEvent =  new Event();
        newEvent.mouseX = MouseInfo.getPointerInfo().getLocation().x ;
        newEvent.mouseY = MouseInfo.getPointerInfo().getLocation().y ;
        newEvent.eventType=0;
        return newEvent;
    }
    
}
