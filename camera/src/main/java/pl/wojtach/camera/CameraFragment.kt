package pl.wojtach.camera

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import com.google.firebase.storage.FirebaseStorage
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.storage.FileDownloadTask
import com.google.android.gms.tasks.OnSuccessListener
import kotlinx.android.synthetic.main.fragment_video.*
import kotlinx.android.synthetic.main.fragment_video.view.*
import java.io.File


class CameraFragment : Fragment() {

    private val videoUrl by lazy {
        arguments?.getString(URL_KEY) ?: ""
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_video, container, false)
                    .apply {
                        with(alert_video) {
                            setMediaController(MediaController(activity))
                            setVideoPath(videoUrl)
                        }
                    }

    override fun onResume() {
        super.onResume()
        alert_video.start()
    }

    companion object {
        const val URL_KEY = "url key"

        fun newInstance(videoUrl: String): CameraFragment =
                CameraFragment().apply { arguments = Bundle().apply { putString(URL_KEY, videoUrl) } }
    }

}