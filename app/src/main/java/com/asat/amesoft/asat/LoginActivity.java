package com.asat.amesoft.asat;


import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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
        change_content(new LoginFragment());



    }

    public void forgot_pass(View view){
        change_content(new PassChangeFragment());
    }

    public void login_submit(View view){
        change_content(new PassChangeFragment());
    }



    private void change_content(Fragment f){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_login,f).addToBackStack(null)
                .commit();
    }
}

