package com.example.regions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GetAllRegions();
    }
    public void GetAllRegions() {

//        ListView listView = (ListView)findViewById(R.id.users_list);
        String url = "http://192.168.1.243:80/android/regions.php";
        //check if there is a permission or not . if there is no permission send permission request
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},
                    123);

        }
        else{
            DownloadTextTask runner = new DownloadTextTask();
            runner.execute(url);
        }



    }
    private InputStream OpenHttpConnection(String urlString) throws IOException
    {
        InputStream in = null;
        int response = -1;

        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();

        if (!(conn instanceof HttpURLConnection))
            throw new IOException("Not an HTTP connection");
        try{
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            //GET since the request is of type get
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            response = httpConn.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
            }
        }
        catch (Exception ex)
        {
            Log.d("Networking", ex.getLocalizedMessage());
            throw new IOException("Error connecting");
        }

        return in;
    }
    //This method talk ro web service using android API
    private String DownloadText(String URL)
    {
        int BUFFER_SIZE = 2000;
        InputStream in = null;
        try {
            in = OpenHttpConnection(URL);
        } catch (IOException e) {
            Log.d("Networking", e.getLocalizedMessage());
            return "";
        }
//loop to read the returned data
        InputStreamReader isr = new InputStreamReader(in);
        int charRead;
        String str = "";
        char[] inputBuffer = new char[BUFFER_SIZE];
        try {
            while ((charRead = isr.read(inputBuffer))>0) {
                //---convert the chars to a String---
                String readString =
                        String.copyValueOf(inputBuffer, 0, charRead);
                str += readString;
                inputBuffer = new char[BUFFER_SIZE];
            }
            in.close();
        } catch (IOException e) {
            Log.d("Networking", e.getLocalizedMessage());
            return "";
        }

        return str;
    }


    //inner class for threads take 3 parameters(data type,progress bar or not, returned data type)
    private class DownloadTextTask extends AsyncTask<String, Void, String> {
        @Override
        //this method take the first parameter and we put the method which take a longtime inside it.
        protected String doInBackground(String... urls) {
            return DownloadText(urls[0]);
        }
        //when it finished it send resulted data to onPostExcute(call back method)
        @Override
        protected void onPostExecute(String result) {
            //Toast.makeText(getBaseContext(), result, Toast.LENGTH_LONG).show();
            //String[] books = result.split(",");
            //String str = "";
            //for(String s : books){
            //    str+= s + "\n";
            // }
//            ListView listView = (ListView)findViewById(R.id.users_list);
            String[] values = result.split(";");
//            String [] data=new String[values.length];
            String []name=new String[values.length];
            String []lang=new String[values.length];
            String []lat=new String[values.length];
            String []pop=new String[values.length];
            int []image_id=new int[values.length];
            for(int i=0;i<values.length;i++){
                String [] newData=values[i].split(",");
                for(int j=0;j<=0;j++){
                    name[i]=newData[j+1];
                    int resourceId = MainActivity.this.getResources().getIdentifier(newData[j+2], "drawable", MainActivity.this.getPackageName());
                    image_id[i]=resourceId;
                    lang[i]=newData[j+3];
                    lat[i]=newData[j+4];
                    pop[i]=newData[j+5];
                }


            }
            RecyclerView recycler = (RecyclerView)findViewById(R.id.regions_recycler);

            recycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            CaptionedImageAdapter adapter = new CaptionedImageAdapter(name, image_id,lang,lat,pop);
            recycler.setAdapter(adapter);

//            ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(MainActivity.this,
//                    android.R.layout.simple_list_item_1,
//                   name);
//            listView.setAdapter(listAdapter);

//            edtData.setText(result);
        }
    }
}