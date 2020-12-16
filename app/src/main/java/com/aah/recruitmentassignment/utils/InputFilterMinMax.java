package com.aah.recruitmentassignment.utils;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputFilterMinMax implements InputFilter {

    private double min, max;
    Pattern mPattern;

    public InputFilterMinMax(float min, float max) {
        this.min = Double.parseDouble(String.valueOf(min));
        this.max = Double.parseDouble(String.valueOf(max));
    }

    public InputFilterMinMax(int min, int max) {
        this.min = Double.parseDouble(String.valueOf(min));
        this.max = Double.parseDouble(String.valueOf(max));
    }

    public InputFilterMinMax(String min, String max) {
        this.min = Double.parseDouble(min);
        this.max = Double.parseDouble(max);
    }

    public InputFilterMinMax(String min, String max, int digitsBeforeZero,int digitsAfterZero) {
        this.min = Double.parseDouble(min);
        this.max = Double.parseDouble(max);
        mPattern= Pattern.compile("[0-9]{0," + (digitsBeforeZero-1) + "}+((\\.[0-9]{0," + (digitsAfterZero-1) + "})?)||(\\.)?");
    }

    public InputFilterMinMax(double min, double max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {
            if(mPattern!= null){
                Matcher matcher=mPattern.matcher(dest);
                if(!matcher.matches())
                    return "";
            }

            String newVal = dest.toString().substring(0, dstart) + dest.toString().substring(dend, dest.toString().length());
            newVal = newVal.substring(0, dstart) + source.toString() + newVal.substring(dstart, newVal.length());

            double input = Double.parseDouble(newVal);
            if (isInRange(min, max, input))
                return null;

        } catch (NumberFormatException nfe) { }
        return "";
    }

    private boolean isInRange(double a, double b, double c) {
        return b > a ? c >= a && c <= b : c >= b && c <= a;
    }
}
