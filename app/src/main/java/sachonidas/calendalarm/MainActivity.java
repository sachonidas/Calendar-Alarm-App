package sachonidas.calendalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    AlarmManager alarmManager;
    TimePicker timePicker;
    Context context;
    PendingIntent pendingIntent;

    private TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = this;

        tv1 = (TextView)findViewById(R.id.tv1);
        timePicker = (TimePicker)findViewById(R.id.timePicker);
        alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

        final Calendar calendar =  Calendar.getInstance();

        Button btnAlarma = (Button)findViewById(R.id.btnAlarma);

        final Intent intent = new Intent(this.context, AlarmReceiver.class);

        btnAlarma.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                calendar.set(Calendar.HOUR_OF_DAY,timePicker.getHour());
                calendar.set(Calendar.MINUTE,timePicker.getMinute());

                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();

                String hourString = String.valueOf(hour);
                String minuteString = String.valueOf(minute);

                if ( hour > 12 ){
                    hourString = String.valueOf(hour - 12);
                }
                if (minute < 10){
                    minuteString = "0" + String.valueOf(minute);
                }

                set_alarm_text("Alarma encendida a las "+ hourString + " : " +minuteString);

                intent.putExtra("extra","yes");

                pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
        });

        Button btnQuitarAlarama = (Button)findViewById(R.id.btnQuitarAlarma);

        btnQuitarAlarama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                set_alarm_text("Alarma apagada!!!");

                alarmManager.cancel(pendingIntent);

                intent.putExtra("extra","no");

                sendBroadcast(intent);
            }
        });
    }

    private void set_alarm_text(String s) {
        tv1.setText(s);
    }

    public void estableceAlarma(View view){


    }

}
