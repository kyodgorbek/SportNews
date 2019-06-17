package edgar.yodgorbek.yangiliklar.detail


import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.webkit.WebView
import android.webkit.WebViewClient

import java.util.Objects

import butterknife.BindView
import butterknife.ButterKnife
import edgar.yodgorbek.yangiliklar.R

class DetailActivity : AppCompatActivity() {


    @BindView(R.id.toolbar)
    internal var toolbar: Toolbar? = null

    @BindView(R.id.webView)
    internal var webView: WebView? = null

    @SuppressLint("SetJavaScriptEnabled", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        ButterKnife.bind(this)
        setSupportActionBar(toolbar)
        Objects.requireNonNull<ActionBar>(supportActionBar).setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        toolbar!!.setBackgroundResource(R.color.colorBlue)
        toolbar!!.setNavigationIcon(R.drawable.ic_arrow_white)

        webView!!.settings.javaScriptEnabled = true
        val url = intent.extras!!.getString("urlKey")


        webView!!.webViewClient = WebViewClient()

        webView!!.loadUrl(url)


    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    inner class WebViewController : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }
    }


}

