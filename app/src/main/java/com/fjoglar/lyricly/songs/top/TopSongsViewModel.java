/*
 * Copyright 2018 Felipe Joglar Santos
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

package com.fjoglar.lyricly.songs.top;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.Nullable;

import com.fjoglar.lyricly.data.SongsRepository;
import com.fjoglar.lyricly.data.source.local.entity.TopSongEntity;

import java.util.List;

public class TopSongsViewModel extends ViewModel {

    private SongsRepository mSongsRepository;
    private LiveData<List<TopSongEntity>> mTopSongs;

    TopSongsViewModel(@Nullable SongsRepository songsRepository) {
        if (mSongsRepository != null) {
            // ViewModel is created per Activity
            return;
        }
        if (songsRepository != null) {
            mSongsRepository = songsRepository;
        }
    }

    public LiveData<List<TopSongEntity>> getTopSongs() {
        if (mTopSongs == null) {
            mTopSongs = loadSongs();
        }
        return mTopSongs;
    }

    private LiveData<List<TopSongEntity>> loadSongs() {
        return mSongsRepository.getTopSongs();
    }

    static class Factory extends ViewModelProvider.NewInstanceFactory {

        private SongsRepository mSongsRepository;

        Factory(SongsRepository songsRepository) {
            mSongsRepository = songsRepository;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new TopSongsViewModel(mSongsRepository);
        }
    }
}