package com.example.taskify.common;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.taskify.R;


public class NavHost {
    public static void navigateTo(Context context, Class<?> targetActivity) {
        Intent intent = new Intent(context, targetActivity);
        context.startActivity(intent);
    }

    public static Intent navigate(Context context, Class<?> targetActivity) {
        return new Intent(context, targetActivity);
    }


}


