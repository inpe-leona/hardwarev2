package br.leona.hardware.image;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.media.Format;
import javax.media.MediaLocator;
import javax.media.format.VideoFormat;
import javax.swing.SwingWorker;

public class TransmitRTP extends SwingWorker<Void, Void> {    
    private boolean message = false;
    private String hostAddress = null;
    private Transmitter transmitter;
    private boolean connect = false;    
    private final MediaLocator mediaLocator;
    private final int port;

    public TransmitRTP(int port, MediaLocator mediaLocator) {        
        super();	
        System.err.println("TransmitRTP:TransmitRTP");
        this.port = port;             
        this.mediaLocator = mediaLocator;
        System.err.println("TransmitRTP(port "+port+", mediaLocator "+mediaLocator+")");  
    }
    
    @Override
    protected Void doInBackground() {      
        System.err.println("TransmitRTP:doInBackground()");
        while (!isCancelled()) {
            if (!message) {
                message = !message;
                System.err.println("Esperando conexão de um cliente...");
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
            if (hostAddress == null) {
                try {
                    ServerSocket hostServer = new ServerSocket(port);
                    Socket socket = hostServer.accept();
                    if (socket.getInetAddress() != null
                    && !socket.getInetAddress().equals("")) {
                        hostAddress = socket.getInetAddress().getHostAddress();
                        connect = true;
                    }
                } catch (IOException e) {
                }
            }
            if (connect) {
                connect = false;
                if (mediaLocator==null){
                    System.out.println("Aqui chegou null");
                }else
                System.out.println("MediaLocator = "+ mediaLocator.toString());
                transmitter = new Transmitter(mediaLocator,
                                              hostAddress, port, 
                                              new Format(VideoFormat.JPEG));
            //1235 é a port de transmissao RTP, tem que ser diferente da TCP
            //aqui estamos especificando por qual port sera o envio 
            //das informacoes de video
                String result = transmitter.start();
                if (result != null) {
                    System.err.println("Error : " + result);
                }
                System.err.println("Transmissão RTP iniciada para " + hostAddress);
            }
        }
        if (transmitter != null) {
            transmitter.stop();
           // transmitter.stopTransmitter();//????
        }
        System.err.println("Servidor desligado.");
        return null;
    }

    public String getHostAddress() {
        return hostAddress;
    }

    public void setHostAddress(String ipCliente) {
        this.hostAddress = ipCliente;
    }

}