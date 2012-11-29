package TeamRocketPower;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;

/**
RateMyProfessorWindow: GUI for the rate my professor information.
*/
public class RateMyProfessorWindow {

	JFrame frame;
	JPanel panel;
	Main main;
	JPanel ratingPanel;
	JComboBox CourseComboBox;
	JComboBox TeacherComboBox;
	JComboBox DepartmentComboBox;
	ArrayList ratingPanelObjects;
	RMPPaint rmpPaint;
	JComboBox rateCourseComboBox;
	RateMyProfessor currentRating;
	ButtonGroup tough;
	ButtonGroup hot;
	ButtonGroup like;
	JTextField grade;
	private JButton btnCourseWindow;
	private JButton btnNewButton;
	
	public RateMyProfessorWindow(Main aMain) {
		main = aMain;
		rmpPaint = new RMPPaint();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = main.frame;
		ratingPanelObjects = new ArrayList();
		panel = new JPanel();
		panel.setLayout(null);
		
		JLabel lblDepartment = new JLabel("Department");
		lblDepartment.setBounds(52, 8, 67, 14);
		panel.add(lblDepartment);
		
		
		DepartmentComboBox = new JComboBox();
		DepartmentComboBox.setBounds(124, 5, 48, 20);
		panel.add(DepartmentComboBox);
		ArrayList depts = new ArrayList();
		for (int x = 0; x < main.myDB.getCourseList().size(); x++)
		{
			Course c1 = (Course)main.myDB.getCourseList().get(x);
			if (!depts.contains(c1.getDepartment()))
			{
				depts.add(c1.getDepartment());
			}
		}
		
		String classes[] = new String[depts.size()+1];
		classes[0] = "";
		for (int x = 0; x < depts.size(); x++)
		{
			classes[x+1] = depts.get(x).toString();
		}
		DepartmentComboBox.setModel(new DefaultComboBoxModel(classes));
		
		DepartmentComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				//System.out.println("HERE");
				if (arg0.getStateChange() == 1)
				{
					String addCourses[] = new String[main.myDB.getCourseList().size()+1];
					CourseComboBox.removeAllItems();
					CourseComboBox.addItem("");
					for (int x = 0; x < main.myDB.getCourseList().size(); x++)
					{
						Course c = (Course)main.myDB.getCourseList().get(x);
						if (c.getDepartment().equalsIgnoreCase(arg0.getItem().toString()))
						{
							addCourses[x+1] = c.getDepartment() + " " + c.getCourseNumber();
							CourseComboBox.addItem(addCourses[x+1]);
						}
					}
					reevaluateTeacherList();
				}
			}
		});
		
		JLabel lblCourse = new JLabel("Course");
		lblCourse.setBounds(182, 8, 44, 14);
		panel.add(lblCourse);
		
		CourseComboBox = new JComboBox();
		CourseComboBox.setBounds(225, 5, 80, 20);
		panel.add(CourseComboBox);
		CourseComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				//System.out.println("HERE");
				if (arg0.getStateChange() == 1)
				{
					reevaluateTeacherList();
				}
			}
		});
		
		
		JLabel lblTeacher = new JLabel("Teacher");
		lblTeacher.setBounds(315, 8, 67, 14);
		panel.add(lblTeacher);
		
		TeacherComboBox = new JComboBox();
		TeacherComboBox.setBounds(365, 5, 134, 20);
		TeacherComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == 1)
				{
					updateProfessorRatingInformation();
				}
			}
		});
		
		panel.add(TeacherComboBox);
		
		ratingPanel = new JPanel();
		//ratingPanel.setBackground(Color.WHITE);
		ratingPanel.setBounds(124, 70, 460, 297);
		//panel.add(ratingPanel);
		panel.add(rmpPaint);
		rmpPaint.setBounds(124, 70, 384, 297);
		rmpPaint.setBackground(Color.WHITE);
		
		
		
		
		rateCourseComboBox = new JComboBox();
		rateCourseComboBox.removeAll();
		for (int x = 0; x < main.currentStudent.getRegisteredClasses().size(); x++)
		{
			Section s = (Section)main.currentStudent.getRegisteredClasses().get(x);
			if (!main.currentStudent.getCompletedRatings().contains(s))
				rateCourseComboBox.addItem(s.getCourseName() + " " + s.getCourseNumber());
		}
		rateCourseComboBox.setBounds(10, 70, 109, 20);
		panel.add(rateCourseComboBox);
		
		JLabel lblRateCourses = new JLabel("Rate Courses");
		lblRateCourses.setBounds(20, 45, 80, 14);
		panel.add(lblRateCourses);
		
		JButton btnBeginRating = new JButton("Begin Rating");
		btnBeginRating.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int x = 0; x < main.currentStudent.getRegisteredClasses().size(); x++)
				{
					Section s = (Section)main.currentStudent.getRegisteredClasses().get(x);
					String sKey = s.getCourseName() + " " + s.getCourseNumber();
					if (sKey.equalsIgnoreCase(rateCourseComboBox.getSelectedItem().toString()))
					{
						currentRating = s.getTeacherReview();
						panel.remove(rmpPaint);
						panel.add(ratingPanel);
						panel.repaint();
						createRatingBox();
					}
				}
			}
		});
		btnBeginRating.setBounds(10, 93, 109, 23);
		panel.add(btnBeginRating);
		
		btnCourseWindow = new JButton("Course Window");
		btnCourseWindow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main.changeViewToCourseWindow();
			}
		});
		btnCourseWindow.setBounds(124, 36, 134, 23);
		panel.add(btnCourseWindow);
		
		btnNewButton = new JButton("Advisment Window");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main.changeViewToAdvisorWindow();
			}
		});
		btnNewButton.setBounds(268, 36, 159, 23);
		panel.add(btnNewButton);
		
		
		

	}
	
	
	/**
	Creates the rating box layout
 */
	public void createRatingBox()
	{
		removeAllratingPanelObjects();
		int offset = 75;
		for (int x = 0; x < 3; x++)
		{
			ButtonGroup toughGroup = null;
			JLabel newLabel = null;
			switch (x)
			{
			case 0:
				toughGroup = tough;
				newLabel = new JLabel("Toughness");
				newLabel.setBounds(10, 10 + offset + 40*x, 80, 20);
				break;
			case 1:
				toughGroup = like;
				newLabel = new JLabel("Likability");
				newLabel.setBounds(10, 10 + offset + 40*x, 80, 20);
				break;
			case 2:
				toughGroup = hot;
				newLabel = new JLabel("Hotness");
				newLabel.setBounds(10, 10 + offset + 40*x, 80, 20);
				break;
			}
			ratingPanel.add(newLabel);
			ratingPanelObjects.add(newLabel);
			for (int y = 1; y < 11; y++)
			{
				JRadioButton radioButton = new JRadioButton("" + y);
				radioButton.setBounds(10 + ((y-1)*43), 30 + offset + 40*x, 40, 23);
				toughGroup.add(radioButton);
				ratingPanel.add(radioButton);
				ratingPanelObjects.add(radioButton);
			}
			
		}
		
		JButton submitRating = new JButton("Submit Rating");
		submitRating.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean failed = false;
				try
				{
					currentRating.addGrade(Integer.parseInt(grade.getText()));
					int answer = (Integer.parseInt(getSelectedButtonText(hot)));
					currentRating.addHotness(answer);
					currentRating.addNewLikability(Integer.parseInt(getSelectedButtonText(like)));
					currentRating.addNewToughness(Integer.parseInt(getSelectedButtonText(tough)));
				}
				catch (NumberFormatException ex)
				{
					failed = true;
				}
				if (!failed)
				{
					String shortKey = currentRating.getCourseName() + " " + currentRating.CourseNumber();
					Section remove = null;
					for (int x = 0; x < main.currentStudent.getRegisteredClasses().size(); x++)
					{
						Section classSec = (Section)main.currentStudent.getRegisteredClasses().get(x);
						String key2 = classSec.getCourseName() + " " + classSec.getCourseNumber();
						if (shortKey.equalsIgnoreCase(key2))
						{
							remove = classSec;
						}
					}
					main.currentStudent.getCompletedRatings().add(remove);
					
					initList();
					
					removeAllratingPanelObjects();
					panel.remove(ratingPanel);
					panel.add(rmpPaint);					
					panel.repaint();
					repaintScene();
					main.myDB.saveDB();
				}
			}
		});
		JLabel newLabel = new JLabel("Enter Final Grade");
		newLabel.setBounds(150-50, 10 + offset, 100, 20);
		ratingPanel.add(newLabel);
		ratingPanelObjects.add(newLabel);
		submitRating.setBounds(ratingPanel.getBounds().width-125, 2, 115, 23);
		ratingPanel.add(submitRating);
		ratingPanelObjects.add(submitRating);
		this.grade = new JTextField();
		grade.setBounds(200, 10 + offset, 50, 20);
		ratingPanel.add(grade);
		ratingPanelObjects.add(grade);
	}
	/**
	Returns a string of the selected button from a radio button group
 */
	public String getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                return button.getText();
            }
        }

        return null;
    }
	
	/**
	Removes all rating gui information
 */
	public void removeAllratingPanelObjects()
	{
		for (int x = 0; x < ratingPanelObjects.size(); x++){
			Component c = (Component)ratingPanelObjects.get(x);
			ratingPanel.remove(c);
		}
		ratingPanelObjects.removeAll(ratingPanelObjects);
		tough = new ButtonGroup();
		like = new ButtonGroup();
		hot = new ButtonGroup();
	}
	/**
	Repaints the scene
 */
	public void repaintScene()
	{
		rmpPaint.repaint();
	}
	/**
	Initializes the current students list of completed classes
	they still need to rate.
 */
	public void initList()
	{
		rateCourseComboBox.removeAllItems();
		for (int x = 0; x < main.currentStudent.getRegisteredClasses().size(); x++)
		{
			Section s = (Section)main.currentStudent.getRegisteredClasses().get(x);
			if (!main.currentStudent.getCompletedRatings().contains(s))
				rateCourseComboBox.addItem(s.getCourseName() + " " + s.getCourseNumber());
		}
	}
	
	/**
	Updates the professor information based on the selected box settings
 */
	public void updateProfessorRatingInformation()
	{		
		String Department = DepartmentComboBox.getSelectedItem().toString();
		String Course = CourseComboBox.getSelectedItem().toString();
		String Teacher = TeacherComboBox.getSelectedItem().toString();
		ArrayList allRatings = new ArrayList();
		if (!Department.equalsIgnoreCase(""))
		{
			for (int x = 0; x < main.myDB.getSectionList().size(); x++)
			{
				Section s = (Section)main.myDB.getSectionList().get(x);
				String sectionKey = s.getCourseName();
				if (sectionKey.equalsIgnoreCase(Department) && !allRatings.contains(s))
				{
					if (!Course.equalsIgnoreCase(""))
					{
						String sectionNumberKey = s.getCourseName() + " " + s.getCourseNumber();
						if (sectionNumberKey.equalsIgnoreCase(Course))
						{
							if (!Teacher.equalsIgnoreCase(""))
							{
								Teacher sectionTeacher = main.myDB.getTeacherForID(s.getTeacherID());
								if (sectionTeacher.getTeacherName().equalsIgnoreCase(Teacher))
								{
									allRatings.add(s.getTeacherReview());
								}
							}
							else
							{
								allRatings.add(s.getTeacherReview());
							}
						}
					}
					else if (!Teacher.equalsIgnoreCase(""))
					{
						Teacher sectionTeacher = main.myDB.getTeacherForID(s.getTeacherID());
						if (sectionTeacher.getTeacherName().equalsIgnoreCase(Teacher))
						{
							allRatings.add(s.getTeacherReview());
						}
					}
					else
					{
						allRatings.add(s.getTeacherReview());
					}
				}
			}
		}
		
		float toughness = 0;
		float hot = 0;
		float likability = 0;
		float averageGrades = 0;
		int legit = 0;
		for (int x = 0; x < allRatings.size(); x++)
		{
			
			RateMyProfessor rmp = (RateMyProfessor)allRatings.get(x);
			if (rmp.getToughness() != 0 && rmp.getHot() != 0 && rmp.getLikability() != 0)
			{
				legit++;
			}
			toughness += rmp.getToughness();
			hot += rmp.getHot();
			likability += rmp.getLikability();
			ArrayList grades = rmp.getGrades();
			for (int y = 0; y < grades.size(); y++)
			{
				float grade = Float.parseFloat(grades.get(y).toString());
				averageGrades += grade;
			}
		}
		
		toughness /= (float)legit;
		hot /= (float)legit;
		likability /= (float)legit;
		averageGrades /= (float)legit;
		
		
		//toughness = (float)Math.random()*10;
		//hot = (float)Math.random()*10;
		//likability = (float)Math.random()*10;
		//averageGrades = (float)Math.random()*100;
		
		rmpPaint.toughness = toughness;
		rmpPaint.hot = hot;
		rmpPaint.likability = likability;
		rmpPaint.averageGrades = averageGrades;
		
		this.repaintScene();
		
	}
	
	/**
	Update the teacher list
 */
	public void reevaluateTeacherList()
	{
		String dept = "";
		String courseShortName = "";
		if (DepartmentComboBox.getSelectedIndex() != -1)
		{
			if (!DepartmentComboBox.getSelectedItem().toString().equalsIgnoreCase(""))
				dept = DepartmentComboBox.getSelectedItem().toString();
		}
		if (CourseComboBox.getSelectedIndex() != -1)
		{
			if (!CourseComboBox.getSelectedItem().toString().equalsIgnoreCase(""))
				courseShortName = CourseComboBox.getSelectedItem().toString();
		}
		ArrayList teachers = new ArrayList();
		for (int x = 0; x < main.myDB.getSectionList().size(); x++)
		{
			Section s = (Section)main.myDB.getSectionList().get(x);
			Teacher t = main.myDB.getTeacherForID(s.getTeacherID());
			if (t != null)
			{
				if (!teachers.contains(t))
				{
					if (!courseShortName.equalsIgnoreCase(""))
					{
						for (int k = 0; k < t.getCurrentCourses().size(); k++)
						{
							Section sc = (Section)t.getCurrentCourses().get(k);
							String shortName = sc.getCourseName() + " " + sc.getCourseNumber();
							if (shortName.equalsIgnoreCase(courseShortName))
							{
								teachers.add(t);
							}
						}
					}
					else
					{
						teachers.add(t);
					}
				}
			}
		}
		String data[] = new String[teachers.size()+1];
		data[0] = "";
		for (int x = 0; x < teachers.size(); x++)
		{
			Teacher t = (Teacher)teachers.get(x);
			data[x+1] = t.getTeacherName();
		}
		TeacherComboBox.setModel(new DefaultComboBoxModel(data));
		updateProfessorRatingInformation();
	}
}
