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
public final class PTZController {

    private SerialPort serialPort;
    private String portaCOM;
    CommPortIdentifier portas = null;
    String left, right, up, down;

    public PTZController() {
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

                if (portas.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                    System.out.println("Serial USB Port: " + portas.getName());
                } else if (portas.getPortType() == CommPortIdentifier.PORT_PARALLEL) {
                    System.out.println("Parallel Port: " + portas.getName());
                } else {
                    System.out.println("Unknown Port: " + portas.getName());
                }

                portaCOM = portas.getName();

            }
            System.out.println("Porta escolhida =" + portaCOM);
        } catch (Exception e) {
            System.out.println("*****Erro ao escolher a porta******");
        }
    }

    public int left(int graus) {
        System.out.println("LIVIA: "+graus);
        if (graus < 10) {
            left = "!00" + graus + "L*";
            System.out.println("LEFT < 10 = " + left);
            serialPort.enviaDados(left);

        } else if (graus >= 10 && graus < 100) {
            left = "!0" + graus + "L*";
            System.out.println("LEFT >= 10 and < 100 = " + left);
            serialPort.enviaDados(left);

        } else {
            left = "!" + graus + "L*";
            System.out.println("LEFT > 100 = " + left);
            serialPort.enviaDados(left);
        }        
        return 1;
    }
}
