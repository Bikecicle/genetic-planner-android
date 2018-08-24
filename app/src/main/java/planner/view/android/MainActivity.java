package planner.view.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import planner.model.core.PlanningAssistant;
import planner.model.data.RoomDBAndroid;
import planner.model.item.Note;

public class MainActivity extends AppCompatActivity {

    PlanningAssistant planningAssistant;
    NoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        planningAssistant = PlanningAssistant.getInstance(new RoomDBAndroid(this, false));

        List<Note> notes = planningAssistant.getAll();
        ListView noteList = findViewById(R.id.main_note_list);
        adapter = new NoteAdapter(this, notes, Interval.week.lFormat);
        noteList.setAdapter(adapter);
        noteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(view.getContext(), NoteActivity.class);
                Bundle bundle = new Bundle();
                int noteId = ((Note) adapterView.getSelectedItem()).noteId;
                ArrayList<Integer> val = new ArrayList();
                val.add(noteId);
                bundle.putIntegerArrayList("NOTE_ID", val);
                intent.putExtras(bundle);
                startActivity(intent); 
            }
        });

        FloatingActionButton fab = findViewById(R.id.main_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddItemActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_reschedule) {
            planningAssistant.planSchedule();
            adapter.clear();
            adapter.addAll(planningAssistant.getAll());
            return true;
        } else if (id == R.id.action_clear) {
            planningAssistant.clean();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
