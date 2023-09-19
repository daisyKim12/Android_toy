package org.texchtown.retrofit_demo

import android.app.ProgressDialog
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.texchtown.retrofit_demo.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
        /*
        1. Data class 정하기
        2. API 호출을 위한 인터페이스 만들기
        3. Retrofit Client 생성하기
        4. Request 전송하기
         */

class MainActivity : AppCompatActivity() {
    //http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchWeeklyBoxOfficeList.json?key=f5eef3421c602c6cb7ea224104795888&targetDt=20230212&weekGb=0&multiMovieYn=N

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            val key = "b1a88557ed769d9424d91c407b517386"
            val targetDt = "20230212"
            val weekGb = "0"
            val multiMovieYn = "N"
            getData(key, targetDt, weekGb, multiMovieYn)
        }


    }

    private fun getData(key: String, targetDt: String, weekGb: String, multiMovieYn: String) {

        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please wait while data is fetch")
        progressDialog.show()


        RetrofirInstance.appInterface.getData(key, targetDt, weekGb, multiMovieYn).enqueue(object : Callback<responceDataClass?> {
            override fun onResponse(
                call: Call<responceDataClass?>,
                response: Response<responceDataClass?>
            ) {
                if(response.isSuccessful) {
                    val result = response.body() as responceDataClass

                    //Log.d(TAG, "onResponse:1 "+result.boxOfficeResult.weeklyBoxOfficeList)
                    Log.d(TAG, "onResponse: "+result.boxOfficeResult.weeklyBoxOfficeList[1])
//                    binding.memeTitle.text = result.weeklyBoxOfficeList.first().audiAcc
//                    binding.memeAuthor.text = result.weeklyBoxOfficeList.first().movieNm


//                    binding.memeTitle.text = result.boxOfficeResult.boxofficeType.toString()
//                    binding.memeAuthor.text = result.boxOfficeResult.showRange.toString()

                } else {
                    Log.d(TAG, "onResponse: response not successful")
                }
                progressDialog.dismiss()

            }

            override fun onFailure(call: Call<responceDataClass?>, t: Throwable) {
                Toast.makeText(this@MainActivity, "${t.localizedMessage}", Toast.LENGTH_SHORT).show()
                progressDialog.dismiss()
            }
        })
    }
}








//        //link the Retrofit object and Retrofit interface file in MainActivity
//        val quotesApi = RetrofitHelper.getInstance().create(QuotesApi::class.java)
//        // launching a new coroutine
//        GlobalScope.launch {
//            val result = quotesApi.getQuotes()
//            if (result != null)
//            // Checking the results
//                Log.d("ayush: ", result.body().toString())
//        }
