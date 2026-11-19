package com.example.simplenotes;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditNoteActivity extends AppCompatActivity {

    public static final String EXTRA_ORIGINAL_TITLE = "extra_original_title";
    public static final String EXTRA_CONTENT = "extra_content";
    public static final String EXTRA_STORAGE_TYPE = "extra_storage_type";

    private EditText edtEditNoteName, edtEditNoteContent;
    private DatabaseHelper dbHelper;

    private String originalTitle;
    private String storageType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        dbHelper = new DatabaseHelper(this);

        edtEditNoteName = findViewById(R.id.edtEditNoteName);
        edtEditNoteContent = findViewById(R.id.edtEditNoteContent);
        TextView txtStorageType = findViewById(R.id.txtStorageType);
        Button btnUpdateNote = findViewById(R.id.btnUpdateNote);

        originalTitle = getIntent().getStringExtra(EXTRA_ORIGINAL_TITLE);
        String content = getIntent().getStringExtra(EXTRA_CONTENT);
        storageType = getIntent().getStringExtra(EXTRA_STORAGE_TYPE);

        if (originalTitle != null) {
            edtEditNoteName.setText(originalTitle);
        }
        if (content != null) {
            edtEditNoteContent.setText(content);
        }
        if (storageType != null) {
            String label = getString(R.string.storage_type_label) + " " + storageType;
            txtStorageType.setText(label);
        }

        btnUpdateNote.setOnClickListener(v -> updateNote());
    }

    private void updateNote() {
        String newTitle = edtEditNoteName.getText().toString().trim();
        String newContent = edtEditNoteContent.getText().toString().trim();

        if (newTitle.isEmpty() || newContent.isEmpty()) {
            Toast.makeText(this, R.string.empty_note_warning, Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences prefs = getSharedPreferences("NotesData", MODE_PRIVATE);

        if (originalTitle != null && !originalTitle.equals(newTitle)) {
            boolean existsInSqlite = dbHelper.noteExists(newTitle);
            boolean existsInPrefs = prefs.contains(newTitle);
            if (existsInSqlite || existsInPrefs) {
                Toast.makeText(this, R.string.note_exists_error, Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if ("SQLite".equals(storageType)) {
            boolean updated = dbHelper.updateNote(originalTitle, newTitle, newContent);
            if (!updated) {
                Toast.makeText(this, R.string.note_exists_error, Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            SharedPreferences.Editor editor = prefs.edit();
            if (originalTitle != null && !originalTitle.equals(newTitle)) {
                editor.remove(originalTitle);
            }
            editor.putString(newTitle, newContent);
            editor.apply();
        }

        Toast.makeText(this, R.string.note_updated, Toast.LENGTH_SHORT).show();
        finish();
    }
}
