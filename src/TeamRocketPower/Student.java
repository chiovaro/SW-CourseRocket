package TeamRocketPower;

import java.util.*;
import java.lang.*;

/**
 */
public class Student {
    /**
     */
    private ArrayList CompletedCourses;

    /**
     */
    private ArrayList ProgressCourses;

    /**
     */
    private ArrayList TentativeCourses;

    /**
     */
    private String ProgramOfStudy;
    
    private String StudentName;
    
    private int StudentID;
    
    public Student()
    {
    	TentativeCourses = new ArrayList();
    	ProgressCourses = new ArrayList();
    	CompletedCourses = new ArrayList();
    }
    
    public Student(String aString)
    {
    	TentativeCourses = new ArrayList();
    	ProgressCourses = new ArrayList();
    	CompletedCourses = new ArrayList();
    	
    	String list[] = aString.split("\n");
    	this.setProgram(list[2]);
    	this.setName(list[0]);
    	this.setStudentID(Integer.parseInt(list[1]));
    	
    	int position = 4;
    	for (int x = 0; x < Integer.parseInt(list[3]); x++)
    	{
    		int crn = Integer.parseInt(list[position++]);
    		for (int k = 0; k < Main.myDB.sections.size(); k++)
    		{
    			Section s = (Section)Main.myDB.sections.get(k);
    			if (s.getCRN() == crn)
    			{
    				CompletedCourses.add(s);
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
    				ProgressCourses.add(s);
    			}
    		}
    	}
    	newPos = position++;
    	for (int x = 0; x < Integer.parseInt(list[newPos]); x++)
    	{
    		int crn = Integer.parseInt(list[position++]);
    		for (int k = 0; k < Main.myDB.sections.size(); k++)
    		{
    			Section s = (Section)Main.myDB.sections.get(k);
    			if (s.getCRN() == crn)
    			{
    				TentativeCourses.add(s);
    			}
    		}
    	}
    }
    
    public int getStudentID()
    {
    	return StudentID;
    }
    
    public void setName(String aString)
    {
    	StudentName = aString;
    }
    public void setStudentID(int aID)
    {
    	StudentID = aID;
    }
    public void setProgram(String aProgram)
    {
    	ProgramOfStudy = aProgram;
    }
       
    public String toString()
    {
    	String myString = "";
    	
    	myString += StudentName + "\n" + StudentID + "\n" + ProgramOfStudy;
    	
    	myString += "\n" + CompletedCourses.size();
    	for (int x = 0; x < CompletedCourses.size(); x++)
    	{
    		myString += "\n";
    		Section s = (Section)CompletedCourses.get(x);
    		myString += s.getCRN();
    	}
    	myString += "\n" + ProgressCourses.size();
    	for (int x = 0; x < ProgressCourses.size(); x++)
    	{
    		myString += "\n";
    		Section s = (Section)ProgressCourses.get(x);
    		myString += s.getCRN();
    	}
    	myString += "\n" + TentativeCourses.size();
    	for (int x = 0; x < TentativeCourses.size(); x++)
    	{
    		myString += "\n";
    		Section s = (Section)TentativeCourses.get(x);
    		myString += s.getCRN();
    	}
    	
    	
    	return myString;
    }
}

