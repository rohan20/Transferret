package com.example.rohan.transferret;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Rohan on 03-Nov-15.
 */
public class CustomSnackbarPopUp extends CoordinatorLayout.Behavior<LinearLayout>
{
    public CustomSnackbarPopUp(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, LinearLayout child, View dependency) {
        return dependency instanceof Snackbar.SnackbarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, LinearLayout child, View dependency) {
        float translationY = Math.min(0, dependency.getTranslationY() - dependency.getHeight());
//        float translationY = Math.min(0, -131);
        child.setTranslationY(translationY);
        return true;
    }
}
