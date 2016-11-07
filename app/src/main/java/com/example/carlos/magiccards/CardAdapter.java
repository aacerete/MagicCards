package com.example.carlos.magiccards;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import com.bumptech.glide.Glide;

/**
 * Created by Carlos on 01/11/2016.
 */

public class CardAdapter extends ArrayAdapter<Card> {
    public CardAdapter(Context context , int resource , List<Card> objects) {
        super(context , resource , objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Card card = getItem(position);

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.lv_cards_row , parent , false);
        }

        TextView nameCard = (TextView) convertView.findViewById(R.id.nameCard);
        TextView typeCard = (TextView) convertView.findViewById(R.id.typeCard);
        ImageView cardImage = (ImageView) convertView.findViewById(R.id.cardImage);

        nameCard.setText(card.getName());
        typeCard.setText(card.getType());
        Glide.with(getContext()).load(card.getImageUrl()).into(cardImage);

        return convertView;
    }
}
