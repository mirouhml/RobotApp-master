package edmt.dev.androidgridlayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LearnFruits extends AppCompatActivity {
    boolean playing = false;
    TextToSpeech t2s;
    String text;
    ImageView play;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_fruits);
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.fruits);
        SharedPreferences gameSettings = getSharedPreferences("MyGamePreferences", MODE_PRIVATE);
        String language = gameSettings.getString("Language","DEFAULT");
        play = findViewById(R.id.play);
        assert language != null;
        if(language.equals("Arabic")){
            play.setVisibility(View.GONE);
            ImageView playPlaceholder = findViewById(R.id.placeholder);
            playPlaceholder.setVisibility(View.VISIBLE);
        }
        play = findViewById(R.id.play);
        ViewPager2 fruitViewPager = findViewById(R.id.viewPager);
        List<Thing> fruits = new ArrayList<>();
        fruits.add(new Thing(getString(R.string.apple),"la",R.drawable.apple));
        fruits.add(new Thing(getString(R.string.banana),"la",R.drawable.banana));
        fruits.add(new Thing(getString(R.string.berries),"la",R.drawable.berries));
        fruits.add(new Thing(getString(R.string.cherry),"la",R.drawable.cherry));
        fruits.add(new Thing(getString(R.string.grapes),"le",R.drawable.grapes));
        fruits.add(new Thing(getString(R.string.coconut),"la",R.drawable.coconut));
        fruits.add(new Thing(getString(R.string.kiwi),"le",R.drawable.kiwi));
        fruits.add(new Thing(getString(R.string.lemon),"le",R.drawable.lemon));
        fruits.add(new Thing(getString(R.string.melon),"le",R.drawable.melon));
        fruits.add(new Thing(getString(R.string.orange),"l'",R.drawable.orange));
        fruits.add(new Thing(getString(R.string.pineapple),"l'",R.drawable.pineapple));
        fruits.add(new Thing(getString(R.string.strawberry),"la",R.drawable.strawberry));
        fruits.add(new Thing(getString(R.string.watermelon),"la",R.drawable.watermelon));
        fruits.add(new Thing(getString(R.string.pumpkin),"la",R.drawable.pumpkin));
        fruits.add(new Thing(getString(R.string.tomato),"la",R.drawable.tomato));
        fruits.add(new Thing(getString(R.string.grenade),"la",R.drawable.pomegranate));
        fruits.add(new Thing(getString(R.string.apricot),"l'",R.drawable.apricot));
        fruits.add(new Thing(getString(R.string.avocado),"l'",R.drawable.avocado));
        fruits.add(new Thing(getString(R.string.blueberry),"la",R.drawable.blueberry));
        fruits.add(new Thing(getString(R.string.chestnut),"la",R.drawable.chestnut));
        fruits.add(new Thing(getString(R.string.fig),"la",R.drawable.fig));
        fruits.add(new Thing(getString(R.string.gooseberry),"la",R.drawable.gooseberry));
        fruits.add(new Thing(getString(R.string.grapefruit),"le",R.drawable.grapefruit));
        fruits.add(new Thing(getString(R.string.guava),"la",R.drawable.guava));
        fruits.add(new Thing(getString(R.string.kumquat),"le",R.drawable.kumquat));
        fruits.add(new Thing(getString(R.string.lime),"le",R.drawable.lime));
        fruits.add(new Thing(getString(R.string.mango),"la",R.drawable.mango));
        fruits.add(new Thing(getString(R.string.mangosteen),"le",R.drawable.mangosteen));
        fruits.add(new Thing(getString(R.string.papaya),"la",R.drawable.papaya));
        fruits.add(new Thing(getString(R.string.granadilla),"la",R.drawable.passion_fruit_1));
        fruits.add(new Thing(getString(R.string.peacj),"la",R.drawable.peach));
        fruits.add(new Thing(getString(R.string.pear),"la",R.drawable.pear));
        fruits.add(new Thing(getString(R.string.persimmon),"le",R.drawable.persimmon));
        fruits.add(new Thing(getString(R.string.plum),"la",R.drawable.plum));
        fruits.add(new Thing(getString(R.string.quince),"le",R.drawable.quince));
        fruits.add(new Thing(getString(R.string.rambutan),"le",R.drawable.rambutan));
        fruits.add(new Thing(getString(R.string.raspberry),"la",R.drawable.raspberry));
        fruits.add(new Thing(getString(R.string.date),"la",R.drawable.date));



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
        t2s=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
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
            }
        });
        fruitViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                text = fruits.get(position).getNAme();
                play.setImageResource(R.drawable.play_button);
                t2s.stop();
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