package TeamRocketPower;

import java.util.*;
import java.lang.*;

/**
 * RateMyProfessor: holds cumulative data about a specific section for a teacher
 */
public class RateMyProfessor {
    /**
     */
    private String CourseName;

    /**
     */
    private float Likability;

    /**
     */
    private float Toughness;

    /**
     */
    private float Hot;

    /**
     */
    private int TeacherID;

    /**
     */
    private int CourseNumber;

    /**
     */
    private ArrayList Grades;
    
    public RateMyProfessor()
    {
    	Grades = new ArrayList();
    }
    /**
	Initializes a professor from a custom DB string
 */
    public RateMyProfessor(String aToString)
    {
    	//System.out.println(aToString);
    	Grades = new ArrayList();
    	String list[] = aToString.split("\n");
    	CourseName = list[0];
    	Likability = Float.parseFloat(list[1]);
    	Toughness = Float.parseFloat(list[2]);
    	Hot = Float.parseFloat(list[3]);
    	TeacherID = Integer.parseInt(list[4]);
    	CourseNumber = Integer.parseInt(list[5]);
    	for (int x = 6; x < list.length-1; x++)
    	{
    		Grades.add(Float.parseFloat(list[x]));
    	}
    }   
    /**
	Returns the string of the entire class for saving to a flat 
	file database.
 */
    public String toString()
    {
    	String output = "";
    	
    	output += CourseName + "\n" + Likability + "\n" + Toughness + "\n"
    			+ Hot + "\n" + TeacherID + "\n" + CourseNumber;
    	
    	
    	for (int x = 0; x < Grades.size(); x++)
    	{
    		output += "\n";
    		output += Grades.get(x);
    	}   	
    	return output;
    }
    
    public String getCourseName()
    {
    	return CourseName;
    }
    
    public float getLikability()
    {
    	return Likability;
    }
    
    public float getToughness()
    {
    	return Toughness;
    }
    
    public float getHot()
    {
    	return Hot;
    }
    
    public int getTeacherID()
    {
    	return TeacherID;
    }
    
    public int CourseNumber()
    {
    	return CourseNumber;
    }
    
    public ArrayList getGrades()
    {
    	return Grades;
    }
   
    
    public void setCourseName(String aName)
    {
    	CourseName = aName;
    }
    
  
    
    public void addNewLikability(float aLikability)
    {
    	if (Likability == 0)
    		Likability = aLikability;
    	else
    		Likability = (Likability + aLikability)/2;
    }
    
    public void addNewToughness(float aToughness)
    {
    	if (Toughness == 0)
    		Toughness = aToughness;
    	else
    		Toughness = (Toughness + aToughness)/2;
    }
    
    public void addHotness(float aHot)
    {
    	if (Hot == 0)
    		Hot = aHot;
    	else
    	{
    		Hot = (aHot + Hot)/2;
    	}
    }
    
    public void setTeacherID(int aID)
    {
    	TeacherID = aID;
    }
    
    public void setCourseNumber(int aNum)
    {
    	CourseNumber = aNum;
    }
    
    public void addGrade(float aGrade)
    {
    	Grades.add(aGrade);
    }
}

