package com.enric.androshoping.WebServices;

import android.os.AsyncTask;
import android.os.Message;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by home on 12/05/15.
 */
public class WSBase {
    
    public WSBase(){

    }
    
    public String get(String url) throws IllegalStateException, IOException {
        BufferedReader in = null;
        HttpClient client = new DefaultHttpClient();
        HttpPost request = new HttpPost(url);
        //Añadimos parametros si es necesario
        
        // Bind custom cookie store to the local context
        HttpResponse response = client.execute(request);
        InputStreamReader ix = new InputStreamReader(response.getEntity().getContent());
        in = new BufferedReader(ix);
        String accum = "";
        String line = "";
        while ((line = in.readLine()) != null) {
            accum += line;
        }
        Log.d("JSON", "Mensaje: " + accum);
        return accum;
    
    }
    
}
