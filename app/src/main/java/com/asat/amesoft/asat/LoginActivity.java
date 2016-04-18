package com.asat.amesoft.asat;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.asat.amesoft.asat.fragments.LoginFragment;
import com.asat.amesoft.asat.fragments.PassChangeFragment;


public class LoginActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_login,new PassChangeFragment())
                .commit();


    }


}

