package planner.external.android;

import android.R.layout;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.Calendar;
import java.util.List;

import planner.external.android.R.id;
import planner.internal.core.PlanningAssistant;
import planner.internal.data.FileSystemAndroid;
import planner.internal.item.Tab;

public class MainActivity extends AppCompatActivity {

    PlanningAssistant planningAssistant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        planningAssistant = PlanningAssistant.getInstance();
        planningAssistant.setDataManager(new FileSystemAndroid(this));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
