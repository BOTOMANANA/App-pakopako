package com.example.tutorialproject.utils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.tutorialproject.R;

public class AlertDialogCustomExpense extends Dialog {
	String title;
	TextView displayNbrPSimba, displayNbrSSimba;
	ImageButton btnPSimbaCounter, btnSSimbaCounter;
	Button btnRegister;
	EditText editNbrPSimba, editNbrSSimba, editExpense;

	@SuppressLint({"ResourceType", "MissingInflatedId"})
	public AlertDialogCustomExpense(Context context) {
		super(context, R.style.RoundedCornerAlertDialog);
		setContentView(R.layout.alert_dialog_custom_expense);
		this.title = "Produits maratra";
		this.displayNbrPSimba = findViewById(R.id.nbrPakopakoSimba);
		this.displayNbrSSimba = findViewById(R.id.nbrSkewerSimba);
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

	public TextView getDisplayNbrPSimba() {
		return displayNbrPSimba;
	}

	public TextView getDisplayNbrSSimba() {
		return displayNbrSSimba;
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
