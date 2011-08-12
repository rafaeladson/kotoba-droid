package net.fiive.activities;

import net.fiive.R;
import net.fiive.domain.Word;
import net.fiive.domain.WordRepository;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WordGameActivity extends Activity {
	
	private static final String TRANSLATION_IS_SHOWN_KEY = "translationIsShown";
	private static final String CURRENT_WORD_KEY = "currentWord";
	private TextView originalView;
	private TextView translationView;
	private WordRepository wordRepository = new WordRepository();
	private Word currentWord;
	private boolean translationIsShown = false;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        originalView = (TextView) findViewById(R.id.valueLabel);
        translationView = (TextView) findViewById(R.id.translationLabel);
        Button nextWordbutton = (Button) findViewById(R.id.nextWordButton);
        nextWordbutton.setOnClickListener(new Button.OnClickListener( ) {
			public void onClick(View v) {
				showNextWord();
			}
        });
        Button showTranslationButton = (Button) findViewById(R.id.showTranslationButton);
        showTranslationButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				showTranslation();
			}
        	
        });
        
        if ( savedInstanceState != null) {
        	currentWord = (Word) savedInstanceState.getSerializable(CURRENT_WORD_KEY);
        	originalView.setText(currentWord.getValue() );
        	if ( savedInstanceState.getBoolean(TRANSLATION_IS_SHOWN_KEY)) {
        		this.showTranslation();
        	}
        	else {
        		emptyTranslation();
        	}
        } else {
        	showNextWord();        	
        }
        
        
    }

	private void emptyTranslation() {
		translationView.setText("");
		translationIsShown = false;
	}
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    	
    	outState.putSerializable(CURRENT_WORD_KEY, currentWord);
    	outState.putBoolean(TRANSLATION_IS_SHOWN_KEY, translationIsShown);
    }



	public void showNextWord() {
    	currentWord = wordRepository.getRandomWord();
    	originalView.setText(currentWord.getValue());
    	emptyTranslation();
    }
    
    public void showTranslation() {
    	assert( currentWord != null );
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
	
	
    
    
}