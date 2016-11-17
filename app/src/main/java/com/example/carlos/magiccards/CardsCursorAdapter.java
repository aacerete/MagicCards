package com.example.carlos.magiccards;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.carlos.magiccards.databinding.LvCardsRowBinding;

public class CardsCursorAdapter extends  CupboardCursorAdapter<Card> {

    public CardsCursorAdapter(Context context , Class<Card> entityClass) {
        super(context , entityClass);
    }

    @Override
    public View newView(Context context, Card model, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        LvCardsRowBinding binding = DataBindingUtil.inflate(
                inflater , R.layout.lv_cards_row , parent , false);

        return binding.getRoot();
    }

    @Override
    public void bindView(View view, Context context, Card model) {
        LvCardsRowBinding binding = DataBindingUtil.getBinding(view);
        binding.nameCard.setText(model.getName());
        binding.typeCard.setText(model.getType());
        Glide.with(context).load(model.getImageUrl()).into(binding.cardImage);
    }
}
