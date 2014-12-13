/* Created by Kapoor and Smith */

package com.smith.kapoor.theeisenhowerwaytogtd.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.smith.kapoor.theeisenhowerwaytogtd.R;
import com.smith.kapoor.theeisenhowerwaytogtd.data.GTDTaskContract;
import com.smith.kapoor.theeisenhowerwaytogtd.data.constants.CompletedEnum;
import com.smith.kapoor.theeisenhowerwaytogtd.data.objects.TaskDO;

/**
 * Detail Fragment show a task and allows completed to be set.
 */
public class DetailFragment extends Fragment {

    private static String LOG_TAG = DetailFragment.class.getSimpleName();

    private static String ARG_TASK = "task";

    private TextView taskName;
    private Button deleteButton, backButton;
    private CheckBox completed;

    private TaskDO taskDO;

    public DetailFragment() {
    }

    /**
     * Create a new instance of DetailFragment.  A task is loaded upon creation of the fragment.
     */
    public static DetailFragment newInstance(TaskDO taskDO) {
        DetailFragment f = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_TASK, taskDO);
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // gets the task object upon creation
        if(getArguments() != null) {
            taskDO = getArguments().getParcelable(ARG_TASK);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_task_details, container, false);

        // set Title
        getActivity().getActionBar().setTitle("Item Options");

        taskName = (TextView) v.findViewById(R.id.task_name);
        deleteButton = (Button) v.findViewById(R.id.delete_task);
        backButton = (Button) v.findViewById(R.id.back);
        completed = (CheckBox) v.findViewById(R.id.completed_cb);

        if(taskDO != null) {
            taskName.setText(taskDO.getName());
            completed.setChecked(taskDO.getCompletedEnum().equals(CompletedEnum.COMPLETED));
        }

        // sets a task to completed if checked or incomplete when not checked.  Updates the object.
        completed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    taskDO.setCompletedEnum(CompletedEnum.COMPLETED);
                } else {
                    taskDO.setCompletedEnum(CompletedEnum.INCOMPLETE);
                }
                GTDTaskContract.updateTask(getActivity(), taskDO);
            }
        });

        // back navigation
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        // throws a dialog asking if the user is sure they want to delete.
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDelete();
            }
        });

        return v;
    }

    /**
     * Dialog to ask if they are sure they want to cancel.  If yes, deletes and navigates back to home.
     *
     */
    private void confirmDelete () {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Are you sure you want to delete Item now?")
                .setCancelable(false)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        GTDTaskContract.removeTask(getActivity(), taskDO);
                        getActivity().onBackPressed();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

}
