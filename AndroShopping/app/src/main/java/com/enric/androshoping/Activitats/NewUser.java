package com.enric.androshoping.Activitats;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import com.enric.androshoping.BBDD.DAOUsuari;
import com.enric.androshoping.Interfaces.OnBackGruound;
import com.enric.androshoping.Objects.DatePickerFragment;
import com.enric.androshoping.Objects.Usuari;
import com.enric.androshoping.R;
import com.enric.androshoping.Singelton.Session;
import com.enric.androshoping.Tasks.BBDDTask;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class NewUser extends ActionBarActivity {

    private String tipusPantalla;
    private Button btnClick;
    private EditText edtEmail;
    private EditText edtContra1;
    private EditText edtContra2;
    private RadioGroup radioGroup;
    private RadioButton radioButton_hom;
    private RadioButton radioButton_muj;
    private EditText edtNombre;
    private EditText edtDate;
    private CheckBox checkCondicions;
    private Spinner spinner;
    private String tipus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle(R.string.title_newUser);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        tipus = getIntent().getExtras().getString("Tipus");
        btnClick = (Button) findViewById(R.id.btnRegistrarse);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtContra1 = (EditText) findViewById(R.id.edtContra1);
        edtContra2 = (EditText) findViewById(R.id.edtContra2);
        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        radioButton_hom = (RadioButton) findViewById(R.id.radiobutton_hom);
        radioButton_muj = (RadioButton) findViewById(R.id.radiobutton_muj);
        edtNombre = (EditText)findViewById(R.id.edtNombre);
        checkCondicions = (CheckBox) findViewById(R.id.checkcondicions);

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayList a = new ArrayList<String>();
        for ( int i=18;i<=100; i++){
            a.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,a);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        if (tipus.equals("user")){
            setTitle(R.string.title_perfil);
            tipusPantalla = tipus;
            btnClick.setText(R.string.modificar);

            edtEmail.setText(Session.usuari.email);
            edtContra1.setText(Session.usuari.contrasenya);
            edtContra2.setText(Session.usuari.contrasenya);
            switch (Session.usuari.genere){
                case 1:
                    radioButton_muj.setChecked(true);
                    break;
                case 0:
                    radioButton_hom.setChecked(true);
                    break;
                default:
                    radioButton_muj.setChecked(true);
                    break;
            }
            edtNombre.setText(Session.usuari.nom);
            checkCondicions.setChecked(true);

            int spinnerPosition = adapter.getPosition(Integer.toString(Session.usuari.edat));
            spinner.setSelection(spinnerPosition);

            btnClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    comprovaUsuari();

                }
            });


        }else{
            if (tipus.equals("admin")){
                setTitle(R.string.title_newUser);
                tipusPantalla = tipus;
                btnClick.setText(R.string.crear);

                radioButton_muj.setChecked(true);
            }
        }


        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Usuari user = comprovaUsuari();
                if (user!= null){
                    if (tipus.equals("user")){
                        user.id = Session.usuari.id;

                        //modifica l'usuari amb els nous valors
                        DAOUsuari bbdd = new DAOUsuari(getApplicationContext());
                        bbdd.update(user);

                        Session.usuari=user;
                        Intent i = new Intent(getApplicationContext(), MenuUser.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        finish();
                        startActivity(i);

                    }else{
                        if (tipus.equals("admin")){
                            //insereix l'usuari
                            DAOUsuari bbdd = new DAOUsuari(getApplicationContext());

                            long retorn = bbdd.insert(user);
                            Toast.makeText(getApplicationContext(), "Retorn: " + retorn, Toast.LENGTH_SHORT).show();

                            if( retorn <= 0){
                                edtEmail.setError("This mail is occupied!");
                            }else {
                                finish();
                            }
                        }
                    }
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
        getMenuInflater().inflate(R.menu.menu_new_user, menu);
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

        if ( id == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private Usuari comprovaUsuari(){
        String email = edtEmail.getText().toString();
        String contra1 = edtContra1.getText().toString();
        String contra2 = edtContra2.getText().toString();
        String nom = edtNombre.getText().toString();


        if (email.matches("")){
            edtEmail.setError("Empty email!");
        }else {
            if (isEmailValid(email)) {

                if (contra1.matches("")){
                    edtContra1.setError("Empty password!");
                }else {

                    if (contra1.length() < 6) {
                        edtContra1.setError("The minimum password lenght is 6");
                    } else {
                        if (contra2.matches("")){
                            edtContra2.setError("Empty password!");
                        }else{
                            if (!contra2.equals(contra1)){
                                edtContra2.setError("Both passwords doesn't match!");
                            }else{
                                if (nom.matches("")){
                                    edtNombre.setError("Empty Name!");
                                }else{

                                    String edat = spinner.getSelectedItem().toString();

                                    if (!checkCondicions.isChecked()) {
                                        checkCondicions.setError("Conditions must be accepted to continue!");
                                    } else {
                                        //Crear el nou usuari!!
                                        int selectedId = radioGroup.getCheckedRadioButtonId();
                                        int sex;
                                        switch (selectedId){
                                            case R.id.radiobutton_muj:
                                                sex = 1;
                                                break;
                                            case R.id.radiobutton_hom:
                                                sex = 0;
                                                break;
                                            default:
                                                sex = 1;
                                                break;
                                        }

                                        Usuari usuari = new Usuari(nom, Integer.parseInt(edat), email, contra1, sex , "user");

                                        return usuari;
                                    }
                                }
                            }
                        }


                    }
                }
            }else{
                edtEmail.setError("Invalid mail format!");

            }
        }
        return null;
    }

    private boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

}
