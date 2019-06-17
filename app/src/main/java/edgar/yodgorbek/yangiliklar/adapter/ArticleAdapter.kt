package edgar.yodgorbek.yangiliklar.adapter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.squareup.picasso.Picasso
import edgar.yodgorbek.yangiliklar.R
import edgar.yodgorbek.yangiliklar.adapter.ArticleAdapter.CustomViewHolder
import edgar.yodgorbek.yangiliklar.detail.DetailActivity
import edgar.yodgorbek.yangiliklar.model.Article
import edgar.yodgorbek.yangiliklar.model.Search
import edgar.yodgorbek.yangiliklar.model.SportNews
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ArticleAdapter: RecyclerView.Adapter<CustomViewHolder> {


    internal lateinit var articles:List<Article>
    private var myClipboard: ClipboardManager? = null
    private var myClip: ClipData? = null

    constructor(articles:List<Article>) {
        this.articles = articles
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull viewGroup: ViewGroup, i:Int): CustomViewHolder {
        val itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.article_list, null)
        return CustomViewHolder(itemView)
    }
    override fun onBindViewHolder(@NonNull customViewHolder: CustomViewHolder, position:Int) {
        val article = articles.get(position)
        val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        val output = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        var d = Date()
        try
        {
            d = input.parse(article.publishedAt)
        }
        catch (e: ParseException) {
            e.printStackTrace()
        }
        val formatted = output.format(d)
        customViewHolder.articleTime.setText(formatted)
        customViewHolder.articleAuthor.setText(article.source.name)
        customViewHolder.articleTitle.setText(article.title)
        Picasso.get().load(article.urlToImage).into(customViewHolder.articleImage)
        customViewHolder.itemView.setOnClickListener({ v->
            val intent = Intent(v.getContext(), DetailActivity::class.java)
            intent.putExtra("urlKey", article.url)
            v.getContext().startActivity(intent)
        })
        customViewHolder.articleShare.setOnClickListener({ v->
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.setType("text/plain")
            val articleDescription = article.description
            val articleTitle = article.title
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, articleDescription)
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, articleTitle)
            v.getContext().startActivity((Intent.createChooser(sharingIntent, "Share using")))
        })
        customViewHolder.articleFavorite.setOnClickListener { v->
            myClipboard = v.getContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            myClip = ClipData.newPlainText("label", customViewHolder.articleTitle.getText().toString())
            myClipboard!!.setPrimaryClip(myClip)
            Toast.makeText(v.getContext(), "Copied to clipboard", Toast.LENGTH_SHORT).show()
        }
    }
    override fun getItemCount(): Int {
        if (articles == null) return 0
        return articles.size
    }

    interface ClickListener
    inner class CustomViewHolder(view: View):RecyclerView.ViewHolder(view) {
        @BindView(R.id.articleAuthor)
         lateinit var articleAuthor: TextView
        @BindView(R.id.articleTitle)
         lateinit var articleTitle:TextView
        @BindView(R.id.articleImage)
         lateinit var articleImage: ImageView
        @BindView(R.id.articleTime)
         lateinit var articleTime:TextView
        @BindView(R.id.articleShare)
         lateinit var articleShare: ImageButton
        @BindView(R.id.articleFavorite)
        internal lateinit var articleFavorite:ImageButton
        init{
            ButterKnife.bind(this, view)
        }
    }
    companion object {
        val urlKey = "urlKey"
    }
}












