package net.fiive.kotoba.data.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.provider.BaseColumns;
import com.google.common.base.Preconditions;
import net.fiive.intern.random.RandomItemRepository;
import net.fiive.kotoba.data.table.QuestionTable;
import net.fiive.kotoba.domain.Question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QuestionDAO {

	private final List<Question> questions;
	private SQLiteDatabase database;


	public QuestionDAO() {
		questions = Arrays.asList(new Question( "空はあおい", "O céu é azul"), new Question( "あの女の人はとても美しいですね。", "Aquela mulher é muito bonita"),
						 new Question( "私はべんごしじゃないです。私はインギニアです。", "I'm not a lawyer. I'm an engineer" ));
	}

	public QuestionDAO(SQLiteDatabase database) {
		this();
		Preconditions.checkNotNull(database);
		this.database = database;
	}

	public Question findById(Long id) {
		Preconditions.checkNotNull(id, "Cannot search for question with id null");
		Question question = null;
		Cursor cursor = database.query(QuestionTable.TABLE_NAME, new String[]{QuestionTable.Columns._ID, QuestionTable.Columns.VALUE, QuestionTable.Columns.ANSWER},
						      String.format("%s = ?", QuestionTable.Columns._ID), new String[]{String.valueOf(id)}, null, null, null, "1");
		if ( cursor.moveToFirst() ) {
			question = buildFromCursor(cursor);
		}

		if( !cursor.isClosed()) {
			cursor.close();
		}

		return question;

	}

	public List<Question> findAll() {
		return Collections.unmodifiableList(questions);
	}

	public Cursor cursorForFindAll() {
		return database.query(QuestionTable.TABLE_NAME, new String[] {QuestionTable.Columns._ID, QuestionTable.Columns.VALUE, QuestionTable.Columns.ANSWER},
					     null, null, null, null, QuestionTable.Columns._ID, null);
	}

	public List<Long> findAllIds() {
		List<Long> allIds = new ArrayList<Long>();
		Cursor cursor = database.query(QuestionTable.TABLE_NAME, new String[] { QuestionTable.Columns._ID}, null, null, null, null, QuestionTable.Columns._ID, null);
		if ( cursor.moveToFirst()) {
			do {
				allIds.add(cursor.getLong(0));
			} while( cursor.moveToNext());
		}

		if ( !cursor.isClosed()) {
			cursor.close();
		}

		return allIds;
	}



	public Question saveOrUpdate(Question question) {
		Preconditions.checkNotNull(question);
		if ( question.getId() == null ) {
			return this.save(question);
		} else {
			return this.update(question);
		}
	}

	public void delete(Question question) {
		Preconditions.checkNotNull(question);
		if ( question.getId() != null && question.getId() > 0 ) {
			database.delete(QuestionTable.TABLE_NAME, String.format("%s = ?", QuestionTable.Columns._ID), new String[] {String.valueOf(question.getId())});
			question.setId(null);
		}
	}

	public Question buildFromCursor(Cursor cursor) {

		if ( cursor != null ) {
			return new Question(cursor.getLong(0), cursor.getString(1), cursor.getString(2));
		}
		return null;
	}


	private Question update(Question question) {
		final ContentValues values = new ContentValues();
		values.put(QuestionTable.Columns.ANSWER, question.getAnswer());
		values.put(QuestionTable.Columns.VALUE, question.getValue());
		database.update(QuestionTable.TABLE_NAME, values, String.format("%s = ?", BaseColumns._ID), new String[]{String.valueOf(question.getId())});
		return question;
	}

	private Question save(Question question) {
		String query = String.format("insert into %s (%s, %s) values (?, ?)", QuestionTable.TABLE_NAME, QuestionTable.Columns.VALUE, QuestionTable.Columns.ANSWER);
		SQLiteStatement statement = database.compileStatement(query);
		statement.bindString(1, question.getValue());
		statement.bindString(2, question.getAnswer());
		Long id = statement.executeInsert();
		question.setId(id);
		return question;
	}


}
