package stegp;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Artur
 */
public class Application {
    public static void main(String[] args) throws IOException {
        BufferedImage image = ImageIO.read(new File("C:\\Users\\Artur\\Desktop\\obrazek.jpg"));
    }
}
