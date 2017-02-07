# FitterNumberPicker

[![Build Status](https://travis-ci.org/Commit451/FitterNumberPicker.svg?branch=master)](https://travis-ci.org/Commit451/FitterNumberPicker) [![](https://jitpack.io/v/Commit451/FitterNumberPicker.svg)](https://jitpack.io/#Commit451/FitterNumberPicker)

## Usage

To easily use in XML:

```xml
<com.commit451.fitternumberpicker.FitterNumberPicker
    android:id="@+id/custom_number_picker"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_gravity="center_horizontal"
    android:textSize="22sp"
    android:textColor="@color/colorPrimary"
    app:fnp_focusability="false"
    app:fnp_minValue="1"
    app:fnp_maxValue="10"
    app:fnp_value="5"
    app:fnp_wrapSelectorWheel="false"
    app:fnp_separatorColor="@android:color/transparent" />
```

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
