package com.example.application_pakopako.controllers;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
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
import com.example.application_pakopako.R;
import com.example.application_pakopako.constants.Constants;
import com.example.application_pakopako.error.LogException;
import com.example.application_pakopako.localData.LocalDataSourceImpl;
import com.example.application_pakopako.utils.BiometricAuthenticator;
import com.example.application_pakopako.utils.NumberFormated;

import org.jetbrains.annotations.NotNull;

public class AddNewCommandPage extends AppCompatActivity {
	EditText editPakopakoSimple, editPakopakoSauce, editSkewer, editChicken, editJuice, editOther, editMoney, editQuantityFFries;
	Spinner spinnerFrenchFries, spinnerJuicesBottle;
	LocalDataSourceImpl localDataSource;
	TextView ariaryText, amountCommand,clientBalance, numberPakopako, numberSkewer, numberChicken, numberJuice, amountFrenchFries;
	Button buttonAddData, buttonShowFingerPrint;
	private long backButtonTime;
	LinearLayout headerWidget;
	private GestureDetector gestureDetector;
	private boolean ignoreSpinnerEvent = false;
	int lastSelectedFrenchFriesPrice = 0, lastSelectedJuiceBottlePrice = 0;
	int subtotalFrenchFries = 0, sumFrenchFries = 0;

	@SuppressLint({"MissingInflatedId", "ClickableViewAccessibility"})
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EdgeToEdge.enable(this);
		setContentView(R.layout.add_new_command_page);
		setupViews();
		localDataSource = new LocalDataSourceImpl(this);
		BiometricAuthenticator biometric = new BiometricAuthenticator(this);

		setupSpinnerAdapter(spinnerJuicesBottle, R.array.JuiceType);
		setupSpinnerAdapter(spinnerFrenchFries, R.array.frenchFriesType);
		generateEditTextListener();
		generateSpinnerListenerAndTakeValue();

		buttonAddData.setOnClickListener(v -> {
			addCommandInDatabase();
			clearEditTextAndRenderInitial();
		});
		buttonShowFingerPrint.setOnClickListener(v -> biometric.authenticate());

		gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
			@Override
			public void onLongPress(@NotNull MotionEvent event) {
				ExpenseDialog.showAddExpenseDialog(AddNewCommandPage.this, localDataSource);

			}
		});

		View rootView = findViewById(android.R.id.content);
		rootView.setOnTouchListener((v, event) -> gestureDetector.onTouchEvent(event));

		getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
			@Override
			public void handleOnBackPressed() {
				int ON_TAP_DURATION = 2000;
				if (backButtonTime + ON_TAP_DURATION > System.currentTimeMillis()) finishAffinity();
				else backButtonTime = System.currentTimeMillis();
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
		if(value.isEmpty()) {
			return 0;
		}
		try {
			return Integer.parseInt(value);
		}
		catch (NumberFormatException error) {
			LogException.logNumberFormatError(error);
			return 0;
		}
	}
	void setupViews(){
		amountCommand = findViewById(R.id.displaySum);
		clientBalance = findViewById(R.id.clientBalance);
		editPakopakoSimple = findViewById(R.id.editPakopkoSimple);
		editPakopakoSauce = findViewById(R.id.editPakopkoSauce);
		editSkewer = findViewById(R.id.editSkewer);
		editChicken = findViewById(R.id.editChicken);
		editJuice = findViewById(R.id.editJuice);
		editOther = findViewById(R.id.editOther);
		editMoney = findViewById(R.id.editMoney);
		editQuantityFFries = findViewById(R.id.editQuantityFFries);

		spinnerJuicesBottle = findViewById(R.id.spinner_juices);
		spinnerFrenchFries = findViewById(R.id.spinner_frenchFries);

		numberPakopako = findViewById(R.id.nbrPakopako);
		numberSkewer = findViewById(R.id.nbrSkewer);
		numberChicken = findViewById(R.id.nbrChicken);
		numberJuice = findViewById(R.id.nbrJuice);
		amountFrenchFries = findViewById(R.id.frenchFriesPrice);
		buttonShowFingerPrint = findViewById(R.id.floatingButton);
		buttonAddData = findViewById(R.id.btn_addData);
		headerWidget = findViewById(R.id.header_widget);
		ariaryText = findViewById(R.id.text_ariary);
		animateHeaderViews();
	}
	private void animateHeaderViews() {
		YoYo.with(Techniques.RotateInUpRight).duration(1000).playOn(headerWidget);
		YoYo.with(Techniques.RotateIn).duration(1000).playOn(amountCommand);
		YoYo.with(Techniques.FadeIn).duration(5000).playOn(ariaryText);
	}
	private void setupSpinnerAdapter(Spinner spinner, int dataList) {
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, dataList, R.layout.spinner_item_selected_color);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
	}
	private void generateSpinnerListenerAndTakeValue() {
		AdapterView.OnItemSelectedListener spinnerListener = new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
				if(ignoreSpinnerEvent) {
					ignoreSpinnerEvent = false;
					return;
				}

				String selectValue = adapterView.getItemAtPosition(position).toString().trim();
				if(adapterView.getId() == R.id.spinner_juices) juiceBottleSelection(selectValue);
				else if(adapterView.getId() == R.id.spinner_frenchFries) frenchFriesSelection(selectValue);
				displayProductQtyAndAmount();
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) { }
		};
		spinnerJuicesBottle.setOnItemSelectedListener(spinnerListener);
		spinnerFrenchFries.setOnItemSelectedListener(spinnerListener);
		getQuantityAndMultiplyWithSelectedValue();
		displayProductQtyAndAmount();
	}
	private void juiceBottleSelection(String literSelect){
		switch (literSelect) {
			case "Bouteille":
				lastSelectedJuiceBottlePrice = 0;
				break;
			case "1 Litre":
				lastSelectedJuiceBottlePrice = 3000;
				break;
			case "1.5 Litres":
				lastSelectedJuiceBottlePrice = 5000;
				break;
			case "2 Litres":
				lastSelectedJuiceBottlePrice = 6000;
				break;
		}
	}
	private  void frenchFriesSelection(String priceSelect){
		switch (priceSelect) {
			case "P-frite":
				lastSelectedFrenchFriesPrice = 0;
				editQuantityFFries.setText("");
				break;
			case "1500 ar":
				lastSelectedFrenchFriesPrice = 1500;
				editQuantityFFries.setText("");
				break;
			case "2000 ar":
				lastSelectedFrenchFriesPrice = 2000;
				editQuantityFFries.setText("");
				break;
			case "3000 ar":
				lastSelectedFrenchFriesPrice = 3000;
				editQuantityFFries.setText("");
				break;
		}
	}
	private void getQuantityAndMultiplyWithSelectedValue(){
		editQuantityFFries.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			@Override
			public void afterTextChanged(Editable userInput) {
				String quantity = userInput.toString().trim();
				if (quantity.isEmpty() || lastSelectedFrenchFriesPrice == 0) return;
				int parseQuantity = Integer.parseInt(quantity);

				subtotalFrenchFries = lastSelectedFrenchFriesPrice * parseQuantity;
				sumFrenchFries += subtotalFrenchFries;
				displayProductQtyAndAmount();
				Log.d(Constants.TAG,"====???"+ subtotalFrenchFries);

				Log.d("DEBUG", "Sous-total add : " + subtotalFrenchFries);
				Log.d("DEBUG", "current total : " + sumFrenchFries);

			}
		});

	}
	private void generateEditTextListener(){
		TextWatcher watcher = new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {displayProductQtyAndAmount();}
			@Override
			public void afterTextChanged(Editable s) {}
		};

		editPakopakoSimple.addTextChangedListener(watcher);
		editPakopakoSauce.addTextChangedListener(watcher);
		editSkewer.addTextChangedListener(watcher);
		editChicken.addTextChangedListener(watcher);
		editJuice.addTextChangedListener(watcher);
		editOther.addTextChangedListener(watcher);
		editMoney.addTextChangedListener(watcher);

		displayProductQtyAndAmount();

	}
	@SuppressLint("SetTextI18n")
	private int calculateSumAmount() {
		int sumAmountCommand = 0;
		sumAmountCommand += getValueFromEditText(editPakopakoSimple) * Constants.PriceOfProduct.PAKOPAKO_SIMPLE_PRICE;
		sumAmountCommand += getValueFromEditText(editPakopakoSauce) * Constants.PriceOfProduct.PAKOPAKO_SAUCE_PRICE;
		sumAmountCommand += getValueFromEditText(editSkewer)   * Constants.PriceOfProduct.SKEWER_PRICE;
		sumAmountCommand += getValueFromEditText(editChicken)  * Constants.PriceOfProduct.CHICKEN_PRICE;
		sumAmountCommand += getValueFromEditText(editJuice)    * Constants.PriceOfProduct.JUICE_PRICE;
		sumAmountCommand += getValueFromEditText(editOther);
		sumAmountCommand += lastSelectedJuiceBottlePrice;
		sumAmountCommand += sumFrenchFries;
		return sumAmountCommand;
	}
	@SuppressLint("SetTextI18n")
	private void displayProductQtyAndAmount(){
		int clientAmount = getValueFromEditText(editMoney);
		int sumAmount = calculateSumAmount();
		int changeAmount = clientAmount - sumAmount;

		numberPakopako.setText(NumberFormated.formatValue(
				  localDataSource.getTotalNumberPakopakoSimple()
					 + localDataSource.getTotalNumberPakopakoSauce())
		);
		numberSkewer.setText(NumberFormated.formatValue(localDataSource.getTotalNumberSkewer()));
		numberChicken.setText(NumberFormated.formatValue(localDataSource.getTotalNumberChicken()));
		numberJuice.setText(NumberFormated.formatValue(localDataSource.getTotalNumberJuice()));
		amountFrenchFries.setText(NumberFormated.formatValue(localDataSource.getTotalAmountFrenchFries()));
		amountCommand.setText(NumberFormated.formatValue(sumAmount));

		if (clientAmount < sumAmount || changeAmount < 0) {
			clientBalance.setText(NumberFormated.formatValue(-clientAmount));
		}
		else {
			clientBalance.setText(NumberFormated.formatValue(changeAmount));
		}
	}
	private void addCommandInDatabase(){
		int pSimpleQty, pSauceQty, skewerQty, chickenQty, juiceQty, otherAmount;
		pSimpleQty = getValueFromEditText(editPakopakoSimple);
		pSauceQty  = getValueFromEditText(editPakopakoSauce);
		skewerQty  = getValueFromEditText(editSkewer);
		chickenQty = getValueFromEditText(editChicken);
		otherAmount = getValueFromEditText(editOther);
		juiceQty   = getValueFromEditText(editJuice);

		commandRepository clientCommand = new commandRepository(this, localDataSource);
		clientCommand.createAndSaveCommand(pSimpleQty,
				  pSauceQty,
				  skewerQty,
				  chickenQty,
				  juiceQty,
				  lastSelectedJuiceBottlePrice,
				  sumFrenchFries,
				  otherAmount);
	}
	private void clearEditTextAndRenderInitial() {
		editPakopakoSimple.setText("");
		editPakopakoSauce.setText("");
		editSkewer.setText("");
		editChicken.setText("");
		editJuice.setText("");
		editOther.setText("");
		editMoney.setText("");
		editQuantityFFries.setText("");
		amountCommand.setText("0");
		subtotalFrenchFries = 0;
		sumFrenchFries = 0;
		lastSelectedJuiceBottlePrice = 0;
		lastSelectedFrenchFriesPrice = 0;
		YoYo.with(Techniques.Tada).duration(500).playOn(buttonAddData);
		renderSpinnerInFirstPosition();
	}
	private void renderSpinnerInFirstPosition() {
		ignoreSpinnerEvent = true;
		spinnerJuicesBottle.setSelection(0);
		spinnerFrenchFries.setSelection(0);
	}

}
