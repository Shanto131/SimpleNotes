# Simple Notes App (MADT Lab #5)

A clean, native Android application built using Java and XML in Android Studio. **Simple Notes** demonstrates full CRUD operations, input validation, localization, and a dual-storage strategy combining **SQLite** (primary storage) and **SharedPreferences** (secondary storage) with automated data synchronization.

---

## 🌟 Application Features

- 📝 **Create Notes**: Add new notes with a title and multi-line content.
- 💾 **Storage Selection**: Choose whether to save notes into **SQLite (Primary)** or **SharedPreferences (Secondary)**.
- 📖 **View Notes**: Browse all saved notes in a single list with clear indicators showing their storage backend.
- ✏️ **Edit Notes**: Click any note from the home screen to view and update its title or content.
- 🗑️ **Delete Notes**: Delete notes from SQLite and SharedPreferences.
- 🔄 **Data Synchronization**: Synchronize all primary SQLite notes to secondary SharedPreferences with a single click.
- 🛡️ **Validation & Uniqueness**: Inputs are validated to prevent empty fields and duplicate note titles across both storage backends.
- 🌐 **Localization**: All user-visible strings are centralized in `strings.xml`.

---

## 💾 Storage Architecture

### Primary Storage: SQLite Database
- Managed via `SQLiteOpenHelper` in [`DatabaseHelper.java`](app/src/main/java/com/example/simplenotes/DatabaseHelper.java).
- Stores notes in the `notes` table with `_id`, `title` (UNIQUE), and `content` columns.
- Offers reliable, structured database persistence for notes.

### Secondary Storage: SharedPreferences
- Managed using Android `SharedPreferences` (`"NotesData"`).
- Stores lightweight key-value pairs (`title` -> `content`).

### Synchronization (SQLite -> SharedPreferences)
- Accessible from the main options menu ("Sync to SharedPreferences").
- Copies all notes currently stored in SQLite over to SharedPreferences without creating duplicate keys.

---

## 🔄 CRUD Operations Summary

| Operation | Activity / Component | Description |
| :--- | :--- | :--- |
| **Create** | `AddNoteActivity` | Validates input, checks cross-storage uniqueness, and saves to selected storage (SQLite or SharedPreferences). |
| **Read** | `MainActivity` | Fetches notes from SQLite and SharedPreferences, merging them into a unified `ListView`. |
| **Update** | `EditNoteActivity` | Pre-populates selected note data, validates new title/content, and updates the appropriate storage backend. |
| **Delete** | `DeleteNoteActivity` | Removes specified notes from both SQLite database and SharedPreferences. |

---

## 🛠️ Technologies Used

- **Language**: Java (JDK 11)
- **UI Framework**: Native Android XML Layouts & Material Design Components
- **IDE**: Android Studio
- **Database**: SQLite (`SQLiteOpenHelper`)
- **Key-Value Store**: Android `SharedPreferences`
- **Build System**: Gradle (Android Gradle Plugin 8+)

---

## 📁 Project Structure

```text
SimpleNotes/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/example/simplenotes/
│   │       │   ├── MainActivity.java        # Home screen list, options menu & sync
│   │       │   ├── AddNoteActivity.java     # Add note screen with storage selection
│   │       │   ├── EditNoteActivity.java    # Edit note screen
│   │       │   ├── DeleteNoteActivity.java  # Delete note screen
│   │       │   ├── DatabaseHelper.java      # SQLite database manager
│   │       │   └── Note.java                # Note data model
│   │       ├── res/
│   │       │   ├── layout/                  # XML layout files
│   │       │   │   ├── activity_main.xml
│   │       │   │   ├── activity_add_note.xml
│   │       │   │   ├── activity_edit_note.xml
│   │       │   │   └── activity_delete_note.xml
│   │       │   ├── menu/                    # Options menu (Add, Delete, Sync)
│   │       │   │   └── main_menu.xml
│   │       │   └── values/                  # Strings, colors, and themes
│   │       │       └── strings.xml
│   │       └── AndroidManifest.xml          # Application manifest
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

---

## 🚀 How to Install and Run

1. **Clone or Download the Repository**:
   ```bash
   git clone https://github.com/your-username/SimpleNotes.git
   ```

2. **Open in Android Studio**:
   - Launch Android Studio.
   - Select **Open an existing project** and navigate to the `SimpleNotes` folder.

3. **Build the Project**:
   - Wait for Gradle sync to complete.
   - Select **Build > Make Project** or run `./gradlew assembleDebug` in the terminal.

4. **Run on Emulator or Physical Device**:
   - Select an Android Virtual Device (AVD) or connect a physical Android device via USB debugging.
   - Click the **Run 'app'** button (▶️) or press `Shift + F10`.

---


## 🎓 Conclusion

This project fulfills all requirements for **MADT Lab #5**. It demonstrates foundational Android development skills including SQLite database management, SharedPreferences persistence, activity transitions, input validation, string localization, and edge-to-edge UI layout design.
