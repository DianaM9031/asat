package com.asat.amesoft.asat;


import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.asat.amesoft.asat.fragments.LOPDFragment;
import com.asat.amesoft.asat.fragments.LoginFragment;
import com.asat.amesoft.asat.fragments.NewPassFragment;
import com.asat.amesoft.asat.fragments.PassChangeFragment;


public class LoginActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.

        Bundle bundle = new Bundle();
        bundle.putBoolean("accept",true);
//        Fragment fragment = new LOPDFragment();
//        Fragment fragment = new LoginFragment();
        Fragment fragment = new PassChangeFragment();
        fragment.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_login,fragment)
                .commit();


    }


}

