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
	
	private TextView originalView;
	private TextView translationView;
	private WordRepository wordRepository = new WordRepository();
	
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
        showNextWord();
    }
    
    public void showNextWord() {
    	Word currentWord = wordRepository.getRandomWord();
    	originalView.setText(currentWord.getValue());
    	translationView.setText("");
    }
    
    public void showTranslation() {
    	Word currentWord = wordRepository.getCurrentWord();
    	assert( currentWord != null );
    	translationView.setText(currentWord.getTranslation());
    }
}