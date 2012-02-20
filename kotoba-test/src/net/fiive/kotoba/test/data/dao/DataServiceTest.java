package net.fiive.kotoba.test.data.dao;

import android.content.Intent;
import android.database.Cursor;
import android.test.ActivityUnitTestCase;
import net.fiive.kotoba.activities.MainActivity;
import net.fiive.kotoba.data.dao.DataService;
import net.fiive.kotoba.domain.Question;

import java.util.List;

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

		new DatabaseCleaner().cleanDatabase(getInstrumentation().getTargetContext());
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

	public void testFindAllQuestionIds() {
		Question questionA = new Question("foo", "bar");
		questionA = this.dataService.saveOrUpdateQuestion(questionA);
		Question questionB = new Question("bar", "foo");
		questionB = dataService.saveOrUpdateQuestion(questionB);

		List<Long> allQuestionIds = dataService.findAllQuestionIds();
		assertEquals(2, allQuestionIds.size());
		assertEquals( questionA.getId(), allQuestionIds.get(0));
		assertEquals( questionB.getId(), allQuestionIds.get(1));
	}




}
