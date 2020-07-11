# crypto-currency

## Info
In this app i am going to show you current cryptocurrencies.

## Installation

Using this commands you can get it:

git clone https://github.com/eyrafabdullayev/crypto-currencies.git

## About

There are two cases: **if you are not using RXJava** or **if you are using it**

1) If you are NOT using RXJava
```kotlin

      val service = retrofit.create(CryptoApi::class.java)
        val call = service.getData()

        call.enqueue(object : Callback<List<CryptoModel>> {
            override fun onFailure(call: Call<List<CryptoModel>>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<List<CryptoModel>>,
                response: Response<List<CryptoModel>>
            ) {
                if (response.isSuccessful) {
                      response.body()?.let {
                          
                      }
                }
            }            

        });
        
//    and interface CryptoAPI  
       
      @GET("prices?key=")
      fun getData(): Call<List<CryptoModel>>
```

  2) If you are using RXJava
  
  ```kotlin
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(CryptoApi::class.java)

        //we can add other calls to 'compositeDisposable' as we trying below
        compositeDisposable?.add(retrofit.getData() //getting data
            .subscribeOn(Schedulers.io()) //here we trying listen to it on another thread
            .observeOn(AndroidSchedulers.mainThread()) //here we trying execute data on main thread
            .subscribe(this::handleResponse)) // sending response to the method

  
        private fun handleResponse(cryptoList: List<CryptoModel>){
        
        }
    
        //and interface CryptoAPI can be like that
    
        @GET("prices?key=")
        fun getData(): Observable<List<CryptoModel>>
    
```    
  
  
  
  
