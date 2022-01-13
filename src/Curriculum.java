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
		int[][] hour = new int[6][6];
		for (int i=0;i<courses.size();i++) {
			c1 = courses.get(i);
			hour[c1.grade][c1.week] += c1.end - c1.start + 1; 
			// check 黃 宜 徐 have class in Tuesday
			if ((c1.teacher.equals("黃") || c1.teacher.equals("宜") || c1.teacher.equals("徐"))
					&& c1.week==2) fitnessValue -= 100;
			
			// check if 賴 course is in afternoon
			if (c1.teacher.equals("賴") && c1.start>=5) fitnessValue += 10;
			
			// check class start at 1 or 5 lesson
			if (c1.start==1 || (c1.start<=5 && 5<=c1.end)) fitnessValue -= 1;
			
			for (int j=i+1;j<courses.size();j++) {
				c2 = courses.get(j);
				// check if c1 course and c2 course overlapping
				if (c1.week==c2.week && (c1.end>=c2.start || c2.end>=c1.start)) {
					// check if c1 or c2 is fixed course
					if ((c1.grade==c2.grade) && (c1.fixed || c2.fixed)) fitnessValue -= 100;
					// check if c1 and c2 are the same teacher
					if (c1.teacher.equals(c2.teacher)) fitnessValue -= 1;
				}
			}
		}
		// check if students have class for more than six hours a day
		for (int i=0;i<hour.length;i++) {
			for (int j=0;j<hour[i].length;j++) {
				if (hour[i][j]>6) fitnessValue -= 10;
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
