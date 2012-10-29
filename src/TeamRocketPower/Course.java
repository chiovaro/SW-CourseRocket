package TeamRocketPower;

import java.util.*;
import java.lang.*;

/**
 */
public class Course extends Section {
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
    public Course()
    {
    	Books = new ArrayList();
    	PrereqList = new ArrayList();
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
    	PrereqList.add(aPrereq);
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
    
}
