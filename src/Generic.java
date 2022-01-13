import java.util.ArrayList;

public class Generic {
	Popularity popularity;
	Popularity generation;
	int popularitySize;
	int bound;
	Curriculum c1;
	Curriculum c2;
	Curriculum offSpring;

	Generic(int popularitySize) {
		this.popularitySize = popularitySize;
		bound = (int)(popularitySize*(1/2.0));
	}
	
	Popularity nextGeneration(Popularity popularity) {
		generation = new Popularity();
		this.popularity = popularity;
		for (int i=0;i<popularitySize*(1/10.0);i++)
			generation.add(popularity.getCurriculum(i));
		while (generation.size()<popularitySize) {
			select();
			crossover1();
		}
		return generation;
	}

	void select() {
		int val1, val2;
		do {
			val1 = MyRandom.rangeInt(bound);
			val2 = MyRandom.rangeInt(bound);
			// System.out.println("[Debug] val1 = " + val1 + " / val2 = " + val2);
		}while(val1 == val2);
		c1 = popularity.getCurriculum(val1);
		c2 = popularity.getCurriculum(val2);
	}

	void crossover(){
		int id = MyRandom.rangeInt(c1.size());
		ArrayList<Course> tmp = new ArrayList<>();
		tmp.addAll(c1.courses.subList(0, id));
		tmp.addAll(c2.courses.subList(id, c2.size()));
		offSpring = new Curriculum(tmp,c1.fixedCourses);
		mutation();
		generation.add(offSpring);
		tmp.clear();
		tmp.addAll(c2.courses.subList(0, id));
		tmp.addAll(c1.courses.subList(id, c1.size()));
		offSpring = new Curriculum(tmp,c1.fixedCourses);
		mutation();
		generation.add(offSpring);
	}
	
	void crossover1() {
		int start = MyRandom.rangeInt(c1.size());
		int end = MyRandom.rangeInt(c1.size());
		if (start>end) {
			int tmp = start;
			start = end;
			end = tmp;
		}
		ArrayList<Course> tmp = new ArrayList<>();
		tmp.addAll(c1.courses.subList(0,start));
		tmp.addAll(c2.courses.subList(start, end));
		tmp.addAll(c1.courses.subList(end, c1.size()));
		offSpring = new Curriculum(tmp,c1.fixedCourses);
		mutation();
		generation.add(offSpring);
		tmp.clear();
		tmp.addAll(c2.courses.subList(0,start));
		tmp.addAll(c1.courses.subList(start, end));
		tmp.addAll(c2.courses.subList(end, c2.size()));
		offSpring = new Curriculum(tmp,c1.fixedCourses);
		mutation();
		generation.add(offSpring);
	}

    void mutation() {
        if(MyRandom.rangeInt(2) == 0) {
            for(int id = 0; id < offSpring.size(); id++) {
                if(MyRandom.rangeInt(5) == 0 && !offSpring.getCourse(id).isFixed()) {
                	do {
                		int week = MyRandom.rangeInt(1,5);
                		int starttime = MyRandom.rangeInt(1,8,5,offSpring.getCourse(id).hour);
                		offSpring.modify(id, week, starttime);
                	} while (offSpring.isConflict(offSpring.getCourse(id)));
                }
            }
        }
    }
}
