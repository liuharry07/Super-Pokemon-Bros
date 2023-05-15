import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class FlipImage {
    public static void main(String[] args) {
        for(int i = 0; i < 8; ++i) {
            BufferedImage img = null;
            try {
                img = ImageIO.read(new File("runL" + i + ".png"));
            } catch (IOException e) {
            }
            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-img.getWidth(null), 0);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            img = op.filter(img, null);
            try {
                ImageIO.write(img, "png", new File("runR" + i + ".png"));
            } catch (IOException e) {
            }
        }
    }
}
