package com.customvideocalling.views


import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.SurfaceView
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.customvideocalling.R
import io.agora.rtc.IRtcEngineEventHandler
import io.agora.rtc.RtcEngine
import io.agora.rtc.video.VideoCanvas
import io.agora.rtc.video.VideoEncoderConfiguration


class VideoChatViewActivity : AppCompatActivity() {
    private var mRtcEngine: RtcEngine? = null
    private var mCallEnd = false
    private var mMuted = false
    private var mLocalContainer: FrameLayout? = null
    private var mRemoteContainer: RelativeLayout? = null
    private var mLocalView: SurfaceView? = null
    private var mRemoteView: SurfaceView? = null
    private var mCallBtn: ImageView? = null
    private var mMuteBtn: ImageView? = null
    private var mSwitchCameraBtn: ImageView? = null

    // Customized logger view

    /**
     * Event handler registered into RTC engine for RTC callbacks.
     * Note that UI operations needs to be in UI thread because RTC
     * engine deals with the events in a separate thread.
     */
    private val mRtcEventHandler: IRtcEngineEventHandler = object : IRtcEngineEventHandler() {
        /**
         * Occurs when the local user joins a specified channel.
         * The channel name assignment is based on channelName specified in the joinChannel method.
         * If the uid is not specified when joinChannel is called, the server automatically assigns a uid.
         *
         * @param channel Channel name.
         * @param uid User ID.
         * @param elapsed Time elapsed (ms) from the user calling joinChannel until this callback is triggered.
         */
        override fun onJoinChannelSuccess(
            channel: String,
            uid: Int,
            elapsed: Int
        ) {
          //  runOnUiThread(Runnable { mLogView.logI("Join channel success, uid: " + (uid and 0xFFFFFFFFL)) })
        }

        /**
         * Occurs when the first remote video frame is received and decoded.
         * This callback is triggered in either of the following scenarios:
         *
         * The remote user joins the channel and sends the video stream.
         * The remote user stops sending the video stream and re-sends it after 15 seconds. Possible reasons include:
         * The remote user leaves channel.
         * The remote user drops offline.
         * The remote user calls the muteLocalVideoStream method.
         * The remote user calls the disableVideo method.
         *
         * @param uid User ID of the remote user sending the video streams.
         * @param width Width (pixels) of the video stream.
         * @param height Height (pixels) of the video stream.
         * @param elapsed Time elapsed (ms) from the local user calling the joinChannel method until this callback is triggered.
         */
        override fun onFirstRemoteVideoDecoded(
            uid: Int,
            width: Int,
            height: Int,
            elapsed: Int
        ) {
            runOnUiThread(Runnable {
                //mLogView.logI("First remote video decoded, uid: " + (uid and 0xFFFFFFFFL))
                setupRemoteVideo(uid)
            })
        }

        /**
         * Occurs when a remote user (Communication)/host (Live Broadcast) leaves the channel.
         *
         * There are two reasons for users to become offline:
         *
         * Leave the channel: When the user/host leaves the channel, the user/host sends a
         * goodbye message. When this message is received, the SDK determines that the
         * user/host leaves the channel.
         *
         * Drop offline: When no data packet of the user or host is received for a certain
         * period of time (20 seconds for the communication profile, and more for the live
         * broadcast profile), the SDK assumes that the user/host drops offline. A poor
         * network connection may lead to false detections, so we recommend using the
         * Agora RTM SDK for reliable offline detection.
         *
         * @param uid ID of the user or host who leaves the channel or goes offline.
         * @param reason Reason why the user goes offline:
         *
         * USER_OFFLINE_QUIT(0): The user left the current channel.
         * USER_OFFLINE_DROPPED(1): The SDK timed out and the user dropped offline because no data packet was received within a certain period of time. If a user quits the call and the message is not passed to the SDK (due to an unreliable channel), the SDK assumes the user dropped offline.
         * USER_OFFLINE_BECOME_AUDIENCE(2): (Live broadcast only.) The client role switched from the host to the audience.
         */
        override fun onUserOffline(uid: Int, reason: Int) {
            runOnUiThread(Runnable {
             //   mLogView.logI("User offline, uid: " + (uid and 0xFFFFFFFFL))
                onRemoteUserLeft()
            })
        }
    }

