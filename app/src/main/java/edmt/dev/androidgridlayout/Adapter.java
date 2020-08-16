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
    public Adapter(List<Thing> things){
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
        holder.setData(things.get(position));
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

        void setData(Thing thing){
            kenBurnsView.setImageResource(thing.getImageResourceId());
            textTitle.setText(thing.getNAme());
        }
    }
}
