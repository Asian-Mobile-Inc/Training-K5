package com.example.asian.issue8.ViewModel;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.asian.issue8.Model.User;
import com.example.asian.issue8.Model.UserDatabase;

import java.util.List;

public class UserViewModel {
    @SuppressLint("StaticFieldLeak")
    private static UserViewModel instance;
    private final Context context;

    public static UserViewModel getInstance(Context context) {
        if (instance == null) {
            instance = new UserViewModel(context);
        }
        return instance;
    }

    private UserViewModel(Context context) {
        this.context = context;
    }

    public User getUser(int id) {
        return UserDatabase.getInstance(context).getUser(id);
    }

    public List<User> getAllUser() {
        return UserDatabase.getInstance(context).getAllUser();
    }

    public void insertUser(User user) {
        UserDatabase.getInstance(context).insertUser(user);
    }

    public void updateUser(User user) {
        UserDatabase.getInstance(context).updateUser(user);
    }

    public void deleteUser(int id) {
        UserDatabase.getInstance(context).deleteUser(id);
    }

    public void deleteAllUser() {
        UserDatabase.getInstance(context).deleteAllUser();
    }
}
