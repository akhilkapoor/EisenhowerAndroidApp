/* Created by Kapoor and Smith */

package com.smith.kapoor.theeisenhowerwaytogtd.fragments;

import android.app.Fragment;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.smith.kapoor.theeisenhowerwaytogtd.R;
import com.smith.kapoor.theeisenhowerwaytogtd.data.constants.PriorityEnum;


/**
 * The main fragment show the user buttons to navigate to the different list activities.
 */
public class MainFragment extends Fragment {

    public static int buttonColor1 = Color.LTGRAY;
    public static int buttonColor2 = Color.LTGRAY;
    public static int buttonColor3 = Color.LTGRAY;
    public static int buttonColor4 = Color.LTGRAY;

    public static int buttonText1 = Color.RED;
    public static int buttonText2 = Color.BLUE;
    public static int buttonText3 = Color.GREEN;
    public static int buttonText4 = Color.YELLOW;


    private static String LOG_TAG = MainFragment.class.getSimpleName();
    public  static String FRAGMENT_TAG = "main";
    //default priority
    public  static PriorityEnum recentPriority = PriorityEnum.NOT_IMPORTANT_NOT_URGENT;
    private Button criticalAndImportant, urgentAndNotImportant, notUrgentAndImportant, unassigned, settings;

    public MainFragment() {
    }

    /**
     * Create a new instance of MainFragment
     */
    public static MainFragment newInstance() {
        MainFragment f = new MainFragment();
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // turns on menu and forces orientation
        setHasOptionsMenu(true);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // inflates layout and sets button onClick
        View v = inflater.inflate(R.layout.fragment_gtd, container, false);

        // set Title
        getActivity().getActionBar().setTitle(R.string.app_name);

        criticalAndImportant = (Button) v.findViewById(R.id.urgent_critical);
        urgentAndNotImportant = (Button) v.findViewById(R.id.urgent_not_critical);
        notUrgentAndImportant = (Button) v.findViewById(R.id.not_urgent_critical);
        unassigned = (Button) v.findViewById(R.id.unassigned);
        settings = (Button) v.findViewById(R.id.options_button);

        criticalAndImportant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recentPriority = PriorityEnum.IMPORTANT_URGENT;
                loadListFragment(recentPriority);
            }
        });

        urgentAndNotImportant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recentPriority = PriorityEnum.NOT_IMPORTANT_URGENT;
                loadListFragment(recentPriority);
            }
        });

        notUrgentAndImportant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recentPriority = PriorityEnum.IMPORTANT_NOT_URGENT;
                loadListFragment(recentPriority);
            }
        });

        unassigned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recentPriority = PriorityEnum.NOT_IMPORTANT_NOT_URGENT;
                loadListFragment(recentPriority);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadOptionsFragment();
            }
        });

        return v;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // sets which actions are visible
        if (menu != null) {
            menu.findItem(R.id.action_new).setVisible(true);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onResume() {
        super.onResume();
        Button listButton1 = (Button) getView().findViewById(R.id.urgent_critical);
        Button listButton2 = (Button) getView().findViewById(R.id.not_urgent_critical);
        Button listButton3 = (Button) getView().findViewById(R.id.urgent_not_critical);
        Button listButton4 = (Button) getView().findViewById(R.id.unassigned);
        listButton1.setBackgroundColor(buttonColor1);
        listButton2.setBackgroundColor(buttonColor2);
        listButton3.setBackgroundColor(buttonColor3);
        listButton4.setBackgroundColor(buttonColor4);
        listButton1.setTextColor(buttonText1);
        listButton2.setTextColor(buttonText2);
        listButton3.setTextColor(buttonText3);
        listButton4.setTextColor(buttonText4);

    }

    // loads a list when clicked
    private void loadListFragment(PriorityEnum priorityEnum) {
        getActivity().getFragmentManager().beginTransaction().replace(R.id.container, ListFragment.newInstance(priorityEnum), priorityEnum.toString()).addToBackStack(null).commit();
    }

    private void loadOptionsFragment() {
        getActivity().getFragmentManager().beginTransaction().replace(R.id.container, OptionsFragment.newInstance()).addToBackStack(null).commit();
    }

}
