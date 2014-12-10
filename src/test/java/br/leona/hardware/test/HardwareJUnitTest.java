/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.leona.hardware.test;

import br.leona.hardware.controller.PTZController;
import br.leona.hardware.controller.SerialPort;
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
    private static SerialPort serialPort;

    public HardwareJUnitTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        ptzController = new PTZController();
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    
     @Test
    public void calculo() { 
        
    int  graus = 22;
        assertEquals(1, ptzController.calculoAzimuteElevacao(graus,"azimute"));
    }
    
    //@Test
    public void camera() { // recebe 1 ou 0
        int x = 0;
        assertEquals(1, ptzController.camera(x));
    }

   // @Test
    public void left() {
        int graus = 19;
        assertEquals(1, ptzController.left(graus));
    }

 //  @Test
    public void right() {
        int graus = 29;
        assertEquals(1, ptzController.right(graus));
    }

   // @Test
    public void up() {
        int graus = 9;
        assertEquals(1, ptzController.up(graus));
    }

  //  @Test
    public void down() {
        int graus = 5;
        assertEquals(1, ptzController.down(graus));
    }

   // @Test
    public void reset() {
        assertEquals(1, ptzController.reset());
    }

    //@Test
    public void close() { // recebe 1 ou 0
        assertEquals(1, ptzController.close());
    }

}
