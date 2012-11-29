package TeamRocketPower;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.ListModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JLayeredPane;
import javax.swing.Box;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.JList;
import java.awt.List;
import javax.swing.ScrollPaneConstants;
import java.awt.Dimension;
import java.awt.Rectangle;
import javax.swing.JScrollBar;
import javax.swing.ListSelectionModel;
import javax.swing.JTextArea;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.MouseMotionAdapter;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.JRadioButton;
import java.awt.event.HierarchyBoundsAdapter;
import java.awt.event.HierarchyEvent;

/**
CourseWindow: GUI for our course registration window. Can look at all the
offered classes in a database as well as register for those classes
*/
public class CourseWindow {

	public JFrame frame;
	JScrollPane scrollPane;
	JPanel drawPanel;
	Main main;
	JComboBox departments;
	JComboBox courses;
	JList sectionsList;
	JTextArea sectionInfo;
	ArrayList weeklySchedule[][];
	ArrayList weeklyScheduleSections[][];
	int calendarHeight;
	JList registeredList;
	JList tentativeList;
	JLabel courseNameLabel;
	ArrayList searchedClasses;
	
	ArrayList tentativeListClasses;
	ArrayList completedListClasses;
	ArrayList progressClasses;
	ArrayList registeredClasses;
	
	ArrayList completeClassLabels;
	ArrayList hiddenByExcludes;
	
	JScrollPane sectionInfoScroll;
	
	Section selectedSection;
	
	JTextPane classInfoPane;

	
	JCheckBox mondayExclude;
	JCheckBox tuesdayExclude;
	JCheckBox wednesdayExclude;
	JCheckBox thursdayExclude;
	JCheckBox fridayExclude;
	
	JTextPane errorPane;
	
	DefaultListModel sectionsModel;
	
	boolean weekdayExclude[];
	boolean hourlyExclude[];
	
