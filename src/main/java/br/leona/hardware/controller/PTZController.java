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
 * @author leona
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
     * Buscar todas as USB Serial Port
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

    /*
     *Move para Esquerda
     */
    public int left(int graus)  throws InterruptedException {
        if (graus < 270) { //limite de elevação 270º
            if (graus < 10) {
                left = "!00" + graus + "L*";
                System.out.println("LEFT = " + left);
                serialPort.enviaDados(left);
            } else if (graus >= 10 && graus < 100) {
                left = "!0" + graus + "L*";
                System.out.println("LEFT = " + left);
                serialPort.enviaDados(left);
            } else {
                left = "!" + graus + "L*";
                System.out.println("LEFT = " + left);
                serialPort.enviaDados(left);
            }
        } else {
            System.out.println(" EXCEDE O LIMITE DE AZIMUTE PERMITIDO");
        }
        return 1;
    }

    /*
     *Move para Direita
     */
    public int right(int graus) throws InterruptedException  {
        if (graus < 270) { //limite de elevação 270º
            if (graus < 10) {
                String right1 = "!00" + graus + "R*";
                System.out.println("RIGHT = " + right1);
                serialPort.enviaDados(right1);
            } else if (graus >= 10 && graus < 100) {
                String right2 = "!0" + graus + "R*";
                System.out.println("RIGHT = " + right2);
                serialPort.enviaDados(right2);
            } else {
                String right3 = "!" + graus + "R*";
                System.out.println("RIGHT = " + right3);
                serialPort.enviaDados(right3);
            }
        } else {
            System.out.println(" EXCEDE O LIMITE DE AZIMUTE PERMITIDO");
        }
        return 1;
    }

    /*
     *Move para Cima
     */
    public int up(int graus) throws InterruptedException {
        if (graus < 85) { //limite de elevação 85º
            if (graus < 10) {
                String up1 = "!00" + graus + "U*";
                System.out.println("UP = " + up1);
                serialPort.enviaDados(up1);
            } else {
                String up2 = "!0" + graus + "U*";
                System.out.println("UP = " + up2);
                serialPort.enviaDados(up2);
            }
        } else {
            System.out.println(" EXCEDE O LIMITE DE ELEVAÇÃO PERMITIDO");
        }
        return 1;
    }

    /*
     *Move para Baixo
     */
    public int down(int graus) throws InterruptedException {
        if (graus < 85) { //limite de elevação 85º
            if (graus < 10) {
                String x3 = "!00" + graus + "D*";
                System.out.println("DOWN = " + x3);
                serialPort.enviaDados(x3);
            } else {
                String x3 = "!0" + graus + "D*";
                System.out.println("DOWN = " + x3);
                serialPort.enviaDados(x3);
            }
        } else {
            System.out.println(" EXCEDE O LIMITE DE ELEVAÇÃO PERMITIDO");
        }
        return 1;

    }

    /*
     *Liga e desliga a camera
     */
    public int camera(int valor) throws InterruptedException {
        System.out.println("camera");
        if (valor == 1) {
            serialPort.enviaDados("!111O*");//camera ON
        } else {
            serialPort.enviaDados("!111F*"); //camera OFF
        }
        return 1;
    }

    /*
     *Reset o pantilt para 0º e camera (a definir a posição) para Posição Inicial
     */
    public int reset() throws InterruptedException {
        System.out.println("Reset");
        serialPort.enviaDados("!111S*");
        return 1;
    }

    /*
     * Chamada do método que fecha a comunicação com a porta serial
     */
    public int close() {
        System.out.println("close");
        serialPort.close();
        return 1;
    }
public int recebe() throws InterruptedException {
    
        serialPort.recebeDados();
        return 1;
    }
}
