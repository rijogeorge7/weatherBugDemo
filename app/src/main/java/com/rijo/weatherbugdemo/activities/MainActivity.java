package com.rijo.weatherbugdemo.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rijo.weatherbugdemo.R;

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.rijo.weatherbugdemo.adapters.ImageRecyclerAdapter;
import com.rijo.weatherbugdemo.helper.Utilities;
import com.rijo.weatherbugdemo.model.Image;
import com.rijo.weatherbugdemo.model.Images;

import static android.content.res.Configuration.ORIENTATION_PORTRAIT;
import static com.rijo.weatherbugdemo.model.Images.*;
import static com.rijo.weatherbugdemo.model.Images.KEY;

public class MainActivity extends AppCompatActivity implements ImageRecyclerAdapter.ActivityCallBaak {

    private ProgressBar progressBar;
    private TextView loadingTextView;
    private RecyclerView imageRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    Images images=null;
    private boolean portrait;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        loadingTextView=(TextView)findViewById(R.id.loadingTextView);
        imageRecyclerView=(RecyclerView)findViewById(R.id.imageRecyclerView);
        //loading after configuration change
        if (savedInstanceState != null) {
                images=savedInstanceState.getParcelable(Images.KEY);
                hideLoadingView();
                setupImagesToGUI(images);
        }
        else {
                //check internet connection available
                if (Utilities.IsInternetAvailable(getApplicationContext())) {
                    //download images data from server
                    getImages();
                } else {
                    //internet not available, so show no network available dialog
                    showNoNetworkDialog();
                    hideLoadingView();
                }
            }
    }

    private void getImages() {
        final String urlString="https://s3.amazonaws.com/sc.va.util.weatherbug.com/interviewdata/mobilecodingchallenge/sampledata.json";
        Image[] imageArray=null;
        ExecutorService executor= Executors.newSingleThreadExecutor();
        Future<Image[]> itemFuture=executor.submit(new Callable<Image[]>() {
            @Override
            public Image[] call() throws Exception {
                return getImagesFromUrl(urlString);
            }
        });
        try{
            imageArray=itemFuture.get();
        }catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(imageArray!=null){
            //image successfully download, so setup gui with images
            images=new Images(Arrays.asList(imageArray));
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    hideLoadingView();
                    setupImagesToGUI(images);
                }
            });
        }
        else{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showImagesNotAvailableDialog();
                    hideLoadingView();
                }
            });
        }


    }

    private Image[] getImagesFromUrl(String urlString) {
        String imagesJson=Utilities.downloadImagesJsonFromUrl(urlString);
        Image[] imageArray=null;
        if(imagesJson!=null) {
            imageArray=getImagesFromJSONString(imagesJson);
        }
        return imageArray;
    }

    public static Image[] getImagesFromJSONString(String jsonString) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        Image[] imageArray;
        try {
            imageArray =gson.fromJson(jsonString, Image[].class);
        }catch (Exception e){
            imageArray =null;
        }
        return imageArray;
    }

    private void setupImagesToGUI(Images images) {
        imageRecyclerView.setHasFixedSize(true);
        if(getResources().getConfiguration().orientation==ORIENTATION_PORTRAIT)
        {
            mLayoutManager = new LinearLayoutManager(this);
            portrait=true;
        }
        else {
            mLayoutManager = new GridLayoutManager(this,2);
        }
        imageRecyclerView.setLayoutManager(mLayoutManager);
        ImageRecyclerAdapter adapter=new ImageRecyclerAdapter(images,getApplicationContext(),this,portrait);
        imageRecyclerView.setAdapter(adapter);
    }

    private void showNoNetworkDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setMessage(R.string.internet_dialog_message)
                .setTitle(R.string.internet_dialog_title);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        builder.show();
    }

    private void showImagesNotAvailableDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setMessage(R.string.image_not_available)
                .setTitle(R.string.no_images);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        builder.show();
    }

    //hide progress view
    private void hideLoadingView() {
        progressBar.setVisibility(View.GONE);
        loadingTextView.setVisibility(View.GONE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(Images.KEY,images);
    }

    //activity callback when an item selected on recycler adapter
    @Override
    public void onItemSelected(int position) {
        Intent detailsActivityIntent=new Intent(this,ImageDetailsActivity.class);
        detailsActivityIntent.putExtra(Image.KEY,images.getImages().get(position));
        startActivity(detailsActivityIntent);
    }
}
