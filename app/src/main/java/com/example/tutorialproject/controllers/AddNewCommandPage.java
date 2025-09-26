package com.example.tutorialproject.controllers;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
	LocalDataSourceImpl localDataSource;
	TextView text_ariary, amount_command,clientBalance, nbrValuePakopako, nbrValueSkewer, nbrValueChicken, nbrValueJuice;
	Button btn_addData;
	private long backButtonTime;
	Button btn_floating;
	LinearLayout header_widget;

	@SuppressLint("MissingInflatedId")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EdgeToEdge.enable(this);
		setContentView(R.layout.add_new_command_page);

		setupViews();

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
		editSkewer.addTextChangedListener(watcher);
		editChicken.addTextChangedListener(watcher);
		editJuice.addTextChangedListener(watcher);
		editMoney.addTextChangedListener(watcher);

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
		String nbrPakopakoDeliver, nbrSkewerDeliver, nbrChickenDeliver, nbrJuiceDeliver, totalSumPayed, changeAmountFormat;

		sumAmountCommand += getValueFromEditText(editPakopakoSimple) * Constants.PriceOfProduct.PAKOPAKO_SIMPLE_PRICE;
		sumAmountCommand += getValueFromEditText(editSkewer)   * Constants.PriceOfProduct.SKEWER_PRICE;
		sumAmountCommand += getValueFromEditText(editChicken)  * Constants.PriceOfProduct.CHICKEN_PRICE;
		sumAmountCommand += getValueFromEditText(editJuice)    * Constants.PriceOfProduct.JUICE_PRICE;

		clientAmount = getValueFromEditText(editMoney);
		Log.d(Constants.TAG, "clientAmount = " + clientAmount);
		changeAmount = clientAmount - sumAmountCommand;


		 nbrPakopakoDeliver = NumberFormated.formatValue(localDataSource.getTotalNumberPakopakoSimple());
		 nbrSkewerDeliver   = NumberFormated.formatValue(localDataSource.getTotalNumberSkewer());
		 nbrChickenDeliver  = NumberFormated.formatValue(localDataSource.getTotalNumberChicken());
		 nbrJuiceDeliver    = NumberFormated.formatValue(localDataSource.getTotalNumberJuice());
		 totalSumPayed      = NumberFormated.formatValue(sumAmountCommand);
		 changeAmountFormat = NumberFormated.formatValue(changeAmount);


		nbrValuePakopako.setText(nbrPakopakoDeliver);
		nbrValueSkewer.setText(nbrSkewerDeliver);
		nbrValueChicken.setText(nbrChickenDeliver);
		nbrValueJuice.setText(nbrJuiceDeliver);
		amount_command.setText(totalSumPayed);
		if(changeAmount < 0){Log.d(Constants.TAG, "error changeAmount = " + changeAmount);}
		else {clientBalance.setText(changeAmountFormat);}
	}

	private void addCommandInDatabase(){

		int bonus = 0;
		int pakopakoSimple, pakopakoSauce, skewer, chicken, juice, frenchFries;
		pakopakoSimple = getValueFromEditText(editPakopakoSimple);
		pakopakoSauce  = getValueFromEditText(editPakopakoSauce);
		skewer         = getValueFromEditText(editSkewer);
		chicken        = getValueFromEditText(editChicken);
		juice          = getValueFromEditText(editJuice);
		frenchFries    = getValueFromEditText(editFrenchFries);


		 if (pakopakoSimple >= 10 && pakopakoSimple < 20) bonus = 1;
		else if (pakopakoSimple >= 20 && pakopakoSimple < 30) bonus = 2;
		else if (pakopakoSimple >= 30 && pakopakoSimple < 40) bonus = 3;
		else if (pakopakoSimple >= 40 && pakopakoSimple < 50) bonus = 4;
		else if (pakopakoSimple >= 50 && pakopakoSimple < 60) bonus = 5;
		else if (pakopakoSimple >= 60 && pakopakoSimple < 70) bonus = 6;
		else if (pakopakoSimple >= 70 && pakopakoSimple < 80) bonus = 7;
		else if (pakopakoSimple >= 80 && pakopakoSimple < 90) bonus = 8;
		else if (pakopakoSimple >= 90 && pakopakoSimple < 100) bonus = 9;
		else if (pakopakoSimple >= 100) bonus = 15;


		String value = "| pakopako:"+ pakopakoSimple + " skewer:" + skewer + " chicken:" + chicken + " juice:" + juice  + " bonus:" + bonus;

		Command command = new Command(pakopakoSimple, pakopakoSauce, skewer, chicken, juice, frenchFries, bonus);

			try {
				long newCommandInsert = localDataSource.addCommands(command);
				Log.d(Constants.TAG ,"this is a value for bonus "+ command.getBonus_number());

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

		nbrValuePakopako = findViewById(R.id.nbrPakopako);
		nbrValueSkewer   = findViewById(R.id.nbrSkewer);
		nbrValueChicken  = findViewById(R.id.nbrChicken);
		nbrValueJuice    = findViewById(R.id.nbrJuice);
		btn_floating     = findViewById(R.id.floatingButton);
		btn_addData      = findViewById(R.id.btn_addData);
		header_widget    = findViewById(R.id.header_widget);
		text_ariary      = findViewById(R.id.text_ariary);

		YoYo.with(Techniques.RotateInUpRight).duration(1000).playOn(header_widget);
		YoYo.with(Techniques.RotateIn).duration(1000).playOn(amount_command);
		YoYo.with(Techniques.FadeIn).duration(5000).playOn(text_ariary);
	}

}