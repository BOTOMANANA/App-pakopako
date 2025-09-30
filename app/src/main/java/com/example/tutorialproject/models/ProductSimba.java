package com.example.tutorialproject.models;

public class ProductSimba {
	private int pakopakoSimba;
	private int skewerSimba;
	private int expense;

	public ProductSimba(int pakopakoSimba, int skewerSimba, int expense) {
		this.pakopakoSimba = pakopakoSimba;
		this.skewerSimba = skewerSimba;
		this.expense = expense;
	}

	public int getPakopakoSimba() {
		return pakopakoSimba;
	}

	public void setPakopakoSimba(int pakopakoSimba) {
		this.pakopakoSimba = pakopakoSimba;
	}

	public int getSkewerSimba() {
		return skewerSimba;
	}

	public void setSkewerSimba(int skewerSimba) {
		this.skewerSimba = skewerSimba;
	}

	public int getExpense() {
		return expense;
	}

	public void setExpense(int expense) {
		this.expense = expense;
	}
}
