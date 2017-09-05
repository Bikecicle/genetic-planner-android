package planner.external.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import planner.internal.core.PlanningAssistant;

public class MainActivity extends AppCompatActivity {

    PlanningAssistant planningAssistant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        planningAssistant = PlanningAssistant.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
