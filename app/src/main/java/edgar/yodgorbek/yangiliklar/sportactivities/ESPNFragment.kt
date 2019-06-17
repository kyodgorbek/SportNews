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
import edgar.yodgorbek.yangiliklar.adapter.EspnAdapter
import edgar.yodgorbek.yangiliklar.internet.SportClient
import edgar.yodgorbek.yangiliklar.model.Article
import edgar.yodgorbek.yangiliklar.model.Espn
import edgar.yodgorbek.yangiliklar.model.Search
import edgar.yodgorbek.yangiliklar.model.SportInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ESPNFragment : Fragment() {

    var articleList: MutableList<Article> = ArrayList()
    @BindView(R.id.recycler_view)
    lateinit var recyclerView: RecyclerView
    private var espn: Espn? = null
    lateinit var espnAdapter: EspnAdapter
    private var search: Search? = null

    private var sportInterface: SportInterface? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_espn, container, false)

        ButterKnife.bind(this, view)
        espnAdapter = EspnAdapter(articleList, espn!!)
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = espnAdapter
        sportInterface = SportClient.apiService
        fetchInitialArticles()

        return view
    }

    private fun fetchInitialArticles() {
        val progress = ProgressDialog(context)
        progress.setMessage("Loading... ")
        progress.isIndeterminate = true
        progress.show()
        val call = sportInterface!!.espnArticles
        call.enqueue(object : Callback<Espn> {
            override fun onResponse(call: Call<Espn>, response: Response<Espn>) {
                if (response.body() != null) {
                    espn = response.body()
                }
                if (espn!!.espnArticles != null) {
                    articleList.clear()
                    espn!!.espnArticles?.let { articleList.addAll(it) }
                }
                espnAdapter!!.notifyDataSetChanged()

                progress.dismiss()
            }

            override fun onFailure(call: Call<Espn>, t: Throwable) {
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
                        search!!.articles?.let { articleList.addAll(it) }
                    }
                    espnAdapter!!.notifyDataSetChanged()

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
