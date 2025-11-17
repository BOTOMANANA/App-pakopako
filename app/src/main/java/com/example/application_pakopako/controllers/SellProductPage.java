package com.example.application_pakopako.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.application_pakopako.R;
import com.example.application_pakopako.constants.Constants;
import com.example.application_pakopako.localData.LocalDataSourceSellProductImpl;
import com.example.application_pakopako.models.SellProduct;
import com.example.application_pakopako.utils.GetValueFromEditText;

public class SellProductPage extends AppCompatActivity {
	EditText editNumberPakopako, editNumberSkewer, editNumberKitchen;
	Button buttonRegisterSellProduct, buttonNext;
	LocalDataSourceSellProductImpl sourceSellProduct;
	int quantityPakopako, quantitySkewer, quantityKitchen, quantityJuices;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EdgeToEdge.enable(this);
		setContentView(R.layout.sell_product_page);
		configViews();
		LinearLayout linearLayout = findViewById(R.id.linearAnimate);
		YoYo.with(Techniques.RotateInUpLeft).duration(1000).playOn(linearLayout);
		sourceSellProduct = new LocalDataSourceSellProductImpl(this);
		buttonRegisterSellProduct.setOnClickListener(v -> {
			onSubmit();
			startActivity(new Intent(this, AddNewCommandPage.class));
		});
		buttonNext.setOnClickListener(v -> startActivity(new Intent(this, AddNewCommandPage.class)));


		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
			Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
			return insets;
		});
	}

	private void configViews() {
		editNumberPakopako = findViewById(R.id.editNumberPakopako);
		editNumberSkewer = findViewById(R.id.editNumberSkewer);
		editNumberKitchen = findViewById(R.id.editNumberKitchen);
		buttonRegisterSellProduct = findViewById(R.id.buttonRegisterSellProduct);
		buttonNext = findViewById(R.id.buttonNextSellProduct);

	}

	private void onSubmit() {
		SellProduct productCount;
		quantityPakopako = GetValueFromEditText.getValue(editNumberPakopako);
		quantitySkewer = GetValueFromEditText.getValue(editNumberSkewer);
		quantityKitchen = GetValueFromEditText.getValue(editNumberKitchen);
		quantityJuices = 1;
		productCount = new SellProduct(quantityPakopako, quantitySkewer, quantityKitchen, quantityJuices);
		sourceSellProduct.appendSellProduct(productCount);
		Log.d(Constants.TAG, " The data is insert in the database");

	}
}