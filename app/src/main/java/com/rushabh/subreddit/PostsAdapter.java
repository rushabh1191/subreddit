package com.rushabh.subreddit;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rushabh.subreddit.models.Children;
import com.rushabh.subreddit.models.SubredditPost;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rushabh on 06/11/16.
 */

public class PostsAdapter extends RecyclerView.Adapter<PostViewHolder> {

    Context context;
    ArrayList<Children> listOfPosts;
    LayoutInflater inflater;

    View.OnClickListener postClickListener;

    public PostsAdapter(Context context, ArrayList<Children> listOfPosts, View.OnClickListener
            onClickListener) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listOfPosts = listOfPosts;
        this.postClickListener=onClickListener;

    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.crow_posts, parent, false);
        PostViewHolder postViewHolder = new PostViewHolder(view);
        view.setOnClickListener(postClickListener);
        view.setTag(postViewHolder);
        return postViewHolder;
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        SubredditPost post=listOfPosts.get(position).post;
        holder.tvPostTitle.setText(post.title);
        holder.id=post.id;
        holder.tvTime.setText(Utility.getTime(post.createdAt*1000));
        if(!TextUtils.isEmpty(post.thumbnail)){
            Picasso.with(context).load(post.thumbnail).into(holder.ivThumbImage);
        }
        else{
            holder.ivThumbImage.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {

        if (listOfPosts == null) {
            return 0;
        }
        return listOfPosts.size();

    }
}

class PostViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_post_title)
    TextView tvPostTitle;
    @BindView(R.id.iv_thumb_image)
    ImageView ivThumbImage;
    @BindView(R.id.tv_time)
    TextView tvTime;

    String id;

    public PostViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}