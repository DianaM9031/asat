package com.asat.amesoft.asat.fragments;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.asat.amesoft.asat.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private EditText user,pass;
    private Button submit;
    private boolean isEmpty =true;
    private CheckBox checkBox;
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            checkFieldsForEmptyValues();
        }
    };

    private void checkFieldsForEmptyValues(){

        if(this.user.getText().toString().equals("") || this.pass.getText().toString().equals("")){
            isEmpty=true;
        }
        else{
            isEmpty=false;
        }
    }

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        user = (EditText) view.findViewById(R.id.login_user);
        pass = (EditText) view.findViewById(R.id.login_password);

        user.addTextChangedListener(textWatcher);
        pass.addTextChangedListener(textWatcher);

        checkBox = (CheckBox) view.findViewById(R.id.login_checkbox);
        checkBox.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                        if(!isEmpty){
                            if(isChecked)
                                submit.setEnabled(true);
                            else
                                submit.setEnabled(false);
                        }
                    }
                }
        );
        submit = (Button) view.findViewById(R.id.login_submit);
        return view;
    }

}