	/**
	Initialize the window
	 */
	public CourseWindow(Main aMain) {
		this.main = aMain;
		initialize();
	}
	/**
	Load a students information into the current GUI
	resets the classes displayed aswell as updates
	the registered and tentative classes lists
	 */
	public void loadStudent(Student s)
	{
		this.removeAllSectionsFromView();
		tentativeListClasses.removeAll(tentativeListClasses);
		completedListClasses.removeAll(completedListClasses);
		progressClasses.removeAll(progressClasses);
		registeredClasses.removeAll(registeredClasses);
		
		
		tentativeListClasses.addAll(s.getTentativeCourses());
		completedListClasses.addAll(s.getCompletedCourses());
		progressClasses.addAll(s.getProgressCourses());
		registeredClasses.addAll(s.getRegisteredClasses());
		
		
		String dataStr[] = new String[registeredClasses.size()];
		for (int x = 0; x < registeredClasses.size(); x++)
		{
			Section sec = (Section)registeredClasses.get(x);
			dataStr[x] = "" + sec.getCRN();
		}
		
		registeredList.removeAll();
		this.registeredList.setListData(dataStr);
		
		String dataStr1[] = new String[tentativeListClasses.size()];
		for (int x = 0; x < tentativeListClasses.size(); x++)
		{
			Section sec = (Section)tentativeListClasses.get(x);
			dataStr1[x] = "" + sec.getCRN();
		}
		this.tentativeList.setListData(dataStr1);
		
		this.initAllSectionsFromView(true);
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		registeredClasses = new ArrayList();
		completeClassLabels = new ArrayList();
		searchedClasses = new ArrayList();
		tentativeListClasses = new ArrayList();
		completedListClasses = new ArrayList();
		progressClasses = new ArrayList();
		hiddenByExcludes = new ArrayList();
		sectionsModel = new DefaultListModel();
		
		
		weekdayExclude = new boolean[Calendar.FRIDAY+1];
		hourlyExclude = new boolean[25];
		for (int x = 0; x < Calendar.FRIDAY+1; x++)
		{
			weekdayExclude[x] = false;
		}
		
		main.currentStudent = (Student)main.myDB.getStudents().get(0);
		weeklySchedule = new ArrayList[5][12];
		this.weeklyScheduleSections = new ArrayList[5][12];
		
		
		
		for (int x = 0; x < 5; x++)
			for (int y = 0; y < 12; y++){
				weeklySchedule[x][y] = new ArrayList();
				weeklyScheduleSections[x][y] = new ArrayList();
			}
		
		frame = main.frame;
		
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

		drawPanel = new JPanel();
		drawPanel.setPreferredSize(new Dimension(874, 578));
		drawPanel.setLayout(null);
		
		JTextPane txtpnWednesday = new JTextPane();
		txtpnWednesday.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				weekdayExclude[Calendar.WEDNESDAY] = !weekdayExclude[Calendar.WEDNESDAY];
				JTextPane pane = (JTextPane)arg0.getSource();
				if (weekdayExclude[Calendar.WEDNESDAY])
					pane.setBackground(Color.BLACK);
				else
					pane.setBackground(Color.WHITE);
				updateSectionExcludes();
				repaint();
			}
		});
		txtpnWednesday.setBounds(342, 27, 69, 20);
		drawPanel.add(txtpnWednesday);
		txtpnWednesday.setEditable(false);
		txtpnWednesday.setText("Wednesday");
		
		//JButton btnUpdate = new JButton("Update");
		//btnUpdate.setBounds(355, 2, 89, 23);
		//drawPanel.add(btnUpdate);
		
		JLabel lblDepartment = new JLabel("Department");
		lblDepartment.setBounds(10, 6, 72, 14);
		drawPanel.add(lblDepartment);
		departments = new JComboBox();
		departments.setBounds(79, 3, 104, 20);
		drawPanel.add(departments);
		departments.setModel(new DefaultComboBoxModel(classes));
		
		JTextPane txtpnFriday = new JTextPane();
		txtpnFriday.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				weekdayExclude[Calendar.FRIDAY] = !weekdayExclude[Calendar.FRIDAY];
				JTextPane pane = (JTextPane)arg0.getSource();
				if (weekdayExclude[Calendar.FRIDAY])
					pane.setBackground(Color.BLACK);
				else
					pane.setBackground(Color.WHITE);
				updateSectionExcludes();
				repaint();
			}
		});
		txtpnFriday.setBounds(488, 27, 63, 20);
		drawPanel.add(txtpnFriday);
		txtpnFriday.setEditable(false);
		txtpnFriday.setText("Friday");
		
		JTextPane txtpnThursday = new JTextPane();
		txtpnThursday.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				weekdayExclude[Calendar.THURSDAY] = !weekdayExclude[Calendar.THURSDAY];
				JTextPane pane = (JTextPane)arg0.getSource();
				if (weekdayExclude[Calendar.THURSDAY])
					pane.setBackground(Color.BLACK);
				else
					pane.setBackground(Color.WHITE);
				updateSectionExcludes();
				repaint();
			}
		});
		txtpnThursday.setBounds(415, 27, 63, 20);
		drawPanel.add(txtpnThursday);
		txtpnThursday.setEditable(false);
		txtpnThursday.setText("Thursday");
		
		JTextPane txtpnTuesday = new JTextPane();
		txtpnTuesday.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				weekdayExclude[Calendar.TUESDAY] = !weekdayExclude[Calendar.TUESDAY];
				JTextPane pane = (JTextPane)arg0.getSource();
				if (weekdayExclude[Calendar.TUESDAY])
					pane.setBackground(Color.BLACK);
				else
					pane.setBackground(Color.WHITE);
				updateSectionExcludes();
				repaint();
			}
		});
		txtpnTuesday.setBounds(273, 27, 63, 20);
		drawPanel.add(txtpnTuesday);
		txtpnTuesday.setEditable(false);
		txtpnTuesday.setText("Tuesday");
		
		JTextPane txtpnMonday = new JTextPane();
		txtpnMonday.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				weekdayExclude[Calendar.MONDAY] = !weekdayExclude[Calendar.MONDAY];
				JTextPane pane = (JTextPane)arg0.getSource();
				if (weekdayExclude[Calendar.MONDAY])
					pane.setBackground(Color.BLACK);
				else
					pane.setBackground(Color.WHITE);
				updateSectionExcludes();
				repaint();				
			}
		});
		txtpnMonday.setBounds(201, 27, 63, 20);
		drawPanel.add(txtpnMonday);
		txtpnMonday.setEditable(false);
		txtpnMonday.setText("Monday");
		
		JLabel lblSection = new JLabel("Sections");
		lblSection.setBounds(10, 31, 57, 14);
		drawPanel.add(lblSection);
		
		courses = new JComboBox();
		
		
		
		courses.setBounds(241, 2, 104, 20);
		drawPanel.add(courses);
		
		JLabel lblCourse = new JLabel("Course");
		lblCourse.setBounds(193, 5, 46, 14);
		drawPanel.add(lblCourse);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 45, 89, 84);
		drawPanel.add(scrollPane_1);
		sectionsList = new JList(sectionsModel);
		sectionsList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				JList aList = (JList)arg0.getSource();
				if (aList.getSelectedIndex() != -1)
				{
					sectionInfo.setText(getInfoForCRN(Integer.parseInt(aList.getSelectedValue().toString())));
					sectionInfo.setCaretPosition(0);
				}
			}
		});
		sectionsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_1.setViewportView(sectionsList);
		
		JLabel lblTentativeSchedule = new JLabel("Tentative Schedule");
		lblTentativeSchedule.setBounds(10, 179, 119, 14);
		drawPanel.add(lblTentativeSchedule);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 193, 89, 84);
		drawPanel.add(scrollPane_2);
		tentativeList = new JList();
		scrollPane_2.setViewportView(tentativeList);
		
		JLabel lblRegisteredClasses = new JLabel("Registered Classes");
		lblRegisteredClasses.setBounds(10, 346, 119, 14);
		drawPanel.add(lblRegisteredClasses);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(10, 363, 89, 84);
		drawPanel.add(scrollPane_3);
		
		registeredList = new JList();
		scrollPane_3.setViewportView(registeredList);
		
		JButton btnSaveSection = new JButton("Save Section");
		btnSaveSection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (sectionsList.getSelectedValue() != null)
				{
					int CRN = Integer.parseInt(sectionsList.getSelectedValue().toString());
										
					for (int x = 0; x < main.myDB.getSectionList().size(); x++)
					{
						Section s = (Section)main.myDB.getSectionList().get(x);
						if (s.getCRN() == CRN)
						{
							String str = main.myDB.registerStudentSection(main.currentStudent, s);
							if (str == null)
							{
								boolean canAdd = true;
								for (int x1 = 0; x1 < main.currentStudent.getRegisteredClasses().size(); x1++)
								{
									Section stuReg = (Section)main.currentStudent.getRegisteredClasses().get(x1);
									if (stuReg.getCRN() == s.getCRN())
									{
										canAdd = false;
										errorPane.setText("Error: Already Registered For Class\n" + errorPane.getText());
										errorPane.setCaretPosition(0);
									}
								}
								if (canAdd)
								{
									String[] myList = new String[main.currentStudent.getTentativeCourses().size()];
									int pos = 0;
									for (int y = 0; y < main.currentStudent.getTentativeCourses().size(); y++)
									{
										Section s1 = (Section)main.currentStudent.getTentativeCourses().get(y);
										myList[pos++] = "" + s1.getCRN();
									}
									tentativeList.removeAll();
									tentativeList.setListData(myList);
									removeAllSectionsFromView();
									tentativeListClasses.add(s);
									initAllSectionsFromView(true);
								}
							}
							else
							{
								errorPane.setText(str + errorPane.getText());
								errorPane.setCaretPosition(0);
							}
						}
					}
				}
				main.myDB.saveDB();
			}
		});
		btnSaveSection.setBounds(10, 140, 119, 23);
		drawPanel.add(btnSaveSection);
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (tentativeList.getSelectedValue() != null)
				{
					int selectedCRN = Integer.parseInt(tentativeList.getSelectedValue().toString());
					for (int x = 0; x < main.myDB.getSectionList().size(); x++)
					{
						Section s = (Section)main.myDB.getSectionList().get(x);
						if (s.getCRN() == selectedCRN)
						{
							boolean canAdd = true;
							for (int h = 0; h < main.myDB.getCourseList().size(); h++)
							{
								Course c = (Course)main.myDB.getCourseList().get(h);
								if (c.getDepartment().equalsIgnoreCase(s.getCourseName())
										&& c.getCourseNumber() == s.getCourseNumber())
								{
									String str = main.myDB.checkAlreadyAdded(main.currentStudent, c);
									if (str != null)
									{
										canAdd = false;
										errorPane.setText(str + errorPane.getText());
										errorPane.setCaretPosition(0);
									}
								}
							}
							
							int reg = s.getRegisteredStudentsList().size();
							int max = s.getMaxCapacity();
							if (reg == max)
							{
								canAdd = false;
								errorPane.setText("Class Is Full. Adding To Notification List\n" + errorPane.getText());
								errorPane.setCaretPosition(0);
								s.addStudentToWaitingList(main.currentStudent);

								/*Strictly here to send the email well make the class unfull*/
								s.getRegisteredStudentsList().removeAll(s.getRegisteredStudentsList());
								try
								{
									String email = JOptionPane.showInputDialog(null, "What Is Your Email?");
									MailClient.send("The Class: " + s.getCourseName() + " " + s.getCourseNumber()
											+ " Is finally available. Login to register before it gets full!" +
											"\n\n -The Course Rocket Team", "DoNotReply@CourseRocket.com", email);
								}
								catch(IOException e)
								{
									System.out.println("Error: " + e);
								}
								
							}
							
							
							for (int k = 0; k < main.currentStudent.getRegisteredClasses().size(); k++)
							{
								Section sec = (Section)main.currentStudent.getRegisteredClasses().get(k);
								if (main.myDB.classesCollide(sec, s))
								{
									canAdd = false;
									errorPane.setText("Classes Conflict With Schedule\n" + errorPane.getText());
									errorPane.setCaretPosition(0);							
								}
							}
							
							if (canAdd)
							{
								removeAllSectionsFromView();
								main.currentStudent.getTentativeCourses().remove(s);
								main.currentStudent.getRegisteredClasses().add(s);
								if (!registeredClasses.contains(s))
									registeredClasses.add(s);
								tentativeListClasses.remove(s);
								String[] myList = new String[main.currentStudent.getTentativeCourses().size()];
								int pos = 0;
								for (int y = 0; y < main.currentStudent.getTentativeCourses().size(); y++)
								{
									Section s1 = (Section)main.currentStudent.getTentativeCourses().get(y);
									myList[pos++] = "" + s1.getCRN();
								}
								tentativeList.removeAll();
								tentativeList.setListData(myList);
								
								myList = new String[main.currentStudent.getRegisteredClasses().size()];
								pos = 0;
								for (int y = 0; y < main.currentStudent.getRegisteredClasses().size(); y++)
								{
									Section s1 = (Section)main.currentStudent.getRegisteredClasses().get(y);
									
									myList[pos++] = "" + s1.getCRN();
								}
								registeredList.removeAll();
								registeredList.setListData(myList);
								
								s.addStudent(main.currentStudent);
								
								
								initAllSectionsFromView(true);
							}
						}
					}
				}
				main.myDB.saveDB();
			}
		});
		btnRegister.setBounds(10, 288, 89, 23);
		drawPanel.add(btnRegister);
		
		JButton btnDropClass = new JButton("Drop Class");
		btnDropClass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				if (registeredList.getSelectedValue() != null)
				{
					int selectedCRN = Integer.parseInt(registeredList.getSelectedValue().toString());
					for (int x = 0; x < main.myDB.getSectionList().size(); x++)
					{
						Section s = (Section)main.myDB.getSectionList().get(x);
						if (s.getCRN() == selectedCRN)
						{
							removeAllSectionsFromView();
							main.currentStudent.getRegisteredClasses().remove(s);
							main.currentStudent.getTentativeCourses().add(s);
							if (!tentativeListClasses.contains(s))
								tentativeListClasses.add(s);
							registeredClasses.remove(s);
							String[] myList = new String[main.currentStudent.getTentativeCourses().size()];
							int pos = 0;
							for (int y = 0; y < main.currentStudent.getTentativeCourses().size(); y++)
							{
								Section s1 = (Section)main.currentStudent.getTentativeCourses().get(y);
								myList[pos++] = "" + s1.getCRN();
							}
							tentativeList.removeAll();
							tentativeList.setListData(myList);
							
							myList = new String[main.currentStudent.getRegisteredClasses().size()];
							pos = 0;
							for (int y = 0; y < main.currentStudent.getRegisteredClasses().size(); y++)
							{
								Section s1 = (Section)main.currentStudent.getRegisteredClasses().get(y);
								myList[pos++] = "" + s1.getCRN();
							}
							registeredList.removeAll();
							registeredList.setListData(myList);
							
							initAllSectionsFromView(true);
							s.getRegisteredStudentsList().remove(main.currentStudent);
							s.getWaitingStudentsList().remove(main.currentStudent);
						}
					}
				}
				main.myDB.saveDB();
			}
		});
		btnDropClass.setBounds(10, 458, 104, 23);
		drawPanel.add(btnDropClass);
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				removeAllSectionsFromView();
				String CRN = tentativeList.getSelectedValue().toString();
				for (int x = 0; x < main.myDB.getSectionList().size(); x++)
				{
					Section s = (Section)main.myDB.getSectionList().get(x);
					if (s.getCRN() == Integer.parseInt(CRN))
					{
						tentativeListClasses.remove(s);
						main.currentStudent.getTentativeCourses().remove(s);
						String[] myList = new String[main.currentStudent.getTentativeCourses().size()];
						int pos = 0;
						for (int y = 0; y < main.currentStudent.getTentativeCourses().size(); y++)
						{
							Section s1 = (Section)main.currentStudent.getTentativeCourses().get(y);
							myList[pos++] = "" + s1.getCRN();
						}
						tentativeList.removeAll();
						tentativeList.setListData(myList);
					}
					
				}
				initAllSectionsFromView(true);
				main.myDB.saveDB();
			}
		});
		btnRemove.setBounds(10, 312, 89, 23);
		drawPanel.add(btnRemove);
		
		sectionInfoScroll = new JScrollPane();
		sectionInfoScroll.setBounds(565, 31, 264, 288);
		drawPanel.add(sectionInfoScroll);
		
		sectionInfo = new JTextArea();
		sectionInfoScroll.setViewportView(sectionInfo);
		sectionInfo.setLineWrap(true);
		sectionInfo.setWrapStyleWord(true);
		sectionInfo.setEditable(false);
		sectionInfo.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		
		JButton btnAdvisment = new JButton("Advisment");
		btnAdvisment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main.changeViewToAdvisorWindow();
			}
		});
		btnAdvisment.setBounds(565, 2, 104, 23);
		drawPanel.add(btnAdvisment);
		
		JButton btnRateMyProfessor = new JButton("Rate My Professor");
		btnRateMyProfessor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main.changeViewToRateProfessorWindow();
			}
		});
		btnRateMyProfessor.setBounds(683, 2, 146, 23);
		drawPanel.add(btnRateMyProfessor);
		
		JTextPane txtpnUnregistered = new JTextPane();
		txtpnUnregistered.setBackground(Color.GRAY);
		txtpnUnregistered.setText("Unregistered");
		txtpnUnregistered.setBounds(612, 321, 75, 20);
		drawPanel.add(txtpnUnregistered);
		
		JTextPane txtpnTentative = new JTextPane();
		txtpnTentative.setBackground(Color.YELLOW);
		txtpnTentative.setText("Tentative");
		txtpnTentative.setBounds(712, 320, 63, 20);
		drawPanel.add(txtpnTentative);
		
		JTextPane txtpnRegistered = new JTextPane();
		txtpnRegistered.setBackground(Color.CYAN);
		txtpnRegistered.setText("Registered");
		txtpnRegistered.setBounds(621, 342, 63, 20);
		drawPanel.add(txtpnRegistered);
		
		JTextPane txtpnConflicted = new JTextPane();
		txtpnConflicted.setBackground(Color.RED);
		txtpnConflicted.setText("Conflicted");
		txtpnConflicted.setBounds(714, 341, 59, 20);
		drawPanel.add(txtpnConflicted);
		
		errorPane = new JTextPane();
		errorPane.setBounds(565, 363, 264, 84);
		drawPanel.add(errorPane);
		
		courseNameLabel = new JLabel("");
		courseNameLabel.setBounds(353, 6, 198, 14);
		drawPanel.add(courseNameLabel);
		
		courses.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) 
			{
				if (arg0.getStateChange() == 1)
				{
					if (!courses.getSelectedItem().toString().equalsIgnoreCase(""))
					{
						for (int k = 0; k < Main.myDB.getCourseList().size(); k++)
						{
							Course c = (Course)Main.myDB.getCourseList().get(k);
							if (courses.getSelectedItem().toString().equalsIgnoreCase(c.getDepartment() + " " + c.getCourseNumber()))
							{
								courseNameLabel.setText(c.getCourseName());
							}
						}
						sectionsModel.removeAllElements();
						for (int x = 0; x < main.myDB.getSectionList().size(); x++)
						{
							
							Section s = (Section)main.myDB.getSectionList().get(x);
							
							if (sectionDoesntInterfereWithExcludes(s) && courses.getSelectedItem().toString().equalsIgnoreCase(departments.getSelectedItem() + " " + s.getCourseNumber()))
							{
								sectionsModel.addElement("" + s.getCRN());
							}
						
						}						
					}
				}
				updateGUIClasses();
			}
		});
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
	/*	btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				updateGUIClasses();
			}
		});
		*/
		
		
		int top = 29+25;
		int bottom = 0;
		int time;
		for (int x = 153; x < 154/* + (207-153)*5*/; x += (207-153))
		{
			time = 8;
			for (int y = 29+25; y < 29 + (45*12); y += 45)
			{
				JTextPane test = new JTextPane();
				test.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						JTextPane pane = (JTextPane)arg0.getSource();
						String times[] = pane.getText().split(":");
						int time = Integer.parseInt(times[0]);
						hourlyExclude[time] = !hourlyExclude[time];
						if (hourlyExclude[time])
							pane.setBackground(Color.BLACK);
						else
							pane.setBackground(Color.WHITE);
						updateSectionExcludes();
						repaint();
					}
				});
				test.setText(time + ":00");
				test.setBounds(x, y, 44, 40);
				bottom = y+40;
				test.setEditable(false);
				drawPanel.add(test);
				time++;
				if (time == 13)
					time = 1;
			}
		}
		loadStudent(main.currentStudent);
		this.calendarHeight = bottom-top;
		repaint();
	}
	
	/**
	Updates the list of classes drawn in our view
	to reflect the new department and course to show.
	 */
	public void updateGUIClasses()
	{
		removeAllSectionsFromView();
		searchedClasses.removeAll(searchedClasses);
		for (int x = 0; x < main.myDB.getSectionList().size(); x++)
		{
			Section s = (Section)main.myDB.getSectionList().get(x);
			if (departments.getSelectedIndex() != -1 && departments.getSelectedItem() != "")
			{
				if (courses.getSelectedIndex() != -1 && courses.getSelectedItem() != "")
				{
					String courseInfo[] = courses.getSelectedItem().toString().split(" ");
					
					boolean add = true;
					for (int r = 0; r < s.getMeetingTimes().size(); r++)
					{
						Date da = (Date)s.getMeetingTimes().get(r);
						if (hourlyExclude[(da.getHours() > 12) ? da.getHours()-12 : da.getHours()])
						{
							add = false;
						}
						//System.out.println(da.getDay() + ":" + Calendar.MONDAY);
						if (weekdayExclude[da.getDay()+1])
						{
							add = false;
						}
					}
					
					if (add && s.getCourseName().equalsIgnoreCase(courseInfo[0]) && s.getCourseNumber() == Integer.parseInt(courseInfo[1]))
					{
						searchedClasses.add(s);
					}
				}
				else
				{
					boolean add = true;
					for (int r = 0; r < s.getMeetingTimes().size(); r++)
					{
						Date da = (Date)s.getMeetingTimes().get(r);
						if (hourlyExclude[(da.getHours() > 12) ? da.getHours()-12 : da.getHours()])
						{
							add = false;
						}
						//System.out.println(da.getDay() + ":" + Calendar.MONDAY);
						if (weekdayExclude[da.getDay()-1])
						{
							add = false;
						}
					}
					if (add && s.getCourseName().equalsIgnoreCase((String) departments.getSelectedItem()))
					{
						searchedClasses.add(s);
					}
				}
			}
		}
		
		initAllSectionsFromView(false);
		repaint();
	}
	/**
	Remove a list of sections from the view.
	 */
	public void removeSectionsFromView(ArrayList sects)
	{
		for (int x = 0; x < 5; x++)
			for (int y = 0; y < 12; y++)
			{
				ArrayList discard1 = new ArrayList();
				ArrayList discard2 = new ArrayList();
				for (int z = 0; z < weeklyScheduleSections[x][y].size(); z++)
				{
					if (sects.contains(weeklyScheduleSections[x][y].get(z)))
					{
						drawPanel.remove((JTextPane)weeklySchedule[x][y].get(z));
						discard1.add(weeklySchedule[x][y].get(z));
						discard2.add(weeklyScheduleSections[x][y].get(z));
					}
				}
				weeklySchedule[x][y].removeAll(discard1);
				weeklyScheduleSections[x][y].removeAll(discard2);
			}
		
		completeClassLabels.removeAll(sects);
	}
	
	/**
	When clicking on a class schedule we need to update
	its sibling windows as well to bring them to the front of the view.
 */
	public void setScheduleFocus(String aDepartment, int aCourseNumber)
	{
		ListModel model = departments.getModel();
		
		for (int x = 0; x < model.getSize(); x++)
		{
			//System.out.println(model.getElementAt(x).toString());
			//System.out.println(aDepartment);
			//System.out.println("---");
			if (model.getElementAt(x).toString().equalsIgnoreCase(aDepartment))
			{
				departments.setSelectedIndex(x);
			}
		}
		
		model = courses.getModel();
		String shortName = aDepartment + " " + aCourseNumber;
		for (int x = 0; x < model.getSize(); x++)
		{
			if (model.getElementAt(x).toString().equalsIgnoreCase(shortName))
			{
				courses.setSelectedIndex(x);
			}
		}
		updateGUIClasses();
	}
	
	/**
	Add sections into our view with a specific color.
 */
	public void updateViewWithSections(ArrayList sects, Color aColor)
	{
		for (int x = 0; x < sects.size(); x++)
		{
			Section s = (Section)sects.get(x);
			if (!completeClassLabels.contains(s))
			{
				completeClassLabels.add(s);
				ArrayList meetingTimes = s.getMeetingTimes();
				for (int z = 0; z < meetingTimes.size(); z++)
				{
					int o = 0, p = 0;
					Date d = (Date)meetingTimes.get(z);
					switch(d.getDay())
					{
						case 1:
							o = 0;
							break;
						case 2:
							o = 1;
							break;
						case 3:
							o = 2;
							break;
						case 4:
							o = 3;
							break;
						case 5:
							o = 4;
							break;
					}
					
					p = d.getHours()-8;
	
					float percent = (float)p/12;
					
					float durationPercent = ((float)s.getClassDuration()/(12*60));
					Course c = this.getCourseForCRN(s.getCRN());
					JTextPane test = new JTextPane();
					Font f = new Font(Font.SANS_SERIF, 3, 10);
					test.setFont(f);
					test.setText(s.getCRN() + " - " + c.getCourseName());
					
					test.setBounds(144+(71)*(o+1) + (weeklySchedule[o][p].size()*10), (int)(29+25+(percent*(calendarHeight))+(weeklySchedule[o][p].size()*10)), 44, (int) (durationPercent * (calendarHeight)));
					test.setEditable(false);
					test.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseExited(MouseEvent arg0) {
							drawPanel.remove(classInfoPane);
							repaint();
						}
					});
					test.addMouseMotionListener(new MouseMotionAdapter() {
						@Override
						public void mouseMoved(MouseEvent arg0) {
							if (classInfoPane == null)
								classInfoPane = new JTextPane();
							
							drawPanel.remove(classInfoPane);
							JTextPane aPane = (JTextPane)arg0.getSource();
							String strs[] = aPane.getText().split(" ");
							Course c = getCourseForCRN(Integer.parseInt(strs[0]));
							
							
							classInfoPane.setText(c.getCourseName());
							classInfoPane.setBounds(arg0.getX()+aPane.getBounds().x+10, arg0.getY()+aPane.getBounds().y, 120, 50);
							classInfoPane.setEditable(false);
							classInfoPane.setCaretPosition(0);
							
							drawPanel.add(classInfoPane, 0);
							//frame.repaint();
							
							repaint();
							
						}
					});
					test.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent arg0) 
						{
							drawPanel.remove((Component)arg0.getSource());
							drawPanel.add((Component)arg0.getSource(), 0);
							
							JTextPane aPane = (JTextPane)arg0.getSource();
							Section s = null;
							int j=0, k=0;
							
							for (int x = 0; x < 5; x++)
								for (int y = 0; y < 12; y++)
								{
									for (int z = 0; z < weeklySchedule[x][y].size(); z++)
									{
																			
										if (weeklySchedule[x][y].get(z) == arg0.getSource())
										{
											j = x;
											k = y;
											s = (Section)weeklyScheduleSections[x][y].get(z);
										}
									}
								}
							
							if (s != null)
							{
								
								for (int x = 0; x < sectionsModel.getSize(); x++)
								{
									
									if (Integer.parseInt(sectionsModel.getElementAt(x).toString()) == s.getCRN())
									{
										sectionsList.setSelectedIndex(x);
									}
								}
								
								ListModel model = tentativeList.getModel();
								
								for (int x = 0; x < model.getSize(); x++)
								{
									if (Integer.parseInt((String)model.getElementAt(x)) == s.getCRN())
									{
										tentativeList.setSelectedIndex(x);
									}
								}
								
								model = registeredList.getModel();
								
								for (int x = 0; x < model.getSize(); x++)
								{
									if (Integer.parseInt((String)model.getElementAt(x)) == s.getCRN())
									{
										registeredList.setSelectedIndex(x);
									}
								}
							}
							
							
							for (int z = 0; z < weeklySchedule[j][k].size(); z++)
							{
								JTextPane aPane2 = (JTextPane)weeklySchedule[j][k].get(z);
								if (weeklySchedule[j][k].get(z) != arg0.getSource())
								{
									drawPanel.remove((Component)weeklySchedule[j][k].get(z));
									drawPanel.add((Component)weeklySchedule[j][k].get(z));
								}
							}
							
							for (int x = 0; x < 5; x++)
								for (int y = 0; y < 12; y++)
								{
									for (int z = 0; z < weeklySchedule[x][y].size(); z++)
									{
										JTextPane aPane2 = (JTextPane)weeklySchedule[x][y].get(z);
										if (aPane.getText().equalsIgnoreCase(aPane2.getText())
												&& weeklySchedule[x][y].get(z) != arg0.getSource())
										{
											drawPanel.remove((Component)weeklySchedule[x][y].get(z));
											drawPanel.add((Component)weeklySchedule[x][y].get(z), 0);
										}
									}
								}
							
							
							

							selectedSection = s;
							sectionInfo.setText(getInfoForCRN(s.getCRN()));
							sectionInfo.setCaretPosition(0);
							
							repaint();
						}
					});

					test.setBorder(BorderFactory.createLineBorder(Color.black));
					test.setBackground(aColor);
					weeklySchedule[o][p].add(test);
					weeklyScheduleSections[o][p].add(s);
					drawPanel.add(test);
				}
			}
		}
	}
	/**
	Reset all the sections in our view to none.
 */
	public void removeAllSectionsFromView()
	{
		removeSectionsFromView(searchedClasses);
		removeSectionsFromView(tentativeListClasses);
		removeSectionsFromView(completedListClasses);
		removeSectionsFromView(registeredClasses);
		removeSectionsFromView(progressClasses);
		this.hiddenByExcludes.removeAll(hiddenByExcludes);
		repaint();
	}
	
	/**
	Initialize our known sections in the view based on
	the current students information.
 */
	public void initAllSectionsFromView(boolean shouldRepaint)
	{

		updateViewWithSections(completedListClasses, Color.green);
		updateViewWithSections(progressClasses, Color.blue);
		updateViewWithSections(registeredClasses, Color.cyan);
		
		ArrayList collisions = new ArrayList();
		for (int x = 0; x < registeredClasses.size(); x++)
		{
			Section regClass = (Section)registeredClasses.get(x);
			for (int y = 0; y < tentativeListClasses.size(); y++)
			{
				Section tentClass = (Section)tentativeListClasses.get(y);
				if (main.myDB.classesCollide(regClass, tentClass))
				{
					collisions.add(tentClass);
				}
			}
			
			for (int y = 0; y < searchedClasses.size(); y++)
			{
				Section tentClass = (Section)searchedClasses.get(y);
				if (main.myDB.classesCollide(regClass, tentClass))
				{
					collisions.add(tentClass);
				}
			}
			
		}
		updateViewWithSections(collisions, Color.red);
		updateViewWithSections(tentativeListClasses, Color.yellow);		
		updateViewWithSections(searchedClasses, Color.gray);
		if (shouldRepaint)
			repaint();
	}
	
	
	
	/**
	 * If we have excluded a class because of time or day
	 * we need to update if they should be shown in the gui
	 * or not.
	 */
	public void updateSectionExcludes()
	{
		ArrayList remove = new ArrayList();
		this.removeSectionsFromView(searchedClasses);
		searchedClasses.addAll(hiddenByExcludes);
		for (int x = 0; x < hiddenByExcludes.size(); x++)
		{
			Section s = (Section)hiddenByExcludes.get(x);
			sectionsModel.addElement(s.getCRN());
		}
		hiddenByExcludes.removeAll(hiddenByExcludes);
		this.initAllSectionsFromView(false);
		for (int x = 0; x < searchedClasses.size(); x++)
		{
			Section s = (Section)searchedClasses.get(x);
			if (!sectionDoesntInterfereWithExcludes(s) 
					&& !this.main.currentStudent.getProgressCourses().contains(s)
					&& !this.main.currentStudent.getRegisteredClasses().contains(s)
					&& !this.main.currentStudent.getTentativeCourses().contains(s))
			{
				if (!remove.contains(s))
					remove.add(s);
				
				for (int y = 0; y < sectionsModel.getSize(); y++)
				{
					String text = sectionsModel.getElementAt(y).toString();
					if (Integer.parseInt(text) == s.getCRN())
					{
						sectionsModel.removeElementAt(y);
					}
				}
			}
		}
		hiddenByExcludes.addAll(remove);
		if (remove.size() > 0)
			this.removeSectionsFromView(remove);
		
		
	}
	/**
	redraws our frame.
 */
	public void repaint()
	{
		
		this.drawPanel.setPreferredSize(drawPanel.getPreferredSize());
		frame.repaint();
	}
	/**
	Returns corresponding Course object for a specific CRN
 */
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
	
	/**
	Returns if a secition interferes with any excluded times.
 */
	public boolean sectionDoesntInterfereWithExcludes(Section s)
	{
		boolean add = true;
		for (int r = 0; r < s.getMeetingTimes().size(); r++)
		{
			Date da = (Date)s.getMeetingTimes().get(r);
			if (hourlyExclude[(da.getHours() > 12) ? da.getHours()-12 : da.getHours()])
			{
				add = false;
			}
			
			if (weekdayExclude[da.getDay()+1])
			{
				add = false;
			}
		}
		return add;
	}
	
	/**
	Returns a string of the information inside of a givin crn number
 */
	public String getInfoForCRN(int aCRN)
	{
		String text = "";
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
					text += c.getCourseName() + "\n";
					text += c.getDepartment() + " " + c.getCourseNumber() + "\n";
					text += "CRN: " + s.getCRN() + "\n";
					for (int x = 0; x < s.getMeetingTimes().size(); x++)
					{
						Date d = (Date)s.getMeetingTimes().get(x);
						if (x != 0)
							text += "-";
						switch(d.getDay())
						{
							case 1:
								text += "M";
								break;
							case 2:
								text += "T";
								break;
							case 3:
								text += "W";
								break;
							case 4:
								text += "R";
								break;
							case 5:
								text += "F";
								break;
						}
						
					}
					text += "\n";
					
					
					Date d = (Date)s.getMeetingTimes().get(0);
					
					text += (d.getHours() > 12 ? d.getHours()-12 : d.getHours()) + ":" + d.getMinutes() + " - ";
					Calendar cal = Calendar.getInstance();
					cal.setTime(d);
	
					float hours = (float)s.getClassDuration()/60.0f;
					hours = (int)hours;
					float minutes = (float)s.getClassDuration() % 60;
					
					cal.add(Calendar.MINUTE, s.getClassDuration());
	
					
					Date endTime = cal.getTime();
					text += (endTime.getHours() > 12 ? endTime.getHours()-12 : endTime.getHours()) + ":" + endTime.getMinutes() + "\n";
					boolean found = false;
					for (int x = 0; x < main.myDB.getTeachers().size(); x++)
					{
						Teacher t = (Teacher)main.myDB.getTeachers().get(x);
						if (t.getTeacherID() == s.getTeacherID())
						{
							found = true;
							text += "Teacher: " + t.getTeacherName() + "\n";
						}
					}
					if (!found)
						text += "Teacher: TBD";
					
					text += s.getRegisteredStudentsList().size() + "/" + s.getMaxCapacity() + " Registered\n"; 
					
					text += "Prereqs: \n";
					
					for (int r = 0; r < c.getPrereqList().size(); r++)
					{
						
						String str = (String)c.getPrereqList().get(r);
						if (!str.equalsIgnoreCase(" "))
							text += str + " ";
					}
					text += "\n";
					
					text += "Description: " + c.getCourseDescription() + "\n";
					
					text += c.getCreditHours() + " Credit Hours.";
					
					
				}
			}
		}
		return text;
	}
}
