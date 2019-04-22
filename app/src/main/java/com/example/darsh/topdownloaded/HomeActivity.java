package com.example.darsh.topdownloaded;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    private ListView listapps;
    private String feedUrl = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/toppaidapplications/limit=%d/xml";
    private int feedlimit = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: Started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        listapps = (ListView) findViewById(R.id.xmlListView);
        downloadUrl( String.format(feedUrl, feedlimit) );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.navigation, menu);
        if(feedlimit == 10){
            menu.findItem(R.id.menu10).setChecked(true);
        }else{
            menu.findItem(R.id.menu25).setChecked(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case  R.id.menufree:
                feedUrl = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=%d/xml";
                break;

            case R.id.menuPaid:
                feedUrl = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/toppaidapplications/limit=%d/xml";
                break;

            case R.id.menuSongs:
                feedUrl = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=%d/xml";
                break;

            case R.id.menu10:
            case R.id.menu25:
                if(!item.isChecked()) {
                    item.setChecked(true);
                    feedlimit = 35 - feedlimit;
                    Log.d(TAG, "onOptionsItemSelected: " + item.getTitle() + "Limit changed To : " + feedlimit);
                }else{
                    Log.d(TAG, "onOptionsItemSelected: " + item.getTitle() + "Limit unchanged");
                }

            default:
                return  super.onOptionsItemSelected(item);
        }
        downloadUrl( String.format( feedUrl, feedlimit) );
        return true;
    }

    private void downloadUrl(String feedUrl){
        DownloadData downloadData = new DownloadData();
        downloadData.execute(feedUrl);
        Log.d(TAG, "onCreate: Done");
    }

    public class DownloadData extends AsyncTask<String,Void,String> {
        private static final String TAG = "DoInBackground";
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
             Log.d(TAG, "onPostExecute: The Parameter is " + s);
            ParseApplications parseApplications = new ParseApplications();
            parseApplications.pares(s);

//            ArrayAdapter<FeedEntry> arrayAdapter = new ArrayAdapter<FeedEntry>(HomeActivity.this,R.layout.list_item,parseApplications.getApplication());
//            listapps.setAdapter(arrayAdapter);

            FeedAdapter feedAdapter = new FeedAdapter(HomeActivity.this,R.layout.list_record,parseApplications.getApplication());
            listapps.setAdapter(feedAdapter);
        }

        @Override
        protected String doInBackground(String... strings) {
            Log.d(TAG, "doInBackground: Starts");
            String rssFeed = downloadXml(strings[0]);
            if(rssFeed == null){
                Log.e(TAG, "doInBackground: Error reading the XML ");
            }
            Log.d(TAG, "doInBackground: rss Feed : " + rssFeed);
            return rssFeed;
        }
        public String downloadXml(String urlPath){
            StringBuilder xmlResult = new StringBuilder();
            //StringBuilder xmlResult = new StringBuilder();

            try {
                URL url = new URL(urlPath);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                int responseCode = connection.getResponseCode();
                Log.d(TAG, "downloadXml: Response Code " + responseCode);
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                int charsRead ;
                char[] inputBuffer = new char[500];
                while(true){
                    charsRead = reader.read(inputBuffer);
                    if(charsRead < 0){
                        break;
                    }if(charsRead > 0) {
                        xmlResult.append(String.copyValueOf(inputBuffer, 0, charsRead));
                    }
                }
                reader.close();
                return xmlResult.toString();
            }catch (MalformedURLException e){
                Log.e(TAG, "downloadXml: MALFORMED URL"+ e.getMessage());
            }catch (IOException e) {
                Log.e(TAG, "downloadXml: " + e.getMessage(), e);
            }catch (SecurityException e) {
                Log.e(TAG, "downloadXml: ERROR CANNOT CONNECT TO INTERNET" + e.getMessage());
            }
            return null;
        }
    }
}
