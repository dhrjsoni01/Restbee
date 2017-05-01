package com.example.dks.restbee;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class RegisterAdminActivity extends AppCompatActivity {

    EditText et_name,et_email,et_mobile,et_pass;
    Button btn_register;
    TextInputLayout layout_name,layout_pass,layout_email,layout_mobile;
    ProgressDialog progressDialog;
    DBHelper dbHelper =new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_admin);
        variable_in();
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait..");
    }

    private void variable_in() {
        et_email = (EditText) findViewById(R.id.input_email_register);
        et_mobile = (EditText) findViewById(R.id.input_phone_register);
        et_name = (EditText) findViewById(R.id.input_name_register);
        et_pass = (EditText) findViewById(R.id.input_password_register);
        btn_register = (Button) findViewById(R.id.btn_register_register);

        layout_email = (TextInputLayout)findViewById(R.id.input_layout_email);
        layout_mobile = (TextInputLayout) findViewById(R.id.input_layout_phone);
        layout_name = (TextInputLayout) findViewById(R.id.input_layout_name);
        layout_pass = (TextInputLayout) findViewById(R.id.input_layout_password);


        et_pass.addTextChangedListener(new MyTextWatcher(et_pass));
        et_mobile.addTextChangedListener(new MyTextWatcher(et_mobile));
        et_pass.addTextChangedListener(new MyTextWatcher(et_pass));
        et_name.addTextChangedListener(new MyTextWatcher(et_name));

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });

    }

    private void submitForm() {
        if (!validateName()) {
            return;
        }

        if (!validateEmail()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }

        regnow();


    }

    private void regnow(){
        progressDialog.show();
        String name,email,pass;
        name = et_name.getText().toString().trim();
        pass =et_pass.getText().toString().trim();
        email = et_email.getText().toString().trim();

        Boolean reg = dbHelper.addAdmin(email,name,pass,"admin");

        progressDialog.dismiss();
        if (reg){
            Log.d("register","done");
            Toast.makeText(getApplicationContext(),"Registration successful",Toast.LENGTH_SHORT).show();
            finish();
        }else {
            Log.d("register","fail");
            Toast.makeText(getApplicationContext(),"Internal Error",Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateName() {
        if (et_name.getText().toString().trim().isEmpty()) {
            layout_name.setError("Please Enter your name");
            requestFocus(et_name);
            return false;
        } else {
            layout_name.setErrorEnabled(false);
        }

        return true;
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
        if (et_pass.getText().toString().length() < 6) {
            layout_pass.setError("Password is too short");
            requestFocus(et_pass);
            return false;
        } else {
            layout_pass.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateMobile() {
        if (et_mobile.getText().toString().length() != 10) {
            layout_mobile.setError("Invalid Mobile");
            requestFocus(et_mobile);
            return false;
        } else {
            layout_mobile.setErrorEnabled(false);
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

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (view.getId()) {
                case R.id.input_name_register:
                    validateName();
                    break;
                case R.id.input_email_register:
                    validateEmail();
                    break;
                case R.id.input_password_register:
                    validatePassword();
                    break;
                case R.id.input_phone_register:
                    validateMobile();
                    break;
            }
        }

    }

}
