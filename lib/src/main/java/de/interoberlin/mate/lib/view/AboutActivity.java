package de.interoberlin.mate.lib.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import de.interoberlin.mate.lib.controller.MateController;

public class AboutActivity extends Activity {
    // Activity
    private static Activity activity;

    // View
    private static TextView tvName;
    private static TextView tvCompany;
    private static TextView tvVersion;
    private static TextView tvLicense;
    private static TextView tvWebsite;
    private static TextView tvIssuetracker;

    private static TextView tvSourcecode;
    private static Button btnSendMail;

    private static ImageView ivMail;
    private static ImageView ivGithub;
    private static ImageView ivTell;

    private static String flavor = "";

    // --------------------
    // Methods - Lifecycle
    // --------------------

    /**
     * To select one flavor use
     * <code>
     * Intent intent = new Intent(MyActivity.this, AboutActivity.class);
     * Bundle b = new Bundle();
     * b.putString("flavor", "interoberlin");
     * intent.putExtras(b);
     * startActivity(intent);
     * <code/>
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Call super
        super.onCreate(savedInstanceState);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            flavor = b.getString("flavor");
        }

        String layout = "activity_about";

        if (!flavor.equals("") && flavor != null) {
            layout += "_" + flavor;
        }

        setContentView(MateController.getResourseIdByName(getPackageName(), "layout", layout));
        getActionBar().setDisplayHomeAsUpEnabled(true);

        // Get activity and context
        activity = this;

        // Load elements
        tvName = (TextView) findViewById(MateController.getResourseIdByName(getPackageName(), "id", "tvName"));
        tvCompany = (TextView) findViewById(MateController.getResourseIdByName(getPackageName(), "id", "tvCompany"));
        tvVersion = (TextView) findViewById(MateController.getResourseIdByName(getPackageName(), "id", "tvVersion"));
        tvLicense = (TextView) findViewById(MateController.getResourseIdByName(getPackageName(), "id", "tvLicense"));
        tvWebsite = (TextView) findViewById(MateController.getResourseIdByName(getPackageName(), "id", "tvWebsite"));

        tvIssuetracker = (TextView) findViewById(MateController.getResourseIdByName(getPackageName(), "id", "tvIssuetracker"));

        if (flavor.equals("interoberlin")) {
            ivGithub = (ImageButton) findViewById(MateController.getResourseIdByName(getPackageName(), "id", "ivGithub"));
            ivMail = (ImageButton) findViewById(MateController.getResourseIdByName(getPackageName(), "id", "ivMail"));
            ivTell = (ImageButton) findViewById(MateController.getResourseIdByName(getPackageName(), "id", "ivTell"));
        } else {
            tvSourcecode = (TextView) findViewById(MateController.getResourseIdByName(getPackageName(), "id", "tvSourcecode"));
            btnSendMail = (Button) findViewById(MateController.getResourseIdByName(getPackageName(), "id", "btnSendMail"));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        draw();

        Bundle b = getIntent().getExtras();
        if (b != null) {
            flavor = b.getString("flavor");
        }

        String version = "";
        version += MateController.getProperty(getApplicationContext(), "versionMajor") + ".";
        version += MateController.getProperty(getApplicationContext(), "versionMinor") + ".";
        version += MateController.getProperty(getApplicationContext(), "versionPatch");
        final String versionCode = version;

        // Add action listeners
        if (flavor.equals("interoberlin")) {
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
        } else {
            tvSourcecode.setText(MateController.getProperty(getApplicationContext(), "sourcecode"));
            tvSourcecode.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(MateController.getProperty(getApplicationContext(), "sourcecode")));
                    startActivity(i);
                }
            });
            btnSendMail.setOnClickListener(new OnClickListener() {
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
        }

        tvName.setText(MateController.getProperty(getApplicationContext(), "artifactId"));
        tvCompany.setText(MateController.getResourseIdByName(getPackageName(), "string", "interoberlin"));
        tvVersion.setText(version);
        tvLicense.setText(MateController.getProperty(getApplicationContext(), "license"));
        tvWebsite.setText(MateController.getProperty(getApplicationContext(), "website"));

        tvIssuetracker.setText(MateController.getProperty(getApplicationContext(), "issuetracker"));
    }

    @Override
    public void onPause() {
        super.onPause();
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

    // --------------------
    // Methods
    // --------------------

    private void draw() {
        activity.setTitle(MateController.getProperty(getApplicationContext(), "artifactId"));
    }
}