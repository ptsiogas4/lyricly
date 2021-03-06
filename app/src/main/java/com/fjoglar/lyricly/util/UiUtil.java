/*
 * Copyright 2019 Felipe Joglar Santos
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

package com.fjoglar.lyricly.util;

import com.fjoglar.lyricly.R;

public class UiUtil {

    public static int getPlaceHolderColor() {
        double random = Math.random();
        if (random < 0.33) {
            return R.color.colorSecondaryUltraLight;
        } else if (random > 0.66) {
            return R.color.colorSecondaryLight;
        }
        return R.color.colorSecondaryExtraLight;
    }
}
