# FitterNumberPicker

[![Build Status](https://travis-ci.org/Commit451/FitterNumberPicker.svg?branch=master)](https://travis-ci.org/Commit451/FitterNumberPicker) [![](https://jitpack.io/v/Commit451/FitterNumberPicker.svg)](https://jitpack.io/#Commit451/FitterNumberPicker)

## Usage

You can either define your `MaterialNumberPicker` via XML or programmatically :

```xml
<com.commit451.fitternumberpicker.FitterNumberPicker
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:fnpMinValue="1"
        app:fnpMaxValue="50"
        app:fnpDefaultValue="10"
        app:fnpBackgroundColor="@color/colorAccent"
        app:fnpSeparatorColor="@color/colorAccent"
        app:fnpTextColor="@color/colorPrimary"
        app:fnpTextSize="25sp"/>
```

```java
FitterNumberPicker numberPicker = new FitterNumberPicker.Builder(context)
                .minValue(1)
                .maxValue(10)
                .defaultValue(1)
                .backgroundColor(Color.WHITE)
                .separatorColor(Color.TRANSPARENT)
                .textColor(Color.BLACK)
                .textSize(20)
                .enableFocusability(false)
                .wrapSelectorWheel(true)
                .build();
```

The latter option only builds the picker for you. It is up to you how you want to display the picker. You can as well insert it in any `ViewGroup` or inside an `AlertDialog` as a custom view.

````java
new AlertDialog.Builder(this)
                .setTitle(yourTitle)
                .setView(numberPicker)
                .setPositiveButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Snackbar.make(findViewById(R.id.your_container), "You picked : " + numberPicker.getValue(), Snackbar.LENGTH_LONG).show();
                    }
                })
                .show();
```

By default there is no `NumberPicker.Formatter` when you build your `MaterialNumberPicker` but you can easily attach one to it using the `formatter` builder proprety.

# Acknowledgements
Forked from [MaterialNumberPicker](https://github.com/KasualBusiness/MaterialNumberPicker).

License
--------

    Copyright 2017 Commit 451

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
