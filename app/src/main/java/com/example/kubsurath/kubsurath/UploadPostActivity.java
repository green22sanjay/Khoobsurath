package com.example.kubsurath.kubsurath;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kubsurath.kubsurath.JSONRequest.AndroidMultiPartEntity;
import com.example.kubsurath.kubsurath.sessionmanager.SessionManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by dinesh on 21-Apr-15.
 */
public class UploadPostActivity extends ActionBarActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private ProgressBar progressBar;
    private String filePath = null;
    private String postTitle,PostDesc,PostHashTag;
    private TextView txtPercentage,titlePost,HashTag,PostDescription;
    private ImageView imgPreview;

    private Button btnUpload;
    long totalSize = 0;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);
        getSupportActionBar().setCustomView(R.layout.customactionbartwo);

        setContentView(R.layout.uploadactivity);

        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        txtPercentage = (TextView) findViewById(R.id.txtPercentage);
        btnUpload = (Button) findViewById(R.id.btnUpload);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        imgPreview = (ImageView) findViewById(R.id.imgPreview);
        titlePost = (TextView) findViewById(R.id.PostTitle);
        HashTag = (TextView) findViewById(R.id.PostHshTag);
        PostDescription = (TextView) findViewById(R.id.PostDes);


        Intent i = getIntent();
        // image or video path that is captured in previous activity
        filePath = i.getStringExtra("filePath");
        postTitle = i.getStringExtra("PostTitle");
        PostDesc = i.getStringExtra("PostHash");
        PostHashTag = i.getStringExtra("PostDes");

        titlePost.setText(postTitle);
        HashTag.setText(PostHashTag);
        PostDescription.setText(PostDesc);

        boolean isImage = i.getBooleanExtra("isImage", true);

        if (filePath != null) {
            // Displaying the image or video on the screen
            previewMedia(isImage);
        } else {
            Toast.makeText(getApplicationContext(),
                    "Sorry, file path is missing!", Toast.LENGTH_LONG).show();
        }
        btnUpload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // uploading the file to server
                new UploadFileToServer().execute();
            }
        });
    }
        /**
         * Displaying captured image/video on the screen
         * */
        private void previewMedia(boolean isImage) {
            // Checking whether captured media is image or video
            if (isImage) {
                imgPreview.setVisibility(View.VISIBLE);

                // bimatp factory
                BitmapFactory.Options options = new BitmapFactory.Options();

                // down sizing image as it throws OutOfMemory Exception for larger
                // images
                options.inSampleSize = 8;

                final Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);

                imgPreview.setImageBitmap(bitmap);
            } else {
                imgPreview.setVisibility(View.GONE);

            }







    }

    /**
     * Uploading the file to server
     * */
    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
        @Override
        protected void onPreExecute() {
            // setting progress bar to zero
            progressBar.setProgress(0);
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Making progress bar visible
            progressBar.setVisibility(View.VISIBLE);

            // updating progress bar value
            progressBar.setProgress(progress[0]);

            // updating percentage value
            txtPercentage.setText(String.valueOf(progress[0]) + "%");
        }

        @Override
        protected String doInBackground(Void... params) {
            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {

            SharedPreferences pref = getApplicationContext().getSharedPreferences("AfterLogin", 0);
            String getloginid = pref.getString("Loginid", null);
            Log.e(TAG,getloginid);
            String PostTitle = titlePost.getText().toString();
            String PostHash = HashTag.getText().toString();
            String PostDescrsiption = PostDescription.getText().toString();

            String responseString = null;

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://andlog.jobstracer.com/fileUpload.php");

            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                        new AndroidMultiPartEntity.ProgressListener() {

                            @Override
                            public void transferred(long num) {
                                publishProgress((int) ((num / (float) totalSize) * 100));
                            }
                        });

                File sourceFile = new File(filePath);
                Log.e(TAG,getloginid);
                // Adding file data to http body
                entity.addPart("image", new FileBody(sourceFile));
                entity.addPart("uid", new StringBody(getloginid));
                // Extra parameters if you want to pass to server
                entity.addPart("PostTitle",
                        new StringBody(PostTitle));
                entity.addPart("PostDescription", new StringBody(PostDescrsiption));
                entity.addPart("PostHashTag",
                        new StringBody(PostHash));

              totalSize = entity.getContentLength();

                httppost.setEntity(entity);

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                } else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;
                }

            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }

            return responseString;

        }

        @Override
        protected void onPostExecute(String result) {
            Log.e(TAG, "Response from server: " + result);

            // showing the server response in an alert dialog
            showAlert(result);

            super.onPostExecute(result);
        }

    }

    /**
     * Method to show alert dialog
     * */
    private void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message).setTitle("Response from Servers")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // do nothing
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
