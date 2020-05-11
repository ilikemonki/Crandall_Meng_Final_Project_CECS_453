package com.example.crandall_meng_final_project_cecs_453.Model;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

/*
    UserData is responsible for accessing the database for and then holding a users account
    information.

    Note: The LoginController is responsible for input validation, NOT UserData.
 */
public class UserData {
    // User account state
    protected String mUsername, mEmail, mPhone;
    private String mPassword;
    protected int mAge;

    // Default value constructor, does not constitute a valid account
    public UserData() {
        mUsername = mPassword = mEmail = mPhone = null;
        mAge = -1;
    }

    // Checks the database for a user with a matching username/password (not safe to timing attacks
    // leaks both the existance of a user, and how many characters of the password are correct).
    //
    // If @param data is not null and a fitting account is found fills out the data object
    // with the users account information and returns null. Otherwise an appropriate error String
    // is returned.
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

    // Attempts to add a user to the database (directly leaks the existence of users).
    // Returns null on success or an appropriate error String on failure.
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

    // Attempts to update an existing users password.
    // Returns null on success or an appropriate error String on failure.
    public String updatePassword(Context ctx, String oldPass, String newPass) {
        if(checkPassword(mPassword, oldPass) == false) { return "Invalid password."; }

        try(Database database = new Database(ctx)) {

            String query = "UPDATE login SET password='" + newPass + "' WHERE username = '" + mUsername + "'";

            database.db.execSQL(query);

            mPassword = newPass;
            return null;
        } catch(Exception e) {
            return "Failed to update database.";
        }

    }

    // Attempts to update an existing users profile.
    // Returns null on success or an appropriate error String on failure.
    public String updateProfile(Context ctx, String name, String pass, String email, String phone, String age) {
        if(checkPassword(mPassword, pass) == false) { return "Invalid password."; }

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

    // Accessor functions for the class
    public String getUsername() { return mUsername; }
    public String getEmail() { return mEmail; }
    public String getPhone() { return mPhone; }
    public int getAge() { return mAge; }

    // Input sensitive timing, interestingly java has no native utility for password comparison(?).
    // Since this is a class project a simple string comparison is used, however this is quite
    // dangerous in real code.
    private static boolean checkPassword(String passLeft, String passRight) { return passLeft.equals(passRight); }

    // Retruns true if a username is not taken in the database, false otherwise
    protected static boolean usernameAvailable(String name, Database database) {
        Cursor cursor = database.db.query("login",
                new String[] {"username"},
                "username=?",
                new String[] {name},
                null, null, null, null);

        if(cursor.moveToFirst()) { return false; }

        return true;
    }

    // Checks the users password, and on success fills out @param data if it is not null then
    // returns true. Returns false if the passwords do not match.
    protected static boolean matchUser(UserData data, Cursor cursor, String username, String password) {
        if(checkPassword(cursor.getString(cursor.getColumnIndex("password")), password)) {
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

    // Attempts to set the default user login, which can be used to bypass the login screen.
    // On success returns null, on failure returns an appropriate String.
    public static String setDefaultLogin(Context ctx, String username) {
        try (Database database = new Database(ctx)) {
            String query = "INSERT INTO default_user(username) VALUES('" + username + "')";
            database.db.execSQL(query);
            return null;
        } catch (Exception e) {
            return "Failed to set default login.";
        }
    }

    // Attempts to clear the default user login, which can be used to bypass the login screen.
    // On success returns null, on failure returns an appropriate String.
    public static String clearDefaultLogin(Context ctx) {
        try (Database database = new Database(ctx)) {
            String query = "DELETE FROM default_user WHERE 1=1";
            database.db.execSQL(query);
            return null;
        } catch (Exception e) {
            return "Failed to clear default login.";
        }
    }

    // Attempts to login via the default user, returns false if any problem is encountered.
    // If a default user exists and the login is successful true is returned.
    public static boolean checkForDefaultLogin(Context ctx, UserData data) {
        try(Database database = new Database(ctx)) {
            Cursor cursor = database.db.query("default_user",
                    new String[] {"username"},
                    null,
                    new String[] {},
                    null, null, null, null);

            if(!cursor.moveToFirst()) { return false; }
            String defaultName = cursor.getString(cursor.getColumnIndex("username"));

            cursor = database.db.query("login",
                    new String[] {"username", "password", "email", "phone", "age"},
                    "username=?",
                    new String[] {defaultName},
                    null, null, null, null);

            if(!cursor.moveToFirst()) {
                Log.e("Default Login Failure", "Could not find default user in login table");
                return false;
            }

            data.mUsername = cursor.getString(cursor.getColumnIndex("username"));
            data.mPassword = cursor.getString(cursor.getColumnIndex("password"));
            data.mEmail = cursor.getString(cursor.getColumnIndex("email"));
            data.mPhone = cursor.getString(cursor.getColumnIndex("phone"));
            data.mAge = cursor.getInt(cursor.getColumnIndex("age"));
            return true;

        } catch(Exception e) {
            Log.e("Default Login Failure", e.toString());
            return false;
        }
    }
}
