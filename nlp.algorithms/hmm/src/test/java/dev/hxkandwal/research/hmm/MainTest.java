package dev.hxkandwal.research.hmm;

import org.junit.Test;

public class MainTest {

	@Test
	public void testMainForViterbi() {
		Main.main(new String[] { "viterbi" });
	}

	@Test
	public void testMainForPOS() {
		Main.main(new String[] { "POS" });
	}
	
}
