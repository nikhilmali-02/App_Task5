package com.example.app_task5

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import androidx.recyclerview.widget.DividerItemDecoration
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    lateinit var emptyView: TextView
    lateinit var recyclerView:RecyclerView
    lateinit var swipeRefresh:SwipeRefreshLayout
    lateinit var progressBar:ProgressBar
    lateinit var searchBox:EditText

    lateinit var adapter:PostAdapter

    var postList = mutableListOf<Post>()


    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        emptyView = findViewById(R.id.emptyView)

        recyclerView = findViewById(R.id.recyclerView)
        swipeRefresh = findViewById(R.id.swipeRefresh)
        progressBar = findViewById(R.id.progressBar)
        searchBox = findViewById(R.id.searchBox)

        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = PostAdapter(postList)
        recyclerView.adapter = adapter

        loadPosts()

        swipeRefresh.setOnRefreshListener {
            loadPosts()
        }
        recyclerView.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )

        searchBox.addTextChangedListener(object:TextWatcher{

            override fun afterTextChanged(s:Editable?) {}

            override fun beforeTextChanged(s:CharSequence?, start:Int, count:Int, after:Int) {}

            override fun onTextChanged(text:CharSequence?, start:Int, before:Int, count:Int) {

                val filtered = postList.filter {
                    it.title.contains(text.toString(),true)
                }

                adapter.updateList(filtered)

                if(filtered.isEmpty()){
                    emptyView.visibility = View.VISIBLE
                }else{
                    emptyView.visibility = View.GONE
                }
            }
        })
    }

    private fun loadPosts(){

        progressBar.visibility = View.VISIBLE

        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(ApiService::class.java)

        api.getPosts().enqueue(object:Callback<List<Post>>{

            override fun onResponse(call:Call<List<Post>>, response:Response<List<Post>>) {

                progressBar.visibility = View.GONE
                swipeRefresh.isRefreshing = false

                if(response.isSuccessful){

                    postList = response.body()!!.toMutableList()

                    adapter.updateList(postList)

                } else {

                    Toast.makeText(this@MainActivity,"API Error",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call:Call<List<Post>>, t:Throwable) {

                progressBar.visibility = View.GONE
                swipeRefresh.isRefreshing = false

                Toast.makeText(this@MainActivity,"Network Error",Toast.LENGTH_SHORT).show()
            }
        })
    }
}