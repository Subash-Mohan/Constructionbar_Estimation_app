package com.billing.oppiliappanagency;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity {
    private RecyclerView Historyview;
    private RecyclerView.Adapter adapter;
    ImageView Delete;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ImageView Back=findViewById(R.id.back);
        Historyview=findViewById(R.id.historyView);
        Delete=findViewById(R.id.delete);
        Historyview.setHasFixedSize(true);//by setting this the recycler view width and height never changes whenever the item inserted, moved or removed ...
        //creating the instance for databasehelper class
        DatabaseHelper dataBaseHelper=new DatabaseHelper(History.this);

        List<CustomerModel> everyone=dataBaseHelper.getEveryone();//here we are getting all the data in the db..
        //setting layoutmanager for an recycler view..
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        Historyview.setLayoutManager(layoutManager);

        //System.out.println("finallist"+everyone);
        ArrayList<HistoryModel> historylist=new ArrayList<>();
        if(everyone.size()!= 0) {//if everyone list is not empty we are adding elements in the list everyone to the history list for passing it to the recycler view...
            for (int i = 0; i < everyone.size(); i++) {
                historylist.add(new HistoryModel(everyone.get(i).getCustomer_name(), everyone.get(i).getTotal_amount(), everyone.get(i).getNumber_of_products()));
            }
            //System.out.println("finallist"+historylist);
            adapter=new Historyadapter(historylist);
            Historyview.setAdapter(adapter);
            //here we are setting background as null if the items added in the recycler view..
            Historyview.setBackground(null);
        }
//Creating an view from the layout to setview of the dialog box..
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.confirmation_layout, null);
        //buttons of dialog box
        Button Yes=promptsView.findViewById(R.id.yes);
        Button No=promptsView.findViewById(R.id.no);

        builder = new AlertDialog.Builder(this);
        //setting view of dialog box..
        builder.setView(promptsView);
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Back.setOnClickListener(v -> startActivity(new Intent(History.this,HomeActivity.class)));
        //while deleting the history showing the confirmation dialog..
        Delete.setOnClickListener(v -> alertDialog.show());
        //clear the data in the historylist and the database..
        Yes.setOnClickListener(v -> {
            DatabaseHelper databaseHelper=new DatabaseHelper(History.this);
            databaseHelper.deleteCourse();
            historylist.clear();
            adapter=new Historyadapter(historylist);
            Historyview.setAdapter(adapter);
            alertDialog.dismiss();
            //setting the background of recycler view if no items are in recycler view..
            Historyview.setBackgroundResource(R.drawable.ic_asset_12);
        });


        No.setOnClickListener(v -> alertDialog.cancel());

    }
}