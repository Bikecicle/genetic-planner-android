package planner.external.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import planner.internal.core.PlanningAssistant;
import planner.internal.data.FileSystemAndroid;

public class MainActivity extends AppCompatActivity {

    PlanningAssistant planningAssistant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        planningAssistant = PlanningAssistant.getInstance();
        planningAssistant.setDataManager(new FileSystemAndroid(this));
        TestData.simpleEvents(planningAssistant);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, DayActivity.class);
        startActivity(intent);
    }
}
