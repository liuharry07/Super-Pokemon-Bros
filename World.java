import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;

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
    private Sprite[] blockHitboxes;

    private double[] score;
    private int[] lives;

    private Sprite p1Label;
    private Sprite p2Label;
    private Sprite p1LifeLabel;
    private Sprite p2LifeLabel;

    private double[] runAnimation;
    private double[] standAnimation;
    private double[] attackAnimation;
    private double[] rangedAttackAnimation;
    private double[] hurtAnimation;
    private int[][] animationFrames;
    private ArrayList<Integer> p1LivesSprites;
    private ArrayList<Integer> p2LivesSprites;

    private boolean[] isMoving;

    private boolean[] attack;
    private boolean[] rangedAttack;
    private boolean[] hurt;
    private boolean[] dealDamage;
    private boolean[] block;
    private int[] blockTime;
    private int[] direction;
    private int[] rangedAttackDirection;
    private final int LEFT = 0;
    private final int RIGHT = 1;

    private int[] jumps;

    public World(int w, int h) {
        runAnimation = new double[2];
        standAnimation = new double[2];
        attackAnimation = new double[2];
        rangedAttackAnimation = new double[2];
        hurtAnimation = new double[2];
        animationFrames = new int[2][4];
        animationFrames[0][0] = 6;
        animationFrames[0][1] = 8;
        animationFrames[0][2] = 10;
        animationFrames[0][3] = 10;
        animationFrames[1][0] = 6;
        animationFrames[1][1] = 6;
        animationFrames[1][2] = 8;
        animationFrames[1][3] = 8;

        isMoving = new boolean[2];
        direction = new int[2];
        rangedAttackDirection = new int[2];
        jumps = new int[2];
        attack = new boolean[2];
        rangedAttack = new boolean[2];
        dealDamage = new boolean[2];
        hurt = new boolean[2];
        lives = new int[2];
        block = new boolean[2];
        blockTime = new int[2];
        for(int i = 0; i < 2; ++i) {
            lives[i] = 3;
        }
        width = w;
        height = h;

        sprites = new ArrayList<Sprite>();
        players = new HeavySprite[2];
        playerHitboxes = new Sprite[2];
        attackHitboxes = new Sprite[2][2];
        blockHitboxes = new Sprite[2];

        //stage hitbox
        sprites.add(new Sprite(245, 525, 900, 20, "hitbox.png", 0));

        //score
        score = new double[2];

        //platform hitboxes
        sprites.add(new Sprite(420, 405, 155, 20, "hitbox.png", 0));
        sprites.add(new Sprite(825, 405, 155, 20, "hitbox.png", 0));

        //player hitboxes
        sprites.add(new Sprite(0, 0, 0, 0, "hitbox.png", 0));
        sprites.add(new Sprite(0, 0, 0, 0, "hitbox.png", 0));
        playerHitboxes[0] = sprites.get(sprites.size() - 2);
        playerHitboxes[1] = sprites.get(sprites.size() - 1);
        sprites.add(new Sprite(0, 0, 1440, 800, "1.png", 0));
        sprites.add(new Sprite(0, 0, 1440, 800, "2.png", 0));
        sprites.add(new Sprite(0, 0, 1440, 800, "3.png", 0));
        sprites.add(new Sprite(0, 0, 1440, 800, "4.png", 0));
        sprites.add(new Sprite(0, 0, 1440, 800, "5.png", 0));

        sprites.add(new Sprite(0, 0, 1440, 800, "background copy.png", 0));


        //stage
        //sprites.add(new Sprite(300, 75, 900, 600, "stage.png", 0));

        //attack hitboxes
        sprites.add(new Sprite(0, 0, 0, 0, "hitbox.png", 0));
        sprites.add(new Sprite(0, 0, 0, 0, "hitbox.png", 0));
        attackHitboxes[0][0] = sprites.get(sprites.size() - 2);
        attackHitboxes[0][1] = sprites.get(sprites.size() - 1);
        sprites.add(new Sprite(0, 0, 0, 0, "hitbox.png", 0));
        sprites.add(new Sprite(0, 0, 0, 0, "hitbox.png", 0));
        attackHitboxes[1][0] = sprites.get(sprites.size() - 2);
        attackHitboxes[1][1] = sprites.get(sprites.size() - 1);

        //block hitboxes
        sprites.add(new Sprite(0, 0, 0, 0, "hitbox.png", 0));
        sprites.add(new Sprite(0, 0, 0, 0, "hitbox.png", 0));
        blockHitboxes[0] = sprites.get(sprites.size() - 2);
        blockHitboxes[1] = sprites.get(sprites.size() - 1);

        //players
        sprites.add(new HeavySprite(500, -25, 0, 0, "1/stand0_0.png", 0.0, 0.0, CHARMANDER));
        sprites.add(new HeavySprite(950, -25, 0, 0, "1/stand0_0.png", 0.0, 0.0, SQUIRTLE));
        players[0] = (HeavySprite) sprites.get(sprites.size() - 2);
        players[1] = (HeavySprite) sprites.get(sprites.size() - 1);

        //labels
        p1Label = new Sprite(500, 33, 20, 20, "Player1Label.png", 0);
        try {
            BufferedImage temp = ImageIO.read(new File("Player1Label.png"));
            p1Label.setHeight((int) (temp.getHeight() / 4));
            p1Label.setWidth((int) (temp.getWidth() / 4));
        } catch (IOException e) {
            e.printStackTrace();
        }
        p2Label = new Sprite(500, 33, 20, 20, "Player2Label.png", 0);
        try {
            BufferedImage temp = ImageIO.read(new File("Player2Label.png"));
            p2Label.setHeight((int) (temp.getHeight() / 4));
            p2Label.setWidth((int) (temp.getWidth() / 4));
        } catch (IOException e) {
            e.printStackTrace();
        }
        sprites.add(p1Label);
        sprites.add(p2Label);

        p1LifeLabel = new Sprite(600, 250, 20, 20, players[0].getType() + "/face.png", 0);
        sprites.add(p1LifeLabel);
        p1LifeLabel = new Sprite(625, 250, 20, 20, players[0].getType() + "/face.png", 0);
        sprites.add(p1LifeLabel);
        p1LifeLabel = new Sprite(650, 250, 20, 20, players[0].getType() + "/face.png", 0);
        sprites.add(p1LifeLabel);
        p1LivesSprites = new ArrayList<Integer>();
        for(int i = 3; i >= 1; i--) {
            p1LivesSprites.add(sprites.size() - i);
        }
        p2LifeLabel = new Sprite(750, 250, 20, 20, players[1].getType() + "/face.png", 0);
        sprites.add(p2LifeLabel);
        p2LifeLabel = new Sprite(775, 250, 20, 20, players[1].getType() + "/face.png", 0);
        sprites.add(p2LifeLabel);
        p2LifeLabel = new Sprite(800, 250, 20, 20, players[1].getType() + "/face.png", 0);
        sprites.add(p2LifeLabel);
        p2LivesSprites = new ArrayList<Integer>();
        for(int i = 3; i >= 1; i--) {
            p2LivesSprites.add(sprites.size() - i);
        }
        text3 box3 = new text3();
        box3.main();
    }

    public void stepAll() {
        for(int i = 0; i < sprites.size(); i++) {
            Sprite s = sprites.get(i);
            s.step(this);
        }

        //platform code
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 2; ++j) {
                if((int) (players[j].getTop() + players[j].getHeight()) >= (int) sprites.get(i).getTop() && (int) (players[j].getTop() + players[j].getHeight()) < (int) (sprites.get(i).getTop() + 12)) {
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
            if(hurt[i]) {
                hurtAnimation[i] += 0.1;
                if(hurtAnimation[i] < 2.0) {
                    players[i].setImage(players[i].getType() + "/hurt.png");
                }
                else {
                    hurtAnimation[i] = 0.0;
                    hurt[i] = false;
                }
            }
            else {
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
                if(rangedAttack[i]) {
                    rangedAttackAnimation[i] += 0.18;
                    int ph = players[i].getHeight();
                    if((int) (rangedAttackAnimation[i] / animationFrames[i][3]) != 1) {
                        players[i].setImage(players[i].getType() + "/ranged" + direction[i] + "_" + (int) (rangedAttackAnimation[i] % animationFrames[i][3]) + ".png");
                        try {
                            BufferedImage temp = ImageIO.read(new File(players[i].getType() + "/ranged" + direction[i] + "_" + (int) (rangedAttackAnimation[i] % animationFrames[i][3]) + ".png"));
                            players[i].setHeight((int) (temp.getHeight() * 1.2));
                            players[i].setWidth((int) (temp.getWidth() * 1.2));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        rangedAttackAnimation[i] = 0;
                        rangedAttack[i] = false;
                    }
                    players[i].setTop(players[i].getTop() - (players[i].getHeight() - ph) / 2);
                }
            }
        }

        //block thingy
        for(int i = 0; i < 2; i++) {
            if(block[i] && blockTime[i]<= 50) {
                blockHitboxes[i].setHeight(players[i].getHeight() + 10);
                blockHitboxes[i].setWidth(players[i].getWidth() + 20);
                blockHitboxes[i].setLeft(players[i].getLeft() - 10);
                blockHitboxes[i].setTop(players[i].getTop() - 10);
                blockTime[i]++;
            }
            else {
                blockHitboxes[i].setWidth(0);
                blockHitboxes[i].setHeight(0);
                block[i] = false;
            }
        }

        //attack stuff
        for(int i = 0; i < 2; ++i) {
            if(attack[i] && !hurt[i]) {
                if(direction[i] == LEFT) {
                    attackHitboxes[i][0].setHeight(20);
                    attackHitboxes[i][0].setWidth(20);
                    attackHitboxes[i][0].setLeft(players[i].getLeft() - attackHitboxes[i][0].getWidth() / 2);
                    attackHitboxes[i][0].setTop(players[i].getTop() + attackHitboxes[i][0].getHeight() / 2);
                }
                else if(direction[i] == RIGHT) {
                    attackHitboxes[i][0].setHeight(20);
                    attackHitboxes[i][0].setWidth(20);
                    attackHitboxes[i][0].setLeft(players[i].getLeft() + players[i].getWidth() - attackHitboxes[i][0].getWidth() / 2);
                    attackHitboxes[i][0].setTop(players[i].getTop() + attackHitboxes[i][0].getHeight() / 2);
                }
                if(i == 0 && block[1])
                    dealDamage[i] = false;
                else if(i == 1 && block[0])
                    dealDamage[i] = false;
                if(touching(attackHitboxes[i][0], playerHitboxes[(i + 1) % 2]) && dealDamage[i]) {
                    score[(i + 1) % 2] += 10.0;
                    dealDamage[i] = false;
                    hurt[(i + 1) % 2] = true;
                }
            }
            else {
                attackHitboxes[i][0].setHeight(0);
                attackHitboxes[i][0].setWidth(0);
            }
        }

        //ranged attack
        for(int i = 0; i < 2; ++i) {
            if(rangedAttack[i] && !hurt[i] && rangedAttackAnimation[i] > 5) {
                if(rangedAttackAnimation[i] < 5.5) {
                    if(direction[i] == LEFT) {
                        attackHitboxes[i][1].setHeight(20);
                        attackHitboxes[i][1].setWidth(20);
                        attackHitboxes[i][1].setTop(players[i].getTop() + attackHitboxes[i][0].getHeight() / 2);
                        attackHitboxes[i][1].setLeft(players[i].getLeft());
                        rangedAttackDirection[i] = LEFT;
                    }
                    else {
                        attackHitboxes[i][1].setHeight(20);
                        attackHitboxes[i][1].setWidth(20);
                        attackHitboxes[i][1].setTop(players[i].getTop() + attackHitboxes[i][0].getHeight() / 2);
                        attackHitboxes[i][1].setLeft(players[i].getLeft() + players[i].getWidth());
                        rangedAttackDirection[i] = RIGHT;
                    }
                }
                if(rangedAttackDirection[i] == LEFT) {
                    attackHitboxes[i][1].setLeft(attackHitboxes[i][1].getLeft() - 8);
                }
                else if(rangedAttackDirection[i] == RIGHT) {
                    attackHitboxes[i][1].setLeft(attackHitboxes[i][1].getLeft() + 8);
                }
                if(i == 0 && block[1])
                    dealDamage[i] = false;
                else if(i == 1 && block[0])
                    dealDamage[i] = false;
                if(touching(attackHitboxes[i][1], playerHitboxes[(i + 1) % 2]) && dealDamage[i]) {
                    score[(i + 1) % 2] += 10.0;
                    dealDamage[i] = false;
                    hurt[(i + 1) % 2] = true;
                }
            }
            else {
                attackHitboxes[i][1].setHeight(0);
                attackHitboxes[i][1].setWidth(0);
            }

        }

        for(int i = 0; i < 2; ++i) {
            if(hurt[i]) {
                if(direction[(i + 1) % 2] == LEFT) {
                    players[i].setLeft(players[i].getLeft() + score[i] / 100.0 * -10.0);//works good enough - exponential???
                }
                else {
                    players[i].setLeft(players[i].getLeft() + score[i] / 100.0 * 10.0);
                }
            }
        }

        //losing
        for(int i = 0; i < 2; i++) {
            if(players[i].getTop() + players[i].getHeight() + 1 >= getHeight()) {
                players[i].setLeft(400);
                players[i].setTop(200);
                players[i].setVY(0.0);
                score[i] = 0.0;
                --lives[i];
                if(i == 0) {
                    sprites.remove(sprites.get(p1LivesSprites.get(p1LivesSprites.size() - 1)));
                    p1LivesSprites.remove(p1LivesSprites.size() - 1);
                    for(int j = 0; j < p2LivesSprites.size(); j++) {
                        int num = p2LivesSprites.get(j);
                        p2LivesSprites.set(j, num - 1);
                    }
                }
                if(i == 1) {
                    sprites.remove(sprites.get(p2LivesSprites.get(p2LivesSprites.size() - 1)));
                    p2LivesSprites.remove(p2LivesSprites.size() - 1);
                }
            }
        }

        if(lives[0] == 0) {
            text box = new text();
            box.main();
            lives[0] = 3;
            players[0].setLeft(500);
            players[0].setHeight(100);
            players[0].setVX(0);
            players[1].setLeft(950);
            players[1].setHeight(0);
            players[1].setVX(0);
        }
        if(lives[1] == 0) {
            text2 box = new text2();
            box.main();
            lives[1] = 3;
            players[0].setLeft(500);
            players[0].setHeight(0);
            players[0].setVX(0);
            players[1].setLeft(950);
            players[1].setHeight(100);
            players[1].setVX(0);
        }
    }

    public boolean touching(Sprite a, Sprite b) {
        return a.getTop() < b.getTop() + b.getHeight() && a.getTop() + a.getHeight() > b.getTop() && a.getLeft() < b.getLeft() + b.getWidth() && a.getLeft() + a.getWidth() > b.getLeft();
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
                if(jumps[0] < 2 && !hurt[0]) {
                    players[0].setVY(-5);
                    ++jumps[0];
                    block[0] = false;
                }
                break;
            }
            case 37: {
                if(!hurt[0]) {//fix later
                    players[0].setVX(-3);
                    isMoving[0] = true;
                    if(!attack[0])
                        direction[0] = LEFT;
                }
                block[0] = false;
                break;
            }
            case 39: {
                if(!hurt[0]) {//fix later
                    players[0].setVX(3);
                    isMoving[0] = true;
                    if(!attack[0])
                        direction[0] = RIGHT;
                }
                block[0] = false;
                break;
            }
            case 44: {
                if(!attack[0] && !hurt[0])
                    dealDamage[0] = true;
                attack[0] = true;
                block[0] = false;
                break;
            }
            case 46: {
                if(!rangedAttack[0] && !hurt[0])
                    dealDamage[0] = true;
                rangedAttack[0] = true;
                block[0] = false;
                break;
            }
            case 32: {
                if(!block[0])
                {
                    block[0] = true;
                }
                break;
            }
            case 87: {
                if(jumps[1] < 2 && !hurt[1]) {
                    players[1].setVY(-5);
                    ++jumps[1];
                    block[1] = false;
                }
                break;
            }
            case 65: {
                if(!hurt[1]) {
                    players[1].setVX(-3);
                    isMoving[1] = true;
                    if(!attack[0])
                        direction[1] = LEFT;
                }
                block[1] = false;
                break;
            }
            case 68: {
                if(!hurt[1]) {
                    players[1].setVX(3);
                    isMoving[1] = true;
                    if(!attack[0])
                        direction[1] = RIGHT;
                }
                block[1] = false;
                break;
            }
            case 70: {
                if(!attack[1] && !hurt[1])
                    dealDamage[1] = true;
                attack[1] = true;
                block[1] = false;
                break;
            }
            case 71: {
                if(!rangedAttack[1] && !hurt[1])
                    dealDamage[1] = true;
                rangedAttack[1] = true;
                block[1] = false;
                break;
            }
            case 16: {
                if(!block[1])
                {
                    block[1] = true;
                }
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
            case 32: {
                block[0] = false;
                blockTime[0] = 0;
                break;
            }
            case 16: {
                block[1] = false;
                blockTime[1] = 0;
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
        g.setColor(new Color(255, 255, 255));
        g.setFont(new Font("TimesRoman", Font.BOLD, 40));
        g.drawString(score[0] + "%", 600, 200);
        g.setColor(new Color(255, 255, 255));
        g.drawString(score[1] + "%", 750, 200);
    }
}

//gameover screen DO IT ON THE SCREEN?
//game beginning - rules
//block JUST NEED ANIMATION
//respawn MAYBE NOT;
//ranged attacks JUST NEED ANIMATION
//platform bottom fix thing MAYBE NOT
//fix hit stuff
//fix text to look better DONE
//fix attack so you can't wiggle 
//zooming and scrolling 

