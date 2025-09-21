package com.example.tutorialproject.utils;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.example.tutorialproject.constants.Constants;
import com.example.tutorialproject.controllers.ListAllCommandPage;

import java.util.concurrent.Executor;

public class BiometricAuthenticator {

	private final Context context;
	private final Executor executor;
	public BiometricAuthenticator(Context context) {
		this.context = context;
		this.executor = ContextCompat.getMainExecutor(context);

	}
	public void authenticate() {
		if (!isBiometricAvailable()) {
			Toast.makeText(context, "Empreinte non disponible", Toast.LENGTH_SHORT).show();

		}

		BiometricPrompt biometricPrompt = new BiometricPrompt(
				  (androidx.fragment.app.FragmentActivity) context,
				  executor,
				  new BiometricPrompt.AuthenticationCallback() {
					  @Override
					  public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
						  super.onAuthenticationSucceeded(result);
						  context.startActivity(new Intent(context, ListAllCommandPage.class)
);
					  }

					  @Override
					  public void onAuthenticationFailed() {
						  super.onAuthenticationFailed();
					  }

					  @Override
					  public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
						  super.onAuthenticationError(errorCode, errString);
						   processDialogPassword();

					  }
				  });

		BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
				  .setTitle(Constants.FINGERPRINT_TITLE)
				  .setSubtitle(Constants.FINGERPRINT_SUBTITLE)
				  .setNegativeButtonText(Constants.FINGERPRINT_BUTTON)
				  .setConfirmationRequired(true)
				  .build();

		biometricPrompt.authenticate(promptInfo);

	}

	private boolean isBiometricAvailable() {
		BiometricManager biometricManager = BiometricManager.from(context);
		int result = biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG);
		return result == BiometricManager.BIOMETRIC_SUCCESS;
	}


	private void processDialogPassword(){
		int VIBRATE_DURATION = 500;
		AlertDialogCustomEditText dialog = new AlertDialogCustomEditText(context);

		dialog.setTitle(Constants.TEXT_PASSWORD);
		dialog.getBtn_annul().setOnClickListener(v -> dialog.dismiss());
		ShowPasswordVisible.putPasswordVisible(dialog.getEdit_password(),dialog.getBtn_showPassword());
		dialog.getBtn_affirm().setOnClickListener(v -> {
			String value_password = dialog.getEdit_password().getText().toString().trim();

			if(TextUtils.isEmpty(value_password)){
				dialog.getEdit_password().setError(Constants.EMPTY_ERROR_EDITTEXT);
				VibrationPasswordError.vibrateError(context, VIBRATE_DURATION);
			}
			else if (!TextUtils.isEmpty(value_password) && value_password.equals(Constants.USER_PASSWORD) ) {
				context.startActivity(new Intent(context, ListAllCommandPage.class));
			}

			else {
				dialog.getEdit_password().setError(Constants.INCORRECT_PASSWORD);
				VibrationPasswordError.vibrateError(context, VIBRATE_DURATION);
			}

		});
		dialog.construct();
		dialog.setCanceledOnTouchOutside(false);

	}

}
