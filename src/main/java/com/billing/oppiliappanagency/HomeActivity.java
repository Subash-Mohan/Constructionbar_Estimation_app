package com.billing.oppiliappanagency;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements OnItemClick{
    private RecyclerView Homeview;
    private RecyclerView.Adapter adapter;
    private EditText Barname;
    private EditText Quantity;
    private EditText Amount;
    String tBarname;
    Double tQuantity;
    Double tAmount;
    Double tTotalamount;
    private List<String> name_of_bar;
    private List<String> quantity_of_bar;
    private List<String> amount_of_bar;
    private List<String> totalamount_of_bar;
    private final int STORAGE_PERMISSION_CODE = 1024;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//here we are checking the android version and the WRITE_EXTERNAL_STORAGE permission for android below pie..
        if (android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.P){
            if(ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                requestStoragePermission();
            }}

        ImageView History=findViewById(R.id.history);
        ImageView next=findViewById(R.id.add_item);
        Barname=findViewById(R.id.barname);
        Quantity=findViewById(R.id.quantity);
        Amount=findViewById(R.id.amount);
        Button Add=findViewById(R.id.add_button);
        Button Cancel=findViewById(R.id.cancel_button);



        name_of_bar=new ArrayList<>();
        quantity_of_bar=new ArrayList<>();
        amount_of_bar=new ArrayList<>();
        totalamount_of_bar=new ArrayList<>();

        Homeview=findViewById(R.id.items);
        Homeview.setHasFixedSize(true);//by setting this the recycler view width and height never changes whenever the item inserted, moved or removed ...
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);//adding layout manager for recyler view..
        Homeview.setLayoutManager(layoutManager);



        ArrayList<ReViewModel> homelist=new ArrayList<>();

        History.setOnClickListener(v -> {
            if(name_of_bar.size()==0) {//calling the history activity by checking whether the items are ready for billing or not..
                startActivity(new Intent(HomeActivity.this, History.class));
            }
        });
        next.setOnClickListener(v -> {//here we are sending all the inputs to billing activity through intents as an concatinated string..
            try{
                if(totalamount_of_bar.size()!=0){
                    String n=GetcatString(name_of_bar);
                    String q=GetcatString(quantity_of_bar);
                    String a=GetcatString(amount_of_bar);
                    String ta=GetcatString(totalamount_of_bar);
                    Intent intent=new Intent(HomeActivity.this, BillActivity.class);
                    intent.putExtra("name_of_bar",n);
                    intent.putExtra("quantity_of_bar",q);
                    intent.putExtra("amount_of_bar",a);
                    intent.putExtra("totalamount_of_bar",ta);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(HomeActivity.this, "Enter the details for billing", Toast.LENGTH_SHORT).show();
                }
        }catch (Exception e){
                Toast.makeText(HomeActivity.this, "Enter the details for billing", Toast.LENGTH_SHORT).show();
            }

            //Toast.makeText(MainActivity.this,"Clicked",Toast.LENGTH_SHORT).show();



        });
        Add.setOnClickListener(v -> {// here we are getting the input from edittext and displaying in the recycler view and storing it in array for sending it to next
            // activity as a concatenated string..
            try{
                if(isNotEmpty(Barname)){
                    tBarname=Barname.getText().toString();
                    tQuantity=Double.parseDouble(String.valueOf(Quantity.getText()));
                    tAmount=Double.parseDouble(String.valueOf(Amount.getText()));
                    tTotalamount= (double) Math.round(tQuantity * tAmount);
                    name_of_bar.add(tBarname);
                    quantity_of_bar.add(String.valueOf(tQuantity));
                    amount_of_bar.add(String.valueOf(tAmount));
                    totalamount_of_bar.add(String.valueOf(tTotalamount));
                    Barname.getText().clear();
                    Quantity.getText().clear();
                    Amount.getText().clear();
                    //here we are adding and passing the list to adapter of home recyclerview for displaying..
                    homelist.add(new ReViewModel(tBarname,tQuantity,tAmount,tTotalamount));
                    adapter=new Homeadapter(homelist, HomeActivity.this);
                    Homeview.setAdapter(adapter);

                }
            } catch (Exception e){
                Toast.makeText(HomeActivity.this,"Enter the details Correctly",Toast.LENGTH_SHORT).show();
            }


        });


    }
    private String GetcatString(List<String> Alist) {//here we are concating the elements of array as a string..
        StringBuilder s= new StringBuilder();
        for (int i = 0; i < Alist.size(); i++) {
            s.append(Alist.get(i));
            if(i<Alist.size()-1){
                s.append(",");
            }

        }
        System.out.println("stringcaT"+s);
        return s.toString();
    }

    private boolean isNotEmpty(EditText etText) {//checking whether the edittext is empty or not..
        return etText.getText().toString().trim().length() > 0;
    }

    @Override
    public void onClick(int value) { //here removing the elements in the array by getting value from deleted item in recycler view through interface..
        name_of_bar.remove(value);
        quantity_of_bar.remove(value);
        amount_of_bar.remove(value);
        totalamount_of_bar.remove(value);

    }
    private void requestStoragePermission() {  //Asking for WRITE_EXTERNAL_STORAGE permission..
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed for storing screenshot in your device ")
                    .setPositiveButton("ok", (dialog, which) -> ActivityCompat.requestPermissions(HomeActivity.this,
                            new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE))
                    .setNegativeButton("cancel", (dialog, which) -> dialog.dismiss())
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE)  {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
}