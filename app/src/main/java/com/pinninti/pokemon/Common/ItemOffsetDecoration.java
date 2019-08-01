package com.pinninti.pokemon.Common;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ItemOffsetDecoration extends RecyclerView.ItemDecoration {
    private int itemOffSet;

    public ItemOffsetDecoration(int itemOffSet) {
        this.itemOffSet = itemOffSet;
    }

    public ItemOffsetDecoration(@NonNull Context context, @DimenRes int dimens){
        this(context.getResources().getDimensionPixelSize(dimens));
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(itemOffSet,itemOffSet,itemOffSet,itemOffSet);
    }
}
