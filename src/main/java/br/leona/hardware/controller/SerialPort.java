/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.leona.hardware.controller;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import java.io.IOException;
import java.io.OutputStream;
import javax.swing.JOptionPane;

/**
 *
 * @author livia.miura
 */
public class SerialPort {

    private OutputStream serialOut;
    private final int taxa;
    private final String portaCOM;
  

    /**
     *
     * @param portaCOM - Porta COM que será utilizada para enviar dados para
     * porta serial "arduino"
     * @param taxa - Taxa de transferência da porta serial geralmente é 9600
     *
     */
    public SerialPort(String portaCOM, int taxa) {
        this.portaCOM = portaCOM;
        this.taxa = taxa;
        initialize();

    }

    /**
     * Método que verifica se a comunicação com a porta serial está ok
     */
    private void initialize() {
        try {
            CommPortIdentifier porta = null;
            try {

                //Tenta verificar se a porta COM informada existe
                porta = CommPortIdentifier.getPortIdentifier(portaCOM);
                System.out.println(" Porta = " + porta.getName());

            } catch (NoSuchPortException e) {
                System.out.println("!!!!!!!!!!! Nehuma Porta Encontrada!!!!!!!!!!!" + e);
            }
            //Abre a porta COM 
            gnu.io.SerialPort port = (gnu.io.SerialPort) porta.open("Comunicação serial", this.taxa);
            serialOut = port.getOutputStream();

            port.setSerialPortParams(this.taxa, //taxa de transferência da porta serial 
                    gnu.io.SerialPort.DATABITS_8, //taxa de 10 bits 8 (envio)
                    gnu.io.SerialPort.STOPBITS_1, //taxa de 10 bits 1 (recebimento)
                    gnu.io.SerialPort.PARITY_NONE); //receber e enviar dados
        } catch (Exception e) {
        }
    }

    /**
     * Método que fecha a comunicação com a porta serial Ainda sem utilidade
     */
    public void close() {
        try {
            serialOut.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Não foi possível fechar porta COM.",
                    "Fechar porta COM", JOptionPane.PLAIN_MESSAGE);
        }
    }

    /*
     * Converter para bytes para enviar dados para porta serial
     */
    public void enviaDados(String opcao) {
        try {
            byte[] bytes = opcao.getBytes();
            serialOut.write(bytes);
        } catch (IOException ex) {
            System.out.println("Não foi possível enviar os dados para porta serial.");

        }
    }
}
