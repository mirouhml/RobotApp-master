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

public class LearnNumbers extends AppCompatActivity {
    private boolean playing = false;
    private TextToSpeech t2s;
    private String text;
    private ImageView play;
    private List<Thing> numbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_numbers);
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.numbers);
        SharedPreferences gameSettings = getSharedPreferences("MyGamePreferences", MODE_PRIVATE);
        String language = gameSettings.getString("Language", "DEFAULT");
        play = findViewById(R.id.play);
        assert language != null;
        if (language.equals("Arabic")) {
            play.setVisibility(View.GONE);
            ImageView playPlaceholder = findViewById(R.id.placeholder);
            playPlaceholder.setVisibility(View.VISIBLE);
        }
        play = findViewById(R.id.play);
        ViewPager2 animalViewHolder = findViewById(R.id.viewPager);
        numbers = new ArrayList<>();
        addNumbers();
        //Resources recources = getResources();
        //View view = findViewById(R.id.learn_layout);
        animalViewHolder.setAdapter(new Adapter(numbers));
        animalViewHolder.setClipChildren(false);
        animalViewHolder.setClipToPadding(false);
        animalViewHolder.setOffscreenPageLimit(3);
        animalViewHolder.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleY(0.95f + r * 0.05f);
        });

        animalViewHolder.setPageTransformer(compositePageTransformer);
        t2s = new TextToSpeech(getApplicationContext(), status -> {
            if (status != TextToSpeech.ERROR) {
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
                switch (language) {
                    case "English": {
                        t2s.setLanguage(Locale.ENGLISH);
                        break;
                    }
                    case "French": {
                        t2s.setLanguage(Locale.FRENCH);
                        break;
                    }
                }

            }
        });

        animalViewHolder.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                text = numbers.get(position).getNAme();
                t2s.stop();
                play.setImageResource(R.drawable.play_button);
                playing = false;
            }

        });

        TextView learnLabel = findViewById(R.id.learnLabel);
        learnLabel.setText(getText(R.string.learn_numbers));
    }

    private void addNumbers() {
        numbers.add(new Thing("0", getString(R.string.zero)));
        numbers.add(new Thing("1", getString(R.string.one)));
        numbers.add(new Thing("2", getString(R.string.two)));
        numbers.add(new Thing("3", getString(R.string.three)));
        numbers.add(new Thing("4", getString(R.string.four)));
        numbers.add(new Thing("5", getString(R.string.five)));
        numbers.add(new Thing("6", getString(R.string.six)));
        numbers.add(new Thing("7", getString(R.string.seven)));
        numbers.add(new Thing("8", getString(R.string.eight)));
        numbers.add(new Thing("9", getString(R.string.nine)));
        numbers.add(new Thing("10", getString(R.string.ten)));
        numbers.add(new Thing("11", getString(R.string.eleven)));
        numbers.add(new Thing("12", getString(R.string.twelve)));
        numbers.add(new Thing("13", getString(R.string.thirteen)));
        numbers.add(new Thing("14", getString(R.string.fourteen)));
        numbers.add(new Thing("15", getString(R.string.fifteen)));
        numbers.add(new Thing("16", getString(R.string.sixteen)));
        numbers.add(new Thing("17", getString(R.string.seventeen)));
        numbers.add(new Thing("18", getString(R.string.eighteen)));
        numbers.add(new Thing("19", getString(R.string.nineteen)));
        numbers.add(new Thing("20", getString(R.string.twenty)));
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
        if (!playing) {
            play.setImageResource(R.drawable.pause_button);
            t2s.speak(text, TextToSpeech.QUEUE_FLUSH, params, "Robot4Kids");
            playing = true;
        } else {
            t2s.stop();
            play.setImageResource(R.drawable.play_button);
            playing = false;
        }
    }
}