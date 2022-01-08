import java.util.Objects;

public class Course {
	boolean fixed;
	String teacher;
	int grade, week, start, end;

	Course(boolean fixed, int grade, String teacher, int week, int start, int end) {
		this.fixed = fixed;
		this.grade = grade;
		this.teacher = teacher;
		this.week = week;
		this.start = start;
		this.end = end;
	}

	boolean isFixed() {
		return fixed;
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
		return "Fixed: " + fixed + " / grade: " + grade + " / teacher: " + teacher + " / week: " + week + " / start: " + start + " / end: " + end;  
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
		
		return (c.fixed==this.fixed) && (c.teacher.equals(this.teacher)) && (c.grade==this.grade) 
				&& (c.week==this.week) && (c.start==this.start) && (c.end==this.end);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(fixed,teacher,grade,week,start,end);
	}
}
