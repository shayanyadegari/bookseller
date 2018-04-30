package com.example.shayan.bookmarket;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by shayan on 4/21/2018.
 */

public class adsparser {

    public List<HashMap<String,Object>> parse(String json){

        List<HashMap<String, Object>> all_ads=new ArrayList<>();

            try {

                JSONObject jsonObject=new JSONObject(json);

                JSONArray jsonArray=jsonObject.getJSONArray("ads");



                for (int i=0;i<jsonArray.length();i++){




                    JSONObject jobj=jsonArray.getJSONObject(i);//khoone hara dooone doone mikhone too araye object darim

                    HashMap<String,Object> ads=new HashMap<String,Object>();

                    ads.put("id",jobj.getString("id"));
                    ads.put("title",jobj.getString("title"));
                    ads.put("intro",jobj.getString("intro"));
                    ads.put("desc",jobj.getString("desc"));
                    ads.put("image_path",jobj.getString("image"));
                    ads.put("image",R.drawable.downloading);
                    ads.put("seller",jobj.getString("seller"));
                    ads.put("email",jobj.getString("email"));
                    ads.put("phone",jobj.getString("phone"));
                    ads.put("date",jobj.getString("date"));


                    all_ads.add(ads);
                }

            }
            catch (Exception e){

            }

            return (all_ads);

    }
}
