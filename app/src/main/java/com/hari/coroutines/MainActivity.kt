package com.hari.coroutines

import android.app.ProgressDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {
    var progressDialog: ProgressDialog? = null

    private lateinit var mJob: Job

    override val coroutineContext: CoroutineContext
        get() = mJob + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mJob = Job()
        launch {
            //show progress
            progressDialog = ProgressDialog(this@MainActivity).apply { setMessage("loading") }
            progressDialog?.show()

            //request
            val request = RetrofitFactory.makeRetrofitService().getIpInfo()
            //response
            val response = request.await()

            //act on output
            if (response.isSuccessful)
                cityTv.text = response.body()?.city
            else
                cityTv.text = "Error getting data!"

            //dismiss progress
            progressDialog?.dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mJob.cancel()
    }
}
