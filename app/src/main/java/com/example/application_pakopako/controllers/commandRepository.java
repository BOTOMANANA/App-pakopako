package com.example.application_pakopako.controllers;

import android.content.Context;

import com.example.application_pakopako.constants.Constants;
import com.example.application_pakopako.constants.BonusCalculator;
import com.example.application_pakopako.error.LogException;
import com.example.application_pakopako.localData.LocalDataSourceImpl;
import com.example.application_pakopako.models.Command;
import com.example.application_pakopako.utils.ToastMessage;

public class commandRepository {
	private final LocalDataSourceImpl localDataSource;
	private final Context context;

	public commandRepository(Context context, LocalDataSourceImpl localDataSource) {
		this.context = context.getApplicationContext();
		this.localDataSource = localDataSource;
	}
	public void createAndSaveCommand(
			  int pSimpleQuantity,
			  int pSauceQuantity,
			  int skewerQuantity,
			  int chickenQuantity,
			  int juiceQuantity,
			  int juiceBottlePrice,
			  int totalFFries,
			  int otherAmount) {

		int numberPakopakoSimpleBonus = BonusCalculator.calculateBonus(pSimpleQuantity);
		int numberPakopakoSauceBonus = BonusCalculator.calculateBonus(pSauceQuantity);

		Command command = new Command(
				  pSimpleQuantity,
				  pSauceQuantity,
				  skewerQuantity,
				  chickenQuantity,
				  juiceQuantity,
				  juiceBottlePrice,
				  totalFFries,
				  otherAmount,
				  numberPakopakoSimpleBonus,
				  numberPakopakoSauceBonus);

		try {
			 localDataSource.addCommands(command);
			ToastMessage.showToast(context, Constants.ADD_SUCCESS_TOAST);
		}
		catch (Exception e) {
			LogException.logError(e);
		}
	}
}
