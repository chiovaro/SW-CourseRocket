package TeamRocketPower;

import java.util.ArrayList;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*; 

public class Main{

	static public DatabaseManager myDB;
	static public JComboBox departments;
	static public JComboBox courses;
	static public JComboBox sections;
	
	public static void main(String[] args) {

		myDB = new DatabaseManager();
		Main window = new Main();
		CourseWindow windowz = new CourseWindow(window);
		windowz.frame.setVisible(true);
		
	}
	
	public Main()
	{
		/*JFrame f = new JFrame("Course Rocket");
		f.getContentPane().setLayout(null);
		JLabel lbl1 = new JLabel("Departments:");
		JLabel lbl2 = new JLabel("Courses:");
		JLabel lbl3 = new JLabel("Sections:");
		
		ArrayList depts = new ArrayList();
		for (int x = 0; x < myDB.getCourseList().size(); x++)
		{
			Course c1 = (Course)myDB.getCourseList().get(x);
			if (!depts.contains(c1.getDepartment()))
			{
				depts.add(c1.getDepartment());
			}
		}
		
		String classes[] = new String[depts.size()+1];
		classes[classes.length-1] = "Test";
		for (int x = 0; x < depts.size(); x++)
		{
			classes[x] = depts.get(x).toString();
		}
		
		departments = new JComboBox(classes);
		courses = new JComboBox();
		sections = new JComboBox();
		
		
		lbl1.setBounds(5,10,100,20);
		lbl2.setBounds(5,50,100,20);
		lbl3.setBounds(5,90,100,20);
		
		departments.setBounds(5,30,100,20);
		
		departments.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == 1)
				{
					String addCourses[] = new String[myDB.getCourseList().size()];
					courses.removeAllItems();
					
					for (int x = 0; x < myDB.getCourseList().size(); x++)
					{
						Course c = (Course)myDB.getCourseList().get(x);
						addCourses[x] = c.getDepartment() + " " + c.getCourseNumber();
						courses.addItem(addCourses[x]);
					}
				}
			}
		});
		
		
		courses.setBounds(5,70,100,20);
		courses.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == 1)
				{
					System.out.println(arg0.getItem());
				}
			}
		});
		sections.setBounds(5,110,100,20);
		sections.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == 1)
				{
					System.out.println(arg0.getItem());
				}
			}
		});
		
		
		
		f.add(lbl1);
		f.add(lbl2);
		f.add(lbl3);
		f.add(departments);
		f.add(courses);
		f.add(sections);
		
		
		
		f.setSize(640, 480);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public void actionPerformed(ActionEvent e) 
	{
		System.out.println(e.getSource());
		/*
		//System.out.println(e.getSource());
		if (e.getSource().getClass() == JComboBox.class);
		{
			JComboBox test = (JComboBox)e.getSource();
			if (test.equals(departments))
			{
								
			}
			else if (test.equals(courses))
			{
				String addCourses[] = new String[myDB.getCourseList().size()+1];
				sections.removeAllItems();
				
				for (int x = 0; x < myDB.getSectionList().size(); x++)
				{
					Section s = (Section)myDB.getSectionList().get(x);
					addCourses[x] = "" + s.getCRN();
					sections.addItem(addCourses[x]);
				}				
			}
		}*/
	}

	

}
