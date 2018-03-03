package planner.external.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import planner.internal.core.C;
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
        TextView tvInterest = findViewById(R.id.interest);
        tvInterest.setText(new SimpleDateFormat(C.DAY_FORMAT).format(interest.getTime()));

        List<Tab> tabs = planningAssistant.getDay(interest);
        TabAdapter adapter = new TabAdapter(this, tabs, C.TIME_FORMAT);
        ListView lvTabs = findViewById(R.id.tab_list);
        lvTabs.setAdapter(adapter);
    }
}
