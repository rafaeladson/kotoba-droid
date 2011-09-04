package net.fiive.kotoba.domain;

import java.util.Comparator;
import java.util.PriorityQueue;

public class WordRepository {
	
	private Word[] words;
	private PriorityQueue<Word> randomizedWordQueue;
	private Comparator<Word> wordQueueComparator = new WordRandomComparator();
	
	
	
	public WordRepository() {
		this( new Word[] { new Word( "ことば", "word"), new Word( "お母さん", "(sua) mãe")});
	}

	public WordRepository(Word[] words) {
		this.words = words;
	}

	public Word getRandomWord() {
		
		if ( words.length < 1 ) {
			return null;
		}
		if ( randomizedWordQueue == null || randomizedWordQueue.isEmpty()) {
			rebuildWordQueue();
		}
		
		return randomizedWordQueue.remove();
	}
	
	void setWordQueueComparator(Comparator<Word> wordQueueComparator) {
		this.wordQueueComparator = wordQueueComparator;
	}

	private void rebuildWordQueue() {
		randomizedWordQueue = new PriorityQueue<Word>(words.length, wordQueueComparator);
		for ( Word word : words ) {
			randomizedWordQueue.add(word);
		}
	}


	private class WordRandomComparator implements Comparator<Word> {

		public int compare(Word word1, Word word2) {
			int isGreaterSelector = ((int)(Math.random() * 2)) % 2;
			return (isGreaterSelector == 0 ) ? -1 : 1;
		}
		
	}
	
	
	
	
}
