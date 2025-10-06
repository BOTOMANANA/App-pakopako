package com.example.tutorialproject.controllers;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
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
import com.example.tutorialproject.R;
import com.example.tutorialproject.constants.Constants;
import com.example.tutorialproject.localData.LocalDataSourceImpl;
import com.example.tutorialproject.models.Command;
import com.example.tutorialproject.models.ProductSimba;
import com.example.tutorialproject.utils.AlertDialogCustomExpense;
import com.example.tutorialproject.utils.BiometricAuthenticator;
import com.example.tutorialproject.utils.NumberFormated;
import com.example.tutorialproject.utils.ToastMessage;

import org.jetbrains.annotations.NotNull;


public class AddNewCommandPage extends AppCompatActivity {
	EditText editPakopakoSimple, editPakopakoSauce, editSkewer, editChicken, editJuice, editOther, editMoney, editQuantityFFries;
	Spinner spinner_frenchFries, spinner_juices;
	LocalDataSourceImpl localDataSource;
	TextView text_ariary, amount_command,clientBalance, nbrValuePakopako, nbrValueSkewer, nbrValueChicken, nbrValueJuice, amountFrenchFries;
	Button btn_addData, btn_floating;
	private long backButtonTime;
	LinearLayout header_widget;
	int NbrSimpleBonus, NbrSauceBonus;
	int juiceBottlePrice = 0;
	private GestureDetector gestureDetector;
	private boolean isFirstSelection = true;
	private boolean ignoreSpinnerEvent = false;
	int  lastSelectedFFriesValue = 0;
	int totalFFries = 0;
	int subtotal = 0;

	@SuppressLint({"MissingInflatedId", "ClickableViewAccessibility"})
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

		componentListener();

		btn_addData.setOnClickListener(v -> {
			ignoreSpinnerEvent = true;
			spinner_frenchFries.setSelection(0);

			addCommandInDatabase();
			cleanAllEditText();

		});

		btn_floating.setOnClickListener(v -> biometric.authenticate());

		gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
			@Override
			public void onLongPress(@NotNull MotionEvent event) {
				showAlertDialogExpense();

			}
		});

		View rootView = findViewById(android.R.id.content);
		rootView.setOnTouchListener((v, event) -> gestureDetector.onTouchEvent(event));

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
	private int calculateSumAmount() {

		int sumAmountCommand = 0;
		sumAmountCommand += getValueFromEditText(editPakopakoSimple) * Constants.PriceOfProduct.PAKOPAKO_SIMPLE_PRICE;
		sumAmountCommand += getValueFromEditText(editPakopakoSauce) * Constants.PriceOfProduct.PAKOPAKO_SAUCE_PRICE;
		sumAmountCommand += getValueFromEditText(editSkewer)   * Constants.PriceOfProduct.SKEWER_PRICE;
		sumAmountCommand += getValueFromEditText(editChicken)  * Constants.PriceOfProduct.CHICKEN_PRICE;
		sumAmountCommand += getValueFromEditText(editJuice)    * Constants.PriceOfProduct.JUICE_PRICE;
		sumAmountCommand += getValueFromEditText(editOther);
		sumAmountCommand += juiceBottlePrice;
		sumAmountCommand += totalFFries;
		return sumAmountCommand;
	}
	@SuppressLint("SetTextI18n")
	private void displayProductQtyAndAmount(){
		int clientAmount = getValueFromEditText(editMoney);
		int sumAmount = calculateSumAmount();
		int changeAmount = clientAmount - sumAmount;

		nbrValuePakopako.setText(NumberFormated.formatValue(localDataSource.getTotalNumberPakopakoSimple() + localDataSource.getTotalNumberPakopakoSauce()));
		nbrValueSkewer.setText(NumberFormated.formatValue(localDataSource.getTotalNumberSkewer()));
		nbrValueChicken.setText(NumberFormated.formatValue(localDataSource.getTotalNumberChicken()));
		nbrValueJuice.setText(NumberFormated.formatValue(localDataSource.getTotalNumberJuice()));
		amountFrenchFries.setText(NumberFormated.formatValue(localDataSource.getTotalAmountFrenchFries()));
		amount_command.setText(NumberFormated.formatValue(sumAmount));

		if (clientAmount < sumAmount || changeAmount < 0) {
			clientBalance.setText("erreur");
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

		NbrSimpleBonus = generateBonus(pSimpleQty);
		NbrSauceBonus = generateBonus(pSauceQty);

		String value = "| pakopako:"+ pSimpleQty + " skewer:" + skewerQty + " chicken:" + chickenQty + " juice:" + juiceQty  + " simpleBonus: " + NbrSimpleBonus + " sauceBonus: "  + NbrSauceBonus ;

		Log.d(Constants.TAG, value);
		Command command = new Command(
				  pSimpleQty,
				  pSauceQty,
				  skewerQty,
				  chickenQty,
				  juiceQty,
				  juiceBottlePrice,
				  totalFFries,
				  otherAmount,
				  NbrSimpleBonus,
				  NbrSauceBonus);

			try {
				long newCommandInsert = localDataSource.addCommands(command);
				Log.d(Constants.TAG ,"this is a value for bonus "+ command.getpSimpleBonus());
				Log.d(Constants.TAG , Constants.ADD_SUCCESS + newCommandInsert + value);

				if (newCommandInsert > 0) ToastMessage.showToast(this, Constants.ADD_SUCCESS_TOAST);
				else Log.d(Constants.TAG, Constants.ADD_FAILURE + newCommandInsert);

			}
			catch (Exception e) {
				Log.d(Constants.TAG, Constants.ADD_ERROR_TOAST + e.getMessage());
				ToastMessage.showToast(this, Constants.ADD_ERROR_TOAST);
				throw new RuntimeException(e);

			}
	}
	private void cleanAllEditText(){
		editQuantityFFries.setVisibility(GONE);
		editOther.setVisibility(VISIBLE);
		editPakopakoSimple.setText("");
		editPakopakoSauce.setText("");
		editSkewer.setText("");
		editChicken.setText("");
		editJuice.setText("");
		editOther.setText("");
		editMoney.setText("");
		editQuantityFFries.setText("");

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
		editOther          = findViewById(R.id.editOther);
		editMoney          = findViewById(R.id.editMoney);
		editQuantityFFries = findViewById(R.id.editQuantityFFries);

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
	private void setupSpinnerAdapter(Spinner spinner, int list_data ){
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, list_data, R.layout.spinner_item_selected_color);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
	}
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
	private void componentListener() {

		TextWatcher watcher = new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {displayProductQtyAndAmount();}
			@Override
			public void afterTextChanged(Editable s) {}
		};
		AdapterView.OnItemSelectedListener spinnerListener = new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

				if(ignoreSpinnerEvent) {
					ignoreSpinnerEvent = false;
					return;
				}

				String selectValue = adapterView.getItemAtPosition(position).toString().trim();

				if (adapterView.getId() == R.id.spinner_juices) {

					Log.d("DEBUG", selectValue);
					if(selectValue.equals("Bouteille")) juiceBottlePrice = 0;
					if(selectValue.equals("1 Litre")) juiceBottlePrice = 3000;
					if(selectValue.equals("1.5 Litres")) juiceBottlePrice = 5000;
					if(selectValue.equals("2 Litres")) juiceBottlePrice = 6000;

				}
				else if (adapterView.getId() == R.id.spinner_frenchFries) {

					if(selectValue.equals("P-frite")){
						lastSelectedFFriesValue = 0;
						editOther.setVisibility(VISIBLE);
						editQuantityFFries.setVisibility(GONE);

					}
					if(selectValue.equals("1500 ar")) {
						lastSelectedFFriesValue = 1500;
						editOther.setVisibility(GONE);
						editQuantityFFries.setVisibility(VISIBLE);

					};

					if(selectValue.equals("2000 ar")){
						lastSelectedFFriesValue = 2000;
						editOther.setVisibility(GONE);
						editQuantityFFries.setVisibility(VISIBLE);
					}
					if(selectValue.equals("3000 ar")) lastSelectedFFriesValue = 3000;

				}
				displayProductQtyAndAmount();
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) { }
		};

		editQuantityFFries.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			@Override
			public void afterTextChanged(Editable s) {
				String qtyText = s.toString().trim();

				if (qtyText.isEmpty() || lastSelectedFFriesValue == 0) return;

				int multiplyValue = Integer.parseInt(qtyText);

				// On calcule uniquement la dernière sélection * quantité
				 subtotal = lastSelectedFFriesValue * multiplyValue;

				// On ajoute au total
				totalFFries += subtotal;
				displayProductQtyAndAmount();


				Log.d("DEBUG", "Sous-total ajouté : " + subtotal);
				Log.d("DEBUG", "Total frites actuel : " + totalFFries);

			}
		});


		componentListenerImp(watcher, spinnerListener);
		displayProductQtyAndAmount();

	}
	private void componentListenerImp(TextWatcher watcher, AdapterView.OnItemSelectedListener spinnerListener){
		editPakopakoSimple.addTextChangedListener(watcher);
		editPakopakoSauce.addTextChangedListener(watcher);
		editSkewer.addTextChangedListener(watcher);
		editChicken.addTextChangedListener(watcher);
		editJuice.addTextChangedListener(watcher);
		editOther.addTextChangedListener(watcher);
		editMoney.addTextChangedListener(watcher);

		spinner_juices.setOnItemSelectedListener(spinnerListener);
		spinner_frenchFries.setOnItemSelectedListener(spinnerListener);

	}
	private void showAlertDialogExpense(){
		AlertDialogCustomExpense dialog = new AlertDialogCustomExpense(this);

		final int[] counterPSimba = {0};
		final int[] counterSSimba = {0};

		String nbrPSimba, nbrSSimba;

		nbrPSimba = String.valueOf(localDataSource.getTotalNumberPakopakoSimba());
		nbrSSimba = String.valueOf(localDataSource.getTotalNumberSkewerSimba());

		dialog.getDisplayNbrPSimba().setText(nbrPSimba);
		dialog.getDisplayNbrSSimba().setText(nbrSSimba);

		dialog.getBtnPSimbaCounter().setOnClickListener(v -> {
			counterPSimba[0] ++;
			dialog.getEditNbrPSimba().setText(String.valueOf(counterPSimba[0]));
			dialog.getDisplayNbrPSimba().setText(String.valueOf(counterPSimba[0]));

		});

		dialog.getBtnSSimbaCounter().setOnClickListener(v -> {
			counterSSimba[0] ++;

			long counter = localDataSource.getTotalNumberPakopakoSimba() + 1;  // think in the logic of my code

			dialog.getEditNbrSSimba().setText(String.valueOf(counterSSimba[0]));
			dialog.getDisplayNbrSSimba().setText(String.valueOf(counterSSimba[0]));

		});

		dialog.getBtnRegister().setOnClickListener(v -> {
			int NbrPakopakoSimba, NbrSkewerSimba, expenseValue;

			String editPakopakoSimba = dialog.getEditNbrPSimba().getText().toString().trim();

			if (editPakopakoSimba.isEmpty()) {
				NbrPakopakoSimba = counterPSimba[0];
			}
			else {
				NbrPakopakoSimba = Integer.parseInt(editPakopakoSimba);
			}

			String editSkewerSimba = dialog.getEditNbrSSimba().getText().toString().trim();
			if (editSkewerSimba.isEmpty()) {
				NbrSkewerSimba = counterSSimba[0];
			}
			else {
				NbrSkewerSimba = Integer.parseInt(editSkewerSimba);
			}

			 String editExpenseValue = dialog.getEditExpense().getText().toString().trim();
			 expenseValue = editExpenseValue.isEmpty() ? 0 : Integer.parseInt(editExpenseValue);

			ProductSimba productSimba = new ProductSimba(NbrPakopakoSimba, NbrSkewerSimba, expenseValue);
			 long isSuccess = localDataSource.insertProductSimba(productSimba);

			if(isSuccess != -1){
				dialog.getDisplayNbrPSimba().setText(nbrPSimba);
				dialog.getDisplayNbrSSimba().setText(nbrSSimba);
				Log.d(Constants.ADD_SUCCESS, "Enregistrement avec success" + isSuccess);

			}
			else {
				Log.d(Constants.ADD_SUCCESS, "Enregistrement avec Erreur" + isSuccess);
			}
			new Handler().postDelayed(dialog::dismiss,800);

		});
		dialog.show();
		dialog.setCanceledOnTouchOutside(true);

	}

}
