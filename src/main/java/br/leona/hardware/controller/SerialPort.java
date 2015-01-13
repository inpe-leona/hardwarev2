/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.leona.hardware.controller;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author leona
 */
@XmlRootElement
@XmlType(propOrder = { "portaCOM", "taxa", "status" })
public class SerialPort {    
    private int taxa;
    private int status;
    private String portaCOM;
    private OutputStream serialOut;    
    private InputStream serialIn;

    
    public SerialPort() {
        portaCOM = "COM4";
        taxa = 9600;
        initialize();
    }
    
    public SerialPort(String portaCOM, int taxa) {
        this.portaCOM = portaCOM;
        this.taxa = taxa;
        initialize();
    }
    
    @XmlElement(name = "taxa")
    public int getTaxa() {
        return taxa;
    }

    public void setTaxa(int taxa) {
        this.taxa = taxa;
    }

    @XmlElement(name = "status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    @XmlElement(name = "potaCOM")
    public String getPortaCOM() {
        return portaCOM;
    }

    public void setPortaCOM(String portaCOM) {
        this.portaCOM = portaCOM;
    }

    /**
     * Método que verifica se a comunicação com a porta serial está ok
     */
    private void initialize() {
        try {
            CommPortIdentifier porta = null;
            try {
                porta = CommPortIdentifier.getPortIdentifier(portaCOM);
            } catch (NoSuchPortException e) {
                System.out.println("!!!!!!!!!!! Nehuma Porta Encontrada!!!!!!!!!!!" + e);
            }
            //Abre a porta COM 
            gnu.io.SerialPort port = (gnu.io.SerialPort) porta.open("Comunicação serial", this.taxa);
            serialOut = port.getOutputStream(); // saida java
            serialIn = port.getInputStream(); // entrada java

            port.setSerialPortParams(this.taxa, //taxa de transferência da porta serial 
                    gnu.io.SerialPort.DATABITS_8, //taxa de 10 bits 8 (envio)
                    gnu.io.SerialPort.STOPBITS_1, //taxa de 10 bits 1 (recebimento)
                    gnu.io.SerialPort.PARITY_NONE
            ); //receber e enviar dados

        } catch (Exception e) {
            System.out.println("Erro ao inicializar no SerialPort" + e);
        }
    }

    /**
     * Método que fecha a comunicação com a porta serial
     */
    public void close() {
        try {
            serialOut.close();
        } catch (IOException e) {
            System.out.println("Não foi possível fechar porta COM");

        }
    }

    /*
     * Converter para bytes para enviar dados para porta serial
     */
    public int enviaDados(String opcao) throws InterruptedException {

        try {
            byte[] bytes = opcao.getBytes();
            serialOut.write(bytes);

        } catch (IOException ex) {
            System.out.println("Não foi possível enviar os dados para porta serial." + ex);
        }
        return 0;
    }
    
    /*
     *Recebe o status do Arduino se está ativo ou inativo
     */    
    public int recebeDados() throws InterruptedException {

        while (true) {
            try {
                serialOut.write('!'); // enviamos um ! de status
                Thread.sleep(100);
                status = serialIn.read(); // e retorna status

                if (status == -1) {
                    System.out.println(" ******************");
                    System.out.println(" Arduino Inativo!  " + status);

                    System.out.println(" ******************");
                } else {
                    System.out.println(" ******************");
                    System.out.println("Arduino Ativo!");
                    System.out.println(status);
                    System.out.println(" ******************");
                }
            } catch (IOException ex) {
                System.out.println("Não foi possível receber os dados para porta serial." + ex);
            }
            return 0;
        }

    }

}
