package net.fiive.kotoba.data.table;

import android.provider.BaseColumns;

public class QuestionTable {

	public static final String TABLE_NAME = "questions";

	public static class Columns implements BaseColumns {
		public static final String VALUE = "value";
		public static final String ANSWER = "answer";
	}
}
