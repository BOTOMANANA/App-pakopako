package com.example.tutorialproject.localData;

import com.example.tutorialproject.models.Command;

public interface LocalDataSource {
	long addCommands(Command commands);
	long getTotalNumberPakopakoSimple();
	long getTotalNumberSkewer();
	long getTotalNumberChicken();
	long getTotalNumberJuice();
	long getTotalBonus();

	void deleteStoryCommand();
//	long getTotalNumberSkewerSimba();
//	long getTotalNumberPakopakoSimba();
//	long getTotalAmountFrenchFries();

}
