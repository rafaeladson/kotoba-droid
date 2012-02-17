package net.fiive.kotoba.test.activities;

import net.fiive.kotoba.domain.Question;
import net.fiive.kotoba.domain.QuestionRepository;

class QuestionRepositoryStub extends QuestionRepository {
	
	private final Question[] wordsInRepository;
	
	private int getRandomWordCallCount = 0;

	public QuestionRepositoryStub() {
		super();
		wordsInRepository = new Question[2];
		wordsInRepository[0] = new Question( "Question", "Wort");
		wordsInRepository[1] = new Question( "Work", "Arbeit");
	}

	@Override
	public Question getRandomWord() {
		return wordsInRepository[getRandomWordCallCount++ % 2];
		
	}
	
	
	

}
