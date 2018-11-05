package com.enric.androshoping.Objects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by home on 03/03/15.
 */
public class Imatge {
    public String url;
    public String titul;
    public File file;
    
    
    public Imatge(String url, String titul){
        this.titul = titul;
        this.url = url;
        this.file = null;
    }

    public Imatge(String url, String titul, File file){
        this.titul = titul;
        this.url = url;
        this.file = file;
    }
    
    public void guardaImatge(Bitmap bmp){

        try {
            String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Fotos";
            File dir = new File(file_path);
            if(!dir.exists())
                dir.mkdirs();
            file = new File(dir, "IMG_" + titul + ".png");
            FileOutputStream fOut = new FileOutputStream(file);

            bmp.compress(Bitmap.CompressFormat.PNG, 85, fOut);
            fOut.flush();
            fOut.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    public Bitmap getFromFile(){
        if(file != null){
            return BitmapFactory.decodeFile(file.getAbsolutePath());
        }
        return null;
    }
    
}
