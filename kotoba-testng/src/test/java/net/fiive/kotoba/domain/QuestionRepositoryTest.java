package net.fiive.kotoba.domain;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Comparator;

public class QuestionRepositoryTest {
  
	
	@Test
	public void testGetRandomWord() {
		QuestionRepository questionRepository = new QuestionRepository();
		Assert.assertNotNull(questionRepository.getRandomWord());
	}
	
	@Test
	public void shouldGetRandomWordWhenRepositoryHasTwoOrMoreWords() {
		Question firstQuestion = new Question("Wort", "Question" );
		Question secondQuestion = new Question("Hund", "Dog");
		QuestionRepository repository = new QuestionRepository(new Question[] {firstQuestion, secondQuestion});
		Assert.assertNotNull(repository.getRandomWord());
	}
	
	@Test
	public void shouldGetNullRandomWordWhenRepositoryHasNoWords() {
		QuestionRepository repository = new QuestionRepository( new Question[] {} );
		Assert.assertNull( repository.getRandomWord() );
		
	}
	
	@Test
	public void shouldGetWordWhenRepositoryHasOnlyOneWord() {
		Question onlyQuestion = new Question( "Kotoba", "Question");
		QuestionRepository repository = new QuestionRepository( new Question[] {onlyQuestion} );
		Assert.assertEquals( repository.getRandomWord(), onlyQuestion);
		Assert.assertEquals( repository.getRandomWord(), onlyQuestion);
		
	}
	
	@Test
	public void shouldNotGetIntoAInfiniteLoopWhenRepositoryHasRepeatedWords() {
		Question firstQuestion = new Question("foo", "bar");
		Question sameQuestion = new Question("foo", "bar");
		QuestionRepository repository = new QuestionRepository( new Question[] {firstQuestion, sameQuestion} );
		Assert.assertEquals( repository.getRandomWord(), firstQuestion);
		Assert.assertEquals( repository.getRandomWord(), firstQuestion);
		
	}
	
	@Test
	public void shouldRebuildQueueWhenItRunsEmpty() {
		Question firstQuestion = new Question( "foo", "bar");
		Question secondQuestion = new Question( "bar", "foo");
		
		QuestionRepository repository = new QuestionRepository( new Question[] {firstQuestion, secondQuestion});
		repository.setWordQueueComparator(new Comparator<Question>() {
			@Override
			public int compare(Question arg0, Question arg1) {
				return 1;
			}
		});
		
		Assert.assertEquals( repository.getRandomWord(), firstQuestion);
		Assert.assertEquals( repository.getRandomWord(), secondQuestion);
		Assert.assertEquals( repository.getRandomWord(), firstQuestion);
	}
	
	
	
	
}
