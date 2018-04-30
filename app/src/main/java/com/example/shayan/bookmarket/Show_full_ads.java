package com.example.shayan.bookmarket;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.HashMap;

public class Show_full_ads extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_full_ads);

        //too layout ye scrol bendaz

        Bundle data = getIntent().getExtras();

        HashMap<String , Object> hm =
                (HashMap<String , Object>) data.get("ads");

        ImageView ads_img = (ImageView) findViewById(R.id.full_ads_img);
        TextView ads_title = (TextView) findViewById(R.id.full_ads_title);
        TextView ads_intro = (TextView) findViewById(R.id.full_ads_intro);
        TextView ads_desc = (TextView) findViewById(R.id.full_ads_desc);
        TextView ads_seller = (TextView) findViewById(R.id.full_ads_seller);
        TextView ads_email = (TextView) findViewById(R.id.full_ads_email);
        TextView ads_phone = (TextView) findViewById(R.id.full_ads_phone);
        TextView ads_date = (TextView) findViewById(R.id.full_ads_date);


        File file=new File((String)hm.get("image"));
        if (file.exists()){
            Bitmap bitmap= BitmapFactory.decodeFile(file.getAbsolutePath());
            ads_img.setImageBitmap(bitmap);

        }else {

        }


        ads_title.setText((String) hm.get("title"));
        ads_intro.setText((String) hm.get("intro"));
        ads_desc.setText((String) hm.get("desc"));
        ads_seller.setText((String) hm.get("seller"));
        ads_email.setText((String) hm.get("email"));
        ads_phone.setText((String) hm.get("phone"));
        ads_date.setText((String) hm.get("date"));
    }
}
