/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.leona.hardware.image;

import br.leona.hardware.model.Servico;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bytedeco.javacpp.opencv_core.IplImage;
import static org.bytedeco.javacpp.opencv_highgui.cvSaveImage;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.VideoInputFrameGrabber;


public class CaptureGrabber extends Thread {//implements Runnable {//   
    private String pathImage;
    private final Servico servico;
    private final int port = 1235;
    private FrameGrabber grabber;
    private final CanvasFrame canvas = new CanvasFrame("Capturar Imagens");

       
    public CaptureGrabber(String pathImage) {
        this.pathImage = pathImage;//  "c:\\ProjetoLeona\\Evento_";
        servico = new Servico();
        servico.setName("capture");
        servico.setStatus(0);
        canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
    }

    public Servico getServico() {
        return servico;
    }
    
    @Override
    public void run() {
        System.err.println("CaptureGrabber:run()");     
        SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String now =  date.format(new Date());           
        String path = pathImage+now;
        File dir = new File(path);
        if(!dir.mkdirs()) System.out.println("Não foi possível criar o diretório: "+path);
        else {            
            servico.setStatus(1);
            canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
            grabber = new VideoInputFrameGrabber(0);
        
            try {            
                grabber.start();//.restart();
                IplImage img = null;
                while (true) {
                    img = grabber.grab();
                    if (img != null) {
                        String path_and_name = path +"\\" 
                                + System.currentTimeMillis()+".jpg";
                        //cvFlip(img, img, 1);//Espelho l-r = 90_degrees_steps_anti_clockwise                   
                        cvSaveImage(path_and_name, img);                   
                        // show image on window
                        canvas.showImage(img);
                        System.out.println("path and name: "+path_and_name);
                    }
                     //Thread.sleep(INTERVAL);
                }
            } catch (Exception e) {
                 System.out.println("ERRO Durante Captura: "+e);
            }
        }
    }   

    public void stopCapture() {  
        System.err.println("CaptureGrabber:stopCapture()");
        try {
            grabber.stop();
            servico.setStatus(0);
        } catch (FrameGrabber.Exception ex) {
            Logger.getLogger(CaptureGrabber.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void endCapture() {   
        System.err.println("CaptureGrabber:endCapture()");
        try {
            grabber.stop();
            servico.setStatus(0);
        } catch (FrameGrabber.Exception ex) {
            Logger.getLogger(CaptureGrabber.class.getName()).log(Level.SEVERE, null, ex);
        }
      //  grabber = null;
        try {
            /*try {
            grabber.stop();
            } catch (FrameGrabber.Exception ex) {
            Logger.getLogger(CaptureGrabber.class.getName()).log(Level.SEVERE, null, ex);
            }*/
            finalize();
        } catch (Throwable ex) {
            Logger.getLogger(CaptureGrabber.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
   
}