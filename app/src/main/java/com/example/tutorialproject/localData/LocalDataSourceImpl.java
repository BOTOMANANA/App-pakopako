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
import com.example.tutorialproject.models.ProductSimba;

import org.jetbrains.annotations.NotNull;

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
		cv.put(SourceDatabase.COLUMN_JUICE_BOTTLE_PRICE , commands.getJuiceBottleLiter());
		cv.put(SourceDatabase.COLUMN_AMOUNT_FRENCH_FRIES, commands.getFrench_fries_amount());
		cv.put(SourceDatabase.COLUMN_AMOUNT_OTHER, commands.getOther_amount());
		cv.put(SourceDatabase.COLUMN_NUMBER_PSIMPLE_BONUS, commands.getpSimpleBonus());
		cv.put(SourceDatabase.COLUMN_NUMBER_PSAUCE_BONUS, commands.getpSauceBonus());
		long newRowCommandId = database.insert(SourceDatabase.TABLE_COMMANDS_NAME ,null, cv);
		closeDatabase();
		if(newRowCommandId != -1){
			Log.d(Constants.TAG, Constants.ADD_SUCCESS + newRowCommandId);
		}
		else {
			Log.d(Constants.TAG, Constants.ADD_FAILURE+ " Error to add data in the database =>>" + newRowCommandId);
		}
		return newRowCommandId;
	}

	@Override
	public long insertProductSimba(ProductSimba productSimba) {
		openDatabase();
		ContentValues cv = new ContentValues();
		cv.put(SourceDatabase.COLUMN_PAKOPAKO_SIMBA, productSimba.getPakopakoSimba());
		cv.put(SourceDatabase.COLUMN_SKEWER_SIMBA, productSimba.getSkewerSimba());
		cv.put(SourceDatabase.COLUMN_EXPANSE, productSimba.getExpense());
		long newProductSimba = database.insert(SourceDatabase.TABLE_PRODUCT_SIMBA, null, cv);
		closeDatabase();
		if(newProductSimba != -1){
			Log.d(Constants.TAG, Constants.ADD_SUCCESS + newProductSimba);
		}
		else{
			Log.d(Constants.TAG, Constants.ADD_FAILURE+ " Error to add data in the database =>>" + newProductSimba);

		}
		return newProductSimba;
	}

	@SuppressLint("Recycle")
	@Override
	public long getTotalNumberPakopakoSimple() {
		return  calculateEitherNbrProduct(SourceDatabase.TABLE_COMMANDS_NAME, SourceDatabase.COLUMN_NUMBER_PAKOPAKO_SIMPLE);
	}

	@Override
	public long getTotalNumberPakopakoSauce() {
		return calculateEitherNbrProduct(SourceDatabase.TABLE_COMMANDS_NAME, SourceDatabase.COLUMN_NUMBER_PAKOPAKO_SAUCE);
	}

	@Override
	public long getTotalNumberSkewer() {
		return calculateEitherNbrProduct(SourceDatabase.TABLE_COMMANDS_NAME, SourceDatabase.COLUMN_NUMBER_SKEWER);
	}
	@Override
	public long getTotalNumberChicken() {
		return calculateEitherNbrProduct(SourceDatabase.TABLE_COMMANDS_NAME, SourceDatabase.COLUMN_NUMBER_CHICKEN);
	}
	@Override
	public long getTotalNumberJuice() {
		return calculateEitherNbrProduct(SourceDatabase.TABLE_COMMANDS_NAME, SourceDatabase.COLUMN_NUMBER_JUICE);
	}

	public long getTotalPriceJuiceBottle() {
		return calculateEitherNbrProduct(SourceDatabase.TABLE_COMMANDS_NAME, SourceDatabase.COLUMN_JUICE_BOTTLE_PRICE);
	}

	@Override
	public long getTotalAmountFrenchFries() {
		return calculateEitherNbrProduct(SourceDatabase.TABLE_COMMANDS_NAME, SourceDatabase.COLUMN_AMOUNT_FRENCH_FRIES);
	}


	@Override
	public long getTotalNbrPSimpleBonus() {
		return calculateEitherNbrProduct(SourceDatabase.TABLE_COMMANDS_NAME, SourceDatabase.COLUMN_NUMBER_PSIMPLE_BONUS);
	}

	@Override
	public long getTotalNbrPSauceBonus() {
		return calculateEitherNbrProduct(SourceDatabase.TABLE_COMMANDS_NAME, SourceDatabase.COLUMN_NUMBER_PSAUCE_BONUS);
	}

	@Override
	public long getTotalNumberPakopakoSimba() {
		return calculateEitherNbrProduct(SourceDatabase.TABLE_PRODUCT_SIMBA, SourceDatabase.COLUMN_PAKOPAKO_SIMBA);
	}
	@Override
	public long getTotalNumberSkewerSimba() {
		return calculateEitherNbrProduct(SourceDatabase.TABLE_PRODUCT_SIMBA, SourceDatabase.COLUMN_SKEWER_SIMBA);
	}
	@Override
	public long getTotalAmountOther() {
		return calculateEitherNbrProduct(SourceDatabase.TABLE_COMMANDS_NAME, SourceDatabase.COLUMN_AMOUNT_OTHER);
	}

	public long getSumAmountExpanse(){
		return calculateEitherNbrProduct(SourceDatabase.TABLE_PRODUCT_SIMBA, SourceDatabase.COLUMN_EXPANSE);
	}

	@Override
	public void deleteStoryCommand() {
		openDatabase();

		// THIS IS FOR CLEAR ALL THE ROW OF MY TABLE (ALL VALUE FOR COLUMN )
		int deleteCommand = database.delete(SourceDatabase.TABLE_COMMANDS_NAME, null, null);
		int deleteProductSimba = database.delete(SourceDatabase.TABLE_PRODUCT_SIMBA, null, null);

		closeDatabase();
		int isDelete = deleteCommand + deleteProductSimba;
		if (isDelete > 0) {
			Log.d(Constants.TAG, Constants.COMMAND_DELETE + isDelete);
			Toast.makeText(context, Constants.LOADING_DELETE_TOAST, Toast.LENGTH_SHORT).show();
		}
		else {
			Log.d(Constants.TAG, Constants.COMMAND_DELETE_NO);
			Toast.makeText(context, Constants.NO_DATA_DELETE_TOAST, Toast.LENGTH_SHORT).show();
		}

	}


	@SuppressLint("Recycle")
	private long calculateEitherNbrProduct(@NotNull String tableName, @NotNull String columnName){
		long nbrProductDeliver = 0;
		openDatabase();
		try (Cursor cursor = database.rawQuery("SELECT SUM(" + columnName + ") FROM " + tableName, null)) {

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

