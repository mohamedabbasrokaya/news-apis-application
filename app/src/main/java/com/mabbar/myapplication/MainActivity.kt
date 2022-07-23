package com.mabbar.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.mabbar.myapplication.api.ApiManger
import com.mabbar.myapplication.api.Constant
import com.mabbar.myapplication.model.NewsAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var tabLayout: TabLayout
    lateinit var progressBar: ProgressBar
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initviews()
        getnewssources()
    }
    val adapter=NewsAdapter(null)
    fun initviews(){

        tabLayout=findViewById(R.id.tab_layout)
        progressBar=findViewById(R.id.progressbar)
        recyclerView=findViewById(R.id.recycle_view)
        recyclerView.adapter=adapter
    }
    fun getnewssources(){
        ApiManger.getApis().getSources(Constant.apiKey)
            .enqueue(object:Callback<SourcesResponse>{
                override fun onResponse(
                    call: Call<SourcesResponse>,
                    response: Response<SourcesResponse>
                ) {
                    progressBar.isVisible=false;
                    addSoourcesToTabLayout(response.body()?.sources)
                }


                override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                    Log.e("error",t.localizedMessage)
                }

            })

    }

    private fun addSoourcesToTabLayout(sources: List<SourcesItem?>?) {
        sources?.forEach { sourcesItem ->
            val tab=tabLayout.newTab()


            tab.setText(sourcesItem?.name)
            tab.tag=sourcesItem
            tabLayout.addTab(tab)
        }
        tabLayout.addOnTabSelectedListener(
            object :TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab?) {
                   //val source= tab?.get(tab.position)
                   val sources= tab?.tag as SourcesItem
                    getNewsBySource(sources)
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    val sources= tab?.tag as SourcesItem
                    getNewsBySource(sources)
                }

            }


        )
        tabLayout.getTabAt(0)?.select()

    }

    fun getNewsBySource(sourcesItem: SourcesItem) {
    ApiManger.getApis()
        .getNews(Constant.apiKey,sourcesItem.id?:"")
        .enqueue(object:Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                progressBar.isVisible=false
                adapter.changeData(response.body()?.articles)

            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                progressBar.isVisible = false
            }
        })


        }
}