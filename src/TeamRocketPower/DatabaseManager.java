package TeamRocketPower;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class DatabaseManager {
	
	static ArrayList classes;
	static ArrayList teachers;
	static ArrayList students;
	static ArrayList sections;
	public DatabaseManager()
	{
		classes = new ArrayList();
		teachers = new ArrayList();
		students = new ArrayList();
		sections = new ArrayList();
		//parseCourses();
		//parseTeachers();
		//parseStudents();
		//generateClasses();
		//saveDB();
		loadDB();
		for (int x = 0; x < sections.size(); x++)
		{
			Section s = (Section)sections.get(x);
			s.expandArrayListConnections();
		}
	}
	
	public boolean registrationPrereqTest(Student aStudent, Section aSection)
	{
		for (int x = 0; x < this.classes.size(); x++)
		{
			Course c = (Course)classes.get(x);
			if (c.getDepartment().equalsIgnoreCase(aSection.getCourseName())
					&& c.getCourseNumber() == aSection.getCourseNumber())
			{	
				
				for (int y = 0; y < c.getPrereqList().size(); y++)
				{
					String prereq = (String)c.getPrereqList().get(y);
					if (!prereq.equalsIgnoreCase(""))
					{
						boolean passed = false;
						for (int z = 0; z < aStudent.getCompletedCourses().size(); z++)
						{
							Section s = (Section)aStudent.getCompletedCourses().get(z);
							String sectionName = s.getCourseName() + " " + s.getCourseNumber();
							if (sectionName.equalsIgnoreCase(prereq))
							{
								passed = true;
							}
						}
						for (int z = 0; z < aStudent.getProgressCourses().size(); z++)
						{
							Section s = (Section)aStudent.getProgressCourses().get(z);
							String sectionName = s.getCourseName() + " " + s.getCourseNumber();
							if (sectionName.equalsIgnoreCase(prereq))
							{
								passed = true;
							}
						}
						
						/*DELETE THIS ITS A HACK TO NOT HAVE REQS*/
						System.out.println("HERE");
						for (int z = 0; z < aStudent.getRegisteredClasses().size(); z++)
						{
							Section s = (Section)aStudent.getRegisteredClasses().get(z);
							String sectionName = s.getCourseName() + " " + s.getCourseNumber() + " ";
							String sectionName2 = s.getCourseName() + " " + s.getCourseNumber();
							System.out.println(prereq + " : " + sectionName);
							if (sectionName.equalsIgnoreCase(prereq)
									|| sectionName2.equalsIgnoreCase(prereq))
							{
								passed = true;
							}
						}
						
						
						
						if (!passed)
						{
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	public boolean classesCollide(Section s1, Section s2)
	{
		ArrayList meets1 = s1.getMeetingTimes();
		ArrayList meets2 = s2.getMeetingTimes();
		
		for (int x = 0; x < meets1.size(); x++)
		{
			for (int y = 0; y < meets2.size(); y++)
			{
				Date meetDate1 = (Date)meets1.get(x);
				Date meetDate2 = (Date)meets2.get(y);
				int c1Duration = s1.getClassDuration();
				int c2Duration = s2.getClassDuration();
				if (meetDate1.getDay() == meetDate2.getDay())
				{
					float start = meetDate1.getHours();
					float end = start + ((float)c1Duration/60.0f);
					
					float meetStart = meetDate2.getHours();
					float meetEnd = meetStart + ((float)c2Duration/60.0f);
					
					Rectangle aRect = new Rectangle();
					Rectangle bRect = new Rectangle();
					aRect.setBounds(0, (int)start*60, 10, (int)((float)c1Duration));
					bRect.setBounds(0, (int)meetStart*60, 10, (int)((float)c2Duration));
					
					if (aRect.intersects(bRect))
					{
						return true;
					}
				}
			}
		}
		return false;
	}
	
	
	public boolean checkAlreadyAdded(Student aStudent, Course c)
	{
		for (int s = 0; s < aStudent.getRegisteredClasses().size(); s++)
		{
			Section sec = (Section)aStudent.getRegisteredClasses().get(s);
			for (int x1 = 0; x1 < this.classes.size(); x1++)
			{
				Course c1 = (Course)classes.get(x1);
				if (c1.getDepartment().equalsIgnoreCase(sec.getCourseName())
						&& c1.getCourseNumber() == sec.getCourseNumber())
				{
					if (c.getDepartment().equalsIgnoreCase(c1.getDepartment())
							&& c.getCourseNumber() == c1.getCourseNumber())
					{
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean registerStudentSection(Student aStudent, Section aSection)
	{
		if (!aStudent.getTentativeCourses().contains(aSection) && this.registrationPrereqTest(aStudent, aSection))
		{
			ArrayList list = aStudent.getTentativeCourses();
			list.add(aSection);
			
			return true;
		}
		return false;
	}
	
	public void generateClasses()
	{
		Date startDate;
		Date endDate;
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.WEEK_OF_MONTH, 2);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		startDate = cal.getTime();
		
		cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, Calendar.APRIL);
		cal.set(Calendar.WEEK_OF_MONTH, 4);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		endDate = cal.getTime();
		
		
		for (int x = 0; x < classes.size(); x++)
		{
			Course c = (Course)classes.get(x);
			int duration = ((int)(Math.random()*2)) == 0 ? 75 : 105;
			int rand = (int)(Math.random()*3) + 1;
			for (int t = 0; t < rand; t++)
			{
				int dayOfWeek = (int)(Math.random()*2);
				int timeOfDay = 8 + (int)(Math.random()*10);
				Date day1;
				Date day2;
				
				
				
				cal = Calendar.getInstance();
				cal.set(Calendar.DAY_OF_WEEK, (dayOfWeek == 0) ? Calendar.MONDAY : Calendar.TUESDAY);
				cal.set(Calendar.HOUR_OF_DAY, timeOfDay);
				cal.set(Calendar.MINUTE, 0);
				day1 = cal.getTime();
				
				Calendar cal1 = Calendar.getInstance();
				cal1.set(Calendar.DAY_OF_WEEK, (dayOfWeek == 0) ? Calendar.WEDNESDAY : Calendar.THURSDAY);
				cal1.set(Calendar.HOUR_OF_DAY, timeOfDay);				
				cal1.set(Calendar.MINUTE, 0);
				day2 = cal1.getTime();
				
				
				
		//		System.out.println(day1.getDay() + ", " + day2.getDay());
			
				
				Teacher selectedTeacher = null;
				
				while (selectedTeacher == null)
				{
					int randomTeacher = (int)(Math.random()*teachers.size());
					boolean canTeach = true;
					Teacher teach = (Teacher)teachers.get(randomTeacher);
					
					ArrayList teacherCourses = teach.getCurrentCourses();
					for (int k = 0; k < teacherCourses.size(); k++)
					{
						Section s = (Section)teacherCourses.get(k);
						ArrayList meets = s.getMeetingTimes();
						int cDuration = s.getClassDuration();
						Date meetDate = (Date)meets.get(0);
						if (day1.getDay() == meetDate.getDay())
						{
							float start = day1.getHours();
							float end = start + ((float)s.getClassDuration()/60.0f);
							
							float meetStart = meetDate.getHours();
							float meetEnd = meetStart + ((float)duration/60.0f);
							
							if (meetStart > start && meetStart < end
								|| start > meetStart && start < meetEnd)
							{
								canTeach = false;
							}
						}
					}
					if (canTeach)
					{
						selectedTeacher = teach;
					}
				}
				if (selectedTeacher != null)
				{
					Section s = new Section();
					s.setClassDuration(duration);
					s.addMeetingTime(day1);
					s.addMeetingTime(day2);
					s.setCRN((int)(Math.random()*9999) + 10000);
					s.setCourseName(c.getDepartment());
					s.setCourseNumber(c.getCourseNumber());
					
					cal = Calendar.getInstance();
					cal.set(Calendar.MONTH, Calendar.MAY);
					cal.set(Calendar.WEEK_OF_MONTH, 1);
					cal.set(Calendar.DAY_OF_WEEK, day2.getDay());
					
					s.setFinalDate(cal.getTime());
					s.setMaxCapacity((int)(Math.random()*10)+25);
					Semester k = new Semester(day1, day2);
					s.setSemester(k);
					s.setCourseName(c.getDepartment());
					s.setTeacherID(selectedTeacher.getTeacherID());
					selectedTeacher.addCurrentCourse(s);
					sections.add(s);
					
				}
			}
		}		
	}
	
	public void saveDB()
	{
		FileOutputStream out;
        PrintStream p;
        try
        {
        	out = new FileOutputStream("FULLDB.txt");
            p = new PrintStream( out );
            p.println("classes");
        	for (int x = 0;x<classes.size();x++)
        	{
        		Course c = (Course)classes.get(x);
        		p.println("--");
        		p.println(c.toString());
        		p.println("--");
        	}
        	p.println("endClasses");
        	
        	p.println("sections");
        	for (int x = 0;x<sections.size();x++)
        	{
        		Section s = (Section)sections.get(x);
        		p.println("--");
        		p.println(s.toString());
        		p.println("--");
        	}
        	p.println("endSections");     	
        	
        	
        	p.println("teachers");
        	for (int x = 0;x<teachers.size();x++)
        	{
        		Teacher t = (Teacher)teachers.get(x);
        		p.println("--");
        		p.println(t.toString());
        		p.println("--");
        	}
        	p.println("endTeachers");
        	
        	p.println("students");
        	for (int x = 0;x<students.size();x++)
        	{
        		Student t = (Student)students.get(x);
        		p.println("--");
        		p.println(t.toString());
        		p.println("--");
        	}
        	p.println("endStudents");
        	
            p.close();
        }
        catch (Exception e){System.err.println ("Error writing to file");}
	}
	
	public void loadDB()
	{
		FileInputStream fin;
		String plr;
		try
		{
		    fin = new FileInputStream ("FULLDB.txt");;  
		    DataInputStream dis = new DataInputStream(fin);
		    String name = dis.readLine();		    
		    dis.readLine();
		    while (!name.equalsIgnoreCase("endClasses"))
		    {    		    	
		    	String temp = dis.readLine();
		    	String finalString = "";
		    	
		    	while (!temp.equalsIgnoreCase("--"))
		    	{
		    		finalString += temp + "\n";
		    		temp = dis.readLine();
		    	}
		    	
		    	Course c = new Course(finalString);
		    	classes.add(c);
		    	name = dis.readLine();
		    }
		    
		    dis.readLine();
		    name = dis.readLine();
		    while (!name.equalsIgnoreCase("endSections"))
		    {    		    	
		    	String temp = dis.readLine();
		    	
		    	String finalString = "";
		    	
		    	while (!temp.equalsIgnoreCase("--"))
		    	{
		    		finalString += temp + "\n";
		    		temp = dis.readLine();
		    	}

		    	Section s = new Section(finalString);
		    	sections.add(s);
		    	name = dis.readLine();
		    }
		    
		    dis.readLine();
		    dis.readLine();
		    while (!name.equalsIgnoreCase("endTeachers"))
		    {    		    	
		    	String temp = dis.readLine();
		    	
		    	String finalString = "";
		    	
		    	while (!temp.equalsIgnoreCase("--"))
		    	{
		    		finalString += temp + "\n";
		    		temp = dis.readLine();
		    	}

		    	Teacher t = new Teacher(finalString);
		    	teachers.add(t);
		    	name = dis.readLine();
		    }
		    
		    name = dis.readLine();
		    dis.readLine();
		    while (!name.equalsIgnoreCase("endStudents"))
		    {    		    	
		    	String temp = dis.readLine();
		    	
		    	String finalString = "";
		    	
		    	while (!temp.equalsIgnoreCase("--"))
		    	{
		    		finalString += temp + "\n";
		    		temp = dis.readLine();
		    	}

		    	Student t = new Student(finalString);
		    	students.add(t);
		    	//System.out.println(t.toString());
		    	name = dis.readLine();
		    }
		    
		    fin.close();
		}
		catch (IOException e)
		{}
	}
	
	public void parseCourses()
	{
		FileInputStream fin;
		try
		{
		    fin = new FileInputStream ("classesDB.txt");
		    DataInputStream stream = new DataInputStream(fin);
		    String courseSubject = stream.readLine();
		    String courseNumber = stream.readLine();
		    while (courseNumber != null)
		    {
		    	String courseName = stream.readLine();
		    	String courseFormalName = stream.readLine();
		    	String coursePrereqs = stream.readLine();
		    	String courseDescription = stream.readLine();
		    	String courseCredits = stream.readLine();
		    	
		    			    	
		    	Course currentCourse = new Course();
		    	currentCourse.setCourseDescription(courseDescription);
		    	currentCourse.setCourseName(courseFormalName);
		    	currentCourse.setCourseNumber(Integer.parseInt(courseNumber));
		    	currentCourse.setCourseSubject(courseSubject);
		    	String[] list = courseCredits.split(" ");
		    	currentCourse.setCreditHours((int)Float.parseFloat(list[0]));
		    	coursePrereqs = coursePrereqs.replaceFirst("Prerequisite: ", "");
		    	list = coursePrereqs.split(", ");
		    	for (int k = 0; k < list.length; k++)
		    	{
		    		currentCourse.addPrereq(list[k]);
		    	}		    	
		    	classes.add(currentCourse);
		    	
	    		courseNumber = stream.readLine();
		    }	    
		    fin.close();
		}
		catch (IOException e)
		{}
	}
	
	public void parseTeachers()
	{
		FileInputStream fin;
		try
		{
		    fin = new FileInputStream ("professorsDB.txt");
		    DataInputStream stream = new DataInputStream(fin);
		    String temp;
		    while ((temp = stream.readLine()) != null)
		    {
		    	Teacher newTeach = new Teacher();
		    	newTeach.setDepartmentName("CSc");
		    	newTeach.setName(temp);
		    	newTeach.setTeacherID((int)(100000 + Math.random()*900000));
		    	teachers.add(newTeach);
		    }

		    fin.close();
		}
		catch (IOException e)
		{}
	}
	
	public void parseStudents()
	{
		FileInputStream fin;
		try
		{
		    fin = new FileInputStream ("studentsDB.txt");
		    DataInputStream stream = new DataInputStream(fin);
		    String temp;
		    while ((temp = stream.readLine()) != null)
		    {
		    	Student newStudent = new Student();
		    	newStudent.setProgram("CSc");
		    	newStudent.setName(temp);
		    	newStudent.setStudentID((int)(100000 + Math.random()*900000));
		    	students.add(newStudent);
		    }

		    fin.close();
		}
		catch (IOException e)
		{}
	}
	
	public ArrayList getCourseList()
	{
		return classes;
	}
	
	public ArrayList getSectionList()
	{
		return sections;
	}
	
	public ArrayList getTeachers()
	{
		return teachers;
	}
	
	public ArrayList getStudents()
	{
		return students;
	}
}
