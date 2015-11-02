/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;

/**
 *
 * @author sheamus
 */
public class Event implements Serializable {
    
    /*
    eventType
    0=mouse moved
    1=mouse clicked
    2=key clicked
    3=terminal command
    4=screen shot
    */
    public int eventType;
    public int mouseX;
    public int mouseY;
    public int mouseCode;
    public int mouseClickCount;
    public int keyCode;
    public String terminalCmd;
    
    public Event(){
        eventType = -1;
        mouseX = -1;
        mouseY = -1;
        mouseCode = -1;
        keyCode = -1;
        terminalCmd = "";
    }
}
