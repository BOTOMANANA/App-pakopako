package com.example.application_pakopako.models;

public class SellProduct {
	int id;
	private int pakopakoCount;
	private int skewerCount;
	private int kitchenCount;
	private int juicesCount;

	public SellProduct(int pakopakoCount, int skewerCount, int kitchenCount, int juicesCount) {
		this.pakopakoCount = pakopakoCount;
		this.skewerCount = skewerCount;
		this.kitchenCount = kitchenCount;
		this.juicesCount = juicesCount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPakopakoCount() {
		return pakopakoCount;
	}

	public void setPakopakoCount(int pakopakoCount) {
		this.pakopakoCount = pakopakoCount;
	}

	public int getSkewerCount() {
		return skewerCount;
	}

	public void setSkewerCount(int skewerCount) {
		this.skewerCount = skewerCount;
	}

	public int getKitchenCount() {
		return kitchenCount;
	}

	public void setKitchenCount(int kitchenCount) {
		this.kitchenCount = kitchenCount;
	}

	public int getJuicesCount() {
		return juicesCount;
	}

	public void setJuicesCount(int juicesCount) {
		this.juicesCount = juicesCount;
	}
}
