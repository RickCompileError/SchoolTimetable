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
		for (int i=0;i<popularitySize*(1/10.0);i++) {
			generation.add(popularity.getCurriculum(i));
		}
		while (generation.size()<popularitySize) {
			select();
			crossover();
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
	
	void crossover() {
		int start = MyRandom.rangeInt(c1.size());
		int end = MyRandom.rangeInt(c1.size());
		if (start>end) {
			int tmp = start;
			start = end;
			end = tmp;
		}
		ArrayList<Course> tmp = new ArrayList<>();
		for (int i=0;i<start;i++)
			tmp.add(c1.getCourse(i));
		for (int i=start;i<end;i++)
			tmp.add(c2.getCourse(i));
		for (int i=end;i<c1.size();i++)
			tmp.add(c1.getCourse(i));
		offSpring = new Curriculum(tmp,c1.fixedCourses);
		mutation();
		offSpring.calcFitness();
		generation.add(offSpring);
		tmp = new ArrayList<>();
		for (int i=0;i<start;i++)
			tmp.add(c2.getCourse(i));
		for (int i=start;i<end;i++)
			tmp.add(c1.getCourse(i));
		for (int i=end;i<c1.size();i++)
			tmp.add(c2.getCourse(i));
		offSpring = new Curriculum(tmp,c1.fixedCourses);
		mutation();
		offSpring.calcFitness();
		generation.add(offSpring);
	}

    void mutation() {
    	// 50% mutate
        if(MyRandom.rangeInt(2) == 0) {
            for(int id = 0; id < offSpring.size(); id++) {
            	// 20% element mutate, and element must not fixed class
                if(MyRandom.rangeInt(5) == 0 && !offSpring.getCourse(id).isFixed()) {
                	do {
                		int week = MyRandom.rangeInt(1,5);
                		int starttime = MyRandom.rangeInt(1,8,new int[]{5,10},offSpring.getCourse(id).hour);
                		offSpring.modify(id, week, starttime);
                	} while (offSpring.isConflict(offSpring.getCourse(id))); // check if new class time is conflict
                }
            }
        }
    }
}
