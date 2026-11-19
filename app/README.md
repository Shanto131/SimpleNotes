# Simple Notes – MADT Lab #5

## Project Description

**Simple Notes** is an Android application developed as part of MADT Lab #5 – Activities, Interactions and Storage.

The application allows users to create, view, edit and delete notes. Each note contains a title and content. It supports two storage methods: SQLite as the primary storage and SharedPreferences as the secondary storage.

Users can select their preferred storage method when creating a note and synchronize notes from SQLite to SharedPreferences.

## Main Features

- Create notes with a title and content.
- View saved notes in a list.
- Edit existing notes.
- Delete existing notes.
- Choose between SQLite and SharedPreferences when saving notes.
- Synchronize notes from SQLite to SharedPreferences.
- Prevent duplicate note titles across storage methods.
- Validate input to prevent empty notes.
- Preserve saved notes after restarting the application.
- Use Android string resources for localizable user-interface text.

## CRUD Operations

The application implements all four CRUD operations:

| Operation | Description |
|---|---|
| Create | Add a new note with a title and content. |
| Read | Display saved notes in the main screen. |
| Update | Edit an existing note's title and content. |
| Delete | Remove an existing note from storage. |

## Storage Methods

### 1. SQLite – Primary Storage

SQLite is used as the primary database.

The application uses `DatabaseHelper.java`, which extends `SQLiteOpenHelper`, to manage the database and perform database operations.

### 2. SharedPreferences – Secondary Storage

SharedPreferences is used as the secondary storage method.

The application stores notes using the SharedPreferences file named `NotesData`.

### 3. Storage Selection and Synchronization

Users can choose SQLite or SharedPreferences when creating a note.

The application also provides a synchronization option that copies notes from the primary SQLite storage to the secondary SharedPreferences storage.

## Technologies Used

- **Programming Language:** Java
- **User Interface:** XML
- **Development Environment:** Android Studio
- **Primary Database:** SQLite
- **Secondary Storage:** SharedPreferences
- **Platform:** Android

## Project Structure

The main project components include:

```text
SimpleNotes/
│
├── app/
│   └── src/
│       └── main/
│           ├── java/com/example/simplenotes/
│           │   ├── MainActivity.java
│           │   ├── AddNoteActivity.java
│           │   ├── EditNoteActivity.java
│           │   ├── DeleteNoteActivity.java
│           │   ├── DatabaseHelper.java
│           │   └── Note.java
│           │
│           ├── res/
│           │   ├── layout/
│           │   ├── menu/
│           │   └── values/
│           │       └── strings.xml
│           │
│           └── AndroidManifest.xml
│
└── README.md
```

## How to Run the Application

1. Install Android Studio.
2. Clone or download this repository.
3. Open the project in Android Studio.
4. Allow Gradle to synchronize the project.
5. Start an Android emulator or connect an Android device.
6. Click the **Run** button in Android Studio.
7. The Simple Notes application will launch on your device.

## Application Screenshots

Add screenshots of the following application screens:

| Screen | Screenshot |
|---|---|
| Main Screen | `screenshots/main_screen.png` |
| Add Note | `screenshots/add_note.png` |
| Edit Note | `screenshots/edit_note.png` |
| Delete Note | `screenshots/delete_note.png` |

*Note: Add your actual screenshots to the screenshots folder before publishing the repository.*

## Input Validation

The application checks note titles and content before saving or updating notes.

If the required fields are empty, the application displays a Toast warning and prevents the invalid operation.

## Code Quality and Localization

The application uses meaningful class, method and variable names. User-visible text is maintained in Android string resources (`strings.xml`) to support localization.

## Conclusion

The Simple Notes application demonstrates Android application development, Activity interactions, CRUD operations and data storage using SQLite and SharedPreferences.

The project was developed as part of MADT Lab #5 to practice building an Android application with multiple activities and different storage methods.