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
public class RemoteControl {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Server server = new Server();
        Input i = new Input();
        KeyBoardController kbc = new KeyBoardController(i);
        kbc.initialize();
    }
    
}
