package TeamRocketPower;

import java.util.*;
/**
*/
public class Section{
	/**
	*/
	private int CRN;
	/**
	*/
	private ArrayList RegisteredStudents;
	/**
	*/
	private int TeacherID;
	/**
	*/
	private int MaxCapacity;
	/**
	*/
	private ArrayList WaitingList;
	/**
	*/
	private int CourseNumber;
	
	private String CourseName;
	/**
	*/
	private ArrayList MeetingTimes;
	/**
	*/
	private Semester Semester;
	/**
	*/
	private Date FinalDate;
	/**
	*/
	private RateMyProfessor TeacherReview;
	/**
	*/
	private ArrayList FinalGrades;

	private int ClassDuration;
	
	public Section()
	{
		FinalGrades = new ArrayList();
		TeacherReview = new RateMyProfessor();
		MeetingTimes = new ArrayList();
		WaitingList = new ArrayList();
		RegisteredStudents = new ArrayList();
		FinalDate = new Date(0);
		Semester = new Semester(new Date(0), new Date(0));
	}
	
	public Section(String aToString)
	{
		//System.out.println(aToString);
		FinalGrades = new ArrayList();
		TeacherReview = new RateMyProfessor();
		MeetingTimes = new ArrayList();
		WaitingList = new ArrayList();
		RegisteredStudents = new ArrayList();
		
		String fields[] = aToString.split("\n");
		CRN = Integer.parseInt(fields[0]);
		TeacherID = Integer.parseInt(fields[1]);
		MaxCapacity = Integer.parseInt(fields[2]);
		CourseNumber = Integer.parseInt(fields[3]);
		FinalDate = new Date(Long.parseLong(fields[4]));
		ClassDuration = Integer.parseInt(fields[5]);
		CourseName = fields[6];
		int pos = 8;
		for (int x = 0; x < Integer.parseInt(fields[7]); x++)
		{
			RegisteredStudents.add(Integer.parseInt(fields[pos++]));
		}
		int newPos = pos++;
		for (int x = 0; x < Integer.parseInt(fields[newPos]); x++)
		{
			WaitingList.add(Integer.parseInt(fields[pos++]));
		}
		newPos = pos++;
		for (int x = 0; x < Integer.parseInt(fields[newPos]); x++)
		{
			MeetingTimes.add(new Date(Long.parseLong(fields[pos++])));
		}
		newPos = pos++;
		for (int x = 0; x < Integer.parseInt(fields[newPos]); x++)
		{
			FinalGrades.add(fields[pos++]);
		}
		pos++;
		int startPos = pos;
		String sem = "";
		while (!fields[pos].equalsIgnoreCase("-+-"))
		{
			if (pos == startPos)
				sem += fields[pos++];
			else
				sem += "\n" + fields[pos++];
		}
		Semester = new Semester(sem);

		pos++;
		pos++;
		startPos = pos;
		String teach = "";
		while (!fields[pos].equalsIgnoreCase("-+-"))
		{
			if (pos == startPos)
				teach += fields[pos++];
			else
				teach += "\n" + fields[pos++];
		}
		TeacherReview = new RateMyProfessor(teach);
	}
	
	public int getTeacherID()
	{
		return TeacherID;
	}
	
	public ArrayList getMeetingTimes()
	{
		return MeetingTimes;
	}
	
	public int getClassDuration()
	{
		return ClassDuration;
	}
	
	
	public void setClassDuration(int aDuration)
	{
		ClassDuration = aDuration;
	}
	
	public void addFinalGrade(float aGrade)
	{
		FinalGrades.add(aGrade);
	}
	
	public void addTeacherReview(RateMyProfessor aRating)
	{
		if (aRating.getGrades().size() == 1)
		{
			TeacherReview.addGrade(Float.parseFloat((String)aRating.getGrades().get(0)));
		}
		TeacherReview.addHotness(aRating.getHot());
		TeacherReview.addNewLikability(aRating.getLikability());
		TeacherReview.addNewToughness(aRating.getToughness());
		
	}
	
	public void setCourseName(String aName)
	{
		
		CourseName = aName;
	}
	
	public String getCourseName()
	{
		return CourseName;
	}
	
	public int getCourseNumber()
	{
		return this.CourseNumber;
	}
	
	
	public int getCRN()
	{
		return CRN;
	}
	
	public void setFinalDate(Date aDate)
	{
		FinalDate = aDate;
	}
	
	public void setSemester(Semester aSemester)
	{
		Semester = aSemester;
	}
	
	public void addMeetingTime(Date aDate)
	{
		MeetingTimes.add(aDate);
	}
	
	public void setCourseNumber(int aNumber)
	{
		CourseNumber = aNumber;
	}
	
	public void addStudentToWaitingList(Student s)
	{
		WaitingList.add(s);
	}
	
	public void setMaxCapacity(int aCapacity)
	{
		MaxCapacity = aCapacity;
	}
	
	public void setTeacherID(int aID)
	{
		TeacherID = aID;
		this.TeacherReview.setTeacherID(this.TeacherID);
		this.TeacherReview.setCourseName(this.CourseName);
		this.TeacherReview.setCourseNumber(this.CourseNumber);
	}
	
	public void addStudent(Student s)
	{
		RegisteredStudents.add(s);
	}
	
	public void setCRN(int aCRN)
	{
		CRN = aCRN;
	}
	
	public String toString()
	{
		String output = "";
		output += CRN + "\n" + TeacherID + "\n" + MaxCapacity
				+ "\n" + CourseNumber + "\n" + FinalDate.getTime()
				+ "\n" + ClassDuration + "\n" + this.CourseName + "\n";
		
		output += RegisteredStudents.size();
		for (int k = 0; k < RegisteredStudents.size(); k++)
		{
			Student s = (Student)RegisteredStudents.get(k);
			output += "\n" + s.getStudentID();
		}
		
		output += "\n" + WaitingList.size();
		for (int k = 0; k < WaitingList.size(); k++)
		{
			Student s = (Student)WaitingList.get(k);
			output += "\n" + s.getStudentID();
		}
		
		output += "\n" + MeetingTimes.size();
		for (int k = 0; k < MeetingTimes.size(); k++)
		{
			Date d = (Date)MeetingTimes.get(k);
			output += "\n" + d.getTime();
		}
		
		output += "\n" + FinalGrades.size();
		for (int k = 0; k < FinalGrades.size(); k++)
		{
			float aGrade = Float.parseFloat((String)FinalGrades.get(k));
			output += "\n" + aGrade;
		}
		
		output += "\n-+-\n" + Semester.toString();
		output += "\n-+-\n";
		
		output += "-+-\n" + TeacherReview.toString() + "\n-+-";
	
		
		
		return output;
	}
	
	public void expandArrayListConnections()
	{
		for (int x = 0; x < RegisteredStudents.size(); x++)
		{
			int studentID = Integer.parseInt((String)RegisteredStudents.get(x));
			for (int k = 0; k < Main.myDB.students.size(); k++)
			{
				Student s = (Student)Main.myDB.students.get(k);
				if (s.getStudentID() == studentID)
				{
					RegisteredStudents.remove(x);
					RegisteredStudents.add(x, s);
				}
			}
		}
	
	
		for (int x = 0; x < WaitingList.size(); x++)
		{
			int studentID = Integer.parseInt((String)WaitingList.get(x));
			for (int k = 0; k < Main.myDB.students.size(); k++)
			{
				Student s = (Student)Main.myDB.students.get(k);
				if (s.getStudentID() == studentID)
				{
					WaitingList.remove(x);
					WaitingList.add(x, s);
				}
			}
		}
	}
}

