package com.example.dks.restbee;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WelcomeAdmin extends AppCompatActivity {

    Button logout,addstudent;
    String name;
    TextView tvname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_admin);

        tvname= (TextView)findViewById(R.id.text_name_faculty);
        addstudent =(Button)findViewById(R.id.btn_take_attendance) ;

        SharedPreferences sharedPreferences = WelcomeAdmin.this.getSharedPreferences(getString(R.string.shared_pref_file),MODE_PRIVATE);
        name= sharedPreferences.getString(getString(R.string.pref_user_name),"fake");
        tvname.setText(name.toUpperCase());

        logout = (Button)findViewById(R.id.btn_logout_faculty);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cleardata();
                startActivity(new Intent(getApplicationContext(),WelcomeActivity.class));
                finish();
            }
        });

        addstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LoginAdminActivity.class));
            }
        });
    }
    private void cleardata(){
        SharedPreferences sharedPreferences = WelcomeAdmin.this.getSharedPreferences(getString(R.string.shared_pref_file),MODE_PRIVATE);
        SharedPreferences.Editor editor =  sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
}
