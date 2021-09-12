package com.example.nytimes.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nytimes.ArticleDetails;
import com.example.nytimes.R;
import com.example.nytimes.model.Article;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.MyViewHolder> {

    private final Context ctx;
    private final List<Article> articles;

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        public final TextView title, author, published;
        public final ImageView image;
        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            author = view.findViewById(R.id.author);
            image = view.findViewById(R.id.image);
            published = view.findViewById(R.id.time);
        }
    }

    public ArticlesAdapter(Context ctx, List<Article> articles) {
        this.ctx = ctx;
        this.articles = articles;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NotNull final MyViewHolder holder, final int position) {
        Article article = articles.get(position);
        holder.title.setText(article.getTitle());
        holder.author.setText(article.getAuthor());
        holder.published.setText(article.getPublished());
        Intent intent = new Intent(ctx, ArticleDetails.class);
        intent.putExtra("article", article);
        holder.itemView.setOnClickListener(v->ctx.startActivity(intent));
        if(article.getMedia().size() > 0) {
            Glide.with(ctx).load(article.getMedia().get(0).getUrl()).circleCrop().into(holder.image);
        }
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

}