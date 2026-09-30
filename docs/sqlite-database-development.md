# SQLite Database Development Notes

## 1. Initial database approach
The Smart Pantry Manager app will rely on Android SQLite to store pantry items and recipe suggestions locally on the device. The database needs to be lightweight, persistent, and easy to query from the UI.

## 2. Database constants and helper setup
The project uses a custom `PantryDbHelper` class extending `SQLiteOpenHelper`. This keeps the database name, version, and table names in one place and allows Android to manage creation and upgrades automatically.

## 3. Pantry table schema
The pantry table stores each ingredient with an auto-increment id, a required name, quantity, optional unit, and expiry date. This gives us a simple structure for the pantry list and for saving ingredient details during add/edit flows.

## 4. Recipes table schema
A second table stores recipe metadata: id, recipe name, ingredient list, and method. This is separate from pantry items so the app can keep suggestions independent from the user inventory.

## 5. SQLite lifecycle hooks
The `onCreate()` method creates both tables and calls a seed routine. The `onUpgrade()` method drops the tables and recreates them so the app can be updated cleanly when the schema changes.

## 6. Pantry CRUD insert and update logic
The helper implements insert and update methods using `ContentValues`, which is the standard Android pattern for inserting rows into SQLite tables. This lets the UI save ingredient records without writing raw SQL for every operation.

## 7. Pantry read and delete logic
The helper adds delete and select methods by using `db.delete(...)` and `db.query(...)` with a `Cursor`. The read flow converts rows into `Ingredient` objects so the pantry screen can render each item.
