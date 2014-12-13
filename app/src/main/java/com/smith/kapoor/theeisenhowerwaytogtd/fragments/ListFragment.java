/* Created by Kapoor and Smith */

package com.smith.kapoor.theeisenhowerwaytogtd.fragments;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.smith.kapoor.theeisenhowerwaytogtd.R;
import com.smith.kapoor.theeisenhowerwaytogtd.data.GTDTaskContract;
import com.smith.kapoor.theeisenhowerwaytogtd.data.constants.CompletedEnum;
import com.smith.kapoor.theeisenhowerwaytogtd.data.constants.PriorityEnum;
import com.smith.kapoor.theeisenhowerwaytogtd.data.objects.TaskDO;

/**
 * List Fragment displays task based on user selection.
 */
public class ListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static String ARG_TASKS = "taskType";
    private ListView listView;
    private CursorAdapter adapter;
    private PriorityEnum priorityEnum;

    public ListFragment() {
    }

    /**
     * Create a new instance of ListFragment
     */
    public static ListFragment newInstance(PriorityEnum priorityEnum) {
        ListFragment f = new ListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TASKS, priorityEnum.getPriorityValue());
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // sets menu to visible.
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // inflates xml layout and get the task priority we are looking for
        View v = inflater.inflate(R.layout.fragment_view_task, container, false);

        if (getArguments() != null) {
            priorityEnum = PriorityEnum.getById(getArguments().getInt(ARG_TASKS, 0));
        }

        // set Title
        getActivity().getActionBar().setTitle(priorityEnum.getText());

        // grabs list found in database and displays them
        adapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_list_item_2, null, new String[]{GTDTaskContract.COLUMN_NAME, GTDTaskContract.COLUMN_DESCRIPTION}, new int[]{android.R.id.text1, android.R.id.text2}, 0);

        listView = (ListView) v.findViewById(R.id.taskList);
        listView.setAdapter(adapter);

        // on short click we show Task edit.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = adapter.getCursor();
                if (cursor != null && cursor.moveToPosition(position)) {
                    TaskDO editTask = GTDTaskContract.populateTask(cursor);
                    getFragmentManager().beginTransaction().replace(R.id.container, AddEditFragment.newInstance(editTask, priorityEnum)).addToBackStack(null).commit();
                }
            }
        });

        // On long click we show the complete and delete options.
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = adapter.getCursor();
                if (cursor != null && cursor.moveToPosition(position)) {
                    TaskDO editTask = GTDTaskContract.populateTask(cursor);
                    getFragmentManager().beginTransaction().replace(R.id.container, DetailFragment.newInstance(editTask)).addToBackStack(null).commit();
                    return true;
                } else {
                    return false;
                }
            }
        });

        // This sets up the loader manager.  it detects data changes and refreshes the cursor on a change.
        getLoaderManager().initLoader(0, null, this);

        return v;
    }

    /**
     * Loader is a special class that detects when there is a database change and reloads displayed data.
     *
     * @param id Used If we had multiple cursors on the screen
     * @param args Arguments is for passing in chunks of data.
     * @return
     */
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String selection = GTDTaskContract.COLUMN_PRIORITY + " = ? AND " +
                GTDTaskContract.COLUMN_COMPLETED + " = ? ";

        String[] selectionArgs = {priorityEnum.toString(), CompletedEnum.INCOMPLETE.toString()};

        String orderBy = GTDTaskContract.COLUMN_DATE + " ASC";

        Uri taskURI = GTDTaskContract.CONTENT_URI;

        return new CursorLoader(
                getActivity(),
                taskURI,
                null,
                selection,
                selectionArgs,
                orderBy
        );
    }

    /**
     * When loading is finished refreshes the adapter with the new data.  Allows this to be completed on a separate thread.
     * @param loader the loader that just finished
     * @param data The cursor data
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    /**
     * When we reset the view, we set the adapter to null.
     * @param loader
     */
    @Override
    public void onLoaderReset(Loader loader) {
        adapter.swapCursor(null);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (menu != null) {
            menu.findItem(R.id.action_new).setVisible(true);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

}
