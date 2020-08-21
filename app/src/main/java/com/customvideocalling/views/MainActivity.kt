package com.customvideocalling.views

import android.content.Intent
import android.view.Gravity
import android.view.View
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.customvideocalling.R
import com.customvideocalling.adapters.MyViewPagerAdapter
import com.customvideocalling.application.MyApplication
import com.customvideocalling.databinding.ActivityMainBinding
import com.customvideocalling.utils.SharedPrefClass
import com.customvideocalling.utils.core.BaseActivity
import com.customvideocalling.viewmodels.MainViewModel
import com.customvideocalling.views.authentication.LoginActivity
import com.customvideocalling.views.fragment.HomeFragment
import com.customvideocalling.views.fragment.JobRequestsFragment
import com.customvideocalling.views.fragment.StudentHistoryFragment
import com.customvideocalling.views.student.AddTokentActivity
import com.customvideocalling.views.student.TokenHistoryActivity
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.layout_custom_alert.*


class MainActivity : BaseActivity() {
    private var activityMainBinding: ActivityMainBinding? = null
    var mainViewModel: MainViewModel? = null
    private var navigationView: NavigationView? = null
    private var drawer: DrawerLayout? = null
    private var sharedPrefClass = SharedPrefClass()
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initViews() {
        activityMainBinding = viewDataBinding as ActivityMainBinding

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

        activityMainBinding!!.pager.addOnPageChangeListener(
            TabLayout.TabLayoutOnPageChangeListener(
                activityMainBinding!!.tabs
            )
        )

        activityMainBinding!!.tabs.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                activityMainBinding!!.pager.currentItem = tab.position
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

                    "ll_profile" -> {
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
                        val name = SharedPrefClass().getPrefValue(
                            MyApplication.instance.applicationContext,
                            getString(R.string.first_name)
                        )
                        activityMainBinding!!.tvName.text = name.toString()
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


        // replaceFragment()
    }


    /* fun replaceFragment(){
         val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
         ft.replace(R.id.frame_layout, StudentHistoryFragment(), "")
         ft.commit()
     }*/
}
