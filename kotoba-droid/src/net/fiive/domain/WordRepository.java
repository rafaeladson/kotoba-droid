package net.fiive.domain;

public class WordRepository {
	
	private Word previousSelectedWord = null;
	
	
	public Word getRandomWord() {
		Word[] allWords = getWordsFromDb();
		Word selectedWord;
		do {
			int selectedWordIndex = (int)(Math.random() * allWords.length);
			selectedWord = allWords[selectedWordIndex];
		} while ( selectedWord.equals( previousSelectedWord ));
		previousSelectedWord = selectedWord;
		
		return selectedWord;
	}
	
	private Word[] getWordsFromDb() {
		Word[] words = new Word[2];
		words[0] = new Word( "ことば", "word");
		words[1] = new Word( "お母さん", "(sua) mãe");
		return words;
	}

}
