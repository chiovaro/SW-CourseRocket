package TeamRocketPower;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;


/**
RMPPaint draws the graphs of the information for the teacher.
*/
public class RMPPaint extends JPanel{

	float toughness = 0;
	float hot = 0;
	float likability = 0;
	float averageGrades = 0;
	
	float transitionToToughness = 0;
	float transitionToHot = 0;
	float transitionToLikability = 0;
	float transitionToGrades = 0;
	
	public RMPPaint()
	{
		JPanel panel = new JPanel();
        panel.setLayout(null);
	}
	
	public boolean stillRendering()
	{
		boolean finished = true;
		if (transitionToToughness > toughness)
		{
			toughness += 3;
			if (toughness >= transitionToToughness)
			{
				toughness = transitionToToughness;
			}
			else
			{
				finished = false;
			}
		}
		
		if (transitionToHot > hot)
		{
			hot += 3;
			if (hot >= transitionToHot)
			{
				hot = transitionToHot;
			}
			else
			{
				finished = false;
			}
		}
		
		
		if (transitionToLikability > likability)
		{
			likability += 3;
			if (likability >= transitionToLikability)
			{
				likability = transitionToLikability;
			}
			else
			{
				finished = false;
			}
		}
		
		if (transitionToGrades > averageGrades)
		{
			averageGrades += 3;
			if (averageGrades >= transitionToGrades)
			{
				averageGrades = transitionToGrades;
			}
			else
			{
				finished = false;
			}
		}
		
		return true;
	}
	
	/**
	Repaints the graph based on the information in the level
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
        
        int bottom = getBounds().height;
        int width = getBounds().width;
        
        
        g2d.setColor(Color.GRAY);
        g2d.fillRect(50, bottom-10-bottom+100, 50, bottom-100);
        g2d.setColor(Color.RED);
        g2d.fillRect(50, bottom-10-(int)((bottom-100)*(toughness/10.0f)), 50, (int)((bottom-100)*(toughness/10.0f)));
        
        g2d.setColor(Color.GRAY);
        g2d.fillRect(width/2 - 25, bottom-10-bottom+100, 50, bottom-100);
        g2d.setColor(Color.RED);
        g2d.fillRect(width/2 - 25, bottom-10-(int)((bottom-100)*(likability/10.0f)), 50, (int)((bottom-100)*(likability/10.0f)));
        
        g2d.setColor(Color.GRAY);
        g2d.fillRect(width - 100, bottom-10-bottom+100, 50, bottom-100);
        g2d.setColor(Color.RED);
        g2d.fillRect(width - 100, bottom-10-(int)((bottom-100)*(averageGrades/100.0f)), 50, (int)((bottom-100)*(averageGrades/100.0f)));
        
        
        g2d.setColor(Color.GRAY);
        g2d.fillRect(10, 20, width-20, 50);
        g2d.setColor(Color.RED);
        g2d.fillRect(10, 20, (int)((width-20)*(hot/10.0f)), 50);
        
        
        g2d.setColor(Color.BLACK);
        g2d.drawString("Toughness",	48, bottom);
        g2d.drawString("Likability",	width/2-25, bottom);
        g2d.drawString("Average Grades",	width-100-10, bottom);
        g2d.drawString("Hotness", width/2-25, 15);

    }
	
	
}
