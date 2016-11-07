package com.example.carlos.magiccards;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private CardAdapter adapter;
    private ArrayList<Card> cards;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_cards_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            refresh();
            return true;
        } else if (id == R.id.action_settings) {
            startActivity(new Intent(getContext() , SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void refresh() {
        RefreshDataTask task = new RefreshDataTask();
        task.execute();
    }

    @Override
    public void onStart() {
        super.onStart();
        refresh();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        ListView listView = (ListView) view.findViewById(R.id.lvCard);

        cards = new ArrayList<>();
        adapter = new CardAdapter(
                getContext(),
                R.layout.lv_cards_row,
                cards
        );

        listView.setAdapter(adapter);

        return view;
    }

    private class RefreshDataTask extends AsyncTask<Void, Void, ArrayList<Card>> {
        @Override
        protected ArrayList<Card> doInBackground(Void... voids) {
            CardsAPI api = new CardsAPI();
            ArrayList<Card> result;

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String color = preferences.getString("color" , "None");
            String rarity = preferences.getString("rarity" , "None");

            //Depenent del color i raresa, fem una crida o un altra amb la API
            if (color.equalsIgnoreCase("None") && rarity.equalsIgnoreCase("None")) {
                result = api.getAllCards();
            } else if (!color.equalsIgnoreCase("None") && !rarity.equalsIgnoreCase("None")) {
                result = api.getCardsByRarityAndOrColor(color , rarity);
            } else if (!color.equalsIgnoreCase("None")) {
                result = api.getCardsByRarityAndOrColor(color , "None");
            } else {
                result = api.getCardsByRarityAndOrColor("None" , rarity);
            }

            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<Card> cards) {
            adapter.clear();
            if (!cards.isEmpty()) {
                for (Card c : cards) {
                    adapter.add(c);
                }
            } else {
                Snackbar.make(getView() , "No hi ha cartes" , Snackbar.LENGTH_LONG).show();
            }
        }
    }
}

