package com.example.tutorialproject.localData;

import com.example.tutorialproject.models.Command;
import com.example.tutorialproject.models.ProductSimba;

public interface LocalDataSource {
	long addCommands(Command commands);
	long insertProductSimba(ProductSimba productSimba);
	long getTotalNumberPakopakoSimple();
	long getTotalNumberPakopakoSauce();
	long getTotalNumberPakopakoSimba();
	long getTotalNumberSkewerSimba();
	long getTotalNumberSkewer();
	long getTotalNumberChicken();
	long getTotalNumberJuice();
	long getTotalAmountFrenchFries();
	long getTotalAmountOther();
	long getTotalNbrPSimpleBonus();
	long getTotalNbrPSauceBonus();

	void deleteStoryCommand();


}
