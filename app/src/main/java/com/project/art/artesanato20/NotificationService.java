package com.project.art.artesanato20;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class NotificationService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.project.art.artesanato20.action.FOO";
    private static final String ACTION_BAZ = "com.project.art.artesanato20.action.BAZ";
    public static final int uniqueID = 634;
    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.project.art.artesanato20.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.project.art.artesanato20.extra.PARAM2";

    public NotificationService() {
        super("NotificationService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            System.out.println("gato");
            if (intent != null) {
                System.out.println("gato");
                NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                String channelId = "Notification Artesanato";

                Notification notification = new Notification.Builder(this)
                        .setContentTitle("123")
                        .setContentText("456")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setChannelId(channelId)
                        .build();

                nm.notify(uniqueID, notification);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
