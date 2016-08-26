package sachonidas.calendalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Usuario on 27/08/2016.
 */
public class AlarmReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("In the receiver", "Yeah");

        String getYourString = intent.getExtras().getString("extra");

        Log.e("What is the key?", getYourString);

        Intent serviceIntent = new Intent(context, RingtonePlayingService.class);

        serviceIntent.putExtra("extra", getYourString);

        context.startService(serviceIntent);

    }


}
