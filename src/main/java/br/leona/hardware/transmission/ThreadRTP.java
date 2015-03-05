package br.leona.hardware.transmission;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.media.Format;
import javax.media.MediaLocator;
import javax.media.format.VideoFormat;
import javax.swing.SwingWorker;

public class ThreadRTP extends SwingWorker<Void, Void> {
    private static boolean message = false;
    private static String hostAddress = null;
    private static Transmitter transmissor;
    private static boolean connect = false;    
    private MediaLocator mediaLocator;
    private static int port;

    public ThreadRTP(int port, 
            MediaLocator mediaLocator
            ) {
        super();          
        this.port = port;
        this.mediaLocator = mediaLocator; 
    }

    @Override
    protected Void doInBackground() {
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
                     System.err.println("Erro quando new ServerSocket(port) !!"+e);
                }
            }
            if (connect) {
                connect = false;
                transmissor = new Transmitter(mediaLocator,
                                              hostAddress, port, 
                                              new Format(VideoFormat.JPEG));
            //1235 é a port de transmissao RTP, tem que ser diferente da TCP
            //aqui estamos especificando por qual port sera o envio 
            //das informacoes de video
                String result = transmissor.start();
                if (result != null) {
                    System.err.println("Error : " + result);
                }
                System.err.println("Transmissão RTP iniciada para " + hostAddress);
            }
        }
        if (transmissor != null) {
                transmissor.stop();
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