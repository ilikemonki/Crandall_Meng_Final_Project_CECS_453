package com.example.crandall_meng_final_project_cecs_453.Controller;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;

import com.example.crandall_meng_final_project_cecs_453.Model.UserData;

/*
    LoginController has the responsibility of managing the login state and user data.
    It also acts as the validation layer before allowing access to the unvalidated backend models.
 */
public class LoginController {
    // Constants for the validation layer to use
    public static final int MAX_AGE_FOR_USER = 130;
    public static final int MIN_AGE_FOR_USER = 5;
    public static final int MAX_CHARACTERS_IN_INPUT = 50;
    public static final int MIN_PASSWORD_LENGTH = 8;
    public static final int MIN_PHONE_LENGTH = 7;

    // Singleton pattern controller object
    protected static LoginController mController = new LoginController();

    // State for the controller object
    protected boolean mLoggedIn = false;
    protected UserData mUserData = null;

    // Singleton pattern accessor method
    public static synchronized LoginController getInstance() { return mController; }

    // Private constructor to disallow mistaken arbitrary Controller creation
    private LoginController() {}

    // Called to log into the default account stored in the database, returns true if such
    // an account was found.
    public boolean checkForDefaultLogin(Context ctx) {
        mUserData = null;
        mLoggedIn = false;
        UserData data = new UserData();

        if(UserData.checkForDefaultLogin(ctx, data)) {
            mUserData = data;
            mLoggedIn = true;

            return true;
        }

        return false;
    }

    // Attempts to sign up a new user account after validation, does NOT log into that account.
    // Returns null if the sign up was successful, otherwise it returns a String with an
    // appropriate error message.
    public String attemptSignup(Context ctx, String username, String password, String retypedPassword, String email, String phone, String age) {
        logOut(ctx);

        if(ctx == null || username == null || password == null || retypedPassword == null || email == null || phone == null || age == null) {
            return "Signup attempted with null parameters.";
        }

        if(TextUtils.isEmpty(username)) { return "Username must be filled in."; }
        if(TextUtils.isEmpty(password)) { return "Password must be filled in."; }
        if(TextUtils.isEmpty(retypedPassword)) { return "Password must be filled in twice."; }
        if(TextUtils.isEmpty(email)) { return "Email must be filled in."; }
        if(TextUtils.isEmpty(phone)) { return "Phone must be filled in."; }
        if(TextUtils.isEmpty(age)) { return "Age must be filled in."; }

        if(username.length() > MAX_CHARACTERS_IN_INPUT) { return "Username is too long (MAX of " + MAX_CHARACTERS_IN_INPUT +" Characters)."; }
        if(password.length() > MAX_CHARACTERS_IN_INPUT) { return "Password is too long (MAX of " + MAX_CHARACTERS_IN_INPUT +" Characters)."; }
        if(email.length() > MAX_CHARACTERS_IN_INPUT) { return "Email is too long (MAX of " + MAX_CHARACTERS_IN_INPUT +" Characters)."; }
        if(phone.length() > MAX_CHARACTERS_IN_INPUT) { return "Phone is too long (MAX of " + MAX_CHARACTERS_IN_INPUT +" Characters)."; }
        if(age.length() > MAX_CHARACTERS_IN_INPUT) { return "Age value is too long (MAX of " + MAX_CHARACTERS_IN_INPUT +" Characters)."; }

        if(password.length() < MIN_PASSWORD_LENGTH) { return "Passwords must be at least length " + MIN_PASSWORD_LENGTH + ".";}
        if(password.equals(retypedPassword) == false) { return "Passwords must match."; }

        if(Patterns.EMAIL_ADDRESS.matcher(email).matches() == false) { return "Email must be valid."; }

        if(validatePhoneNumber(phone) == false) { return "Phone number must be valid."; }

        try {
            Integer numericAge = Integer.parseInt(age);
            if(numericAge > MAX_AGE_FOR_USER) { return "Age was too high to be valid."; }
            else if(numericAge < MIN_AGE_FOR_USER) { return "Age was too low to be valid."; }
        } catch(NumberFormatException e) {
            return "Age was invalid.";
        }

        return UserData.signupUser(ctx, username, password, email, phone, age);
    }

    // Attempts to log into an account after validation, if the user is already logged in it logs
    // then out first. If successful it returns null, otherwise it returns an appropriate
    // error String.
    public String attemptLogin(Context ctx, String username, String password, boolean rememberMe) {
        logOut(ctx);

        UserData data = new UserData();

        if(ctx == null || username == null || password == null) {
            return "Login attempted with null parameters.";
        }

        if(TextUtils.isEmpty(username)) { return "Username must be filled in."; }
        if(TextUtils.isEmpty(password)) { return "Password must be filled in."; }

        if(username.length() > MAX_CHARACTERS_IN_INPUT) { return "Username is too long (MAX of " + MAX_CHARACTERS_IN_INPUT +" Characters)."; }
        if(password.length() > MAX_CHARACTERS_IN_INPUT) { return "Password is too long (MAX of " + MAX_CHARACTERS_IN_INPUT +" Characters)."; }

        String err = UserData.validateUser(data, ctx, username, password);
        if(err != null) { return err; }

        if(rememberMe) {
            err = UserData.setDefaultLogin(ctx, username);
            if(err != null) { return err; }
        }

        mUserData = data;
        mLoggedIn = true;
        return err;
    }

