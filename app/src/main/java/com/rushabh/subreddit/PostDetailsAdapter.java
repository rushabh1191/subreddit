package com.rushabh.subreddit;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rushabh.subreddit.models.Children;
import com.rushabh.subreddit.models.ImageObject;
import com.rushabh.subreddit.models.SubredditPost;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rushabh on 06/11/16.
 */

public class PostDetailsAdapter extends RecyclerView.Adapter {

    ArrayList<Children> listOfPosts;
    LayoutInflater inflater;
    Context context;

    public PostDetailsAdapter(ArrayList<Children> listOfPosts, Context context) {

        this.listOfPosts = listOfPosts;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    class SubredditView extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_post_title)
        TextView tvPostTitle;
        @BindView(R.id.iv_thumb_image)
        ImageView ivThumbImage;
        @BindView(R.id.tv_time)
        TextView tvTime;

        @BindView(R.id.tv_author)
        TextView tvAuthor;

        public SubredditView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class PostView extends SubredditView {

        @BindView(R.id.rv_image_swiper)
        RecyclerView recyclerView;


        public PostView(View itemView) {
            super(itemView);
        }
    }

    class CommentsView extends SubredditView {

        public CommentsView(View itemView) {
            super(itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (listOfPosts.get(position).post.isMainPost) {
            return R.layout.crow_post_details;
        } else {

            return R.layout.crow_comments;
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(viewType, parent, false);
        RecyclerView.ViewHolder holder;
        if (viewType == R.layout.crow_post_details) {
            holder = new PostView(view);
        } else {
            holder = new CommentsView(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        SubredditPost post = listOfPosts.get(position).post;

        SubredditView subredditView;
        if (post.isMainPost) {
            subredditView = (PostView) holder;
            subredditView.tvPostTitle.setText(post.title);

            RecyclerView recyclerView = ((PostView) subredditView).recyclerView;
            try {
                if (post.preview.imagesInformations.size() == 0) {


                    recyclerView.setVisibility(View.GONE);
                } else {

                    recyclerView.setVisibility(View.VISIBLE);
                }
                PostImageAdapter adapter = new PostImageAdapter(context, post.
                        preview.imagesInformations, inflater);

                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(context,
                        LinearLayoutManager.HORIZONTAL, false));
            } catch (Exception e) {
                recyclerView.setVisibility(View.GONE);
            }


        } else {
            subredditView = (CommentsView) holder;
            subredditView.tvPostTitle.setText(post.body);

        }
        subredditView.tvTime.setText(Utility.getTime(post.createdAt * 1000));
        subredditView.tvAuthor.setText(post.author);

        if(!TextUtils.isEmpty(post.thumbnail)){
            Picasso.with(context).load(post.thumbnail).into(subredditView.ivThumbImage);
        }
        else{
            subredditView.ivThumbImage.setVisibility(View.INVISIBLE);
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

class PostImageAdapter extends RecyclerView.Adapter {

    ArrayList<ImageObject> list;
    LayoutInflater inflater;
    Context context;

    PostImageAdapter(Context context, ArrayList<ImageObject> list, LayoutInflater inflater) {
        this.list = list;
        this.inflater = inflater;
        this.context = context;
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_image)
        ImageView ivImage;


        public ImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.crow_image, parent, false);
        ImageViewHolder holder = new ImageViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ImageViewHolder ivHolder = (ImageViewHolder) holder;
        ImageObject information = list.get(position);
        Picasso.with(context).load(information.source.url).into(ivHolder.ivImage);

    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }
}

