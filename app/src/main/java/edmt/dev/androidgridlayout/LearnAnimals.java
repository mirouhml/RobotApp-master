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

public class LearnAnimals extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_animals);
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ViewPager2 animalViewHolder = findViewById(R.id.viewPager);
        List<Thing> animals = new ArrayList<>();
        animals.add(new Thing("Le chat","le",R.drawable.cat2));
        animals.add(new Thing("Le chien","le",R.drawable.dog));
        animals.add(new Thing("Le poulet","le",R.drawable.chicken));
        animals.add(new Thing("Le canard","le",R.drawable.duck));
        animals.add(new Thing("Le poisson","le",R.drawable.fish));
        animals.add(new Thing("Le dauphin","le",R.drawable.dolphin));
        animals.add(new Thing("L'éléphant","l'",R.drawable.elephant));
        animals.add(new Thing("Le chameau","le",R.drawable.camel));
        animals.add(new Thing("Le cerf","le",R.drawable.deer));
        animals.add(new Thing("La girafe","la",R.drawable.giragge));
        animals.add(new Thing("Le lion","le",R.drawable.lion));
        animals.add(new Thing("Le mouton","le",R.drawable.sheep));
        animals.add(new Thing("Le lapin","le",R.drawable.rabbit));
        animals.add(new Thing("Le cheval","le",R.drawable.horse));
        animals.add(new Thing("Le singe","le",R.drawable.monkey));
        animals.add(new Thing("Le cygne","le",R.drawable.swan));
        animals.add(new Thing("La tortue","la",R.drawable.turtle));
        animals.add(new Thing("L'écureuil","l'",R.drawable.squirrel));
        animals.add(new Thing("Le koala","le",R.drawable.koala));
        animals.add(new Thing("Le panda","le",R.drawable.panda_bear));
        animals.add(new Thing("La grenouille","la",R.drawable.frog));
        animals.add(new Thing("Le porc","le",R.drawable.pig));
        animals.add(new Thing("Le serpent","le",R.drawable.snake));
        animals.add(new Thing("L'abeille","l'",R.drawable.bee));
        animals.add(new Thing("Le crabe","le",R.drawable.crab));
        animals.add(new Thing("Le flamant","le",R.drawable.flamingo));
        animals.add(new Thing("L'hérisson","l'",R.drawable.hedgedog));
        animals.add(new Thing("L'hippopotame","l'",R.drawable.hippopotamus));
        animals.add(new Thing("La méduse","la",R.drawable.jellyfish));
        animals.add(new Thing("Le kangourou","le",R.drawable.kangaroo));
        animals.add(new Thing("Le lama","le",R.drawable.llama));
        animals.add(new Thing("Le poulpe","le",R.drawable.octopus));
        animals.add(new Thing("L'hibou","l'",R.drawable.owl));
        animals.add(new Thing("Le perroquet","le",R.drawable.parrot));
        animals.add(new Thing("Le manchot","le",R.drawable.penguin));
        animals.add(new Thing("L'hippocampe","l'",R.drawable.seahorse));
        animals.add(new Thing("Le phoque","le",R.drawable.seal));
        animals.add(new Thing("Le requin","le",R.drawable.shark));
        animals.add(new Thing("Le calamar","le",R.drawable.squid));
        animals.add(new Thing("La baleine","la",R.drawable.whale));

        animalViewHolder.setAdapter(new Adapter(animals));
        animalViewHolder.setClipChildren(false);
        animalViewHolder.setClipToPadding(false);
        animalViewHolder.setOffscreenPageLimit(3);
        animalViewHolder.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.95f + r * 0.05f);
            }
        });

        animalViewHolder.setPageTransformer(compositePageTransformer);
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
