package com.example.simplenotes;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DeleteNoteActivity extends AppCompatActivity {

    private EditText edtDeleteNote;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_note);

        dbHelper = new DatabaseHelper(this);
        edtDeleteNote = findViewById(R.id.edtDeleteNote);
        Button btnDeleteNote = findViewById(R.id.btnDeleteNote);

        btnDeleteNote.setOnClickListener(v -> deleteNote());
    }

    private void deleteNote() {
        String noteName = edtDeleteNote.getText().toString().trim();

        if (noteName.isEmpty()) {
            Toast.makeText(this, R.string.empty_title_warning, Toast.LENGTH_SHORT).show();
            return;
        }

        boolean deletedFromSqlite = dbHelper.deleteNote(noteName);

        SharedPreferences preferences = getSharedPreferences("NotesData", MODE_PRIVATE);
        boolean deletedFromPrefs = false;
        if (preferences.contains(noteName)) {
            preferences.edit().remove(noteName).apply();
            deletedFromPrefs = true;
        }

        if (deletedFromSqlite || deletedFromPrefs) {
            Toast.makeText(this, R.string.note_deleted, Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, R.string.note_not_found, Toast.LENGTH_SHORT).show();
        }
    }
}
