package com.pinninti.pokemon.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pinninti.pokemon.Common.Common;
import com.pinninti.pokemon.Interface.IItemClickListener;
import com.pinninti.pokemon.R;
import com.robertlevonyan.views.chip.Chip;
import com.robertlevonyan.views.chip.OnChipClickListener;

import java.util.List;

public class PokemonTypeAdapter extends RecyclerView.Adapter<PokemonTypeAdapter.MyViewHolder> {
    Context context;
    List<String> typeList;

    public PokemonTypeAdapter(Context context, List<String> typeList) {
        this.context = context;
        this.typeList = typeList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=LayoutInflater.from(context).inflate(R.layout.chip_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.chip.setChipText(typeList.get(position));
        holder.chip.changeBackgroundColor(Common.getColorByType(typeList.get(position)));
    }

    @Override
    public int getItemCount() {
        return typeList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        Chip chip;

        public MyViewHolder(View itemView) {
            super(itemView);

            chip=itemView.findViewById(R.id.chip);

            chip.setOnChipClickListener(new OnChipClickListener() {
                @Override
                public void onChipClick(View v) {
                    LocalBroadcastManager.getInstance(context)
                            .sendBroadcast(new Intent(Common.KEY_POKEMON_TYPE).putExtra("type",typeList.get(getAdapterPosition())));
                }
            });
        }
    }
}
