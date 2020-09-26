package com.guru.timerapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.guru.timerapp.ListAdapter.recyclerViewAdapter;
import com.guru.timerapp.Modal.Data;
import com.guru.timerapp.Sqlite.DataBaseHandler;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
private TextView t1,t2,t3,t4;
private AlertDialog.Builder builder;
private AlertDialog dialog;
private Button b;
//  Alarme instance
private Intent i;
private PendingIntent pendingIntent;
private AlarmManager alarmManager;

//popup inctence

    private EditText hour;
    private EditText minat;
    private Button popup_button;

    // recycler view
   public  List<Data> list;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;

    //Sql
    private DataBaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // reccler view
        recyclerView=findViewById(R.id.myrecycler);
        recyclerView.setHasFixedSize(true);
     layoutManager=new LinearLayoutManager(this);
         recyclerView.setLayoutManager(layoutManager);
        db =new DataBaseHandler(this);
        list=db.getAllContact();
        recyclerViewAdapter myadapter= new recyclerViewAdapter(list,this,alarmManager);
       recyclerView.setAdapter(myadapter);

       //Alarmmaneger
       /* alarmManager=(AlarmManager) MainActivity.this.getSystemService(MainActivity.ALARM_SERVICE);
        i=new Intent(MainActivity.this, Mybroadcast.class);*/

        ////   clock
        t1=findViewById(R.id.textView1);
        t2=findViewById(R.id.textView2);
        t3=findViewById(R.id.textView3);
        t4=findViewById(R.id.textMillion);
        b=findViewById(R.id.button);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    builder=new AlertDialog.Builder(MainActivity.this);
                    View view=getLayoutInflater().inflate(R.layout.pop_up,null);
                    // popup
                   hour=view.findViewById(R.id.sethour);
                   minat=view.findViewById(R.id.setminat);
                   popup_button=view.findViewById(R.id.setbutton);
                     builder.setView(view);
                   dialog=builder.create();
                   dialog.show();

                    popup_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // geting time for alarm
                            if(hour.getText().toString().isEmpty()&&minat.getText().toString().isEmpty())
                            {
                                Toast.makeText(MainActivity.this,"SET TIME..",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                int gethour = Integer.parseInt(hour.getText().toString());
                                int getminat = Integer.parseInt(minat.getText().toString());
                                // int getsecond =Integer.parseInt(second.getText().toString());
                                //SQlite part
                                String timeText = TimePatten(gethour, getminat);
                                DataBaseHandler dataBaseHandler = new DataBaseHandler(getApplicationContext());
                                Data data = new Data();
                                data.setTime(timeText);
                                dataBaseHandler.addItem(data);

                                //setAlarm
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(Calendar.HOUR, gethour);
                                calendar.set(Calendar.MINUTE, getminat);
                                calendar.set(Calendar.SECOND, 0);
                                Data data1 = dataBaseHandler.getAlarm_id();
                                setAlarm(calendar, data1.getAlarmId());
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                                dialog.dismiss();
                            }
                        }
                    });
            }
        });
        clock();

        for(int i=0;i<list.size();i++)
        {
            Log.d("list ","loop "+list.get(i).getAlarmId()+" "+list.get(i).getTime());
        }
        List<Data> list1=db.getAllContact();
        for(int i=0;i<list1.size();i++)
        {
            Log.d("list1    ","loop "+list1.get(i).getAlarmId()+" "+list1.get(i).getTime());
        }
    }
    public void clock()
    {
        final Handler handler=new Handler();
          handler.post(new Runnable() {
              @Override
              public void run() {
                  Calendar calendar= Calendar.getInstance();
                  t1.setText(String.valueOf(calendar.get(Calendar.HOUR)));
                  t2.setText(String.valueOf(calendar.get(Calendar.MINUTE)));
                  t3.setText(String.valueOf(calendar.get(Calendar.SECOND)));
                  t4.setText(String.valueOf(calendar.getTimeInMillis()));
                  handler.postDelayed(this,1000);
              }
          });


        }
        public void  setAlarm(Calendar calendar,int a)
        {   //Calendar calendar=Calendar.getInstance();
            i=new Intent(MainActivity.this, Mybroadcast.class);
             pendingIntent= PendingIntent.getBroadcast(getApplicationContext(),a,i,0);
            alarmManager=(AlarmManager) MainActivity.this.getSystemService(MainActivity.ALARM_SERVICE);
           //assert alarmManager != null;
          // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            assert alarmManager != null;
                alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
              // alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                  //     SystemClock.elapsedRealtime() +
                   //            (x * 1000), pendingIntent);

         //  }
            Toast.makeText(MainActivity.this,"Al.....",Toast.LENGTH_LONG).show();
            Log.d("guru","times "+calendar.getTimeInMillis()+" "+System.currentTimeMillis()+" "+a);
        }
        public String TimePatten(int hour,int minat)
        {
            String s= String.valueOf(hour)+" : "+String.valueOf(minat)+" ";
            return s;
        }

    }