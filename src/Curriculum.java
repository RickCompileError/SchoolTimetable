import java.util.ArrayList;
import java.util.Objects;

public class Curriculum implements Comparable<Curriculum> {
	ArrayList<Course> courses = new ArrayList<>();
	int fitnessValue;
	
	Curriculum(){
		
	}
	
	Curriculum(ArrayList<Course> courses){
		this.courses = courses;
		calcFitness();
	}
	
	void add(Course course) {
		courses.add(course);
	}

	void modify(int id, int week, int starttime) {
		courses.get(id).setweek(week);
		courses.get(id).setstarttime(starttime);
		calcFitness();
	}

	void calcFitness() {
		fitnessValue = 100;
		Course c1, c2;
		for (int i=0;i<courses.size();i++) {
			c1 = courses.get(i);
			if ((c1.teacher.equals("¶À") || c1.teacher.equals("©y") || c1.teacher.equals("®}"))
					&& c1.week==2) fitnessValue = 0;
			if (c1.start==1 || (c1.start<=5 && 5<=c1.end)) fitnessValue -= 1;
			for (int j=i+1;j<courses.size();j++) {
				c2 = courses.get(j);
				if (c1.week==c2.week && (c1.end>=c2.start || c2.end>=c1.start)) {
					if ((c1.grade==c2.grade) && (c1.fixed || c2.fixed)) fitnessValue = 0;
					if (c1.teacher.equals(c2.teacher)) fitnessValue -= 1;
				}
			}
		}
	}

	int getFitnessValue() {
		return fitnessValue;
	}

	Course getCourse(int id) {
		return courses.get(id);
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
