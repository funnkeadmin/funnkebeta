package com.example.funnkebeta;

import com.hookmobile.mrn.MrnManager;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	
	/**
	 * Update following two constants with values assigned by Funnke
	 * Partner Portal for your app account.
	 */
	private String FUNNKE_APP_KEY = "651AACB845BC4BD2BC3F462D203250BF";
	private String FUNNKE_SECRET = "CD5E2A407C46489C98AF439A4EE5A927";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// *******************************************
		// * Activate MRN Manager                    *
		// *******************************************
		boolean debugging = true;
		boolean useVirtualNumber = true;
		
		MrnManager.activate(this, FUNNKE_APP_KEY, FUNNKE_SECRET, debugging, useVirtualNumber);
		
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
