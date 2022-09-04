package com.hk.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button button1, button2, button3, button4;
    EditText editText;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        editText = findViewById(R.id.edtEnterText);
        textView = findViewById(R.id.textView);
        String message = editText.getText().toString();

        Intent broadcastIntent = new Intent(this, NotificationReceiver.class);
        broadcastIntent.putExtra("Notification_ID", 1);

        PendingIntent pendingIntent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getBroadcast
                    (this, 0, broadcastIntent, PendingIntent.FLAG_MUTABLE);
        }
        else
        {
            pendingIntent = PendingIntent.getBroadcast
                    (this, 0, broadcastIntent, PendingIntent.FLAG_ONE_SHOT);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel("hi", "My notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);

        }

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = new Toast(getApplicationContext());
                toast.makeText(MainActivity.this, editText.getText(), toast.LENGTH_SHORT).show();
            }
        });

        PendingIntent finalPendingIntent = pendingIntent;
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MainActivity.this);

                NotificationCompat.Builder mbuilder = new NotificationCompat.Builder(MainActivity.this)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle("Notification is here!")
                        .setContentText("The entered text is: " + editText.getText())
                        .setOngoing(true)
                        .setChannelId("hi")
                        .addAction(R.mipmap.ic_launcher, "Dismiss", finalPendingIntent);


                notificationManager.notify(1, mbuilder.build());

            }
        });

        // TODO Add red color
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MainActivity.this)
                        //.setMessage("<font color='#E91E63'>" + "The Enter text is: " + editText.getText() + "</font>")
                        .setMessage(Html.fromHtml("<font color='#FF7F27'>The Enter text is: </font>" + editText.getText()))
                        .setCancelable(false)
                        .setPositiveButton("OK!", null)
                        .show();

            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("COMPLETED!");
            }
        });
    }
}