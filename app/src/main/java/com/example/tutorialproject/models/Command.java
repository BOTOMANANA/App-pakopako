package com.example.tutorialproject.models;

public class Command {

	private int id ;
	private  int pakopako_simple_number;
	private  int pakopako_sauce_number;
	private int skewer_number;//BROCHETTE
	private int chicken_number; // POULET
	private int juice_number;
	private int french_fries_amount;
	private int bonus_number;


	public Command(int pakopako_simple_number, int pakopako_sauce_number, int skewer_number, int chicken_number, int juice_number,int french_fries_amount, int bonus_number) {
		this.pakopako_simple_number = pakopako_simple_number;
		this.pakopako_sauce_number = pakopako_sauce_number;
		this.skewer_number = skewer_number;
		this.chicken_number = chicken_number;
		this.juice_number = juice_number;
		this.french_fries_amount = french_fries_amount;
		this.bonus_number = bonus_number;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public int getSkewer_number() {
		return skewer_number;
	}
	public void setSkewer_number(int skewer_number) {
		this.skewer_number = skewer_number;
	}
	public int getJuice_number() {
		return juice_number;
	}
	public void setJuice_number(int juice_number) {
		this.juice_number = juice_number;
	}
	public int getChicken_number() {
		return chicken_number;
	}
	public void setChicken_number(int chicken_number) {
		this.chicken_number = chicken_number;
	}
	public int getBonus_number() {
		return bonus_number;
	}
	public void setBonus_number(int bonus_number) {
		this.bonus_number = bonus_number;
	}

	public int getPakopako_simple_number() {
		return pakopako_simple_number;
	}

	public void setPakopako_simple_number(int pakopako_simple_number) {
		this.pakopako_simple_number = pakopako_simple_number;
	}

	public int getPakopako_sauce_number() {
		return pakopako_sauce_number;
	}

	public void setPakopako_sauce_number(int pakopako_sauce_number) {
		this.pakopako_sauce_number = pakopako_sauce_number;
	}

	public int getFrench_fries_amount() {
		return french_fries_amount;
	}

	public void setFrench_fries_amount(int french_fries_amount) {
		this.french_fries_amount = french_fries_amount;
	}
}
