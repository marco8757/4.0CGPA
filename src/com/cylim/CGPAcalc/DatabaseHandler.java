package com.cylim.CGPAcalc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "db_grade";
	private static final String TABLE_GRADE = "tb_grades";
	private static final String KEY_ID = "id";
	private static final String KEY_GRADE = "grade";
	private static final String KEY_GRADEPOINTS = "gpoint";
	private static final String KEY_COURSE_NAME = "course";
	private static final String KEY_CREDIT_HOURS = "credit";

	private static final String TABLE_GPA = "tb_gpa";
	private static final String KEY_GRADES = "grades";
	private static final String KEY_POINTS = "points";
	private static final String KEY_ENABLED = "enabled";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

		try {
			String CREATE_TABLE_GRADE = "CREATE TABLE " + TABLE_GRADE + "("
					+ KEY_ID + " INTEGER PRIMARY KEY, " + KEY_COURSE_NAME
					+ " TEXT UNIQUE," + KEY_CREDIT_HOURS + " TEXT," + KEY_GRADE
					+ " TEXT, " + KEY_GRADEPOINTS + " DOUBLE" + ")";

			String CREATE_TABLE_GPA = "CREATE TABLE " + TABLE_GPA + "("
					+ KEY_GRADES + " TEXT PRIMARY KEY," + KEY_POINTS
					+ " DOUBLE," + KEY_ENABLED + " INTEGER" + ")";

			db.execSQL(CREATE_TABLE_GRADE);
			db.execSQL(CREATE_TABLE_GPA);

			db.execSQL("INSERT INTO " + TABLE_GPA + " VALUES ('A+','-1.0','0')");
			db.execSQL("INSERT INTO " + TABLE_GPA + " VALUES ('A','-1.0','0')");
			db.execSQL("INSERT INTO " + TABLE_GPA + " VALUES ('A-','-1.0','0')");

			db.execSQL("INSERT INTO " + TABLE_GPA + " VALUES ('B+','-1.0','0')");
			db.execSQL("INSERT INTO " + TABLE_GPA + " VALUES ('B','-1.0','0')");
			db.execSQL("INSERT INTO " + TABLE_GPA + " VALUES ('B-','-1.0','0')");

			db.execSQL("INSERT INTO " + TABLE_GPA + " VALUES ('C+','-1.0','0')");
			db.execSQL("INSERT INTO " + TABLE_GPA + " VALUES ('C','-1.0','0')");
			db.execSQL("INSERT INTO " + TABLE_GPA + " VALUES ('C-','-1.0','0')");

			db.execSQL("INSERT INTO " + TABLE_GPA + " VALUES ('D+','-1.0','0')");
			db.execSQL("INSERT INTO " + TABLE_GPA + " VALUES ('D','-1.0','0')");
			db.execSQL("INSERT INTO " + TABLE_GPA + " VALUES ('D-','-1.0','0')");

			db.execSQL("INSERT INTO " + TABLE_GPA + " VALUES ('E+','-1.0','0')");
			db.execSQL("INSERT INTO " + TABLE_GPA + " VALUES ('E','-1.0','0')");
			db.execSQL("INSERT INTO " + TABLE_GPA + " VALUES ('E-','-1.0','0')");

			db.execSQL("INSERT INTO " + TABLE_GPA + " VALUES ('F+','-1.0','0')");
			db.execSQL("INSERT INTO " + TABLE_GPA + " VALUES ('F','-1.0','0')");
			db.execSQL("INSERT INTO " + TABLE_GPA + " VALUES ('F-','-1.0','0')");

		} catch (Exception e) {
			Log.d("CREATION ERROR", e.toString());
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_GRADE);

		onCreate(db);
	}

	// table result start

	public void addResult(String course, String credit, String grade,
			String point) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_COURSE_NAME, course);
		values.put(KEY_CREDIT_HOURS, credit);
		values.put(KEY_GRADE, grade);
		values.put(KEY_GRADEPOINTS, point);
		// Inserting Row
		db.insert(TABLE_GRADE, null, values);
		db.close(); // Closing database connection
	}

	public String[] getResultCourse() {

		String selectQuery = "SELECT  * FROM " + TABLE_GRADE;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		String[] array = new String[cursor.getCount()];
		int i = 0;
		while (cursor.moveToNext()) {
			String course = cursor.getString(cursor
					.getColumnIndex(KEY_COURSE_NAME));
			array[i] = course;
			i++;
		}
		db.close();
		return array;

	}

	public String[] getResultCredit() {

		String selectQuery = "SELECT  * FROM " + TABLE_GRADE;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		String[] array = new String[cursor.getCount()];
		int i = 0;
		while (cursor.moveToNext()) {
			String credit = cursor.getString(cursor
					.getColumnIndex(KEY_CREDIT_HOURS));
			array[i] = credit;
			i++;
		}
		db.close();
		return array;

	}

	public String[] getResultGrade() {
		String selectQuery = "SELECT  * FROM " + TABLE_GRADE;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		String[] array = new String[cursor.getCount()];
		int i = 0;
		while (cursor.moveToNext()) {
			String grade = cursor.getString(cursor.getColumnIndex(KEY_GRADE));
			array[i] = grade;
			i++;
		}
		db.close();
		return array;

	}

	public String[] getResultPoints() {
		String selectQuery = "SELECT  * FROM " + TABLE_GRADE;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		String[] array = new String[cursor.getCount()];
		int i = 0;
		while (cursor.moveToNext()) {
			String points = cursor.getString(cursor
					.getColumnIndex(KEY_GRADEPOINTS));
			array[i] = points;
			i++;
		}
		db.close();
		return array;
	}

	public double getCreditSum() {
		String selectQuery = "SELECT  SUM(" + KEY_CREDIT_HOURS
				+ ") AS 'TOTALCREDIT' FROM " + TABLE_GRADE;
		double sum = 0;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			sum = cursor.getDouble(0);
		}

		return sum;
	}

	public double getCreditXPoints() {
		double sum = 0;
		String selectQuery = "SELECT  SUM(" + KEY_GRADEPOINTS + "*"
				+ KEY_CREDIT_HOURS + ") AS 'TOTAL' FROM " + TABLE_GRADE;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			sum = cursor.getDouble(0);
		}

		return sum;
	}

	public String[] getResult(String course) {

		String[] result;
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_GRADE, new String[] { KEY_ID,
				KEY_COURSE_NAME, KEY_CREDIT_HOURS, KEY_GRADE }, KEY_COURSE_NAME
				+ "=?", new String[] { course }, null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();

			String[] queryresult = {
					cursor.getString(cursor.getColumnIndex(KEY_COURSE_NAME)),
					cursor.getString(cursor.getColumnIndex(KEY_CREDIT_HOURS)),
					cursor.getString(cursor.getColumnIndex(KEY_GRADE)),
					cursor.getString(cursor.getColumnIndex(KEY_GRADEPOINTS)) };
			result = queryresult;
		} else {
			result = new String[] { "null", "null", "null", "null" };
		}
		db.close();
		return result;

	}

	public void updateResult(String course, String credit, String grade,
			String newcourse, String newcredit, String newgrade, String point) {
		SQLiteDatabase db = this.getWritableDatabase();

		db.execSQL("UPDATE " + TABLE_GRADE + " SET " + KEY_COURSE_NAME + " = '"
				+ newcourse + "', " + KEY_GRADE + " = '" + newgrade + "', "
				+ KEY_GRADEPOINTS + " = '" + point + "' WHERE "
				+ KEY_COURSE_NAME + " = '" + course + "' AND "
				+ KEY_CREDIT_HOURS + " = '" + credit + "' AND " + KEY_GRADE
				+ " = '" + grade + "'");
		db.close();

		
	}

	public void deleteResult(String course, String credit, String grade) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DELETE FROM " + TABLE_GRADE + " WHERE " + KEY_COURSE_NAME
				+ " = '" + course + "' AND " + KEY_CREDIT_HOURS + " = '"
				+ credit + "' AND " + KEY_GRADE + " = '" + grade + "'");
		db.close();
	}

	// table result ends

	// table gpa start
	public String[][] getGPAGrades() {

		String selectQuery = "SELECT  * FROM " + TABLE_GPA + " WHERE "
				+ KEY_ENABLED + " <> '0' ";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		String[][] array = new String[cursor.getCount()][3];
		int i = 0;
		while (cursor.moveToNext()) {
			String grade = cursor.getString(cursor.getColumnIndex(KEY_GRADES));
			String points = cursor.getString(cursor.getColumnIndex(KEY_POINTS));
			String enabled = cursor.getString(cursor
					.getColumnIndex(KEY_ENABLED));
			array[i][0] = grade;
			array[i][1] = points;
			array[i][2] = enabled;
			i++;
		}
		db.close();
		return array;

	}
	
	public String[][] getAllGPAGrades() {

		String selectQuery = "SELECT  * FROM " + TABLE_GPA ;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		String[][] array = new String[cursor.getCount()][3];
		int i = 0;
		while (cursor.moveToNext()) {
			String grade = cursor.getString(cursor.getColumnIndex(KEY_GRADES));
			String points = cursor.getString(cursor.getColumnIndex(KEY_POINTS));
			String enabled = cursor.getString(cursor
					.getColumnIndex(KEY_ENABLED));
			array[i][0] = grade;
			array[i][1] = points;
			array[i][2] = enabled;
			i++;
		}
		db.close();
		return array;

	}

	public void updateGrade(String grade, String points, String enabled) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_POINTS, points);
		values.put(KEY_ENABLED, enabled);
		db.update(TABLE_GPA, values, KEY_GRADES + " = ?",
				new String[] { grade });
		db.close();
	}
	// table gpa ends

}
