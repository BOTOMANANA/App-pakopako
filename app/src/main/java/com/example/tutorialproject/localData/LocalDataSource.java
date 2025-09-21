package com.example.tutorialproject.localData;

import com.example.tutorialproject.models.Command;

public interface LocalDataSource {
	long addCommands(Command commands);
	long getTotalNumberPakopako();
	long getTotalNumberSkewer();
	long getTotalNumberChicken();
	long getTotalNumberJuice();
	long getTotalBonus();

	int deleteStoryCommand();





}
