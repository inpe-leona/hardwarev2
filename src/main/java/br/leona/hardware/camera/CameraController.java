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
    

    public void iniciarVideo() throws IOException, NoPlayerException, CannotRealizeException {

        MediaLocator m1 = new MediaLocator("vfw://0");
        if (m1==null){
            System.out.println("Aqui chegou null");
        }
        player = Manager.createRealizedPlayer(m1);
        player.start();
               System.out.println("hw- iniciarVideo");
    }

    public void pararVideo() throws IOException, NoPlayerException, CannotRealizeException {
      
        player.stop();
                System.out.println("hw- pararVideo");
    }

    public void iniciarCaptura() throws IOException, NoPlayerException, CannotRealizeException {
        capturar = new Capturar(this);
        capturar.start();
            System.out.println("hw- iniciarCaptura");
    }

    public void pararCaptura() throws IOException, NoPlayerException, CannotRealizeException {
        
        capturar.stop();
        System.out.println("hw- pararCaptura");
    }

}
