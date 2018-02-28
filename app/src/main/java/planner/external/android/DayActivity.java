package planner.external.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Calendar;

import planner.internal.core.PlanningAssistant;
import planner.internal.item.Tab;

public class DayActivity extends AppCompatActivity {

    PlanningAssistant planningAssistant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        planningAssistant = PlanningAssistant.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

        Calendar interest = Calendar.getInstance();
        ArrayAdapter<Tab>  = new ArrayAdapter<Tab>(this, R.layout.simple_list_item_1, planningAssistant.getDay(interest));
        ListView dayView = (ListView) findViewById(R.id.day_list);
        dayView
    }
}
