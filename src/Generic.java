import java.util.ArrayList;
import java.util.Random;

public class Generic {
	Random rd = new Random();
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
			val1 = rd.nextInt(bound);
			val2 = rd.nextInt(bound);
			System.out.println("[Debug] val1 = " + val1 + " / val2 = " + val2);
		}while(val1 == val2);
		c1 = popularity.getCurriculum(val1);
		c2 = popularity.getCurriculum(val2);
	}

	void crossover(){
		int id = rd.nextInt(c1.size());
		ArrayList<Course> tmp = new ArrayList<>();
		tmp.addAll(c1.courses.subList(0, id));
		tmp.addAll(c2.courses.subList(id, c2.size()));
		offSpring = new Curriculum(tmp);
		mutation();
		generation.add(offSpring);
		tmp.clear();
		tmp.addAll(c2.courses.subList(0, id));
		tmp.addAll(c1.courses.subList(id, c1.size()));
		offSpring = new Curriculum(tmp);
		mutation();
		generation.add(offSpring);
	}
	
	void crossover1() {
		int start = rd.nextInt(c1.size());
		int end = rd.nextInt(c1.size());
		if (start>end) {
			int tmp = start;
			start = end;
			end = tmp;
		}
		ArrayList<Course> tmp = new ArrayList<>();
		tmp.addAll(c1.courses.subList(0,start));
		tmp.addAll(c2.courses.subList(start, end));
		tmp.addAll(c1.courses.subList(end, c1.size()));
		offSpring = new Curriculum(tmp);
		mutation();
		generation.add(offSpring);
		tmp.clear();
		tmp.addAll(c2.courses.subList(0,start));
		tmp.addAll(c1.courses.subList(start, end));
		tmp.addAll(c2.courses.subList(end, c2.size()));
		offSpring = new Curriculum(tmp);
		mutation();
		generation.add(offSpring);
	}

	void mutation() {
		if(rd.nextInt(10) == 0) {
			int id = rd.nextInt(c1.size()), week = rd.nextInt(5) + 1, starttime = rd.nextInt(8) + 1;
			offSpring.modify(id, week, starttime);
		}
	}
}
