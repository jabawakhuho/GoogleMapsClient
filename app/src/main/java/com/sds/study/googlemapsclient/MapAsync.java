package com.sds.study.googlemapsclient;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by student on 2016-12-16.
 */

public class MapAsync extends AsyncTask<String, Void, String> {
    URL url;
    HttpURLConnection con;
    String TAG;
    GoogleMap googleMap;

    public MapAsync(GoogleMap googleMap) {
        this.googleMap=googleMap;
        this.TAG = getClass().getName();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        BufferedReader buffr=null;
        StringBuffer sb=null;
        try {
            url = new URL("http://192.168.0.20/map/list.jsp");
            con=(HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            con.setDoInput(true);
            buffr=new BufferedReader(new InputStreamReader(con.getInputStream(),"utf-8"));

            sb = new StringBuffer();
            String data=null;
            while(true){
                data=buffr.readLine();
                if(data==null)break;
                sb.append(data);
            }
            con.getResponseCode();//요청하기
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(buffr!=null){
                try {
                    buffr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.d(TAG,"111:"+sb.toString());
        return sb.toString();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d(TAG,s);
        //엑티비티의 프레그먼트를 갱신 시키자~ 불러온 json을 적용해서!!
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray=jsonObject.getJSONArray("position");

            for(int i=0; i < jsonArray.length();i++){
                JSONObject obj=jsonArray.getJSONObject(i);

                LatLng point = new LatLng(obj.getDouble("lat"),obj.getDouble("lng") ) ;
                googleMap.addMarker(new MarkerOptions()
                        .title("마커마커").position(point)
                );


            }


        } catch (JSONException e) {
            e.printStackTrace();
        }





        super.onPostExecute(s);
    }
}
