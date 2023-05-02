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
    Display display = new Display(500, 500);
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

    //normal sprites
    sprites.add(new Sprite(Math.random() * (width - 50),
                           Math.random() * (height - 50), 50, 50, "square.png"));
    sprites.add(new Sprite(Math.random() * (width - 50),
                           Math.random() * (height - 50), 50, 50, "square.png"));

    //mobile sprites
   dir = Math.random() * 2 * Math.PI;
   sprites.add(new MobileSprite(Math.random() * (width - 50),
                                Math.random() * (height - 50), 50, 50, "circle.png",
                                Math.cos(dir), Math.sin(dir)));
   dir = Math.random() * 2 * Math.PI;
   sprites.add(new MobileSprite(Math.random() * (width - 50),
                                Math.random() * (height - 50), 50, 50, "circle.png",
                                Math.cos(dir), Math.sin(dir)));

    //heavy sprites
//    dir = Math.random() * 2 * Math.PI;
//    sprites.add(new HeavySprite(Math.random() * (width - 50),
//                                 Math.random() * (height - 50), 50, 50, "triangle.png",
//                                 Math.cos(dir), Math.sin(dir)));
//    dir = Math.random() * 2 * Math.PI;
//    sprites.add(new HeavySprite(Math.random() * (width - 50),
//                                 Math.random() * (height - 50), 50, 50, "triangle.png",
//                                 Math.cos(dir), Math.sin(dir)));
  }
  
  public void stepAll()
  {
    for (int i = 0; i < sprites.size(); i++)
    {
      Sprite s = sprites.get(i);
      
      //insert code here
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
    System.out.println("keyPressed:  " + key);
  }
  
  public void keyReleased(int key)
  {
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