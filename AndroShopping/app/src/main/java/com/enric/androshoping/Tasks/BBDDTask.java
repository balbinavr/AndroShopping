package com.enric.androshoping.Tasks;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.enric.androshoping.Interfaces.OnBackGruound;
import com.enric.androshoping.Interfaces.OnResult;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by home on 03/03/15.
 */
public class BBDDTask extends AsyncTask<Void, Integer, Boolean> {
    private Context context;
    private OnBackGruound onBG;

    public BBDDTask(Context context, OnBackGruound onBG) {
        this.context = context;
        this.onBG = onBG;
    }

    protected Boolean doInBackground(Void... params) {
        return onBG.onBackGround();
    }

    protected void onProgressUpdate(Integer... progress) {
        
    }

    protected void onPostExecute(Boolean result) {
        if( result ) {
            onBG.onSucces();
        }else{
            onBG.onFailure();
        }
    }
}