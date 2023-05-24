import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class ChangeColor {
    public static void main(String[] args) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("shield.png"));
        } catch (IOException e) {
        }
        Color remove = Color.RED;
        for(int i = 0; i < img.getWidth(); ++i) {
            for(int j = 0; j < img.getHeight(); ++j) {
                if(new Color(img.getRGB(i, j)).equals(remove)) {
                    img.setRGB(i, j, Color.WHITE.getRGB());
                }
            }
        }
        try {
            ImageIO.write(img, "png", new File("shield2.png"));
        } catch (IOException e) {
        }

    }
}


