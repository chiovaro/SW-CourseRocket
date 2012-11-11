package TeamRocketPower;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;

public class RateMyProfessorWindow {

	private JFrame frame;

	public RateMyProfessorWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(null);
		
		JLabel lblDepartment = new JLabel("Department");
		lblDepartment.setBounds(10, 11, 85, 14);
		panel.add(lblDepartment);
		
		JComboBox DepartmentComboBox = new JComboBox();
		DepartmentComboBox.setBounds(10, 32, 28, 20);
		panel.add(DepartmentComboBox);
		
		JLabel lblCourse = new JLabel("Course");
		lblCourse.setBounds(79, 11, 46, 14);
		panel.add(lblCourse);
		
		JComboBox CourseComboBox = new JComboBox();
		CourseComboBox.setBounds(79, 32, 28, 20);
		panel.add(CourseComboBox);
		
		JLabel lblTeacher = new JLabel("Teacher");
		lblTeacher.setBounds(135, 11, 46, 14);
		panel.add(lblTeacher);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(145, 32, 28, 20);
		panel.add(comboBox);
		
		JPanel RateInfoPanel = new JPanel();
		RateInfoPanel.setBounds(178, 69, 252, 184);
		panel.add(RateInfoPanel);
	}
}
