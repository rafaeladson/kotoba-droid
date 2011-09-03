package net.fiive.activities;

import net.fiive.R;
import net.fiive.domain.Word;
import net.fiive.domain.WordRepository;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class WordGameFragment extends Fragment {
	
	private static final String TRANSLATION_IS_SHOWN_KEY = "translationIsShown";
	private static final String CURRENT_WORD_KEY = "currentWord";
	private TextView originalView;
	private TextView translationView;
	private WordRepository wordRepository = new WordRepository();
	private Word currentWord;
	private boolean translationIsShown = false;
	private View wordGameView;

	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		

		if (savedInstanceState != null) {
			currentWord = (Word) savedInstanceState.getSerializable(CURRENT_WORD_KEY);
			originalView.setText(currentWord.getValue());
			if (savedInstanceState.getBoolean(TRANSLATION_IS_SHOWN_KEY)) {
				this.showTranslation();
			} else {
				emptyTranslation();
			}
		} else {
			showNextWord();
		}

	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		wordGameView = inflater.inflate(R.layout.word_game, container);
		
		originalView = (TextView) wordGameView.findViewById(R.id.valueLabel);
		translationView = (TextView) wordGameView.findViewById(R.id.translationLabel);
		Button nextWordButton = (Button) wordGameView.findViewById(R.id.nextWordButton);
		nextWordButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				showNextWord();
			}
		});
		Button showTranslationButton = (Button) wordGameView.findViewById(R.id.showTranslationButton);
		showTranslationButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				showTranslation();
			}

		});
		
		
		return wordGameView;
	}
	
	
	public void showNextWord() {
		currentWord = wordRepository.getRandomWord();
		originalView.setText(currentWord.getValue());
		emptyTranslation();
	}

	public void showTranslation() {
		assert (currentWord != null);
		translationView.setText(currentWord.getTranslation());
		translationIsShown = true;

	}

	public void setWordRepository(WordRepository wordRepository) {
		this.wordRepository = wordRepository;
	}

	public Word getCurrentWord() {
		return currentWord;
	}

	public void setCurrentWord(Word currentWord) {
		this.currentWord = currentWord;
	}
	
	private void emptyTranslation() {
		translationView.setText("");
		translationIsShown = false;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putSerializable(CURRENT_WORD_KEY, currentWord);
		outState.putBoolean(TRANSLATION_IS_SHOWN_KEY, translationIsShown);
	}

	
	
}
