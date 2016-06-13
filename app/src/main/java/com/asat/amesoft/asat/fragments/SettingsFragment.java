package com.asat.amesoft.asat.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.asat.amesoft.asat.MyApplication;
import com.asat.amesoft.asat.R;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    Spinner language;
    TextView password;
    Button save,cancel;
    String languageToLoad;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        language = (Spinner) view.findViewById(R.id.setting_language);
        save = (Button) view.findViewById(R.id.setting_save);
        cancel = (Button) view.findViewById(R.id.setting_cancel);
        password = (TextView) view.findViewById(R.id.setting_pass);

        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),R.array.language_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        language.setAdapter(adapter);



        language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item=adapter.getItem(position).subSequence(0,2).toString().toLowerCase();
                String current = Locale.getDefault().getDisplayLanguage().subSequence(0,2).toString().toLowerCase();
                Log.v("language",item);
                Log.v("current lan",current);
                if(!current.equals(item)) {
                    languageToLoad = item;
                    save.setEnabled(true);
                }
                else{
                    save.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               saveChanges();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment f = new PassChangeFragment();
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_main,f).addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }

    private void saveChanges(){

        SharedPreferences prefs =
                getActivity().getSharedPreferences("Preferences",Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("language", languageToLoad);
        editor.apply();
        MyApplication.changeLanguage(languageToLoad,getActivity());

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
        getFragmentManager().popBackStack();
        Snackbar.make(language, R.string.settings_message, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }





}
