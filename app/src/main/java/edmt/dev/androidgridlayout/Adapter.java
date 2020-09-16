package edmt.dev.androidgridlayout;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ThingViewHolder> {
    private List<Thing> things;
    public Adapter(List<Thing> things/*, Resources resources, View view*/){
        this.things = things;
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
        private TextView kenBurnsText;
        private ImageView kenBurnsView;
        private TextView textTitle;
        private View view;

        ThingViewHolder(@NonNull View itemView) {
            super(itemView);
            kenBurnsText = itemView.findViewById(R.id.kenBurnsText);
            kenBurnsView = itemView.findViewById(R.id.kenBurnsView);
            textTitle = itemView.findViewById(R.id.textTitle);
            view = itemView.findViewById(R.id.view);
        }

        public void setData(Thing thing/*, Resources resources, View view*/) {
            if (thing.isHasImage()) {
                view.setVisibility(View.VISIBLE);
                textTitle.setVisibility(View.VISIBLE);
                kenBurnsView.setVisibility(View.VISIBLE);
                kenBurnsText.setVisibility(View.GONE);
                kenBurnsView.setImageResource(thing.getImageResourceId());
                textTitle.setText(thing.getNAme());
            } else {
                kenBurnsView.setVisibility(View.GONE);
                kenBurnsText.setVisibility(View.VISIBLE);
                kenBurnsText.setText(thing.getNAme());
                textTitle.setText(thing.getGender());
                if (thing.isHasText()) {
                    view.setVisibility(View.VISIBLE);
                    textTitle.setVisibility(View.VISIBLE);
                } else {
                    view.setVisibility(View.INVISIBLE);
                    textTitle.setVisibility(View.INVISIBLE);
                }
            }
        }
    }
}
