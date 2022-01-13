import java.util.Objects;

public class Course {
	boolean fixed, major;
	String teacher;
	int grade, week, start, end, hour;

	Course(boolean fixed, boolean major, int grade, String teacher, int week, int hour, int start, int end) {
		this.fixed = fixed;
		this.major = major;
		this.grade = grade;
		this.teacher = teacher;
		this.week = week;
		this.hour = hour;
		this.start = start;
		this.end = end;
	}

	boolean isFixed() {
		return fixed;
	}

	boolean isMajor() {
		return major;
	}
	
	void setweek(int n) {
		this.week = n;
	}

	void setstarttime(int starttime) {
		int diff = this.end - this.start;
		this.start = starttime;
		this.end = start + diff;
	}

	@Override
	public String toString() {
		return "Fixed: " + fixed + " / major: " + major + 
				" / grade: " + grade + " / teacher: " + teacher + 
				" / week: " + week + " / hour: " + hour + 
				" / start: " + start + " / end: " + end;  
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj==this) {
			return true;
		}
		
		if (obj.getClass()!=this.getClass()) {
			return false;
		}
		
		Course c = (Course) obj;
		
		return (c.fixed==this.fixed) && (c.major==this.major) && (c.teacher.equals(this.teacher)) 
				&& (c.grade==this.grade) && (c.week==this.week) && (c.hour==this.hour) 
				&& (c.start==this.start) && (c.end==this.end);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(fixed,major,teacher,grade,week,hour,start,end);
	}
}
