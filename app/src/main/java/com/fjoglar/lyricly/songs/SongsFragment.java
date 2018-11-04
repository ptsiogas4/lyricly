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

package com.fjoglar.lyricly.songs;

import android.arch.lifecycle.Lifecycle;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fjoglar.lyricly.R;
import com.fjoglar.lyricly.data.model.Song;
import com.fjoglar.lyricly.util.Injection;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class SongsFragment extends Fragment {

    private SongsAdapter mSongsAdapter;

    private GridLayoutManager mLayoutManager;

    @BindView(R.id.recyclerview_songs)
    RecyclerView mRecyclerViewSongs;
    @BindView(R.id.progressbar_songs_loading)
    ProgressBar mProgressBarSongsLoading;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_songs, container, false);
        ButterKnife.bind(this, root);

        setUpRecyclerView();

        SongsViewModelFactory songsViewModelFactory =
                Injection.provideSongsViewModelFactory(getActivity());
        initViewModel(songsViewModelFactory);

        subscribeUi();

        return root;
    }

    public void goToTop() {
        RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(getActivity()) {
            @Override
            protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }
        };

        smoothScroller.setTargetPosition(0);
        mLayoutManager.startSmoothScroll(smoothScroller);
    }

    private void setUpRecyclerView() {
        mLayoutManager = new GridLayoutManager(getActivity(),
                this.getResources().getInteger(R.integer.songs_activity_column_number));
        mSongsAdapter = new SongsAdapter(getActivity(), mSongClickCallback);

        mRecyclerViewSongs.setLayoutManager(mLayoutManager);
        mRecyclerViewSongs.setHasFixedSize(true);
        mRecyclerViewSongs.setAdapter(mSongsAdapter);
    }

    private void renderLoadingState() {
        mRecyclerViewSongs.setVisibility(View.GONE);
        mProgressBarSongsLoading.setVisibility(View.VISIBLE);
    }

    private void renderDataState(List<Song> songs) {
        mProgressBarSongsLoading.setVisibility(View.GONE);
        mRecyclerViewSongs.setVisibility(View.VISIBLE);
        mSongsAdapter.setSongs(songs);
    }

    private void renderErrorState(Throwable throwable) {
        mProgressBarSongsLoading.setVisibility(View.GONE);
        mRecyclerViewSongs.setVisibility(View.GONE);
        Toast.makeText(getActivity(), throwable.toString(), Toast.LENGTH_SHORT).show();
    }

    private final SongClickCallback mSongClickCallback = song -> {

        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            ((SongsActivity) getActivity()).show(song);
        }
    };

    protected void showSongs(SongsResponse songsResponse) {
        switch (songsResponse.status) {
            case LOADING:
                renderLoadingState();
                break;
            case SUCCESS:
                renderDataState(songsResponse.data);
                break;
            case ERROR:
                renderErrorState(songsResponse.error);
                break;
        }
    }

    protected abstract void initViewModel(SongsViewModelFactory songsViewModelFactory);

    protected abstract void subscribeUi();
}