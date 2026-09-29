package com.smartpantry.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RecipeMatcher {
    private static final List<String> UNIT_WORDS = Arrays.asList(
            "g", "kg", "mg", "ml", "l", "cup", "cups", "tbsp", "tsp",
            "tablespoon", "tablespoons", "teaspoon", "teaspoons",
            "clove", "cloves", "slice", "slices", "can", "cans",
            "packet", "packets", "pinch", "pinches", "gram", "grams",
            "liter", "liters", "litre", "litres", "oz", "ounce", "ounces"
    );

    public static List<Recipe> getSuggestedRecipes(List<Ingredient> pantry, List<Recipe> recipes) {
        List<Recipe> suggested = new ArrayList<>();

        for (Recipe recipe : recipes) {
            boolean canMake = true;
            String[] ingredientList = recipe.getIngredients().split(";");

            for (String ingredientEntry : ingredientList) {
                String requiredName = extractIngredientName(ingredientEntry);
                double requiredQuantity = extractQuantity(ingredientEntry);

                if (requiredName == null || requiredName.isEmpty()) {
                    continue;
                }

                boolean matchesPantry = false;
                for (Ingredient pantryItem : pantry) {
                    if (ingredientMatchesPantry(pantryItem, requiredName, requiredQuantity)) {
                        matchesPantry = true;
                        break;
                    }
                }

                if (!matchesPantry) {
                    canMake = false;
                    break;
                }
            }

            if (canMake) {
                suggested.add(recipe);
            }
        }

        return suggested;
    }

    private static boolean ingredientMatchesPantry(Ingredient pantryItem, String requiredName, double requiredQuantity) {
        if (pantryItem == null) {
            return false;
        }

        String pantryName = normalizeIngredientName(pantryItem.getName());
        if (!pantryName.equals(requiredName)) {
            return false;
        }

        double pantryQuantity = pantryItem.getQuantity();
        if (requiredQuantity <= 0) {
            return pantryQuantity > 0;
        }

        return pantryQuantity >= requiredQuantity;
    }

    private static String extractIngredientName(String text) {
        if (text == null) {
            return "";
        }

        String[] tokens = text.toLowerCase(Locale.US).replaceAll("[,/]+", " ").trim().split("\\s+");
        List<String> words = new ArrayList<>();

        for (String token : tokens) {
            String normalizedToken = token.trim();
            if (normalizedToken.isEmpty()) {
                continue;
            }

            if (normalizedToken.matches("^[0-9]+(\\.[0-9]+)?$")) {
                continue;
            }

            if (UNIT_WORDS.contains(normalizedToken)) {
                continue;
            }

            words.add(singularize(normalizedToken.replaceAll("[^a-z]", "")));
        }

        String name = String.join(" ", words).trim();
        return normalizeIngredientName(name);
    }

    private static double extractQuantity(String text) {
        if (text == null) {
            return 0;
        }

        Matcher matcher = Pattern.compile("([0-9]+(?:\\.[0-9]+)?)").matcher(text);
        if (matcher.find()) {
            return Double.parseDouble(matcher.group(1));
        }

        return 0;
    }

    public static String normalizeIngredientName(String text) {
        if (text == null) {
            return "";
        }

        String value = text.toLowerCase(Locale.US).trim();
        value = value.replaceAll("[^a-z0-9\\s]", " ");
        value = value.replaceAll("\\s+", " ").trim();

        if (value.isEmpty()) {
            return "";
        }

        String[] words = value.split(" ");
        List<String> cleaned = new ArrayList<>();
        for (String word : words) {
            String normalizedWord = singularize(word.trim());
            if (!normalizedWord.isEmpty()) {
                cleaned.add(normalizedWord);
            }
        }

        return String.join(" ", cleaned);
    }

    private static String singularize(String word) {
        if (word == null || word.isEmpty()) {
            return "";
        }

        if (word.endsWith("ies") && word.length() > 3) {
            return word.substring(0, word.length() - 3) + "y";
        }

        if (word.endsWith("sses") && word.length() > 4) {
            return word.substring(0, word.length() - 2);
        }

        if (word.endsWith("es") && word.length() > 3 && !word.endsWith("ses")
                && !word.endsWith("xes") && !word.endsWith("zes") && !word.endsWith("ches") && !word.endsWith("shes")) {
            return word.substring(0, word.length() - 2);
        }

        if (word.endsWith("s") && !word.endsWith("ss") && word.length() > 3) {
            return word.substring(0, word.length() - 1);
        }

        return word;
    }
}
