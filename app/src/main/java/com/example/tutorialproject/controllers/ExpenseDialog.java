package com.example.tutorialproject.controllers;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.example.tutorialproject.constants.Constants;
import com.example.tutorialproject.localData.LocalDataSourceImpl;
import com.example.tutorialproject.models.ProductSimba;
import com.example.tutorialproject.utils.AlertDialogCustomExpense;
import com.example.tutorialproject.utils.ToastMessage;

public class ExpenseDialog {
	public static void showAddExpenseDialog(Context context, LocalDataSourceImpl dataSource) {

		AlertDialogCustomExpense dialog = new AlertDialogCustomExpense(context);

		final int[] counterPakopakoSimba = {0};
		final int[] counterSkewerSimba = {0};
		String numberPakopakoSimba, numberSkewerSimba;

		numberPakopakoSimba = String.valueOf(dataSource.getTotalNumberPakopakoSimba());
		numberSkewerSimba   = String.valueOf(dataSource.getTotalNumberSkewerSimba());
		
		dialog.getDisplayNbrPakopakoSimba().setText(numberPakopakoSimba);
		dialog.getDisplayNbrSkewerSimba().setText(numberSkewerSimba);

		dialog.getBtnPSimbaCounter().setOnClickListener(v -> {
			counterPakopakoSimba[0] ++;
			dialog.getEditNbrPSimba().setText(String.valueOf(counterPakopakoSimba[0]));
			dialog.getDisplayNbrPakopakoSimba().setText(String.valueOf(counterPakopakoSimba[0]));
		});

		dialog.getBtnSSimbaCounter().setOnClickListener(v -> {
			counterSkewerSimba[0] ++;
			dialog.getEditNbrSSimba().setText(String.valueOf(counterSkewerSimba[0]));
			dialog.getDisplayNbrSkewerSimba().setText(String.valueOf(counterSkewerSimba[0]));
		});

		dialog.getBtnRegister().setOnClickListener(v -> {
			int NbrPakopakoSimba, NbrSkewerSimba, expenseValue;

			String editPakopakoSimba = dialog.getEditNbrPSimba().getText().toString().trim();

			if (editPakopakoSimba.isEmpty()) {
				NbrPakopakoSimba = counterPakopakoSimba[0];
			}
			else NbrPakopakoSimba = Integer.parseInt(editPakopakoSimba);


			String editSkewerSimba = dialog.getEditNbrSSimba().getText().toString().trim();
			if (editSkewerSimba.isEmpty()) {
				NbrSkewerSimba = counterSkewerSimba[0];
			}
			else {
				NbrSkewerSimba = Integer.parseInt(editSkewerSimba);
			}

			String editExpenseValue = dialog.getEditExpense().getText().toString().trim();
			expenseValue = editExpenseValue.isEmpty() ? 0 : Integer.parseInt(editExpenseValue);

			ProductSimba productSimba = new ProductSimba(NbrPakopakoSimba, NbrSkewerSimba, expenseValue);
			long insertWithSuccess = dataSource.insertProductSimba(productSimba);

			if (insertWithSuccess != -1) {
				dialog.getDisplayNbrPakopakoSimba().setText(numberPakopakoSimba);
				dialog.getDisplayNbrSkewerSimba().setText(numberSkewerSimba);
				ToastMessage.showToast(context, "Enregistrement avec success");
				Log.d(Constants.TAG, "Enregistrement avec success" + insertWithSuccess);

			}
			else {
				Log.d(Constants.TAG, "Enregistrement avec Erreur" + insertWithSuccess);
			}
			new Handler().postDelayed(dialog::dismiss,800);

		});
		dialog.show();
		dialog.setCanceledOnTouchOutside(true);
	}
}
