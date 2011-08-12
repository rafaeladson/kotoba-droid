package net.fiive.test.activities;

import net.fiive.domain.Word;
import net.fiive.domain.WordRepository;

class WordRepositoryStub extends WordRepository {
	
	private final Word[] wordsInRepository;
	
	private int getRandomWordCallCount = 0;

	public WordRepositoryStub() {
		super();
		wordsInRepository = new Word[2];
		wordsInRepository[0] = new Word( "Word", "Wort");
		wordsInRepository[1] = new Word( "Work", "Arbeit");
	}

	@Override
	public Word getRandomWord() {
		return wordsInRepository[getRandomWordCallCount++ % 2];
		
	}
	
	
	

}
