package com.example.bulsride.userAcivity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.example.bulsride.Adaptor.Menuadaptor
import com.example.bulsride.R
import com.example.bulsride.variables.Article
import com.example.bulsride.variables.JsonResponce


class MenuDetails : AppCompatActivity() {
    lateinit var menuview:RecyclerView
    lateinit var sss:ArrayList<Article>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_details)
        bindviews()
        getblogs()
    }
    private fun bindviews(){
        menuview=findViewById(R.id.menuview)
    }
    private fun getblogs(){
        AndroidNetworking.get("http://newsapi.org/v2/4fa930a01dd94adab35485c5227ee4f2")
                .setPriority(Priority.HIGH)
                .build()
                .getAsObject(JsonResponce::class.java,object :ParsedRequestListener<JsonResponce>{
                    override fun onResponse(response: JsonResponce?) {
                        Log.d("ss","success")
                       // setmenublog(response)
                    }

                    override fun onError(anError: ANError?) {
                        Log.d("Error",anError?.localizedMessage)
                    }
                })
    }
    private fun setmenublog(response: JsonResponce?) {
        val blogadaptor=Menuadaptor(response!!.articles)
        var linear= LinearLayoutManager(this@MenuDetails)
        linear.setOrientation(RecyclerView.VERTICAL)
        menuview.setLayoutManager(linear)
        menuview.setAdapter(blogadaptor)
    }
}