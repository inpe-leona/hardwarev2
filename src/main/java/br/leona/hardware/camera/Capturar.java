/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.leona.hardware.camera;

import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.media.Buffer;
import javax.media.control.FrameGrabbingControl;
import javax.media.format.VideoFormat;
import javax.media.util.BufferToImage;

/**
 *
 * @author Leona_Lenovo
 */
public class Capturar extends Thread {

    private final CameraController cameraController;
    String nfile;
    BufferedImage bi;
    LocalDateTime agora;

    Capturar(CameraController cameraController) {

        this.cameraController = cameraController;
    }

    @Override
    public void run() {

        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat format2 = new SimpleDateFormat("hh mm ss");
        String formatada = format2.format(date);

        String diretorio = "C:/ProjetoLeona/";
        String observacao = "Evento "
                + "" + localDate().format(DateTimeFormatter.ofPattern("ddMMyyyy"))
                + " " + formatada
                + "/";
        String imagem;

        int[] a = new int[1080000];
        File dir = new File(diretorio + observacao);

        FrameGrabbingControl fgc = (FrameGrabbingControl) cameraController.player.getControl("javax.media.control.FrameGrabbingControl");

        Buffer buffer = fgc.grabFrame();

        BufferToImage bti = new BufferToImage((VideoFormat) buffer.getFormat());
        Image image = bti.createImage(buffer);
        bi = (BufferedImage) image;

        if (dir.mkdirs()) {

            for (int i = 0; i < a.length; i++) {

                //agora = LocalDateTime.now();
                Date date1 = new Date(System.currentTimeMillis());
                SimpleDateFormat hora = new SimpleDateFormat("HH mm ss");

                FileDialog fd = new FileDialog(new Frame(), " ", FileDialog.SAVE);

              //  imagem = "[Evento" + i + "]" + localDate() + " " + hora.format(date1);
                 imagem = "[Evento" + i + "]";
                System.out.println("Evento " + i + "]" + localDate() + " " + hora.format(date1));
                fd.setDirectory(diretorio + observacao);

                fd.setFile(imagem);

                if (fd.getFile() != null) {
                    nfile = fd.getDirectory() + fd.getFile() + ".jpg";
                    try {

                        File make = new File(nfile);
                        try {

                            ImageIO.write(bi, "jpg", make);
                        } catch (IOException ex) {

                        }
                    } catch (Exception ex) {
                    }
                }
            }
        } else {

            System.out.println("Nao foi possivel criar o diretorio");
        }
       
    }

    private LocalDate localDate() {

        LocalDate hoje = LocalDate.now();
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd MM yyyy");
        hoje.format(formatador); // exemplo 08/04/2014
        return hoje;
    }

}