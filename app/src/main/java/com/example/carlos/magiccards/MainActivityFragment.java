package com.example.carlos.magiccards;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
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

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private ArrayList<String> items;
    private ArrayAdapter<String> adapter;

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
        }
        return super.onOptionsItemSelected(item);
    }

    public void refresh() {
        RefreshDataTask task = new RefreshDataTask();
        task.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        ListView listView = (ListView) view.findViewById(R.id.lvCard);

        String[] data = {
                "Buba 500",
                "El hola",
                "El adios",
                "Hola que tal, soy colosal!",
                "Infiltradisimos en 56"
        };

        items = new ArrayList<>(Arrays.asList(data));
        adapter = new ArrayAdapter<>(
                getContext(),
                R.layout.lv_cards_row,
                R.id.tvCard,
                items
        );

        listView.setAdapter(adapter);

        return view;
    }

    private class RefreshDataTask extends AsyncTask<Void, Void, ArrayList<Card>> {
        @Override
        protected ArrayList<Card> doInBackground(Void... voids) {
            CardsAPI api = new CardsAPI();
            ArrayList<Card> result = api.getAllCards();

            Log.d("DEBUG", result.toString());

            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<Card> cards) {
            adapter.clear();
            for (Card c : cards) {
                adapter.add(c.getName());
            }
        }
    }
}

