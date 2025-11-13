package com.example.application_pakopako.constants;

public class BonusCalculator {
	private static  final int ITEMS_PER_BONUS = 10;
	public static int calculateBonus(int quantity) {
		if(quantity < 10) return 0;
		return quantity / ITEMS_PER_BONUS;
	}
}
