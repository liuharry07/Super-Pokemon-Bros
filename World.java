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

    private Sprite[] playerHitboxes;
    private Sprite[][] attackHitboxes;

    private double score1;
    private double score2;

    private Sprite p1Label;
    private Sprite p2Label;

    private double[] runAnimation;
    private double[] standAnimation;
    private double[] attackAnimation;
    private int[][] animationFrames;

    private boolean[] isMoving;

    private boolean[] attack;

    private int[] direction;
    private final int LEFT = 0;
    private final int RIGHT = 1;

    private int[] jumps;

    public World(int w, int h) {
        runAnimation = new double[2];
        standAnimation = new double[2];
        attackAnimation = new double[2];
        animationFrames = new int[2][3];
        animationFrames[0][0] = 6;
        animationFrames[0][1] = 8;
        animationFrames[0][2] = 10;
        animationFrames[1][0] = 6;
        animationFrames[1][1] = 6;
        animationFrames[1][2] = 8;

        isMoving = new boolean[2];
        direction = new int[2];
        jumps = new int[2];
        attack = new boolean[2];

        width = w;
        height = h;

        sprites = new ArrayList<Sprite>();
        players = new HeavySprite[2];
        playerHitboxes = new Sprite[2];
        attackHitboxes = new Sprite[2][2];

        //stage hitbox
        sprites.add(new Sprite(345, 550, 800, 20, "hitbox.png", 0));

        //score
        score1 = 0.0;
        score2 = 0.0;

        //platform hitboxes
        sprites.add(new Sprite(450, 430, 155, 20, "hitbox.png", 0));
        sprites.add(new Sprite(900, 430, 155, 20, "hitbox.png", 0));

        //stage
        sprites.add(new Sprite(300, 200, 900, 600, "stage.png", 0));

        //player hitboxes
        sprites.add(new Sprite(0, 0, 0, 0, "hitbox.png", 0));
        sprites.add(new Sprite(0, 0, 0, 0, "hitbox.png", 0));
        playerHitboxes[0] = sprites.get(sprites.size() - 2);
        playerHitboxes[1] = sprites.get(sprites.size() - 1);

        //attack hitboxes
        sprites.add(new Sprite(0, 0, 0, 0, "hitbox.png", 0));
        sprites.add(new Sprite(0, 0, 0, 0, "hitbox.png", 0));
        attackHitboxes[0][0] = sprites.get(sprites.size() - 2);
        attackHitboxes[0][1] = sprites.get(sprites.size() - 1);
        sprites.add(new Sprite(0, 0, 0, 0, "hitbox.png", 0));
        sprites.add(new Sprite(0, 0, 0, 0, "hitbox.png", 0));
        attackHitboxes[1][0] = sprites.get(sprites.size() - 2);
        attackHitboxes[1][1] = sprites.get(sprites.size() - 1);

        //players
        sprites.add(new HeavySprite(500, 100, 0, 0, "1/stand0_0.png", 0.0, 0.0, 1));
        sprites.add(new HeavySprite(700, 100, 0, 0, "1/stand0_0.png", 0.0, 0.0, 2));
        players[0] = (HeavySprite) sprites.get(sprites.size() - 2);
        players[1] = (HeavySprite) sprites.get(sprites.size() - 1);

        //labels
        p1Label = new Sprite(500, 158, 20, 20, "Player1Label.png", 0);
        try {
            BufferedImage temp = ImageIO.read(new File("Player1Label.png"));
            p1Label.setHeight((int) (temp.getHeight() / 4));
            p1Label.setWidth((int) (temp.getWidth() / 4));
        } catch (IOException e) {
            e.printStackTrace();
        }
        p2Label = new Sprite(500, 158, 20, 20, "Player2Label.png", 0);
        try {
            BufferedImage temp = ImageIO.read(new File("Player2Label.png"));
            p2Label.setHeight((int) (temp.getHeight() / 4));
            p2Label.setWidth((int) (temp.getWidth() / 4));
        } catch (IOException e) {
            e.printStackTrace();
        }
        sprites.add(p1Label);
        sprites.add(p2Label);
    }

    public void stepAll() {
        for(int i = 0; i < sprites.size(); i++) {
            Sprite s = sprites.get(i);
            s.step(this);
        }

        //platform code
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 2; ++j) {
                if((int) (players[j].getTop() + players[j].getHeight()) >= (int) sprites.get(i).getTop() && (int) (players[j].getTop() + players[j].getHeight()) < (int) (sprites.get(i).getTop() + 10)) {
                    if(players[j].getLeft() > sprites.get(i).getLeft()) {
                        if(players[j].getLeft() < sprites.get(i).getLeft() + sprites.get(i).getWidth()) {
                            players[j].setVY(0);
                            jumps[j] = 0;
                            players[j].setTop(sprites.get(i).getTop() - players[j].getHeight());
                        }
                    }
                }
            }
        }

        //label movement
        p1Label.setLeft(players[0].getLeft());
        p1Label.setTop(players[0].getTop() - players[0].getHeight());
        p2Label.setLeft(players[1].getLeft());
        p2Label.setTop(players[1].getTop() - players[1].getHeight());

        //hitbox stuff
        for(int i = 0; i < 2; ++i) {
            playerHitboxes[i].setHeight(players[i].getHeight());
            playerHitboxes[i].setWidth(players[i].getWidth());
            playerHitboxes[i].setLeft(players[i].getLeft());
            playerHitboxes[i].setTop(players[i].getTop());
        }

        //animation code
        for(int i = 0; i < 2; ++i) {
            if(isMoving[i]) {
                //run animation
                runAnimation[i] += 0.18;
                int ph = players[i].getHeight();
                players[i].setImage(players[i].getType() + "/run" + direction[i] + "_" + (int) (runAnimation[i] % animationFrames[i][1]) + ".png");
                try {
                    BufferedImage temp = ImageIO.read(new File(players[i].getType() + "/run" + direction[i] + "_" + (int) (runAnimation[i] % animationFrames[i][1]) + ".png"));
                    players[i].setHeight((int) (temp.getHeight() * 1.2));
                    players[i].setWidth((int) (temp.getWidth() * 1.2));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                players[i].setTop(players[i].getTop() - (players[i].getHeight() - ph) / 2);

            }
            else {
                //stand animation
                standAnimation[i] += 0.1;
                int ph = players[i].getHeight();
                players[i].setImage(players[i].getType() + "/stand" + direction[i] + "_" + (int) (standAnimation[i] % animationFrames[i][0]) + ".png");
                try {
                    BufferedImage temp = ImageIO.read(new File(players[i].getType() + "/stand" + direction[i] + "_" + (int) (standAnimation[i] % animationFrames[i][0]) + ".png"));
                    players[i].setHeight((int) (temp.getHeight() * 1.2));
                    players[i].setWidth((int) (temp.getWidth() * 1.2));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                players[i].setTop(players[i].getTop() - (players[i].getHeight() - ph) / 2);

            }
            if(attack[i]) {
                //attack animation;
                attackAnimation[i] += 0.18;
                int ph = players[i].getHeight();
                if((int) (attackAnimation[i] / animationFrames[i][2]) != 1) {
                    players[i].setImage(players[i].getType() + "/attack" + direction[i] + "_" + (int) (attackAnimation[i] % animationFrames[i][2]) + ".png");
                    try {
                        BufferedImage temp = ImageIO.read(new File(players[i].getType() + "/attack" + direction[i] + "_" + (int) (attackAnimation[i] % animationFrames[i][2]) + ".png"));
                        players[i].setHeight((int) (temp.getHeight() * 1.2));
                        players[i].setWidth((int) (temp.getWidth() * 1.2));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    attackAnimation[i] = 0;
                    attack[i] = false;
                }
                players[i].setTop(players[i].getTop() - (players[i].getHeight() - ph) / 2);
            }
        }
        /*
        for(int i = 0; i < 2; ++i) {
            if(attack[i]) {
                attackHitboxes[i][0].setLeft(i);
            }
        }
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
                if(jumps[0] < 2) {
                    players[0].setVY(-5);
                    ++jumps[0];
                }
                break;
            }
            case 37: {
                if(!attack[0]) {//fix later
                    players[0].setVX(-3);
                    isMoving[0] = true;
                    direction[0] = LEFT;
                }
                break;
            }
            case 39: {
                if(!attack[0]) {//fix later
                    players[0].setVX(3);
                    isMoving[0] = true;
                    direction[0] = RIGHT;
                }
                break;
            }
            case 44: {
                attack[0] = true;
                break;
            }
            case 87: {
                if(jumps[1] < 2) {
                    players[1].setVY(-5);
                    ++jumps[1];
                }
                break;
            }
            case 65: {
                players[1].setVX(-3);
                isMoving[1] = true;
                direction[1] = LEFT;
                break;
            }
            case 68: {
                players[1].setVX(3);
                isMoving[1] = true;
                direction[1] = RIGHT;
                break;
            }
            case 70: {
                attack[1] = true;
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
                isMoving[0] = false;
                break;
            }
            case 39: {
                if(players[0].getVX() > 0)
                    players[0].setVX(0);
                isMoving[0] = false;
                break;
            }
            case 65: {
                if(players[1].getVX() < 0)
                    players[1].setVX(0);
                isMoving[1] = false;
                break;
            }
            case 68: {
                if(players[1].getVX() > 0)
                    players[1].setVX(0);
                isMoving[1] = false;
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
