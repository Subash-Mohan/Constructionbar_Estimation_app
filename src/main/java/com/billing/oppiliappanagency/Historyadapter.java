package com.billing.oppiliappanagency;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Historyadapter extends RecyclerView.Adapter<Historyadapter.HistoryViewHolder> {
    private ArrayList<HistoryModel> Historylist;
    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        public TextView Name;
        public TextView Quantity;
        public TextView Amount;
        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            Name=itemView.findViewById(R.id.hisname);
            Quantity=itemView.findViewById(R.id.hisproducts);
            Amount=itemView.findViewById(R.id.hisamount);
        }

    }
    public Historyadapter(ArrayList<HistoryModel> historylist) {
        Historylist=historylist;
    }
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_view, parent, false);
        HistoryViewHolder Hvh = new HistoryViewHolder(v);
        return Hvh;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        HistoryModel currentItem = Historylist.get(position);

        holder.Name.setText(currentItem.getHisname());
        holder.Quantity.setText(String.valueOf( currentItem.getHisamount()));//oppositely changed
        holder.Amount.setText(String.valueOf(currentItem.getHisquantity()));

    }

    @Override
    public int getItemCount() {
        return Historylist.size();
    }


}
