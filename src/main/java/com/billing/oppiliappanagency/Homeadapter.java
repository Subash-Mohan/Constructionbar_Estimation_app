package com.billing.oppiliappanagency;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Homeadapter extends RecyclerView.Adapter<Homeadapter.HomeViewHolder> {
    private ArrayList<ReViewModel> Homelist;
    private OnItemClick mCallback;

    public class HomeViewHolder extends RecyclerView.ViewHolder {
        public TextView Barname;
        public TextView Quantity;
        public TextView Amount;
        public TextView Totalamount;
        ImageView Close;
        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            Barname=itemView.findViewById(R.id.barname);
            Quantity=itemView.findViewById(R.id.quantity);
            Amount=itemView.findViewById(R.id.amount);
            Totalamount=itemView.findViewById(R.id.totalamount);
            Close=itemView.findViewById(R.id.close);
        }
    }
    public Homeadapter(ArrayList<ReViewModel> homelist, OnItemClick listener) {
        Homelist=homelist;
        this.mCallback=listener;
    }

    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_view, parent, false);
        HomeViewHolder Hvh = new HomeViewHolder(v);
        return Hvh;

    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        ReViewModel currentItem = Homelist.get(position);

        holder.Barname.setText(currentItem.getBarname());
        holder.Quantity.setText(String.valueOf( currentItem.getQuantity()));
        holder.Amount.setText(String.valueOf(currentItem.getAmount()));
        holder.Totalamount.setText(String.valueOf( currentItem.getTotalamount()));

        holder.Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Homelist.remove(position);
                notifyDataSetChanged();
                mCallback.onClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return Homelist.size();
    }


}
