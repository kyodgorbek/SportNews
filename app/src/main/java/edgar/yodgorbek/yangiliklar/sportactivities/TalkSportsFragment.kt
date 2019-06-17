package edgar.yodgorbek.yangiliklar.sportactivities

import android.app.ProgressDialog
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
import edgar.yodgorbek.yangiliklar.adapter.TalkSportAdapter
import edgar.yodgorbek.yangiliklar.internet.SportClient
import edgar.yodgorbek.yangiliklar.model.Article
import edgar.yodgorbek.yangiliklar.model.Search
import edgar.yodgorbek.yangiliklar.model.SportInterface
import edgar.yodgorbek.yangiliklar.model.TalkSports
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TalkSportsFragment : Fragment() {


    var articleList: MutableList<Article> = ArrayList()
    @BindView(R.id.recycler_view)
    internal var recyclerView: RecyclerView? = null
    private var talkSports: TalkSports? = null
    private var talkSportAdapter: TalkSportAdapter? = null
    private var sportInterface: SportInterface? = null
    private var search: Search? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_bbcsport, container, false)
        ButterKnife.bind(this, view)
        sportInterface = SportClient.apiService
        talkSportAdapter = TalkSportAdapter(articleList, talkSports!!)
        val layoutManager = LinearLayoutManager(context)
        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.adapter = talkSportAdapter
        fetchInitialArticles()


        return view


    }

    private fun fetchInitialArticles() {
        val progress = ProgressDialog(context)
        progress.setMessage("Loading... ")
        progress.isIndeterminate = true
        progress.show()
        val call = sportInterface!!.sportArticles
        call.enqueue(object : Callback<TalkSports> {
            override fun onResponse(call: Call<TalkSports>, response: Response<TalkSports>) {
                if (response.body() != null) {
                    talkSports = response.body()
                }
                if (talkSports!!.sportArticles != null) {
                    articleList.clear()
                    articleList.addAll(talkSports!!.sportArticles)
                }
                talkSportAdapter!!.notifyDataSetChanged()
                progress.dismiss()

            }

            override fun onFailure(call: Call<TalkSports>, t: Throwable) {
                progress.dismiss()
                Toast.makeText(context, "" + t.message, Toast.LENGTH_SHORT).show()
            }
        })

    }

    fun doFilter(searchQuery: String) {
        searchAPICall(searchQuery)
    }

    private fun searchAPICall(searchQuery: String) {
        val progress = ProgressDialog(context)
        progress.setMessage("Searching... ")
        progress.isIndeterminate = true
        progress.show()

        val searchCall = sportInterface!!.getSearchViewArticles(searchQuery)
        searchCall.enqueue(object : Callback<Search> {
            override fun onResponse(call: Call<Search>, response: Response<Search>) {
                try {
                    search = response.body()

                    if (search != null && search!!.articles != null) {
                        articleList.clear()
                        articleList.addAll(search!!.articles)
                    }
                    talkSportAdapter!!.notifyDataSetChanged()

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
