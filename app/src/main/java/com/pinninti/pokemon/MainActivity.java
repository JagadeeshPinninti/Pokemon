package com.pinninti.pokemon;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.pinninti.pokemon.Common.Common;
import com.pinninti.pokemon.Model.Pokemon;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;

    BroadcastReceiver showPokemonType = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().toString().equals(Common.KEY_POKEMON_TYPE)) {

                //Replace Fragement
                Fragment pokemonType = PokemonType.getInstance();
                String type=intent.getStringExtra("type");
                Bundle bundle = new Bundle();
                bundle.putString("type",type);
                pokemonType.setArguments(bundle);

                getSupportFragmentManager().popBackStack(0,FragmentManager.POP_BACK_STACK_INCLUSIVE);

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.list_pokemon_fragement, pokemonType);
                fragmentTransaction.addToBackStack("type");
                fragmentTransaction.commit();

                toolbar.setTitle("POKEMON TYPE " +type.toUpperCase());
            }
        }
    };

    BroadcastReceiver showDetail = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().toString().equals(Common.KEY_ENABLE_HOME)) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //Enable back button on toolbar
                getSupportActionBar().setDisplayShowHomeEnabled(true);

                //Replace Fragement
                Fragment detailFragement = PokemonDetail.getInstance();
               String num=intent.getStringExtra("num");
                Bundle bundle = new Bundle();
                bundle.putString("num",num);
                detailFragement.setArguments(bundle);

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.list_pokemon_fragement, detailFragement);
                fragmentTransaction.addToBackStack("detail");
                fragmentTransaction.commit();

                //set Pokemon for toolbar

                Pokemon pokemon = Common.findPokemonByNum(num);
                toolbar.setTitle(pokemon.getName());
            }
        }
    };

    BroadcastReceiver showEvolution = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().toString().equals(Common.KEY_NUM_EVOLUTION)) {


                //Replace Fragement
                Fragment detailFragement = PokemonDetail.getInstance();

                Bundle bundle = new Bundle();
              String num=intent.getStringExtra("num");
              bundle.putString("num",num);
                detailFragement.setArguments(bundle);

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.remove(detailFragement);  //Remove Current Fragement
                fragmentTransaction.replace(R.id.list_pokemon_fragement, detailFragement);
                fragmentTransaction.addToBackStack("detail");
                fragmentTransaction.commit();

                //set Pokemon for toolbar

                Pokemon pokemon = Common.findPokemonByNum(num);
                toolbar.setTitle(pokemon.getName());
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Pokemon List");
        setSupportActionBar(toolbar);

        //Register Broadcast
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(showDetail, new IntentFilter(Common.KEY_ENABLE_HOME));

        LocalBroadcastManager.getInstance(this)
                .registerReceiver(showEvolution, new IntentFilter(Common.KEY_NUM_EVOLUTION));

        LocalBroadcastManager.getInstance(this)
                .registerReceiver(showPokemonType, new IntentFilter(Common.KEY_POKEMON_TYPE));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                toolbar.setTitle("Pokemon List");

                //clear all fragement detail and pop to list Fragement
                getSupportFragmentManager().popBackStack("detail", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getSupportFragmentManager().popBackStack("type", FragmentManager.POP_BACK_STACK_INCLUSIVE);

                getSupportActionBar().setDisplayShowHomeEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);

                Fragment pokemonList=PokemonList.getInstance();

                FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                fragmentTransaction.remove(pokemonList);
                fragmentTransaction.replace(R.id.list_pokemon_fragement,pokemonList);
                fragmentTransaction.commit();

                getSupportActionBar().setDisplayShowHomeEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);

                break;
        }
        return true;
    }
}


//Pokemon List App

//https://www.youtube.com/watch?v=r2ALBYx1JuY&t=1574s
//https://raw.githubusercontent.com/Biuni/PokemonGO-Pokedex/master/pokedex.json