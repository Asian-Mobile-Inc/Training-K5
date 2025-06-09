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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LanguageActivity extends AppCompatActivity {
    private ActivityLanguageBinding binding;
    private ArrayList<String> languages;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLanguageBinding.inflate(getLayoutInflater());

        // Initial language lists
        languages = new ArrayList<>(Arrays.asList(getString(R.string.item_list_language_english),
                getString(R.string.item_list_language_spain),
                getString(R.string.item_list_language_vietnam),
                getString(R.string.item_list_language_france),
                getString(R.string.item_list_language_india),
                getString(R.string.item_list_language_germany),
                getString(R.string.item_list_language_portugal),
                getString(R.string.item_list_language_italy),
                getString(R.string.item_list_language_arabic),
                getString(R.string.item_list_language_japan),
                getString(R.string.item_list_language_korea),
                getString(R.string.item_list_language_south_africa),
                getString(R.string.item_list_language_israel),
                getString(R.string.item_list_language_nigeria),
                getString(R.string.item_list_language_china),
                getString(R.string.item_list_language_turkey),
                getString(R.string.item_list_language_indonesia),
                getString(R.string.item_list_language_netherlands),
                getString(R.string.item_list_language_russia),
                getString(R.string.item_list_language_poland)));

        // Get selected language position
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.user_preferences_key), MODE_PRIVATE);
        int languagePosition = sharedPreferences.getInt(getString(R.string.language_position_key), 0);

        binding.tvLanguage.setText(languages.get(languagePosition));

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
        List<LanguageItem> languageLists = new ArrayList<>();
        languageLists.add(new LanguageItem(R.drawable.ic_english, getString(R.string.language_item_english)));
        languageLists.add(new LanguageItem(R.drawable.ic_spain, getString(R.string.language_item_spain)));
        languageLists.add(new LanguageItem(R.drawable.ic_vietnam, getString(R.string.language_item_vietnam)));
        languageLists.add(new LanguageItem(R.drawable.ic_france, getString(R.string.language_item_france)));
        languageLists.add(new LanguageItem(R.drawable.ic_india, getString(R.string.language_item_india)));
        languageLists.add(new LanguageItem(R.drawable.ic_germany, getString(R.string.language_item_germany)));
        languageLists.add(new LanguageItem(R.drawable.ic_portugal, getString(R.string.language_item_portugal)));
        languageLists.add(new LanguageItem(R.drawable.ic_italy, getString(R.string.language_item_italy)));
        languageLists.add(new LanguageItem(R.drawable.ic_arabic, getString(R.string.language_item_arabic)));
        languageLists.add(new LanguageItem(R.drawable.ic_japan, getString(R.string.language_item_japan)));
        languageLists.add(new LanguageItem(R.drawable.ic_korea, getString(R.string.language_item_korea)));
        languageLists.add(new LanguageItem(R.drawable.ic_south_africa, getString(R.string.language_item_south_africa)));
        languageLists.add(new LanguageItem(R.drawable.ic_israel, getString(R.string.language_item_israel)));
        languageLists.add(new LanguageItem(R.drawable.ic_nigeria, getString(R.string.language_item_nigeria)));
        languageLists.add(new LanguageItem(R.drawable.ic_china, getString(R.string.language_item_china)));
        languageLists.add(new LanguageItem(R.drawable.ic_turkey, getString(R.string.language_item_turkey)));
        languageLists.add(new LanguageItem(R.drawable.ic_indonesia, getString(R.string.language_item_indonesia)));
        languageLists.add(new LanguageItem(R.drawable.ic_netherlands, getString(R.string.language_item_netherlands)));
        languageLists.add(new LanguageItem(R.drawable.ic_russia, getString(R.string.language_item_russia)));
        languageLists.add(new LanguageItem(R.drawable.ic_poland, getString(R.string.language_item_poland)));

        // Setup adapter
        LanguagesAdapter languagesAdapter = new LanguagesAdapter();
        binding.rvLanguage.setLayoutManager(new LinearLayoutManager(this));
        binding.rvLanguage.setAdapter(languagesAdapter);

        // Set position of selected language position
        languagesAdapter.setPosition(languagePosition);
        languagesAdapter.submitList(languageLists);

        binding.ivDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedPosition = languagesAdapter.getSelectedPosition();

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(getString(R.string.language_position_key), selectedPosition);
                editor.apply();

                // Restart activity
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
    }
}
