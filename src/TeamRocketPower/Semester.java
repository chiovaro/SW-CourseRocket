package TeamRocketPower;

import java.util.*;

/**
 * Holds information about a semester
 */
public class Semester {
    /**
     */
    private Date StartDate;

    /**
     */
    private Date EndDate;
    /**
	Initializes a semester with a start and end date
 */
    public Semester(Date start, Date end)
    {
    	StartDate = start;
    	EndDate = end;
    }
    /**
	Init from a DB flat file.
 */
    public Semester(String aToString)
    {
    	String split[] = aToString.split("\n");
    	StartDate = new Date(Long.parseLong(split[0]));
    	EndDate = new Date(Long.parseLong(split[1]));
    }
    /**
	String to save into the flat file DB.
 */
    public String toString()
    {
    	String output = StartDate.getTime() + "\n" + EndDate.getTime();
    	return output;
    }
        
    public Date getEndDate()
    {
    	return EndDate;
    }
    
    public Date getStartDate()
    {
    	return StartDate;
    }
}
