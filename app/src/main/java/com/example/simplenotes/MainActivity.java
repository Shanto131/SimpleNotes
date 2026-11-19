package com.example.simplenotes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ListView listNotes;
    private final List<Note> noteList = new ArrayList<>();
    private final List<String> notesDisplayList = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        listNotes = findViewById(R.id.listNotes);

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                notesDisplayList
        );
        listNotes.setAdapter(adapter);

        listNotes.setOnItemClickListener((parent, view, position, id) -> {
            if (position >= 0 && position < noteList.size()) {
                Note selectedNote = noteList.get(position);
                Intent intent = new Intent(MainActivity.this, EditNoteActivity.class);
                intent.putExtra(EditNoteActivity.EXTRA_ORIGINAL_TITLE, selectedNote.getTitle());
                intent.putExtra(EditNoteActivity.EXTRA_CONTENT, selectedNote.getContent());
                intent.putExtra(EditNoteActivity.EXTRA_STORAGE_TYPE, selectedNote.getStorageType());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotes();
    }

    private void loadNotes() {
        noteList.clear();
        notesDisplayList.clear();

        List<Note> sqliteNotes = dbHelper.getAllNotes();
        noteList.addAll(sqliteNotes);

        SharedPreferences prefs = getSharedPreferences("NotesData", MODE_PRIVATE);
        Map<String, ?> prefNotes = prefs.getAll();
        for (Map.Entry<String, ?> entry : prefNotes.entrySet()) {
            boolean existsInSqlite = false;
            for (Note sqliteNote : sqliteNotes) {
                if (sqliteNote.getTitle().equals(entry.getKey())) {
                    existsInSqlite = true;
                    break;
                }
            }
            if (!existsInSqlite) {
                String title = entry.getKey();
                String content = entry.getValue() != null ? entry.getValue().toString() : "";
                noteList.add(new Note(title, content, "SharedPreferences"));
            }
        }

        for (Note note : noteList) {
            notesDisplayList.add(note.toString());
        }

        adapter.notifyDataSetChanged();
    }

    private void syncSQLiteToSharedPreferences() {
        List<Note> sqliteNotes = dbHelper.getAllNotes();
        if (sqliteNotes.isEmpty()) {
            Toast.makeText(this, R.string.sync_empty, Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences.Editor editor = getSharedPreferences("NotesData", MODE_PRIVATE).edit();
        for (Note note : sqliteNotes) {
            editor.putString(note.getTitle(), note.getContent());
        }
        editor.apply();

        Toast.makeText(this, R.string.sync_success, Toast.LENGTH_SHORT).show();
        loadNotes();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuAddNote) {
            startActivity(new Intent(this, AddNoteActivity.class));
            return true;
        }

        if (item.getItemId() == R.id.menuDeleteNote) {
            startActivity(new Intent(this, DeleteNoteActivity.class));
            return true;
        }

        if (item.getItemId() == R.id.menuSyncNotes) {
            syncSQLiteToSharedPreferences();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
