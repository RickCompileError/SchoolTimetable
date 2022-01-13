import java.util.Random;

public class MyRandom {
	
	static Random rd = new Random();
	
	static int rangeInt(int end) {
		return rd.nextInt(end);
	}
	
	static int rangeInt(int start, int end) {
		return rd.nextInt(end-start+1) + start;
	}
	
	static int rangeInt(int start, int end, int prohibit, int offset) {
		while (true) {
			int result = rd.nextInt(end-start+1) + start;
			if (result<=prohibit && prohibit<=result+offset) continue;
			return result;
		}
	}
}
