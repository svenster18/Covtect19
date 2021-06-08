package com.gachateam.covtect_19

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.media.*
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import com.gachateam.covtect_19.databinding.ActivityRecordCoughBinding
import com.gachateam.covtect_19.databinding.FragmentPersonalInformationBinding
import com.gachateam.covtect_19.databinding.FragmentRecordCoughBinding
import java.io.File
import java.io.IOException
import java.lang.Exception

class RecordCoughFragment : Fragment(){

    companion object {
        const val EXTRA_USER = "extra_user"
        const val RECORD_PERMISSION = Manifest.permission.RECORD_AUDIO
        const val WRITE_PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE
        const val READ_PERMISSION = Manifest.permission.READ_EXTERNAL_STORAGE
        const val RECORD_PERMISSION_CODE = 21;
        const val WRITE_PERMISSION_CODE = 31;
        const val READ_PERMISSION_CODE = 41;
    }

    private var mediaRecorder: MediaRecorder? = null
    private lateinit var fileName: String
    private lateinit var filePath: String
    private lateinit var file: File
    private var isRecord = false
    private var mMediaPlayer: MediaPlayer? = null
    private var isReady: Boolean = false
    private var _binding: FragmentRecordCoughBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRecordCoughBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user = RecordCoughFragmentArgs.fromBundle(arguments as Bundle).user

        filePath = activity?.getExternalFilesDir("/")?.absolutePath.toString()
        fileName = "${user?.userId}_cough.wav"

        binding.ibRecord.setOnClickListener {
            if(isRecord) {
                // stop Record
                stopRecord()
                Toast.makeText(activity, "Stop Recording", Toast.LENGTH_SHORT).show()
                binding.ibRecord.setImageDrawable(resources.getDrawable(R.drawable.ic_mic_white_24))
                isRecord = false
            } else {
                if (checkPermissions()) {
                    startRecord()
                    Toast.makeText(activity, "Recording", Toast.LENGTH_SHORT).show()
                    binding.ibRecord.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_stop_24))
                    isRecord = true
                }
            }
        }
        binding.ibPlay.setOnClickListener {
            try {
                playAndStop()
            }
            catch (e : Exception) {
                Toast.makeText(activity, resources.getString(R.string.play_error), Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnKirim.setOnClickListener {
            val toRecordCoughFragment =
                RecordCoughFragmentDirections.actionRecordCoughFragmentToRecordBreathFragment(user)
            view.findNavController().navigate(toRecordCoughFragment)
        }
    }

    private fun checkPermissions(): Boolean {
        var isRecordGranted = false
        var isWriteGranted = false
        var isReadGranted = false
        if (ActivityCompat.checkSelfPermission(requireActivity(), RECORD_PERMISSION) == PackageManager.PERMISSION_GRANTED) {
            isRecordGranted = true
        } else {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(RECORD_PERMISSION),
                RECORD_PERMISSION_CODE
            )
            isRecordGranted = false
        }
        if (ActivityCompat.checkSelfPermission(requireActivity(), WRITE_PERMISSION) == PackageManager.PERMISSION_GRANTED) {
            isWriteGranted = true
        } else {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(WRITE_PERMISSION),
                WRITE_PERMISSION_CODE
            )
            isWriteGranted = false
        }
        if (ActivityCompat.checkSelfPermission(requireActivity(), READ_PERMISSION) == PackageManager.PERMISSION_GRANTED) {
            isReadGranted = true
        } else {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(RecordCoughActivity.READ_PERMISSION),
                READ_PERMISSION_CODE
            )
            isReadGranted = false
        }
        return isRecordGranted && isWriteGranted && isReadGranted
    }
    private fun startRecord() {
        file = File(filePath + "/" + fileName)
        if (file.exists()) {
            file?.delete()
        }
        mediaRecorder = MediaRecorder()
        mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder?.setOutputFormat(AudioFormat.ENCODING_PCM_16BIT)
        mediaRecorder?.setOutputFile(filePath + "/" + fileName)
        mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
        mediaRecorder?.setAudioChannels(1)
        mediaRecorder?.setAudioEncodingBitRate(128000)
        mediaRecorder?.setAudioSamplingRate(48000)

        try {
            mediaRecorder?.prepare()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        mediaRecorder?.start()
    }

    private fun stopRecord() {
        mediaRecorder?.stop()
        mediaRecorder?.release()
        mediaRecorder = null
    }

    private fun playAndStop() {
        init()
        try {
            //mediaPlayer.setDataSource()
            if (!isReady) {
                mMediaPlayer?.prepare()
            } else {
                if (mMediaPlayer?.isPlaying() as Boolean || isReady) {
                    mMediaPlayer?.stop()
                    Toast.makeText(activity, "Stop", Toast.LENGTH_SHORT).show()
                    binding.ibPlay.setImageDrawable(resources.getDrawable(R.drawable.ic_play_arrow_white_24))
                    isReady = false
                }else {
                    Toast.makeText(activity, "Play", Toast.LENGTH_SHORT).show()
                    binding.ibPlay.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_stop_24))
                    mMediaPlayer?.start()
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun init() {
        mMediaPlayer = MediaPlayer()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val attribute = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()
            mMediaPlayer?.setAudioAttributes(attribute)
        } else {
            mMediaPlayer?.setAudioStreamType(AudioManager.STREAM_MUSIC)
        }
        try {
            mMediaPlayer?.setDataSource(filePath + "/" + fileName)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        mMediaPlayer?.setOnPreparedListener({
            isReady = true
            mMediaPlayer?.start()
        })
        mMediaPlayer?.setOnErrorListener({ mp, what, extra -> false })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}