package com.example.carlos.magiccards;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.carlos.magiccards.databinding.FragmentMainBinding;

import java.util.ArrayList;


public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private CardsCursorAdapter adapter;

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

        FragmentMainBinding binding = DataBindingUtil.inflate(inflater , R.layout.fragment_main , container , false);
        View view = binding.getRoot();

        adapter = new CardsCursorAdapter(getContext() , Card.class);

        binding.lvCard.setAdapter(adapter);
        binding.lvCard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Card card = (Card) parent.getItemAtPosition(position);

                Intent intent = new Intent(getContext() , DetailActivity.class);
                intent.putExtra("card" , card);

                startActivity(intent);
            }
        });

        getLoaderManager().initLoader(0 , null , this);

        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return DataManager.getCursorLoader(getContext());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    private class RefreshDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            ArrayList<Card> result;

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String color = preferences.getString("color" , "None");
            String rarity = preferences.getString("rarity" , "None");

            //Depenent del color i raresa, fem una crida o un altra amb la API
            if (color.equalsIgnoreCase("None") && rarity.equalsIgnoreCase("None")) {
                result = CardsAPI.getAllCards();
            } else if (!color.equalsIgnoreCase("None") && !rarity.equalsIgnoreCase("None")) {
                result = CardsAPI.getCardsByRarityAndOrColor(color , rarity);
            } else if (!color.equalsIgnoreCase("None")) {
                result = CardsAPI.getCardsByRarityAndOrColor(color , "None");
            } else {
                result = CardsAPI.getCardsByRarityAndOrColor("None" , rarity);
            }

            DataManager.deleteCards(getContext());
            DataManager.saveCards(result , getContext());

            return null;
        }

    }
}

