package com.example.ac.mp1;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class GameScreenActivity extends AppCompatActivity {

    Button opt1; // four buttons for options
    Button opt2;
    Button opt3;
    Button opt4;

    TextView scoreText;

    ImageView image; // image of current person

    ArrayList<Button> allButtons; // used to store all buttons -- easy randomization

    ArrayList<String> memberNames; // all member names

    String correctName; // stores correct name -- used to compare answer and find image
    int score = 0; // score
    int index = 0; // cycle through member names, so use index to keep track (no unnecessary repeats)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        image = findViewById(R.id.currentImage);

        opt1 = findViewById(R.id.option1);
        opt2 = findViewById(R.id.option2);
        opt3 = findViewById(R.id.option3);
        opt4 = findViewById(R.id.option4);

        scoreText = findViewById(R.id.score);

        allButtons = new ArrayList<Button>();
        allButtons.add(opt1);
        allButtons.add(opt2);
        allButtons.add(opt3);
        allButtons.add(opt4);

        scoreText.setText(Integer.toString(score));

        memberNames = Utils.memberNames;
        Collections.shuffle(memberNames);

        playRound();

    }

    private void playRound() {
        // shuffle all buttons so not always in same order
        Collections.shuffle(allButtons);

        // get current correct person
        correctName = memberNames.get(index);
        String imageURL = correctName.replaceAll("\\s","").toLowerCase();

        int imageID = getResources().getIdentifier(imageURL, "drawable", getPackageName());

        // set image
//        Drawable testImage = Resources.getSystem().getDrawable(imageID);
        image.setImageResource(imageID);

        // find 3 other random names
        ArrayList<Integer> list = new ArrayList<Integer>(); // get 3 indices that are unique and NOT index
        for (int i = 0; i < memberNames.size(); i++) {
            if (i != index) {
                list.add(new Integer(i));
            }
        }

        // shuffle other names (not including current)
        Collections.shuffle(list);

        // set all texts for other names
        for (int i = 0; i < allButtons.size(); i++) {
            if (i == 0) {
                allButtons.get(i).setText(correctName);
                allButtons.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedCorrectName();
                    }
                });
            } else {
                allButtons.get(i).setText(memberNames.get(list.get(i)));
                allButtons.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedIncorrectName();
                    }
                });
            }
        }

        // go to the next person in the shuffled list (no duplicates)
        index++;
        if (index == memberNames.size()) { // went through all, shuffle and restart
            Collections.shuffle(memberNames);
            index = 0;
        }

    }

    private void selectedCorrectName() {
        // correct name
        score++;
        scoreText.setText(Integer.toString(score));
        playRound();
    }

    private void selectedIncorrectName() {
        // incorrect name
        playRound();
    }

}
