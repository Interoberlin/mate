package de.interoberlin.mate.lib.view;

import android.app.Activity;
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


public class LogActivity extends BaseActivity {
    public static String PACKAGE_NAME;

    private static Activity activity;

    private static TableLayout tblLog;
    private static ScrollView scrl;
    private static CheckBox cbAutoRefresh;
    private static Spinner spnnrLogLevel;

    private boolean running;
    private static ELog threshold = ELog.VERBOSE;

    // --------------------
    // Methods - Lifecycle
    // --------------------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDisplayHomeAsUpEnabled(true);

        PACKAGE_NAME = getApplicationContext().getPackageName();

        activity = this;
    }

    public void onResume() {
        super.onResume();

        // Load layout
        tblLog = (TableLayout) findViewById(MateController.getResourseIdByName(getPackageName(), "id", "tbl"));
        scrl = (ScrollView) findViewById(MateController.getResourseIdByName(getPackageName(), "id", "scrl"));
        cbAutoRefresh = (CheckBox) findViewById(MateController.getResourseIdByName(getPackageName(), "id", "cbAutoRefresh"));
        spnnrLogLevel = (Spinner) findViewById(MateController.getResourseIdByName(getPackageName(), "id", "spnnrLogLevel"));

        draw();

        List<String> list = new ArrayList<>();
        list.add(ELog.VERBOSE.name());
        list.add(ELog.DEBUG.name());
        list.add(ELog.INFO.name());
        list.add(ELog.WARN.name());
        list.add(ELog.ERROR.name());
        list.add(ELog.WTF.name());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spnnrLogLevel.setAdapter(adapter);

        running = true;

        new Thread() {
            @Override
            public void run() {
                while (running) {
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

                if (selected.equals(ELog.VERBOSE.toString())) {
                    threshold = ELog.VERBOSE;
                } else if (selected.equals(ELog.DEBUG.toString())) {
                    threshold = ELog.DEBUG;
                } else if (selected.equals(ELog.INFO.toString())) {
                    threshold = ELog.INFO;
                } else if (selected.equals(ELog.WARN.toString())) {
                    threshold = ELog.WARN;
                } else if (selected.equals(ELog.ERROR.toString())) {
                    threshold = ELog.ERROR;
                } else if (selected.equals(ELog.WTF.toString())) {
                    threshold = ELog.WTF;
                }

                uiDraw();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                threshold = ELog.VERBOSE;
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        running = false;
    }

    @Override
    protected int getLayoutResource() {
        return MateController.getResourseIdByName(getPackageName(), "layout", "activity_log");
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

    // --------------------
    // Methods
    // --------------------

    public static void uiDraw() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                draw();
            }
        });
    }

    public static void draw() {
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
                    tvLogLevel.setText(l.getLogLevel().toString().substring(0, 1));
                    tvMessage.setText(l.getMessage());

                    switch (l.getLogLevel()) {
                        case VERBOSE: {
                            tvTimestamp.setTextColor(activity.getResources().getColor(MateController.getResourseIdByName(PACKAGE_NAME, "color", "md_green_500")));
                            tvLogLevel.setTextColor(activity.getResources().getColor(MateController.getResourseIdByName(PACKAGE_NAME, "color", "md_green_500")));
                            tvMessage.setTextColor(activity.getResources().getColor(MateController.getResourseIdByName(PACKAGE_NAME, "color", "md_green_500")));
                            break;
                        }
                        case DEBUG: {
                            tvTimestamp.setTextColor(activity.getResources().getColor(MateController.getResourseIdByName(PACKAGE_NAME, "color", "md_blue_500")));
                            tvLogLevel.setTextColor(activity.getResources().getColor(MateController.getResourseIdByName(PACKAGE_NAME, "color", "md_blue_500")));
                            tvMessage.setTextColor(activity.getResources().getColor(MateController.getResourseIdByName(PACKAGE_NAME, "color", "md_blue_500")));
                            break;
                        }
                        case INFO: {
                            tvTimestamp.setTextColor(activity.getResources().getColor(MateController.getResourseIdByName(PACKAGE_NAME, "color", "md_black_1000")));
                            tvLogLevel.setTextColor(activity.getResources().getColor(MateController.getResourseIdByName(PACKAGE_NAME, "color", "md_black_1000")));
                            tvMessage.setTextColor(activity.getResources().getColor(MateController.getResourseIdByName(PACKAGE_NAME, "color", "md_black_1000")));
                            break;
                        }
                        case WARN: {
                            tvTimestamp.setTextColor(activity.getResources().getColor(MateController.getResourseIdByName(PACKAGE_NAME, "color", "md_deep_orange_500")));
                            tvLogLevel.setTextColor(activity.getResources().getColor(MateController.getResourseIdByName(PACKAGE_NAME, "color", "md_deep_orange_500")));
                            tvMessage.setTextColor(activity.getResources().getColor(MateController.getResourseIdByName(PACKAGE_NAME, "color", "md_deep_orange_500")));
                            break;
                        }
                        case ERROR: {
                            tvTimestamp.setTextColor(activity.getResources().getColor(MateController.getResourseIdByName(PACKAGE_NAME, "color", "md_red_500")));
                            tvLogLevel.setTextColor(activity.getResources().getColor(MateController.getResourseIdByName(PACKAGE_NAME, "color", "md_red_500")));
                            tvMessage.setTextColor(activity.getResources().getColor(MateController.getResourseIdByName(PACKAGE_NAME, "color", "md_red_500")));
                            break;
                        }
                        case WTF: {
                            tvTimestamp.setTextColor(activity.getResources().getColor(MateController.getResourseIdByName(PACKAGE_NAME, "color", "md_red_900")));
                            tvLogLevel.setTextColor(activity.getResources().getColor(MateController.getResourseIdByName(PACKAGE_NAME, "color", "md_red_900")));
                            tvMessage.setTextColor(activity.getResources().getColor(MateController.getResourseIdByName(PACKAGE_NAME, "color", "md_red_900")));
                            break;
                        }
                    }

                    tvTimestamp.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 0.3f));
                    tvLogLevel.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 0.1f));
                    tvMessage.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.00f));

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
}