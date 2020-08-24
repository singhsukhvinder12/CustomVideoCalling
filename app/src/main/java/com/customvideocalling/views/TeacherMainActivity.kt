package com.customvideocalling.views

import android.content.Intent
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.customvideocalling.R
import com.customvideocalling.adapters.MyViewPagerAdapter
import com.customvideocalling.adapters.MyViewTeacherPagerAdapter
import com.customvideocalling.application.MyApplication
import com.customvideocalling.constants.GlobalConstants
import com.customvideocalling.databinding.ActivityMainBinding
import com.customvideocalling.databinding.ActivityTeacherMainBinding
import com.customvideocalling.utils.SharedPrefClass
import com.customvideocalling.utils.core.BaseActivity
import com.customvideocalling.viewmodels.MainViewModel
import com.customvideocalling.viewmodels.TeacherMainViewModel
import com.customvideocalling.views.authentication.LoginActivity
import com.customvideocalling.views.fragment.HomeFragment
import com.customvideocalling.views.fragment.JobRequestsFragment
import com.customvideocalling.views.fragment.StudentHistoryFragment
import com.customvideocalling.views.student.AddTokentActivity
import com.customvideocalling.views.student.TokenHistoryActivity
import com.customvideocalling.views.teacher.AddScheduleActivity
import com.customvideocalling.views.teacher.ScheduleListActivity
import com.customvideocalling.views.teacher.TeacherNotificationListActivity
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_custom_alert.*


class TeacherMainActivity : BaseActivity(), View.OnClickListener {
    private var activityTeacherMainBinding: ActivityTeacherMainBinding? = null
    var teacherMainViewModel: TeacherMainViewModel? = null
    private var navigationView: NavigationView? = null
    private var drawer: DrawerLayout? = null
    private var sharedPrefClass = SharedPrefClass()
    var mIndicator: View? = null
    lateinit var tv_active: TextView
    lateinit var tv_history: TextView

    override fun getLayoutId(): Int {
        return R.layout.activity_teacher_main
    }

    override fun initViews() {
        tv_active = findViewById(R.id.tv_active)
        tv_history = findViewById(R.id.tv_history)
        activityTeacherMainBinding = viewDataBinding as ActivityTeacherMainBinding
        navigationView = activityTeacherMainBinding!!.navView
        navigationView!!.alpha = 0.9f
        drawer = activityTeacherMainBinding!!.drawerLayout
        mIndicator = findViewById(R.id.indicator);
        teacherMainViewModel = ViewModelProviders.of(this).get(TeacherMainViewModel::class.java)
        activityTeacherMainBinding!!.mainViewModel = teacherMainViewModel

        activityTeacherMainBinding!!.commonToolBar.toolbarNavigationBtn.visibility=View.VISIBLE
        activityTeacherMainBinding!!.commonToolBar.toolbarBack.visibility=View.GONE
        activityTeacherMainBinding!!.commonToolBar.toolbarRightImage.visibility = View.VISIBLE
        activityTeacherMainBinding!!.commonToolBar.toolbarRightImage.setImageResource(R.drawable.ic_notifications)
        activityTeacherMainBinding!!.commonToolBar.toolbarText.text =
            resources.getString(R.string.home)


        activityTeacherMainBinding!!.tabs.addTab(
            activityTeacherMainBinding!!.tabs!!.newTab().setText("Active")
        )
        activityTeacherMainBinding!!.tabs.addTab(
            activityTeacherMainBinding!!.tabs!!.newTab().setText("History")
        )
        activityTeacherMainBinding!!.tabs.tabGravity = TabLayout.GRAVITY_CENTER

        val adapter = MyViewTeacherPagerAdapter(
            this,
            supportFragmentManager,
            activityTeacherMainBinding!!.tabs.tabCount
        )
        activityTeacherMainBinding!!.pager.adapter = adapter

        activityTeacherMainBinding!!.pager.addOnPageChangeListener(
            TabLayout.TabLayoutOnPageChangeListener(
                activityTeacherMainBinding!!.tabs
            )
        )

        activityTeacherMainBinding!!.pager.addOnPageChangeListener(object :
            ViewPager.OnPageChangeListener {
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

        activityTeacherMainBinding!!.tabs.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                activityTeacherMainBinding!!.pager.currentItem = tab.position

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

        // replaceFragment()


        teacherMainViewModel!!.isClick().observe(
            this, Observer<String>(function =
            fun(it: String?) {
                when (it) {
                    "toolbar_navigationBtn"->{
                        drawer!!.openDrawer(Gravity.LEFT)
                    }
                    "toolbar_right_image" ->{
                        val intent = Intent(this, TeacherNotificationListActivity::class.java)
                        startActivity(intent)
                    }
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
                        if (drawer!!.isDrawerOpen(GravityCompat.START)) {
                            drawer!!.closeDrawer(Gravity.LEFT) //CLOSE Nav Drawer!
                        }
                    }
                    "tv_add_schedule" -> {
                        val intent = Intent(this, ScheduleListActivity::class.java)
                        startActivity(intent)
                        if (drawer!!.isDrawerOpen(GravityCompat.START)) {
                            drawer!!.closeDrawer(Gravity.LEFT) //CLOSE Nav Drawer!
                        }
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
                        if (drawer!!.isDrawerOpen(GravityCompat.START)) {
                            drawer!!.closeDrawer(Gravity.LEFT) //CLOSE Nav Drawer!
                        }
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
            activityTeacherMainBinding!!.pager.setCurrentItem(0)
            changeBackgroundAndText(tv_active, tv_history, "1")
        }
        tv_history.setOnClickListener {
            activityTeacherMainBinding!!.pager.setCurrentItem(1)
            changeBackgroundAndText(tv_active, tv_history, "2")
        }
        changeBackgroundAndText(tv_active, tv_history, "1")

    }

    override fun onResume() {
        super.onResume()
        val name = SharedPrefClass().getPrefValue(
            MyApplication.instance.applicationContext,
            GlobalConstants.USERNAME
        )
        activityTeacherMainBinding!!.tvName.text = name.toString()
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

    override fun onClick(p0: View?) {
        when(p0!!.id) {
            R.id.toolbar_right_image -> {
                val intent = Intent(this, TeacherNotificationListActivity::class.java)
                startActivity(intent)
            }
        }
    }
}

