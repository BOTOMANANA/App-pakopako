package com.example.tutorialproject.utils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.tutorialproject.R;
public class AlertDialogCustomEditText extends Dialog {
	String title;
	Button btn_affirm, btn_annul;
	TextView textTitle;
	EditText edit_password;
	ImageButton btn_showPassword;
	@SuppressLint("MissingInflatedId")
	public AlertDialogCustomEditText(Context context){
		super(context, R.style.RoundedCornerAlertDialog);
		setContentView(R.layout.alert_dialog_affirm_password);
		this.title = "Hi! antHome";
		this.textTitle = findViewById(R.id.alert_title);
		this.edit_password = findViewById(R.id.edit_password);
		this.btn_annul  = findViewById(R.id.btn_alert_annul);
		this.btn_affirm   = findViewById(R.id.btn_alert_affirm);
		this.btn_showPassword = findViewById(R.id.btn_showPassword);

	}

	public void construct(){
		show();
		textTitle.setText(title);
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Button getBtn_affirm() {
		return btn_affirm;
	}
	public Button getBtn_annul() {
		return btn_annul;
	}
	public EditText getEdit_password(){
		return edit_password;
	}
	public ImageButton getBtn_showPassword(){return btn_showPassword;}

}
