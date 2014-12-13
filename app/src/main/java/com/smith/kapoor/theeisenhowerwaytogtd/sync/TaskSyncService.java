/* Created by Kapoor and Smith */

package com.smith.kapoor.theeisenhowerwaytogtd.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Task Sync Service allows a sync with Google Task to be run in a background thread.
 */
public class TaskSyncService extends Service {

    private static final Object sSyncAdapterLock = new Object();
    private static TaskSyncAdapter sTaskSyncAdapter = null;

    @Override
    public void onCreate() {
        Log.d("TaskSyncService", "onCreate - TaskSyncService");
        synchronized (sSyncAdapterLock) {
            if (sTaskSyncAdapter == null) {
                sTaskSyncAdapter = new TaskSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sTaskSyncAdapter.getSyncAdapterBinder();
    }
}
