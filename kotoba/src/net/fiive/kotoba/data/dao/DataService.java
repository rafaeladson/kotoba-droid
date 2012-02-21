package net.fiive.kotoba.data.dao;


import java.util.List;

import net.fiive.intern.android.data.OpenHelper;
import net.fiive.kotoba.data.exceptions.DataOperationFailedException;
import net.fiive.kotoba.data.migration.M00_001_CreateQuestion;
import net.fiive.kotoba.domain.Question;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DataService {

	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "kotoba";

	private Context context;
	private SQLiteDatabase database;
	private QuestionDAO questionDAO;

	public DataService(Context context) {
		this.context = context;
		SQLiteOpenHelper openHelper = new OpenHelper(this.context, DATABASE_NAME, DATABASE_VERSION, new M00_001_CreateQuestion());
		this.database = openHelper.getWritableDatabase();
		this.questionDAO = new QuestionDAO(this.database);
	}

	public Cursor cursorForFindAll() {
		try {
			return questionDAO.cursorForFindAll();
		} catch( SQLException sqlException ) {
			throw new DataOperationFailedException(sqlException.getMessage());
		}
	}

	public List<Long> findAllQuestionIds() {
		try {
			return questionDAO.findAllIds();
		} catch( SQLException sqlException) {
			throw new DataOperationFailedException(sqlException.getMessage());
		}
	}


	public Question findQuestionById(Long id) {
		try {
			return questionDAO.findById(id);
		} catch( SQLException exception ) {
			throw new DataOperationFailedException(exception.getMessage());
		}
	}

	public Question saveOrUpdateQuestion(Question question) {
		database.beginTransaction();
		try {
			question = questionDAO.saveOrUpdate(question);
			database.setTransactionSuccessful();
		} catch( SQLException sqlException ) {
			throw new DataOperationFailedException(sqlException.getMessage());
		} finally {
			database.endTransaction();
		}

		return question;
	}

	public void removeQuestion(Question question) {
		database.beginTransaction();
		try {
			questionDAO.delete(question);
			database.setTransactionSuccessful();
		} catch( SQLException sqlException ) {
			throw new DataOperationFailedException(sqlException.getMessage());
		}
		finally {
			database.endTransaction();
		}
	}

	public Question buildQuestionFromCursor(Cursor cursor) {
		Question question = questionDAO.buildFromCursor(cursor);
		if ( question == null ) {
			throw new DataOperationFailedException("Error building question from cursor");
		}
		return question;
	}



}
