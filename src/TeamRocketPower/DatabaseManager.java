package TeamRocketPower;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class DatabaseManager {
	
	ArrayList classes;
	
	public DatabaseManager()
	{
		classes = new ArrayList();
		parseCourses();
		for (int x = 0; x < classes.size(); x++)
		{
			Course c = (Course)classes.get(x);
			System.out.println(c.toString());
		}
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
}
