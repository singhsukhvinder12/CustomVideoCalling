package com.customvideocalling.views

import android.annotation.SuppressLint
import android.os.AsyncTask
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.gson.JsonObject
import com.customvideocalling.R
import com.customvideocalling.RoomListAdapter
import com.customvideocalling.common.UtilsFunctions
import com.customvideocalling.databinding.ActivityUserroomBinding
import com.customvideocalling.roomdatabase.AppDatabase
import com.customvideocalling.roomdatabase.User
import com.customvideocalling.utils.core.BaseActivity
import com.customvideocalling.viewmodels.UserViewModel
import java.util.*
@SuppressLint("StaticFieldLeak")
class UserRoomActivity : BaseActivity() {
    private lateinit var activityUserBinding: ActivityUserroomBinding
    lateinit var mainViewModel: UserViewModel
    var myPage = 1
    var mPastVisibleItems = 0
    var mVisibleItemCount = 0
    var mTotalItemCount = 0
    var mLoadMoreViewCheck = true
    private var rideListAdapter: RoomListAdapter? = null
    private var arrayList: ArrayList<User>? = null
    private var appDatabase: AppDatabase? = null
    override fun getLayoutId(): Int {
        return com.customvideocalling.R.layout.activity_userroom
    }

    override fun initViews() {
        activityUserBinding = viewDataBinding as ActivityUserroomBinding
        mainViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        arrayList = ArrayList()
        activityUserBinding.userViewModel = mainViewModel
        appDatabase = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database-name").build()
        /*mainViewModel.list!!.observe(this, Observer<List<User>> { data ->
            rideListAdapter!!.newData(data!!)
            rideListAdapter!!.notifyDataSetChanged()
            mLoadMoreViewCheck = true
        })*/


        //OfflineSyncing.userDataSyncing()
        /*mainViewModel.isClick.observe(this, Observer<String> { string ->
            when(string) {
                "btn"->{ insertData()}
                "clear"->{ clearData()}
            }
        })*/

        activityUserBinding.pullToRefresh.setOnRefreshListener {
            mainViewModel.getAllList()
            activityUserBinding.pullToRefresh.isRefreshing = false
        }



        initRecyclerView()

    }
    private fun insertTask(note: User) {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                appDatabase!!.userDao().insertAll(note)

                val new=appDatabase!!.userDao().findLatestEntry()
                val mJsonObject = JsonObject()
                mJsonObject.addProperty("first_name", note.firstName)
                mJsonObject.addProperty("last_name", note.lastName)
                if(UtilsFunctions.isNetworkConnectedWithout()) mainViewModel.uplaodEntry(new.uid, mJsonObject)
                return null
            }
        }.execute()


        mainViewModel.getAllList()
    }

    private fun clearData()
    {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                appDatabase!!.userDao().nukeTable()
                arrayList!!.clear()
                return null
            }
        }.execute()
        mainViewModel.getAllList()


    }

    private fun insertData() {
        when {
            activityUserBinding.etFname.text.toString() == "" -> {
                activityUserBinding.etFname.requestFocus()
                activityUserBinding.etFname.error = getString(R.string.enter_fname)
            }
            activityUserBinding.etLname.text.toString() == "" -> {
                activityUserBinding.etLname.requestFocus()
                activityUserBinding.etLname.error = getString(R.string.enter_lname)
            }
            else -> {
                val note = User(0, activityUserBinding.etFname.text.toString(), activityUserBinding.etLname.text.toString(), 0)
                insertTask(note)
            }
        }
    }

    private fun initRecyclerView() {
        rideListAdapter = RoomListAdapter(this, arrayList!!)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        activityUserBinding.rvHistory.layoutManager = linearLayoutManager
        activityUserBinding.rvHistory.adapter = rideListAdapter


        activityUserBinding.rvHistory.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
                            mainViewModel.paginate(myPage.toString())
                        }
                    }
                }
            }
        })
    }
}
