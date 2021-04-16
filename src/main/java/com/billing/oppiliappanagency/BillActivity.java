package com.billing.oppiliappanagency;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class BillActivity extends AppCompatActivity {
    private RecyclerView Billview;
    private RecyclerView.LayoutManager layoutManager;
    private List<String> name_of_bar;
    private List<String> quantity_of_bar;
    private List<String> amount_of_bar;
    private List<String> totalamount_of_bar;
    TextView Total_Amount,Gst_amount,Customer_name,Customer_Address;
    AlertDialog.Builder builder;
    EditText input_name,input_address;
    Button input_ok,input_cancel;
    ImageView Showdialog,Share,Back;
    Bitmap picture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        String n,q,a,ta,g;
        String s="";
         //getting the current view of the display..
        View rootView = getWindow().getDecorView().findViewById(android.R.id.content);

        Total_Amount=findViewById(R.id.total_Amount);
        Gst_amount=findViewById(R.id.gst_amount);
        Customer_name=findViewById(R.id.customer_name);
        Customer_Address=findViewById(R.id.customer_address);
        Showdialog=findViewById(R.id.showdialog);
        Share=findViewById(R.id.share);
        Back=findViewById(R.id.backb);

        TextView Date=findViewById(R.id.date);
        TextView Time=findViewById(R.id.time);
        //setting date and time in the text view..
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Object date = dateFormat.format(Calendar.getInstance().getTime());
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        Object time = timeFormat.format(Calendar.getInstance().getTime());
        Time.setText((CharSequence) time);
        Date.setText((CharSequence)date);


        ArrayList<BillModel> billlist=new ArrayList<>();

        Billview=findViewById(R.id.bill_view);
        Billview.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        Billview.setLayoutManager(layoutManager);

        Intent intent=getIntent();
        n= intent.getStringExtra("name_of_bar");
        q= intent.getStringExtra("quantity_of_bar");
        a= intent.getStringExtra("amount_of_bar");
        ta= intent.getStringExtra("totalamount_of_bar");
        name_of_bar= Arrays.asList(n.split(","));
        quantity_of_bar=Arrays.asList(q.split(","));
        amount_of_bar=Arrays.asList(a.split(","));
        totalamount_of_bar=Arrays.asList(ta.split(","));

        for (int i = 0; i <name_of_bar.size(); i++) {
            billlist.add(new BillModel(name_of_bar.get(i),quantity_of_bar.get(i),amount_of_bar.get(i),totalamount_of_bar.get(i)));
        }

        RecyclerView.Adapter adapter = new BillAdapter(billlist);
        Billview.setAdapter(adapter);
        try {
            if(totalamount_of_bar!=null) {
                s = GetTotalSum(totalamount_of_bar);
                g = Gst(s);
                Total_Amount.setText(s);
                Gst_amount.setText(g);
            }else {
                Toast.makeText(this, "Enter All the details...", Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e){
            Toast.makeText(this, "Enter All the details...", Toast.LENGTH_SHORT).show();
        }

        //System.out.println("ListElements " + name_of_bar);
        //creating view from the layout..
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.dialog_layout, null);

        input_name=promptsView.findViewById(R.id.inputname);
        input_address=promptsView.findViewById(R.id.inputaddress);
        input_ok=promptsView.findViewById(R.id.inputok);
        input_cancel=promptsView.findViewById(R.id.inputcancel);

        builder = new AlertDialog.Builder(this);
        builder.setView(promptsView);
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//showing name and address dialog..
        Showdialog.setOnClickListener(v -> alertDialog.show());

//if details entered and ok button is clicked data is added to the variable..
        input_ok.setOnClickListener(v -> {
            if((input_name != null)&&(input_address != null)){
                Customer_name.setText(input_name.getText().toString());
                Customer_Address.setText(input_address.getText().toString());
                input_name.getText().clear();
                input_address.getText().clear();
            }else {
                Toast.makeText(BillActivity.this, "Again enter the details correctly..", Toast.LENGTH_SHORT).show();
            }
           alertDialog.cancel();
        });

        //if cancel is clicked dialog box is dissmised..
        input_cancel.setOnClickListener(v -> alertDialog.cancel());

        String finalS = s;
        final int[] obs = {0};
        Share.setOnClickListener(v -> {
            if(Customer_name.getText().length()!= 0){
                CustomerModel customerModel = null;
                if(obs[0] == 0) {
                    try {
                        customerModel = new CustomerModel(-1, Customer_name.getText().toString(), String.valueOf(name_of_bar.size()), finalS);//adding elements to the list and recyclerview..
                    } catch (Exception ignored) {

                    }
                    DatabaseHelper dataBaseHelper = new DatabaseHelper(BillActivity.this);//creating instance of class..
                    boolean success = dataBaseHelper.addOne(customerModel);//adding list to the db..
                    if (success) {
                        obs[0] = 1;
                        Toast.makeText(BillActivity.this, "Successfully added", Toast.LENGTH_SHORT).show();
                    }
                }
                picture=getScreenShot(rootView);//getting bitmap from view
                storeimage(picture);
            }
            else {
                Toast.makeText(BillActivity.this,"Enter all the details..",Toast.LENGTH_SHORT).show();
            }

        });
        Back.setOnClickListener(v -> {
            startActivity(new Intent(BillActivity.this, HomeActivity.class));
            finish();
        });

    }

    private String Gst(String s) {//calculating gst from total amount..
        double v,g;
        String gst;
        v=Double.parseDouble(s);
        g= (double) Math.round(v * 18 / 100);
        gst=String.valueOf(g);

        return gst;
    }

    private String GetTotalSum(List<String> totalamount_of_bar) {//getting total sum of the amount
        double v=0.0;
        String s;
        if(totalamount_of_bar != null) {
            for (int i = 0; i < totalamount_of_bar.size(); i++) {
                v = Double.parseDouble(totalamount_of_bar.get(i)) + v;
            }
            s=String.valueOf(v);
            return s;
        }
        return null;
    }
    private void storeimage(Bitmap picture) {//storing image to the storage and getting its uri..
        OutputStream fos;
        try{
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {//executes whether the device is android 9 or below...
                if(ActivityCompat.checkSelfPermission(BillActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
                String root = Environment.getExternalStorageDirectory().toString();
                File myDir = new File(root + "/Agency Estimation");
                myDir.mkdirs();

                //String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String fname = "Screenshot.jpg";

                File file = new File(myDir, fname);
                //if (file.exists()) file.delete ();
                try {
                    FileOutputStream out = new FileOutputStream(file);
                    picture.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                share(Uri.fromFile(file));}
            }else {//executes whether the device is android 10 or above...

                ContentResolver resolver = getContentResolver();
                ContentValues contentValues = new ContentValues();
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "Screenshot.jpg");
                contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
                contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + File.separator + "Agency Estimation");

                Uri imageuri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                fos = resolver.openOutputStream(Objects.requireNonNull(imageuri));
                //Toast.makeText(this,"Image Saved",Toast.LENGTH_LONG).show();
                picture.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                Objects.requireNonNull(fos);
                share(imageuri);

            }

        }
        catch (Exception e){
            Toast.makeText(this,e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }
    public static Bitmap getScreenShot(View view) {//converts the view to bitmap..
        View screenView = view.getRootView();
        screenView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
        screenView.setDrawingCacheEnabled(false);
        return bitmap;
    }
    private void share(Uri uri){//sharing the image
        StrictMode.VmPolicy.Builder builder=new StrictMode.VmPolicy.Builder();//of defining stictmode policies within your application is
        // to force you in the development phase to make your application more well behaved within the device it is running on:
        StrictMode.setVmPolicy(builder.build());
        Intent shareint;
        try {
            shareint=new Intent(Intent.ACTION_SEND);
            shareint.setType("image/*");
            shareint.putExtra(Intent.EXTRA_STREAM,uri);
            shareint.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        startActivity(Intent.createChooser(shareint,"Share Image"));
    }
}