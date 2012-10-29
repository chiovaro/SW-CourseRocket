package TeamRocketPower;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class DatabaseManager {
	
	ArrayList classes; //Update
	
	public DatabaseManager()
	{
		parseCourses();
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
		    	
		    	
		    	
		    	System.out.println(courseSubject + " " + courseNumber + " - " + courseName);
		    	System.out.println(courseFormalName);
		    	System.out.println(coursePrereqs);
		    	System.out.println(courseDescription);
		    	System.out.println(courseCredits);
		    	Course currentCourse = new Course();
		    	currentCourse.setCourseDescription(courseDescription);
		    	currentCourse.setCourseName(courseFormalName);
		    	currentCourse.setCourseNumber(Integer.parseInt(courseNumber));
		    	String[] list = courseCredits.split(" ");
		    	currentCourse.setCreditHours(Integer.parseInt(list[0]));
		    	coursePrereqs = coursePrereqs.replaceFirst("Prerequisite: ", "");
		    	list = coursePrereqs.split(", ");
		    	for (int k = 0; k < list.length; k++)
		    	{
		    		currentCourse.addPrereq(list[k]);
		    	}
		    	
		    	/*
		    	
		    	
		    	
	    		String y = new DataInputStream(fin).readLine();	
	    		String xy = new DataInputStream(fin).readLine();
	    		double y1 = Double.parseDouble(y);
	    		double x1 = Double.parseDouble(x);
	    		double xy1 = Double.parseDouble(xy);
	    		maps[(int)x1][(int)y1] = (int)xy1;
	    		System.out.println(x1);
	    		System.out.println(y1);
	    		System.out.println(xy1);
	    		*/
	    		courseNumber = stream.readLine();
		    }	    
		    fin.close();
		}
		catch (IOException e)
		{}
	}
}
