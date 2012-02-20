package net.fiive.kotoba.test.data.dao;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.test.ActivityUnitTestCase;
import net.fiive.intern.data.OpenHelper;
import net.fiive.kotoba.activities.MainActivity;
import net.fiive.kotoba.data.dao.DataService;
import net.fiive.kotoba.data.migration.M00_001_CreateQuestion;
import net.fiive.kotoba.data.table.QuestionTable;
import net.fiive.kotoba.domain.Question;

public class DataServiceTest extends ActivityUnitTestCase<MainActivity> {

	private DataService dataService;


	public DataServiceTest() {
		super(MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		startActivity(new Intent(getInstrumentation().getTargetContext(), MainActivity.class), null, null );
		getActivity();
		this.dataService = new DataService(getInstrumentation().getTargetContext());

		cleanDatabase();
	}

	public void testQuestionLifecycle() {
		Question question = new Question("foo", "bar");
		question = dataService.saveOrUpdateQuestion(question);
		assertTrue( question.getId() > 0 );


		Question questionFromDb = dataService.findQuestionById(question.getId());
		assertEquals(question, questionFromDb);

		Long id = question.getId();
		question.setAnswer("blah");
		dataService.saveOrUpdateQuestion(question);
		assertEquals( id, question.getId());

		dataService.removeQuestion(question);
		assertNull(question.getId());
		assertNull( dataService.findQuestionById(id));
	}

	public void testFindCursorForAllQuestions() {

		Question questionA = new Question("foo", "bar");
		questionA = this.dataService.saveOrUpdateQuestion(questionA);
		Question questionB = new Question("bar", "foo" );
		questionB = this.dataService.saveOrUpdateQuestion(questionB);

		Cursor questionCursor = dataService.cursorForFindAll();
		assertEquals(questionCursor.getCount(), 2);
		assertTrue(questionCursor.moveToFirst());
		Question questionFromCursor = dataService.buildQuestionFromCursor(questionCursor);
		assertEquals(questionA, questionFromCursor);
		assertTrue(questionCursor.moveToNext());
		assertEquals(questionB, dataService.buildQuestionFromCursor(questionCursor));
		assertFalse(questionCursor.moveToNext());

		if(!questionCursor.isClosed()) {
			questionCursor.close();
		}
	}

	private void cleanDatabase() {
		SQLiteOpenHelper openHelper = new OpenHelper(getInstrumentation().getTargetContext(), DataService.DATABASE_NAME, DataService.DATABASE_VERSION, new M00_001_CreateQuestion());
		SQLiteDatabase database = openHelper.getWritableDatabase();
		database.delete(QuestionTable.TABLE_NAME, null, null);

	}


}
