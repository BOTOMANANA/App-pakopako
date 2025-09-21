package com.example.tutorialproject.utils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.TextView;

import com.example.tutorialproject.R;

public class AlertDialogCustom extends Dialog {
	String title, subTitle;
	Button btn_confirm, btn_cancel;
	TextView textTitle, textSubTitle;
	@SuppressLint("MissingInflatedId")
	public AlertDialogCustom(Context context){
		super(context, R.style.RoundedCornerAlertDialog);
		setContentView(R.layout.alert_dialog_custom);
		this.title = "Hi! antHome";
		this.subTitle = "How can I help you";
		this.textTitle = findViewById(R.id.alert_title);
		this.textSubTitle = findViewById(R.id.alert_subTitle);
		this.btn_confirm  = findViewById(R.id.alert_confirm);
		this.btn_cancel   = findViewById(R.id.alert_cancel);

	}

	public void construct(){
		show();
		textTitle.setText(title);
		textSubTitle.setText(subTitle);

	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public Button getBtn_cancel() {
		return btn_cancel;
	}

	public Button getBtn_confirm() {
		return btn_confirm;
	}
}

