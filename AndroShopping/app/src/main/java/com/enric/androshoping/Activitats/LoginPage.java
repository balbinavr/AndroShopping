package com.enric.androshoping.Activitats;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.enric.androshoping.BBDD.DAOUsuari;
import com.enric.androshoping.Objects.Usuari;
import com.enric.androshoping.R;
import com.enric.androshoping.Singelton.Session;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LoginPage extends ActionBarActivity {

    private Button btnLogin;
    private EditText edtEmail;
    private EditText edtContra;
    private CheckBox checkRecuerda;
    private TextView txtRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        getSupportActionBar().hide();
        prepareActivity();
        assignControls();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login_page, menu);
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
    
    private void prepareActivity(){
        
        DAOUsuari bbdd = new DAOUsuari(getApplicationContext());

        ArrayList<Usuari> usuaris = bbdd.selectAll();
        
        //Toast.makeText(getApplicationContext(), "## Llistat usuaris ##", Toast.LENGTH_SHORT).show();
        /*for(Usuari u : usuaris){
            Toast.makeText(getApplicationContext(), "Usuari: " + u.nom, Toast.LENGTH_SHORT).show();
        }*/
        
    }

    private void assignControls(){
        btnLogin = (Button) findViewById(R.id.btnLogin);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtContra = (EditText) findViewById(R.id.edtContra);
        checkRecuerda = (CheckBox) findViewById(R.id.checkRecuerda);
        txtRegistrar = (TextView) findViewById(R.id.txtRegistrar);

        txtRegistrar.setClickable(true);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                String password = edtContra.getText().toString();

                if (email.matches("")){
                    edtEmail.setError(getText(R.string.error_mail));
                }else {
                    if (isEmailValid(email)) {

                        if ( password.length() < 6){

                            edtContra.setError(getText(R.string.error_min_pass));

                        }else {
                            //Cal comprovar si la contrasenya es correcta per aquest usuari

                            DAOUsuari bbdd = new DAOUsuari(getApplicationContext());
                            
                            Usuari jo = bbdd.autenticaUsuari(email, password);
                            if (jo != null){
                                Session.initUser(jo);
                                
                                if(jo.rol.equals(Usuari.ADMIN)){
                                    Intent i = new Intent(getApplicationContext(), MenuAdmin.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(i);
                                    finish();
                                }else {
                                    //sino setError dient que la contrasenya es incorrecta
                                    Intent i = new Intent(getApplicationContext(), MenuUser.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(i);
                                    finish();
                                }
                            }else{
                                edtContra.setText("");
                                edtEmail.setError(getText(R.string.error_user));
                            }

                        }
                    }else{
                        edtEmail.setError(getText(R.string.error_mail_format));

                    }
                }

            }
        });

        txtRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RegisterPage.class);
                startActivity(i);
            }
        });

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
