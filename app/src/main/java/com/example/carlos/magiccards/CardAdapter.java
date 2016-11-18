package com.example.carlos.magiccards;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;
import com.bumptech.glide.Glide;
import com.example.carlos.magiccards.databinding.LvCardsRowBinding;


public class CardAdapter extends ArrayAdapter<Card> {
    public CardAdapter(Context context , int resource , List<Card> objects) {
        super(context , resource , objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Card card = getItem(position);

        LvCardsRowBinding binding;

        // Mirem a veure si la View s'està reusant, si no es així "inflem" la View
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            binding = DataBindingUtil.inflate(inflater , R.layout.lv_cards_row , parent , false);
        } else {
            binding = DataBindingUtil.getBinding(convertView);
        }

        binding.nameCard.setText(card.getName());
        Glide.with(getContext()).load(card.getImageUrl()).into(binding.cardImage);
        binding.typeCard.setText(card.getType());

        return binding.getRoot();
    }
}
