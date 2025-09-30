package com.example.tutorialproject.utils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.tutorialproject.R;

public class AlertDialogCustomExpense extends Dialog {
	String title;
	TextView displayNbrPSimba, displayNbrSSimba;
	Button btnPSimbaCounter, btnSSimbaCounter, btnRegister;
	EditText editNbrPSimba, editNbrSSimba;


	@SuppressLint("ResourceType")
	public AlertDialogCustomExpense(@NonNull Context context) {
		super(context, R.layout.alert_dialog_custom_expense);
	}
}
