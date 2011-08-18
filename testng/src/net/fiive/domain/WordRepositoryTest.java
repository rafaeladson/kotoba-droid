package net.fiive.domain;

import junit.framework.Assert;

import org.testng.annotations.Test;

public class WordRepositoryTest {
  
	
	@Test
	public void testGetRandomWord() {
		WordRepository wordRepository = new WordRepository();
		Assert.assertNotNull(wordRepository.getRandomWord());
	}
	
	
}
