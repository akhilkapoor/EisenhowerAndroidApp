/* Created by Kapoor and Smith */

package com.smith.kapoor.theeisenhowerwaytogtd.sync;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableNotifiedException;

import java.io.IOException;

/**
 * TaskSyncAdapter allows a sync to happen as part of the OS.
 */
public class TaskSyncAdapter extends AbstractThreadedSyncAdapter {

    public final String TAG = TaskSyncAdapter.class.getSimpleName();

    private static final String GOOGLE_TASKS_PREFERENCES = "GOOGLE_TASKS_PREFERENCES";

    private static final String KEY_GOOGLE_TASKS_USER = "KEY_GOOGLE_TASKS_USER";

    private Context mContext;

    public TaskSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);

        mContext = context;
    }

    /**
     * Helper method to have the sync adapter sync immediately
     *
     * @param context The context used to access the account service
     */
    public static void syncImmediately(Context context) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
    }

    /**
     * Gets Google Auth token from Accounts on the phone.
     * @return token
     * @throws IOException
     */
    private String getGoogleAuthToken() throws IOException {

        String googleUserName = mContext.getSharedPreferences(GOOGLE_TASKS_PREFERENCES, Context.MODE_MULTI_PROCESS).getString(KEY_GOOGLE_TASKS_USER, null);

        String token = "";
        try {
            token = GoogleAuthUtil.getTokenWithNotification(mContext, googleUserName, "oauth2:https://www.googleapis.com/auth/tasks", null);
        } catch (UserRecoverableNotifiedException userNotifiedException) {
            Log.e(TAG, userNotifiedException.getMessage(), userNotifiedException);
        } catch (GoogleAuthException authEx) {
            Log.e(TAG, authEx.getMessage(), authEx);
        }
        return token;
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {

    }

}
