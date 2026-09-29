package com.smartpantry.manager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddEditIngredientActivity extends AppCompatActivity {
    private PantryDbHelper dbHelper;
    private EditText editIngredientName;
    private EditText editQuantity;
    private EditText editUnit;
    private EditText editExpiryDate;
    private long ingredientId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_ingredient);

        dbHelper = new PantryDbHelper(this);

        editIngredientName = findViewById(R.id.editIngredientName);
        editQuantity = findViewById(R.id.editQuantity);
        editUnit = findViewById(R.id.editUnit);
        editExpiryDate = findViewById(R.id.editExpiryDate);
        Button saveButton = findViewById(R.id.buttonSaveIngredient);
        Button deleteButton = findViewById(R.id.buttonDeleteIngredient);

        ingredientId = getIntent().getLongExtra("ingredient_id", -1);

        if (ingredientId > 0) {
            Ingredient ingredient = dbHelper.getIngredientById(ingredientId);
            if (ingredient != null) {
                editIngredientName.setText(ingredient.getName());
                editQuantity.setText(String.valueOf(ingredient.getQuantity()));
                editUnit.setText(ingredient.getUnit());
                editExpiryDate.setText(ingredient.getExpiryDate());
                deleteButton.setVisibility(Button.VISIBLE);
                setTitle("Edit Ingredient");
            }
        } else {
            setTitle("Add Ingredient");
        }

        saveButton.setOnClickListener(v -> saveIngredient());
        deleteButton.setOnClickListener(v -> deleteIngredient());
    }

    private void saveIngredient() {
        String name = editIngredientName.getText().toString().trim();
        if (name.isEmpty()) {
            Toast.makeText(this, "Ingredient name is required.", Toast.LENGTH_SHORT).show();
            return;
        }

        String quantityText = editQuantity.getText().toString().trim();
        if (quantityText.isEmpty()) {
            Toast.makeText(this, "Quantity is required.", Toast.LENGTH_SHORT).show();
            return;
        }

        double quantity;
        try {
            quantity = Double.parseDouble(quantityText);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid quantity.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (quantity <= 0) {
            Toast.makeText(this, "Quantity must be greater than zero.", Toast.LENGTH_SHORT).show();
            return;
        }

        String unit = editUnit.getText().toString().trim();
        if (unit.isEmpty()) {
            unit = "units";
        }

        String expiryDate = editExpiryDate.getText().toString().trim();
        Ingredient ingredient = new Ingredient(name, quantity, unit, expiryDate);

        if (ingredientId > 0) {
            ingredient.setId(ingredientId);
            dbHelper.updateIngredient(ingredient);
        } else {
            dbHelper.addIngredient(ingredient);
        }

        setResult(RESULT_OK);
        finish();
    }

    private void deleteIngredient() {
        if (ingredientId > 0) {
            dbHelper.deleteIngredient(ingredientId);
        }
        setResult(RESULT_OK);
        finish();
    }
}
