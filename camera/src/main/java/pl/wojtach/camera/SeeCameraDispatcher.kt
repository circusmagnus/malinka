package pl.wojtach.camera

import android.app.Activity
import android.content.Context
import android.content.Intent

class SeeCameraDispatcher(val activity: Context) {

    fun navigateToCamera(videoUrl: String) = Intent(activity, FullscreenActivity::class.java)
            .apply { putExtra(CameraFragment.URL_KEY, videoUrl) }
            .let { activity.startActivity(it) }
}