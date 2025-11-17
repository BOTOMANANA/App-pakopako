package com.example.application_pakopako.localData;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.application_pakopako.constants.Constants;
import com.example.application_pakopako.models.SellProduct;

public class LocalDataSourceSellProductImpl implements LocalDataSourceSellProduct {

	private SQLiteDatabase database;
	private final SourceDatabase sourceDatabase;
	Context context;

	public LocalDataSourceSellProductImpl(Context context) {
		sourceDatabase = new SourceDatabase(context);
		this.context = context;
	}
	private void openDatabase(){ database = sourceDatabase.getWritableDatabase();}
	public void closeDatabase(){ sourceDatabase.close();}
	@Override
	public void appendSellProduct(SellProduct sellProduct) {
		openDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(SourceDatabase.COLUMN_PAKOPAKO_COUNT, sellProduct.getPakopakoCount());
		contentValues.put(SourceDatabase.COLUMN_SKEWER_COUNT, sellProduct.getSkewerCount());
		contentValues.put(SourceDatabase.COLUMN_KITCHEN_COUNT, sellProduct.getKitchenCount());
		contentValues.put(SourceDatabase.COLUMN_JUICES_COUNT, sellProduct.getJuicesCount());

		long newRowCommandId = database.insert(SourceDatabase.TABLE_SELL_PRODUCT ,null, contentValues);
		closeDatabase();

		if (newRowCommandId != -1) Log.d(Constants.TAG, Constants.ADD_SUCCESS + newRowCommandId);
		else Log.d(Constants.TAG, Constants.ADD_FAILURE+ " Error to add data in the database =>>" + newRowCommandId);
	}

	@Override
	public long getPakopakoCount() {
		return getLastCountForColumn(SourceDatabase.COLUMN_PAKOPAKO_COUNT);
	}

	@Override
	public long getSkewerCount() {
		return getLastCountForColumn(SourceDatabase.COLUMN_SKEWER_COUNT);
	}

	@Override
	public long getKitchenCount() {
		return getLastCountForColumn(SourceDatabase.COLUMN_KITCHEN_COUNT);
	}

	@Override
	public long getJuicesCount() {
		return getLastCountForColumn(SourceDatabase.COLUMN_JUICES_COUNT);
	}

	private long getLastCountForColumn(String columnName) {
		long count = 0;
		Cursor cursor = null;
		try {
			openDatabase();
			cursor = database.query(SourceDatabase.TABLE_SELL_PRODUCT,
					  new String[]{columnName},
					  null, null, null, null,
					  SourceDatabase.COLUMN_ID_SELL_PRODUCT + " DESC", "1");

			if (cursor.moveToFirst()) {
				count = cursor.getLong(cursor.getColumnIndexOrThrow(columnName));
			}
		} catch (SQLException e) {
			Log.e(Constants.TAG, "Error getting last count for " + columnName + ": " + e.getMessage());
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			closeDatabase();
		}
		return count;
	}
}



