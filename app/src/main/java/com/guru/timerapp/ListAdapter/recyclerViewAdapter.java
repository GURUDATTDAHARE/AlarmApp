package com.guru.timerapp.ListAdapter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.guru.timerapp.MainActivity;
import com.guru.timerapp.Modal.Data;
import com.guru.timerapp.Mybroadcast;
import com.guru.timerapp.R;
import com.guru.timerapp.Sqlite.DataBaseHandler;

import java.util.List;


public class recyclerViewAdapter extends RecyclerView.Adapter<recyclerViewAdapter.ViewHolder> {
   private List<Data> list;
    private Context context;
    private  AlarmManager alarmManager;

   public recyclerViewAdapter(List<Data> list,Context context,AlarmManager alarmManager) {
        this.list = list;
        this.context=context;
        this.alarmManager=alarmManager;
    }

 //   public recyclerViewAdapter(Context context) {
      //  this.context = context;
   // }

    public recyclerViewAdapter(MainActivity mainActivity) {
    }


    @Override
    public recyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.row_list,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerViewAdapter.ViewHolder holder, final int position) {
           Data data = list.get(position);
           holder.time.setText(data.getTime());
           holder.cancle.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   DataBaseHandler db= new DataBaseHandler(context);
                   Data d=new Data();
                    d=list.get(position);
                     db.deleteItem(d.getAlarmId());
                   /*Intent i=new Intent(context, Mybroadcast.class);
                   PendingIntent pendingIntent=PendingIntent.getBroadcast(context,d.getAlarmId(),i,0);
                   alarmManager.cancel(pendingIntent);*/
                     list.remove(position);
                     notifyItemRemoved(position);

                   Log.d("position ","recycler view "+position);

               }
           });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView time;
        public Button cancle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time=itemView.findViewById(R.id.Time_view_list);
            cancle=itemView.findViewById(R.id.delete_time_list);

        }
    }
}
