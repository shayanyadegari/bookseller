package com.example.shayan.bookmarket;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class firstpage extends AppCompatActivity {

    //private final String cat="http://10.0.2.2/test/get_categori.php";
    //private final String ads="http://10.0.2.2/test/get_data2.php?page=";
    //private final String user_inser_ads="http://10.0.2.2/test/set_data.php";
    private final String ads="http://mrpi.ir/shtest/get_data2.php?page=";
    private final String user_inser_ads="http://mrpi.ir/shtest/set_data.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstpage);

        if (netconnect()==false){

            AlertDialog.Builder alert=new AlertDialog.Builder(this);
            alert.setMessage("internet connection error");
            alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            alert.create();
            alert.show();


        }


        Button button=(Button)findViewById(R.id.exit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });





            Button button1=(Button)findViewById(R.id.buy_button);
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(firstpage.this,Activity_ads.class);

                    intent.putExtra("url",ads);

                    startActivity(intent);
                }
            });


            Button button2=(Button)findViewById(R.id.sell_button);

            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(firstpage.this,insert_ads.class);


                    intent.putExtra("url",user_inser_ads);

                    startActivity(intent);
                }
            });

        Button about=(Button)findViewById(R.id.about);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder al=new AlertDialog.Builder(firstpage.this);

                al.setTitle("about");
                al.setMessage("shayan yadegari \n email adress:\nshayan.13761376@gmail.com");

                al.create();
                al.show();


            }
        });

    }

    private boolean netconnect() {

        ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();

        if (networkInfo==null)
            return false;
            else
                return true;

    }






}
