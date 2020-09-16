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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LearnAlphabet extends AppCompatActivity {
    String language;
    private boolean playing = false;
    private TextToSpeech t2s;
    private String text;
    private ImageView play;
    private List<Thing> alphabet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_alphabet);
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.alphabet);
        SharedPreferences gameSettings = getSharedPreferences("MyGamePreferences", MODE_PRIVATE);
        language = gameSettings.getString("Language", "DEFAULT");
        play = findViewById(R.id.play);
        ViewPager2 animalViewHolder = findViewById(R.id.viewPager);
        alphabet = new ArrayList<>();
        animalViewHolder.setAdapter(new Adapter(alphabet));
        assert language != null;
        if (language.equals("Arabic")) {
            play.setVisibility(View.GONE);
            ImageView playPlaceholder = findViewById(R.id.placeholder);
            playPlaceholder.setVisibility(View.VISIBLE);
            //animalViewHolder.setRotation(180);
        }
        play = findViewById(R.id.play);
        addAlphabet();

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
                    default: {
                        break;
                    }
                }

            }
        });

        animalViewHolder.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                text = alphabet.get(position).getNAme();
                t2s.stop();
                play.setImageResource(R.drawable.play_button);
                playing = false;
            }

        });

        TextView learnLabel = findViewById(R.id.learnLabel);
        learnLabel.setText(getString(R.string.learn_alphabet));
    }

    private void addAlphabet() {
        if (language.equals("Arabic")) {
            Toast.makeText(this, "Arabic", Toast.LENGTH_SHORT).show();
            alphabet.add(new Thing("أ"));
            alphabet.add(new Thing("ب"));
            alphabet.add(new Thing("ث"));
            alphabet.add(new Thing("ج"));
            alphabet.add(new Thing("ح"));
            alphabet.add(new Thing("خ"));
            alphabet.add(new Thing("د"));
            alphabet.add(new Thing("ذ"));
            alphabet.add(new Thing("ر"));
            alphabet.add(new Thing("ز"));
            alphabet.add(new Thing("س"));
            alphabet.add(new Thing("ش"));
            alphabet.add(new Thing("ص"));
            alphabet.add(new Thing("ض"));
            alphabet.add(new Thing("ط"));
            alphabet.add(new Thing("ظ"));
            alphabet.add(new Thing("ع"));
            alphabet.add(new Thing("غ"));
            alphabet.add(new Thing("ف"));
            alphabet.add(new Thing("ق"));
            alphabet.add(new Thing("ك"));
            alphabet.add(new Thing("ل"));
            alphabet.add(new Thing("م"));
            alphabet.add(new Thing("ن"));
            alphabet.add(new Thing("ه"));
            alphabet.add(new Thing("و"));
            alphabet.add(new Thing("ي"));
        } else {
            alphabet.add(new Thing("A"));
            alphabet.add(new Thing("B"));
            alphabet.add(new Thing("C"));
            alphabet.add(new Thing("D"));
            alphabet.add(new Thing("E"));
            alphabet.add(new Thing("F"));
            alphabet.add(new Thing("G"));
            alphabet.add(new Thing("H"));
            alphabet.add(new Thing("I"));
            alphabet.add(new Thing("J"));
            alphabet.add(new Thing("K"));
            alphabet.add(new Thing("L"));
            alphabet.add(new Thing("M"));
            alphabet.add(new Thing("N"));
            alphabet.add(new Thing("O"));
            alphabet.add(new Thing("P"));
            alphabet.add(new Thing("Q"));
            alphabet.add(new Thing("R"));
            alphabet.add(new Thing("S"));
            alphabet.add(new Thing("T"));
            alphabet.add(new Thing("U"));
            alphabet.add(new Thing("V"));
            alphabet.add(new Thing("W"));
            alphabet.add(new Thing("X"));
            alphabet.add(new Thing("Y"));
            alphabet.add(new Thing("Z"));
        }

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