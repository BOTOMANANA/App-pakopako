package com.example.application_pakopako.utils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


import com.example.application_pakopako.R;

public class AlertDialogCustomExpense extends Dialog {
	String title;
	TextView displayNbrPakopakoSimba, displayNbrSkewerSimba;
	ImageButton btnPSimbaCounter, btnSSimbaCounter;
	Button btnRegister;
	EditText editNbrPSimba, editNbrSSimba, editExpense;

	@SuppressLint({"ResourceType", "MissingInflatedId"})
	public AlertDialogCustomExpense(Context context) {
		super(context, R.style.RoundedCornerAlertDialog);
		setContentView(R.layout.alert_dialog_custom_expense);
		this.title = "Produits maratra";
		this.displayNbrPakopakoSimba = findViewById(R.id.nbrPakopakoSimba);
		this.displayNbrSkewerSimba = findViewById(R.id.nbrSkewerSimba);
		this.btnPSimbaCounter = findViewById(R.id.btnCounterPSimba);
		this.btnSSimbaCounter = findViewById(R.id.btnCounterSkewerSimba);
		this.btnRegister      = findViewById(R.id.btn_register_expense);
		this.editNbrPSimba    = findViewById(R.id.editPSimba);
		this.editNbrSSimba    = findViewById(R.id.editSkewerSimba);
		this.editExpense      = findViewById(R.id.editExpense);
	}

	public String getTitle() {
		return title;
	}

	public TextView getDisplayNbrPakopakoSimba() {
		return displayNbrPakopakoSimba;
	}

	public TextView getDisplayNbrSkewerSimba() {
		return displayNbrSkewerSimba;
	}

	public ImageButton getBtnPSimbaCounter() {
		return btnPSimbaCounter;
	}

	public ImageButton getBtnSSimbaCounter() {
		return btnSSimbaCounter;
	}

	public Button getBtnRegister() {
		return btnRegister;
	}

	public EditText getEditNbrPSimba() {
		return editNbrPSimba;
	}

	public EditText getEditNbrSSimba() {
		return editNbrSSimba;
	}

	public EditText getEditExpense() {
		return editExpense;
	}
}
