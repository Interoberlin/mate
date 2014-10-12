package de.interoberlin.mate.lib.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import de.interoberlin.mate.lib.controller.MateController;

public class AboutActivity extends Activity
{
	// private static Context context;
	private static Activity	activity;

	// GUI elements
    private static TextView tvName;
    private static TextView tvBy;
    private static TextView tvVersion;
    private static TextView tvLicense;
    private static TextView tvWebsite;
    private static TextView tvSourcecode;
    private static TextView tvIssuetracker;

	private static Button	btnSupport;


	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		// Call super
		super.onCreate(savedInstanceState);
        setContentView(MateController.getResourseIdByName(getPackageName(), "layout", "activity_about"));
		getActionBar().setDisplayHomeAsUpEnabled(true);

		// Get activity and context
		activity = this;

		// Load elements
        tvName = (TextView) findViewById(MateController.getResourseIdByName(getPackageName(), "id", "tvName"));
        tvBy = (TextView) findViewById(MateController.getResourseIdByName(getPackageName(), "id", "tvBy"));
        tvVersion = (TextView) findViewById(MateController.getResourseIdByName(getPackageName(), "id", "tvVersion"));
        tvLicense = (TextView) findViewById(MateController.getResourseIdByName(getPackageName(), "id", "tvLicense"));
        tvWebsite = (TextView) findViewById(MateController.getResourseIdByName(getPackageName(), "id", "tvWebsite"));
        tvSourcecode = (TextView) findViewById(MateController.getResourseIdByName(getPackageName(), "id", "tvSourcecode"));
        tvIssuetracker = (TextView) findViewById(MateController.getResourseIdByName(getPackageName(), "id", "tvIssuetracker"));

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

        String version = "";
        version+= MateController.getProperty(getApplicationContext(), "versionMajor") + ".";
        version+= MateController.getProperty(getApplicationContext(), "versionMinor") + ".";
        version+= MateController.getProperty(getApplicationContext(), "versionPatch");


        tvName.setText(MateController.getProperty(getApplicationContext(), "artifactId"));
        tvBy.setText(MateController.getResourseIdByName(getPackageName(), "string", "interoberlin"));
        tvVersion.setText(version);
        tvLicense.setText(MateController.getProperty(getApplicationContext(), "license"));
        tvWebsite.setText(MateController.getProperty(getApplicationContext(), "website"));
        tvSourcecode.setText(MateController.getProperty(getApplicationContext(), "sourcecode"));
        tvIssuetracker.setText(MateController.getProperty(getApplicationContext(), "issuetracker"));
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