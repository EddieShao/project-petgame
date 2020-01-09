package com.example.petgame.MemoryTile;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.petgame.R;

public class MemoGridAdapter extends BaseAdapter {

    private Context context;
    private final boolean[][] moneyMap;
    private TileListener listener;
    private final int mapColumns;

    private static final String MONEYIMAGE = "$$$";
    private static final String EMPTYIMAGE = "";
    private static final String COVERIMAGE = "--";
    private final int MEMORYDELAY = 3000;

    interface TileListener {
        void updateAfterClick(int position);
    }

    MemoGridAdapter(Context context, boolean[][] moneyMap, TileListener listener) {
        this.context = context;
        this.moneyMap = moneyMap;
        this.listener = listener;
        this.mapColumns = moneyMap[0].length;
    }

    @Override
    public int getCount() {
        return moneyMap.length * moneyMap[0].length;
    }

    @Override
    public Drawable getItem(int position) {
        if (moneyMap[position / mapColumns][position % mapColumns]) {
            return context.getDrawable(R.drawable.tile_coin);
        } else {
            return context.getDrawable(R.drawable.tile_poopoo);
        }
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private void setImage(ImageView tile, boolean to_cover, int position) {
        if (to_cover) {
            tile.setImageDrawable(context.getDrawable(R.drawable.tile_treasure_box));
            tile.setTag(true);
        } else {
            tile.setImageDrawable(getItem(position));
            tile.setTag(false);
        }
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ImageView tile;
        if (view == null) {

            tile = new ImageView(context);
            tile.setMaxHeight(100);
            tile.setAdjustViewBounds(true);

            setImage(tile, false, position);

            // cover the tiles after a delay.
            new Handler().postDelayed(
                    () -> {
                        setImage(tile, true, position);
                        // the onClick method is called when tile is clicked.
                        tile.setOnClickListener(new ButtonOnClickListener(position));
                    },
                    MEMORYDELAY);

        } else {
            tile = (ImageView) view;
        }
        return tile;
    }

    private class ButtonOnClickListener implements View.OnClickListener {
        private final int position;

        ButtonOnClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View view) {
            ImageView clicked = (ImageView) view;

            if (((boolean) clicked.getTag())) {
                setImage(clicked, false, position);
                listener.updateAfterClick(position);
            } else {
                Toast.makeText(context, "The tile is clicked...", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
