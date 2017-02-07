/*
 * Copyright (C) 2015 Kasual Business
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.commit451.fitternumberpicker;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.ColorInt;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;

import java.lang.reflect.Field;

/**
 * Stock {@link NumberPicker} with hooks for customization
 */
public class FitterNumberPicker extends NumberPicker {

    static int[] ANDROID_ATTRS = new int[] {
            android.R.attr.textSize,
            android.R.attr.textColor
    };

    static int INDEX_OF_TEXT_SIZE = 0;
    static int INDEX_OF_TEXT_COLOR = 1;

    private static float pixelsToSp(Context context, float px) {
        return px / context.getResources().getDisplayMetrics().scaledDensity;
    }

    private int mTextColor;
    private float mTextSize;
    private int mSeparatorColor;
    private boolean mEnableFocusability;

    private Paint mSelectorWheelPaint;

    //Cache fields since reflection is kinda slow
    private Field mPickerDividerField;
    private Field mMaximumFlingVelocityField;

    public FitterNumberPicker(Context context) {
        super(context);
        initView();
    }

    public FitterNumberPicker(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView();

        //Parse Android attributes
        TypedArray android = context.obtainStyledAttributes(attributeSet, ANDROID_ATTRS);

        if (android.hasValue(INDEX_OF_TEXT_SIZE)) {
            float size = android.getDimensionPixelSize(INDEX_OF_TEXT_SIZE, 0);
            setTextSize(size);
        }
        if (android.hasValue(INDEX_OF_TEXT_COLOR)) {
            //noinspection ResourceType
            setTextColor(android.getColor(INDEX_OF_TEXT_COLOR, Color.BLACK));
        }

        android.recycle();

        //Parse custom attributes
        TypedArray a = context.obtainStyledAttributes(attributeSet, R.styleable.FitterNumberPicker);

        if (a.hasValue(R.styleable.FitterNumberPicker_fnp_minValue)) {
            setMinValue(a.getInt(R.styleable.FitterNumberPicker_fnp_minValue, 0));
        }
        if (a.hasValue(R.styleable.FitterNumberPicker_fnp_maxValue)) {
            setMaxValue(a.getInt(R.styleable.FitterNumberPicker_fnp_maxValue, 0));
        }
        if (a.hasValue(R.styleable.FitterNumberPicker_fnp_value)) {
            setValue(a.getInt(R.styleable.FitterNumberPicker_fnp_value, 0));
        }
        if (a.hasValue(R.styleable.FitterNumberPicker_fnp_separatorColor)) {
            setSeparatorColor(a.getColor(R.styleable.FitterNumberPicker_fnp_separatorColor, Color.TRANSPARENT));
        }
        if (a.hasValue(R.styleable.FitterNumberPicker_fnp_focusability)) {
            setFocusability(a.getBoolean(R.styleable.FitterNumberPicker_fnp_focusability, true));
        }
        if (a.hasValue(R.styleable.FitterNumberPicker_fnp_wrapSelectorWheel)) {
            setWrapSelectorWheel(a.getBoolean(R.styleable.FitterNumberPicker_fnp_wrapSelectorWheel, true));
        }

        a.recycle();
    }

    /**
     * Init number picker by disabling focusability of edit text embedded inside the number picker
     * We also override the edit text filter private attribute by using reflection as the formatter is still buggy while attempting to display the default value
     * This is still an open Google @see <a href="https://code.google.com/p/android/issues/detail?id=35482#c9">issue</a> from 2012
     */
    private void initView() {

        try {
            Field f = NumberPicker.class.getDeclaredField("mInputText");
            f.setAccessible(true);
            EditText inputText = (EditText) f.get(this);
            inputText.setFilters(new InputFilter[0]);
        } catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException e) {
            //We do not really want to display the errors, since we are a lib
        }
    }

    @ColorInt
    public int getTextColor() {
        return mTextColor;
    }

    @ColorInt
    public int getSeparatorColor() {
        return mSeparatorColor;
    }

    public boolean isFocusabilityEnabled() {
        return mEnableFocusability;
    }

    /**
     * Uses reflection to access divider private attribute and override its color
     * Use Color.Transparent if you wish to hide them
     *
     * @return true if separator set, false if field was not accessible
     */
    public boolean setSeparatorColor(int separatorColor) {
        mSeparatorColor = separatorColor;
        if (mPickerDividerField == null) {
            Field[] pickerFields = NumberPicker.class.getDeclaredFields();
            for (Field pf : pickerFields) {
                if (pf.getName().equals("mSelectionDivider")) {
                    pf.setAccessible(true);
                    mPickerDividerField = pf;
                    break;
                }
            }
        }
        if (mPickerDividerField != null) {
            try {
                mPickerDividerField.set(this, new ColorDrawable(separatorColor));
            } catch (IllegalAccessException | IllegalArgumentException e) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    /**
     * Uses reflection to access text color private attribute for both wheel and edit text inside the number picker.
     */
    public void setTextColor(int textColor) {
        mTextColor = textColor;
        updateTextAttributes();
    }

    /**
     * Uses reflection to access text size private attribute for both wheel and edit text inside the number picker.
     * @param textSize text size in pixels
     */
    public void setTextSize(float textSize) {
        mTextSize = textSize;
        updateTextAttributes();
    }

    public void setFocusability(boolean isFocusable) {
        mEnableFocusability = isFocusable;
        setDescendantFocusability(isFocusable ? FOCUS_AFTER_DESCENDANTS : FOCUS_BLOCK_DESCENDANTS);
    }

    /**
     * Set the maximum fling velocity. This basically makes it where items scroll faster.
     *
     * @param maximumFlingVelocity the maximum fling velocity
     * @return true if it worked, false otherwise
     */
    public boolean setMaximumFlingVelocity(int maximumFlingVelocity) {
        if (mMaximumFlingVelocityField == null) {
            try {
                Field field = NumberPicker.class.getDeclaredField("mMaximumFlingVelocity");
                field.setAccessible(true);
                mMaximumFlingVelocityField = field;
            } catch (Exception e) {
                return false;
            }
        }
        try {
            mMaximumFlingVelocityField.set(this, maximumFlingVelocity);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private void updateTextAttributes() {

        updateSelectorWheelPaint();

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child instanceof EditText) {
                EditText editText = ((EditText) child);
                editText.setTextColor(mTextColor);
                editText.setTextSize(pixelsToSp(getContext(), mTextSize));
                editText.invalidate();
            }
        }
    }

    private boolean updateSelectorWheelPaint() {
        if (mSelectorWheelPaint == null) {
            try {
                Field selectorWheelPaintField = NumberPicker.class.getDeclaredField("mSelectorWheelPaint");
                selectorWheelPaintField.setAccessible(true);

                mSelectorWheelPaint = ((Paint) selectorWheelPaintField.get(this));
            } catch (Exception e) {
                return false;
            }

        }
        if (mSelectorWheelPaint != null) {
            mSelectorWheelPaint.setColor(mTextColor);
            mSelectorWheelPaint.setTextSize(mTextSize);
            invalidate();
            return true;
        }
        return false;
    }
}
