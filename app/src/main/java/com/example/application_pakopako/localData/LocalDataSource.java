package com.example.application_pakopako.localData;

import com.example.application_pakopako.models.Command;
import com.example.application_pakopako.models.ProductSimba;

public interface LocalDataSource {
	void addCommands(Command commands);
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
