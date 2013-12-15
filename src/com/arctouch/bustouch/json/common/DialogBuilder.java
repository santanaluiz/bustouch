package com.arctouch.bustouch.json.common;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface.OnClickListener;

public class DialogBuilder {
	
	protected static ProgressDialog PROGRESS_DIAG = null;
	
	/**
	 * Show message dialog
	 * 
	 * @param message
	 * @param title
	 * @param activity
	 * @return Dialog
	 */
	public static Dialog createMessageDialog(String message, String title, Activity activity) {
		Dialog diag = null;
		
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setNeutralButton("OK", null);
		
		diag = builder.create();
		
		return diag;
	}
	
	/**
	 * Mostra dialog com mensagem/titulo na acitivty passada por parametro
	 * e os botoes de OK e Cancelar
	 * 
	 * @param message
	 * @param title
	 * @param activity
	 * @param clickListener
	 * 		parametro para o momento em q o usuário clica em OK
	 * @return Dialog
	 */
	public static Dialog createConfirmDialog(String message, String title, Activity activity, OnClickListener clickListener) {
		Dialog diag = null;
		
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setNeutralButton("OK", clickListener);
		builder.setNegativeButton("Cancelar", null);
		
		diag = builder.create();
		
		return diag;
	}
	
	/**
	 * Method to override onCreateDialog 
	 * 
	 * @param errorNumber
	 * @param activity
	 * @return
	 */
	public static Dialog onCreateDialogOverride(int errorNumber, Activity activity) {
		switch (errorNumber) {
			case CommonData.CONNECTION_ERROR:
				return DialogBuilder.createMessageDialog("Sem conectividade com a internet", "Erro", activity);
			case CommonData.HORARIOS_NOT_FOUND:
				return DialogBuilder.createMessageDialog("Não foi possível buscar os horários desta linha", "Atenção", activity);
			case CommonData.LOCATION_NOT_FOUND:
				return DialogBuilder.createMessageDialog("Não foi possível encontrar localização", "Erro", activity);
			default:
				return DialogBuilder.createMessageDialog("Ocorreu um erro no sistema", "Erro", activity);
		}
	}
	
	/**
	 * Show loading dialog
	 * 
	 * @param message
	 * @param title
	 * @param activity
	 */
	public static void showProgressDialog(String message, String title, Activity activity) {
		DialogBuilder.PROGRESS_DIAG = ProgressDialog.show(activity, title, message, false, true);
	}
	
	/**
	 * Close loading dialog
	 */
	public static void closeProgressDialog() {
		// Remove dialog da tela
		if (DialogBuilder.PROGRESS_DIAG != null && DialogBuilder.PROGRESS_DIAG.isShowing()) {
			DialogBuilder.PROGRESS_DIAG.dismiss();
			DialogBuilder.PROGRESS_DIAG = null;
		}
	}
}
