package com.example.tutorialproject.controllers;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.example.tutorialproject.R;
import com.example.tutorialproject.constants.Constants;
import com.example.tutorialproject.localData.LocalDataSourceImpl;
import com.example.tutorialproject.utils.AlertDialogCustom;
import com.example.tutorialproject.utils.CounterNumberAnimation;
import com.example.tutorialproject.utils.NumberFormated;

public class ListAllCommandPage extends AppCompatActivity {
	TextView getNbrPakopakoSimple,getNbrPakopakoSauce, getNbrPSimpleBonus, getNbrPSauceBonus, getNbrSkewer, getNbrChicken, getNbrJuice, getNbrSkewerSimba, getNbrPakopakoSimba;
	TextView sumTotalAmountDaily, sumAmountPSimple, sumAmountPSauce, sumAmountSkewer, sumAmountChicken, sumAmountJuice, sumAmountFrenchFries, sumAmountExpense;
	LocalDataSourceImpl localDataSource;
	LottieAnimationView animationView;
	ImageButton btn_goBack;
	Button btn_clearAll;
	@SuppressLint("UseSwitchCompatOrMaterialCode")
	 long  TIME_OF_CLEAR = 3000, TIME_ANIMATION = 6000 ;
	int   TIME_COUNTER = 5200;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EdgeToEdge.enable(this);
		setContentView(R.layout.list_all_command_page);
		localDataSource = new LocalDataSourceImpl(this);

		setupViews();
		processEitherSumProduct();

		btn_goBack.setOnClickListener(v -> startActivity(new Intent(this, AddNewCommandPage.class)));
		btn_clearAll.setOnClickListener(v -> {

			AlertDialogCustom dialogCustom = new AlertDialogCustom(this);
			dialogCustom.setTitle("Suppression des données");
			dialogCustom.setSubTitle("Est-vous sure de supprimer tous les données!");

			dialogCustom.getBtn_cancel().setOnClickListener(view -> dialogCustom.dismiss());
			dialogCustom.getBtn_confirm().setOnClickListener(view -> {
				localDataSource.deleteStoryCommand();
				dialogCustom.dismiss();

				new Handler().postDelayed(() -> {
					startActivity(new Intent(this, AddNewCommandPage.class));
					Toast.makeText(this, "Tous les donnees sont bien supprimées", Toast.LENGTH_SHORT).show();

				}, TIME_OF_CLEAR);

			});
			dialogCustom.setCanceledOnTouchOutside(false);
			dialogCustom.construct();

		});


