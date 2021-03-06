package edmt.dev.androidgridlayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LearnVegetables extends AppCompatActivity {
    private boolean playing = false;
    private TextToSpeech t2s;
    private String text;
    private ImageView play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_vegetables);
        assert getSupportActionBar() != null;   //null check
        SharedPreferences gameSettings = getSharedPreferences("MyGamePreferences", MODE_PRIVATE);
        String language = gameSettings.getString("Language", "DEFAULT");
        play = findViewById(R.id.play);
        assert language != null;
        if (language.equals("Arabic")) {
            play.setVisibility(View.GONE);
            ImageView playPlaceholder = findViewById(R.id.placeholder);
            playPlaceholder.setVisibility(View.VISIBLE);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.vegetables);
        ViewPager2 vegetableViewHolder = findViewById(R.id.viewPager);
        List<Thing> vegetables = new ArrayList<>();
        vegetables.add(new Thing(getString(R.string.carrot),"la",R.drawable.carrot));
        vegetables.add(new Thing(getString(R.string.bell_pepper),"le",R.drawable.bell_pepper));
        vegetables.add(new Thing(getString(R.string.corn),"le",R.drawable.corn));
        vegetables.add(new Thing(getString(R.string.cucumber),"le",R.drawable.cucumber));
        vegetables.add(new Thing(getString(R.string.eggplant),"l'",R.drawable.eggplant));
        vegetables.add(new Thing(getString(R.string.garlic),"l'",R.drawable.garlic));
        vegetables.add(new Thing(getString(R.string.peas),"les",R.drawable.peas));
        vegetables.add(new Thing(getString(R.string.potato),"la",R.drawable.potato));
        vegetables.add(new Thing(getString(R.string.radish),"le",R.drawable.radish));
        vegetables.add(new Thing(getString(R.string.lettuce),"la",R.drawable.lettuce));
        vegetables.add(new Thing(getString(R.string.broccoli),"le",R.drawable.broccoli));
        vegetables.add(new Thing(getString(R.string.beet),"le",R.drawable.beet));
        vegetables.add(new Thing(getString(R.string.cabbage),"le",R.drawable.cabbage));
        vegetables.add(new Thing(getString(R.string.cauliflower),"le",R.drawable.cauliflower));
        vegetables.add(new Thing(getString(R.string.chili_pepper),"le",R.drawable.chili_pepper));
        vegetables.add(new Thing(getString(R.string.ginger),"le",R.drawable.ginger));
        vegetables.add(new Thing(getString(R.string.horseradish),"le",R.drawable.horseradish));
        vegetables.add(new Thing(getString(R.string.olives),"l'",R.drawable.olives));
        vegetables.add(new Thing(getString(R.string.spinach),"l'",R.drawable.spinach));
        vegetables.add(new Thing(getString(R.string.turnip),"le",R.drawable.turnip));
        vegetables.add(new Thing(getString(R.string.zucchini),"la",R.drawable.zucchini));

        //Resources recources = getResources();
        //View view = findViewById(R.id.learn_layout);
        vegetableViewHolder.setAdapter(new Adapter(vegetables));
        vegetableViewHolder.setClipChildren(false);
        vegetableViewHolder.setClipToPadding(false);
        vegetableViewHolder.setOffscreenPageLimit(3);
        vegetableViewHolder.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleY(0.95f + r * 0.05f);
        });

        vegetableViewHolder.setPageTransformer(compositePageTransformer);
        t2s=new TextToSpeech(getApplicationContext(), status -> {
            if(status != TextToSpeech.ERROR) {
                t2s.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                    @Override
                    public void onDone(String utteranceId) {
                        play.setImageResource(R.drawable.play_button);
                        playing = false;
                    }

                    @Override
                    public void onError(String utteranceId) {
                    }

                    @Override
                    public void onStart(String utteranceId) {
                    }
                });
                switch(language){
                    case "English":{
                        t2s.setLanguage(Locale.ENGLISH);
                        break;
                    }
                    case "French":{
                        t2s.setLanguage(Locale.FRENCH);
                        break;
                    }
                }
            }
        });
        vegetableViewHolder.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                text = vegetables.get(position).getNAme();
                play.setImageResource(R.drawable.play_button);
                playing = false;
                t2s.stop();
            }

        });

        TextView learnLabel = findViewById(R.id.learnLabel);
        learnLabel.setText(getText(R.string.apprend_les_noms_des_legumes));
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
            play.setImageResource(R.drawable.pause_button);
            t2s.speak(text, TextToSpeech.QUEUE_FLUSH, params,"Robot4Kids");
            playing = true;
        }
        else {
            t2s.stop();
            play.setImageResource(R.drawable.play_button);
            playing = false;
        }
    }
}