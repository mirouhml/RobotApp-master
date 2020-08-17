package edmt.dev.androidgridlayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.res.Resources;
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
        fruits.add(new Thing(getString(R.string.apple),"la",R.drawable.apple));
        fruits.add(new Thing(getString(R.string.banana),"la",R.drawable.banana));
        fruits.add(new Thing(getString(R.string.berries),"la",R.drawable.berries));
        fruits.add(new Thing(getString(R.string.cherry),"la",R.drawable.cherry));
        fruits.add(new Thing(getString(R.string.grapes),"le",R.drawable.grapes));
        fruits.add(new Thing(getString(R.string.grenade),"la",R.drawable.grenade));
        fruits.add(new Thing(getString(R.string.coconut),"la",R.drawable.coconut));
        fruits.add(new Thing(getString(R.string.kiwi),"le",R.drawable.kiwi));
        fruits.add(new Thing(getString(R.string.lemon),"le",R.drawable.leomn));
        fruits.add(new Thing(getString(R.string.melon),"le",R.drawable.melon));
        fruits.add(new Thing(getString(R.string.orange),"l'",R.drawable.orange_2));
        fruits.add(new Thing(getString(R.string.pineapple),"l'",R.drawable.pineapple));
        fruits.add(new Thing(getString(R.string.strawberry),"la",R.drawable.strawberry));
        fruits.add(new Thing(getString(R.string.watermelon),"la",R.drawable.watermelon_2));
        //Resources recources = getResources();
        //View view = findViewById(R.id.learn_layout);
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