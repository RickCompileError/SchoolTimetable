import java.util.ArrayList;
import java.util.Objects;

public class Curriculum implements Comparable<Curriculum> {
	ArrayList<Course> courses = new ArrayList<>();
	ArrayList<Course> fixedCourses = new ArrayList<>();
	int fitnessValue;
	
	Curriculum(){
		
	}
	
	Curriculum(ArrayList<Course> courses, ArrayList<Course> fixedCourses){
		this.courses = courses;
		this.fixedCourses = fixedCourses;
		calcFitness();
	}
	
	Curriculum(Curriculum that) {
		for (int i=0;i<that.courses.size();i++) {
			this.courses.add(new Course(that.courses.get(i)));
		}
		this.fixedCourses = that.fixedCourses;
		calcFitness();
	}
	
	void add(Course course) {
		courses.add(course);
		if (course.isFixed()) fixedCourses.add(course);
	}

	void modify(int id, int week, int starttime) {
		courses.get(id).setweek(week);
		courses.get(id).setstarttime(starttime);
		calcFitness();
	}

	void calcFitness() {
		fitnessValue = Fitness.getValue(this);
	}

	int getFitnessValue() {
		return fitnessValue;
	}

	Course getCourse(int id) {
		return new Course(courses.get(id));
	}

	boolean isConflict(Course course) {
		if (course.isFixed()) return false;
		for (Course c : fixedCourses) {
			if ((c.grade==course.grade) && (c.week==course.week) &&
				((c.start<=course.end && c.start>=course.start) ||
				 (c.end>=course.start && c.end<=course.end)))
				return true;
		}
		return false;
	}
	
	int size() {
		return courses.size();
	}

	@Override
	public int compareTo(Curriculum o) {
		if(this.getFitnessValue() > o.getFitnessValue())
			return -1;
		else if(this.getFitnessValue() < o.getFitnessValue())
			return 1;
		else {
			if (this.hashCode() > o.hashCode())
				return -1;
			else
				return 1;
		}
	}
	
	@Override
	public String toString() {
		String str = "-----\n";
		for (int i=0;i<courses.size();i++) {
			str += courses.get(i).toString() + "\n";
		}
		str += "-----";
		return str;
	}
	
	@Override
	public boolean equals(Object obj) {
		System.out.println("[Curriculum] equals execute");
		if (obj==this) {
			return true;
		}
		
		if (!(obj instanceof Curriculum)) {
			return false;
		}
		
		Curriculum c = (Curriculum) obj;
		
		for (int i=0;i<this.courses.size();i++) {
			if (!this.courses.get(i).equals(c.courses.get(i))) return false;
		}
		
		return true;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(courses,fitnessValue);
	}
}
