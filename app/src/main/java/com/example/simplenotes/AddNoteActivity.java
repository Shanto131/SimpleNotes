package com.example.simplenotes;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddNoteActivity extends AppCompatActivity {

    private EditText edtNoteName, edtNoteContent;
    private RadioButton rbSqlite;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        dbHelper = new DatabaseHelper(this);

        edtNoteName = findViewById(R.id.edtNoteName);
        edtNoteContent = findViewById(R.id.edtNoteContent);
        rbSqlite = findViewById(R.id.rbSqlite);
        Button btnSaveNote = findViewById(R.id.btnSaveNote);

        btnSaveNote.setOnClickListener(v -> saveNote());
    }

    private void saveNote() {
        String noteName = edtNoteName.getText().toString().trim();
        String noteContent = edtNoteContent.getText().toString().trim();

        if (noteName.isEmpty() || noteContent.isEmpty()) {
            Toast.makeText(this, R.string.empty_note_warning, Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences prefs = getSharedPreferences("NotesData", MODE_PRIVATE);
        boolean existsInSqlite = dbHelper.noteExists(noteName);
        boolean existsInPrefs = prefs.contains(noteName);

        if (existsInSqlite || existsInPrefs) {
            Toast.makeText(this, R.string.note_exists_error, Toast.LENGTH_SHORT).show();
            return;
        }

        if (rbSqlite.isChecked()) {
            long id = dbHelper.insertNote(noteName, noteContent);
            if (id == -1) {
                Toast.makeText(this, R.string.note_exists_error, Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            prefs.edit().putString(noteName, noteContent).apply();
        }

        Toast.makeText(this, R.string.note_saved, Toast.LENGTH_SHORT).show();
        finish();
    }
}
