package io.neilharvey.bogglebuddy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;

public class MainActivity extends AppCompatActivity {

    private static final int MIN_WORD_LENGTH = 3;
    private WordFinder wordFinder;
    private char[][] board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wordFinder = createWordFinder();
        board = createBoard();
    }

    public void onFloatingActionButtonClick(View v) {
        solveBoard();
    }

    private void solveBoard() {
        String[] words = wordFinder.findWords(board);
        showWords(words);
    }

    private void showWords(String[] words) {
        ExpandableListView listView = (ExpandableListView) findViewById(R.id.expandableListView);
        WordListAdapter listAdapter = new WordListAdapter(this.getApplicationContext(), words);
        listView.setAdapter(listAdapter);
    }

    private WordFinder createWordFinder() {
        Dictionary dictionary = new DatabaseDictionary(this.getApplicationContext());
        return new WordFinder(dictionary, MIN_WORD_LENGTH);
    }

    private static char[][] createBoard() {
//        return new char[][] {
//                {'A', 'B', 'C', 'D'},
//                {'E', 'F', 'G', 'H'},
//                {'I', 'J', 'K', 'L'},
//                {'M', 'N', 'O', 'P'}
//        };
        return new char[][] {
                {'A','B'},
                {'C','D'}
        };
    }
}
