package com.example.tutorialproject.utils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import com.example.tutorialproject.R;

public class AlertDialogQuantity extends Dialog {
	String title;
	EditText editQuantity;
	Button btnCheckQuantity;
	@SuppressLint("MissingInflatedId")
	public AlertDialogQuantity(AdapterView.OnItemSelectedListener context) {
		super((Context) context, R.style.RoundedCornerAlertDialog);
		setContentView(R.layout.quantity_of_client);

		this.title = "Quantite de produits";
		this.editQuantity = findViewById(R.id.editQuantity);
		this.btnCheckQuantity = findViewById(R.id.btn_quantity);
	}

	public String getTitle() {
		return title;
	}

	public EditText getEditQuantity() {
		return editQuantity;
	}

	public Button getBtnCheckQuantity() {
		return btnCheckQuantity;
	}
}
