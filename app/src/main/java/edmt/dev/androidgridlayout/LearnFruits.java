package edmt.dev.androidgridlayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class LearnFruits extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_fruits);
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ViewPager2 fruitViewPager = findViewById(R.id.viewPager);
        List<Thing> fruits = new ArrayList<>();
        fruits.add(new Thing("La pomme","la",R.drawable.apple));
        fruits.add(new Thing("La banane","la",R.drawable.banana));
        fruits.add(new Thing("La baie","la",R.drawable.berries));
        fruits.add(new Thing("La cerise","la",R.drawable.cherry));
        fruits.add(new Thing("Le raisin","le",R.drawable.grapes));
        fruits.add(new Thing("Le grenade","la",R.drawable.grenade));
        fruits.add(new Thing("La noix de coco","la",R.drawable.coconut));
        fruits.add(new Thing("Le kiwi","le",R.drawable.kiwi));
        fruits.add(new Thing("Le citron","le",R.drawable.leomn));
        fruits.add(new Thing("Le melon","le",R.drawable.melon));
        fruits.add(new Thing("L'orange","l'",R.drawable.orange_2));
        fruits.add(new Thing("L'ananas","l'",R.drawable.pineapple));
        fruits.add(new Thing("La fraise","la",R.drawable.strawberry));
        fruits.add(new Thing("La pastèque","la",R.drawable.watermelon_2));

        fruitViewPager.setAdapter(new Adapter(fruits));
        fruitViewPager.setClipChildren(false);
        fruitViewPager.setClipToPadding(false);
        fruitViewPager.setOffscreenPageLimit(3);
        fruitViewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.95f + r * 0.05f);
            }
        });

        fruitViewPager.setPageTransformer(compositePageTransformer);
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