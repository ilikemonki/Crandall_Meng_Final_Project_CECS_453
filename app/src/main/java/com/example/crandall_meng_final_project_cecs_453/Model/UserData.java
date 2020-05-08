package com.example.crandall_meng_final_project_cecs_453.Model;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class UserData {
    protected String mUsername, mEmail, mPhone;
    private String mPassword;
    protected int mAge;

    public UserData() {
        mUsername = mPassword = mEmail = mPhone = "";
        mAge = 0;
    }

    /*
        protected static final String ENSURE_USER_TABLE = "create TABLE IF NOT EXISTS login(" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "username TEXT NOT NULL, " +
            "password TEXT NOT NULL, " +
            "email TEXT NOT NULL, " +
            "phone TEXT NOT null, " +
            "age INTEGER)";
     */

    // @param data: if data isn't null and the credentials are valid, it initializes the object
    public static String validateUser(UserData data, Context ctx, String username, String password) {
        try(Database database = new Database(ctx)) {

            Cursor cursor = database.db.query("login",
                    new String[] {"username", "password", "email", "phone", "age"},
                    "username=?",
                    new String[] {username},
                    null, null, null, null);

            if(cursor.moveToFirst()) {
                if(matchUser(data, cursor, username, password)) { return null; }

                // Should not be required since usernames should be unique now.
                //while(cursor.moveToNext()) { if(matchUser(data, cursor, username, password)) { return null; } }
            }

            return "Invalid username/password combination";

        } catch(Exception e) {
            return "Failed to access database.";
        }
    }

    public static String signupUser(Context ctx, String username, String password, String email, String phone, String age) {
        try(Database database = new Database(ctx)) {
            if(!usernameAvailable(username, database)) { return "Username not available."; }

            String query = "INSERT INTO login (username, password, email, phone, age) VALUES(" +
                    "'" + username + "', " +
                    "'" + password + "', " +
                    "'" + email + "', " +
                    "'" + phone + "', " +
                    "'" + age + "')";

            database.db.execSQL(query);

            return null;
        } catch(Exception e) {
            return "Failed to access database.";
        }
    }

    public String updatePassword(Context ctx, String oldPass, String newPass) {
        if(checkPassword(oldPass) == false) { return "Invalid password."; }

        try(Database database = new Database(ctx)) {

            String query = "UPDATE login SET password='" + newPass + "' WHERE username = '" + mUsername + "'";

            database.db.execSQL(query);

            mPassword = newPass;
            return null;
        } catch(Exception e) {
            return "Failed to update database.";
        }

    }
    public String updateProfile(Context ctx, String name, String pass, String email, String phone, String age) {
        if(checkPassword(pass) == false) { return "Invalid password."; }

        try(Database database = new Database(ctx)) {
            if(!name.equals(mUsername) && !usernameAvailable(name, database)) { return "New username is not available."; }

            String query = "UPDATE login SET username = '" + name + "', email = '" + email + "', phone = '" + phone + "', age = '" + age + "' WHERE username = '" + mUsername+"'";

            database.db.execSQL(query);

            mUsername = name;
            mEmail = email;
            mPhone = phone;
            mAge = Integer.parseInt(age);
            return null;
        } catch(Exception e) {
            return "Failed to update database.";
        }
    }

    public String getUsername() { return mUsername; }
    public String getEmail() { return mEmail; }
    public String getPhone() { return mPhone; }
    public int getAge() { return mAge; }


    public boolean checkPassword(String pass) { return mPassword.equals(pass); }

    protected static boolean usernameAvailable(String name, Database database) {
        Cursor cursor = database.db.query("login",
                new String[] {"username"},
                "username=?",
                new String[] {name},
                null, null, null, null);

        if(cursor.moveToFirst()) { return false; }

        return true;
    }

    protected static boolean matchUser(UserData data, Cursor cursor, String username, String password) {
        if(cursor.getString(cursor.getColumnIndex("password")).equals(password)) {
            if(data != null) {
                data.mUsername = username;
                data.mPassword = password;
                data.mEmail = cursor.getString(cursor.getColumnIndex("email"));
                data.mPhone = cursor.getString(cursor.getColumnIndex("phone"));
                data.mAge = cursor.getInt(cursor.getColumnIndex("age"));
            }
            return true;
        }

        return false;
    }

    public static String setDefaultLogin(Context ctx, String username) {
        try (Database database = new Database(ctx)) {
            String query = "INSERT INTO default_user(username) VALUES('" + username + "')";
            database.db.execSQL(query);
            return null;
        } catch (Exception e) {
            return "Failed to set default login.";
        }
    }

    public static String clearDefaultLogin(Context ctx) {
        try (Database database = new Database(ctx)) {
            String query = "DELETE FROM default_user WHERE 1=1";
            database.db.execSQL(query);
            return null;
        } catch (Exception e) {
            return "Failed to clear default login.";
        }
    }

    public static boolean checkForDefaultLogin(Context ctx, UserData data) {
        try(Database database = new Database(ctx)) {
            Cursor cursor = database.db.query("default_user",
                    new String[] {"username"},
                    null,
                    new String[] {},
                    null, null, null, null);

            if(!cursor.moveToFirst()) { Log.e("???", "????"); return false; }
            String defaultName = cursor.getString(cursor.getColumnIndex("username"));

            cursor = database.db.query("login",
                    new String[] {"username", "password", "email", "phone", "age"},
                    "username=?",
                    new String[] {defaultName},
                    null, null, null, null);

            if(!cursor.moveToFirst()) { Log.e("2???", "2????"); return false; }

            data.mUsername = cursor.getString(cursor.getColumnIndex("username"));
            data.mPassword = cursor.getString(cursor.getColumnIndex("password"));
            data.mEmail = cursor.getString(cursor.getColumnIndex("email"));
            data.mPhone = cursor.getString(cursor.getColumnIndex("phone"));
            data.mAge = cursor.getInt(cursor.getColumnIndex("age"));
            return true;

        } catch(Exception e) {
            Log.e("Default Login Failure: ", e.toString());
            return false;
        }
    }
}
