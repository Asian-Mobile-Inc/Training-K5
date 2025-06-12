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
    private TextView mTvTitle;
    private ImageButton mIbSelect;
    private RecyclerView mRvLanguage;
    private LanguageAdapter mLanguageAdapter;
    private ArrayList<Language> mLanguages;
    private Language mLanguage;
    private SharedPreferences mSharedPreferences;
    public static final String LANGUAGE = "language";

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

        mTvTitle = findViewById(R.id.tvTitle);
        mIbSelect = findViewById(R.id.ibSelect);
        mRvLanguage = findViewById(R.id.rvLanguage);
        mLanguages = new ArrayList<>();
        mIbSelect.setImageResource(0);

        createLanguageList();

        mLanguageAdapter = new LanguageAdapter(mLanguages, new LanguageAdapter.OnLanguageSelectedListen() {
            @Override
            public void onLanguageSelected(Language language) {
                mIbSelect.setImageResource(R.drawable.ic_select);
                mLanguage = language;
            }
        });

        mRvLanguage.setLayoutManager(new LinearLayoutManager(this));
        mRvLanguage.setAdapter(mLanguageAdapter);

        mIbSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefs.getInstance(ActivityLanguage.this).putSharedPrefs(LANGUAGE, mLanguage.getCode());
                recreate();
            }
        });
    }

    private void createLanguageList() {
        List<String> languageNames = Arrays.asList(getResources().getStringArray(R.array.language_names));
        List<String> languageCodes = Arrays.asList(getResources().getStringArray(R.array.language_codes));
        if (languageNames.size() == languageCodes.size()) {
            for (int i = 0; i < languageNames.size(); i++) {
                String imageName = "img_" + languageCodes.get(i);
                int imageResId = this.getResources().getIdentifier(imageName, "drawable", this.getPackageName());
                mLanguages.add(new Language(imageResId, languageCodes.get(i), languageNames.get(i)));
            }
        }
    }

    private void setLocale(String code) {
        Locale locale = new Locale(code);
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }
}