		OnBackPressedCallback callback = new OnBackPressedCallback(true) {
			@Override
			public void handleOnBackPressed() {
			}
		};
		getOnBackPressedDispatcher().addCallback(this,callback);
		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
			Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
			return insets;
		});

	}
	private void processEitherSumProduct(){
		String amountPricePSimple, amountPricePSauce, amountPriceSkewer, amountPriceChicken, amountPriceJuice,amountFrenchFries;
		String sumNbrPSimple, sumNbrPSauce, sumNbrSkewer, sumNbrChicken, sumNbrJuice, sumNbrPSimpleBonus, sumNbrPSauceBonus ;
		long getSumNbrPSimple,getSumNbrPSauce, getSumNbrSkewer, getSumNbrChicken, getSumNbrJuice,getAmountFrenchFries, getSumNbrPSimpleBonus,getSumNbrPSauceBonus, sumAllMoneyDaily;

		getSumNbrPSimple      = localDataSource.getTotalNumberPakopakoSimple();
		getSumNbrPSauce       = localDataSource.getTotalNumberPakopakoSauce();
		getSumNbrSkewer       = localDataSource.getTotalNumberSkewer();
		getSumNbrChicken      = localDataSource.getTotalNumberChicken();
		getSumNbrJuice        = localDataSource.getTotalNumberJuice();
		getAmountFrenchFries  = localDataSource.getTotalAmountFrenchFries();
		getSumNbrPSimpleBonus = localDataSource.getTotalNbrPSimpleBonus();
		getSumNbrPSauceBonus  = localDataSource.getTotalNbrPSauceBonus();

		Log.d(Constants.TAG , "this is sum of the bonus " + getSumNbrPSimpleBonus);

		sumNbrPSimple = NumberFormated.formatValue(getSumNbrPSimple);
		sumNbrPSauce  = NumberFormated.formatValue(getSumNbrPSauce);
		sumNbrPSimpleBonus = NumberFormated.formatValue(getSumNbrPSimpleBonus);
		sumNbrPSauceBonus  = NumberFormated.formatValue(getSumNbrPSauceBonus);
		sumNbrSkewer  = NumberFormated.formatValue(getSumNbrSkewer);
		sumNbrChicken = NumberFormated.formatValue(getSumNbrChicken);
		sumNbrJuice   = NumberFormated.formatValue(getSumNbrJuice);


		getNbrPakopakoSimple.setText(sumNbrPSimple);
		getNbrPakopakoSauce.setText(sumNbrPSauce);
		getNbrPSimpleBonus.setText(sumNbrPSimpleBonus);
		getNbrPSauceBonus.setText(sumNbrPSauceBonus);
		getNbrSkewer.setText(sumNbrSkewer);
		getNbrChicken.setText(sumNbrChicken);
		getNbrJuice.setText(sumNbrJuice);


		amountPricePSimple = NumberFormated.formatValue(getSumNbrPSimple * Constants.PriceOfProduct.PAKOPAKO_SIMPLE_PRICE);
		amountPricePSauce  = NumberFormated.formatValue(getSumNbrPSauce * Constants.PriceOfProduct.PAKOPAKO_SAUCE_PRICE);
		amountPriceSkewer   = NumberFormated.formatValue(getSumNbrSkewer * Constants.PriceOfProduct.SKEWER_PRICE);
		amountPriceChicken  = NumberFormated.formatValue(getSumNbrChicken * Constants.PriceOfProduct.CHICKEN_PRICE);
		amountPriceJuice    = NumberFormated.formatValue(getSumNbrJuice * Constants.PriceOfProduct.JUICE_PRICE);
		amountFrenchFries   = NumberFormated.formatValue(getAmountFrenchFries);


		sumAllMoneyDaily =( (getSumNbrPSimple * Constants.PriceOfProduct.PAKOPAKO_SIMPLE_PRICE) +
				  (getSumNbrPSauce * Constants.PriceOfProduct.PAKOPAKO_SAUCE_PRICE) +
				  (getSumNbrSkewer * Constants.PriceOfProduct.SKEWER_PRICE) +
				  (getSumNbrChicken * Constants.PriceOfProduct.CHICKEN_PRICE) +
				  (getSumNbrJuice * Constants.PriceOfProduct.JUICE_PRICE)) - localDataSource.getSumAmountExpanse();


		if(sumAllMoneyDaily > 0) playAnimationLottie();

		sumAmountPSimple.setText(amountPricePSimple);
		sumAmountPSauce.setText(amountPricePSauce);
		sumAmountSkewer.setText(amountPriceSkewer);
		sumAmountChicken.setText(amountPriceChicken);
		sumAmountJuice.setText(amountPriceJuice);
		sumAmountFrenchFries.setText(amountFrenchFries);
		getNbrPakopakoSimba.setText(NumberFormated.formatValue(localDataSource.getTotalNumberPakopakoSimba()));
		getNbrSkewerSimba.setText(NumberFormated.formatValue(localDataSource.getTotalNumberSkewerSimba()));
		sumAmountExpense.setText(NumberFormated.formatValue(localDataSource.getSumAmountExpanse()));

		CounterNumberAnimation.counterAnimate(sumTotalAmountDaily ,0 , (int) sumAllMoneyDaily,TIME_COUNTER );

	}
	private void playAnimationLottie(){
		animationView = findViewById(R.id.lottieAnimationView);
		animationView.setVisibility(VISIBLE);
		animationView.playAnimation();
		new Handler().postDelayed(() -> runOnUiThread(() -> animationView.setVisibility(GONE)), TIME_ANIMATION);

	}
	private void setupViews(){
		btn_goBack     = findViewById(R.id.btn_goBack);
		btn_clearAll    = findViewById(R.id.btn_cleanData);

		getNbrPakopakoSimple = findViewById(R.id.getNbrPakopakoSimple);
		getNbrPakopakoSauce  = findViewById(R.id.getNbrPakopakoSauce);
		getNbrPakopakoSimba  = findViewById(R.id.getNbrPakopakoSimba);
		getNbrSkewerSimba    = findViewById(R.id.getNbrSkewerSimba);
		getNbrPSimpleBonus   = findViewById(R.id.getNbrPSimpleBonus);
		getNbrPSauceBonus    = findViewById(R.id.getNbrPSauceBonus);
		getNbrSkewer         = findViewById(R.id.getNbrSkewer);
		getNbrChicken        = findViewById(R.id.getNbrChicken);
		getNbrJuice          = findViewById(R.id.getNbrJuice);

		sumAmountPSimple     = findViewById(R.id.sumAmountPakopakoSimple);
		sumAmountPSauce      = findViewById(R.id.sumAmountPakopakoSauce);
		sumAmountFrenchFries = findViewById(R.id.sumAmountFrenchFries);
		sumTotalAmountDaily  = findViewById(R.id.sumTotalAmountDaily);
		sumAmountSkewer      = findViewById(R.id.sumAmountSkewer);
		sumAmountChicken     = findViewById(R.id.sumAmountChicken);
		sumAmountJuice       = findViewById(R.id.sumAmountJuice);
		sumAmountExpense     = findViewById(R.id.sumAmountOutgo);
	}

}