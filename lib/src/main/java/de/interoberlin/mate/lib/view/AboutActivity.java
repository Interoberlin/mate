package de.interoberlin.mate.lib.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import de.interoberlin.mate.lib.controller.MateController;

public class AboutActivity extends BaseActivity {
    // --------------------
    // Methods - Lifecycle
    // --------------------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Load layout
        final TextView tvName = (TextView) findViewById(MateController.getResourseIdByName(getPackageName(), "id", "tvName"));
        final TextView tvCompany = (TextView) findViewById(MateController.getResourseIdByName(getPackageName(), "id", "tvCompany"));
        final TextView tvVersion = (TextView) findViewById(MateController.getResourseIdByName(getPackageName(), "id", "tvVersion"));
        final TextView tvLicense = (TextView) findViewById(MateController.getResourseIdByName(getPackageName(), "id", "tvLicense"));
        final TextView tvWebsite = (TextView) findViewById(MateController.getResourseIdByName(getPackageName(), "id", "tvWebsite"));
        final TextView tvIssuetracker = (TextView) findViewById(MateController.getResourseIdByName(getPackageName(), "id", "tvIssuetracker"));
        final ImageView ivGithub = (ImageButton) findViewById(MateController.getResourseIdByName(getPackageName(), "id", "ivGithub"));
        final ImageView ivMail = (ImageButton) findViewById(MateController.getResourseIdByName(getPackageName(), "id", "ivMail"));
        final ImageView ivTell = (ImageButton) findViewById(MateController.getResourseIdByName(getPackageName(), "id", "ivTell"));

        String version = "";
        version += MateController.getProperty(getApplicationContext(), "versionMajor") + ".";
        version += MateController.getProperty(getApplicationContext(), "versionMinor") + ".";
        version += MateController.getProperty(getApplicationContext(), "versionPatch");
        final String versionCode = version;

        // Set values
        tvName.setText(MateController.getProperty(getApplicationContext(), "artifactId"));
        tvCompany.setText(MateController.getResourseIdByName(getPackageName(), "string", "interoberlin"));
        tvVersion.setText(version);
        tvLicense.setText(MateController.getProperty(getApplicationContext(), "license"));
        tvWebsite.setText(MateController.getProperty(getApplicationContext(), "website"));
        tvIssuetracker.setText(MateController.getProperty(getApplicationContext(), "issuetracker"));

        // Add actions
        ivGithub.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(MateController.getProperty(getApplicationContext(), "sourcecode")));
                startActivity(i);
            }
        });
        ivMail.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("message/rfc822");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]
                        {"support@interoberlin.de"});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback " + MateController.getProperty(getApplicationContext(), "artifactId") + " " + versionCode);
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Write your comment here");
                startActivity(Intent.createChooser(emailIntent, "Send mail"));
            }
        });
        ivTell.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String projectUrl = MateController.getProperty(getApplicationContext(), "projectUrl");

                if (projectUrl != null) {
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.setType("message/rfc822");
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Get the " + MateController.getProperty(getApplicationContext(), "artifactId") + " app");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "To learn more go to " + projectUrl);
                    startActivity(Intent.createChooser(emailIntent, "Send mail"));
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected int getLayoutResource() {
        return MateController.getResourseIdByName(getPackageName(), "layout", "activity_about_interoberlin");
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                break;
            }
        }
        return true;
    }
}