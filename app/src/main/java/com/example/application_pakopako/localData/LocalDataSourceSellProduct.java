package com.example.application_pakopako.localData;

import com.example.application_pakopako.models.Command;
import com.example.application_pakopako.models.SellProduct;

public interface LocalDataSourceSellProduct {
	void appendSellProduct(SellProduct sellProduct);
	long getPakopakoCount();
	long getSkewerCount();
	long getKitchenCount();
	long getJuicesCount();
}
