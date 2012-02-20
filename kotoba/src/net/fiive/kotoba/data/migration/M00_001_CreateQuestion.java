package net.fiive.kotoba.data.migration;

import android.database.sqlite.SQLiteDatabase;
import net.fiive.intern.android.data.DatabaseMigration;
import net.fiive.kotoba.data.table.QuestionTable;

public class M00_001_CreateQuestion implements DatabaseMigration {


	@Override
	public int getVersion() {
		return 0;
	}

	@Override
	public void execute(SQLiteDatabase db) {
		StringBuilder sb = new StringBuilder();
		String createSql = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT NOT NULL, %s TEXT NOT NULL);", QuestionTable.TABLE_NAME, QuestionTable.Columns._ID,
							QuestionTable.Columns.VALUE, QuestionTable.Columns.ANSWER);
		db.execSQL(createSql);
	}

}
