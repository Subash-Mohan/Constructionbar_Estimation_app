package com.billing.oppiliappanagency;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.BillViewHolder> {

    private ArrayList<BillModel> Billlist;

    public class BillViewHolder extends RecyclerView.ViewHolder {
        public TextView b_name;
        public TextView b_quantity;
        public TextView b_amount;
        public TextView b_totalamount;
        public BillViewHolder(@NonNull View itemView) {
            super(itemView);
            b_name=itemView.findViewById(R.id.b_barname);
            b_quantity=itemView.findViewById(R.id.b_quantity);
            b_amount=itemView.findViewById(R.id.b_amount);
            b_totalamount=itemView.findViewById(R.id.b_tamount);
        }
    }
    public BillAdapter(ArrayList<BillModel> billlist) {
        Billlist=billlist;
    }

    @Override
    public BillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.billtable, parent, false);
        BillViewHolder Bvh = new BillViewHolder(v);
        return Bvh;
    }

    @Override
    public void onBindViewHolder(@NonNull BillViewHolder holder, int position) {
        BillModel currentItem = Billlist.get(position);
        holder.b_name.setText(currentItem.getBname());
        holder.b_quantity.setText(String.valueOf( currentItem.getQ_bill()));
        holder.b_amount.setText(String.valueOf(currentItem.getA_bill()));
        holder.b_totalamount.setText(String.valueOf(currentItem.getTa_bill()));

    }

    @Override
    public int getItemCount() {
        return Billlist.size();
    }


}
