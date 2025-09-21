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
	TextView getNbrPakopako, getNbrSkewer, getNbrChicken, getNbrJuice ,getNbrBonus;
	TextView sumTotalAmountDaily, sumAmountPakopako, sumAmountSkewer, sumAmountChicken, sumAmountJuice;
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

		btn_goBack     = findViewById(R.id.btn_goBack);
		btn_clearAll    = findViewById(R.id.btn_cleanData);

		getNbrBonus    = findViewById(R.id.getNbrPakopakoBonus);
		getNbrPakopako = findViewById(R.id.getNbrPakopako);
		getNbrSkewer   = findViewById(R.id.getNbrSkewer);
		getNbrChicken  = findViewById(R.id.getNbrChicken);
		getNbrJuice    = findViewById(R.id.getNbrJuice);

		sumTotalAmountDaily = findViewById(R.id.sumTotalAmountDaily);
		sumAmountPakopako   = findViewById(R.id.sumAmountPakopako);
		sumAmountSkewer     = findViewById(R.id.sumAmountSkewer);
		sumAmountChicken    = findViewById(R.id.sumAmountChicken);
		sumAmountJuice      = findViewById(R.id.sumAmountJuice);



		processEitherSumPieces();

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
	private void processEitherSumPieces(){
		String amountPricePakopako, amountPriceSkewer, amountPriceChicken, amountPriceJuice;
		String sumNbrPakopako, sumNbrSkewer, sumNbrChicken, sumNbrJuice, sumNbrBonus ;
		long getSumNbrPakopako , getSumNbrSkewer, getSumNbrChicken, getSumNbrJuice, getSumNbrBonus, sumAllMoneyDaily;

		getSumNbrPakopako = localDataSource.getTotalNumberPakopako();
		getSumNbrSkewer   = localDataSource.getTotalNumberSkewer();
		getSumNbrChicken  = localDataSource.getTotalNumberChicken();
		getSumNbrJuice    = localDataSource.getTotalNumberJuice();
		getSumNbrBonus    = localDataSource.getTotalBonus();

		Log.d(Constants.TAG , "this is sum of the bonus " + getSumNbrBonus);

		sumNbrPakopako = NumberFormated.formatValue(getSumNbrPakopako);
		sumNbrSkewer   = NumberFormated.formatValue(getSumNbrSkewer);
		sumNbrChicken  = NumberFormated.formatValue(getSumNbrChicken);
		sumNbrJuice    = NumberFormated.formatValue(getSumNbrJuice);
		sumNbrBonus    = NumberFormated.formatValue(getSumNbrBonus);

		getNbrPakopako.setText(sumNbrPakopako);
		getNbrSkewer.setText(sumNbrSkewer);
		getNbrChicken.setText(sumNbrChicken);
		getNbrJuice.setText(sumNbrJuice);
		getNbrBonus.setText(sumNbrBonus);

		amountPricePakopako = NumberFormated.formatValue(getSumNbrPakopako * Constants.PAKOPAKO_PRICE);
		amountPriceSkewer   = NumberFormated.formatValue(getSumNbrSkewer * Constants.SKEWER_PRICE);
		amountPriceChicken  = NumberFormated.formatValue(getSumNbrChicken * Constants.CHICKEN_PRICE);
		amountPriceJuice    = NumberFormated.formatValue(getSumNbrJuice * Constants.JUICE_PRICE);

		sumAllMoneyDaily = (getSumNbrPakopako * Constants.PAKOPAKO_PRICE) + (getSumNbrSkewer * Constants.SKEWER_PRICE) +
				           (getSumNbrChicken * Constants.CHICKEN_PRICE) + (getSumNbrJuice * Constants.JUICE_PRICE);

		if(sumAllMoneyDaily > 0) playAnimationLottie();

		sumAmountPakopako.setText(amountPricePakopako);
		sumAmountSkewer.setText(amountPriceSkewer);
		sumAmountChicken.setText(amountPriceChicken);
		sumAmountJuice.setText(amountPriceJuice);
		CounterNumberAnimation.counterAnimate(sumTotalAmountDaily ,0 , (int) sumAllMoneyDaily,TIME_COUNTER );

	}
	private void playAnimationLottie(){
		animationView = findViewById(R.id.lottieAnimationView);
		animationView.setVisibility(VISIBLE);
		animationView.playAnimation();
		new Handler().postDelayed(() -> runOnUiThread(() -> animationView.setVisibility(GONE)), TIME_ANIMATION);

	}

}