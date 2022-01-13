import java.util.Random;

public class CourseFactory {
	Course getCourse(String info){
		String[] s = info.split(" ");
		if (Integer.parseInt(s[0]) == 1) {
			return new Course(true,
					(Integer.parseInt(s[2])==1?true:false),
					Integer.parseInt(s[3]),
					"",
					Integer.parseInt(s[4]),
					Integer.parseInt(s[5]),
					Integer.parseInt(s[6]));
		} else {
			Random rd = new Random();
			int week, start;
			do {
				week = rd.nextInt(5) + 1;
				start = rd.nextInt(8) + 1;
			} while(week == 2 && start == 8);
			return new Course(false,
					(Integer.parseInt(s[2])==1?true:false),
					Integer.parseInt(s[3]),
					s[5],
					week,
					start,
					start + Integer.parseInt(s[4]));
		}
	}
}
