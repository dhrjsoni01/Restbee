package com.example.dks.restbee;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WelcomeStudent extends AppCompatActivity {

    Button logout;
    String name;
    TextView tvname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_student);

        tvname= (TextView)findViewById(R.id.text_name_student);

        SharedPreferences sharedPreferences = WelcomeStudent.this.getSharedPreferences(getString(R.string.shared_pref_file),MODE_PRIVATE);
        name= sharedPreferences.getString(getString(R.string.student_name),"fake");
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
    }
    private void cleardata(){
        SharedPreferences sharedPreferences = WelcomeStudent.this.getSharedPreferences(getString(R.string.shared_pref_file),MODE_PRIVATE);
        SharedPreferences.Editor editor =  sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
}
