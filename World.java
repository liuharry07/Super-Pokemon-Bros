import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class World {
    public static void main(String[] args) {
        run();
    }

    public static void run() {
        Display display = new Display(1440, 800);
        display.run();
    }

    private ArrayList<Sprite> sprites;
    private int width;
    private int height;

    private HeavySprite player;
    private Sprite stageHitbox;

    private double score1;
    private double score2;

    private JLabel scorelabelp1;
    private JLabel scorelabelp2;

    private double runAnimation;
    private int standAnimation;

    private boolean isMoving;

    private int direction;

    private final int LEFT = 0;
    private final int RIGHT = 1;
    private int jumps;

    public World(int w, int h) {
        runAnimation = 0;
        isMoving = false;
        jumps = 0;
        width = w;
        height = h;
        sprites = new ArrayList<Sprite>();

        //stage hitbox
        sprites.add(new Sprite(345, 550, 800, 20, "stagehitbox.png"));

        /*
         * scorelabelp1.setBounds(50, 50, 100, 100);
         * scorelabelp1.setText("00%");
         * 
         * scorelabelp2.setBounds(750, 50, 100, 100);
         * scorelabelp2.setText("00%");
         */

        //platform hitboxes
        sprites.add(new Sprite(450, 430, 155, 20, "stagehitbox.png"));
        sprites.add(new Sprite(900, 430, 155, 20, "stagehitbox.png"));

        //stage
        sprites.add(new Sprite(300, 200, 900, 600, "stage.png"));

        //charmander
        sprites.add(new HeavySprite(500, 200, 43, 42, "stand0.png", 0.0, 0.0));

        player = (HeavySprite) sprites.get(sprites.size() - 1);
        stageHitbox = sprites.get(0);
    }

    public void stepAll() {
        for(int i = 0; i < sprites.size(); i++) {
            Sprite s = sprites.get(i);
            s.step(this);
        }
        for(int i = 0; i < 3; ++i) {
            if((int) (player.getTop() + player.getHeight()) >= (int) sprites.get(i).getTop() && (int) (player.getTop() + player.getHeight()) < (int) (sprites.get(i).getTop() + 10)) {
                if(player.getLeft() > sprites.get(i).getLeft()) {
                    if(player.getLeft() < sprites.get(i).getLeft() + sprites.get(i).getWidth()) {
                        player.setVY(0);
                        jumps = 0;
                        player.setTop(sprites.get(i).getTop() - player.getHeight());
                    }
                }
            }
        }
        if(isMoving) {
            runAnimation += 0.18;
            player.setImage("run" + direction + "_" + (int) (runAnimation % 8) + ".png");
            try {
                BufferedImage temp = ImageIO.read(new File("run" + direction + "_" + (int) (runAnimation % 8) + ".png"));
                player.setHeight((int) (temp.getHeight() * 1.2));
                player.setWidth((int) (temp.getWidth() * 1.2));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        /*
         * else {
         * player.setImage("stand" + standAnimation % 6 + ".png");
         * try {
         * BufferedImage temp = ImageIO.read(new File("stand0.png"));
         * player.setHeight((int) (temp.getHeight() * 1.2));
         * player.setWidth((int) (temp.getWidth() * 1.2));
         * player.setTop(player.getTop() - 5);
         * } catch (IOException e) {
         * e.printStackTrace();
         * }
         * }
         */
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getNumSprites() {
        return sprites.size();
    }

    public Sprite getSprite(int index) {
        return sprites.get(index);
    }

    public void mouseClicked(int x, int y) {
        System.out.println("mouseClicked:  " + x + ", " + y);
    }

    public void keyPressed(int key) {
        switch(key) {
            case 38: {
                if(jumps < 2) {
                    player.setVY(-5);
                    ++jumps;
                }
                break;
            }
            case 37: {
                player.setVX(-3);
                isMoving = true;
                direction = LEFT;
                break;
            }
            case 39: {
                player.setVX(3);
                isMoving = true;
                direction = RIGHT;
                break;
            }
        }
        System.out.println("keyPressed:  " + key);
    }

    public void keyReleased(int key) {
        switch(key) {
            case 37: {
                if(player.getVX() < 0)
                    player.setVX(0);
                isMoving = false;
                break;
            }
            case 39: {
                if(player.getVX() > 0)
                    player.setVX(0);
                isMoving = false;
                break;
            }
        }
        System.out.println("keyReleased:  " + key);
    }

    public String getTitle() {
        return "World";
    }

    public void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        for(int i = 0; i < sprites.size(); i++) {
            Sprite sprite = sprites.get(i);
            g.drawImage(Display.getImage(sprite.getImage()), (int) sprite.getLeft(), (int) sprite.getTop(), sprite.getWidth(), sprite.getHeight(), null);
        }
    }
}
