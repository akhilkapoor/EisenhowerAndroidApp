/* Created by Kapoor and Smith */

 package com.smith.kapoor.theeisenhowerwaytogtd;

import android.app.Activity;
import android.app.ActionBar;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.smith.kapoor.theeisenhowerwaytogtd.fragments.AddEditFragment;
import com.smith.kapoor.theeisenhowerwaytogtd.fragments.MainFragment;

/**
 * Main Activity of GTD.  Sets up interface menues and the first fragment.
 */
public class GTDActivity extends Activity {

    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gtd);
        // if we starting activity for the first time
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, MainFragment.newInstance(), MainFragment.FRAGMENT_TAG)
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gtd, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // controls visibility and action of the Action Bar in android
        switch (item.getItemId()) {
            case android.R.id.home:
                onHomeSelected();
                ActionBar actionBar = getActionBar();
                actionBar.setDisplayHomeAsUpEnabled(false);
                actionBar.setHomeButtonEnabled(false);
                return true;
            case R.id.action_new:
                getFragmentManager().beginTransaction().replace(R.id.container, AddEditFragment.newInstance(null, MainFragment.recentPriority)).addToBackStack(null).commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Regulates how we get back to te main fragment of the activity
     */
    public void onHomeSelected(){
        FragmentManager manager = getFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        MainFragment taskFragment = (MainFragment)getFragmentManager().findFragmentByTag(MainFragment.FRAGMENT_TAG);
        ft.remove(taskFragment);
        ft.commit();
        manager.popBackStack();
    }

    // Taken from the following source and modified to work with fragments
    // http://stackoverflow.com/questions/8430805/android-clicking-twice-the-back-button-to-exit-activity
    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else
            getFragmentManager().popBackStack();
    }

}
