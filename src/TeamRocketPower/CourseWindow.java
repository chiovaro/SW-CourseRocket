package TeamRocketPower;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.ListModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JLayeredPane;
import javax.swing.Box;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
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
	Student currentStudent;
	int calendarHeight;
	JList registeredList;
	JList tentativeList;
	ArrayList searchedClasses;
	
	ArrayList tentativeListClasses;
	ArrayList completedListClasses;
	ArrayList progressClasses;
	ArrayList registeredClasses;
	
	ArrayList completeClassLabels;
	JScrollPane sectionInfoScroll;
	
	Section selectedSection;

	public CourseWindow(Main aMain) {
		this.main = aMain;
		initialize();
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
		currentStudent = (Student)main.myDB.getStudents().get(0);
		
		weeklySchedule = new ArrayList[5][12];
		this.weeklyScheduleSections = new ArrayList[5][12];
		
		for (int x = 0; x < 5; x++)
			for (int y = 0; y < 12; y++){
				weeklySchedule[x][y] = new ArrayList();
				weeklyScheduleSections[x][y] = new ArrayList();
			}
		
		frame = new JFrame("Course Rocket");
		frame.setBounds(100, 100, 866, 612);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
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
		frame.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		drawPanel = new JPanel();
		drawPanel.setPreferredSize(new Dimension(500, 900));
		scrollPane = new JScrollPane(drawPanel);
		scrollPane.setPreferredSize(new Dimension(502, 900));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		frame.getContentPane().add(scrollPane);
		//scrollPane.setLayout(null);
		drawPanel.setLayout(null);
		
		JTextPane txtpnWednesday = new JTextPane();
		txtpnWednesday.setBounds(346, 31, 63, 20);
		drawPanel.add(txtpnWednesday);
		txtpnWednesday.setEditable(false);
		txtpnWednesday.setText("Wednesday");
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBounds(10, 104, 89, 23);
		drawPanel.add(btnUpdate);
		
		JLabel lblDepartment = new JLabel("Department");
		lblDepartment.setBounds(10, 11, 72, 14);
		drawPanel.add(lblDepartment);
		departments = new JComboBox();
		departments.setBounds(10, 31, 104, 20);
		drawPanel.add(departments);
		departments.setModel(new DefaultComboBoxModel(classes));
		
		JTextPane txtpnFriday = new JTextPane();
		txtpnFriday.setBounds(492, 31, 63, 20);
		drawPanel.add(txtpnFriday);
		txtpnFriday.setEditable(false);
		txtpnFriday.setText("Friday");
		
		JTextPane txtpnThursday = new JTextPane();
		txtpnThursday.setBounds(419, 31, 63, 20);
		drawPanel.add(txtpnThursday);
		txtpnThursday.setEditable(false);
		txtpnThursday.setText("Thursday");
		
		JTextPane txtpnTuesday = new JTextPane();
		txtpnTuesday.setBounds(277, 31, 63, 20);
		drawPanel.add(txtpnTuesday);
		txtpnTuesday.setEditable(false);
		txtpnTuesday.setText("Tuesday");
		
		JTextPane txtpnMonday = new JTextPane();
		txtpnMonday.setBounds(205, 31, 63, 20);
		drawPanel.add(txtpnMonday);
		txtpnMonday.setEditable(false);
		txtpnMonday.setText("Monday");
		
		JLabel lblSection = new JLabel("Sections");
		lblSection.setBounds(10, 138, 57, 14);
		drawPanel.add(lblSection);
		
		courses = new JComboBox();
		courses.setBounds(10, 73, 104, 20);
		drawPanel.add(courses);
		
		JLabel lblCourse = new JLabel("Course");
		lblCourse.setBounds(10, 57, 46, 14);
		drawPanel.add(lblCourse);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 152, 89, 84);
		drawPanel.add(scrollPane_1);
		sectionsList = new JList();
		sectionsList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				JList aList = (JList)arg0.getSource();
				if (aList.getSelectedIndex() != -1)
				{
					setInfoForCRN(Integer.parseInt(aList.getSelectedValue().toString()));
				}
			}
		});
		sectionsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_1.setViewportView(sectionsList);
		
		JLabel lblTentativeSchedule = new JLabel("Tentative Schedule");
		lblTentativeSchedule.setBounds(10, 286, 119, 14);
		drawPanel.add(lblTentativeSchedule);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 300, 89, 84);
		drawPanel.add(scrollPane_2);
		tentativeList = new JList();
		scrollPane_2.setViewportView(tentativeList);
		
		JLabel lblRegisteredClasses = new JLabel("Registered Classes");
		lblRegisteredClasses.setBounds(10, 453, 119, 14);
		drawPanel.add(lblRegisteredClasses);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(10, 470, 89, 84);
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
							
							if (main.myDB.registerStudentSection(currentStudent, s))
							{
								boolean canAdd = true;
								for (int x1 = 0; x1 < currentStudent.getRegisteredClasses().size(); x1++)
								{
									Section stuReg = (Section)currentStudent.getRegisteredClasses().get(x1);
									if (stuReg.getCRN() == s.getCRN())
									{
										canAdd = false;
									}
								}
								if (canAdd)
								{
									String[] myList = new String[currentStudent.getTentativeCourses().size()];
									int pos = 0;
									for (int y = 0; y < currentStudent.getTentativeCourses().size(); y++)
									{
										Section s1 = (Section)currentStudent.getTentativeCourses().get(y);
										myList[pos++] = "" + s1.getCRN();
									}
									tentativeList.removeAll();
									tentativeList.setListData(myList);
									removeAllSectionsFromView();
									tentativeListClasses.add(s);
									initAllSectionsFromView();
								}
							}
							else
							{
								System.out.println("Registration Error");
							}
						}
					}
				}
			}
		});
		btnSaveSection.setBounds(10, 247, 119, 23);
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
									canAdd = !main.myDB.checkAlreadyAdded(currentStudent, c);
								}
							}
							
							if (canAdd)
							{
								removeAllSectionsFromView();
								currentStudent.getTentativeCourses().remove(s);
								currentStudent.getRegisteredClasses().add(s);
								registeredClasses.add(s);
								tentativeListClasses.remove(s);
								String[] myList = new String[currentStudent.getTentativeCourses().size()];
								int pos = 0;
								for (int y = 0; y < currentStudent.getTentativeCourses().size(); y++)
								{
									Section s1 = (Section)currentStudent.getTentativeCourses().get(y);
									myList[pos++] = "" + s1.getCRN();
								}
								tentativeList.removeAll();
								tentativeList.setListData(myList);
								
								myList = new String[currentStudent.getRegisteredClasses().size()];
								pos = 0;
								for (int y = 0; y < currentStudent.getRegisteredClasses().size(); y++)
								{
									Section s1 = (Section)currentStudent.getRegisteredClasses().get(y);
									myList[pos++] = "" + s1.getCRN();
								}
								registeredList.removeAll();
								registeredList.setListData(myList);
								
								initAllSectionsFromView();
							}
						}
					}
				}
			}
		});
		btnRegister.setBounds(10, 395, 89, 23);
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
							currentStudent.getRegisteredClasses().remove(s);
							currentStudent.getTentativeCourses().add(s);
							tentativeListClasses.add(s);
							registeredClasses.remove(s);
							String[] myList = new String[currentStudent.getTentativeCourses().size()];
							int pos = 0;
							for (int y = 0; y < currentStudent.getTentativeCourses().size(); y++)
							{
								Section s1 = (Section)currentStudent.getTentativeCourses().get(y);
								myList[pos++] = "" + s1.getCRN();
							}
							tentativeList.removeAll();
							tentativeList.setListData(myList);
							
							myList = new String[currentStudent.getRegisteredClasses().size()];
							pos = 0;
							for (int y = 0; y < currentStudent.getRegisteredClasses().size(); y++)
							{
								Section s1 = (Section)currentStudent.getRegisteredClasses().get(y);
								myList[pos++] = "" + s1.getCRN();
							}
							registeredList.removeAll();
							registeredList.setListData(myList);
							
							initAllSectionsFromView();
						}
					}
				}
			}
		});
		btnDropClass.setBounds(10, 565, 104, 23);
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
						currentStudent.getTentativeCourses().remove(s);
						String[] myList = new String[currentStudent.getTentativeCourses().size()];
						int pos = 0;
						for (int y = 0; y < currentStudent.getTentativeCourses().size(); y++)
						{
							Section s1 = (Section)currentStudent.getTentativeCourses().get(y);
							myList[pos++] = "" + s1.getCRN();
						}
						tentativeList.removeAll();
						tentativeList.setListData(myList);
					}
					
				}
				initAllSectionsFromView();
			}
		});
		btnRemove.setBounds(10, 419, 89, 23);
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
		
		courses.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) 
			{
				if (arg0.getStateChange() == 1)
				{
					if (!courses.getSelectedItem().toString().equalsIgnoreCase(""))
					{
						int hits = 0;
						for (int x = 0; x < main.myDB.getSectionList().size(); x++)
						{
							
							Section s = (Section)main.myDB.getSectionList().get(x);
							if (courses.getSelectedItem().toString().equalsIgnoreCase(departments.getSelectedItem() + " " + s.getCourseNumber()))
							{
								hits++;
							}
						}
						String addCourses[] = new String[hits];
						sectionsList.removeAll();
						int pos = 0;
						for (int x = 0; x < main.myDB.getSectionList().size(); x++)
						{
							
							Section s = (Section)main.myDB.getSectionList().get(x);
							if (courses.getSelectedItem().toString().equalsIgnoreCase(departments.getSelectedItem() + " " + s.getCourseNumber()))
							{
								addCourses[pos] = "" + s.getCRN();
								pos++;
							}
						}
						sectionsList.removeAll();
						sectionsList.setListData(addCourses);
					}
				}
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
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				removeAllSectionsFromView();
				searchedClasses.removeAll(searchedClasses);
				for (int x = 0; x < main.myDB.getSectionList().size(); x++)
				{
					Section s = (Section)main.myDB.getSectionList().get(x);
					if (departments.getSelectedItem() != "")
					{
						if (courses.getSelectedItem() != "")
						{
							String courseInfo[] = courses.getSelectedItem().toString().split(" ");
							if (s.getCourseName().equalsIgnoreCase(courseInfo[0]) && s.getCourseNumber() == Integer.parseInt(courseInfo[1]))
							{
								searchedClasses.add(s);
							}
						}
						else
						{
							if (s.getCourseName().equalsIgnoreCase((String) departments.getSelectedItem()))
							{
								searchedClasses.add(s);
							}
						}
					}
				}
				initAllSectionsFromView();
			}
		});
		
		
		
		int top = 29+25;
		int bottom = 0;
		int time;
		for (int x = 153; x < 154/* + (207-153)*5*/; x += (207-153))
		{
			time = 8;
			for (int y = 29+25; y < 29 + (45*12); y += 45)
			{
				JTextPane test = new JTextPane();
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
		
		this.calendarHeight = bottom-top;
		repaint();
	}
	
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
		repaint();
		
	}
	
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
					
					JTextPane test = new JTextPane();
					test.setText(s.getCRN() + "");
					test.setBounds(144+(71)*(o+1) + (weeklySchedule[o][p].size()*10), (int)(29+25+(percent*(calendarHeight))+(weeklySchedule[o][p].size()*10)), 44, (int) (durationPercent * (calendarHeight)));
					test.setEditable(false);
					test.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent arg0) 
						{
							drawPanel.remove((Component)arg0.getSource());
							drawPanel.add((Component)arg0.getSource());
							
							
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
								ListModel model = sectionsList.getModel();
								
								for (int x = 0; x < model.getSize(); x++)
								{
									if (Integer.parseInt((String)model.getElementAt(x)) == s.getCRN())
									{
										sectionsList.setSelectedIndex(x);
									}
								}
								
								model = tentativeList.getModel();
								
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
											
								if (weeklySchedule[j][k].get(z) != arg0.getSource())
								{
									drawPanel.remove((Component)weeklySchedule[j][k].get(z));
									drawPanel.add((Component)weeklySchedule[j][k].get(z));
								}
							}
							

							selectedSection = s;
							setInfoForCRN(s.getCRN());
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
		repaint();
	}
	
	public void removeAllSectionsFromView()
	{
		removeSectionsFromView(searchedClasses);
		removeSectionsFromView(tentativeListClasses);
		removeSectionsFromView(completedListClasses);
		removeSectionsFromView(registeredClasses);
		removeSectionsFromView(progressClasses);
	}
	
	public void initAllSectionsFromView()
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
	}
	
	public void repaint()
	{
		this.drawPanel.setPreferredSize(drawPanel.getPreferredSize());
		frame.repaint();
	}
	
	public String setInfoForCRN(int aCRN)
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
					
					
					sectionInfo.setText(text);
					//System.out.println(sectionInfoScroll.getVerticalScrollBar().getValue());
					
				}
			}
		}
		return text;
	}
}