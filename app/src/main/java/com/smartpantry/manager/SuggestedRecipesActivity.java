package com.smartpantry.manager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SuggestedRecipesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView emptyStateText;
    private PantryDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_recipes);

        setTitle(R.string.suggested_title);
        dbHelper = new PantryDbHelper(this);

        recyclerView = findViewById(R.id.recyclerRecipes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        emptyStateText = findViewById(R.id.textEmptyState);

        loadSuggestedRecipes();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSuggestedRecipes();
    }

    private void loadSuggestedRecipes() {
        List<Ingredient> pantry = dbHelper.getAllIngredients();
        List<Recipe> recipes = dbHelper.getAllRecipes();
        List<Recipe> suggested = RecipeMatcher.getSuggestedRecipes(pantry, recipes);

        if (suggested.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyStateText.setVisibility(View.VISIBLE);
            return;
        }

        recyclerView.setVisibility(View.VISIBLE);
        emptyStateText.setVisibility(View.GONE);

        RecipeAdapter adapter = new RecipeAdapter(suggested, recipe -> {
            Intent intent = new Intent(this, RecipeDetailActivity.class);
            intent.putExtra("recipe_id", recipe.getId());
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);
    }
}
