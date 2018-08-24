package planner.view.android;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import planner.model.core.PlanningAssistant;
import planner.model.data.RoomDBAndroid;
import planner.model.item.Note;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NoteListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NoteListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteListFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_INTERVAL = "interval";

    private Interval interval;

    private OnFragmentInteractionListener mListener;

    public NoteListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NoteListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NoteListFragment newInstance(Interval interval) {
        NoteListFragment fragment = new NoteListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_INTERVAL, interval);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            interval = (Interval) getArguments().getSerializable(ARG_INTERVAL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Calendar interest = Calendar.getInstance();
        PlanningAssistant planningAssistant = PlanningAssistant.getInstance(new RoomDBAndroid(getContext(), false));

        TextView tvTitle = getView().findViewById(R.id.note_fragment_title);
        tvTitle.setText(interval.fTitle);

        TextView tvInterest = getView().findViewById(R.id.note_fragment_interest);
        ListView lvTabs = getView().findViewById(R.id.note_fragment_list);

        tvInterest.setText(new SimpleDateFormat(interval.iFormat).format(interest.getTime()));

        List<Note> notes = null;
        if (interval == Interval.day) {
            notes = planningAssistant.getDay(interest);
        } else if (interval == Interval.week) {
            notes = planningAssistant.getWeek(interest);
        } else if (interval == Interval.month) {
            notes = planningAssistant.getMonth(interest);
        } else if (interval == Interval.year) {
            notes = planningAssistant.getYear(interest);
        }
        NoteAdapter adapter = new NoteAdapter(this.getContext(), notes, interval.lFormat);
        lvTabs.setAdapter(adapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
