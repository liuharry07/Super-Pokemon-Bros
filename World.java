import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class World
{
  public static void main(String[] args)
  {
      run();
  }
  
  public static void run()
  {
    Display display = new Display(1440, 900);
    display.run();
  }
  
  private ArrayList<Sprite> sprites;
  private int width;
  private int height;
  
  public World(int w, int h)
  {
    width = w;
    height = h;
    
    sprites = new ArrayList<Sprite>();
    double dir;

    //stage
    sprites.add(new Sprite(300,
                           200, 900, 600, "stage.png"));
    //make a hitbox for stage

    //charmander
   dir = Math.random() * 2 * Math.PI;
   sprites.add(new HeavySprite(Math.random() * (width - 50),
                                Math.random() * (height - 50), 38, 42, "1.png",
                                0, Math.sin(dir)));  }
  public void stepAll()
  {
    for (int i = 0; i < sprites.size(); i++)
    {
      Sprite s = sprites.get(i);
      s.step(this);
    }
  }
  
  public int getWidth()
  {
    return width;
  }
  
  public int getHeight()
  {
    return height;
  }
  
  public int getNumSprites()
  {
    return sprites.size();
  }
  
  public Sprite getSprite(int index)
  {
    return sprites.get(index);
  }

  public void mouseClicked(int x, int y)
  {
    System.out.println("mouseClicked:  " + x + ", " + y);
  }
  
  public void keyPressed(int key)
  {
    switch(key) {
        case 38: {
            ((HeavySprite)sprites.get(1)).setVY(-3);
            break;
        }
        case 37: {
            ((MobileSprite)sprites.get(1)).setXY(-3);
            break;
        }
        case 39: {
            ((MobileSprite)sprites.get(1)).setXY(3);
            break;
        }
    }
    System.out.println("keyPressed:  " + key);
  }
  
  public void keyReleased(int key)
  {
    switch(key) {
        case 37: {
            if(((MobileSprite)sprites.get(1)).getXY() < 0)
                ((MobileSprite)sprites.get(1)).setXY(0);
            break;
        }
        case 39: {
            if(((MobileSprite)sprites.get(1)).getXY() > 0)
                ((MobileSprite)sprites.get(1)).setXY(0);
            break;
        }
    }
    System.out.println("keyReleased:  " + key);
  }
  
  public String getTitle()
  {
    return "World";
  }

  public void paintComponent(Graphics g)
  {
    g.setColor(Color.BLACK);
    g.fillRect(0, 0, width, height);
    for (int i = 0; i < sprites.size(); i++)
    {
      Sprite sprite = sprites.get(i);
      g.drawImage(Display.getImage(sprite.getImage()),
                  (int)sprite.getLeft(), (int)sprite.getTop(),
                  sprite.getWidth(), sprite.getHeight(), null);
    }
  }
}