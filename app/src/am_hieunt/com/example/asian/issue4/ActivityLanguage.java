package com.example.asian.issue4;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asian.R;
import com.example.asian.issue4.adapter.LanguageAdapter;
import com.example.asian.issue4.model.Language;
import com.example.asian.issue4.model.SharedPrefs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class ActivityLanguage extends AppCompatActivity {
    private ImageButton mIbSelect;
    private RecyclerView mRvLanguage;
    private LanguageAdapter mLanguageAdapter;
    private ArrayList<Language> mLanguages;
    private Language mLanguage;
    private static final String LANGUAGE = "language";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLocale(SharedPrefs.getInstance(this).getSharedPrefs(LANGUAGE));
        setContentView(R.layout.activity_language);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mIbSelect = findViewById(R.id.ibSelect);
        mRvLanguage = findViewById(R.id.rvLanguage);
        mLanguages = new ArrayList<>();
        mIbSelect.setImageResource(0);
        createLanguageList();
        mLanguageAdapter = new LanguageAdapter(this, new LanguageAdapter.OnLanguageSelectedListen() {
            @Override
            public void onLanguageSelected(Language language) {
                mIbSelect.setImageResource(R.drawable.ic_select);
                mLanguage = language;
            }
        });
        mRvLanguage.setLayoutManager(new LinearLayoutManager(this));
        mRvLanguage.setAdapter(mLanguageAdapter);
        mLanguageAdapter.submitList(mLanguages);
        initListener();
    }

    private void initListener() {
        mIbSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefs.getInstance(ActivityLanguage.this).putSharedPrefs(LANGUAGE, mLanguage.getCode());
                recreate();
            }
        });
    }

    private void createLanguageList() {
        mLanguages.add(new Language(R.drawable.img_en, getString(R.string.code_language_english), getString(R.string.text_language_english), false));
        mLanguages.add(new Language(R.drawable.img_es, getString(R.string.code_language_spanish), getString(R.string.text_language_spanish), false));
        mLanguages.add(new Language(R.drawable.img_vi, getString(R.string.code_language_vietnamese), getString(R.string.text_language_vietnamese), false));
        mLanguages.add(new Language(R.drawable.img_fr, getString(R.string.code_language_french), getString(R.string.text_language_french), false));
        mLanguages.add(new Language(R.drawable.img_hi, getString(R.string.code_language_hindi), getString(R.string.text_language_hindi), false));
        mLanguages.add(new Language(R.drawable.img_de, getString(R.string.code_language_german), getString(R.string.text_language_german), false));
        mLanguages.add(new Language(R.drawable.img_pt, getString(R.string.code_language_portuguese), getString(R.string.text_language_portuguese), false));
        mLanguages.add(new Language(R.drawable.img_it, getString(R.string.code_language_italian), getString(R.string.text_language_italian), false));
        mLanguages.add(new Language(R.drawable.img_ar, getString(R.string.code_language_arabic), getString(R.string.text_language_arabic), false));
        mLanguages.add(new Language(R.drawable.img_ja, getString(R.string.code_language_japanese), getString(R.string.text_language_japanese), false));
        mLanguages.add(new Language(R.drawable.img_ko, getString(R.string.code_language_korean), getString(R.string.text_language_korean), false));
        mLanguages.add(new Language(R.drawable.img_ur, getString(R.string.code_language_urdu), getString(R.string.text_language_urdu), false));
        mLanguages.add(new Language(R.drawable.img_af, getString(R.string.code_language_afrikaans), getString(R.string.text_language_afrikaans), false));
        mLanguages.add(new Language(R.drawable.img_iw, getString(R.string.code_language_hebrew), getString(R.string.text_language_hebrew), false));
        mLanguages.add(new Language(R.drawable.img_ha, getString(R.string.code_language_hausa), getString(R.string.text_language_hausa), false));
        mLanguages.add(new Language(R.drawable.img_zh, getString(R.string.code_language_chinese), getString(R.string.text_language_chinese), false));
        mLanguages.add(new Language(R.drawable.img_tr, getString(R.string.code_language_turkish), getString(R.string.text_language_turkish), false));
        mLanguages.add(new Language(R.drawable.img_in, getString(R.string.code_language_indonesia), getString(R.string.text_language_indonesia), false));
        mLanguages.add(new Language(R.drawable.img_nl, getString(R.string.code_language_dutch), getString(R.string.text_language_dutch), false));
        mLanguages.add(new Language(R.drawable.img_ru, getString(R.string.code_language_russian), getString(R.string.text_language_russian), false));
        mLanguages.add(new Language(R.drawable.img_pl, getString(R.string.code_language_polish), getString(R.string.text_language_polish), false));

    }

    private void setLocale(String code) {
        Locale locale = new Locale(code);
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }
}
