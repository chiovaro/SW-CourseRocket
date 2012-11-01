package TeamRocketPower;

/**
	CompletedCourse: This class will be used to keep track of a students
	final grade in a completed course. We are able to do a reverse lookup
	of the CRN to find the name and course number of the class.
 */
public class CompletedCourse {
    /**
     */
    private int CRN;

    /**
     */
    private float FinalGrade;
    
    public CompletedCourse(int cRN, float finalGrade)
    {
    	CRN = cRN;
    	FinalGrade = finalGrade;
    }
    
    public CompletedCourse(String aToString)
    {
    	String list[] = aToString.split("\n");
    	CRN = Integer.parseInt(list[0]);
    	FinalGrade = Float.parseFloat(list[1]);
    }
    
    public String toString()
    {
    	String output = "";
    	output += CRN + "\n" + FinalGrade;
    	return output;
    }    
}

