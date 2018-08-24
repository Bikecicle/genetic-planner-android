package planner.view.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

import planner.model.core.PlanningAssistant;
import planner.model.data.RoomDBAndroid;
import planner.model.item.Event;
import planner.model.item.Item;
import planner.model.item.ItemType;
import planner.model.item.Task;

public class AddItemActivity extends AppCompatActivity {

    private static final int HR_MIN = 0;
    private static final int HR_MAX = 100;
    private static final int TIME_HR_MIN = 1;
    private static final int TIME_HR_MAX = 12;
    private static final int TIME_MIN_MIN = 0;
    private static final int TIME_MIN_MAX = 59;

    private PlanningAssistant planningAssistant;
    private ItemType itemType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        planningAssistant = PlanningAssistant.getInstance(new RoomDBAndroid(this, false));

        ((NumberPicker) findViewById(R.id.add_time_hr)).setMinValue(TIME_HR_MIN);
        ((NumberPicker) findViewById(R.id.add_time_hr)).setMaxValue(TIME_HR_MAX);
        ((NumberPicker) findViewById(R.id.add_time_min)).setMinValue(TIME_MIN_MIN);
        ((NumberPicker) findViewById(R.id.add_time_min)).setMaxValue(TIME_MIN_MAX);

        ((NumberPicker) findViewById(R.id.add_dur_hr)).setMinValue(HR_MIN);
        ((NumberPicker) findViewById(R.id.add_dur_hr)).setMaxValue(HR_MAX);
        ((NumberPicker) findViewById(R.id.add_dur_min)).setMinValue(TIME_MIN_MIN);
        ((NumberPicker) findViewById(R.id.add_dur_min)).setMaxValue(TIME_MIN_MAX);

        String[] types = new String[ItemType.values().length];
        for (int i = 0; i < types.length; i++) {
            types[i] = ItemType.values()[i].name;
        }
        Spinner typeSpinner = findViewById(R.id.add_type);
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, types);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                itemType = ItemType.valueOf(((String) adapterView.getItemAtPosition(i)).toLowerCase());
                findViewById(R.id.add_layout_standard).setVisibility(View.VISIBLE);
                ((TextView) findViewById(R.id.add_date_label)).setText(itemType.dateName);
                // Set any unique fields to visible
                findViewById(R.id.add_button).setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                findViewById(R.id.add_layout_standard).setVisibility(View.GONE);
                // Set all unique fields to gone
                findViewById(R.id.add_button).setVisibility(View.GONE);
            }
        });

        final String[] amPm = {"AM","PM"};
        final Spinner amPmSpinner = findViewById(R.id.add_time_am_pm);
        ArrayAdapter<String> amPmAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, amPm);
        amPmAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        amPmSpinner.setAdapter(amPmAdapter);

        Button addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar date = Calendar.getInstance();
                date.set(Calendar.YEAR, ((DatePicker)findViewById(R.id.add_date)).getYear());
                date.set(Calendar.MONTH, ((DatePicker)findViewById(R.id.add_date)).getMonth());
                date.set(Calendar.DAY_OF_MONTH, ((DatePicker) findViewById(R.id.add_date)).getDayOfMonth());
                date.set(Calendar.HOUR_OF_DAY, ((NumberPicker) findViewById(R.id.add_time_hr)).getValue());
                if (amPmSpinner.getSelectedItem().equals("AM"))
                    date.set(Calendar.AM_PM, Calendar.AM);
                else
                    date.set(Calendar.AM_PM, Calendar.PM);
                date.set(Calendar.MINUTE, ((NumberPicker) findViewById(R.id.add_time_min)).getValue());

                Calendar duration = Calendar.getInstance();
                duration.setTimeInMillis(0);
                duration.set(Calendar.HOUR_OF_DAY, ((NumberPicker) findViewById(R.id.add_dur_hr)).getValue());
                duration.set(Calendar.MINUTE, ((NumberPicker) findViewById(R.id.add_dur_min)).getValue());

                String title = ((EditText) findViewById(R.id.add_title)).getText().toString();
                String details = ((EditText) findViewById(R.id.add_details)).getText().toString();

                Item item = null;
                int itemId = (int) (Math.random() * Integer.MAX_VALUE);
                if (itemType == ItemType.event) {
                    item = new Event(itemId, title, details, date.getTimeInMillis(), duration.getTimeInMillis());
                } else if (itemType == ItemType.task){
                    item = new Task(itemId, title, details, date.getTimeInMillis(), duration.getTimeInMillis());
                } else if (itemType == ItemType.routine) {
                    // TODO
                } else if (itemType == ItemType.quota){
                    // TODO
                }
                planningAssistant.addItem(item);
                planningAssistant.planSchedule();
                Intent intent = new Intent(view.getContext(), MainActivityWithTabs.class);
                startActivity(intent);
            }
        });
    }
}
