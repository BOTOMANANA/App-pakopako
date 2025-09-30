package com.example.tutorialproject.localData;

import com.example.tutorialproject.models.Command;

public interface LocalDataSource {
	long addCommands(Command commands);
	long getTotalNumberPakopakoSimple();
	long getTotalNumberPakopakoSauce();
	long getTotalNumberPakopakoSimba();
	long getTotalNumberSkewerSimba();
	long getTotalNumberSkewer();
	long getTotalNumberChicken();
	long getTotalNumberJuice();
	long getTotalAmountFrenchFries();
	long getTotalNbrPSimpleBonus();
	long getTotalNbrPSauceBonus();

	void deleteStoryCommand();
//	long getTotalNumberSkewerSimba();
//	long getTotalNumberPakopakoSimba();
//	long getTotalAmountFrenchFries();

}
