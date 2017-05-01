package com.example.dks.restbee;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class WelcomeActivity extends AppCompatActivity {

    TextInputLayout layout_pass,layout_email;
    EditText et_email,et_password;
    Button btn_login,btn_register,btn_student;
    String username="fake",password="fake",email="fake";
    ProgressDialog progressDialog;
    DBHelper dbHelper = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait..");
        variable_in();
        onclicks();
    }


    private void variable_in(){
        btn_login = (Button) findViewById(R.id.btn_login_login);
        btn_register = (Button)findViewById(R.id.btn_register_login);
        btn_student = (Button)findViewById(R.id.btn_student_login_login);
        et_email = (EditText)findViewById(R.id.input_email_login);
        et_password = (EditText)findViewById(R.id.input_password_login);
        layout_email = (TextInputLayout)findViewById(R.id.input_layout_email);
        layout_pass = (TextInputLayout)findViewById(R.id.input_layout_password);
    }

    private boolean validateEmail() {
        String email = et_email.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            layout_email.setError("Invalid Email");
            requestFocus(et_email);
            return false;
        } else {
            layout_email.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {
        if (et_password.getText().toString().length() < 6) {
            layout_pass.setError("Password is too short");
            requestFocus(et_password);
            return false;
        } else {
            layout_pass.setErrorEnabled(false);
        }
        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void save_data(){
        SharedPreferences sharedPreferences = WelcomeActivity.this.getSharedPreferences(getString(R.string.shared_pref_file),MODE_PRIVATE);
        SharedPreferences.Editor editor =  sharedPreferences.edit();
        editor.putString(getString(R.string.pref_email_id),email);
        editor.putString(getString(R.string.pref_pass),password);
        editor.putString(getString(R.string.pref_user_name),username);
        editor.putBoolean(getString(R.string.pref_login_status),true);
        editor.putString(getString(R.string.pref_login_mode),"Faculty");
        editor.commit();
    }


    private void logintask(){

        password =et_password.getText().toString().trim();
        email = et_email.getText().toString().trim();
        if(!validateEmail()){
            progressDialog.dismiss();

            return;
        }else if (!validatePassword()){
            progressDialog.dismiss();
            return;

        }else {
            //TODO : login from SQLite
            Cursor data = dbHelper.getdata(email);
            if (data.getCount() == 0 ){
                Toast.makeText(getApplicationContext(),"Email is not registered",Toast.LENGTH_LONG).show();
            }else{
                while (data.moveToNext()){
                    if (data.getString(1).equals(password) && data.getString(2).equals("admin")){
                        username = data.getString(0);
                        save_data();
                        progressDialog.dismiss();
                        startActivity(new Intent(getApplicationContext(),WelcomeAdmin.class));
                        finish();
                    }else {
                        Toast.makeText(getApplicationContext(),"Invalid password",Toast.LENGTH_LONG).show();
                    }
                }
            }
            progressDialog.dismiss();
        }
    }

    private void onclicks(){
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                logintask();
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RegisterAdminActivity.class));
            }
        });
        btn_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LoginStudentActivity.class));
            }
        });
    }

}
