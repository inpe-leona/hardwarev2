/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.leona.hardware.controller;

import br.leona.hardware.model.Servico;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.SerialPort;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author leona
 */
public class SerialPortIO {   
    private final Servico servico;
    private int taxa;
    private String portaCOM;
    private OutputStream serialOut;  
    private SerialPort serialPort;
    private static BufferedReader input;
    private InputStream serialIn;
    
    public SerialPortIO() {
        servico = new Servico();
        servico.setName("pantilt");
        servico.setStatus(0);
        initialize();
    }
    
    public SerialPortIO(String portaCOM, int taxa) {        
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
            //gnu.io.SerialPort port 
            serialPort = (gnu.io.SerialPort) porta.open("Comunicação serial", this.taxa);
            serialOut = //port
                    serialPort.getOutputStream(); // saida java
            serialIn = //port
                    serialPort.getInputStream(); // entrada java

            //port
            serialPort.setSerialPortParams(this.taxa, //taxa de transferência da porta serial 
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
                    Logger.getLogger(SerialPortIO.class.getName()).log(Level.SEVERE, null, ex);
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
    /*
     *Recebe o status do Arduino se está ativo ou inativo
     */    
    public String receberCoordXYZ() {
        System.out.println("receberCoordXYZ()");  
        while (true) {   
            try {
                serialOut.write('?'); // enviamos um ! de status       
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(SerialPortIO.class.getName()).log(Level.SEVERE, null, ex);
                }    
                input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));                 
                String coordXYZ = input.readLine();
                System.out.println("lenght CoordXYZ: "+coordXYZ.length());
               
                System.out.println("ultmo caracter: "+coordXYZ.charAt(coordXYZ.length()-1));
                System.out.println(" ******************");
                System.out.println("Hr CoordXYZ:" + coordXYZ+"_"); 
                System.out.println(" ******************");
                input.close();
                int lenght = coordXYZ.length();
                
                while(!isNumber(coordXYZ.charAt(lenght-1))) {                    
                   coordXYZ = coordXYZ.substring(0, coordXYZ.length()-1);
                   System.out.println("substring CoordXYZ:" + coordXYZ+"_"); 
                   lenght = coordXYZ.length();
                }
                return coordXYZ;
            } catch (IOException ex) {
                System.out.println("Não foi possível receber as coord XYZ da porta serial."); 
                System.out.println(ex);
                return "-999 -999 -999";
            }        
        } 
    }
        
    private boolean isNumber(char charAt) {
        System.out.println("charAt:_"+charAt+"_"); 
        if(charAt=='0'
        || charAt=='1'
        || charAt=='2'
        || charAt=='3'
        || charAt=='4'
        || charAt=='5'
        || charAt=='6'
        || charAt=='7'
        || charAt=='8'
        || charAt=='9') return true;
        else return false;
    }
}