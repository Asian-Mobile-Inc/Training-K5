package issues4;

import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.asian.R;
import com.example.asian.databinding.ActivityLanguageBinding;

import android.content.res.Configuration;

import java.util.Locale;

import java.util.ArrayList;
import java.util.List;

public class LanguageActivity extends AppCompatActivity {
    private ActivityLanguageBinding binding;
    private static final String LANGUAGE_CODE = "LANGUAGE_CODE";
    private static final String ENGLISH_LANGUAGE_CODE = "en";
    private static final String SPANISH_LANGUAGE_CODE = "es";
    private static final String VIETNAMESE_LANGUAGE_CODE = "vi";
    private static final String FRENCH_LANGUAGE_CODE = "fr";
    private static final String HINDI_LANGUAGE_CODE = "hi";
    private static final String GERMAN_LANGUAGE_CODE = "de";
    private static final String PORTUGUESE_LANGUAGE_CODE = "pt";
    private static final String ITALIAN_LANGUAGE_CODE = "it";
    private static final String ARABIC_LANGUAGE_CODE = "ar";
    private static final String JAPANESE_LANGUAGE_CODE = "ja";
    private static final String KOREAN_LANGUAGE_CODE = "ko";
    private static final String SOUTH_AFRIKAANS_LANGUAGE_CODE = "af";
    private static final String HEBREW_LANGUAGE_CODE = "iw";
    private static final String HAUSA_LANGUAGE_CODE = "ha";
    private static final String CHINESE_LANGUAGE_CODE = "zh";
    private static final String TURKISH_LANGUAGE_CODE = "tr";
    private static final String BAHASA_INDONESIA_LANGUAGE_CODE = "in";
    private static final String DUTCH_LANGUAGE_CODE = "nl";
    private static final String RUSSIAN_LANGUAGE_CODE = "ru";
    private static final String POLISH_LANGUAGE_CODE = "pl";

    private int languagePosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLanguageBinding.inflate(getLayoutInflater());

        // Get selected language position
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.user_preferences_key), MODE_PRIVATE);
        String languageCode = sharedPreferences.getString(LANGUAGE_CODE, "");

        if (languageCode.isEmpty()) {
            languageCode = ENGLISH_LANGUAGE_CODE;
        }

        // Localization
        Locale locale = new Locale(languageCode);
        Configuration config = getBaseContext().getResources().getConfiguration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        // Set textview language
        binding.tvLanguage.setText(getString(R.string.language));

        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.clLanguages, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Setup toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        // Initial language item of recyclerview
        List<LanguageItem> languageLists = getLanguageLists(languageCode);

        // Setup adapter
        LanguagesAdapter languagesAdapter = new LanguagesAdapter(languageLists);
        binding.rvLanguage.setLayoutManager(new LinearLayoutManager(this));
        binding.rvLanguage.setAdapter(languagesAdapter);

        // Set position of selected language position
        languagesAdapter.setPosition(languagePosition);
        languagesAdapter.submitList(languageLists);

        binding.ivDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedPosition = languagesAdapter.getSelectedPosition();

                String languageCode = languageLists.get(selectedPosition).getLanguageCode();

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(LANGUAGE_CODE, languageCode);
                editor.apply();

                // Restart activity
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
    }

    public ArrayList<LanguageItem> getLanguageLists(String languageCode) {
        ArrayList<LanguageItem> languageLists = new ArrayList<>();

        languageLists.add(new LanguageItem(R.drawable.ic_english, getString(R.string.language_item_english), ENGLISH_LANGUAGE_CODE));
        languageLists.add(new LanguageItem(R.drawable.ic_spain, getString(R.string.language_item_spainish), SPANISH_LANGUAGE_CODE));
        languageLists.add(new LanguageItem(R.drawable.ic_vietnam, getString(R.string.language_item_vietnamese), VIETNAMESE_LANGUAGE_CODE));
        languageLists.add(new LanguageItem(R.drawable.ic_france, getString(R.string.language_item_french), FRENCH_LANGUAGE_CODE));
        languageLists.add(new LanguageItem(R.drawable.ic_india, getString(R.string.language_item_hindi), HINDI_LANGUAGE_CODE));
        languageLists.add(new LanguageItem(R.drawable.ic_germany, getString(R.string.language_item_german), GERMAN_LANGUAGE_CODE));
        languageLists.add(new LanguageItem(R.drawable.ic_portugal, getString(R.string.language_item_portuguese), PORTUGUESE_LANGUAGE_CODE));
        languageLists.add(new LanguageItem(R.drawable.ic_italy, getString(R.string.language_item_italian), ITALIAN_LANGUAGE_CODE));
        languageLists.add(new LanguageItem(R.drawable.ic_arabic, getString(R.string.language_item_arabic), ARABIC_LANGUAGE_CODE));
        languageLists.add(new LanguageItem(R.drawable.ic_japan, getString(R.string.language_item_japanese), JAPANESE_LANGUAGE_CODE));
        languageLists.add(new LanguageItem(R.drawable.ic_korea, getString(R.string.language_item_korean), KOREAN_LANGUAGE_CODE));
        languageLists.add(new LanguageItem(R.drawable.ic_south_africa, getString(R.string.language_item_south_afrikaans), SOUTH_AFRIKAANS_LANGUAGE_CODE));
        languageLists.add(new LanguageItem(R.drawable.ic_israel, getString(R.string.language_item_hebrew), HEBREW_LANGUAGE_CODE));
        languageLists.add(new LanguageItem(R.drawable.ic_nigeria, getString(R.string.language_item_hausa), HAUSA_LANGUAGE_CODE));
        languageLists.add(new LanguageItem(R.drawable.ic_china, getString(R.string.language_item_chinese), CHINESE_LANGUAGE_CODE));
        languageLists.add(new LanguageItem(R.drawable.ic_turkey, getString(R.string.language_item_turkish), TURKISH_LANGUAGE_CODE));
        languageLists.add(new LanguageItem(R.drawable.ic_indonesia, getString(R.string.language_item_bahasa_indonesia), BAHASA_INDONESIA_LANGUAGE_CODE));
        languageLists.add(new LanguageItem(R.drawable.ic_netherlands, getString(R.string.language_item_dutch), DUTCH_LANGUAGE_CODE));
        languageLists.add(new LanguageItem(R.drawable.ic_russia, getString(R.string.language_item_russian), RUSSIAN_LANGUAGE_CODE));
        languageLists.add(new LanguageItem(R.drawable.ic_poland, getString(R.string.language_item_polish), POLISH_LANGUAGE_CODE));

        for (int i = 0; i < languageLists.size(); i++) {
            if (languageLists.get(i).getLanguageCode().equals(languageCode)) {
                languageLists.get(i).setSelected(true);
                languagePosition = i;
            }
        }

        return languageLists;
    }
}
