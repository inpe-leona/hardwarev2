/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.leona.hardware.controller;

import gnu.io.CommPortIdentifier;
import java.util.Enumeration;

/**
 *
 * @author livia.miura
 */
public class PTZControl {

    private SerialPort serialPort;
    CommPortIdentifier portas = null;
    private String portaCOM;

    public PTZControl() {
        searchPorts();
        serialPort = new SerialPort(portaCOM, 9600);
    }

    /*
     *
     * Buscar todas as USB Serial Port
     *
     */
    public void searchPorts() {
        try {
            Enumeration pList = CommPortIdentifier.getPortIdentifiers();
            System.out.println("Porta =: " + pList.hasMoreElements());

            while (pList.hasMoreElements()) {
                portas = (CommPortIdentifier) pList.nextElement();

                System.out.println("Portas " + portas.getName() + " ");
                portaCOM = portas.getName();

            }
            System.out.println("Porta escolhida =" + portaCOM);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*
     *by Jean "modelo"
     */

    public void moveDirection(String degrees, String direction) {
        System.out.println("Pantilt.moveDirection(String graus, String direcao)");
        int typedDegrees = Integer.parseInt(degrees);
        System.out.println("Graus = " + typedDegrees);

        if (direction.equals("LEFT")) {
            moveLeft(typedDegrees, degrees);
       
        }
    }

    public void moveLeft(int typedDegrees, String degrees) {
        System.out.println("Pantilt.moveLeft(String direcao, int grausDigitado, String graus)");
        String left;
        if (typedDegrees < 10) {
            left = "!00" + degrees + "L*";
            System.out.println("LEFT < 10 = " + left);
            serialPort.enviaDados(left);
        } else if (typedDegrees >= 10 && typedDegrees < 100) {
            left = "!0" + degrees + "L*";
            System.out.println("LEFT >= 10 and < 100 = " + left);
            serialPort.enviaDados(left);
        } else {
            left = "!" + degrees + "L*";
            System.out.println("LEFT > 100 = " + left);
            serialPort.enviaDados(left);
        }
    }
}
