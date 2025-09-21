package com.example.tutorialproject.utils;

import android.text.InputType;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.tutorialproject.R;

public class ShowPasswordVisible {
	static final boolean[] isVisible = {false};

	public static void putPasswordVisible(EditText editText, ImageButton btn_show){
		btn_show.setOnClickListener(v -> {

			if(isVisible[0]){
				editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
				btn_show.setImageResource(R.drawable.eye_crossed);
				isVisible[0] = false;

			}
			else{
				editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
				btn_show.setImageResource(R.drawable.eye);
				isVisible[0] = true;
			}
			editText.setSelection(editText.getText().length()); // put the cursor in the end of text

		});


	}

}
