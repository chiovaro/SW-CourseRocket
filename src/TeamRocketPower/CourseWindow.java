package TeamRocketPower;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JLayeredPane;
import javax.swing.Box;
import java.awt.Component;
import java.awt.GridLayout;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextPane;

public class CourseWindow {

	public JFrame frame;
	Main main;
	JComboBox departments;
	JComboBox courses;
	JComboBox sections;

	public CourseWindow(Main aMain) {
		this.main = aMain;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Course Rocket");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
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
		
		departments = new JComboBox();
		departments.setModel(new DefaultComboBoxModel(classes));
		departments.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				//System.out.println("HERE");
				if (arg0.getStateChange() == 1)
				{
					String addCourses[] = new String[main.myDB.getCourseList().size()+1];
					courses.removeAllItems();
					courses.addItem("");
					for (int x = 0; x < main.myDB.getCourseList().size(); x++)
					{
						Course c = (Course)main.myDB.getCourseList().get(x);
						if (c.getDepartment().equalsIgnoreCase(arg0.getItem().toString()))
						{
							addCourses[x+1] = c.getDepartment() + " " + c.getCourseNumber();
							courses.addItem(addCourses[x+1]);
						}
					}
				}
			}
		});
		departments.setBounds(10, 29, 104, 20);
		frame.getContentPane().add(departments);
		
		JLabel lblDepartment = new JLabel("Department");
		lblDepartment.setBounds(10, 11, 72, 14);
		frame.getContentPane().add(lblDepartment);
		
		JLabel lblCourse = new JLabel("Course");
		lblCourse.setBounds(10, 60, 46, 14);
		frame.getContentPane().add(lblCourse);
		
		courses = new JComboBox();
		courses.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) 
			{
				if (arg0.getStateChange() == 1)
				{
					String addCourses[] = new String[main.myDB.getSectionList().size()+1];
					sections.removeAllItems();
					
					for (int x = 0; x < main.myDB.getSectionList().size(); x++)
					{
						
						Section s = (Section)main.myDB.getSectionList().get(x);
						if (courses.getSelectedItem().toString().equalsIgnoreCase(departments.getSelectedItem() + " " + s.getCourseNumber()))
						{
							addCourses[x] = "" + s.getCRN();
							sections.addItem(addCourses[x]);
						}
					}
				}
			}
		});
		courses.setBounds(10, 85, 104, 20);
		frame.getContentPane().add(courses);
		
		JLabel lblSection = new JLabel("Section");
		lblSection.setBounds(10, 116, 46, 14);
		frame.getContentPane().add(lblSection);
		
		sections = new JComboBox();
		sections.setBounds(10, 141, 104, 20);
		frame.getContentPane().add(sections);
		
		JTextPane txtpnMonday = new JTextPane();
		txtpnMonday.setText("Monday");
		txtpnMonday.setBounds(153, 29, 44, 20);
		frame.getContentPane().add(txtpnMonday);
		
		JTextPane txtpnTuesday = new JTextPane();
		txtpnTuesday.setText("Tuesday");
		txtpnTuesday.setBounds(207, 29, 44, 20);
		frame.getContentPane().add(txtpnTuesday);
		
		JTextPane txtpnWednesday = new JTextPane();
		txtpnWednesday.setText("Wednesday");
		txtpnWednesday.setBounds(261, 29, 46, 20);
		frame.getContentPane().add(txtpnWednesday);
		
		JTextPane txtpnThursday = new JTextPane();
		txtpnThursday.setText("Thursday");
		txtpnThursday.setBounds(317, 29, 44, 20);
		frame.getContentPane().add(txtpnThursday);
		
		JTextPane txtpnFriday = new JTextPane();
		txtpnFriday.setText("Friday");
		txtpnFriday.setBounds(371, 29, 44, 20);
		frame.getContentPane().add(txtpnFriday);
		
		int time = 8;
		for (int x = 153; x < 153 + (207-153)*4; x += 207-153)
		{
			for (int y = 29; y < 29 + (25*12); y += 25)
			{
				JTextPane test = new JTextPane();
				txtpnFriday.setText(time + ":00");
				txtpnFriday.setBounds(x, y, 44, 20);
				frame.getContentPane().add(test);
				time++;
				if (time == 13)
					time = 1;
			}
		}
		
	}
}
