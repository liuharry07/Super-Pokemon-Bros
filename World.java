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

    private HeavySprite[] players;
    private final int CHARMANDER = 1;
    private final int SQUIRTLE = 2;

    private double score1;
    private double score2;

    private double runAnimation;
    private double standAnimation;

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
        players = new HeavySprite[2];

        //stage hitbox
        sprites.add(new Sprite(345, 550, 800, 20, "stagehitbox.png", 0));

        //score
        score1 = 0.0;
        score2 = 0.0;

        //platform hitboxes
        sprites.add(new Sprite(450, 430, 155, 20, "stagehitbox.png", 0));
        sprites.add(new Sprite(900, 430, 155, 20, "stagehitbox.png", 0));

        //stage
        sprites.add(new Sprite(300, 200, 900, 600, "stage.png", 0));

        //charmander
        sprites.add(new HeavySprite(500, 200, 43, 42, "1/stand0_0.png", 0.0, 0.0, 1));

        players[0] = (HeavySprite) sprites.get(sprites.size() - 1);
    }

    public void stepAll() {
        for(int i = 0; i < sprites.size(); i++) {
            Sprite s = sprites.get(i);
            s.step(this);
        }

        //platform code
        //add so that it works for all players
        for(int i = 0; i < 3; ++i) {
            if((int) (players[0].getTop() + players[0].getHeight()) >= (int) sprites.get(i).getTop() && (int) (players[0].getTop() + players[0].getHeight()) < (int) (sprites.get(i).getTop() + 10)) {
                if(players[0].getLeft() > sprites.get(i).getLeft()) {
                    if(players[0].getLeft() < sprites.get(i).getLeft() + sprites.get(i).getWidth()) {
                        players[0].setVY(0);
                        jumps = 0;
                        players[0].setTop(sprites.get(i).getTop() - players[0].getHeight());
                    }
                }
            }
        }

        //animation code
        if(isMoving) {
            //run animation
            runAnimation += 0.18;
            int ph = players[0].getHeight();
            players[0].setImage(players[0].getType() + "/run" + direction + "_" + (int) (runAnimation % 8) + ".png");
            try {
                BufferedImage temp = ImageIO.read(new File(players[0].getType() + "/run" + direction + "_" + (int) (runAnimation % 8) + ".png"));
                players[0].setHeight((int) (temp.getHeight() * 1.2));
                players[0].setWidth((int) (temp.getWidth() * 1.2));
            } catch (IOException e) {
                e.printStackTrace();
            }
            players[0].setTop(players[0].getTop() - (players[0].getHeight() - ph) / 2);
        }
        else {
            //stand animation
            standAnimation += 0.1;
            int ph = players[0].getHeight();
            players[0].setImage(players[0].getType() + "/stand" + direction + "_" + (int) (standAnimation % 6) + ".png");
            try {
                BufferedImage temp = ImageIO.read(new File(players[0].getType() + "/stand" + direction + "_" + (int) (standAnimation % 6) + ".png"));
                players[0].setHeight((int) (temp.getHeight() * 1.2));
                players[0].setWidth((int) (temp.getWidth() * 1.2));
            } catch (IOException e) {
                e.printStackTrace();
            }
            players[0].setTop(players[0].getTop() - (players[0].getHeight() - ph) / 2);
        }
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
                    players[0].setVY(-5);
                    ++jumps;
                }
                break;
            }
            case 37: {
                players[0].setVX(-3);
                isMoving = true;
                direction = LEFT;
                break;
            }
            case 39: {
                players[0].setVX(3);
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
                if(players[0].getVX() < 0)
                    players[0].setVX(0);
                isMoving = false;
                break;
            }
            case 39: {
                if(players[0].getVX() > 0)
                    players[0].setVX(0);
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

        //damage text
        g.setColor(new Color(0, 0, 255));
        g.setFont(new Font("TimesRoman", Font.BOLD, 40));
        g.drawString(score1 + "%", 450, 750);
        g.setColor(new Color(255, 0, 0));
        g.drawString(score2 + "%", 850, 750);
    }
}
