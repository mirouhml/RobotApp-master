package edmt.dev.androidgridlayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class LearnVegetables extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_vegetables);
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ViewPager2 vegetableViewHolder = findViewById(R.id.viewPager);
        List<Thing> vegetables = new ArrayList<>();
        vegetables.add(new Thing("La carrot","la",R.drawable.carrot));
        vegetables.add(new Thing("Le poivron","le",R.drawable.bell_pepper));
        vegetables.add(new Thing("Le maïs","le",R.drawable.corn));
        vegetables.add(new Thing("Le concombre","le",R.drawable.cucumber));
        vegetables.add(new Thing("L'aubergine","l'",R.drawable.eggplant));
        vegetables.add(new Thing("L'ail","l'",R.drawable.garlic));
        vegetables.add(new Thing("L'oignon","l'",R.drawable.onion_2));
        vegetables.add(new Thing("Les pois","les",R.drawable.peas));
        vegetables.add(new Thing("La pomme de terre","la",R.drawable.potato));
        vegetables.add(new Thing("La citrouille","la",R.drawable.pumpkin));
        vegetables.add(new Thing("La tomate","la",R.drawable.tomato));
        vegetables.add(new Thing("Le radis","le",R.drawable.radish));
        vegetables.add(new Thing("La laitue","la",R.drawable.lettuce));
        vegetables.add(new Thing("le brocoli","le",R.drawable.broccoli));

        vegetableViewHolder.setAdapter(new Adapter(vegetables));
        vegetableViewHolder.setClipChildren(false);
        vegetableViewHolder.setClipToPadding(false);
        vegetableViewHolder.setOffscreenPageLimit(3);
        vegetableViewHolder.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.95f + r * 0.05f);
            }
        });

        vegetableViewHolder.setPageTransformer(compositePageTransformer);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {// app icon in action bar clicked; go home
            Intent intent = new Intent(this, Learn.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}