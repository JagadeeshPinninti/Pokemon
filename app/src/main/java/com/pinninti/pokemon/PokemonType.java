package com.pinninti.pokemon;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mancj.materialsearchbar.MaterialSearchBar;
import com.pinninti.pokemon.Adapter.PokemonListAdapter;
import com.pinninti.pokemon.Common.Common;
import com.pinninti.pokemon.Common.ItemOffsetDecoration;
import com.pinninti.pokemon.Model.Pokemon;
import com.pinninti.pokemon.Retrofit.IPokemonDex;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class PokemonType extends Fragment {
    RecyclerView pokemon_list_recyclerView;

    PokemonListAdapter adapter,search_adapter;
    List<String> last_suggest=new ArrayList<>();

    MaterialSearchBar searchBar;

    List<Pokemon> typeList;

    static  PokemonType instance;

    public static PokemonType getInstance() {
        if (instance==null)
            instance=new PokemonType();
        return instance;
    }

    public PokemonType() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_pokemon_type, container, false);

        pokemon_list_recyclerView = view.findViewById(R.id.pokemon_list_recyclerview);
        pokemon_list_recyclerView.setHasFixedSize(true);
        pokemon_list_recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        ItemOffsetDecoration itemOffsetDecoration = new ItemOffsetDecoration(getActivity(), R.dimen.spacing);
        pokemon_list_recyclerView.addItemDecoration(itemOffsetDecoration);

        //setup SearchBar
        searchBar=view.findViewById(R.id.search_bar);
        searchBar.setHint("Enter Pokemon Name ");
        searchBar.setCardViewElevation(10);
        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<String> suggest=new ArrayList<>();
                for (String search:last_suggest)
                {
                    if (search.toLowerCase().contains(searchBar.getText().toLowerCase()))
                        suggest.add(search);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if (!enabled)
                    pokemon_list_recyclerView.setAdapter(adapter); //Return Default Adapter
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

        if (getArguments()!=null){
            String type=getArguments().getString("type");
            if (type!=null){
                typeList=Common.findPokemonsByType(type);
                adapter=new PokemonListAdapter(getActivity(),typeList);
                pokemon_list_recyclerView.setAdapter(adapter);

                loadSuggest();
            }
        }

        return  view;
    }

    private void loadSuggest() {
        last_suggest.clear();
        if (typeList.size()>0){
            for (Pokemon pokemon:typeList)
                last_suggest.add(pokemon.getName());
            searchBar.setLastSuggestions(last_suggest);
        }
    }

    private void startSearch(CharSequence text) {
        if (typeList.size()>0){
            List<Pokemon> result=new ArrayList<>();
            for (Pokemon pokemon:typeList)
                if (pokemon.getName().toLowerCase().contains(text.toString().toLowerCase()))
                    result.add(pokemon);
            search_adapter=new PokemonListAdapter(getActivity(),result);
            pokemon_list_recyclerView.setAdapter(search_adapter);
        }
    }


}
