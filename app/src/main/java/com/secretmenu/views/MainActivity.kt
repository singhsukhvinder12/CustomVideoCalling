package com.secretmenu.views

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.secretmenu.R
import com.secretmenu.RideListAdapter
import com.secretmenu.application.MyApplication
import com.secretmenu.common.UtilsFunctions
import com.secretmenu.databinding.ActivityMainBinding
import com.secretmenu.model.RideListModel
import com.secretmenu.utils.SharedPrefClass
import com.secretmenu.utils.core.BaseActivity
import com.secretmenu.viewmodels.MainViewModel

class MainActivity : BaseActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    var mainViewModel: MainViewModel? = null
    var myPage = 1
    var mPastVisibleItems = 0
    var mVisibleItemCount = 0
    var mTotalItemCount = 0
    var mLoadMoreViewCheck = true
    private var rideListAdapter: RideListAdapter? = null
    private var arrayList = ArrayList<RideListModel.Datum>()
    private var sharedPrefClass = SharedPrefClass()
    override fun getLayoutId(): Int {
        return com.secretmenu.R.layout.activity_main
    }

    override fun initViews() {
        activityMainBinding = viewDataBinding as ActivityMainBinding
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        activityMainBinding.clickListener = mainViewModel
        activityMainBinding.executePendingBindings()
//set APp BAckground using Image
        val myLogo = BitmapFactory.decodeResource(this.resources, R.drawable.logo)

        setBackground(myLogo)


        mainViewModel!!.getHistoryList()

        mainViewModel!!.list?.observe(this, Observer<RideListModel> { response ->
            if (response != null) {
                val message = response.message
                when {
                    response.code == 200 -> {
                        if (myPage == 1)
                            arrayList = response.data!!
                        else
                            arrayList.addAll(response.data!!)

                        activityMainBinding.rlMain.visibility = View.VISIBLE
                        activityMainBinding.rlNorecord.visibility = View.GONE
                        saveintoDatabase(arrayList)

                    }
                    response.code == 404 -> {
                        if (myPage == 1) {
                            activityMainBinding.rlMain.visibility = View.GONE
                            activityMainBinding.rlNorecord.visibility = View.VISIBLE
                        }
                    }
                    else -> showToastError(message)
                }
            }
        })



        mainViewModel!!.historyList?.observe(this, Observer<List<RideListModel.Datum>> { data ->
            if (data != null) {
                rideListAdapter!!.newData(data)
                rideListAdapter!!.notifyDataSetChanged()
                mLoadMoreViewCheck = true
                activityMainBinding.loadMore.visibility = View.GONE
            }
        })





        if (checkObjectNull(sharedPrefClass.getPrefValue(this, "theme"))) {
            val type = sharedPrefClass.getPrefValue(this, "theme") as String
            if (type == "1") activityMainBinding.changeTheme.isChecked = true

        }


        activityMainBinding.changeTheme.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                UtilsFunctions.changeThemeMode(1)
                sharedPrefClass.putObject(this, "theme", 1)

            } else {
                UtilsFunctions.changeThemeMode(0)
                sharedPrefClass.putObject(this, "theme", 0)

            }
            val intent = this.intent
            finish()
            startActivity(intent)
        }


        mainViewModel!!.loading.observe(this, Observer<Boolean> { aBoolean ->
            if (aBoolean!!) {
                activityMainBinding.loadMore.visibility = View.VISIBLE
            } else {
                stopProgressDialog()
            }
        })


        mainViewModel!!.isClick.observe(this, Observer<String> {
            val i = Intent(applicationContext, UserRoomActivity::class.java)
            startActivity(i)
        })


        initRecyclerView()

    }

    private fun initRecyclerView() {
        rideListAdapter = RideListAdapter(this, arrayList)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        activityMainBinding.rvHistory.layoutManager = linearLayoutManager
        activityMainBinding.rvHistory.adapter = rideListAdapter


        activityMainBinding.rvHistory.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) //check for scroll down
                {
                    mVisibleItemCount = linearLayoutManager.childCount
                    mTotalItemCount = linearLayoutManager.itemCount
                    mPastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition()
                    if (mLoadMoreViewCheck) {
                        if ((mVisibleItemCount + mPastVisibleItems) >= mTotalItemCount) {
                            mLoadMoreViewCheck = false
                            myPage++
                            mainViewModel!!.paginate(myPage.toString())
                        }
                    }
                }
            }
        })
    }

    @SuppressLint("StaticFieldLeak")
    private fun saveintoDatabase(arraylist: ArrayList<RideListModel.Datum>) {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                MyApplication.appDatabase.historyDao().nukeTable()
                for (i in 0 until arraylist.size) {
                    MyApplication.appDatabase.historyDao().insertAll(arraylist[i])
                }
                // MyApplication.appDatabase.historyDao().insertAll(datum)
                return null
            }
        }.execute()
        mainViewModel!!.getHistoryList()
//        Toast.makeText(MyApplication.instance, "Insert Success",
//    Toast.LENGTH_LONG).show()
    }

    private fun setBackground(bitmap: Bitmap) {
        // Generate the palette and get the vibrant swatch
        val vibrantSwatch = createPaletteSync(bitmap).vibrantSwatch
        // Set the toolbar background and text colors.
        // Fall back to default colors if the vibrant swatch is not available.
        with(activityMainBinding.rvHistory) {
            setBackgroundColor(vibrantSwatch?.rgb ?:
            ContextCompat.getColor(context, R.color.appBackground))
        }

        with(activityMainBinding.rlNorecord) {
            setBackgroundColor(vibrantSwatch?.rgb ?:
            ContextCompat.getColor(context, R.color.appBackground))
        }
    }


    private fun createPaletteSync(bitmap: Bitmap): Palette = Palette.from(bitmap).generate()

}
