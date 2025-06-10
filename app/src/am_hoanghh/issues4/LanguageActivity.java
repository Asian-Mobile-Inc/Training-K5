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
    private int languagePosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLanguageBinding.inflate(getLayoutInflater());

        // Get selected language position
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.user_preferences_key), MODE_PRIVATE);
        String languageCode = sharedPreferences.getString(LANGUAGE_CODE, "");

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

        languageLists.add(new LanguageItem(R.drawable.ic_english, getString(R.string.language_item_english), getString(R.string.language_code_english)));
        languageLists.add(new LanguageItem(R.drawable.ic_spain, getString(R.string.language_item_spainish), getString(R.string.language_code_spainish)));
        languageLists.add(new LanguageItem(R.drawable.ic_vietnam, getString(R.string.language_item_vietnamese), getString(R.string.language_code_vietnamese)));
        languageLists.add(new LanguageItem(R.drawable.ic_france, getString(R.string.language_item_french), getString(R.string.language_code_french)));
        languageLists.add(new LanguageItem(R.drawable.ic_india, getString(R.string.language_item_hindi), getString(R.string.language_code_hindi)));
        languageLists.add(new LanguageItem(R.drawable.ic_germany, getString(R.string.language_item_german), getString(R.string.language_code_german)));
        languageLists.add(new LanguageItem(R.drawable.ic_portugal, getString(R.string.language_item_portuguese), getString(R.string.language_code_portuguese)));
        languageLists.add(new LanguageItem(R.drawable.ic_italy, getString(R.string.language_item_italian), getString(R.string.language_code_italian)));
        languageLists.add(new LanguageItem(R.drawable.ic_arabic, getString(R.string.language_item_arabic), getString(R.string.language_code_arabic)));
        languageLists.add(new LanguageItem(R.drawable.ic_japan, getString(R.string.language_item_japanese), getString(R.string.language_code_japanese)));
        languageLists.add(new LanguageItem(R.drawable.ic_korea, getString(R.string.language_item_korean), getString(R.string.language_code_korean)));
        languageLists.add(new LanguageItem(R.drawable.ic_south_africa, getString(R.string.language_item_south_afrikaans), getString(R.string.language_code_south_afrikaans)));
        languageLists.add(new LanguageItem(R.drawable.ic_israel, getString(R.string.language_item_hebrew), getString(R.string.language_code_hebrew)));
        languageLists.add(new LanguageItem(R.drawable.ic_nigeria, getString(R.string.language_item_hausa), getString(R.string.language_code_hausa)));
        languageLists.add(new LanguageItem(R.drawable.ic_china, getString(R.string.language_item_chinese), getString(R.string.language_code_chinese)));
        languageLists.add(new LanguageItem(R.drawable.ic_turkey, getString(R.string.language_item_turkish), getString(R.string.language_code_turkish)));
        languageLists.add(new LanguageItem(R.drawable.ic_indonesia, getString(R.string.language_item_bahasa_indonesia), getString(R.string.language_code_bahasa_indonesia)));
        languageLists.add(new LanguageItem(R.drawable.ic_netherlands, getString(R.string.language_item_dutch), getString(R.string.language_code_dutch)));
        languageLists.add(new LanguageItem(R.drawable.ic_russia, getString(R.string.language_item_russian), getString(R.string.language_code_russian)));
        languageLists.add(new LanguageItem(R.drawable.ic_poland, getString(R.string.language_item_polish), getString(R.string.language_code_polish)));

        for (int i = 0; i < languageLists.size(); i++) {
            if (languageLists.get(i).getLanguageCode().equals(languageCode)) {
                languageLists.get(i).setSelected(true);
                languagePosition = i;
            }
        }

        return languageLists;
    }
}
