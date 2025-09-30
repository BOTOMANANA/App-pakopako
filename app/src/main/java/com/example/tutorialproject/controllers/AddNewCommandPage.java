package com.example.tutorialproject.controllers;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.tutorialproject.R;
import com.example.tutorialproject.constants.Constants;
import com.example.tutorialproject.localData.LocalDataSourceImpl;
import com.example.tutorialproject.models.Command;
import com.example.tutorialproject.utils.BiometricAuthenticator;
import com.example.tutorialproject.utils.NumberFormated;
import com.example.tutorialproject.utils.ToastMessage;


public class AddNewCommandPage extends AppCompatActivity {
	EditText editPakopakoSimple, editPakopakoSauce, editSkewer, editChicken, editJuice, editFrenchFries, editMoney;
	Spinner spinner_frenchFries, spinner_juices;
	LocalDataSourceImpl localDataSource;
	TextView text_ariary, amount_command,clientBalance, nbrValuePakopako, nbrValueSkewer, nbrValueChicken, nbrValueJuice, amountFrenchFries;
	Button btn_addData;
	private long backButtonTime;
	Button btn_floating;
	LinearLayout header_widget;
	int NbrSimpleBonus, NbrSauceBonus;

	int juiceSelectedValue, frenchFriesSelectedValue;

	@SuppressLint("MissingInflatedId")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EdgeToEdge.enable(this);
		setContentView(R.layout.add_new_command_page);

		setupViews();
		setupSpinnerAdapter(spinner_juices, R.array.JuiceType);
		setupSpinnerAdapter(spinner_frenchFries, R.array.frenchFriesType);


		localDataSource = new LocalDataSourceImpl(this);
		BiometricAuthenticator biometric = new BiometricAuthenticator(this);

