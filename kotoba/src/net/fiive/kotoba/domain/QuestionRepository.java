package net.fiive.kotoba.domain;

import java.util.Comparator;
import java.util.PriorityQueue;

public class QuestionRepository {
	
	private Question[] questions;
	private PriorityQueue<Question> randomizedQuestionQueue;
	private Comparator<Question> wordQueueComparator = new WordRandomComparator();
	
	
	
	public QuestionRepository() {
		this( new Question[] { new Question( "空はあおい", "O céu é azul"), new Question( "あの女の人はとても美しいですね。", "Aquela mulher é muito bonita"), new Question( "私はべんごしじゃないです。私はインギニアです。", "I'm not a lawyer. I'm an engineer" )});
	}

	public QuestionRepository(Question[] questions) {
		this.questions = questions;
	}

	public Question getRandomWord() {
		
		if ( questions.length < 1 ) {
			return null;
		}
		if ( randomizedQuestionQueue == null || randomizedQuestionQueue.isEmpty()) {
			rebuildWordQueue();
		}
		
		return randomizedQuestionQueue.remove();
	}
	
	void setWordQueueComparator(Comparator<Question> wordQueueComparator) {
		this.wordQueueComparator = wordQueueComparator;
	}

	private void rebuildWordQueue() {
		randomizedQuestionQueue = new PriorityQueue<Question>(questions.length, wordQueueComparator);
		for ( Question question : questions) {
			randomizedQuestionQueue.add(question);
		}
	}


	private class WordRandomComparator implements Comparator<Question> {

		public int compare(Question question1, Question question2) {
			int isGreaterSelector = ((int)(Math.random() * 2)) % 2;
			return (isGreaterSelector == 0 ) ? -1 : 1;
		}
		
	}
	
	
	
	
}
