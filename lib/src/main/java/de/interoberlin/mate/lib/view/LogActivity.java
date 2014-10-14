package de.interoberlin.mate.lib.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.interoberlin.mate.lib.controller.MateController;
import de.interoberlin.mate.lib.model.ELog;
import de.interoberlin.mate.lib.model.Log;
import de.interoberlin.mate.lib.model.LogEntry;


public class LogActivity extends Activity {
    public static String PACKAGE_NAME;
    private static Context context;
    private static Activity activity;
    private static TableLayout tblLog;
    private static ScrollView scrl;
    private static CheckBox cbAutoRefresh;
    private static Spinner spnnrLogLevel;

    private static boolean RUNNING;
    private static ELog threshold = ELog.TRACE;

    public static void uiDraw() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                draw();
            }
        });
    }

    public static void draw() {
        activity.setTitle("Log");

        tblLog.removeAllViews();

        synchronized (Log.getAll()) {
            for (LogEntry l :
                    Log.getAll()) {
                if (l.getLogLevel().ordinal() >= threshold.ordinal()) {
                    TableRow tr = new TableRow(activity);
                    TextView tvTimestamp = new TextView(activity);
                    TextView tvLogLevel = new TextView(activity);
                    TextView tvMessage = new TextView(activity);

                    tvTimestamp.setText(l.getTimeStamp());
                    tvLogLevel.setText(l.getLogLevel().toString());
                    tvMessage.setText(l.getMessage());

                    switch (l.getLogLevel()) {
                        case TRACE: {
                            tvTimestamp.setTextColor(activity.getResources().getColor(MateController.getResourseIdByName(PACKAGE_NAME, "color", "green")));
                            tvLogLevel.setTextColor(activity.getResources().getColor(MateController.getResourseIdByName(PACKAGE_NAME, "color", "green")));
                            tvMessage.setTextColor(activity.getResources().getColor(MateController.getResourseIdByName(PACKAGE_NAME, "color", "green")));
                            break;
                        }
                        case DEBUG: {
                            tvTimestamp.setTextColor(activity.getResources().getColor(MateController.getResourseIdByName(PACKAGE_NAME, "color", "blue")));
                            tvLogLevel.setTextColor(activity.getResources().getColor(MateController.getResourseIdByName(PACKAGE_NAME, "color", "blue")));
                            tvMessage.setTextColor(activity.getResources().getColor(MateController.getResourseIdByName(PACKAGE_NAME, "color", "blue")));
                            break;
                        }
                        case INFO: {
                            tvTimestamp.setTextColor(activity.getResources().getColor(MateController.getResourseIdByName(PACKAGE_NAME, "color", "black")));
                            tvLogLevel.setTextColor(activity.getResources().getColor(MateController.getResourseIdByName(PACKAGE_NAME, "color", "black")));
                            tvMessage.setTextColor(activity.getResources().getColor(MateController.getResourseIdByName(PACKAGE_NAME, "color", "black")));
                            break;
                        }
                        case WARN: {
                            tvTimestamp.setTextColor(activity.getResources().getColor(MateController.getResourseIdByName(PACKAGE_NAME, "color", "orange")));
                            tvLogLevel.setTextColor(activity.getResources().getColor(MateController.getResourseIdByName(PACKAGE_NAME, "color", "orange")));
                            tvMessage.setTextColor(activity.getResources().getColor(MateController.getResourseIdByName(PACKAGE_NAME, "color", "orange")));
                            break;
                        }
                        case ERROR: {
                            tvTimestamp.setTextColor(activity.getResources().getColor(MateController.getResourseIdByName(PACKAGE_NAME, "color", "red")));
                            tvLogLevel.setTextColor(activity.getResources().getColor(MateController.getResourseIdByName(PACKAGE_NAME, "color", "red")));
                            tvMessage.setTextColor(activity.getResources().getColor(MateController.getResourseIdByName(PACKAGE_NAME, "color", "red")));
                            break;
                        }
                        case FATAL: {
                            tvTimestamp.setTextColor(activity.getResources().getColor(MateController.getResourseIdByName(PACKAGE_NAME, "color", "red")));
                            tvLogLevel.setTextColor(activity.getResources().getColor(MateController.getResourseIdByName(PACKAGE_NAME, "color", "red")));
                            tvMessage.setTextColor(activity.getResources().getColor(MateController.getResourseIdByName(PACKAGE_NAME, "color", "red")));
                            break;
                        }
                    }

                    tvTimestamp.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 0.3f));
                    tvLogLevel.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 0.2f));
                    tvMessage.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 0.5f));

                    tr.addView(tvTimestamp, 0);
                    tr.addView(tvLogLevel, 1);
                    tr.addView(tvMessage, 2);

                    tblLog.addView(tr);
                }
            }
        }


        scrl.post(new Runnable() {
            @Override
            public void run() {
                scrl.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(MateController.getResourseIdByName(getPackageName(), "layout", "activity_log"));
        getActionBar().setDisplayHomeAsUpEnabled(true);

        // Set package name
        PACKAGE_NAME = getApplicationContext().getPackageName();

        // Get activity and context
        activity = this;
        context = getApplicationContext();

        // Get views by id
        tblLog = (TableLayout) findViewById(MateController.getResourseIdByName(getPackageName(), "id", "tbl"));
        scrl = (ScrollView) findViewById(MateController.getResourseIdByName(getPackageName(), "id", "scrl"));
        cbAutoRefresh = (CheckBox) findViewById(MateController.getResourseIdByName(getPackageName(), "id", "cbAutoRefresh"));
        spnnrLogLevel = (Spinner) findViewById(MateController.getResourseIdByName(getPackageName(), "id", "spnnrLogLevel"));
    }

    public void onResume() {
        super.onResume();
        draw();

        // Create an ArrayAdapter using the string array and a default spinner
        // layout
        // ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, MateController.getResourseIdByName(getPackageName(), "string", "loglevel"), MateController.getResourseIdByName(getPackageName(), "layout", "simple_spinner_item"));

        List<String> list = new ArrayList<String>();
        list.add("TRACE");
        list.add("DEBUG");
        list.add("INFO");
        list.add("WARN");
        list.add("ERROR");
        list.add("FATAL");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spnnrLogLevel.setAdapter(adapter);

        RUNNING = true;

        new Thread() {
            @Override
            public void run() {
                while (RUNNING) {
                    if (cbAutoRefresh.isChecked()) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        LogActivity.uiDraw();
                    }
                }
            }
        }.start();

        spnnrLogLevel.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String selected = parent.getItemAtPosition(pos).toString();

                if (selected.equals(ELog.TRACE.toString())) {
                    threshold = ELog.TRACE;
                } else if (selected.equals(ELog.DEBUG.toString())) {
                    threshold = ELog.DEBUG;
                } else if (selected.equals(ELog.INFO.toString())) {
                    threshold = ELog.INFO;
                } else if (selected.equals(ELog.WARN.toString())) {
                    threshold = ELog.WARN;
                } else if (selected.equals(ELog.ERROR.toString())) {
                    threshold = ELog.ERROR;
                } else if (selected.equals(ELog.FATAL.toString())) {
                    threshold = ELog.FATAL;
                }

                uiDraw();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                threshold = ELog.TRACE;
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        RUNNING = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(MateController.getResourseIdByName(getPackageName(), "menu", "activity_log"), menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }
}