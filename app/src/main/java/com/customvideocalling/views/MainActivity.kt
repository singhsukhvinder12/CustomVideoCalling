package com.customvideocalling.views

import android.content.Intent
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.customvideocalling.R
import com.customvideocalling.adapters.MyViewPagerAdapter
import com.customvideocalling.application.MyApplication
import com.customvideocalling.constants.GlobalConstants
import com.customvideocalling.databinding.ActivityMainBinding
import com.customvideocalling.utils.SharedPrefClass
import com.customvideocalling.utils.core.BaseActivity
import com.customvideocalling.viewmodels.MainViewModel
import com.customvideocalling.views.authentication.LoginActivity
import com.customvideocalling.views.student.AddTokentActivity
import com.customvideocalling.views.student.TokenHistoryActivity
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import org.w3c.dom.Text


class MainActivity : BaseActivity() {
    private var activityMainBinding: ActivityMainBinding? = null
    var mainViewModel: MainViewModel? = null
    private var navigationView: NavigationView? = null
    private var drawer: DrawerLayout? = null
    private var sharedPrefClass = SharedPrefClass()

    var mIndicator: View? = null
    private var indicatorWidth = 0

    lateinit var tv_active: TextView
    lateinit var tv_history: TextView

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initViews() {

        tv_active = findViewById(R.id.tv_active)
        tv_history = findViewById(R.id.tv_history)

        activityMainBinding = viewDataBinding as ActivityMainBinding
        mIndicator = findViewById(R.id.indicator);

        navigationView = activityMainBinding!!.navView
        navigationView!!.alpha = 0.9f
        drawer = activityMainBinding!!.drawerLayout
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        activityMainBinding!!.mainViewModel = mainViewModel

        activityMainBinding!!.commonToolBar.toolbarBack.setImageResource(R.drawable.ic_sidebar)
        activityMainBinding!!.commonToolBar.toolbarRightImage.visibility = View.VISIBLE
        activityMainBinding!!.commonToolBar.toolbarRightImage.setImageResource(R.drawable.ic_notifications)
        activityMainBinding!!.commonToolBar.toolbarText.text =
            resources.getString(R.string.home)
        activityMainBinding!!.tabs.addTab(activityMainBinding!!.tabs.newTab().setText("Active"))
        activityMainBinding!!.tabs.addTab(activityMainBinding!!.tabs.newTab().setText("History"))
        activityMainBinding!!.tabs.tabGravity = TabLayout.GRAVITY_CENTER

        val adapter =
            MyViewPagerAdapter(this, supportFragmentManager, activityMainBinding!!.tabs.tabCount)
        activityMainBinding!!.pager.adapter = adapter


//        activityMainBinding!!.tabs.post(Runnable {
//            indicatorWidth =  activityMainBinding!!.tabs.getWidth() /  activityMainBinding!!.tabs.getTabCount()
//
//            //Assign new width
//            val indicatorParams =
//                mIndicator!!.getLayoutParams() as FrameLayout.LayoutParams
//            indicatorParams.width = indicatorWidth
//            mIndicator!!.setLayoutParams(indicatorParams)
//        })

        activityMainBinding!!.pager.addOnPageChangeListener(
            TabLayout.TabLayoutOnPageChangeListener(
                activityMainBinding!!.tabs
            )
        )

        activityMainBinding!!.pager.addOnPageChangeListener(object : OnPageChangeListener {
            //To move the indicator as the user scroll, we will need the scroll offset values
            //positionOffset is a value from [0..1] which represents how far the page has been scrolled
            //see https://developer.android.com/reference/android/support/v4/view/ViewPager.OnPageChangeListener
            override fun onPageScrolled(
                i: Int,
                positionOffset: Float,
                positionOffsetPx: Int
            ) {
                /*  val params =
                      mIndicator!!.getLayoutParams() as FrameLayout.LayoutParams

                  //Multiply positionOffset with indicatorWidth to get translation
                  val translationOffset = (positionOffset + i) * indicatorWidth
                  params.leftMargin = translationOffset.toInt()
                  mIndicator!!.setLayoutParams(params)*/


            }

            override fun onPageSelected(i: Int) {}
            override fun onPageScrollStateChanged(i: Int) {}
        })



        activityMainBinding!!.tabs.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                activityMainBinding!!.pager.currentItem = tab.position

                if (tab.position == 0) {
                    changeBackgroundAndText(tv_active, tv_history, "1")
                } else if (tab.position == 1) {
                    changeBackgroundAndText(tv_active, tv_history, "2")
                } else {

                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

        mainViewModel!!.isClick().observe(
            this, Observer<String>(function =
            fun(it: String?) {
                when (it) {
                    "img_right" -> {
                        /* val intent = Intent(this, NotificationsListActivity::class.java)
                         startActivity(intent)*/
                    }
                    "tv_nav_notification" -> {

                    }
                    "tv_nav_fuel" -> {

                    }
                    "tv_nav_history" -> {
                        val intent = Intent(this, TokenHistoryActivity::class.java)
                        startActivity(intent)
                    }
                    "tv_add_token" -> {
                        val intent = Intent(this, AddTokentActivity::class.java)
                        startActivity(intent)
                    }
                    "tv_nav_home" -> {
                        if (drawer!!.isDrawerOpen(GravityCompat.START)) {
                            drawer!!.closeDrawer(Gravity.LEFT) //CLOSE Nav Drawer!
                        }
                    }
                    "tv_nav_contact" -> {
                        showToastSuccess("Coming Soon")
                    }

                    "ivProfile" -> {
                        val intent = Intent(this, ProfileActivity::class.java)
                        startActivity(intent)
                    }
                    "img_nav_cancel" -> {
                        if (drawer!!.isDrawerOpen(GravityCompat.START)) {
                            drawer!!.closeDrawer(Gravity.LEFT) //CLOSE Nav Drawer!
                        }
                    }
                    "tv_nav_logout" -> {
                        sharedPrefClass!!.cleanPref(this)
                        val intent = Intent(this, LoginActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
/*
                        confirmationDialog = mDialogClass.setDefaultDialog(
                            this,
                            this,
                            "logout",
                            getString(R.string.warning_logout)
                        )
                        confirmationDialog?.show()
*/

                    }

                    "toolbar_back" -> {
                        /* val image = SharedPrefClass().getPrefValue(
                             MyApplication.instance.applicationContext,
                             GlobalConstants.USER_IAMGE
                         )
                         // ic_profile
                         Glide.with(this)
                             .load(image)
                             .placeholder(R.drawable.user)
                             .into(activityDashboardBinding!!.icProfile)*/
                        /*val name = SharedPrefClass().getPrefValue(
                            MyApplication.instance.applicationContext,
                            getString(R.string.first_name)
                        )*/


                        if (drawer!!.isDrawerOpen(GravityCompat.START)) {
                            drawer!!.closeDrawer(Gravity.LEFT) //CLOSE Nav Drawer!
                        } else {
                            drawer!!.openDrawer(Gravity.LEFT)
                        }
                        /* val fragmentType =
                             supportFragmentManager.findFragmentById(R.id.frame_layout)
                         when (fragmentType) {
                             is HomeFragment -> {
                                 activityDashboardBinding!!.toolbarCommon.imgRight.visibility =
                                     View.VISIBLE
                             }
                         }*/
                    }
                }
            })
        )

        tv_active.setOnClickListener {
            activityMainBinding!!.pager.setCurrentItem(0)
            changeBackgroundAndText(tv_active, tv_history, "1")
        }
        tv_history.setOnClickListener {
            activityMainBinding!!.pager.setCurrentItem(1)
            changeBackgroundAndText(tv_active, tv_history, "2")
        }
        changeBackgroundAndText(tv_active, tv_history, "1")

        // replaceFragment()
    }


    override fun onResume() {
        super.onResume()
        val name = SharedPrefClass().getPrefValue(
            MyApplication.instance.applicationContext,
            GlobalConstants.USERNAME
        )
        activityMainBinding!!.tvName.text = name.toString()
    }

    fun changeBackgroundAndText(text: TextView, text2: TextView, status: String) {

        if (status.equals("1")) {
            text.setTextColor(resources.getColor(R.color.white))
            text.setBackgroundResource(R.drawable.shape_left_round_yellow)
            text.setTypeface(text.getTypeface(), Typeface.NORMAL);

            text2.setTextColor(resources.getColor(R.color.custom_blue))
            text2.setBackgroundResource(R.drawable.shape_right_round_yellow_stroke)
            text2.setTypeface(text2.getTypeface(), Typeface.BOLD);

        } else if (status.equals("2")) {
            text.setTextColor(resources.getColor(R.color.custom_blue))
            text.setBackgroundResource(R.drawable.shape_left_round_yellow_stroke)
            text.setTypeface(text.getTypeface(), Typeface.BOLD);


            text2.setTextColor(resources.getColor(R.color.white))
            text2.setBackgroundResource(R.drawable.shape_right_round_yellow)
            text2.setTypeface(text2.getTypeface(), Typeface.NORMAL);

        }

    }
    /* fun replaceFragment(){
         val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
         ft.replace(R.id.frame_layout, StudentHistoryFragment(), "")
         ft.commit()
     }*/
}
