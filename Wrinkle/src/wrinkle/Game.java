/*
 * Game.java
 *
 * Created on February 8, 2011, 9:12 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */


package wrinkle;

import java.awt.*;

import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.awt.event.*;
//import java.awt.geom.*;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.io.File;



/**
 *
 * @author a.bresee
 */



class Game extends JPanel implements KeyListener {


    Wrinkle wrinkle;
    ArrayList<Terrain> terrains;

    BufferedImage[] backgrounds;
    
    BufferedImage fgcanvas;
    BufferedImage bgcanvas1;
    BufferedImage bgcanvas2;
    BufferedImage bgcanvas3;
    BufferedImage buff;

    boolean bg1changed;
    boolean bg2changed;
    boolean bg3changed;

    boolean running;

    Graphics2D fg;
    Graphics2D bg1;
    Graphics2D bg2;
    Graphics2D bg3;
    Graphics2D buffg;
    Graphics2D panel;

    public Game()
    {
        setIgnoreRepaint(true);
        addKeyListener(this);
        setFocusable(true);

        terrains=new ArrayList<Terrain>();

        String prefix="Data/Images/background/";
        backgrounds=new BufferedImage[3];
        try
        {
            backgrounds[2]=ImageIO.read(new File(prefix+"bg1.png"));
            backgrounds[1]=ImageIO.read(new File(prefix+"bg2.png"));
            backgrounds[0]=ImageIO.read(new File(prefix+"bg3.png"));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        //fgcanvas  = new BufferedImage(Global.WinX,Global.WinY,BufferedImage.TYPE_INT_RGB);
        //bgcanvas1 = new BufferedImage(Global.WinX,Global.WinY,BufferedImage.TYPE_INT_RGB);
        //bgcanvas2 = new BufferedImage(Global.WinX,Global.WinY,BufferedImage.TYPE_INT_RGB);
        //bgcanvas3 = new BufferedImage(Global.WinX,Global.WinY,BufferedImage.TYPE_INT_RGB);
        buff        = new BufferedImage(Global.WinX,Global.WinY,BufferedImage.TYPE_INT_RGB);

        //fg          = (Graphics2D)fgcanvas.createGraphics();
        //bg1         = (Graphics2D)bgcanvas1.createGraphics();
        //bg2         = (Graphics2D)bgcanvas2.createGraphics();
        //bg3         = (Graphics2D)bgcanvas3.createGraphics();
        buffg       = (Graphics2D)buff.createGraphics();
        //panel       = (Graphics2D)this.getGraphics();

        //bg1changed=false;
        //bg2changed=false;
        //bg3changed=false;

        running=true;

        wrinkle=new Wrinkle(Global.WinX/4+1,Global.WinY-200);

        terrains.add(new Terrain(0,Global.WinY-200+wrinkle.getHeight(),
                            400,400,Color.GREEN));
        terrains.add(new Terrain(500,Global.WinY-200+wrinkle.getHeight(),
                            300,400,Color.RED));
        terrains.add(new Terrain(300,400,100,100));
        terrains.add(new Terrain(200,200,50,50));
        terrains.add(new Terrain(Global.WinX,Global.WinY-200+wrinkle.getHeight(),
                            400,400,Color.MAGENTA));
        terrains.add(new Terrain(1200,Global.WinY-200+wrinkle.getHeight(),
                            400,400,Color.GREEN));
        terrains.add(new Terrain(1800,Global.WinY-200+wrinkle.getHeight(),
                            400,400,Color.GREEN));
    }

    public void keyTyped(KeyEvent e)
    {

    }

    public void keyPressed(KeyEvent e)
    {
        int key=e.getKeyCode();

        switch(key)
        {
            case KeyEvent.VK_SPACE:
                wrinkle.jump();
                break;
            case KeyEvent.VK_RIGHT:
                wrinkle.goRight();
                break;

            case KeyEvent.VK_LEFT:
                wrinkle.goLeft();
                break;

        }

    }

    public void keyReleased(KeyEvent e)
    {
        int key=e.getKeyCode();
        if (key==KeyEvent.VK_RIGHT)
        {
            wrinkle.unGoRight();
        }
        else if(key == KeyEvent.VK_LEFT)
        {
            wrinkle.unGoLeft();
        }

    }

    void drawToForeground()
    {

       
//        fg.setStroke(new BasicStroke(5));
//        fg.setColor(Color.CYAN);
//        fg.fillRect(0,0,Global.WinX,Global.WinY);
        
        for(int i=0;i<terrains.size();++i)
        {
            terrains.get(i).draw(buffg);
        }
        wrinkle.draw(buffg);

    }
    void drawBackground()
    {
        buffg.clearRect(0,0,Global.WinX,Global.WinY);
        for(int i=0;i<backgrounds.length;++i)
        {
           int x=(Math.round(-Global.coeff[i]*Global.OffsetX))%Global.WinX;
           int y=(Math.round(-Global.coeff[i]*Global.OffsetY));
           

           if(x>=0)
           {
               x=x-Global.WinX;
           }
           if(i==1)
            {
            System.out.println(i+"'s x is: "+x);
            }
           buffg.drawImage(backgrounds[i],x,y,this);
        }

        
    }
    
//    void drawToBuff()
//    {
//        buffg.drawImage(bgcanvas3,0,0, this);
//        buffg.drawImage(bgcanvas2,0,0, this);
//        buffg.drawImage(bgcanvas1,0,0, this);
//        buffg.drawImage(fgcanvas,0,0, this);
//    }

    @Override
    public void paint(Graphics g)
    {
        
        //g.drawImage(buff, 0,0, this);
    }
    void go()
    {

    wrinkle.update(terrains);
    drawBackground();
    drawToForeground();
    //drawToBuff();
    panel=(Graphics2D)this.getGraphics();
    panel.drawImage(buff,0,0, this);
    }
    void loop()
    {

       while(running)
       {
            long time=System.currentTimeMillis();

            go();
            
            time=System.currentTimeMillis()-time;
            if(time<Global.timeStep)
            {
                try
                {
                    Thread.sleep(Global.timeStep-time);
                }

                catch(Exception e)
                {
                    e.printStackTrace();
                }
           }
            
        }
    }
}
