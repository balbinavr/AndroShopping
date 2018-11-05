package com.enric.androshoping.Activitats;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.enric.androshoping.BBDD.DAOProducte;
import com.enric.androshoping.Fragments.FirstStep;
import com.enric.androshoping.Fragments.SecondStep;
import com.enric.androshoping.Fragments.ThirdStep;
import com.enric.androshoping.Interfaces.OnBackGruound;
import com.enric.androshoping.Objects.Producte;
import com.enric.androshoping.Objects.Tag;
import com.enric.androshoping.R;
import com.enric.androshoping.Tasks.BBDDTask;

import java.util.ArrayList;


public class NewProduct extends ActionBarActivity {

    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btnAnt;
    private Button btnSeg;
    private View frg;
    private static FirstStep fragmentPrimer = new FirstStep();
    private static SecondStep fragmentSegon = new SecondStep();
    private static ThirdStep fragmentTercer  = new ThirdStep();
    private Producte producte;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle(R.string.title_newProduct);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);

        producte = new Producte();
        
        fragmentSegon.selectedImageUri = Uri.EMPTY;
        fragmentTercer.tags = new ArrayList<Tag>();
        
        btn1 = (Button) findViewById(R.id.btnA);
        btn2 = (Button) findViewById(R.id.btnB);
        btn3 = (Button) findViewById(R.id.btnC);
        btnAnt = (Button) findViewById(R.id.btnAnt);
        btnSeg = (Button) findViewById(R.id.btnSeg);
        frg = (View) findViewById(R.id.frameLayout1);

        fragment1();
        btn1.setClickable(false);
        btn2.setClickable(true);
        btn3.setClickable(true);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch ((int)frg.getTag()){
                    case 1:
                        save1();
                        break;
                    case 2:
                        save2();
                        break;
                    case 3:
                        save3();
                        break;
                    default:
                        break;
                }
                fragment1();
            }
        });

       btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch ((int)frg.getTag()){
                    case 1:
                        save1();
                        break;
                    case 2:
                        save2();
                        break;
                    case 3:
                        save3();
                        break;
                    default:
                        break;

                }
                fragment2();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch ((int)frg.getTag()){
                    case 1:
                        save1();
                        break;
                    case 2:
                        save2();
                        break;
                    case 3:
                        save3();
                        break;
                    default:
                        break;

                }
                fragment3();
            }
        });

        btnAnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch ((int)frg.getTag()){
                    case 1:
                        break;
                    case 2:
                        save2();
                        fragment1();
                        break;
                    case 3:
                        save3();
                        fragment2();
                        break;
                    default:
                        break;

                }

            }
        });

        btnSeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch ((int)frg.getTag()){
                    case 1:
                        save1();
                        fragment2();
                        break;
                    case 2:
                        save2();
                        fragment3();
                        break;
                    case 3:
                        addProduct();

                        break;
                    default:
                        break;

                }



            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.drawable.compra5);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3CB371")));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_product, menu);
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

        if (id == android.R.id.home){
            fragmentPrimer = new FirstStep();
            fragmentSegon = new SecondStep();
            fragmentTercer = new ThirdStep();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void fragment1(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout1, fragmentPrimer);
        fragmentTransaction.commit();
        btn1.setBackgroundColor(0x3CB371);
        btn1.invalidate();
        btn1.setClickable(false);
        btn2.setBackgroundResource(android.R.drawable.btn_default);
        btn2.invalidate();
        btn2.setClickable(true);
        btn3.setBackgroundResource(android.R.drawable.btn_default);
        btn3.invalidate();
        btn3.setClickable(true);
        btnAnt.setVisibility(View.INVISIBLE);
        frg.setTag(1);

    }

    private void fragment2(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout1, fragmentSegon);
        fragmentTransaction.commit();
        btn2.setBackgroundColor(0x3CB371);
        btn2.invalidate();
        btn2.setClickable(false);
        btn1.setBackgroundResource(android.R.drawable.btn_default);
        btn1.invalidate();
        btn1.setClickable(true);
        btn3.setBackgroundResource(android.R.drawable.btn_default);
        btn3.invalidate();
        btn3.setClickable(true);
        btnAnt.setVisibility(View.VISIBLE);
        frg.setTag(2);

    }

    private void fragment3(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout1, fragmentTercer);
        fragmentTransaction.commit();
        btn3.setBackgroundColor(0x3CB371);
        btn3.invalidate();
        btn3.setClickable(false);
        btn2.setBackgroundResource(android.R.drawable.btn_default);
        btn2.invalidate();
        btn2.setClickable(true);
        btn1.setBackgroundResource(android.R.drawable.btn_default);
        btn1.invalidate();
        btn1.setClickable(true);
        btnAnt.setVisibility(View.VISIBLE);
        frg.setTag(3);
    }

    private void save1(){
        
    }

    private void save2(){

    }

    private void save3(){

    }
    
    private void addProduct(){
        int stock = 0;
        float preu = 0;
        String preu1, stock1;
        try {
            try {
               preu1 = fragmentSegon.preu.getText().toString();

            } catch (NumberFormatException e) {
                Toast.makeText(getApplicationContext(), "S'ha d'inserir un preu", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                stock1 = fragmentSegon.stock.getText().toString();
            } catch (NumberFormatException e) {
                Toast.makeText(getApplicationContext(), "S'ha d'inserir un stock", Toast.LENGTH_SHORT).show();
                return;
            }
            boolean activat = fragmentSegon.activat.isChecked();
            String nom = ((EditText) fragmentPrimer.nom).getText().toString();
            String descripcio = ((EditText) fragmentPrimer.descripcio).getText().toString();
            String imatge = fragmentSegon.selectedImageUri.toString();
            
            if(nom.equals("")){
                btn1.setError("Empty Name!");
                fragmentPrimer.nom.setError("Empty Name!");
                return;
            }else {

                if (descripcio.equals("")) {
                    btn1.setError("Empty Description!");
                    fragmentPrimer.descripcio.setError("Empty Description!");
                    return;
                }else{
                    if(imatge.equals(Uri.EMPTY.toString())){
                        btn2.setError("Empty Picture!");
                        fragmentSegon.btnSelectFoto.setError("Empty Picture!");
                        return;
                    }else{
                        if (preu1.equals("")){
                            btn2.setError("Empty Product Prize!");
                            fragmentSegon.preu.setError("Empty Product Prize!");
                        }else{
                            preu = Float.parseFloat(preu1);
                            if (preu < 0){
                                btn2.setError("Invalid Prize value!");
                                fragmentSegon.preu.setError("Invalid Prize value!");
                            }else{
                                if (stock1.equals("")){
                                    btn2.setError("Empty stock");
                                    fragmentSegon.stock.setError("Empty stock");
                                }else {
                                    stock = Integer.parseInt(stock1);
                                    if (stock < 0){
                                        btn2.setError("Invalid Stock value!");
                                        fragmentSegon.stock.setError("Invalid Stock value!");
                                    }else{
                                        ArrayList<Tag> tags = fragmentTercer.tags;

                                            final Producte producte = new Producte(-1, -1, nom, descripcio, preu, stock, imatge, 0);

                                            producte.tags = new String[tags.size()];
                                            int i = 0;
                                            for (Tag tag : tags) {
                                                producte.tags[i++] = tag.nom;
                                            }
                                            producte.activo = activat;

                                            OnBackGruound bg = new OnBackGruound() {
                                                @Override
                                                public boolean onBackGround() {
                                                    DAOProducte bbdd = new DAOProducte(getApplicationContext());
                                                    bbdd.insert(producte);

                                                    return true;
                                                }

                                                @Override
                                                public void onSucces() {
                                                    fragmentPrimer = new FirstStep();
                                                    fragmentSegon = new SecondStep();
                                                    fragmentTercer = new ThirdStep();
                                                    finish();
                                                }

                                                @Override
                                                public void onFailure() {

                                                }
                                            };

                                            BBDDTask task = new BBDDTask(getApplicationContext(), bg);
                                            task.execute();

                                    }
                                }

                            }
                        }
                    }
                }
            }

        }catch(NullPointerException e){
            Toast.makeText(getApplicationContext(), "Falten camps per omplir", Toast.LENGTH_SHORT).show();
        }
        


    }
    

}
