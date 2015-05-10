package com.example.kubsurath.kubsurath;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kubsurath.kubsurath.Apiconfig.Config;
import com.example.kubsurath.kubsurath.JSONRequest.AndroidMultiPartEntity;
import com.example.kubsurath.kubsurath.converter.Base64;
import com.example.kubsurath.kubsurath.datmodels.FileimagePath;
import com.example.kubsurath.kubsurath.datmodels.Person;
import com.example.kubsurath.kubsurath.sessionmanager.SessionManager;
import com.example.kubsurath.kubsurath.sqlhandler.SQLiteHandler;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;


public class AddPostActivitys extends ActionBarActivity {


    private static final String TAG = AddPostActivitys.class.getSimpleName();
    private static final int SELECT_PICTURE = 1;
    // Fields
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;

    private TextView  txtPercentage;

    private Uri fileUri; // file url to store image/video
    Button btncapcam;
    private String selectedImagePath;
    private ImageView img;
    long totalSize = 0;
    private LinearLayout mLinearLayout;
    File compressedFile;
    private ProgressBar progressBar;
    private static final int PICK_IMAGE = 1;
    private ImageView imgView;
    private Button upload;
    private EditText caption,PostTitle,PostDescription,PostHastag;
    private Bitmap bitmap;
    private ProgressDialog dialog;
    private String imagepath=null;
    private String URL_FEED = "http://andlog.jobstracer.com/category.php";
    Person person;
    FileimagePath filepatssss;
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);
        getSupportActionBar().setCustomView(R.layout.customactionbartwo);

        Button mButton = (Button)getSupportActionBar().getCustomView().findViewById(
                R.id.btnPost);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UploadFileToServer().execute();
                Toast.makeText(getApplicationContext(), "Post click",
                        Toast.LENGTH_LONG).show();
            }
        });
        setContentView(R.layout.postfeed);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        txtPercentage = (TextView) findViewById(R.id.txtPercentage);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        PostTitle=(EditText)findViewById(R.id.posttile);
        PostDescription=(EditText)findViewById(R.id.PostDescription);
        PostHastag=(EditText)findViewById(R.id.postHastTag);


        String title= PostTitle.getText().toString();
        String hashtag = PostHastag.getText().toString();
        String pstDes = PostDescription.getText().toString();


        mLinearLayout =(LinearLayout)findViewById(R.id.linearLayoutafterchild);
        img = (ImageView)findViewById(R.id.setImg);
        btncapcam = (Button) findViewById(R.id.addpic);
        btncapcam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intent, 0);
                */
               /*  Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE); */

              Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
              //  fileUri= getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"),PICK_IMAGE);
/*
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

                // start the image capture Intent
                startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE); */

            }
        });


    }
    @SuppressWarnings("deprecation")
    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
        }
   public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }


    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                Config.IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
/*        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "Oops! Failed create "
                        + Config.IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        } */

        return mediaStorageDir;
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on screen orientation
        // changes
        outState.putParcelable("file_uri", fileUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();

            imagepath = getPath(selectedImage);

            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
           String picturePath = cursor.getString(columnIndex);
            cursor.close();

            FileimagePath filepatssss= new FileimagePath();
            filepatssss.setImagefilepath(picturePath);

            decodeFile(picturePath);



        }
    }

    /*
    private void launchUploadActivity(boolean isImage){

        Intent i = new Intent(AddPostActivitys.this, UploadPostActivity.class);
        i.putExtra("filePath", fileUri.getPath());
        i.putExtra("PostTitle",title);
        i.putExtra("PostHash",hashtag);
        i.putExtra("PostDes", pstDes);
        i.putExtra("isImage", isImage);
        startActivity(i);
    }
    */

    public void decodeFile(String filePath) {

        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, o);

        // The new size we want to scale to
        final int REQUIRED_SIZE = 1024;

        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        bitmap = BitmapFactory.decodeFile(filePath, o2);

        img.setImageBitmap(bitmap);
        mLinearLayout.setVisibility(View.VISIBLE);
        img.setVisibility(View.VISIBLE);
    }




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
            txtPercentage.setVisibility(View.VISIBLE);

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
            String PostTite = PostTitle.getText().toString();
            String PostHash = PostHastag.getText().toString();
            String PostDescrsiption = PostDescription.getText().toString();

            String responseString = null;

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://andlog.jobstracer.com/fileUpload.php");

            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                        new AndroidMultiPartEntity.ProgressListener() {

                            @Override
                            public void transferred(long num) {
                                publishProgress((int) ((num / (float)
                                        totalSize) * 100));
                            }
                        });

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                byte[] data = bos.toByteArray();
                String file = Base64.encodeBytes(data);


               File sourceFile = new File(imagepath);


                Log.e(TAG,file);
                // Adding file data to http body
                entity.addPart("image", new FileBody(sourceFile));
                entity.addPart("uid", new StringBody(getloginid));
                // Extra parameters if you want to pass to server
                entity.addPart("PostTitle",
                        new StringBody(PostTite));
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




