package com.example.shayan.bookmarket;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.graphics.BitmapCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class insert_ads extends AppCompatActivity {

    private String url_insert_ads;

    private EditText title,intro,desc,seller,email,phone;

    private ImageView selectedimage;

    private int galleryreq=2;

    private int myrequest_code=1;
    private Bitmap my_bitmap;

    private String camera_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_ads);

        Bundle bundle=getIntent().getExtras();

        url_insert_ads=bundle.getString("url");

        title=(EditText)findViewById(R.id.insert_title);

        intro=(EditText)findViewById(R.id.insert_intro);

        desc=(EditText)findViewById(R.id.insert_desc);

        seller=(EditText)findViewById(R.id.insert_seller);

        email=(EditText)findViewById(R.id.insert_email);

        phone=(EditText)findViewById(R.id.insert_phone);

        selectedimage =(ImageView)findViewById(R.id.agahi_image);


        Button submit=(Button)findViewById(R.id.btninsert);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //first we check that items dont be null

                if (title.getText().length()>1){

                    if (intro.getText().length()>1){


                        if (desc.getText().length()>1){


                            if (seller.getText().length()>1){

                                if (email.getText().length()>1){


                                    if (phone.getText().length()>1){


                                      //check image bezan
                                        //then do what i want

                                        String str_title=title.getText().toString();

                                        String str_intro=intro.getText().toString();

                                        String str_desc=desc.getText().toString();

                                        String str_seller=seller.getText().toString();

                                        String str_email=email.getText().toString();

                                        String str_phone=phone.getText().toString();

                                        Bitmap bitmap=((BitmapDrawable)selectedimage.getDrawable()).getBitmap();

                                        HashMap<String,String>passdata=new HashMap<>();

                                        passdata.put("title",str_title);

                                        passdata.put("intro",str_intro);

                                        passdata.put("desc",str_desc);

                                        passdata.put("seller",str_seller);

                                        passdata.put("email",str_email);

                                        passdata.put("phone",str_phone);


                                        upload_image up=new upload_image(passdata,bitmap);

                                        up.execute();




                               //         String image="image="+camera_image;

                             //           String and="&";

                           //             url_insert_ads=url_insert_ads+str_title+and+str_intro+and+str_desc+and+str_seller
                         //                       +and+str_email+and+str_phone+and+image;

                                     //   AlertDialog.Builder alertDialog=new AlertDialog.Builder(insert_ads.this);

                                   //     alertDialog.setMessage(url_insert_ads);

                                 //       alertDialog.show();




                                    }
                                    else {
                                        phone.setHint("please fill blancket");
                                    }

                                }
                                else {
                                    email.setHint("please fill blancket");
                                }


                            }
                            else {
                                seller.setHint("please fill blancket");
                            }

                        }
                        else {
                            desc.setHint("please fill blancket");
                        }

                    }
                    else {
                        intro.setHint("please fill blancket");
                    }



                }
                else {
                    title.setHint("please fill blancket");
                }


            }
        });

        Button btncamera=(Button)findViewById(R.id.btncamera);

        btncamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check phone camera is work

                if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)){

                    Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    startActivityForResult(intent,myrequest_code);



                }
                else {

                    Toast.makeText(insert_ads.this, "camera cant work for any reason", Toast.LENGTH_SHORT).show();

                }

            }
        });


        Button gallerybtn=(Button)findViewById(R.id.btngallery);
        gallerybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(intent,galleryreq);

            }
        });

        Button back=(Button)findViewById(R.id.goback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode==myrequest_code &&resultCode==RESULT_OK ){


            Bundle bundle=data.getExtras();

            my_bitmap= (Bitmap) bundle.get("data");

            imageshow();




        }else if (requestCode==galleryreq &&resultCode==RESULT_OK){

            Uri uri=data.getData();

            Log.e("gg","it come to result");

            show_image_gallery(uri);

        }



        else {

            Toast.makeText(this, "ERROR cant get picture", Toast.LENGTH_SHORT).show();

        }


    }

    private void show_image_gallery(final Uri uri) {


        try
        {
            AlertDialog.Builder imageLoader = new AlertDialog.Builder(this);

            LayoutInflater inflater = (LayoutInflater)
                    this.getSystemService(LAYOUT_INFLATER_SERVICE);

            View layout = inflater.inflate(R.layout.full_image,
                    (ViewGroup) findViewById(R.id.full_img_layout_root));

            final ImageView bigImage = (ImageView) layout.findViewById(R.id.full_img_img);

            bigImage.setImageURI(uri);

            Log.e("kir",uri.toString());

            TextView imgTitle = (TextView) layout.findViewById(R.id.full_img_title);

            imgTitle.setText("axe");

            imageLoader.setView(layout);

            imageLoader.setCancelable(false);

            imageLoader.setPositiveButton("ok",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {


                            selectedimage.setImageURI( uri );


                            dialog.dismiss();
                        }
                    }
            );

            imageLoader.setNegativeButton("again",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                            Intent i = new Intent( Intent.ACTION_PICK ,
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                            );

                            startActivityForResult( i , galleryreq );



                        }
                    }
            );

            imageLoader.setNeutralButton("cancel",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    }
            );

            imageLoader.create();

            imageLoader.show();
        }
        catch ( Exception e )
        {
            /*
             * Log.i( "MatiMessage" , "error 1 -> " + e.toString() );
             */
        }

    }


    public void imageshow(){

        try
        {
            AlertDialog.Builder imageLoader = new AlertDialog.Builder(this);

            LayoutInflater inflater = (LayoutInflater)
                    this.getSystemService(LAYOUT_INFLATER_SERVICE);

            View layout = inflater.inflate(R.layout.full_image,
                    (ViewGroup) findViewById(R.id.full_img_layout_root));

            final ImageView bigImage = (ImageView) layout.findViewById(R.id.full_img_img);

            bigImage.setImageBitmap(my_bitmap);

            TextView imgTitle = (TextView) layout.findViewById(R.id.full_img_title);

            imgTitle.setText("set text");

            imageLoader.setView(layout);

            imageLoader.setCancelable(false);

            imageLoader.setPositiveButton("ok",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            selectedimage.setImageBitmap( my_bitmap );

    //                        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
  //                          my_bitmap.compress(Bitmap.CompressFormat.JPEG,90,byteArrayOutputStream);

//                            byte[] bytes=byteArrayOutputStream.toByteArray();

                    //        camera_image= Base64.encodeToString(bytes,0);


                            dialog.dismiss();
                        }
                    }
            );

            imageLoader.setNegativeButton("again",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                            onBtnCameraClick(null);



                        }
                    }
            );

            imageLoader.setNeutralButton("cancel",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    }
            );

            imageLoader.create();

            imageLoader.show();
        }
        catch ( Exception e )
        {
            /*
             * Log.i( "MatiMessage" , "error 1 -> " + e.toString() );
             */
        }

    }

    private class upload_image extends AsyncTask<Void,Void,Void>{

        private HashMap<String,String>main_hm;
        private boolean result;
        private Bitmap mimage;
        public upload_image(HashMap<String,String>hm,Bitmap im){
            mimage=im;
            main_hm=hm;
        }


        @Override
        protected Void doInBackground(Void... voids) {

            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();

            mimage.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);

            String encode=Base64.encodeToString(byteArrayOutputStream.toByteArray(),Base64.DEFAULT);

            ArrayList<NameValuePair> dataToSend = new ArrayList<>();

            dataToSend.add( new BasicNameValuePair("title" , main_hm.get("title") ) );
            dataToSend.add( new BasicNameValuePair("intro" , main_hm.get("intro") ) );
            dataToSend.add( new BasicNameValuePair("desc" , main_hm.get("desc") ) );
            dataToSend.add( new BasicNameValuePair("seller" , main_hm.get("seller") ) );
            dataToSend.add( new BasicNameValuePair("email" , main_hm.get("email") ) );
            dataToSend.add( new BasicNameValuePair("phone" , main_hm.get("phone") ) );
            dataToSend.add(new BasicNameValuePair("image", encode));

            HttpParams httpParams=new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams,15000);
            HttpConnectionParams.setSoTimeout(httpParams,12000);

            HttpClient httpClient=new DefaultHttpClient(httpParams);

            HttpPost httpPost=new HttpPost(url_insert_ads);

            try{

                httpPost.setEntity(new UrlEncodedFormEntity(dataToSend));

                httpClient.execute(httpPost);

                result=true;

            }catch (Exception e){

                result=false;
            }



            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (result){

                Toast.makeText(insert_ads.this, "advertise uploads", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(insert_ads.this, "cant uploads for some reason!!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void onBtnCameraClick( View v )
    {
        if( getPackageManager().hasSystemFeature( PackageManager.FEATURE_CAMERA_ANY ) )
        {
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            startActivityForResult(i, myrequest_code);
        }

    }

}
