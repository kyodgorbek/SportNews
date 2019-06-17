package edgar.yodgorbek.yangiliklar.adapter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import com.squareup.picasso.Picasso

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

import butterknife.BindView
import butterknife.ButterKnife
import edgar.yodgorbek.yangiliklar.R
import edgar.yodgorbek.yangiliklar.adapter.EspnAdapter.CustomViewHolder
import edgar.yodgorbek.yangiliklar.detail.DetailActivity
import edgar.yodgorbek.yangiliklar.model.Article
import edgar.yodgorbek.yangiliklar.model.Espn

class EspnAdapter(var articles: List<Article>?, espnNews: Espn) : RecyclerView.Adapter<CustomViewHolder>() {
    internal var espnNews: Espn? = null

    private var myClipboard: ClipboardManager? = null
    private var myClip: ClipData? = null


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CustomViewHolder {
        val itemView = LayoutInflater.from(viewGroup.context).inflate(R.layout.footballitalia_list, null)
        return CustomViewHolder(itemView)
    }

    override fun onBindViewHolder(customViewHolder: CustomViewHolder, position: Int) {
        val article = articles!![position]
        val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val output = SimpleDateFormat("dd/MM/yyyy")

        var d = Date()
        try {
            d = input.parse(article.publishedAt)
        } catch (e: ParseException) {
            e.printStackTrace()
        }


        val formatted = output.format(d)
        customViewHolder.articleTime!!.text = formatted
        customViewHolder.articleAuthor!!.text = article.source.name
        customViewHolder.articleTitle!!.text = article.title
        Picasso.get().load(article.urlToImage).into(customViewHolder.articleImage)
        customViewHolder.itemView.setOnClickListener { v ->
            val intent = Intent(v.context, DetailActivity::class.java)

            intent.putExtra("urlKey", article.url)

            v.context.startActivity(intent)

        }
        customViewHolder.articleShare!!.setOnClickListener { v ->
            val sharingIntent = Intent(android.content.Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            val articleDescription = article.description
            val articleTitle = article.title
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, articleDescription)
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, articleTitle)
            v.context.startActivity(Intent.createChooser(sharingIntent, "Share using"))


        }

        customViewHolder.articleFavorite!!.setOnClickListener { v ->
            myClipboard = v.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager


            myClip = ClipData.newPlainText("label", customViewHolder.articleTitle!!.text.toString())
            myClipboard!!.primaryClip = myClip!!
            Toast.makeText(v.context, "Copied to clipboard", Toast.LENGTH_SHORT).show()

        }
    }


    override fun getItemCount(): Int {
        return if (articles == null) 0 else articles!!.size
    }

    inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        @BindView(R.id.articleAuthor)
        internal var articleAuthor: TextView? = null
        @BindView(R.id.articleTitle)
        internal var articleTitle: TextView? = null
        @BindView(R.id.articleImage)
        internal var articleImage: ImageView? = null
        @BindView(R.id.articleTime)
        internal var articleTime: TextView? = null
        @BindView(R.id.articleShare)
        internal var articleShare: ImageButton? = null
        @BindView(R.id.articleFavorite)
        internal var articleFavorite: ImageButton? = null

        init {
            ButterKnife.bind(this, view)


        }
    }

    companion object {

        val urlKey = "urlKey"
    }

}

