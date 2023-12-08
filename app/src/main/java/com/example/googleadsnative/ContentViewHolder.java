package com.example.googleadsnative;

import android.net.Uri;
import android.util.Log;
import android.view.View;

import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.RawResourceDataSource;
import com.google.android.exoplayer2.MediaItem;

public class ContentViewHolder extends FeedViewHolder {
    private static final String TAG = "";
    private final PlayerView playerView;
    private final SimpleExoPlayer player;

    public ContentViewHolder(View itemView) {
        super(itemView);
        // Define click listener for the ContentViewHolder's View.
        itemView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d(TAG, "Element " + getAbsoluteAdapterPosition() + " clicked.");
                    }
                });

        player = new SimpleExoPlayer.Builder(itemView.getContext()).build();
        player.setRepeatMode(Player.REPEAT_MODE_ALL);
        playerView = (PlayerView) itemView.findViewById(R.id.player_view);
        playerView.setPlayer(player);
        playerView.setUseController(false);
        player.prepare();
        itemView
                .findViewById(R.id.overlay)
                .setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Log.d(TAG, "Overlay clicked.");
                                if (player.isPlaying()) {
                                    player.pause();
                                } else {
                                    player.play();
                                }
                            }
                        });
    }
    /** Gets a reference to the Player instance. */
    public Player getPlayer() {
        return player;
    }

    /** Gets a reference to the PlayerView instance. */
    public PlayerView getPlayerView() {
        return playerView;
    }

    /** Expecting the id of raw resource video file. */
    public void bind(FeedAdapter.ContentFeedItem contentFeedItem, int position) {
        Player player = this.getPlayer();
        Uri uri = RawResourceDataSource.buildRawResourceUri(contentFeedItem.getResourceId());
        player.setMediaItem(MediaItem.fromUri(uri));
    }

    /** Starts playing the video when the ViewHolder is visible. */
    @Override
    public void attach() {
        getPlayer().prepare();
        getPlayer().play();
        Log.d(TAG, "Playing " + getLayoutPosition());
    }

    /** Stops playing the video when the ViewHolder is no longer visible. */
    @Override
    public void detach() {
        getPlayer().stop();
        Log.d(TAG, "Stopping # " + getLayoutPosition());
    }
}