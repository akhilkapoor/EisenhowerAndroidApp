/* Created by Kapoor and Smith */

package com.smith.kapoor.theeisenhowerwaytogtd.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.smith.kapoor.theeisenhowerwaytogtd.R;
import com.smith.kapoor.theeisenhowerwaytogtd.data.GTDTaskContract;
import com.smith.kapoor.theeisenhowerwaytogtd.data.constants.CompletedEnum;
import com.smith.kapoor.theeisenhowerwaytogtd.data.constants.PriorityEnum;
import com.smith.kapoor.theeisenhowerwaytogtd.data.objects.TaskDO;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * AddEdit Allows The adding or editing of a specific Task
 */
public class AddEditFragment extends Fragment {

    private static String LOG_TAG = AddEditFragment.class.getSimpleName();

    private static String ARG_TASK = "task";
    private static String ARG_PRIORITY = "priority";

    private TaskDO taskDO;

    private EditText name, description;
    private Spinner priority;
    private DatePicker completeDate;
    private TimePicker completeTime;
    private Button saveButton, cancelButton;

    private PriorityEnum priorityEnum;

    public AddEditFragment() {
    }

    /**
     * Create a new instance of AddEditFragment
     */
    public static AddEditFragment newInstance(TaskDO taskDO, PriorityEnum priorityEnum) {
        // We pass in a Task and a Priority to default to
        AddEditFragment f = new AddEditFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_TASK, taskDO);
        if (priorityEnum == null)
            bundle.putInt(ARG_PRIORITY, 4);
        else
            bundle.putInt(ARG_PRIORITY, priorityEnum.getPriorityValue());
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // When we create an instance of the fragment, this is how we get any arguments.
            taskDO = getArguments().getParcelable(ARG_TASK);
            priorityEnum = PriorityEnum.getById(getArguments().getInt(ARG_PRIORITY));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // We inflate xml and get the Objects
        View v = inflater.inflate(R.layout.fragment_add_edit, container, false);

        name = (EditText) v.findViewById(R.id.task_name);
        description = (EditText) v.findViewById(R.id.description);

        // Spinners are special and we have prepare the data listed in them.
        priority = (Spinner) v.findViewById(R.id.priority);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.priority_list, android.R.layout.simple_spinner_item);
        priority.setAdapter(adapter);
        priority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //set the value when we select it
                    priorityEnum = PriorityEnum.getById(position + 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //sets the default selection
                priorityEnum = PriorityEnum.NOT_IMPORTANT_NOT_URGENT;
            }
        });

        int spinnerPosition = priorityEnum.getPriorityValue() - 1;
        priority.setSelection(spinnerPosition);


        completeDate = (DatePicker) v.findViewById(R.id.deadline);
        completeTime = (TimePicker) v.findViewById(R.id.deadline_time);

        saveButton = (Button) v.findViewById(R.id.save_btn);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAndReturn();
            }
        });

        cancelButton = (Button) v.findViewById(R.id.cancel_btn);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        // if we sent a task in to be edited, here is where we get that information from the data object
        if (taskDO != null) {
            name.setText(taskDO.getName());
            description.setText(taskDO.getDescription());

            Calendar cal = Calendar.getInstance();
            cal.setTime(taskDO.getCompletetionDate());

            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            int min = cal.get(Calendar.MINUTE);

            completeDate.updateDate(year, month, day);
            completeTime.setCurrentHour(hour);
            completeTime.setCurrentMinute(min);

            // set Title
            getActivity().getActionBar().setTitle(R.string.edit_task);

        } else {

            // set Title
            getActivity().getActionBar().setTitle(R.string.add_new_task);

        }

        return v;
    }

    /**
     * Saves the new or updated Task.
     */
    private void saveAndReturn() {

        if (name.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "The task must have a name", Toast.LENGTH_SHORT).show();
        } else {
        // first case is new task, otherwise we are trying to edit
            if (taskDO == null) {
                taskDO = new TaskDO();
                taskDO.setCompletedEnum(CompletedEnum.INCOMPLETE);
            }

            // set the values and save it or update it
            taskDO.setName(name.getText().toString());
            taskDO.setDescription(description.getText().toString());
            taskDO.setPriorityEnum(priorityEnum);
            Date taskDate = getDate();
            taskDO.setCompletetionDate(taskDate);

            if (taskDO.getId() == -1) {
                GTDTaskContract.addTask(getActivity(), taskDO);
            } else {
                GTDTaskContract.updateTask(getActivity(), taskDO);
            }

            getFragmentManager().popBackStack();

        }
    }

    /**
     * Gets the date from the Date and Time picker.  Creates the calendar and return it.
     * @return - Spinner Date/Time value
     */
    private Date getDate() {
        int day = completeDate.getDayOfMonth();
        int month = completeDate.getMonth();
        int year = completeDate.getYear();
        int hour = completeTime.getCurrentHour();
        int min = completeTime.getCurrentMinute();
        return new GregorianCalendar(year, month, day, hour, min).getTime();
    }

}
