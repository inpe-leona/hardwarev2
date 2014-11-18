/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.leona.hardware.test;

import br.leona.hardware.controller.PTZController;
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
    public void hello() {
        int graus = 29;
        assertEquals(1, ptzController.left(graus));
    }
}
