package com.example.hw221;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText mInputNote;
    private Button mBtnSaveNote;
    private TextView noteNumberTxt;
    private SharedPreferences myNoteSharedPref;
    private int noteNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        getDateFromSharedPref();
    }

    private void initViews() {

        mInputNote = findViewById(R.id.inputNote);
        mBtnSaveNote = findViewById(R.id.btnSaveNote);
        myNoteSharedPref = getSharedPreferences(getString(R.string.notes), MODE_PRIVATE);
        noteNumberTxt = findViewById(R.id.noteNumberTxt);

        mBtnSaveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor myEditor = myNoteSharedPref.edit();
                String noteTxt = mInputNote.getText().toString();
                myEditor.putString(String.format(getString(R.string.note_format), noteNumber), noteTxt);
                noteNumber++;
                myEditor.apply();
                Toast.makeText(MainActivity.this, String.format(getString(R.string.note_format_saved), noteNumber), Toast.LENGTH_SHORT).show();
                noteNumberTxt.setText(String.format(getString(R.string.note_format), noteNumber));
            }
        });
    }

    private void getDateFromSharedPref() {
        int size = myNoteSharedPref.getAll().size();
        if (size == 0) return;
        else {
            noteNumber = size;
        }
        noteNumberTxt.setText(String.format(getString(R.string.note_format), noteNumber));
        String noteTxt = myNoteSharedPref.getString(String.format(getString(R.string.note_format), noteNumber), "");
        mInputNote.setText(noteTxt);
    }
}