		TextWatcher watcher = new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				calculate_displaySum();

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		};

		editPakopakoSimple.addTextChangedListener(watcher);
		editPakopakoSauce.addTextChangedListener(watcher);
		editSkewer.addTextChangedListener(watcher);
		editChicken.addTextChangedListener(watcher);
		editJuice.addTextChangedListener(watcher);
		editFrenchFries.addTextChangedListener(watcher);
		editMoney.addTextChangedListener(watcher);
		spinner_juices.setOnItemSelectedListener(spinnerListener);
		spinner_frenchFries.setOnItemSelectedListener(spinnerListener);


		calculate_displaySum();

		btn_addData.setOnClickListener(v -> {
			addCommandInDatabase();
			cleanAllEditText();

		});

		btn_floating.setOnClickListener(v -> biometric.authenticate());

		getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
			@Override
			public void handleOnBackPressed() {
				if (backButtonTime + 2000 > System.currentTimeMillis()) {
					finishAffinity();
				}
				else {
					backButtonTime = System.currentTimeMillis();
				}
			}
		});
		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
			Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
			return insets;
		});
	}

	private int getValueFromEditText(EditText userInput){
		String value  = userInput.getText().toString().trim();
		if(value.isEmpty()) return 0;

		try {
			return Integer.parseInt(value);
		}
		catch (NumberFormatException e) {
			Log.d(Constants.TAG_FORMAT , Constants.FORMAT_EXCEPTION + e.getMessage());
			return 0;
		}
	}

	@SuppressLint("SetTextI18n")
	private void calculate_displaySum(){
		int sumAmountCommand = 0, clientAmount;
		int changeAmount ;
		String nbrPakopakoDeliver, nbrSkewerDeliver, nbrChickenDeliver, nbrJuiceDeliver, frenchFriesDeliver, totalSumPayed, changeAmountFormat;

		sumAmountCommand += getValueFromEditText(editPakopakoSimple) * Constants.PriceOfProduct.PAKOPAKO_SIMPLE_PRICE;
		sumAmountCommand += getValueFromEditText(editPakopakoSauce) * Constants.PriceOfProduct.PAKOPAKO_SAUCE_PRICE;
		sumAmountCommand += getValueFromEditText(editSkewer)   * Constants.PriceOfProduct.SKEWER_PRICE;
		sumAmountCommand += getValueFromEditText(editChicken)  * Constants.PriceOfProduct.CHICKEN_PRICE;
		sumAmountCommand += getValueFromEditText(editJuice)    * Constants.PriceOfProduct.JUICE_PRICE;
		sumAmountCommand += getValueFromEditText(editFrenchFries);
		sumAmountCommand += juiceSelectedValue;
		sumAmountCommand += frenchFriesSelectedValue;

		clientAmount = getValueFromEditText(editMoney);
		Log.d(Constants.TAG, "clientAmount = " + clientAmount);
		changeAmount = clientAmount - sumAmountCommand;


		 nbrPakopakoDeliver = NumberFormated.formatValue(localDataSource.getTotalNumberPakopakoSimple());
		 nbrSkewerDeliver   = NumberFormated.formatValue(localDataSource.getTotalNumberSkewer());
		 nbrChickenDeliver  = NumberFormated.formatValue(localDataSource.getTotalNumberChicken());
		 nbrJuiceDeliver    = NumberFormated.formatValue(localDataSource.getTotalNumberJuice());
		 frenchFriesDeliver = NumberFormated.formatValue(localDataSource.getTotalAmountFrenchFries());
		 totalSumPayed      = NumberFormated.formatValue(sumAmountCommand);
		 changeAmountFormat = NumberFormated.formatValue(changeAmount);


		nbrValuePakopako.setText(nbrPakopakoDeliver);
		nbrValueSkewer.setText(nbrSkewerDeliver);
		nbrValueChicken.setText(nbrChickenDeliver);
		nbrValueJuice.setText(nbrJuiceDeliver);
		amountFrenchFries.setText(frenchFriesDeliver);
		amount_command.setText(totalSumPayed);
		if(changeAmount < 0){Log.d(Constants.TAG, "error changeAmount = " + changeAmount);}
		else {clientBalance.setText(changeAmountFormat);}
	}

	private void addCommandInDatabase(){

		int pakopakoSimple, pakopakoSauce, skewer, chicken, juice, frenchFries;
		pakopakoSimple = getValueFromEditText(editPakopakoSimple);
		pakopakoSauce  = getValueFromEditText(editPakopakoSauce);
		skewer         = getValueFromEditText(editSkewer);
		chicken        = getValueFromEditText(editChicken);
		juice          = getValueFromEditText(editJuice);
		frenchFries    = getValueFromEditText(editFrenchFries);

		 NbrSimpleBonus = generateBonus(pakopakoSimple);
		 NbrSauceBonus = generateBonus(pakopakoSauce);

		String value = "| pakopako:"+ pakopakoSimple + " skewer:" + skewer + " chicken:" + chicken + " juice:" + juice  + " simpleBonus: " + NbrSimpleBonus + " sauceBonus: "  + NbrSauceBonus ;

		Log.d(Constants.TAG, value);
		Command command = new Command(pakopakoSimple, pakopakoSauce, skewer, chicken, juice, frenchFries, NbrSimpleBonus, NbrSauceBonus);

			try {
				long newCommandInsert = localDataSource.addCommands(command);
				Log.d(Constants.TAG ,"this is a value for bonus "+ command.getpSimpleBonus());

				if (newCommandInsert > 0) {
					Log.d(Constants.TAG , Constants.ADD_SUCCESS + newCommandInsert + value);
					ToastMessage.showToast(this, Constants.ADD_SUCCESS_TOAST);
				}
				else{
					Log.d(Constants.TAG, Constants.ADD_FAILURE + newCommandInsert);

				}
			}
			catch (Exception e) {
				Log.d(Constants.TAG, Constants.ADD_ERROR_TOAST + e.getMessage());
				ToastMessage.showToast(this, Constants.ADD_ERROR_TOAST);
				throw new RuntimeException(e);

			}
	}
	private void cleanAllEditText(){
		editPakopakoSimple.setText("");
		editPakopakoSauce.setText("");
		editSkewer.setText("");
		editChicken.setText("");
		editJuice.setText("");
		editFrenchFries.setText("");
		editMoney.setText("");
		YoYo.with(Techniques.Tada).duration(500).playOn(btn_addData);
	}

	void setupViews(){
		amount_command     = findViewById(R.id.displaySum);
		clientBalance      = findViewById(R.id.clientBalance);
		editPakopakoSimple = findViewById(R.id.editPakopkoSimple);
		editPakopakoSauce  = findViewById(R.id.editPakopkoSauce);
		editSkewer         = findViewById(R.id.editSkewer);
		editChicken        = findViewById(R.id.editChicken);
		editJuice          = findViewById(R.id.editJuice);
		editFrenchFries    = findViewById(R.id.editPFrenchFries);
		editMoney          = findViewById(R.id.editMoney);

		spinner_juices      = findViewById(R.id.spinner_juices);
		spinner_frenchFries = findViewById(R.id.spinner_frenchFries);

		nbrValuePakopako  = findViewById(R.id.nbrPakopako);
		nbrValueSkewer    = findViewById(R.id.nbrSkewer);
		nbrValueChicken   = findViewById(R.id.nbrChicken);
		nbrValueJuice     = findViewById(R.id.nbrJuice);
		amountFrenchFries = findViewById(R.id.frenchFriesPrice);
		btn_floating      = findViewById(R.id.floatingButton);
		btn_addData       = findViewById(R.id.btn_addData);
		header_widget     = findViewById(R.id.header_widget);
		text_ariary       = findViewById(R.id.text_ariary);

		YoYo.with(Techniques.RotateInUpRight).duration(1000).playOn(header_widget);
		YoYo.with(Techniques.RotateIn).duration(1000).playOn(amount_command);
		YoYo.with(Techniques.FadeIn).duration(5000).playOn(text_ariary);
	}

	private void setupSpinnerAdapter(Spinner spinner , int list_data ){
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, list_data, R.layout.spinner_item_selected_color);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
	}


		AdapterView.OnItemSelectedListener spinnerListener = new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
				String selectValue = adapterView.getItemAtPosition(position).toString().trim();
				if(adapterView.getId() == R.id.spinner_juices) {
					juiceSelectedValue = Integer.parseInt(selectValue);

				} else if (adapterView.getId() == R.id.spinner_frenchFries) {
					frenchFriesSelectedValue = Integer.parseInt(selectValue);
					
				}
				calculate_displaySum();

			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		};


	private int generateBonus(int pakopakoType){
		int pNbrBonus = 0;

		if (pakopakoType >= 10 && pakopakoType < 20) pNbrBonus = 1;
		else if (pakopakoType >= 20 && pakopakoType < 30) pNbrBonus = 2;
		else if (pakopakoType >= 30 && pakopakoType < 40) pNbrBonus = 3;
		else if (pakopakoType >= 40 && pakopakoType < 50) pNbrBonus = 4;
		else if (pakopakoType >= 50 && pakopakoType < 60) pNbrBonus = 5;
		else if (pakopakoType >= 60 && pakopakoType < 70) pNbrBonus = 6;
		else if (pakopakoType >= 70 && pakopakoType < 80) pNbrBonus = 7;
		else if (pakopakoType >= 80 && pakopakoType < 90) pNbrBonus = 8;
		else if (pakopakoType >= 90 && pakopakoType < 100) pNbrBonus = 9;
		else if (pakopakoType >= 100) pNbrBonus = 15;
		return pNbrBonus;

	}
}
