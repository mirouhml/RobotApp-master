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
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LearnAnimals extends AppCompatActivity {
    boolean playing = false;
    TextToSpeech t2s;
    String text;
    ImageView play;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_animals);
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        play = findViewById(R.id.play);
        ViewPager2 animalViewHolder = findViewById(R.id.viewPager);
        List<Thing> animals = new ArrayList<>();
        animals.add(new Thing(getString(R.string.cat),"le",R.drawable.cat2));
        animals.add(new Thing(getString(R.string.dog),"le",R.drawable.dog));
        animals.add(new Thing(getString(R.string.chicken),"le",R.drawable.chicken));
        animals.add(new Thing(getString(R.string.duck),"le",R.drawable.duck));
        animals.add(new Thing(getString(R.string.fish),"le",R.drawable.fish));
        animals.add(new Thing(getString(R.string.dolphin),"le",R.drawable.dolphin));
        animals.add(new Thing(getString(R.string.elephant),"l'",R.drawable.elephant));
        animals.add(new Thing(getString(R.string.camel),"le",R.drawable.camel));
        animals.add(new Thing(getString(R.string.deer),"le",R.drawable.deer));
        animals.add(new Thing(getString(R.string.giragge),"la",R.drawable.giragge));
        animals.add(new Thing(getString(R.string.lion),"le",R.drawable.lion));
        animals.add(new Thing(getString(R.string.sheep),"le",R.drawable.sheep));
        animals.add(new Thing(getString(R.string.rabbit),"le",R.drawable.rabbit));
        animals.add(new Thing(getString(R.string.horse),"le",R.drawable.horse));
        animals.add(new Thing(getString(R.string.monkey),"le",R.drawable.monkey));
        animals.add(new Thing(getString(R.string.swan),"le",R.drawable.swan));
        animals.add(new Thing(getString(R.string.turtle),"la",R.drawable.turtle));
        animals.add(new Thing(getString(R.string.squirrel),"l'",R.drawable.squirrel));
        animals.add(new Thing(getString(R.string.koala),"le",R.drawable.koala));
        animals.add(new Thing(getString(R.string.panda_bear),"le",R.drawable.panda_bear));
        animals.add(new Thing(getString(R.string.frog),"la",R.drawable.frog));
        animals.add(new Thing(getString(R.string.pig),"le",R.drawable.pig));
        animals.add(new Thing(getString(R.string.snake),"le",R.drawable.snake));
        animals.add(new Thing(getString(R.string.bee),"l'",R.drawable.bee));
        animals.add(new Thing(getString(R.string.crab),"le",R.drawable.crab));
        animals.add(new Thing(getString(R.string.flamingo),"le",R.drawable.flamingo));
        animals.add(new Thing(getString(R.string.hedgehog),"l'",R.drawable.hedgedog));
        animals.add(new Thing(getString(R.string.hippopotamus),"l'",R.drawable.hippopotamus));
        animals.add(new Thing(getString(R.string.jellyfish),"la",R.drawable.jellyfish));
        animals.add(new Thing(getString(R.string.kangaroo),"le",R.drawable.kangaroo));
        animals.add(new Thing(getString(R.string.llama),"le",R.drawable.llama));
        animals.add(new Thing(getString(R.string.octopus),"le",R.drawable.octopus));
        animals.add(new Thing(getString(R.string.owl),"l'",R.drawable.owl));
        animals.add(new Thing(getString(R.string.parrot),"le",R.drawable.parrot));
        animals.add(new Thing(getString(R.string.penguin),"le",R.drawable.penguin));
        animals.add(new Thing(getString(R.string.seahorse),"l'",R.drawable.seahorse));
        animals.add(new Thing(getString(R.string.seal),"le",R.drawable.seal));
        animals.add(new Thing(getString(R.string.shark),"le",R.drawable.shark));
        animals.add(new Thing(getString(R.string.squid),"le",R.drawable.squid));
        animals.add(new Thing(getString(R.string.whale),"la",R.drawable.whale));
        //Resources recources = getResources();
        //View view = findViewById(R.id.learn_layout);
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
        t2s=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t2s.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                        @Override
                        public void onDone(String utteranceId) {
                            play.setImageResource(R.drawable.baseline_play_circle_filled_white_48);
                            playing = false;
                        }

                        @Override
                        public void onError(String utteranceId) {
                        }

                        @Override
                        public void onStart(String utteranceId) {
                        }
                    });
                    if(Locale.getDefault().getLanguage().equals(new Locale("en").getLanguage()))
                        t2s.setLanguage(Locale.ENGLISH);
                    else
                        t2s.setLanguage(Locale.FRENCH);
                }
            }
        });

        animalViewHolder.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                text = animals.get(position).getNAme();
                t2s.stop();
                play.setImageResource(R.drawable.baseline_play_circle_filled_white_48);
                playing = false;
            }

        });
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

    public void play(View view) {
        Bundle params = new Bundle();
        params.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "");
        if (!playing){
            play.setImageResource(R.drawable.baseline_pause_circle_filled_white_48);
            t2s.speak(text, TextToSpeech.QUEUE_FLUSH, params,"Robot4Kids");
            playing = true;
        }
        else {
            t2s.stop();
            play.setImageResource(R.drawable.baseline_play_circle_filled_white_48);
            playing = false;
        }
    }
}
