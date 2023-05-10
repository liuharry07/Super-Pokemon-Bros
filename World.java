import java.awt.*;
import java.awt.event.*;
import java.util.*;
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

    private boolean inAir;

    public World(int w, int h) {
        width = w;
        height = h;
        inAir = true;
        sprites = new ArrayList<Sprite>();
        double dir;

        sprites.add(new Sprite(345, 560, 800, 20, "stagehitbox.png"));
        //make a hitbox for stage

        //stage
        sprites.add(new Sprite(300, 200, 900, 600, "stage.png"));

        //charmander
        dir = Math.random() * 2 * Math.PI;
        sprites.add(new HeavySprite(Math.random() * (width - 50), Math.random() * (height - 50), 38, 42, "1.png", 0, Math.sin(dir)));

        player = (HeavySprite)sprites.get(2);
        stageHitbox = sprites.get(0);
    }

    public void stepAll() {
        for(int i = 0; i < sprites.size(); i++) {
            Sprite s = sprites.get(i);
            s.step(this);
        }
        if((int) (player.getTop() + player.getHeight()) == (int) stageHitbox.getTop()) {
            if(player.getLeft() > stageHitbox.getLeft()) {
                if(player.getLeft() < stageHitbox.getLeft() + stageHitbox.getWidth()) {
                    player.setVY(0);
                }
            }
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
                player.setVY(-3);
                inAir = true;
                break;
            }
            case 37: {
                player.setVX(-3);
                break;
            }
            case 39: {
                player.setVX(3);
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
                break;
            }
            case 39: {
                if(player.getVX() > 0)
                    player.setVX(0);
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
