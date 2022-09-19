package com.knubisoft;

import com.knubisoft.utils.ProcessingUtils;
import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;


public class Main {
    @SneakyThrows
    public static void main(String[] args) {
        BufferedImage input1 = ImageIO.read(new File("src/main/resources/image-1.png"));
        BufferedImage input2 = ImageIO.read(new File("src/main/resources/image-2.png"));

        ImageIO.write(new ProcessingUtils().generateResultImage(input1, input2), "jpg", new File("src/main/resources/image-res.png"));
    }
}