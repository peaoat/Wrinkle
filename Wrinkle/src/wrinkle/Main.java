/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wrinkle;

import javax.swing.JFrame;
import java.awt.Insets;
public class Main {  
    
    public static void main(String[] args) {
        JFrame window=new JFrame("Wrinkle... th-t-the Dinosaur!");

        Game g=new Game();
        
        window.addKeyListener(g);
        window.add(g);
        window.getContentPane().add(g);
        window.setSize(Global.WinX,Global.WinY);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        Insets off=window.getInsets();
        int xoff=off.left+off.right;
        int yoff=off.top+off.bottom;
        window.setSize(Global.WinX+xoff, Global.WinY+yoff);


        g.loop();
    }

}
