package edgar.yodgorbek.sportnews.adapter;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edgar.yodgorbek.sportnews.R;
import edgar.yodgorbek.sportnews.detail.DetailActivity;
import edgar.yodgorbek.sportnews.model.Article;
import edgar.yodgorbek.sportnews.model.Search;
import edgar.yodgorbek.sportnews.model.SportNews;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.CustomViewHolder> {

    public static final String urlKey = "urlKey";
    List<Article> articles;

    private ClipboardManager myClipboard;
    private ClipData myClip;


    public ArticleAdapter(List<Article> articles, SportNews sportNews) {
        this.articles = articles;


    }

    public ArticleAdapter(ClickListener clickListener) {
    }

    public ArticleAdapter(List<Article> articleList, Search search) {
    }

    @NonNull
    @Override
    public ArticleAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.article_list, null);
        return new ArticleAdapter.CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull edgar.yodgorbek.sportnews.adapter.ArticleAdapter.CustomViewHolder customViewHolder, int position) {
        Article article = articles.get(position);
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy");

        Date d = new Date();
        try {
            d = input.parse(article.getPublishedAt());
        } catch (ParseException e) {
            e.printStackTrace();
        }


        String formatted = output.format(d);
        customViewHolder.articleTime.setText(formatted);
        customViewHolder.articleAuthor.setText(article.getSource().getName());
        customViewHolder.articleTitle.setText(article.getTitle());
        Picasso.get().load(article.getUrlToImage()).into(customViewHolder.articleImage);
        customViewHolder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), DetailActivity.class);

            intent.putExtra("urlKey", article.getUrl());

            v.getContext().startActivity(intent);

        });
        customViewHolder.articleShare.setOnClickListener(v -> {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String articleDescription = article.getDescription();
            String articleTitle = article.getTitle();
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, articleDescription);
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, articleTitle);
            v.getContext().startActivity((Intent.createChooser(sharingIntent, "Share using")));


        });

        customViewHolder.articleFavorite.setOnClickListener(v -> {
            myClipboard = (ClipboardManager) v.getContext().getSystemService(Context.CLIPBOARD_SERVICE);


            myClip = ClipData.newPlainText("label", customViewHolder.articleTitle.getText().toString());
            myClipboard.setPrimaryClip(myClip);
            Toast.makeText(v.getContext(), "Copied to clipboard", Toast.LENGTH_SHORT).show();

        });
    }


    @Override
    public int getItemCount() {
        if (articles == null) return 0;
        return articles.size();
    }

    public interface ClickListener {
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.articleAuthor)
        TextView articleAuthor;
        @BindView(R.id.articleTitle)
        TextView articleTitle;
        @BindView(R.id.articleImage)
        ImageView articleImage;
        @BindView(R.id.articleTime)
        TextView articleTime;
        @BindView(R.id.articleShare)
        ImageButton articleShare;
        @BindView(R.id.articleFavorite)
        ImageButton articleFavorite;

        public CustomViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);


        }
    }


}
