package edmt.dev.androidgridlayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
        setTitle(R.string.animals);
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
        ViewPager2 animalViewHolder = findViewById(R.id.viewPager);
        List<Thing> animals = new ArrayList<>();
        animals.add(new Thing(getString(R.string.cat),"le",R.drawable.cat2));
        animals.add(new Thing(getString(R.string.dog),"le",R.drawable.dog));
        animals.add(new Thing(getString(R.string.chicken),"le",R.drawable.chicken));
        animals.add(new Thing(getString(R.string.duck),"le",R.drawable.duck));
        animals.add(new Thing(getString(R.string.dolphin),"le",R.drawable.dolphin));
        animals.add(new Thing(getString(R.string.elephant),"l'",R.drawable.elephant));
        animals.add(new Thing(getString(R.string.camel),"le",R.drawable.camel));
        animals.add(new Thing(getString(R.string.deer),"le",R.drawable.deer));
        animals.add(new Thing(getString(R.string.giragge),"la",R.drawable.giraffe));
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
        animals.add(new Thing(getString(R.string.hedgehog),"l'",R.drawable.hedgehog));
        animals.add(new Thing(getString(R.string.hippopotamus),"l'",R.drawable.hippopotamus));
        animals.add(new Thing(getString(R.string.jellyfish),"la",R.drawable.jellyfish));
        animals.add(new Thing(getString(R.string.kangaroo),"le",R.drawable.kangaroo));
        animals.add(new Thing(getString(R.string.llama),"le",R.drawable.llama));
        animals.add(new Thing(getString(R.string.octopus),"le",R.drawable.octopus));
        animals.add(new Thing(getString(R.string.owl),"l'",R.drawable.owl));
        animals.add(new Thing(getString(R.string.penguin),"le",R.drawable.penguin));
        animals.add(new Thing(getString(R.string.seahorse),"l'",R.drawable.seahorse));
        animals.add(new Thing(getString(R.string.seal),"le",R.drawable.seal));
        animals.add(new Thing(getString(R.string.shark),"le",R.drawable.shark));
        animals.add(new Thing(getString(R.string.whale),"la",R.drawable.whale));
        animals.add(new Thing(getString(R.string.ant),"la",R.drawable.ant));
        animals.add(new Thing(getString(R.string.anteater),"le",R.drawable.anteater));
        animals.add(new Thing(getString(R.string.arctic_fox),"le",R.drawable.arctic_fox));
        animals.add(new Thing(getString(R.string.bear),"l'",R.drawable.bear));
        animals.add(new Thing(getString(R.string.beetle),"le",R.drawable.beetle));
        animals.add(new Thing(getString(R.string.bison),"le",R.drawable.bison));
        animals.add(new Thing(getString(R.string.black_panther),"la",R.drawable.black_panther));
        animals.add(new Thing(getString(R.string.chameleon),"le",R.drawable.chameleon));
        animals.add(new Thing(getString(R.string.cheetah),"le",R.drawable.cheetah));
        animals.add(new Thing(getString(R.string.clown_fish),"le",R.drawable.clown_fish));
        animals.add(new Thing(getString(R.string.cougar),"le",R.drawable.cougar));
        animals.add(new Thing(getString(R.string.cow),"la",R.drawable.cow));
        animals.add(new Thing(getString(R.string.crocodile),"le",R.drawable.crocodile));
        animals.add(new Thing(getString(R.string.eagle),"l'",R.drawable.eagle));
        animals.add(new Thing(getString(R.string.emu),"l'",R.drawable.emu));
        animals.add(new Thing(getString(R.string.fox),"le",R.drawable.fox));
        animals.add(new Thing(getString(R.string.goat),"la",R.drawable.goat));
        animals.add(new Thing(getString(R.string.gorilla),"le",R.drawable.gorilla));
        animals.add(new Thing(getString(R.string.hamster),"le",R.drawable.hamster));
        animals.add(new Thing(getString(R.string.jaguar),"le",R.drawable.jaguar));
        animals.add(new Thing(getString(R.string.ladybug),"la",R.drawable.ladybug));
        animals.add(new Thing(getString(R.string.maki),"le",R.drawable.lemur));
        animals.add(new Thing(getString(R.string.leopard),"le",R.drawable.leopard));
        animals.add(new Thing(getString(R.string.lobster),"l'",R.drawable.lobster));
        animals.add(new Thing(getString(R.string.mouse),"la",R.drawable.mouse));
        animals.add(new Thing(getString(R.string.orca),"l'",R.drawable.orca));
        animals.add(new Thing(getString(R.string.ostrich),"l'",R.drawable.ostrich));
        animals.add(new Thing(getString(R.string.otter),"la",R.drawable.otter));
        animals.add(new Thing(getString(R.string.peacock),"le",R.drawable.peacock));
        animals.add(new Thing(getString(R.string.pigeon),"le",R.drawable.pigeon));
        animals.add(new Thing(getString(R.string.polar_bear),"l'",R.drawable.whale));
        animals.add(new Thing(getString(R.string.prawn),"la",R.drawable.prawn));
        animals.add(new Thing(getString(R.string.puffer_fish),"le",R.drawable.puffer_fish));
        animals.add(new Thing(getString(R.string.raccoon),"le",R.drawable.raccoon));
        animals.add(new Thing(getString(R.string.rat),"le",R.drawable.rat));
        animals.add(new Thing(getString(R.string.red_panda),"le",R.drawable.red_panda));
        animals.add(new Thing(getString(R.string.reindeer),"le",R.drawable.reindeer));
        animals.add(new Thing(getString(R.string.rhinoceros),"le",R.drawable.rhinoceros));
        animals.add(new Thing(getString(R.string.salmon),"le",R.drawable.salmon));
        animals.add(new Thing(getString(R.string.scorpion),"le",R.drawable.scorpion));
        animals.add(new Thing(getString(R.string.skunk),"la",R.drawable.skunk));
        animals.add(new Thing(getString(R.string.sloth),"la",R.drawable.sloth));
        animals.add(new Thing(getString(R.string.snail),"l'",R.drawable.snail));
        animals.add(new Thing(getString(R.string.spider),"l'",R.drawable.spider));
        animals.add(new Thing(getString(R.string.starfish),"l'",R.drawable.starfish));
        animals.add(new Thing(getString(R.string.tiger),"le",R.drawable.tiger));
        animals.add(new Thing(getString(R.string.tortoise),"la",R.drawable.tortoise));
        animals.add(new Thing(getString(R.string.toucan),"le",R.drawable.toucan));
        animals.add(new Thing(getString(R.string.turkey),"la",R.drawable.turkey));
        animals.add(new Thing(getString(R.string.walrus),"le",R.drawable.walrus));
        animals.add(new Thing(getString(R.string.wolf),"le",R.drawable.wolf));
        animals.add(new Thing(getString(R.string.yak),"le",R.drawable.yak));
        animals.add(new Thing(getString(R.string.zebra),"le",R.drawable.zebra));

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

        animalViewHolder.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                text = animals.get(position).getNAme();
                t2s.stop();
                play.setImageResource(R.drawable.play_button);
                playing = false;
            }

        });

        TextView learnLabel = findViewById(R.id.learnLabel);
        learnLabel.setText(getText(R.string.apprend_les_noms_des_animaux));
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
