# Smart Pantry Manager

Smart Pantry Manager is an Android Java app designed to help users reduce food waste by tracking pantry ingredients and suggesting only the recipes they can make using what they already have at home.

## Project overview

The application allows a user to:
- add, edit, and delete pantry items
- store ingredient quantity and expiry information in SQLite
- review a list of seeded recipes
- view only the recipes that satisfy the strict matching rule
- open recipe details and view preparation steps
- adjust basic app settings

## Core logic

The app enforces a strict rule: a recipe is only suggested when every required ingredient is currently available in the pantry in at least the required quantity. Partial matches are excluded from the main suggestion list.

## Database choice

This project uses SQLite via `SQLiteOpenHelper`.

Why SQLite:
- it is local to the device and suitable for a single-user pantry app
- it matches the persistent storage approach covered in the module
- it supports full CRUD operations without needing external services or network setup
- it keeps the app fully self-contained for offline use

## Project structure

- `app/src/main/java/com/smartpantry/manager` - Java activities, adapters, models, database helper, and recipe matching logic
- `app/src/main/res/layout` - Android XML layouts
- `app/src/main/res/values` - strings, colors, and theme resources
- `app/src/main/AndroidManifest.xml` - app manifest and activity registration

## Setup and run instructions

1. Open the project in Android Studio.
2. Ensure the Android SDK and Java 17 JDK are installed and configured.
3. Let Gradle sync the project.
4. Select an emulator or connected device.
5. Run the app from the `PantryActivity` launcher entry.

## Repository

GitHub repository: https://github.com/tshepang416/Smart-Pantry-Manager

## Notes

This app is built as a Java Android project for the Mobile App Development 700 practical assignment and is intended to demonstrate pantry management, strict recipe matching, and persistent SQLite data storage.
