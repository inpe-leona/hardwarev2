package br.leona.hardware.camera;

import java.io.IOException;
import javax.media.CannotRealizeException;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NoPlayerException;
import javax.media.Player;

/**
 *
 * @author Leona
 */
public final class CameraController {

    Player player;
    Capturar capturar;

  

    public void iniciarCamera() throws IOException, NoPlayerException, CannotRealizeException {

        MediaLocator m1 = new MediaLocator("vfw://0");
        
         // para pensar!!!
      /*  transmissor = new Transmitter(new MediaLocator("vfw://0"),
                                              hostAddress, 1235, 
                                              new Format(VideoFormat.JPEG));*/
        
        
        player = Manager.createRealizedPlayer(m1);
        //  this.tbcamera.add(player.getVisualComponent());
        player.start();
        System.out.println("");

    }

    public void iniciarCaptura() {
        System.out.println("**************Inicia de capturar imagens**********");
        capturar = new Capturar(this);
        capturar.start(); 
    }
    
    public void pararCaptura() {       
        capturar.stop();
        System.out.println("**************Cancela de capturar imagens**********");     
    }
    
   } 

