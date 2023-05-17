import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class FlipImage {
    public static void main(String[] args) {
        for(int i = 0; i < 10; ++i) {
            BufferedImage img = null;
            try {
                img = ImageIO.read(new File("attack" + "0_" + i + ".png"));
            } catch (IOException e) {
            }
            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-img.getWidth(null), 0);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            img = op.filter(img, null);
            try {
                ImageIO.write(img, "png", new File("attack" + "1_" + i + ".png"));
            } catch (IOException e) {
            }
        }
    }
}
