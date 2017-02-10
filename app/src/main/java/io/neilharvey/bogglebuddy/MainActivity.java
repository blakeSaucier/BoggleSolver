package io.neilharvey.bogglebuddy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static final int MIN_WORD_LENGTH = 3;
    private WordFinder wordFinder;
    private char[][] board = new char[4][4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWordFinder();
        addTextChangedListener();
    }

    private void addTextChangedListener() {

        final EditText editText = (EditText) findViewById(R.id.editText);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String value = s.toString().toLowerCase();
                for (int i = start; i < start + count; i++) {
                    int x = i % 4;
                    int y = i / 4;
                    board[x][y] = value.charAt(i);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                drawBoard();
            }
        });

        editText.setOnEditorActionListener(new EditText.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager in = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    solveBoard();
                }

                return false;
            }
        });
    }

    private void drawBoard() {
        setBoardCharacter(R.id.a1, 0, 0);
        setBoardCharacter(R.id.b1, 1, 0);
        setBoardCharacter(R.id.c1, 2, 0);
        setBoardCharacter(R.id.d1, 3, 0);
        setBoardCharacter(R.id.a2, 0, 1);
        setBoardCharacter(R.id.b2, 1, 1);
        setBoardCharacter(R.id.c2, 2, 1);
        setBoardCharacter(R.id.d2, 3, 1);
        setBoardCharacter(R.id.a3, 0, 2);
        setBoardCharacter(R.id.b3, 1, 2);
        setBoardCharacter(R.id.c3, 2, 2);
        setBoardCharacter(R.id.d3, 3, 2);
        setBoardCharacter(R.id.a4, 0, 3);
        setBoardCharacter(R.id.b4, 1, 3);
        setBoardCharacter(R.id.c4, 2, 3);
        setBoardCharacter(R.id.d4, 3, 3);
    }

    private void setBoardCharacter(int id, int x, int y) {
        TextView text = (TextView) findViewById(id);
        char character = Character.toUpperCase(board[x][y]);
        text.setText(new char[]{character}, 0, 1);
    }

    public void onFloatingActionButtonClick(View v) {
        resetBoard();
    }

    private void resetBoard() {
        board = new char[4][4];
        EditText editText = (EditText) findViewById(R.id.editText);
        editText.setText("");
        editText.requestFocus();
        ExpandableListView listView = (ExpandableListView) findViewById(R.id.expandableListView);
        listView.setVisibility(View.INVISIBLE);
    }

    private void solveBoard() {

        if(!boardIsValid()) {
            return;
        }

        Set<Word> words = wordFinder.findWords(board);
        showWords(words);
    }

    private boolean boardIsValid() {
        for(int i=0; i<4; i++) {
            for(int j=0; j<4; j++) {
                if(!Character.isLetter(board[i][j])) {
                    return false;
                }
            }
        }
        return true;
    }

    private void showWords(Set<Word> words) {
        ExpandableListView listView = (ExpandableListView) findViewById(R.id.expandableListView);
        WordListAdapter listAdapter = WordListAdapter.create(this.getApplicationContext(), words);
        listView.setAdapter(listAdapter);
        listView.setVisibility(View.VISIBLE);
    }

    private void initWordFinder() {
        TrieVocabulary dictionary = new TrieVocabulary();
        try {
            dictionary.loadWords(this.getApplicationContext());
        } catch (IOException ex) {
        }
        wordFinder = new WordFinder(dictionary, MIN_WORD_LENGTH);
    }
}
