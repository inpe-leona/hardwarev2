/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.leona.hardware.controller;

import br.leona.hardware.model.Servico;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author leona
 */
public class SerialPort {   
    private final Servico servico;
    private int taxa;
    private String portaCOM;
    private OutputStream serialOut;    
    private InputStream serialIn;
    
    public SerialPort() {
        servico = new Servico();
        servico.setName("pantilt");
        servico.setStatus(0);
        initialize();
    }
    
    public SerialPort(String portaCOM, int taxa) {        
        servico = new Servico();
        servico.setName("pantilt");
        servico.setStatus(0);
        this.portaCOM = portaCOM;
        this.taxa = taxa;
        initialize();
    }
        
    public Servico getServico() {
        return servico;
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
    public int close() {
        try {
            serialOut.close();
            return 1;
        } catch (IOException e) {
            System.out.println("Não foi possível fechar porta COM");
            return 0;
        }
    }

    /*
     * Converter para bytes para enviar dados para porta serial
     */
    public int enviaDados(String opcao) {
        try {
            byte[] bytes = opcao.getBytes();
            serialOut.write(bytes);
            return 1;
        } catch (IOException ex) {
            System.out.println("Não foi possível enviar os dados para porta serial." + ex);
            return 0;
        }
    }
    
    /*
     *Recebe o status do Arduino se está ativo ou inativo
     */    
    public int recebeDados() {
        while (true) {
            try {
                serialOut.write('!'); // enviamos um ! de status
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(SerialPort.class.getName()).log(Level.SEVERE, null, ex);
                }
                int status = serialIn.read(); // e retorna status
                if (status == -1) {
                    System.out.println(" ******************");
                    System.out.println(" Arduino Inativo!  " + status);
                    System.out.println(status);
                    System.out.println(" ******************");
                    servico.setStatus(0);
                    return 0;
                } else {
                    System.out.println(" ******************");
                    System.out.println("Arduino Ativo!");
                    System.out.println(status);
                    System.out.println(" ******************");
                    servico.setStatus(1);
                    return 1;
                }
            } catch (IOException ex) {
                System.out.println("Não foi possível receber os dados para porta serial." + ex);
                return 0;
            }
        }
    }

}