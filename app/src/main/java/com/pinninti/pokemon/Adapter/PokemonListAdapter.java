package com.pinninti.pokemon.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pinninti.pokemon.Common.Common;
import com.pinninti.pokemon.Interface.IItemClickListener;
import com.pinninti.pokemon.Model.Pokemon;
import com.pinninti.pokemon.R;

import java.util.List;

public class PokemonListAdapter extends RecyclerView.Adapter<PokemonListAdapter.MyViewHolder> {
    Context context;
    List<Pokemon> pokemonList;

    public PokemonListAdapter(Context context, List<Pokemon> pokemonList) {
        this.context = context;
        this.pokemonList = pokemonList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView=LayoutInflater.from(context).inflate(R.layout.pokemon_list_item,viewGroup,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        //Load image
        Glide.with(context).load(pokemonList.get(i).getImg()).into(myViewHolder.pokemon_image);

        //set Name
        myViewHolder.pokemon_name.setText(pokemonList.get(i).getName());

        //Event
        myViewHolder.setiItemClickListener(new IItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(context, "Clicked on Pokemon : " +pokemonList.get(position).getName(), Toast.LENGTH_SHORT).show();

                LocalBroadcastManager.getInstance(context)
                        .sendBroadcast(new Intent(Common.KEY_ENABLE_HOME).putExtra("num",pokemonList.get(position).getNum()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView pokemon_image;
        TextView pokemon_name;

        IItemClickListener iItemClickListener;

        public void setiItemClickListener(IItemClickListener iItemClickListener) {
            this.iItemClickListener = iItemClickListener;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            pokemon_image=itemView.findViewById(R.id.pokemon_image);
            pokemon_name=itemView.findViewById(R.id.txt_pokemon_name);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            iItemClickListener.onClick(v,getAdapterPosition());
        }
    }
}
