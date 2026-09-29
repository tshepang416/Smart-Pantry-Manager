package com.smartpantry.manager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.PantryViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(Ingredient ingredient);
    }

    private final List<Ingredient> ingredients;
    private final OnItemClickListener listener;

    public PantryAdapter(List<Ingredient> ingredients, OnItemClickListener listener) {
        this.ingredients = ingredients;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PantryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ingredient, parent, false);
        return new PantryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PantryViewHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);
        holder.nameText.setText(ingredient.getName());
        String meta = ingredient.getQuantity() + " " + ingredient.getUnit();
        holder.metaText.setText(meta.trim());

        if (ingredient.getExpiryDate() == null || ingredient.getExpiryDate().trim().isEmpty()) {
            holder.expiryText.setText("No expiry date");
        } else {
            holder.expiryText.setText("Expires: " + ingredient.getExpiryDate());
        }

        holder.itemView.setOnClickListener(v -> listener.onItemClick(ingredient));
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    static class PantryViewHolder extends RecyclerView.ViewHolder {
        TextView nameText;
        TextView metaText;
        TextView expiryText;

        PantryViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.textIngredientName);
            metaText = itemView.findViewById(R.id.textIngredientMeta);
            expiryText = itemView.findViewById(R.id.textExpiry);
        }
    }
}
