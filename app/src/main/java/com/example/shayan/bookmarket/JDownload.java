package com.example.shayan.bookmarket;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by shayan on 4/20/2018.
 */
//downloading the link that created in json format
public class JDownload {

    public static String dlurl(String surl){

        String data = "";//get data from web

        InputStream inputStream;

        try {
            URL url=new URL(surl);

            HttpURLConnection urlConnection=(HttpURLConnection) url.openConnection();

            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(10000);

            urlConnection.setRequestMethod("GET");

            urlConnection.setDoInput(true);

            urlConnection.connect();

            inputStream=urlConnection.getInputStream();

            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder stringBuilder=new StringBuilder();

            String a;
            while((a=bufferedReader.readLine())!=null){

                stringBuilder.append(a);


            }
            data=stringBuilder.toString();
            bufferedReader.close();
            inputStream.close();
            urlConnection.disconnect();

        }
        catch (Exception e){

            Log.e("shit",e.toString());


        }



        return (data);
    }

}
