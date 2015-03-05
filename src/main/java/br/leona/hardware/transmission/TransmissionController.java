package br.leona.hardware.transmission;


import java.util.Vector;
import javax.media.CaptureDeviceInfo;
import javax.media.CaptureDeviceManager;
import javax.media.MediaLocator;

/**
 *
 * @author Leona
 */
public class TransmissionController{
    private static MediaLocator mediaLocator;
    private static int port;
    private static ThreadRTP threadRTP;

    public TransmissionController(int port) {
        this.port = port;
    }
    
     public void iniciarVideo(){
        Vector deviceList = CaptureDeviceManager.getDeviceList(null); 
        System.out.println("Numero de DEVICES: "+deviceList.size());
        
        for(int i = 0; i < deviceList.size();  i++)
            System.out.println("Device"+i+": "+deviceList.get(i).toString());
        
        CaptureDeviceInfo device = new CaptureDeviceInfo("vfw://0",new MediaLocator("vfw:Microsoft WDM Image Capture (Win32):0"),null);
        System.out.println("DEVICE:"+device.toString());
        mediaLocator = device.getLocator();         
        System.out.println("MEIDALOCATOR:"+mediaLocator.toString());
    }

    
    public void iniciarTransmissao(){
        // escolhida a port do socket para a conexao TCP, 
         (threadRTP = new ThreadRTP(port, mediaLocator)).execute();
        //pode ser qualquer uma, eu tinha escolhido 1234 ao acaso.
        // esse numero especifica em qual port o servidor vai estar escutando
        //pedidos de conexao tcp de clientes.
    }
    
    public void pararTransmissao(){
        //threadRTP.cancel(true);
        threadRTP = null;
    }  
}
