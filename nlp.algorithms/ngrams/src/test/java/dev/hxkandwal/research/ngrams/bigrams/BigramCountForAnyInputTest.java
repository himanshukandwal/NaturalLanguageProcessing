package dev.hxkandwal.research.ngrams.bigrams;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

public class BigramCountForAnyInputTest {
	
	@Test
	public void testMain() {
		InputStream stdin = System.in;
		
		try {
			String data = "is and\r\n";	
			System.setIn(new ByteArrayInputStream(data.getBytes()));
			BigramCountForAnyInput.main(null);
	
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			  System.setIn(stdin);
		}
	}

}
