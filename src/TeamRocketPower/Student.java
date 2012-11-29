package TeamRocketPower;

import java.util.*;
import java.lang.*;

/**
 * Student: Holds information about the student.
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

    private ArrayList RegisteredCourses;
    
    /**
     */
    private String ProgramOfStudy;
    
    private String StudentName;
    
    private int StudentID;
    
    private ArrayList completedRatings;
    
    public Student()
    {
    	completedRatings = new ArrayList();
    	TentativeCourses = new ArrayList();
    	ProgressCourses = new ArrayList();
    	CompletedCourses = new ArrayList();
    	RegisteredCourses = new ArrayList();
    }
    /**
	Initializes student from a DB string
 */
    public Student(String aString)
    {
    	completedRatings = new ArrayList();
    	TentativeCourses = new ArrayList();
    	ProgressCourses = new ArrayList();
    	CompletedCourses = new ArrayList();
    	RegisteredCourses = new ArrayList();
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
    	newPos = position++;
    	for (int x = 0; x < Integer.parseInt(list[newPos]); x++)
    	{
    		int crn = Integer.parseInt(list[position++]);
    		for (int k = 0; k < Main.myDB.sections.size(); k++)
    		{
    			Section s = (Section)Main.myDB.sections.get(k);
    			if (s.getCRN() == crn)
    			{
    				RegisteredCourses.add(s);
    			}
    		}
    	}
    	newPos = position++;
    	for (int x = 0; x < Integer.parseInt(list[newPos]); x++)
    	{
    		String finished = list[position++];
    		completedRatings.add(finished);
    	}
    }
    /**
	Expand rating information after the DB has finished initalizing
 */
    public void expandRatings()
    {
    	ArrayList add = new ArrayList();
    	ArrayList remove = new ArrayList();
    	for (int x = 0; x < completedRatings.size(); x++)
    	{
    		String finished = completedRatings.get(x).toString();
    		for (int y = 0; y < Main.myDB.sections.size(); y++)
    		{
    			Section s = (Section)Main.myDB.sections.get(y);
    			if (s.getCRN() == Integer.parseInt(finished))
    			{
    				add.add(s);    				
    				remove.add(finished);

    			}
    		}
    	}
    	completedRatings.addAll(add);
    	completedRatings.removeAll(remove);
    }
    
    public int getStudentID()
    {
    	return StudentID;
    }
    
    public ArrayList getCompletedRatings()
    {
    	return completedRatings;
    }
    
    public ArrayList getProgressCourses()
    {
    	return this.ProgressCourses;
    }
    
    public ArrayList getTentativeCourses()
    {
    	return this.TentativeCourses;
    }
    
    public ArrayList getCompletedCourses()
    {
    	return this.CompletedCourses;
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
    
    public ArrayList getRegisteredClasses()
    {
    	return RegisteredCourses;
    }
    
    /**
	String that would be saved to a flat file DB.
 */
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
    	myString += "\n" + RegisteredCourses.size();
    	for (int x = 0; x < RegisteredCourses.size(); x++)
    	{
    		myString += "\n";
    		Section s = (Section)RegisteredCourses.get(x);
    		myString += s.getCRN();
    	}
    	myString += "\n" + completedRatings.size();
    	for (int x = 0; x < completedRatings.size(); x++)
    	{
    		myString += "\n";
    		Section s = (Section)completedRatings.get(x);
    		myString += s.getCRN();
    	}
    	
    	return myString;
    }
}

