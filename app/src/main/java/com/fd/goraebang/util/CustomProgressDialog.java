package com.fd.goraebang.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.fd.goraebang.R;

public class CustomProgressDialog extends ProgressDialog {
	public CustomProgressDialog(Context context) {
		super(context);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {  
		// TODO Auto-generated method stub  
		super.onCreate(savedInstanceState);
		setCancelable(true);
		setContentView(R.layout.layout_custom_progress_dialog);
		ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar);
		if (pb != null) {
			pb.getIndeterminateDrawable().setColorFilter(
					getContext().getResources().getColor(R.color.colorPrimary),
					android.graphics.PorterDuff.Mode.MULTIPLY
			);
		}


	}
	@Override
	public void show() {
		super.show();
	}

	@Override
	public void dismiss() {
		super.dismiss();
	}
}  