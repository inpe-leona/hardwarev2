/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.leona.hardware.image;



import br.leona.hardware.file.FileXML;
import br.leona.hardware.model.Servico;
import javax.media.MediaLocator;

/**
 *
 * @author Leona
 */
public class CameraController {
    private boolean captureFlag;
    private final String pathImage;
    private final String fileName;
    private final Servico servico;
    private final FileXML fileXML;
    private final int port;// = 1235;    
    private static CaptureGrabber captureGrabber;
    private static TransmitRTP transmitRTP;
    
    public CameraController(int port){
        pathImage = "c:\\ProjetoLeona\\Evento_";
        fileName = "c:/ProjetoLeona/camera.xml";
        this.port = port;
        servico = new Servico();
        servico.setName("camera");
        servico.setStatus(0);
        captureGrabber = new CaptureGrabber(pathImage);  
        fileXML = new FileXML();
        fileXML.writeFile(fileName, servico); 
        captureFlag = false;        
    }
    
    public void transmit(){
        MediaLocator mediaLocator = new MediaLocator("vfw://0");
        if(mediaLocator != null) {
            servico.setStatus(1);
            fileXML.writeFile(fileName, servico); 
        }
        (transmitRTP = new TransmitRTP(port, mediaLocator)).execute();
        System.err.println("CameraController:Transmit()");
    }
    
    public void stopTransmit(){
        System.err.println("CameraController:Cancel()");
        servico.setStatus(0);
        fileXML.writeFile(fileName, servico); 
        transmitRTP.cancel(true);
        transmitRTP = null;
    }
    
    public void capture(){  
        captureGrabber.createDirectory();
        if(captureFlag == false) {
            captureGrabber.start();
            captureFlag = true;
        }
        else captureGrabber.resume();
    }
    
    public void stopCapture(){
        System.out.println("***************************************ok******************************************");
        captureGrabber.suspend();
    }    
    
     public void endCapture(){
        servico.setStatus(0);
        fileXML.writeFile(fileName, servico); 
        captureGrabber.endCapture();
        captureGrabber = null;  
    }    
    
}

