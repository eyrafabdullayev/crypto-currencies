package com.eyrafabdullayev.cryptocurrency.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eyrafabdullayev.cryptocurrency.R
import com.eyrafabdullayev.cryptocurrency.adapter.RecyclerViewAdapter
import com.eyrafabdullayev.cryptocurrency.model.CryptoModel
import com.eyrafabdullayev.cryptocurrency.service.CryptoAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), RecyclerViewAdapter.Listener {

    private var BASE_URL = "https://api.nomics.com/v1/"
    private lateinit var cryptoModels: ArrayList<CryptoModel>
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter

    //Disposable
    //it used for when the Activity has been destroyed closing calls
    private var compositeDisposable: CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //RecyclerView
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        compositeDisposable = CompositeDisposable()

        //Load Data
        loadData()
    }

    fun loadData(){

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(CryptoAPI::class.java)

        //we can add other calls to 'compositeDisposable' as we trying below
        compositeDisposable?.add(retrofit.getData() //getting data
            .subscribeOn(Schedulers.io()) //here we trying listen to it on another thread
            .observeOn(AndroidSchedulers.mainThread()) //here we trying execute data on main thread
            .subscribe(this::handleResponse)) // sending response to the method

        /*
        val service = retrofit.create(CryptoAPI::class.java)
        val call = service.getData()

        call.enqueue(object : Callback<List<CryptoModel>>{
            override fun onFailure(call: Call<List<CryptoModel>>, t: Throwable) {
                // ..
            }

            override fun onResponse(
                call: Call<List<CryptoModel>>,
                response: Response<List<CryptoModel>>
            ) {
                if(response.isSuccessful){
                    response.body()?.let { it ->
                        cryptoModels = ArrayList(it)

                        //this@MainActivity represents Listener
                        recyclerViewAdapter = RecyclerViewAdapter(cryptoModels,this@MainActivity)
                        recyclerView.adapter = recyclerViewAdapter
                    }
                }
            }

        });
        */
    }

    fun handleResponse(cryptoList: List<CryptoModel>){
        cryptoModels = ArrayList(cryptoList)

        cryptoModels?.let {
            //this@MainActivity represents Listener
            recyclerViewAdapter = RecyclerViewAdapter(cryptoModels,this@MainActivity)
            recyclerView.adapter = recyclerViewAdapter
        }
    }

    override fun onItemClick(cryptoModel: CryptoModel) {
        // ...
    }

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable?.clear()
    }
}
