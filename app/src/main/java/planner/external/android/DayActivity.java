package planner.external.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Calendar;
import java.util.List;

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
        List<Tab> tabs = planningAssistant.getDay(interest);
        ArrayAdapter<Tab> tabArrayAdapter = new ArrayAdapter<Tab>(this, R.layout., );
    }
}
