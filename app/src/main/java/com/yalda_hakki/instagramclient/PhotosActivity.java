package com.yalda_hakki.instagramclient;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class PhotosActivity extends AppCompatActivity {
    public static final String CLIENT_ID = "e05c462ebd86446ea48a5af73769b602";
    private ArrayList<InstagramPhoto> photos;
    private InstagramPhotosAdapter aPhotos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        photos = new ArrayList<>();
        //create the adaptor linking it to the source
        aPhotos = new InstagramPhotosAdapter(this,photos);
        //find the list view from the layout
        ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
        //set the adapter binding it to the list view
        lvPhotos.setAdapter(aPhotos);
        //Send out API requests to popular photos
        fetchPopularPhotos();
    }

    //Trigger API requests
    public void fetchPopularPhotos(){
        String url = "https://api.instagram.com/v1/media/popular?client_id="+CLIENT_ID;
        //Create the network client
        AsyncHttpClient client = new AsyncHttpClient();
        //Trigger the get request
        client.get(url,null, new JsonHttpResponseHandler (){
            //onSuccess (worked)

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //Expecting a JSON Object
                //Log.i("DEBUG",response.toString());
                //Iterate each of the photo items and decode the item into a java object
                JSONArray photosJSON = null;
                try{
                    photosJSON =response.getJSONArray("data");//array of posts
                    //iterate array of posts
                    for (int i=0 ;i < photosJSON.length();i++){
                        //get the jason object at that position in the array
                        JSONObject photoJSON = photosJSON.getJSONObject(i);
                        //decode the attributes of JSON into a data model
                        InstagramPhoto photo = new InstagramPhoto();
                        photo.username = photoJSON.getJSONObject("user").getString("username");
                        photo.caption = photoJSON.getJSONObject("caption").getString("text");
                        photo.imageUrl=photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
                        photo.imageHeight=photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
                        photo.likesCount=photoJSON.getJSONObject("likes").getInt("count");
                        photos.add(photo);

                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }

                //callback
                aPhotos.notifyDataSetChanged();
            }

            //onFailure

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
               //Do Something
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
