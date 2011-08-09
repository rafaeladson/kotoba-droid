package net.fiive.domain;

public class WordRepository {
	
	private Word currentWord = null;
	
	
	public Word getRandomWord() {
		Word[] allWords = getWordsFromDb();
		Word selectedWord;
		do {
			int selectedWordIndex = (int)(Math.random() * allWords.length);
			selectedWord = allWords[selectedWordIndex];
		} while ( selectedWord.equals( currentWord ));
		currentWord = selectedWord;
		
		return currentWord;
	}


	public Word getCurrentWord() {
		return currentWord;
	}
	
	private Word[] getWordsFromDb() {
		Word[] words = new Word[2];
		words[0] = new Word( "ことば", "word");
		words[1] = new Word( "お母さん", "(sua) mãe");
		return words;
	}

}
