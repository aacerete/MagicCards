package com.example.carlos.magiccards;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Carlos on 18/10/2016.
 */

public class CardsAPI {
    private final String BASE_URL = "http://api.magicthegathering.io/v1/cards";

    ArrayList<Card> getAllCards() {
        Uri builtUri = Uri.parse(BASE_URL)
                .buildUpon()
                .build();
        String url = builtUri.toString();
        return doCall(url);
    }

    private ArrayList<Card> doCall(String url) {

        try {
            String JsonResponse = HttpUtils.get(url);
            return processJson(JsonResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<Card> processJson(String jsonResponse) {

        ArrayList<Card> cards = new ArrayList<>();

        try {
            JSONObject data = new JSONObject(jsonResponse);
            JSONArray jsonCards = data.getJSONArray("cards");

            for (int i = 0 ; i < jsonCards.length() ; i++) {
                Card card = new Card();
                JSONObject object = jsonCards.getJSONObject(i);

                if (object.has("name")) {
                    card.setName(object.getString("name"));
                } else {
                    card.setName("");
                }
                if (object.has("rarity")) {
                    card.setRarity(object.getString("rarity"));
                } else {
                    card.setRarity("");
                }
                if (object.has("text")) {
                    card.setText(object.getString("text"));
                } else {
                    card.setText("");
                }
                if (object.has("type")) {
                    card.setType(object.getString("type"));
                } else {
                    card.setType("");
                }
                if (object.has("imageUrl")) {
                    card.setImageUrl(object.getString("imageUrl"));
                } else {
                    card.setImageUrl("");
                }

                cards.add(card);
            }

        } catch (JSONException e) {
            e.getMessage();
        }

        return cards;
    }

}