    // Logs the user out, and clears the default user.
    public void logOut(Context ctx) {
        mLoggedIn = false;
        mUserData = null;

        {
            String backendProblem = UserData.clearDefaultLogin(ctx);
            if(backendProblem != null) { Log.e("Failed to clear default login", backendProblem); }
        }
    }

    // Attempts to updates the users password after validation. Returns null on success, or an
    // appropriate error String on failure.
    public String updatePassword(Context ctx, String oldPass, String newPass) {
        if(!mLoggedIn) { return "Invalid login state detected."; }

        if(oldPass == null || newPass == null) {
            return "Update password attempted with null parameters.";
        }
        if(TextUtils.isEmpty(oldPass)) { return "Current Password must be filled in."; }
        if(TextUtils.isEmpty(newPass)) { return "New Password must be filled in."; }

        if(oldPass.length() > MAX_CHARACTERS_IN_INPUT) { return "Current Password is too long (MAX of " + MAX_CHARACTERS_IN_INPUT +" Characters)."; }
        if(newPass.length() > MAX_CHARACTERS_IN_INPUT) { return "New Password is too long (MAX of " + MAX_CHARACTERS_IN_INPUT +" Characters)."; }
        if(oldPass.length() < MIN_PASSWORD_LENGTH) { return "Current Password must be at least length " + MIN_PASSWORD_LENGTH + ".";}
        if(newPass.length() < MIN_PASSWORD_LENGTH) { return "New Password must be at least length " + MIN_PASSWORD_LENGTH + ".";}

        return mUserData.updatePassword(ctx, oldPass, newPass);
    }

    // Attempts to updates the users profile after validation. Returns null on success, or an
    // appropriate error String on failure.
    public String updateProfile(Context ctx, String name, String pass, String email, String phone, String age) {
        if(!mLoggedIn) { return "Invalid login state detected."; }

        if(name == null || pass == null || email == null || phone == null || age == null) {
            return "Update Profile attempted with null parameters.";
        }
        if(TextUtils.isEmpty(name)) { return "Username must be filled in."; }
        if(TextUtils.isEmpty(pass)) { return "Password must be filled in."; }
        if(TextUtils.isEmpty(email)) { return "Password must be filled in twice."; }
        if(TextUtils.isEmpty(email)) { return "Email must be filled in."; }
        if(TextUtils.isEmpty(phone)) { return "Phone must be filled in."; }
        if(TextUtils.isEmpty(age)) { return "Age must be filled in."; }

        if(name.length() > MAX_CHARACTERS_IN_INPUT) { return "Username is too long (MAX of " + MAX_CHARACTERS_IN_INPUT +" Characters)."; }
        if(pass.length() > MAX_CHARACTERS_IN_INPUT) { return "Password is too long (MAX of " + MAX_CHARACTERS_IN_INPUT +" Characters)."; }
        if(email.length() > MAX_CHARACTERS_IN_INPUT) { return "Email is too long (MAX of " + MAX_CHARACTERS_IN_INPUT +" Characters)."; }
        if(phone.length() > MAX_CHARACTERS_IN_INPUT) { return "Phone is too long (MAX of " + MAX_CHARACTERS_IN_INPUT +" Characters)."; }
        if(age.length() > MAX_CHARACTERS_IN_INPUT) { return "Age value is too long (MAX of " + MAX_CHARACTERS_IN_INPUT +" Characters)."; }

        if(pass.length() < MIN_PASSWORD_LENGTH) { return "Password must be at least length " + MIN_PASSWORD_LENGTH + ".";}

        if(!validatePhoneNumber(phone)) { return "Phone number must be valid."; }

        try {
            Integer numericAge = Integer.parseInt(age);
            if(numericAge > MAX_AGE_FOR_USER) { return "Age was too high to be valid."; }
            else if(numericAge < MIN_AGE_FOR_USER) { return "Age was too low to be valid."; }
        } catch(NumberFormatException e) {
            return "Age was invalid.";
        }

        return mUserData.updateProfile(ctx, name, pass, email, phone, age);
    }

    // Internal helper function to validate phone numbers.
    // Note: Just checking if its alpha numeric, as the more specific check wasn't important in HM2.
    protected static boolean validatePhoneNumber(String phone) {
        if(phone.length() < MIN_PHONE_LENGTH ) { return false; }
        return TextUtils.isDigitsOnly(phone.replace(' ', '0').replace('-', '0'));
    }

    // Below are getters for the users account information, they return the empty string if the
    // user is not logged in. Double checked for faulty state protection.
    public String getUsername() {
        if(mUserData == null || !mLoggedIn) return "";
        return mUserData.getUsername();
    }
    public String getEmail() {
        if(mUserData == null || !mLoggedIn) return "";
        return mUserData.getEmail();
    }
    public String getPhone() {
        if(mUserData == null || !mLoggedIn) return "";
        return mUserData.getPhone();
    }
    public int getAge() {
        if(mUserData == null || !mLoggedIn) return -1;
        return mUserData.getAge();
    }
}
