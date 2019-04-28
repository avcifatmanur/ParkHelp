package com.parkhelp;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;
public class SmsTrigger extends BroadcastReceiver {


    final SmsManager sms = SmsManager.getDefault();

    public void onReceive(Context context, Intent intent) {


        final Bundle bundle = intent.getExtras();

        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String gonderen = phoneNumber;
                    String mesaj = currentMessage.getDisplayMessageBody();

                    if (mesaj.toString().equals("Arabayı çeker misiniz.Acil!")){
                        Toast toast = Toast.makeText(context,
                                " Sms içeriği:"  + mesaj, Toast.LENGTH_LONG);
                        toast.show();
                        Vibrator vibrator=(Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
                        long pattern[] = {60,120,180,240,300,360,420,480}; //titreşim örüntüsü
                        vibrator.vibrate(pattern,-1); //titreşim
                        //Zil sesini çal
                        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                        Ringtone r = RingtoneManager.getRingtone(context, notification);
                        r.play();

                    }

                }
            }

        } catch (Exception e) {
            Log.e("smstrigger", "Exception" + e);

        }
    }
}