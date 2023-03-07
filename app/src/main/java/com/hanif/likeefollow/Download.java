package com.hanif.likeefollow;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.List;














public class Download extends AppCompatActivity {
    public boolean canDownload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        Button download = findViewById(R.id.download);
        EditText link = findViewById(R.id.link);


        download.setOnClickListener(v -> {


//            String url = String.valueOf(link.getText());
//            if (url.contains("https://")) {
//                new callFbData().execute(url);
//            } else {
//                Toast.makeText(Download.this, "Enter a valid Url", Toast.LENGTH_SHORT).show();
//            }
        });


        checkAccessibilityServicePermission();
        checkPermission();

    }


    class callFbData extends AsyncTask<String, Void, Document> {
        Document fbDoc;

        @Override
        protected Document doInBackground(String... strings) {
            try {
                fbDoc = Jsoup.connect(strings[0]).get();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return fbDoc;
        }

        @Override
        protected void onPostExecute(Document document) {
            Element Video = document.select("video").first();
            String videourl =  Video.absUrl("src");
            super.onPostExecute(document);

            if (!videourl.equals("")) {
                download(videourl, "facebook" + System.currentTimeMillis() + ".mp4");
            }
        }
    }


    public void download(String url, String fileName) {
        if (canDownload){
            String RootDirectoryFacebook = "LikeeVideo";
            Toast.makeText(Download.this, "Downloading", Toast.LENGTH_SHORT).show();
            Uri uri = Uri.parse(url);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setTitle(fileName);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, RootDirectoryFacebook + fileName);
            ((DownloadManager) Download.this.getSystemService(Context.DOWNLOAD_SERVICE)).enqueue(request);
        }else {
            checkPermission();
        }


    }


    public void checkPermission() {
        Dexter.withContext(getApplicationContext())
                .withPermissions(android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        if(multiplePermissionsReport.areAllPermissionsGranted()){
                            canDownload = true;
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }


    public void checkAccessibilityServicePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int access = 0;
            try{
                access = Settings.Secure.getInt(this.getContentResolver(), android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
            } catch (Settings.SettingNotFoundException e){
                e.printStackTrace();
                //put a Toast
            }
            if (access == 0) {
                Intent myIntent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(myIntent);
            }
        }
    }

}