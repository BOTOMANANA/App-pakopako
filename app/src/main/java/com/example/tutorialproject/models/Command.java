package com.example.tutorialproject.models;

public class Command {

	private int id ;
	private  int pakopako_simple_number;
	private  int pakopako_sauce_number;
	private int skewer_number;
	private int chicken_number;
	private int juice_number;
	private int french_fries_amount;
	private int pSimpleBonus, pSauceBonus;
	private int juiceBottleLiter;


	public Command(
			  int pakopako_simple_number,
			  int pakopako_sauce_number,
			  int skewer_number,
			  int chicken_number,
			  int juice_number,
			  int juiceBottleLiter,
			  int french_fries_amount,
			  int pSimpleBonus,
			  int pSauceBonus)
	{
		this.pakopako_simple_number = pakopako_simple_number;
		this.pakopako_sauce_number = pakopako_sauce_number;
		this.skewer_number = skewer_number;
		this.chicken_number = chicken_number;
		this.juice_number = juice_number;
		this.juiceBottleLiter = juiceBottleLiter;
		this.french_fries_amount = french_fries_amount;
		this.pSimpleBonus = pSimpleBonus;
		this.pSauceBonus  = pSauceBonus;
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

	public int getJuiceBottleLiter() {
		return juiceBottleLiter;
	}

	public void setJuiceBottleLiter(int juiceBottleLiter) {
		this.juiceBottleLiter = juiceBottleLiter;
	}

	public int getChicken_number() {
		return chicken_number;
	}
	public void setChicken_number(int chicken_number) {
		this.chicken_number = chicken_number;
	}

	public int getpSimpleBonus() {
		return pSimpleBonus;
	}

	public void setpSimpleBonus(int pSimpleBonus) {
		this.pSimpleBonus = pSimpleBonus;
	}

	public int getpSauceBonus() {
		return pSauceBonus;
	}

	public void setpSauceBonus(int pSauceBonus) {
		this.pSauceBonus = pSauceBonus;
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
