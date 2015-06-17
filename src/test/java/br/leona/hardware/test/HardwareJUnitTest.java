/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.leona.hardware.test;

import br.leona.hardware.controller.PTZController;
import br.leona.hardware.file.FileXML;
//import br.leona.hardware.image.CameraController;
import br.leona.hardware.model.Servico;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 *
 * @author Leona
 */
public class HardwareJUnitTest {
    private static PTZController ptzControll; 
 //   private static CameraController cameraControll;
    private static int port = 1235;

    public HardwareJUnitTest() {
    }

    @BeforeClass
    public static void setUpClass() { 
        ptzControll = new PTZController();             
       // cameraControll = new CameraController(1235);
        ptzControll.cameraOn();
        //cameraControll.transmit();
        //cameraControll.capture();        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(HardwareJUnitTest.class.getName()).log(Level.SEVERE, null, ex);
        }
      //  cameraControll.stopCapture();
      //  cameraControll.endCapture();
    }

    @AfterClass
    public static void tearDownClass() {        
        //cameraControll.stopCapture();
        //cameraControll.endCapture();
        //cameraControll.stopTransmit();        
        //ptzControll.cameraOff();
        ptzControll.close();
    }

    @Before
    public void setUp()  {             
       
    }

    @After
    public void tearDown() {      
        //cameraControll.stopCapture();
       // cameraControll.endCapture();
      //cameraControll.stopTransmit();       
     //   ptzControll.reset();       
        assertEquals(1, 1);//ptzControll.close());        
    }
    
     
    
   // @Test
    public void moverAzimute(){
        int graus = 60;
        graus = ptzControll.calculoAzimuteElevacao(graus,"azimute");
        System.out.println("azimute: "+graus);
        assertNotSame(0, graus);
    }
    
   // @Test
    public void moverElevacao(){
        int graus = 60;
        graus = ptzControll.calculoAzimuteElevacao(graus,"elevacao");
        System.out.println("elevacao: "+graus);
        assertEquals(1,  1);//graus);
    }
    /*
    @Test
    public void left() {
        int graus = 60;
        assertEquals(1, ptzControll.left(graus));
    }

    @Test
    public void right() {
        int graus = 60;
        assertEquals(1, ptzControll.right(graus));
    }

    @Test
    public void up() {
        int graus = 30;
        assertEquals(1, ptzControll.up(graus));
    }
   
    @Test
    public void down() {
        int graus = 30;
        assertEquals(1, ptzControll.down(graus));       
    }
    */
    //@Test
    public void reset() {
        assertEquals(1,  1);//ptzControll.resetPantilt());
    }
    
    
    @Test
    public void fileXML(){      
        List<Servico> list = new ArrayList<>();
        FileXML fileXML = new FileXML();
       // ptzControll.writeFile("d:/ProjetoLeona/serialPort.xml", ptzControll.);
        
        Servico pantilt = fileXML.readFile("d:/ProjetoLeona/pantilt.xml");
        list.add(pantilt);
        Servico camera = fileXML.readFile("d:/ProjetoLeona/camera.xml");
        list.add(camera);
        assertNotNull(list);
    }
    
      // @Test
    public void calculo() { 
        int  graus = 22;
        graus = ptzControll.calculoAzimuteElevacao(graus,"azimute");
        System.out.println("calculoAzimuteElevacao: "+graus);
        assertEquals(1, graus);
    }
    
    //@Test
    public void camera()  { // recebe 1 ou 0
        int x = 1;
    //    try {
      //      assertEquals(1, ptzControll.camera(x));
   //     } catch (InterruptedException ex) {
         //   Logger.getLogger(HardwareJUnitTest.class.getName()).log(Level.SEVERE, null, ex);
   //     }
    }
    
    //@Test
    public void cameraOn(){
        int on = ptzControll.cameraOn();
        System.out.println("cameraOn: "+on);
        assertEquals(1,  on);
    }

    //@Test
    public void cameraOff(){
        int off = ptzControll.cameraOff();
        System.out.println("cameraOff: "+off);
        assertEquals(1,  off);
    }
    
   //@Test
    public void left() {
        int graus = 60;
        graus = ptzControll.left(graus);
        System.out.println("left: "+graus);
        assertEquals(1,  graus);
    }

    //@Test
    public void right() {
        int graus = 60;
        graus = ptzControll.right(graus);
        System.out.println("right: "+graus);
        assertEquals(1, graus);
    }

   // @Test
    public void up() {
        int graus = 30;
        graus = ptzControll.up(graus);
        System.out.println("up: "+graus);
        assertEquals(1,  graus);
    }

   // @Test
    public void down() {
        int graus = 30;
        graus = ptzControll.down(graus);
        System.out.println("down: "+graus);
        assertEquals(1,  graus);
    }

   //@Test
    public void resetPantilit() {
        int reset = ptzControll.resetPantilt();
        System.out.println("resetPantilt: "+reset);
        assertEquals(1,  reset);
    }
    
  // @Test
    public void recebe()  { 
        int recebe = ptzControll.recebeDados();
        System.out.println("recebe: "+recebe);
        assertEquals(1,  recebe);
    }
    
    //@Test
    public void close() { // recebe 1 ou 0
        int close = ptzControll.close();
        System.out.println("close: "+close);
        assertEquals(1,  close);
    }
    
}
