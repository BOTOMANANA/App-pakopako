package com.example.tutorialproject.localData;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SourceDatabase extends SQLiteOpenHelper {


	public static final String DATABASE_NAME = "AntMobile3.db";
	private static final int VERSION = 2;
	// Creation de la table commands
	public static final String TABLE_COMMANDS_NAME = "commands";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_NUMBER_PAKOPAKO = "pakopako";
	public static final String COLUMN_NUMBER_SKEWER = "skewer";
	public static final String COLUMN_NUMBER_CHICKEN = "chicken";
	public static final String COLUMN_NUMBER_JUICE = "juice";
	public static final String COLUMN_NUMBER_BONUS = "bonus";

	private static final String CREATE_TABLE_COMMANDS =
			  "CREATE TABLE " + TABLE_COMMANDS_NAME + " (" +
						 COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
						 COLUMN_NUMBER_PAKOPAKO + " INTEGER, " +
						 COLUMN_NUMBER_SKEWER + " INTEGER, " +
						 COLUMN_NUMBER_CHICKEN+ " INTEGER, " +
						 COLUMN_NUMBER_JUICE+ " INTEGER," +
						 COLUMN_NUMBER_BONUS + " INTEGER);";

	public SourceDatabase(Context context) {
		super(context, DATABASE_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_COMMANDS);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (oldVersion < newVersion) {
			db.execSQL("ALTER TABLE " + TABLE_COMMANDS_NAME + " ADD COLUMN " + COLUMN_NUMBER_BONUS + "TEXT");
		}
		db.execSQL(" DROP TABLE IF EXISTS " + TABLE_COMMANDS_NAME);
		onCreate(db);

	}
}
