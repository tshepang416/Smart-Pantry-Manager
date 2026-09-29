package com.smartpantry.manager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class PantryActivity extends AppCompatActivity {
    private static final int REQUEST_MANAGE_INGREDIENT = 101;

    private PantryDbHelper dbHelper;
    private RecyclerView recyclerPantry;
    private List<Ingredient> pantryItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.pantry_title);
        }

        dbHelper = new PantryDbHelper(this);
        recyclerPantry = findViewById(R.id.recyclerPantry);
        recyclerPantry.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fabAddIngredient = findViewById(R.id.fabAddIngredient);
        fabAddIngredient.setOnClickListener(v -> openIngredientEditor(-1));

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.action_pantry);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.action_suggested) {
                startActivity(new Intent(this, SuggestedRecipesActivity.class));
                return true;
            }
            if (itemId == R.id.action_settings) {
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            }
            return true;
        });

        loadPantry();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPantry();
    }

    private void openIngredientEditor(long ingredientId) {
        Intent intent = new Intent(this, AddEditIngredientActivity.class);
        if (ingredientId > 0) {
            intent.putExtra("ingredient_id", ingredientId);
        }
        startActivityForResult(intent, REQUEST_MANAGE_INGREDIENT);
    }

    private void loadPantry() {
        pantryItems = dbHelper.getAllIngredients();
        PantryAdapter adapter = new PantryAdapter(pantryItems, ingredient -> openIngredientEditor(ingredient.getId()));
        recyclerPantry.setAdapter(adapter);

        if (pantryItems.isEmpty()) {
            Toast.makeText(this, "Your pantry is empty. Add ingredients to get suggestions.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_MANAGE_INGREDIENT && resultCode == RESULT_OK) {
            loadPantry();
        }
    }
}
