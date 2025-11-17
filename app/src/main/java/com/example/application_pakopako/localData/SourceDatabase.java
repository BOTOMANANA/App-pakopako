package com.example.application_pakopako.localData;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SourceDatabase extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "AntMobile13.db";
	private static final int VERSION = 12;
	public static final String TABLE_COMMANDS_NAME = "commands";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_NUMBER_PAKOPAKO_SIMPLE = "pakopakoSimple";
	public static final String COLUMN_NUMBER_PAKOPAKO_SAUCE = "pakopakoSauce";
	public static final String COLUMN_NUMBER_SKEWER = "skewer";
	public static final String COLUMN_NUMBER_CHICKEN = "chicken";
	public static final String COLUMN_NUMBER_JUICE = "juice";
	public static final String COLUMN_JUICE_BOTTLE_PRICE = "juice_bottle_price";
	public static final String COLUMN_AMOUNT_FRENCH_FRIES = "french_fries";
	public static final String COLUMN_NUMBER_PSIMPLE_BONUS = "psimple_bonus";
	public static final String COLUMN_NUMBER_PSAUCE_BONUS = "psauce_bonus";
	public static final String COLUMN_AMOUNT_OTHER = "other";

	private static final String CREATE_TABLE_COMMANDS =
			  "CREATE TABLE " + TABLE_COMMANDS_NAME + " (" +
						 COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
						 COLUMN_NUMBER_PAKOPAKO_SIMPLE + " INTEGER, " +
						 COLUMN_NUMBER_PAKOPAKO_SAUCE + " INTEGER, " +
						 COLUMN_NUMBER_SKEWER + " INTEGER, " +
						 COLUMN_NUMBER_CHICKEN + " INTEGER, " +
						 COLUMN_NUMBER_JUICE + " INTEGER," +
						 COLUMN_JUICE_BOTTLE_PRICE + " INTEGER," +
						 COLUMN_AMOUNT_FRENCH_FRIES + " INTEGER, " +
						 COLUMN_AMOUNT_OTHER + " INTEGER, " +
						 COLUMN_NUMBER_PSIMPLE_BONUS + " INTEGER, " +
						 COLUMN_NUMBER_PSAUCE_BONUS + " INTEGER);";

	public static final String TABLE_PRODUCT_SIMBA = "product_simba";
	public static final String COLUMN_ID_PRODUCT = "id";
	public static final String COLUMN_PAKOPAKO_SIMBA = "pakopako_simba";
	public static final String COLUMN_SKEWER_SIMBA = "skewer_simba";
	public static final String COLUMN_EXPANSE = "expanse_simba";

	private static final String CREATE_TABlE_PRODUCT_SIMBA =
			  "CREATE TABLE " + TABLE_PRODUCT_SIMBA + "(" +
						 COLUMN_ID_PRODUCT + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
						 COLUMN_PAKOPAKO_SIMBA + " INTEGER, " +
						 COLUMN_SKEWER_SIMBA + " INTEGER, " +
						 COLUMN_EXPANSE + " INTEGER);";


	public static final String TABLE_SELL_PRODUCT = "sell_product";
	public static final String COLUMN_ID_SELL_PRODUCT = "id";
	public static final String COLUMN_PAKOPAKO_COUNT = "pakopako_count";
	public static final String COLUMN_SKEWER_COUNT = "skewer_count";
	public static final String COLUMN_KITCHEN_COUNT = "kitchen_count";
	public static final String COLUMN_JUICES_COUNT = "juices_count";


	private static final String CREATE_TABlE_SELL_PRODUCT =
			  "CREATE TABLE " + TABLE_SELL_PRODUCT + "(" +
						 COLUMN_ID_SELL_PRODUCT + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
						 COLUMN_PAKOPAKO_COUNT + " INTEGER, " +
						 COLUMN_SKEWER_COUNT + " INTEGER, " +
						 COLUMN_KITCHEN_COUNT + " INTEGER," +
						 COLUMN_JUICES_COUNT + " INTEGER);";


	public SourceDatabase(Context context) {
		super(context, DATABASE_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_COMMANDS);
		db.execSQL(CREATE_TABlE_PRODUCT_SIMBA);
		db.execSQL(CREATE_TABlE_SELL_PRODUCT);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(" DROP TABLE IF EXISTS " + TABLE_COMMANDS_NAME);
		onCreate(db);

	}
}

