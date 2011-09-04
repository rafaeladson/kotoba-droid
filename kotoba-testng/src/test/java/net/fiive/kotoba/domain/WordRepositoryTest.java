package net.fiive.kotoba.domain;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Comparator;

public class WordRepositoryTest {
  
	
	@Test
	public void testGetRandomWord() {
		WordRepository wordRepository = new WordRepository();
		Assert.assertNotNull(wordRepository.getRandomWord());
	}
	
	@Test
	public void shouldGetRandomWordWhenRepositoryHasTwoOrMoreWords() {
		Word firstWord = new Word("Wort", "Word" );
		Word secondWord = new Word("Hund", "Dog");
		WordRepository repository = new WordRepository(new Word[] {firstWord, secondWord});
		Assert.assertNotNull(repository.getRandomWord());
	}
	
	@Test
	public void shouldGetNullRandomWordWhenRepositoryHasNoWords() {
		WordRepository repository = new WordRepository( new Word[] {} );
		Assert.assertNull( repository.getRandomWord() );
		
	}
	
	@Test
	public void shouldGetWordWhenRepositoryHasOnlyOneWord() {
		Word onlyWord = new Word( "Kotoba", "Word");
		WordRepository repository = new WordRepository( new Word[] {onlyWord} );
		Assert.assertEquals( repository.getRandomWord(), onlyWord);
		Assert.assertEquals( repository.getRandomWord(), onlyWord);
		
	}
	
	@Test
	public void shouldNotGetIntoAInfiniteLoopWhenRepositoryHasRepeatedWords() {
		Word firstWord = new Word("foo", "bar");
		Word sameWord = new Word("foo", "bar");
		WordRepository repository = new WordRepository( new Word[] { firstWord, sameWord } );
		Assert.assertEquals( repository.getRandomWord(), firstWord );
		Assert.assertEquals( repository.getRandomWord(), firstWord );
		
	}
	
	@Test
	public void shouldRebuildQueueWhenItRunsEmpty() {
		Word firstWord = new Word( "foo", "bar");
		Word secondWord = new Word( "bar", "foo");
		
		WordRepository repository = new WordRepository( new Word[] {firstWord, secondWord});
		repository.setWordQueueComparator(new Comparator<Word>() {
			@Override
			public int compare(Word arg0, Word arg1) {
				return 1;
			}
		});
		
		Assert.assertEquals( repository.getRandomWord(), firstWord);
		Assert.assertEquals( repository.getRandomWord(), secondWord);
		Assert.assertEquals( repository.getRandomWord(), firstWord);
	}
	
	
	
	
}
