package com.smartpantry.manager;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

public class SettingsActivity extends AppCompatActivity {
    private static final String PREFS_NAME = "smart_pantry_prefs";
    private static final String KEY_EXPIRY_ALERTS = "expiry_alerts";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SwitchCompat switchExpiryAlerts = findViewById(R.id.switchExpiryAlerts);
        switchExpiryAlerts.setChecked(preferences.getBoolean(KEY_EXPIRY_ALERTS, true));

        switchExpiryAlerts.setOnCheckedChangeListener((buttonView, isChecked) -> {
            preferences.edit().putBoolean(KEY_EXPIRY_ALERTS, isChecked).apply();
        });
    }
}
