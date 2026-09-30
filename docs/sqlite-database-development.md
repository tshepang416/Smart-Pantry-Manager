# SQLite Database Development Notes

## 1. Initial database approach
The Smart Pantry Manager app will rely on Android SQLite to store pantry items and recipe suggestions locally on the device. The database needs to be lightweight, persistent, and easy to query from the UI.

## 2. Database constants and helper setup
The project uses a custom `PantryDbHelper` class extending `SQLiteOpenHelper`. This keeps the database name, version, and table names in one place and allows Android to manage creation and upgrades automatically.
