package hero.huy.smssync.mybroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.net.DatagramSocket;

import hero.huy.smssync.model.CommandMessage;
import hero.huy.smssync.model.CommandType;
import hero.huy.smssync.myservice.SmsSyncService;
import hero.huy.smssync.util.ConstantValues;

public class IncomingSmsReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {

        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();

        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String message = currentMessage.getDisplayMessageBody();

                    Log.i("SmsReceiver", "senderNum: " + phoneNumber + "; message: " + message);


                    // Show Alert
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context,
                            "senderNum: "+ phoneNumber + ", message: " + message, duration);
                    toast.show();

                    CommandMessage cmd = new CommandMessage(CommandType.IN_COMING_SMS,phoneNumber,message,"192.168.137.1");
                    
                    Intent proIntent = new Intent(context, SmsSyncService.class);
                    proIntent.setAction(ConstantValues.ACTION_IN_SMS);
                    proIntent.putExtra(ConstantValues.EXTRA_COMMAND_MESSAGE, cmd);
                    context.startService(proIntent);

                } // end for loop
            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);
        }
    }
}