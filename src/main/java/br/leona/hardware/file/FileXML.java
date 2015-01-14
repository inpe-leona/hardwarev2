/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.leona.hardware.file;


import br.leona.hardware.controller.SerialPort;
import br.leona.hardware.model.Servico;
import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 *
 * @author Admin_2
 */
public class FileXML {
    
    public void createFile(String fileName, SerialPort serialPort) {
        Servico servico = serialPort;        
        try {
            File file = new File(fileName);
            JAXBContext jaxbContext = JAXBContext.newInstance(Servico.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(servico, file);
            jaxbMarshaller.marshal(servico, System.out);
 
        } catch (JAXBException e) {
            e.printStackTrace();
        }     
    }
    
}
