package com.cleveertaprudderstack;

import io.invertase.firebase.messaging.ReactNativeFirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.clevertap.android.sdk.CleverTapAPI;
import android.os.Bundle; 
import android.util.Log;
import java.util.Map;
import com.clevertap.android.sdk.pushnotification.NotificationInfo;

public class MyFCMListenerService extends ReactNativeFirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage message){
        try {
            if (message.getData().size() > 0) {
                Bundle extras = new Bundle();
                for (Map.Entry<String, String> entry : message.getData().entrySet()) {
                    extras.putString(entry.getKey(), entry.getValue());
                }
                NotificationInfo info = CleverTapAPI.getNotificationInfo(extras);
                if (info.fromCleverTap) {
// over here got is channel id which you will have to use while sending push notification from CleverTap Dashboard //and this is required for Android Os 8.0 and above.

                    CleverTapAPI.createNotification(getApplicationContext(), extras);
                } else {
                    // not from CleverTap handle yourself or pass to another provider
                    super.onMessageReceived(message);
                }
            }
        } catch (Throwable t) {
           Log.d("MYFCMLIST", "Error parsing FCM message", t);
        }
    }
}


    