package TeamRocketPower;


import java.util.*;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.*;

/**
 */
public class Course{
    /**
     */
    private String CourseName;
    
    private String CourseSubject;

    /**
     */
    private String CourseDescription;

    /**
     */
    private ArrayList PrereqList;

    /**
     */
    private int CourseNumber;

    /**
     */
    private int CreditHours;


    /**
     */
    private ArrayList Books;

    /**
     * @param param1 
     */
    
    private ArrayList sections;
    public Course()
    {
    	Books = new ArrayList();
    	PrereqList = new ArrayList();
    	sections = new ArrayList();
    }
    
    public Course(String aString)
    {
    	Books = new ArrayList();
    	PrereqList = new ArrayList();
    	sections = new ArrayList();
    	
    	String fields[] = aString.split("\n");
    	
    	String courseSubject = fields[0];
	    String courseNumber = fields[1];
       	String courseName = fields[2];
    	String courseFormalName = fields[3];
    	String coursePrereqs = fields[4];
    	String courseDescription = fields[5];
    	String courseCredits = fields[6];
    	int courseSectionsSize = Integer.parseInt(fields[7]);
    	
    	setCourseDescription(courseDescription);
    	setCourseName(courseFormalName);
    	setCourseNumber(Integer.parseInt(courseNumber));
    	setCourseSubject(courseSubject);
    	String[] list = courseCredits.split(" ");
    	setCreditHours((int)Float.parseFloat(list[0]));
    	coursePrereqs = coursePrereqs.replaceAll("Prerequisites: ", "");
    	coursePrereqs = coursePrereqs.replaceAll("Prerequisites:", "");
    	//System.out.println(coursePrereqs);
    	list = coursePrereqs.split(", ");
    	for (int k = 0; k < list.length; k++)
    	{
    		//System.out.println(list[k]);
    		String splits[] = list[k].split("or");
    		for (int l = 0; l < splits.length; l++)
    		{
    			if (splits[l].trim().length() > 0)
    				addPrereq(splits[l].trim());
    		}
    	}

    	
    	int pos = 8;
    	for (int t = 0; t < courseSectionsSize; t++)
    	{
    		int crn = Integer.parseInt(fields[pos++]);
    		for (int k = 0; k < Main.myDB.sections.size(); k++)
    		{
    			Section s = (Section)Main.myDB.sections.get(k);
    			if (s.getCRN() == crn)
    			{
    				addSection(s);
    			}
    		}
    	}
    	
    	int bookSize = Integer.parseInt(fields[pos++]);
    	for (int t = 0; t < bookSize; t++)
    	{
    		this.addBook(fields[pos++]);
    	}
    	
    }
    
    public String getDepartment()
    {
    	return this.CourseSubject;
    }
    
    public int getCourseNumber()
    {
    	return this.CourseNumber;	
    }
    
    public String getCourseName()
    {
    	return this.CourseName;
    }
    
    public void addSection(Section aSection)
    {
    	sections.add(aSection);
    }
    
    public ArrayList getSectionList()
    {
    	return sections;
    }
    
    public void setCourseName(String aName)
    {
    	CourseName = aName;
    }
    
    public void setCourseSubject(String aSub)
    {
    	CourseSubject = aSub;
    }
    
    public void setCourseNumber(int aNumber)
    {
    	CourseNumber = aNumber;
    }
    
    public void addPrereq(String aPrereq)
    {
    		String splits[] = aPrereq.split("or");
    		for (int l = 0; l < splits.length; l++)
    			PrereqList.add(splits[l].trim());
    }
    
    public void addBook(String aBook)
    {
    	Books.add(aBook);
    }
    
    public void setCourseDescription(String aDescript)
    {
    	CourseDescription = aDescript;
    }
    
    public void setCreditHours(int aNumber)
    {
    	CreditHours = aNumber;
    }
    
    public ArrayList getPrereqList()
    {
    	return PrereqList;
    }
    
    public String getCourseDescription()
    {
    	return CourseDescription;
    }
    
    public int getCreditHours()
    {
    	return CreditHours;
    }
    
    public String toString()
    {
    	String sendString = CourseSubject + "\n" + CourseNumber + "\n" + CourseName + "\n" + CourseName + "\nPrerequisites: ";
    	
    	for (int x = 0; x < PrereqList.size(); x++)
    	{
    		if (x == PrereqList.size()-1)
    			sendString += PrereqList.get(x);
    		else
    			sendString += PrereqList.get(x) + ", ";
    	}    	
    	sendString += "\n";
    	
    	sendString += CourseDescription + "\n" + CreditHours + " Credit Hours\n";	
    	
    	sendString += sections.size();
    	for (int x = 0; x < sections.size(); x++)
    	{
    		Section s = (Section)sections.get(x);
    		sendString += "\n" + s.getCRN();
    	}
    	
    	sendString += "\n" + Books.size();
    	for (int x = 0; x < Books.size(); x++)
    	{
    		sendString += "\n" + Books.get(x);
    	}
    	
    	return sendString;
    }
}
