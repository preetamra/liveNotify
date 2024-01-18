package com.livenotifytest2;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import java.util.Map;
import java.util.HashMap;

public class CustomModule extends ReactContextBaseJavaModule {

    NotificationManager notificationManager = (NotificationManager) getReactApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
    RemoteViews notificationLayout = new RemoteViews(getReactApplicationContext().getPackageName(), R.layout.notification_collapsed);
    RemoteViews notificationLayoutExpanded = new RemoteViews(getReactApplicationContext().getPackageName(), R.layout.notification_expanded);
    CustomModule(ReactApplicationContext context) {
        super(context);
    }

    @NonNull
    @Override
    public String getName() {
        return "CustomModule";
    }

    private void sendNotification(Promise promise) {
        try{
            // Get the layouts to use in the custom notification
            String id = "my_channel_01";
            CharSequence name = getReactApplicationContext().getString(R.string.app_name);
            int importance = NotificationManager.IMPORTANCE_MAX;
            // Apply the layouts to the notification.
            Notification customNotification = new NotificationCompat.Builder(getReactApplicationContext(), "Channel1")
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setCustomContentView(notificationLayout)
                    .setCustomBigContentView(notificationLayoutExpanded)
                    .setChannelId(id)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setSilent(true)
                    .setOngoing(true)
                    .build();

            NotificationChannel mChannel = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                mChannel = new NotificationChannel(id, name,NotificationManager.IMPORTANCE_DEFAULT);
                // Configure the notification channel.
                mChannel.enableVibration(true);

                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

                mChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

                notificationManager.createNotificationChannel(mChannel);
            }
            //show the notification
            notificationManager.notify(1,customNotification);
            promise.resolve("notification Created");
        }catch (Exception e) {
            promise.reject(e);
        }
    }

    @ReactMethod
    public void createNotification(Promise promise) {
        try{
            sendNotification(promise);
        }catch (Exception e) {

        }
    }

    @ReactMethod
    public void updateNotification(String src,String srcTime,String des,String desTime,String flightNo,Promise promise)
    {
        try{
            notificationLayout.setTextViewText(R.id.src,src);
            notificationLayout.setTextViewText(R.id.des,des);
            notificationLayout.setTextViewText(R.id.srctime,srcTime);
            notificationLayout.setTextViewText(R.id.desTime,desTime);
            notificationLayout.setTextViewText(R.id.flightNo,flightNo);
            notificationLayout.setTextColor(R.id.srctime, Color.rgb(255,20,20));
            notificationLayout.setTextColor(R.id.desTime, Color.rgb(20,255,20));
            sendNotification(promise);
        }catch (Exception e)
        {

        }
    }
}