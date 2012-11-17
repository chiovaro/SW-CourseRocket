package TeamRocketPower;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.JTextPane;
import java.awt.ScrollPane;
import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.HierarchyBoundsAdapter;
import java.awt.event.HierarchyEvent;

public class AdvisorWindow {

	JFrame frame;
	Main main;
	Lines aLines;
	ArrayList componentsInAList;
	JTextPane classInfoPane;
	JScrollPane scrollPane;
	long lastClick;
	private JButton btnNewButton;
	/**
	 * Create the application.
	 */
	public AdvisorWindow(Main aMain) {
		componentsInAList = new ArrayList();
		main = aMain;
		frame = main.frame;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	public void addToFrame()
	{
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		aLines.repaint();
	}
	
	public void removeFromFrame()
	{
		frame.getContentPane().remove(scrollPane);
	}
	
	private void initialize() {
		scrollPane = main.scrollPane;
		
		

		aLines = new Lines();
		aLines.addHierarchyBoundsListener(new HierarchyBoundsAdapter() {
			@Override
			public void ancestorResized(HierarchyEvent arg0) {
				
				aLines.count = 0;
				for (int x = 0; x < componentsInAList.size(); x++)
				{
					JTextPane aPane = (JTextPane)componentsInAList.get(x);
					aLines.remove(aPane);
					
				}
				//aLines.repaint();
				componentsInAList.removeAll(componentsInAList);
				//aLines.drawLines();
				initCourseBoxes();
				//System.out.println("HERE");
			}
		});
		
		aLines.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 11, 162, 124);
		aLines.add(scrollPane_1);
		
		
		classInfoPane = new JTextPane();
		scrollPane_1.setViewportView(classInfoPane);
		
		JButton btnCourseWindow = new JButton("Course Window");
		btnCourseWindow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				main.changeViewToCourseWindow();
			}
		});
		btnCourseWindow.setBounds(182, 11, 162, 23);
		aLines.add(btnCourseWindow);
		
		btnNewButton = new JButton("Rate My Professor");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main.changeViewToRateProfessorWindow();
			}
		});
		btnNewButton.setBounds(182, 40, 162, 23);
		aLines.add(btnNewButton);
		
		
	}
	
	public void initCourseBoxes()
	{
		int count = 0;
		Point lines[][] = new Point[100][2];
		
		
		int width = aLines.getWidth();
		int x = 10;
		int y = 10;
		ArrayList classRows = new ArrayList();
		ArrayList firstRow = new ArrayList();
		classRows.add(firstRow);
		
		ArrayList createdCourseNames = new ArrayList();
		
		for (int z = 0; z < main.myDB.getCourseList().size(); z++)
		{
			Course c = (Course)main.myDB.getCourseList().get(z);
			/*JTextPane txtpnTest = new JTextPane();
			txtpnTest.setText(c.getCourseName());
			txtpnTest.setBounds(x, y, 55, 51);
			*/
			if (c.getPrereqList().size() == 0)
			{
				ArrayList temp = (ArrayList)classRows.get(0);
				temp.add(c);
			}
			else
			{
				int lowestLevel = -1;
				int hits = 0;
				for (int l = 0; l < c.getPrereqList().size(); l++)
				{
					String prereq = c.getPrereqList().get(l).toString();
					
					for (int k = 0; k < classRows.size(); k++)
					{
						ArrayList list = (ArrayList)classRows.get(k);
						for (int t = 0; t < list.size(); t++)
						{
							Course c1 = (Course)list.get(t);
							String c1Name = c1.getDepartment() + " " + c1.getCourseNumber();
							if (c1Name.equalsIgnoreCase(prereq))
							{
								hits++;
								lowestLevel = k;
							}
						}		
					}
				}
				//System.out.println(hits + ":" + c.getPrereqList().size());
				if (hits == c.getPrereqList().size())
				{
					if (lowestLevel+1 >= classRows.size())
					{
						ArrayList newList = new ArrayList();
						newList.add(c);
						classRows.add(newList);
					}
					else
					{
						ArrayList newList = (ArrayList)classRows.get(lowestLevel+1);
						newList.add(c);
					}
				}
				else
				{
					if (lowestLevel > -1 && lowestLevel+1 < classRows.size())
					{
						ArrayList newList = (ArrayList)classRows.get(lowestLevel+1);
						newList.add(c);
					}
					else
					{
						ArrayList next = new ArrayList();
						next.add(c);
						classRows.add(next);
					}
					for (int x2 = 0; x2 < c.getPrereqList().size(); x2++)
					{
						String shortStr = c.getPrereqList().get(x2).toString().trim();
						Course c2 = this.getCourseShortName(shortStr);
						
						
						if (c2 == null)
						{
							Course newCourse = new Course();
							boolean make = true;
							for (int r = 0; r < createdCourseNames.size(); r++)
							{
								String s = createdCourseNames.get(r).toString();
								
								if (s.trim().equalsIgnoreCase(shortStr.trim()))
								{
									make = false;
								}
							}
							
							if (make)
							{
								//System.out.println(">" + shortStr + "<");
								String splits[] = shortStr.split(" ");
								if (splits.length > 1)
								{
									createdCourseNames.add(shortStr);
									
									try
									{
										newCourse.setCourseNumber(Integer.parseInt(splits[1]));
										newCourse.setCourseSubject(splits[0]);
										newCourse.setCourseName(splits[0] +  " " + splits[1]);
										if (lowestLevel > -1 && lowestLevel+1 < classRows.size())
										{
											ArrayList newList = (ArrayList)classRows.get(lowestLevel);
											newList.add(newCourse);
										}	
										else
										{
											ArrayList next = (ArrayList)classRows.get(classRows.size()-1);
											next.add(newCourse);
										}
									}
									catch(NumberFormatException e)
									{
										
									}
								}
							}
						}
						
					}
					
				}
				
			}				
		}
		
		ArrayList addPanes = new ArrayList();
		for (int h = 0; h < classRows.size(); h++)
		{
			
			ArrayList list = (ArrayList)classRows.get(h);
			int midX = 512;
			int screenWidth = aLines.getBounds().width;
			int heightSpacing = 140;
			int spacing = 80;
			//System.out.println(h + ":" + list.size());
			for (int e = 0; e < list.size(); e++)
			{
				Course c = (Course)list.get(e);
				
				JTextPane txtpnTest = new JTextPane();
				txtpnTest.setText(c.getCourseName());
				txtpnTest.setBounds(midX-((list.size()*spacing)/2)+(e * spacing), y+(h*heightSpacing), 75, 51);
				
				aLines.setPreferredSize(new Dimension(aLines.getWidth(), txtpnTest.getBounds().y+txtpnTest.getBounds().height+50));
				scrollPane.getViewport().setAlignmentY(0);
				
				//txtpnTest.setBounds(y+(h*heightSpacing), midX-((list.size()*spacing)/2)+(e * spacing), 55, 51);
				addPanes.add(txtpnTest);
				for (int x2 = 0; x2 < c.getPrereqList().size(); x2++)
				{
					String shortStr = c.getPrereqList().get(x2).toString();
					Course c2 = this.getCourseShortName(shortStr);	
					if (c2 == null)
					{
						c2 = new Course();
						c2.setCourseName(shortStr);
					}
					if (c2 != null)
					{
						for (int u = 0; u < classRows.size(); u++)
						{
							ArrayList list2 = (ArrayList)classRows.get(u);
						
							for (int i = 0; i < list2.size(); i++)
							{
								Course c22 = (Course)list2.get(i);
								if (c22.getCourseName().equalsIgnoreCase(c2.getCourseName()))
								{
									Point newPoint = new Point(midX-((list2.size()*spacing)/2)+(i * spacing)+75/2, y+(u*heightSpacing)+51);
									Point newPoint2 = new Point(midX-((list.size()*spacing)/2)+(e * spacing)+75/2, y+(h*heightSpacing));
									aLines.coords[aLines.count][0] = newPoint.x;
				                    aLines.coords[aLines.count][1] = newPoint.y;
				                    aLines.count++;
				                    aLines.coords[aLines.count][0] = newPoint2.x;
				                    aLines.coords[aLines.count][1] = newPoint2.y;
				                    aLines.count++;
								}
							}
						}
					}
				}	
				
				
				txtpnTest.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						if (classInfoPane == null)
							classInfoPane = new JTextPane();
						
						for (int r = 0; r < componentsInAList.size(); r++)
						{
							JTextPane aPane = (JTextPane)componentsInAList.get(r);
							aPane.setBackground(Color.white);
						}

						updatePaneColors();
						
						//aLines.remove(classInfoPane);
						JTextPane aPane = (JTextPane)arg0.getSource();
						Course c = getCourseForName(aPane.getText().toString());
						
						if (c != null)
						{
							String textMe = c.getDepartment() + " " + c.getCourseNumber()
											+ "\n" + c.getCourseName();
							
							for (int x = 0; x < c.getPrereqList().size(); x++)
							{
								textMe += "\n" + c.getPrereqList().get(x).toString();
							}
							
							classInfoPane.setText(textMe);
							classInfoPane.setCaretPosition(0);
						}
						if (c == null)
						{
							c = getCourseShortName(c.getDepartment() + " " + c.getCourseNumber());
						}
						if (c != null)
						{
							changePaneColor(c);
							aPane.setBackground(Color.blue);
						}
						
						long one = System.nanoTime();
						if (lastClick == 0)
							lastClick = one;
						else
						{
							
							long max = Long.parseLong("268469946");
							if (one-lastClick < (long)max)
							{
								main.changeViewToCourseWindow(c.getDepartment(), c.getCourseNumber());
							}
							lastClick = one;
						}
					}
					
				});
				
			}	
		}
		
		for (int x3 = 0; x3 < addPanes.size(); x3++)
		{
			JTextPane aPane = (JTextPane)addPanes.get(x3);
			aLines.add(aPane, 0);
		
		}
		componentsInAList.addAll(addPanes);

		updatePaneColors();
		aLines.repaint();
		
