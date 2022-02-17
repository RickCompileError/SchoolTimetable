public class CourseFactory {
	Course getCourse(String info){
		String[] s = info.split(" ");
		if (Integer.parseInt(s[0]) == 1) {
			return new Course(true,
					s[1],
					(Integer.parseInt(s[2])==1?true:false),
					Integer.parseInt(s[3]),
					(s.length==7?"":s[7]), // length = 7 means info doesn't contain teacher name
					Integer.parseInt(s[4]),
					Integer.parseInt(s[6])-Integer.parseInt(s[5])+1,
					Integer.parseInt(s[5]),
					Integer.parseInt(s[6]));
		} else {
			int week, start;
			do {
				week = MyRandom.rangeInt(1, 5);
				start = MyRandom.rangeInt(1, 8, 5, Integer.parseInt(s[4])-1);
			} while(week == 2 && start == 8);
			return new Course(false,
					s[1],
					(Integer.parseInt(s[2])==1?true:false),
					Integer.parseInt(s[3]),
					s[5],
					week,
					Integer.parseInt(s[4]),
					start,
					start + Integer.parseInt(s[4]) - 1);
		}
	}
}
