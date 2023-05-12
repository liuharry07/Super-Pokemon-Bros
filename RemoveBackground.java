import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class RemoveBackground {
    public static void main(String[] args) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("sprites.png"));
        } catch (IOException e) {
        }
        Color remove = new Color(img.getRGB(0, 0));
        for(int i = 0; i < img.getWidth(); ++i) {
            for(int j = 0; j < img.getHeight(); ++j) {
                if(new Color(img.getRGB(i, j)).equals(remove)) {
                   img.setRGB(i, j, 0);
                }
            }
        }
        try {
            ImageIO.write(img, "png", new File("sprites2.png"));
        } catch (IOException e) {
        }

    }
}
