package com.example.dks.restbee;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginStudentActivity extends AppCompatActivity {

    String roll,Stringpass,name;
    Button btn_login,btn_forget;
    EditText et_enroll,et_pass;
    TextInputLayout layout_enroll,layout_pass;
    ProgressDialog progressDialog;
    DBHelper dbHelper = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_student);
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait..");
        variable_in();
    }

    private boolean validatePassword() {
        if (et_pass.getText().toString().length() < 6) {
            layout_pass.setError("Password is too short");
            requestFocus(et_pass);
            return false;
        } else {
            layout_pass.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateEnroll() {
        if (et_enroll.getText().toString().length() != 12) {
            layout_enroll.setError("Invalid Enrollment No.");
            requestFocus(et_enroll);
            return false;
        } else {
            layout_enroll.setErrorEnabled(false);
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void variable_in(){
        btn_forget = (Button) findViewById(R.id.btn_forget_student);
        btn_login = (Button) findViewById(R.id.btn_login_student);
        et_enroll = (EditText) findViewById(R.id.input_enroll_student);
        et_pass = (EditText) findViewById(R.id.input_password_student);
        layout_enroll = (TextInputLayout) findViewById(R.id.input_layout_enroll);
        layout_pass = (TextInputLayout) findViewById(R.id.input_layout_password);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login(){
        if (!validateEnroll()){
            return;
        }
        if (!validatePassword()){
            return;
        }

        // logic on login...
        progressDialog.show();
        roll = et_enroll.getText().toString().trim();
        Stringpass = et_pass.getText().toString().trim();

        Cursor data = dbHelper.getdata(roll);
        if (data.getCount() == 0 ){
            Toast.makeText(getApplicationContext(),"Enroll is not registered",Toast.LENGTH_LONG).show();
        }else{
            while (data.moveToNext()){
                if (data.getString(1).equals(Stringpass)&& data.getString(2).equals("student")){
                    name = data.getString(0);
                    save_data();
                    progressDialog.dismiss();
                    startActivity(new Intent(getApplicationContext(),WelcomeStudent.class));
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(),"Invalid password",Toast.LENGTH_LONG).show();
                }
            }
        }

        //TODO : LOGIN logic

        progressDialog.dismiss();

    }
    private void save_data(){
        SharedPreferences sharedPreferences = LoginStudentActivity.this.getSharedPreferences(getString(R.string.shared_pref_file),MODE_PRIVATE);
        SharedPreferences.Editor editor =  sharedPreferences.edit();
        editor.putString(getString(R.string.student_roll),roll);
        editor.putString(getString(R.string.student_pass),Stringpass);
        editor.putString(getString(R.string.student_name),name);
        editor.putBoolean(getString(R.string.student_login_status),true);
        editor.putString(getString(R.string.pref_login_mode),"Student");
        editor.commit();
    }
}
