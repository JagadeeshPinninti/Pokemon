package com.pinninti.pokemon;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pinninti.pokemon.Adapter.PokemonEvolutionAdapter;
import com.pinninti.pokemon.Adapter.PokemonTypeAdapter;
import com.pinninti.pokemon.Common.Common;
import com.pinninti.pokemon.Model.Pokemon;

public class PokemonDetail extends Fragment {
    ImageView pokemon_image;
    TextView pokemon_name,pokemon_height,pokemon_weight;
    RecyclerView recycler_type,recycler_weakness,recycler_next_evolution,recycler_pre_evolution;
    static PokemonDetail instance;

    public static PokemonDetail getInstance() {
        if (instance==null)
            instance=new PokemonDetail();
        return instance;
    }

    public PokemonDetail() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView= inflater.inflate(R.layout.fragment_pokemon_detail, container, false);

        Pokemon pokemon=Common.findPokemonByNum(getArguments().getString("num"));


        pokemon_image=itemView.findViewById(R.id.pokemon_image);
        pokemon_name=itemView.findViewById(R.id.name);
        pokemon_height=itemView.findViewById(R.id.height);
        pokemon_weight=itemView.findViewById(R.id.weight);

        recycler_type=itemView.findViewById(R.id.recycler_type);
        recycler_type.setHasFixedSize(true);
        recycler_type.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        recycler_weakness=itemView.findViewById(R.id.recycler_weakness);
        recycler_weakness.setHasFixedSize(true);
        recycler_weakness.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        recycler_pre_evolution=itemView.findViewById(R.id.recycler_pre_evolution);
        recycler_pre_evolution.setHasFixedSize(true);
        recycler_pre_evolution.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        recycler_next_evolution=itemView.findViewById(R.id.recycler_next_evolution);
        recycler_next_evolution.setHasFixedSize(true);
        recycler_next_evolution.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        setDetailPokemon(pokemon);

        return  itemView;
    }

    private void setDetailPokemon(Pokemon pokemon) {
        //Load image
        Glide.with(getActivity()).load(pokemon.getImg()).into(pokemon_image);

        pokemon_name.setText(pokemon.getName());

        pokemon_weight.setText("Weight: "+pokemon.getWeight());
        pokemon_height.setText("Height: "+pokemon.getHeight());

        //Set Type
        PokemonTypeAdapter typeAdapter=new PokemonTypeAdapter(getActivity(),pokemon.getType());
        recycler_type.setAdapter(typeAdapter);

        //Set weakness
        PokemonTypeAdapter weaknessAdapter=new PokemonTypeAdapter(getActivity(),pokemon.getWeaknesses());
        recycler_weakness.setAdapter(weaknessAdapter);

        //Set Pre Evolution
        PokemonEvolutionAdapter preEvoltionAdapter=new PokemonEvolutionAdapter(getActivity(),pokemon.getPrev_evolution());
        recycler_pre_evolution.setAdapter(preEvoltionAdapter);

        //Set Next Evolution
        PokemonEvolutionAdapter nextEvoltionAdapter=new PokemonEvolutionAdapter(getActivity(),pokemon.getNext_evolution());
        recycler_next_evolution.setAdapter(nextEvoltionAdapter);

    }

}
