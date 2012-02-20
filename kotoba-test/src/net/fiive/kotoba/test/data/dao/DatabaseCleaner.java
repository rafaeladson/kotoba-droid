package net.fiive.kotoba.test.data.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import net.fiive.intern.android.data.OpenHelper;
import net.fiive.kotoba.data.dao.DataService;
import net.fiive.kotoba.data.migration.M00_001_CreateQuestion;
import net.fiive.kotoba.data.table.QuestionTable;

public class DatabaseCleaner {

	public void cleanDatabase(Context context) {
		SQLiteOpenHelper openHelper = new OpenHelper(context, DataService.DATABASE_NAME, DataService.DATABASE_VERSION, new M00_001_CreateQuestion());
		SQLiteDatabase database = openHelper.getWritableDatabase();
		database.delete(QuestionTable.TABLE_NAME, null, null);
	}
}
