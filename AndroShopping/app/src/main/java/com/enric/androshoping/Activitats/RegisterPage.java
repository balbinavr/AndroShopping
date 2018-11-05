package com.enric.androshoping.Activitats;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.enric.androshoping.BBDD.DAOUsuari;
import com.enric.androshoping.Objects.DatePickerFragment;
import com.enric.androshoping.Objects.Usuari;
import com.enric.androshoping.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterPage extends ActionBarActivity {

    private EditText edtEmail;
    private EditText edtContra1;
    private EditText edtContra2;
    private RadioGroup radioGroup;
    private RadioButton radioButton_hom;
    private RadioButton radioButton_muj;
    private EditText edtNombre;
    private EditText edtDate;
    private ImageButton btnDatePicker;
    private CheckBox checkCondicions;
    private Button btnRegistrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        getSupportActionBar().hide();

        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtContra1 = (EditText) findViewById(R.id.edtContra1);
        edtContra2 = (EditText) findViewById(R.id.edtContra2);
        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        radioButton_hom = (RadioButton) findViewById(R.id.radiobutton_hom);
        radioButton_muj = (RadioButton) findViewById(R.id.radiobutton_muj);
        edtNombre = (EditText)findViewById(R.id.edtNombre);
        edtDate = (EditText)findViewById(R.id.edtDate);
        btnDatePicker = (ImageButton) findViewById(R.id.btnDate_picker);
        checkCondicions = (CheckBox) findViewById(R.id.checkcondicions);
        btnRegistrarse = (Button) findViewById(R.id.btnRegistrarse);

        radioButton_muj.setChecked(true);
        edtDate.setKeyListener(null);
        edtDate.setTextIsSelectable(true);
        edtDate.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(edtDate.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                return false;
            }
        });


        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                afegeixUsuari();

            }
        });


        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // DialogFragment newFragment = new DatePickerFragment();
                // newFragment.show(getSupportFragmentManager(), "datePicker");
                showDatePicker();
            }
        });

    }


    private void showDatePicker() {

        DatePickerFragment date = new DatePickerFragment();
        //Set Up Current Date Into dialog
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);

        //Set Call back to capture selected date
        date.setCallBack(ondate);
        date.show(getSupportFragmentManager(), "Date Picker");
    }

    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            edtDate.setText(String.valueOf(dayOfMonth)+"/"+String.valueOf(monthOfYear+1)+ "/"+String.valueOf(year));
        }
    };

    private int CalculEdat(String data_naix) {

        //fecha_nac debe tener el formato dd/MM/yyyy

        Date data_actual = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String avui = format.format(data_actual);
        String[] dat1 = data_naix.split("/");
        String[] dat2 = avui.split("/");
        int anys = Integer.parseInt(dat2[2]) - Integer.parseInt(dat1[2]);
        int mes = Integer.parseInt(dat2[1]) - Integer.parseInt(dat1[1]);
        if (mes < 0) {
            anys = anys - 1;
        } else if (mes == 0) {
            int dia = Integer.parseInt(dat2[0]) - Integer.parseInt(dat1[0]);
            if (dia > 0) {
                anys = anys - 1;
            }
        }
        return anys;
    }


    private void afegeixUsuari(){
        String email = edtEmail.getText().toString();
        String contra1 = edtContra1.getText().toString();
        String contra2 = edtContra2.getText().toString();
        String nom = edtNombre.getText().toString();
        String data = edtDate.getText().toString();

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
                                    if ( data.matches("")){
                                        edtDate.setError("A date must be selected!");
                                    }else {

                                        String[] parts = data.split("/");
                                        String day = parts[0];
                                        String month = parts[1];
                                        String year = parts[2];

                                        if ( Integer.parseInt(year) > Calendar.getInstance().get(Calendar.YEAR)){
                                            edtDate.setError("Invalid Date!");
                                        }else {
                                            if ( Integer.parseInt(year) == Calendar.getInstance().get(Calendar.YEAR) && (Integer.parseInt(month)-1) > Calendar.getInstance().get(Calendar.MONTH)){
                                                edtDate.setError("Invalid Date!");
                                            }else {

                                                if ( Integer.parseInt(year) == Calendar.getInstance().get(Calendar.YEAR) && (Integer.parseInt(month)-1) == Calendar.getInstance().get(Calendar.MONTH) && Integer.parseInt(day) > Calendar.getInstance().get(Calendar.DATE)){
                                                    edtDate.setError("Invalid Date!");
                                                }else {
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

                                                        Usuari usuari = new Usuari(nom, CalculEdat(data), email, contra1, sex , "user");

                                                        DAOUsuari bbdd = new DAOUsuari(getApplicationContext());

                                                        long retorn = bbdd.insert(usuari);
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
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register_page, menu);
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

        return super.onOptionsItemSelected(item);
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
