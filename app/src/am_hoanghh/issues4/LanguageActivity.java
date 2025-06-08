package issues4;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;

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
import java.util.List;

public class LanguageActivity extends AppCompatActivity {
    private ActivityLanguageBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLanguageBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.clLanguages, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        List<LanguageItem> languageLists = new ArrayList<>();
        languageLists.add(new LanguageItem(R.drawable.ic_english, "English"));
        languageLists.add(new LanguageItem(R.drawable.ic_spain, "Español"));
        languageLists.add(new LanguageItem(R.drawable.ic_vietnam, "Tiếng Việt"));
        languageLists.add(new LanguageItem(R.drawable.ic_france, "Français"));
        languageLists.add(new LanguageItem(R.drawable.ic_india, "हिन्दी"));
        languageLists.add(new LanguageItem(R.drawable.ic_germany, "Deutsch"));
        languageLists.add(new LanguageItem(R.drawable.ic_portugal, "Português"));
        languageLists.add(new LanguageItem(R.drawable.ic_italy, "Italiano"));
        languageLists.add(new LanguageItem(R.drawable.ic_arabic, "عربي"));
        languageLists.add(new LanguageItem(R.drawable.ic_japan, "日本語"));
        languageLists.add(new LanguageItem(R.drawable.ic_korea, "이스라엘"));
        languageLists.add(new LanguageItem(R.drawable.ic_south_africa, "Afrikaans"));
        languageLists.add(new LanguageItem(R.drawable.ic_israel, "ישראל"));
        languageLists.add(new LanguageItem(R.drawable.ic_nigeria, "Harshen Hausa"));
        languageLists.add(new LanguageItem(R.drawable.ic_china, "中国人"));
        languageLists.add(new LanguageItem(R.drawable.ic_turkey, "Türkçe"));
        languageLists.add(new LanguageItem(R.drawable.ic_indonesia, "Bahasa Indonesia"));
        languageLists.add(new LanguageItem(R.drawable.ic_netherlands, "Dutch"));
        languageLists.add(new LanguageItem(R.drawable.ic_russia, "Русский"));
        languageLists.add(new LanguageItem(R.drawable.ic_poland, "Polski"));

        LanguageAdapter languageAdapter = new LanguageAdapter(languageLists);
        binding.rvLanguage.setLayoutManager(new LinearLayoutManager(this));
        binding.rvLanguage.setAdapter(languageAdapter);

        binding.ivDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
            }
        });
    }
}
