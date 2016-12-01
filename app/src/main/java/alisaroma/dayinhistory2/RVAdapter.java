package alisaroma.dayinhistory2;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Roma-Alisa on 08/09/16.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.EventViewHolder>{

    ArrayList<String> event;

    RVAdapter( ArrayList<String> event)
    {
        this.event = event;
    }
    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        //View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_layout, viewGroup, false);
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_layout, viewGroup, false);
        EventViewHolder pvh = new EventViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(EventViewHolder eventViewHolder, int i) {
        int index=0;
        String tempYear="",tempEvent="";

        tempEvent = event.get(i);
        index = event.get(i).indexOf(8211);
        if(index>-1) {
            tempYear = event.get(i);
            tempYear = tempYear.substring(0, index);
            tempEvent = tempEvent.replace(tempYear, "");
        }
        eventViewHolder.year.setText(tempYear);
        eventViewHolder.event.setText(tempEvent);
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    @Override
    public int getItemCount() {
        return event.size();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView year;
        TextView event;
        EventViewHolder(View itemView) {
            super(itemView);
           /* cv = (CardView)itemView.findViewById(R.id.card_football_clubs);
            name = (TextView)itemView.findViewById(R.id.name);
            image = (ImageView)itemView.findViewById(R.id.icon_image);*/
            cv = (CardView)itemView.findViewById(R.id.card_event);
            year = (TextView)itemView.findViewById(R.id.year_bold);
            event = (TextView)itemView.findViewById(R.id.event);
            //cv.setBackgroundResource(R.drawable.card);
        }
    }

}