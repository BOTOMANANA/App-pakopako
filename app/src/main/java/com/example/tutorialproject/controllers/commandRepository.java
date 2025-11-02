package com.example.tutorialproject.controllers;

import android.content.Context;

import com.example.tutorialproject.constants.Constants;
import com.example.tutorialproject.constants.BonusCalculator;
import com.example.tutorialproject.error.LogException;
import com.example.tutorialproject.localData.LocalDataSourceImpl;
import com.example.tutorialproject.models.Command;
import com.example.tutorialproject.utils.ToastMessage;

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
