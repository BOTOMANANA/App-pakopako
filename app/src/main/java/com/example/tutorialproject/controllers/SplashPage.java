package com.example.tutorialproject.controllers;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.tutorialproject.R;
import com.example.tutorialproject.utils.CounterNumberAnimation;


public class SplashPage extends AppCompatActivity {
	Button btn_getStarted;

	@SuppressLint("MissingInflatedId")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EdgeToEdge.enable(this);
		setContentView(R.layout.splash_screen_page);
		btn_getStarted = findViewById(R.id.btn_getStarted);
		btn_getStarted.setOnClickListener(v -> {
			YoYo.with(Techniques.Wave).duration(1000).playOn(btn_getStarted);
			new Handler().postDelayed(() -> {
				startActivity(new Intent(this, AddNewCommandPage.class));

			}, 900);
		});


		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
			Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
			return insets;
		});
	}
}