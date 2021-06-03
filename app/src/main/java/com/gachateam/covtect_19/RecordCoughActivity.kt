package com.gachateam.covtect_19

import android.Manifest
import android.content.pm.PackageManager
import android.media.*
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.gachateam.covtect_19.databinding.ActivityRecordCoughBinding
import java.io.File
import java.io.IOException

class RecordCoughActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val EXTRA_USER = "extra_user"
        const val RECORD_PERMISSION = Manifest.permission.RECORD_AUDIO
        const val WRITE_PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE
        const val READ_PERMISSION = Manifest.permission.READ_EXTERNAL_STORAGE
        const val RECORD_PERMISSION_CODE = 21;
        const val WRITE_PERMISSION_CODE = 31;
        const val READ_PERMISSION_CODE = 41;
    }

    private lateinit var binding: ActivityRecordCoughBinding
    private var mediaRecorder: MediaRecorder? = null
    private lateinit var fileName: String
    private lateinit var filePath: String
    private var isRecord = false
    private var mMediaPlayer: MediaPlayer? = null
    private var isReady: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecordCoughBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getParcelableExtra<User>(EXTRA_USER)!!

        filePath = this.getExternalFilesDir("/")?.absolutePath.toString()
        fileName = "${user?.userId}.wav"
        binding.ibRecord.setOnClickListener(this)

        binding.ibPlay.setOnClickListener (this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            binding.ibRecord.id -> {
                if(isRecord) {
                    // stop Record
                    stopRecord()
                    Toast.makeText(this, "Stop Recording", Toast.LENGTH_SHORT).show()
                    binding.ibRecord.setImageDrawable(resources.getDrawable(R.drawable.ic_mic_white_24))
                    isRecord = false
                } else {
                    if (checkPermissions()) {
                        startRecord()
                        Toast.makeText(this, "Recording", Toast.LENGTH_SHORT).show()
                        binding.ibRecord.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_stop_24))
                        isRecord = true
                    }
                }
            }
            binding.ibPlay.id -> {
                playAndStop()
            }
            binding.btnKirim.id -> {

            }
        }
    }

    private fun checkPermissions(): Boolean {
        var isRecordGranted = false
        var isWriteGranted = false
        var isReadGranted = false
        if (ActivityCompat.checkSelfPermission(this, RECORD_PERMISSION) == PackageManager.PERMISSION_GRANTED) {
            isRecordGranted = true
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(RECORD_PERMISSION), RECORD_PERMISSION_CODE)
            isRecordGranted = false
        }
        if (ActivityCompat.checkSelfPermission(this, WRITE_PERMISSION) == PackageManager.PERMISSION_GRANTED) {
            isWriteGranted = true
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(WRITE_PERMISSION), WRITE_PERMISSION_CODE)
            isWriteGranted = false
        }
        if (ActivityCompat.checkSelfPermission(this, READ_PERMISSION) == PackageManager.PERMISSION_GRANTED) {
            isReadGranted = true
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(READ_PERMISSION), READ_PERMISSION_CODE)
            isReadGranted = false
        }
        return isRecordGranted && isWriteGranted && isReadGranted
    }
    private fun startRecord() {
        val file = File(filePath + "/" + fileName)
        if (file.exists()) {
            file.delete()
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
                mMediaPlayer?.prepareAsync()
            } else {
                if (mMediaPlayer?.isPlaying() as Boolean || isReady) {
                    mMediaPlayer?.stop()
                    Toast.makeText(this, "Stop", Toast.LENGTH_SHORT).show()
                    binding.ibPlay.setImageDrawable(resources.getDrawable(R.drawable.ic_play_arrow_white_24))
                    isReady = false
                }else {
                    Toast.makeText(this, "Play", Toast.LENGTH_SHORT).show()
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
}