    private fun setupRemoteVideo(uid: Int) {
        // Only one remote video view is available for this
        // tutorial. Here we check if there exists a surface
        // view tagged as this uid.
        val count = mRemoteContainer!!.childCount
        var view: View? = null
        for (i in 0 until count) {
            val v = mRemoteContainer!!.getChildAt(i)
            if (v.tag is Int && v.tag as Int == uid) {
                view = v
            }
        }
        if (view != null) {
            return
        }

        /*
          Creates the video renderer view.
          CreateRendererView returns the SurfaceView type. The operation and layout of the view
          are managed by the app, and the Agora SDK renders the view provided by the app.
          The video display view must be created using this method instead of directly
          calling SurfaceView.
         */mRemoteView = RtcEngine.CreateRendererView(getBaseContext())
        mRemoteContainer!!.addView(mRemoteView)
        // Initializes the video view of a remote user.
        mRtcEngine!!.setupRemoteVideo(VideoCanvas(mRemoteView, VideoCanvas.RENDER_MODE_HIDDEN, uid))
        mRemoteView!!.setTag(uid)
    }

    private fun onRemoteUserLeft() {
        removeRemoteVideo()
    }

    private fun removeRemoteVideo() {
        if (mRemoteView != null) {
            mRemoteContainer!!.removeView(mRemoteView)
        }
        // Destroys remote view
        mRemoteView = null
    }

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_chat_view)
        initUI()

        // Ask for permissions at runtime.
        // This is just an example set of permissions. Other permissions
        // may be needed, and please refer to our online documents.
        if (checkSelfPermission(
                REQUESTED_PERMISSIONS[0],
                PERMISSION_REQ_ID
            ) &&
            checkSelfPermission(
                REQUESTED_PERMISSIONS[1],
                PERMISSION_REQ_ID
            ) &&
            checkSelfPermission(
                REQUESTED_PERMISSIONS[2],
                PERMISSION_REQ_ID
            )
        ) {
            initEngineAndJoinChannel()
        }
    }

    private fun initUI() {
        mLocalContainer = findViewById(R.id.local_video_view_container)
        mRemoteContainer = findViewById(R.id.remote_video_view_container)
        mCallBtn = findViewById(R.id.btn_call)
        mMuteBtn = findViewById(R.id.btn_mute)
        mSwitchCameraBtn = findViewById(R.id.btn_switch_camera)


        // Sample logs are optional.
        showSampleLogs()
    }

    private fun showSampleLogs() {

    }

    private fun checkSelfPermission(permission: String, requestCode: Int): Boolean {
        if (ContextCompat.checkSelfPermission(this, permission) !==
            PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                REQUESTED_PERMISSIONS,
                requestCode
            )
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == PERMISSION_REQ_ID) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED || grantResults[1] != PackageManager.PERMISSION_GRANTED || grantResults[2] != PackageManager.PERMISSION_GRANTED
            ) {
                showLongToast(
                    "Need permissions " + Manifest.permission.RECORD_AUDIO +
                            "/" + Manifest.permission.CAMERA + "/" + Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                finish()
                return
            }

            // Here we continue only if all permissions are granted.
            // The permissions can also be granted in the system settings manually.
            initEngineAndJoinChannel()
        }
    }

    private fun showLongToast(msg: String) {
        this.runOnUiThread(Runnable {
            Toast.makeText(
                getApplicationContext(),
                msg,
                Toast.LENGTH_LONG
            ).show()
        })
    }

    private fun initEngineAndJoinChannel() {
        // This is our usual steps for joining
        // a channel and starting a call.
        initializeEngine()
        setupVideoConfig()
        setupLocalVideo()
        joinChannel()
    }

    private fun initializeEngine() {
        mRtcEngine = try {
            RtcEngine.create(
                getBaseContext(),
                getString(R.string.agora_app_id),
                mRtcEventHandler
            )
        } catch (e: Exception) {
            Log.e(
                TAG,
                Log.getStackTraceString(e)
            )
            throw RuntimeException(
                "NEED TO check rtc sdk init fatal error\n" + Log.getStackTraceString(
                    e
                )
            )
        }
    }

    private fun setupVideoConfig() {
        // In simple use cases, we only need to enable video capturing
        // and rendering once at the initialization step.
        // Note: audio recording and playing is enabled by default.
        mRtcEngine!!.enableVideo()

        // Please go to this page for detailed explanation
        // https://docs.agora.io/en/Video/API%20Reference/java/classio_1_1agora_1_1rtc_1_1_rtc_engine.html#af5f4de754e2c1f493096641c5c5c1d8f
        mRtcEngine!!.setVideoEncoderConfiguration(
            VideoEncoderConfiguration(
                VideoEncoderConfiguration.VD_640x360,
                VideoEncoderConfiguration.FRAME_RATE.FRAME_RATE_FPS_15,
                VideoEncoderConfiguration.STANDARD_BITRATE,
                VideoEncoderConfiguration.ORIENTATION_MODE.ORIENTATION_MODE_FIXED_PORTRAIT
            )
        )
    }

    private fun setupLocalVideo() {
        mRtcEngine!!.enableVideo()
        // This is used to set a local preview.
        // The steps setting local and remote view are very similar.
        // But note that if the local user do not have a uid or do
        // not care what the uid is, he can set his uid as ZERO.
        // Our server will assign one and return the uid via the event
        // handler callback function (onJoinChannelSuccess) after
        // joining the channel successfully.
        mLocalView = RtcEngine.CreateRendererView(getBaseContext())
        mLocalView!!.setZOrderMediaOverlay(true)
        mLocalContainer!!.addView(mLocalView)
        // Initializes the local video view.
        // RENDER_MODE_HIDDEN: Uniformly scale the video until it fills the visible boundaries. One dimension of the video may have clipped contents.
        mRtcEngine!!.setupLocalVideo(VideoCanvas(mLocalView, VideoCanvas.RENDER_MODE_HIDDEN, 0))
    }

    private fun joinChannel() {
        // 1. Users can only see each other after they join the
        // same channel successfully using the same app id.
        // 2. One token is only valid for the channel name that
        // you use to generate this token.
        var token: String? = getString(R.string.agora_access_token)
        if (TextUtils.isEmpty(token) || TextUtils.equals(token, "#YOUR ACCESS TOKEN#")) {
            token = null // default, no token
        }
        mRtcEngine!!.joinChannel(token, "sahil", "Extra Optional Data", 0)
    }

    protected override fun onDestroy() {
        super.onDestroy()
        if (!mCallEnd) {
            leaveChannel()
        }
        /*
          Destroys the RtcEngine instance and releases all resources used by the Agora SDK.

          This method is useful for apps that occasionally make voice or video calls,
          to free up resources for other operations when not making calls.
         */RtcEngine.destroy()
    }

    private fun leaveChannel() {
        mRtcEngine!!.leaveChannel()
    }

    fun onLocalAudioMuteClicked(view: View?) {
        mMuted = !mMuted
        // Stops/Resumes sending the local audio stream.
        mRtcEngine!!.muteLocalAudioStream(mMuted)
        val res: Int = if (mMuted) R.drawable.btn_mute else R.drawable.btn_unmute
        mMuteBtn!!.setImageResource(res)
    }

    fun onSwitchCameraClicked(view: View?) {
        // Switches between front and rear cameras.
        mRtcEngine!!.switchCamera()
    }

    fun onCallClicked(view: View?) {
        if (mCallEnd) {
            startCall()
            mCallEnd = false
            mCallBtn!!.setImageResource(R.drawable.btn_endcall)
        } else {
            endCall()
            mCallEnd = true
            mCallBtn!!.setImageResource(R.drawable.btn_startcall)
        }
        showButtons(!mCallEnd)
    }

    private fun startCall() {
        setupLocalVideo()
        joinChannel()
    }

    private fun endCall() {
        removeLocalVideo()
        removeRemoteVideo()
        leaveChannel()
    }

    private fun removeLocalVideo() {
        if (mLocalView != null) {
            mLocalContainer!!.removeView(mLocalView)
        }
        mLocalView = null
    }

    private fun showButtons(show: Boolean) {
        val visibility = if (show) View.VISIBLE else View.GONE
        mMuteBtn!!.visibility = visibility
        mSwitchCameraBtn!!.visibility = visibility
    }

    companion object {
        private val TAG = VideoChatViewActivity::class.java.simpleName
        private const val PERMISSION_REQ_ID = 22

        // Permission WRITE_EXTERNAL_STORAGE is not mandatory
        // for Agora RTC SDK, just in case if you wanna save
        // logs to external sdcard.
        private val REQUESTED_PERMISSIONS = arrayOf(
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }
}