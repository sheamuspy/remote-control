/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package remote.control;

import controllers.KeyBoardController;
import models.Server;
import views.Input;

/**
 *
 * @author sheamus
 */
public class RemoteControl extends Thread{
    public static KeyBoardController kbc = null;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
//        new RemoteControl().start();
        Input i = new Input();
//        kbc = new KeyBoardController(i);
//        kbc.initialize();
        Server server = new Server(i);
        server.initialize();
        server.serve();
    }
    
    @Override
    public void run(){
    }
}
