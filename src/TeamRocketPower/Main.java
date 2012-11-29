package TeamRocketPower;

import java.util.ArrayList;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*; 
/**
Main: Initializes the windows and switches the views.
*/
public class Main{

	static public DatabaseManager myDB;
	static public JComboBox departments;
	static public JComboBox courses;
	static public JComboBox sections;
	public Student currentStudent;
	static public JFrame frame;
	public JScrollPane scrollPane;
	public AdvisorWindow advisorWindow;
	public CourseWindow courseWindow;
	public RateMyProfessorWindow professorWindow;
	public int windowNumber;
	public static void main(String[] args) {
		frame = new JFrame("Course Rocket");
		frame.setBounds(100, 100, 658, 530);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Main window = new Main();
		frame.setVisible(true);		
	}
	

	
	public Main()
	{
		myDB = new DatabaseManager();
		currentStudent = (Student)myDB.getStudents().get(0);
		scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(502, 800));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		
		professorWindow = new RateMyProfessorWindow(this);
		courseWindow = new CourseWindow(this);
		advisorWindow = new AdvisorWindow(this);
		advisorWindow.initCourseBoxes();

		changeViewToCourseWindow();
		
	}
	
	public void changeViewToAdvisorWindow()
	{
		advisorWindow.resetColors();
		advisorWindow.updatePaneColors();
		scrollPane.setViewportView(advisorWindow.aLines);
		frame.getContentPane().repaint();
	}
	public void changeViewToCourseWindow()
	{
		scrollPane.setViewportView(courseWindow.drawPanel);
		courseWindow.removeAllSectionsFromView();
		courseWindow.loadStudent(currentStudent);
		frame.getContentPane().repaint();
	}
	
	public void changeViewToCourseWindow(String aDepartment, int aCourseNumber)
	{
		scrollPane.setViewportView(courseWindow.drawPanel);
		courseWindow.removeAllSectionsFromView();
		courseWindow.loadStudent(currentStudent);
		courseWindow.setScheduleFocus(aDepartment, aCourseNumber);
		frame.getContentPane().repaint();
	}
	
	public void changeViewToRateProfessorWindow()
	{
		scrollPane.setViewportView(professorWindow.panel);
		professorWindow.initList();
		frame.getContentPane().repaint();
	}
	
	
	
}