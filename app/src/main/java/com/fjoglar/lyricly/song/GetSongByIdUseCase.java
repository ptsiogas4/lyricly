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

package com.fjoglar.lyricly.song;

import com.fjoglar.lyricly.data.SongsRepository;
import com.fjoglar.lyricly.data.model.Song;
import com.fjoglar.lyricly.util.usecases.SingleUseCase;

import io.reactivex.Single;

public class GetSongByIdUseCase implements SingleUseCase<Integer, Song> {

    @Override
    public Single<Song> execute(SongsRepository repository, Integer parameter) {
        return repository.getSongById(parameter);
    }
}
