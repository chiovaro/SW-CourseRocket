package TeamRocketPower;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
/**
Lines: A canvas for drawing lines on
*/
public class Lines extends JPanel {

    int coords[][];
    int count;

    public Lines() {
        initUI();
    }
    
    public final void initUI() {

        coords = new int[500][2];
        count = 0;

        JPanel panel = new JPanel();
        panel.setLayout(null);

        setSize(300, 200);
    }
    /**
	Repaints the scene
 */
    public void paint(Graphics g)
    {
    	super.paint(g);
    	Graphics2D g2d = (Graphics2D) g;

        int w = getWidth();
        int h = getHeight();

        RenderingHints rh =
                new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        g2d.setRenderingHints(rh);

        
        for (int i = 0; i < count - 1; i+=2) 
        {
                g2d.drawLine(coords[i][0], coords[i][1],
                        coords[i+1][0], coords[i+1][1]);         
        }

    }

    /**
	Draws all the lines that have been added to the view
 */
    public void drawLines() {

    	Graphics g = this.getGraphics();

        Graphics2D g2d = (Graphics2D) g;

        int w = getWidth();
        int h = getHeight();

        RenderingHints rh =
                new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        g2d.setRenderingHints(rh);

        
        for (int i = 0; i < count - 1; i+=2) 
        {
                g2d.drawLine(coords[i][0], coords[i][1],
                        coords[i+1][0], coords[i+1][1]);         
        }
        

    }

}