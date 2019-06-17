package edgar.yodgorbek.yangiliklar.sportactivities


import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import java.util.ArrayList

import butterknife.BindView
import butterknife.ButterKnife
import edgar.yodgorbek.yangiliklar.R
import edgar.yodgorbek.yangiliklar.adapter.ArticleAdapter
import edgar.yodgorbek.yangiliklar.component.ApplicationComponent
import edgar.yodgorbek.yangiliklar.component.BBCSportFragmentComponent
import edgar.yodgorbek.yangiliklar.internet.SportClient
import edgar.yodgorbek.yangiliklar.model.Article
import edgar.yodgorbek.yangiliklar.model.Search
import edgar.yodgorbek.yangiliklar.model.SportInterface
import edgar.yodgorbek.yangiliklar.model.SportNews
import edgar.yodgorbek.yangiliklar.module.BBCFragmentContextModule
import edgar.yodgorbek.yangiliklar.qualifier.ActivityContext
import edgar.yodgorbek.yangiliklar.qualifier.ApplicationContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BBCSportFragment : Fragment(), ArticleAdapter.ClickListener {

    var articleList: MutableList<Article> = ArrayList()


    @ActivityContext
    var activityContext: Context? = null
    @ApplicationContext
    var mContext: Context? = null
    @BindView(R.id.recycler_view)
    internal var recyclerView: RecyclerView? = null
    internal var bbcSportFragmentComponent: BBCSportFragmentComponent? = null
    internal var bbcFragmentContextModule: BBCFragmentContextModule? = null
    private var search: Search? = null
    private var sportNews: SportNews? = null
    private var articleAdapter: ArticleAdapter? = null
    private var apiInterface: SportInterface? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_bbcsport, container, false)
        ButterKnife.bind(this, view)

        articleAdapter = ArticleAdapter(articleList, sportNews!!)
        val layoutManager = LinearLayoutManager(context)
        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.adapter = articleAdapter

        apiInterface = SportClient.apiService

        fetchInitialArticles()

        return view
    }

    private fun fetchInitialArticles() {
        val progress = ProgressDialog(context)
        progress.setMessage("Loading... ")
        progress.isIndeterminate = true
        progress.show()
        val call = apiInterface!!.articles
        call.enqueue(object : Callback<SportNews> {
            override fun onResponse(call: Call<SportNews>, response: Response<SportNews>) {
                if (response.body() != null) {
                    sportNews = response.body()
                    if (sportNews!!.articles != null) {
                        articleList.clear()
                        articleList.addAll(sportNews!!.articles)
                    }
                    articleAdapter!!.notifyDataSetChanged()
                }
                progress.dismiss()
            }

            override fun onFailure(call: Call<SportNews>, t: Throwable) {
                progress.dismiss()
                Toast.makeText(context, "" + t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getContext(applicationComponent: ApplicationComponent): Context? {
        return null
    }

    fun doFilter(searchQuery: String) {
        searchAPICall(searchQuery)

    }

    private fun searchAPICall(searchQuery: String) {
        val progress = ProgressDialog(context)
        progress.setMessage("Searching... ")
        progress.isIndeterminate = true
        progress.show()

        val searchCall = apiInterface!!.getSearchViewArticles(searchQuery)
        searchCall.enqueue(object : Callback<Search> {
            override fun onResponse(call: Call<Search>, response: Response<Search>) {
                try {
                    search = response.body()

                    if (search != null && search!!.articles != null) {
                        articleList.clear()
                        articleList.addAll(search!!.articles)
                    }
                    articleAdapter!!.notifyDataSetChanged()

                    Toast.makeText(context, "Searched.", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(context, "" + e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }

                progress.dismiss()
            }

            override fun onFailure(call: Call<Search>, t: Throwable) {
                progress.dismiss()
                Toast.makeText(context, "" + t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
