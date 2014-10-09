package de.interoberlin.mate.lib.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import de.interoberlin.mate.lib.controller.MateController;

public class SupportActivity extends Activity
{
	// private static Context context;
	private static Context	context;
	private static Activity	activity;

	// GUI elements
	private static Button	btnSupport;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		// Call super
		super.onCreate(savedInstanceState);
        setContentView(MateController.getResourseIdByName(getPackageName(), "layout", "activity_support"));
		// setContentView(R.layout.activity_support);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		// Get activity and context
		activity = this;
		context = getApplicationContext();

		// Load elements
		btnSupport = (Button) findViewById(MateController.getResourseIdByName(getPackageName(), "id", "btnSendMail"));
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		draw();

		// Add action listeners
		btnSupport.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent emailIntent = new Intent(Intent.ACTION_SEND);
				emailIntent.setType("message/rfc822");
				emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]
				{ "support@interoberlin.de" });
				emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Support #tt Interoberlin");
				emailIntent.putExtra(Intent.EXTRA_TEXT, "Write your comment here");
				startActivity(Intent.createChooser(emailIntent, "Send mail"));
			}
		});
	}

	@Override
	public void onPause()
	{
		// Call super
		super.onPause();
	}

	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case android.R.id.home:
			{
				finish();
				break;
			}
		}
		return true;
	}

	private void draw()
	{
		activity.setTitle("Support");
	}
}