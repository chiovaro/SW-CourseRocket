package TeamRocketPower;

import java.util.*;

/**
 * Teacher: Holds the information about a teacher
 */
public class Teacher extends Section {
    /**
     */
    private ArrayList CurrentCourse;

    /**
     */
    private ArrayList TaughtCourses;

    /**
     */
    private String Dept;
    
    private String TeacherName;
    
    private int TeacherID;
    
    public Teacher()
    {
    	CurrentCourse = new ArrayList();
    	TaughtCourses = new ArrayList();
    }
    /**
	Initalizes teacher from a DB string.
 */
    public Teacher(String aString)
    {
    	CurrentCourse = new ArrayList();
    	TaughtCourses = new ArrayList();
    	String list[] = aString.split("\n");
    	this.setName(list[0]);
    	this.setTeacherID(Integer.parseInt(list[1]));
    	this.setDepartmentName(list[2]);
    	
    	int position = 4;
    	for (int x = 0; x < Integer.parseInt(list[3]); x++)
    	{
    		int crn = Integer.parseInt(list[position++]);
    		for (int k = 0; k < Main.myDB.sections.size(); k++)
    		{
    			Section s = (Section)Main.myDB.sections.get(k);
    			if (s.getCRN() == crn)
    			{
    				CurrentCourse.add(s);
    			}
    		}
    	}
    	int newPos = position++;
    	for (int x = 0; x < Integer.parseInt(list[newPos]); x++)
    	{
    		int crn = Integer.parseInt(list[position++]);
    		for (int k = 0; k < Main.myDB.sections.size(); k++)
    		{
    			Section s = (Section)Main.myDB.sections.get(k);
    			if (s.getCRN() == crn)
    			{
    				TaughtCourses.add(s);
    			}
    		}
    	}
    }
    
    public String getTeacherName()
    {
    	return TeacherName;
    }
    
    public int getTeacherID()
    {
    	return TeacherID;
    }
    
    public ArrayList getCurrentCourses()
    {
    	return CurrentCourse;
    }
    
    public void setName(String aString)
    {
    	TeacherName = aString;
    }
    
    public void setTeacherID(int aID)
    {
    	TeacherID = aID;
    }
    
    public void addTaughtCourse(Section aSection)
    {
    	TaughtCourses.add(aSection);
    }
    
    public void addCurrentCourse(Section aSection)
    {
    	CurrentCourse.add(aSection);
    }
    
    public void setDepartmentName(String aDepartment)
    {
    	Dept = aDepartment;
    }
    /**
	String to save to a flat file DB.
 */
    public String toString()
    {
    	String myString = "";
    	   	
    	myString += TeacherName + "\n" + TeacherID + "\n" + Dept;
    	myString += "\n" + CurrentCourse.size();
    	for (int x = 0; x < CurrentCourse.size(); x++)
    	{
    		myString += "\n";
    		Section s = (Section)CurrentCourse.get(x);
    		myString += s.getCRN();
    	}
    	myString += "\n" + TaughtCourses.size();
    	for (int x = 0; x < TaughtCourses.size(); x++)
    	{
    		myString += "\n";
    		Section s = (Section)TaughtCourses.get(x);
    		myString += s.getCRN();
    	}   	
    	return myString;
    }
}

