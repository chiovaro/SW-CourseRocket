package TeamRocketPower;

import java.util.*;

/**
 */
public class Semester {
    /**
     */
    private Date StartDate;

    /**
     */
    private Date EndDate;
    
    public Semester(Date start, Date end)
    {
    	StartDate = start;
    	EndDate = end;
    }
    
    public Semester(String aToString)
    {
    	String split[] = aToString.split("\n");
    	StartDate = new Date(Long.parseLong(split[0]));
    	EndDate = new Date(Long.parseLong(split[1]));
    }
    
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
