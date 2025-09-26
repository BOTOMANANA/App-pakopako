package com.example.tutorialproject.localData;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.tutorialproject.constants.Constants;
import com.example.tutorialproject.models.Command;

public class LocalDataSourceImpl implements LocalDataSource {
	private SQLiteDatabase database;
	private final SourceDatabase ldb;
	Context context;

	public LocalDataSourceImpl(Context context) {
		ldb = new SourceDatabase(context);
		this.context = context;
	}
	private void openDatabase(){ database = ldb.getWritableDatabase();}
	public void closeDatabase(){ ldb.close();}
	@Override
	public long addCommands(Command commands) {
		openDatabase();
		ContentValues cv = new ContentValues();
		cv.put(SourceDatabase.COLUMN_NUMBER_PAKOPAKO_SIMPLE , commands.getPakopako_simple_number());
		cv.put(SourceDatabase.COLUMN_NUMBER_PAKOPAKO_SAUCE, commands.getPakopako_sauce_number());
		cv.put(SourceDatabase.COLUMN_NUMBER_CHICKEN , commands.getChicken_number());
		cv.put(SourceDatabase.COLUMN_NUMBER_SKEWER , commands.getSkewer_number());
		cv.put(SourceDatabase.COLUMN_NUMBER_JUICE , commands.getJuice_number());
		cv.put(SourceDatabase.COLUMN_AMOUNT_FRENCH_FRIES, commands.getFrench_fries_amount());
		cv.put(SourceDatabase.COLUMN_NUMBER_BONUS, commands.getBonus_number());
		long newRowCommandId = database.insert(SourceDatabase.TABLE_COMMANDS_NAME ,null, cv);
		closeDatabase();
		if(newRowCommandId != -1){
			Log.d(Constants.TAG, Constants.ADD_SUCCESS + newRowCommandId);
		}else {
			Log.d(Constants.TAG, Constants.ADD_FAILURE+ " Error to add data in the database =>>" + newRowCommandId);

		}

		return newRowCommandId;
	}

	@SuppressLint("Recycle")
	@Override
	public long getTotalNumberPakopakoSimple() {
		return  calculateEitherNbrProduct(SourceDatabase.COLUMN_NUMBER_PAKOPAKO_SIMPLE);
	}
	@Override
	public long getTotalNumberSkewer() {
		return calculateEitherNbrProduct( SourceDatabase.COLUMN_NUMBER_SKEWER);
	}
	@Override
	public long getTotalNumberChicken() {
		return calculateEitherNbrProduct(SourceDatabase.COLUMN_NUMBER_CHICKEN);
	}
	@Override
	public long getTotalNumberJuice() {
		return calculateEitherNbrProduct(SourceDatabase.COLUMN_NUMBER_JUICE);
	}
	@Override
	public long getTotalBonus() {
		return calculateEitherNbrProduct( SourceDatabase.COLUMN_NUMBER_BONUS);
	}

	@Override
	public void deleteStoryCommand() {
		openDatabase();

		// THIS IS FOR CLEAR ALL THE ROW OF MY TABLE (ALL VALUE FOR COLUMN )
		int numberOfDeletedRows = database.delete(SourceDatabase.TABLE_COMMANDS_NAME, null, null);
		closeDatabase();

		if (numberOfDeletedRows > 0) {
			Log.d(Constants.TAG, Constants.COMMAND_DELETE+ numberOfDeletedRows);
			Toast.makeText(context, Constants.LOADING_DELETE_TOAST, Toast.LENGTH_SHORT).show();
		} else {
			Log.d(Constants.TAG, Constants.COMMAND_DELETE_NO);
			Toast.makeText(context, Constants.NO_DATA_DELETE_TOAST, Toast.LENGTH_SHORT).show();
		}

	}


	@SuppressLint("Recycle")
	private long calculateEitherNbrProduct(String columnName){
		long nbrProductDeliver = 0;
		openDatabase();
		try (Cursor cursor = database.rawQuery("SELECT SUM(" + columnName + ") FROM " + SourceDatabase.TABLE_COMMANDS_NAME, null)) {

			if (cursor.moveToFirst()) {
				nbrProductDeliver = cursor.getLong(0);
			}

		} catch (Exception e) {
			Log.e(Constants.TAG, Constants.SUM_ERROR + columnName, e);
		} finally {
			closeDatabase();
		}

		return nbrProductDeliver;

	}


}
