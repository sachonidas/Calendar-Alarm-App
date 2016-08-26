package sachonidas.calendalarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Usuario on 27/08/2016.
 */
public class RingtonePlayingService extends Service {

    MediaPlayer mediaPlayer;
    int startId;
    boolean isRunning;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId){

        Log.e("In the service", "startcommand");

        String estado = intent.getExtras().getString("extra");

        Log.e("Ringtone extra is ", estado);


        assert  estado != null;
        switch (estado){
            case "yes":
                startId = 1;
                break;
            case "no":
                startId = 0;
                break;
            default:
                startId = 0;
                break;
        }

        if (!this.isRunning && startId == 1){

            Log.e("there is no music","and you want to start");

            mediaPlayer = MediaPlayer.create(this, R.raw.Mind_Feat_Kai);
            mediaPlayer.start();

            this.isRunning = true;
            this.startId = 0;

            NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

            Intent intentMainAcitivity = new Intent(this.getApplicationContext(), MainActivity.class);

            PendingIntent pendingIntentMainActivity = PendingIntent.getActivity(this, 0, intentMainAcitivity, 0);

            Notification notificacionPopUp = new Notification.Builder(this)
                    .setContentTitle("An alarm is going of")
                    .setContentText("Click me!")
                    .setContentIntent(pendingIntentMainActivity)
                    .setAutoCancel(true)
                    .build();

            notificationManager.notify(0, notificacionPopUp);


        }else if (this.isRunning && startId == 0){

            Log.e("there is  music","and you want end");

            mediaPlayer.stop();
            mediaPlayer.reset();

            this.isRunning = false;
            this.startId = 0;

        }else if (!this.isRunning && startId == 0){

            Log.e("there is  music","and you want start");

            this.isRunning = false;
            this.startId = 0;

        }else if (this.isRunning && startId == 1){

            Log.e("there is  music","and you want end");

            this.isRunning = true;
            this.startId = 1;

        }else{

            Log.e("else","somehow you reached this");


        }



        return START_NOT_STICKY;

    }

    @Override
    public void onDestroy(){
        Log.e("on Destroy called", "ta da");

        super.onDestroy();
        this.isRunning = false;
    }
}
