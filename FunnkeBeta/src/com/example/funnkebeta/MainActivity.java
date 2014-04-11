package com.example.funnkebeta;

import com.hookmobile.mrn.MrnManager;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	private String MRN_APP_KEY = "93CFAB5A70FA492E84CE47770EF040D6";
	private String MRN_SECRET = "151426858EFF4824AE92102E6ECF2A4D";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// *******************************************
		// * Activate MRN Manager                    *
		// *******************************************
		MrnManager.activate(this, MRN_APP_KEY, MRN_SECRET, true);
		
		// create a button for launching offerwall
		Button btnOpen = (Button) findViewById(R.id.btn_open);
		btnOpen.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				boolean success = MrnManager.getInstance().openOfferWall();		
			}

		});
		
	}
	
	@Override
	public void onStart() {
		super.onStart();

		// *******************************************
		// * Track New Session                       *
		// *******************************************
		MrnManager.getInstance().createSession();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		// *******************************************
		// * Release resources                       *
		// *******************************************
		MrnManager.getInstance().release();
	}
}