//		aLines.drawLines();
		
	}
	
	public void resetColors()
	{
		for (int x3 = 0; x3 < componentsInAList.size(); x3++)
		{
			JTextPane pane = (JTextPane)componentsInAList.get(x3);
			pane.setBackground(Color.WHITE);
		}
	}
	
	public void updatePaneColors()
	{
		Student myStudent = main.currentStudent;
		
		for (int x3 = 0; x3 < componentsInAList.size(); x3++)
		{

			JTextPane pane = (JTextPane)componentsInAList.get(x3);
			String paneText = pane.getText();
			Course subCourse = getCourseForName(paneText);
			if (subCourse == null)
				subCourse = getCourseShortName(paneText);
			
			String subStr = "";
			if (subCourse != null)
			{
				
				subStr = subCourse.getDepartment() + " " + subCourse.getCourseNumber();
			}
			else
				subStr = paneText;
			
			for (int e = 0; e < myStudent.getRegisteredClasses().size(); e++)
			{
				Section s = (Section)myStudent.getRegisteredClasses().get(e);
				Course c = getCourseForCRN(s.getCRN());
				String cStr = c.getDepartment() + " " + c.getCourseNumber();
				if (cStr.equalsIgnoreCase(subStr))
				{
					pane.setBackground(Color.green);
				}
			}	
		}		
		

	}
	
	public void changePaneColor(Course c)
	{
		for (int d = 0; d < c.getPrereqList().size(); d++)
		{
			String aStr = c.getPrereqList().get(d).toString();
			for (int x3 = 0; x3 < componentsInAList.size(); x3++)
			{

				JTextPane pane = (JTextPane)componentsInAList.get(x3);
				String paneText = pane.getText();
				Course subCourse = getCourseForName(paneText);
				if (subCourse == null)
					subCourse = getCourseShortName(paneText);
				
				String subStr = "";
				if (subCourse != null)
				{
					
					subStr = subCourse.getDepartment() + " " + subCourse.getCourseNumber();
				}
				else
					subStr = paneText;
				
				if (subStr.equalsIgnoreCase(aStr))
				{
					if (subCourse != null)
						changePaneColor(subCourse);
					pane.setBackground(Color.RED);
				}
				
			}
		}
		updatePaneColors();

	}
	
	public Course getCourseForName(String aName)
	{
		
		for (int l = 0; l < main.myDB.getCourseList().size(); l++)
		{
			Course c = (Course)main.myDB.getCourseList().get(l);
			if (c.getCourseName().equalsIgnoreCase(aName))
			{
				return c;
			}
		}
	
		return null;
	}
	
	public Course getCourseShortName(String aName)
	{
		
		for (int l = 0; l < main.myDB.getCourseList().size(); l++)
		{
			Course c = (Course)main.myDB.getCourseList().get(l);
			String cName = c.getDepartment() + " " + c.getCourseNumber();
			if (cName.equalsIgnoreCase(aName))
			{
				return c;
			}
		}
	
		return null;
	}
	
	public Course getCourseForCRN(int aCRN)
	{
		Section s = null;
		for (int x = 0; x < main.myDB.getSectionList().size(); x++)
		{
			Section aSec = (Section)main.myDB.getSectionList().get(x);
			if (aSec.getCRN() == aCRN)
			{
				s = aSec;
			}
		}
		if (s != null)
		{
			for (int l = 0; l < main.myDB.getCourseList().size(); l++)
			{
				Course c = (Course)main.myDB.getCourseList().get(l);
				if (c.getDepartment().equalsIgnoreCase(s.getCourseName())
						&& c.getCourseNumber() == s.getCourseNumber())
				{
					return c;
				}
			}
		}
		return null;
	}
}

class PrereqNode
{
	public ArrayList children;
	public Course myCourse;
	public PrereqNode(Course aCourse)
	{
		children = new ArrayList();
		myCourse = aCourse;
	}
	public void addChild(Course c)
	{
		children.add(c);
	}
}