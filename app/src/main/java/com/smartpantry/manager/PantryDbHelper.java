package com.smartpantry.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class PantryDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "smart_pantry.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_PANTRY = "pantry_items";
    public static final String TABLE_RECIPES = "recipes";

    public PantryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_PANTRY + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "quantity REAL NOT NULL, " +
                "unit TEXT, " +
                "expiry_date TEXT)"
        );

        db.execSQL("CREATE TABLE " + TABLE_RECIPES + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "ingredients TEXT NOT NULL, " +
                "method TEXT NOT NULL)"
        );

        seedRecipes(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PANTRY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        onCreate(db);
    }

    private void seedRecipes(SQLiteDatabase db) {
        insertRecipe(db, "Tomato Pasta", "200 g pasta; 2 tomatoes; 1 onion; 2 tbsp olive oil; 1 clove garlic",
                "Boil the pasta until tender. Chop the onion, tomatoes, and garlic. Fry the onion and garlic in olive oil, then add the tomatoes. Toss in the cooked pasta and serve warm.");
        insertRecipe(db, "Veggie Omelette", "2 eggs; 1 onion; 1 tomato; 1 tsp salt; 1 tbsp butter",
                "Beat the eggs with salt. Fry the onion and tomato in butter, pour in the eggs, and cook until set.");
        insertRecipe(db, "Banana Oat Smoothie", "1 banana; 1 cup oats; 1 cup milk; 1 tbsp honey",
                "Blend all ingredients until smooth and pour into a glass.");
        insertRecipe(db, "Vegetable Stir-fry", "2 carrots; 1 bell pepper; 1 onion; 200 g rice; 2 tbsp soy sauce",
                "Cook the rice. Stir-fry the vegetables with soy sauce, then serve over the rice.");
        insertRecipe(db, "Bean Salad", "1 can beans; 1 tomato; 1 cucumber; 1 red onion; 1 tbsp olive oil",
                "Chop all vegetables and mix with beans and olive oil. Season to taste and serve chilled.");
        insertRecipe(db, "Veggie Wrap", "2 tortillas; 1 tomato; 1 cucumber; 1 carrot; 1 tbsp hummus",
                "Spread hummus on tortillas, add chopped vegetables, and roll tightly.");
        insertRecipe(db, "Chicken Rice Bowl", "1 cup rice; 1 chicken breast; 1 carrot; 1 cup peas; 2 tbsp soy sauce",
                "Cook rice. Pan-fry chicken, then add carrot and peas. Stir through rice and soy sauce.");
        insertRecipe(db, "Fried Rice", "2 cups rice; 2 eggs; 1 onion; 1 cup peas; 1 tbsp oil",
                "Stir-fry onion and peas. Add rice and eggs, cook until hot and fluffy.");
        insertRecipe(db, "Tomato Soup", "3 tomatoes; 1 onion; 2 cups stock; 1 tbsp oil",
                "Cook onion and tomatoes with stock until soft. Blend until smooth and heat through.");
        insertRecipe(db, "Pancakes", "1 cup flour; 1 egg; 1 cup milk; 1 tbsp butter; 1 tsp baking powder",
                "Mix the batter, fry spoonfuls in a pan, and flip once golden.");
        insertRecipe(db, "Veggie Curry", "1 onion; 2 tomatoes; 1 cup chickpeas; 1 cup coconut milk; 1 tsp curry powder",
                "Cook onion and tomatoes, add chickpeas, coconut milk, and curry powder. Simmer until thick.");
        insertRecipe(db, "Potato Salad", "3 potatoes; 1 onion; 1 tbsp vinegar; 1 tbsp olive oil",
                "Boil potatoes, chop onion, and toss together with vinegar and olive oil.");
        insertRecipe(db, "Quesadilla", "2 tortillas; 1 cup cheese; 1 tomato; 1 onion",
                "Fill tortillas with cheese, tomato, and onion. Grill until crisp and melted.");
        insertRecipe(db, "Pasta Primavera", "200 g pasta; 1 zucchini; 1 carrot; 1 tomato; 1 tbsp olive oil",
                "Boil pasta. Fry the vegetables in olive oil, then combine with the pasta.");
        insertRecipe(db, "Egg Fried Rice", "2 cups rice; 2 eggs; 1 onion; 1 carrot; 1 tbsp soy sauce",
                "Cook the onion and carrot, add rice and eggs, then season with soy sauce.");
        insertRecipe(db, "Lentil Soup", "1 cup lentils; 1 onion; 2 tomatoes; 2 cups stock",
                "Simmer lentils, onion, and tomatoes in stock until soft and hearty.");
    }

    private void insertRecipe(SQLiteDatabase db, String name, String ingredients, String method) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("ingredients", ingredients);
        values.put("method", method);
        db.insert(TABLE_RECIPES, null, values);
    }

    public long addIngredient(Ingredient ingredient) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", ingredient.getName());
        values.put("quantity", ingredient.getQuantity());
        values.put("unit", ingredient.getUnit());
        values.put("expiry_date", ingredient.getExpiryDate());
        return db.insert(TABLE_PANTRY, null, values);
    }

    public int updateIngredient(Ingredient ingredient) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", ingredient.getName());
        values.put("quantity", ingredient.getQuantity());
        values.put("unit", ingredient.getUnit());
        values.put("expiry_date", ingredient.getExpiryDate());
        return db.update(TABLE_PANTRY, values, "id = ?", new String[]{String.valueOf(ingredient.getId())});
    }

    public int deleteIngredient(long id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_PANTRY, "id = ?", new String[]{String.valueOf(id)});
    }

    public List<Ingredient> getAllIngredients() {
        List<Ingredient> ingredients = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_PANTRY, null, null, null, null, null, "name ASC");

        try {
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    Ingredient ingredient = new Ingredient();
                    ingredient.setId(cursor.getLong(cursor.getColumnIndexOrThrow("id")));
                    ingredient.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
                    ingredient.setQuantity(cursor.getDouble(cursor.getColumnIndexOrThrow("quantity")));
                    ingredient.setUnit(cursor.getString(cursor.getColumnIndexOrThrow("unit")));
                    ingredient.setExpiryDate(cursor.getString(cursor.getColumnIndexOrThrow("expiry_date")));
                    ingredients.add(ingredient);
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return ingredients;
    }

    public Ingredient getIngredientById(long id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_PANTRY, null, "id = ?", new String[]{String.valueOf(id)}, null, null, null);

        try {
            if (cursor != null && cursor.moveToFirst()) {
                Ingredient ingredient = new Ingredient();
                ingredient.setId(cursor.getLong(cursor.getColumnIndexOrThrow("id")));
                ingredient.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
                ingredient.setQuantity(cursor.getDouble(cursor.getColumnIndexOrThrow("quantity")));
                ingredient.setUnit(cursor.getString(cursor.getColumnIndexOrThrow("unit")));
                ingredient.setExpiryDate(cursor.getString(cursor.getColumnIndexOrThrow("expiry_date")));
                return ingredient;
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return null;
    }

    public List<Recipe> getAllRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_RECIPES, null, null, null, null, null, "name ASC");

        try {
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    Recipe recipe = new Recipe();
                    recipe.setId(cursor.getLong(cursor.getColumnIndexOrThrow("id")));
                    recipe.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
                    recipe.setIngredients(cursor.getString(cursor.getColumnIndexOrThrow("ingredients")));
                    recipe.setMethod(cursor.getString(cursor.getColumnIndexOrThrow("method")));
                    recipes.add(recipe);
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return recipes;
    }

    public Recipe getRecipeById(long id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_RECIPES, null, "id = ?", new String[]{String.valueOf(id)}, null, null, null);

        try {
            if (cursor != null && cursor.moveToFirst()) {
                Recipe recipe = new Recipe();
                recipe.setId(cursor.getLong(cursor.getColumnIndexOrThrow("id")));
                recipe.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
                recipe.setIngredients(cursor.getString(cursor.getColumnIndexOrThrow("ingredients")));
                recipe.setMethod(cursor.getString(cursor.getColumnIndexOrThrow("method")));
                return recipe;
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return null;
    }
}
