package com.example.dks.restbee;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextInputLayout layout_pass,layout_email;
    EditText et_email,et_password;
    Button btn_login,btn_register,btn_student;
    String username="fake",password="fake",email="fake";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences(getString(R.string.shared_pref_file),MODE_PRIVATE);
        String mode =sharedPreferences.getString(getString(R.string.pref_login_mode),"error");
        if (mode.equals("Student")){
            if (sharedPreferences.getBoolean(getString(R.string.student_login_status),false)){
                startActivity(new Intent(getApplicationContext(),WelcomeStudent.class));
                finish();
            }
        }else if (mode.equals("Faculty")) {
            if (sharedPreferences.getBoolean(getString(R.string.pref_login_status), false)) {
                Intent welcome = new Intent(getApplicationContext(), WelcomeAdmin.class);
                startActivity(welcome);
                finish();
            }
        }else {
            startActivity(new Intent(this, WelcomeActivity.class));
            finish();
        }
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
        SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences(getString(R.string.shared_pref_file),MODE_PRIVATE);
        SharedPreferences.Editor editor =  sharedPreferences.edit();
        editor.putString(getString(R.string.pref_email_id),email);
        editor.putString(getString(R.string.pref_pass),password);
        editor.putString(getString(R.string.pref_user_name),username);
        editor.putBoolean(getString(R.string.pref_login_status),true);
        editor.putString(getString(R.string.pref_login_mode),"Faculty");
        editor.commit();
    }


}
