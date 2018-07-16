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

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fjoglar.lyricly.R;
import com.fjoglar.lyricly.data.model.Song;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.SongViewHolder> {

    private final SongClickListener mOnClickListener;

    private List<? extends Song> mSongs;
    private Context mContext;

    SongsAdapter(Context context, @Nullable SongClickListener listener) {
        mContext = context;
        mOnClickListener = listener;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_song, parent, false);
        return new SongViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        // TODO: bind data to views.
        String albumId = mSongs.get(position).getAlbumId();
        String imageSize = "500x500";
        String imageUrl = mContext.getString(R.string.album_image_url, albumId, imageSize);

        Picasso.get()
                .load(imageUrl)
                .placeholder(R.color.colorSecondaryLight)
                .error(R.color.colorPrimaryLight)
                .into(holder.imageViewAlbumCover);
    }

    @Override
    public int getItemCount() {
        return mSongs == null ? 0 : mSongs.size();
    }

    public void setSongs(final List<? extends Song> songs) {
        if (mSongs == null) {
            mSongs = songs;
            notifyItemRangeInserted(0, songs.size());
        } else {
            SongsDiffCallback songsDiffCallback = new SongsDiffCallback(mSongs, songs);
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(songsDiffCallback);
            mSongs = songs;
            result.dispatchUpdatesTo(this);
        }
    }

    public List<? extends Song> getSongs() {
        return mSongs;
    }

    public class SongViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.imageview_album_cover)
        ImageView imageViewAlbumCover;

        SongViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnClickListener.onSongClick(mSongs.get(getAdapterPosition()));
        }
    }

    public interface SongClickListener {
        void onSongClick(Song song);
    }
}