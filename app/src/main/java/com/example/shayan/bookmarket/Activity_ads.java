package com.example.shayan.bookmarket;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Activity_ads extends AppCompatActivity {

    private String url_ads = "";

    private int curent_page = 0;

    private ListView lv;

    private boolean next=false;

    private List<HashMap<String, Object>> all_ads = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cache_clear();

        setContentView(R.layout.activity_ads);

        Bundle bundle = getIntent().getExtras();

        lv = (ListView) findViewById(R.id.ads_list);


        url_ads = bundle.getString("url");


        make_ads_list();

        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {


                if (scrollState == 1) {
                    if (view.getId() == lv.getId()) {
                        int currentFirstVisibleItem = lv.getFirstVisiblePosition();
                        int mLastFirstVisibleItem = lv.getLastVisiblePosition();

                        if (currentFirstVisibleItem > mLastFirstVisibleItem) {
                            // go up
                        } else if (currentFirstVisibleItem < mLastFirstVisibleItem) {
                            // go down


                                if (next==true){
                                    next=false;
                                    make_ads_list();
                                }

                        }
                    }
                }

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });





        lv.setOnItemClickListener(
                new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                        Intent intent = new Intent(getApplicationContext(), Show_full_ads.class);

                        intent.putExtra("ads", all_ads.get(position));

                        startActivity(intent);
                    }
                }
        );


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cache_clear();
    }

    public void make_ads_list(){

        try{


                next=true;
                DownloadTask dl=new DownloadTask();
                dl.execute(url_ads+curent_page);
                curent_page++;



        }

        catch (Exception e){

        }

    }

    public class DownloadTask extends AsyncTask<String,Void,String>{


        @Override
        protected String doInBackground(String... strings) {

            String temp="";

            try{



              temp=  JDownload.dlurl(strings[0]);


            }
            catch (Exception e){

            }

            return temp;
        }
        @Override
        protected void onPostExecute(String s) {

            Listviewloads listviewloads=new Listviewloads();

            listviewloads.execute(s);



        }


    }

    private class Listviewloads extends AsyncTask<String,Void,SimpleAdapter>{//loads list view



        @Override
        protected SimpleAdapter doInBackground(String... strings) {


            try{


                adsparser adsp=new adsparser();


                all_ads.addAll(adsp.parse(strings[0]));



            }
            catch (Exception e){

            }
            String[] from={"image","title","intro","date"};
            int[] to={R.id.ads_img,R.id.ads_title,R.id.ads_ntro,R.id.ads_date};

            SimpleAdapter simpleAdapter=new SimpleAdapter(

                    getBaseContext(),all_ads,R.layout.ads_list_row,from,to
            );

            return simpleAdapter;
        }

        @Override
        protected void onPostExecute(SimpleAdapter simpleAdapter) {
            lv.setAdapter(simpleAdapter);
           for (int i = 0; i <simpleAdapter.getCount() ; i++) {

                HashMap<String,Object>hashMap=(HashMap<String,Object>)simpleAdapter.getItem(i);

                String imageurl= (String) hashMap.get("image_path");


                HashMap<String,Object> fordwonload=new HashMap<>();

                fordwonload.put("image_path",imageurl);

                fordwonload.put("position",i);

               imagedownloader ig=new imagedownloader();
                ig.execute(fordwonload);

            }
            next=true;


        }


    }

    private class imagedownloader extends AsyncTask<HashMap<String,Object>,Void,HashMap<String,Object>>{


        @Override
        protected HashMap<String, Object> doInBackground(HashMap<String, Object>[] hashMaps) {

            InputStream inputStream;



            String Imageurl= (String) hashMaps[0].get("image_path");





            int position=(int)hashMaps[0].get("position");




            try {

                URL url=new URL(Imageurl);



                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();

                httpURLConnection.setDoInput(true);

                httpURLConnection.connect();


                inputStream=httpURLConnection.getInputStream();



                File chach=getBaseContext().getCacheDir();



                File  temp=new File(chach+"/image_"+position+"_"+curent_page+".png");



                FileOutputStream fileOutputStream=new FileOutputStream(temp);


                Bitmap bitmap= BitmapFactory.decodeStream(inputStream);



                bitmap.compress(Bitmap.CompressFormat.PNG,100,fileOutputStream);



                fileOutputStream.flush();

                fileOutputStream.close();

                HashMap<String,Object>hashMap=new HashMap<>();

                hashMap.put("image",temp.getPath());

                hashMap.put("position",position);

                return hashMap;



            }catch (Exception e){



            }





            return null;
        }

        @Override
        protected void onPostExecute(HashMap<String, Object> stringObjectHashMap) {

            String image= (String) stringObjectHashMap.get("image");

            int position=(int)stringObjectHashMap.get("position");

            SimpleAdapter simpleAdapter= (SimpleAdapter) lv.getAdapter();

            HashMap<String,Object>hm= (HashMap<String, Object>) simpleAdapter.getItem(position);

            hm.put("image",image);

            simpleAdapter.notifyDataSetChanged();

        }
    }

    public void cache_clear(){
       //delete file for clear cache directory


            try{
                File[] file=getBaseContext().getCacheDir().listFiles();

                for (File p:file) {
                    p.delete();
                }
            }catch (Exception e){

            }




        }

    }
