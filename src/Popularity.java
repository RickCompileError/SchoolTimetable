import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

public class Popularity {
	SortedSet<Curriculum> set = new TreeSet<Curriculum>();

	void add(Curriculum curriculum) {
//		System.out.println("[Popularity] Curriculum fitness value: " + curriculum.getFitnessValue());
		set.add(curriculum);
//		System.out.println("[Popularity] After add, size: " + set.size());
	}

	Curriculum getBest() {
		return set.first();
	}

	Curriculum getCurriculum(int n) {
		// SortedSet not provide [n] or at(n), use iterator
		Iterator<Curriculum> i = set.iterator();
		Curriculum c = new Curriculum();
		while( n-- >= 0)
			c = i.next();
		return c;
	}

	int size() {
		return set.size();
	}
	
	void printFitness() {
		Iterator<Curriculum> i = set.iterator();
		System.out.println("[Popularity] Print fitness value");
		while (i.hasNext()) {
			System.out.println("\tFitness value " + i.next().getFitnessValue());
		}
		System.out.println("[Popularity] Print fiteness value done");
	}
}
