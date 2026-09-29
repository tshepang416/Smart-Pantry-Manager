package com.smartpantry.manager;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        long recipeId = getIntent().getLongExtra("recipe_id", -1);
        PantryDbHelper dbHelper = new PantryDbHelper(this);
        Recipe recipe = dbHelper.getRecipeById(recipeId);

        TextView titleText = findViewById(R.id.textRecipeTitle);
        TextView ingredientsText = findViewById(R.id.textIngredients);
        TextView methodText = findViewById(R.id.textMethod);

        if (recipe == null) {
            titleText.setText("Recipe not found");
            ingredientsText.setText("");
            methodText.setText("");
            return;
        }

        titleText.setText(recipe.getName());
        ingredientsText.setText(recipe.getIngredients().replace(";", "\n"));
        methodText.setText(recipe.getMethod());
    }
}
