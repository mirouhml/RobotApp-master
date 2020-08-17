package edmt.dev.androidgridlayout;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Adapter extends RecyclerView.Adapter<Adapter.ThingViewHolder> {
    private List<Thing> things;
    //Resources resources;
   // View view;
    public Adapter(List<Thing> things/*, Resources resources, View view*/){
        this.things = things;
        //this.resources = resources;
        //this.view = view;
    }
    @NonNull
    @Override
    public ThingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ThingViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_container,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ThingViewHolder holder, int position) {
        holder.setData(things.get(position)/*,resources,view*/);
    }

    @Override
    public int getItemCount() {
        return things.size();
    }

    static class ThingViewHolder extends RecyclerView.ViewHolder{

        private ImageView kenBurnsView;
        private TextView textTitle;

        ThingViewHolder(@NonNull View itemView) {
            super(itemView);
            kenBurnsView = itemView.findViewById(R.id.kenBurnsView);
            textTitle = itemView.findViewById(R.id.textTitle);
        }

        void setData(Thing thing/*, Resources resources, View view*/){
            kenBurnsView.setImageResource(thing.getImageResourceId());
            textTitle.setText(thing.getNAme());
            /*Bitmap bitmap = BitmapFactory.decodeResource(resources,
                    thing.getImageResourceId());
            Palette.from(bitmap).generate(p -> {
                // Use generated instance
                assert p != null;
                int color = getDominantSwatch(p).getRgb();
                view.setBackgroundColor(color);
            });*/
        }
        /*private Palette.Swatch getDominantSwatch(Palette palette) {
            // find most-represented swatch based on population
            return Collections.max(palette.getSwatches(), new Comparator<Palette.Swatch>() {
                @Override
                public int compare(Palette.Swatch sw1, Palette.Swatch sw2) {
                    return Integer.compare(sw1.getPopulation(), sw2.getPopulation());
                }
            });
        }*/
    }
}
