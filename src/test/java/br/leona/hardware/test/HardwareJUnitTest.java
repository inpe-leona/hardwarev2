/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.leona.hardware.test;

import br.leona.hardware.camera.CameraController;
import br.leona.hardware.controller.PTZController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.CannotRealizeException;
import javax.media.NoPlayerException;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author livia.miura
 */
public class HardwareJUnitTest {
    private static PTZController ptzController;
    private static CameraController  cameraController;
    private int valor = 0;
   

    public HardwareJUnitTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        ptzController = new PTZController();
        cameraController = new CameraController();
        try {
            cameraController.iniciarVideo();
        } catch (IOException | NoPlayerException | CannotRealizeException ex) {
            Logger.getLogger(HardwareJUnitTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @AfterClass
    public static void tearDownClass() {        
       
    }

    @Before
    public void setUp() throws IOException, NoPlayerException, CannotRealizeException {
        ptzController.cameraOn();       
        cameraController.iniciarCaptura();
        try {
            Thread.sleep(1000);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }       
    }

    @After
    public void tearDown() throws IOException, NoPlayerException, CannotRealizeException {
        cameraController.pararCaptura();
      //  ptzController.cameraOff();
        try {
            ptzController.reset();
        } catch (InterruptedException ex) {
            Logger.getLogger(HardwareJUnitTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertEquals(1, ptzController.close());        
    }
    
   // @Test
    public void calculo() { 
    int  graus = 22;
        assertEquals(1, ptzController.calculoAzimuteElevacao(graus,"azimute"));
    }
    
    //@Test
    public void camera()  { // recebe 1 ou 0
        int x = 1;
    //    try {
      //      assertEquals(1, ptzController.camera(x));
   //     } catch (InterruptedException ex) {
         //   Logger.getLogger(HardwareJUnitTest.class.getName()).log(Level.SEVERE, null, ex);
   //     }
    }
    
    ///@Test
    public void cameraOn(){
        assertEquals(0, ptzController.cameraOn());
    }

    //@Test
    public void cameraOff(){
        assertEquals(0, ptzController.cameraOff());
    }
    
   //@Test
    public void left() {
        int graus = 60;
        try {
            assertEquals(1, ptzController.left(graus));
        } catch (InterruptedException ex) {
            Logger.getLogger(HardwareJUnitTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //@Test
    public void right() {
        int graus = 60;
        try {
            assertEquals(1, ptzController.right(graus));
        } catch (InterruptedException ex) {
            Logger.getLogger(HardwareJUnitTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   // @Test
    public void up() {
        int graus = 30;
        try {
            assertEquals(1, ptzController.up(graus));
        } catch (InterruptedException ex) {
            Logger.getLogger(HardwareJUnitTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   // @Test
    public void down() {
        int graus = 30;
        try {
            assertEquals(1, ptzController.down(graus));
        } catch (InterruptedException ex) {
            Logger.getLogger(HardwareJUnitTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //@Test
    public void reset() {
        try {
            assertEquals(1, ptzController.resetPantilt());
        } catch (InterruptedException ex) {
            Logger.getLogger(HardwareJUnitTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //@Test
    public void recebe()  { 
        try {
            assertEquals(1, ptzController.recebeDados());
        } catch (InterruptedException ex) {
            Logger.getLogger(HardwareJUnitTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //@Test
    public void close() { // recebe 1 ou 0
        assertEquals(1, ptzController.close());
    }
    
    @Test
    public void iniciarCaptura() throws InterruptedException, IOException, NoPlayerException, CannotRealizeException { 
       
          assertEquals(1, cameraController.iniciarCaptura());
    }
        
}
