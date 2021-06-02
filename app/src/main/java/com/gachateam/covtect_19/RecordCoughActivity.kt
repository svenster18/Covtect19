package com.gachateam.covtect_19

import android.Manifest
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.MediaPlayer
import android.media.MediaRecorder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.gachateam.covtect_19.databinding.ActivityRecordCoughBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class RecordCoughActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USER = "extra_user"
        const val fileName = "/user.wav"
    }

    private lateinit var binding: ActivityRecordCoughBinding
    private lateinit var mediaRecorder: MediaRecorder
    private val file = Environment.getExternalStorageDirectory().absolutePath + fileName
    private val fileOutput = File(file)
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecordCoughBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getParcelableExtra<User>(EXTRA_USER)

        mediaRecorder = MediaRecorder()
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder.setOutputFormat(AudioFormat.ENCODING_PCM_16BIT)
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
        mediaRecorder.setAudioChannels(1)
        mediaRecorder.setAudioEncodingBitRate(128000)
        mediaRecorder.setAudioSamplingRate(48000)
        fileOutput.createNewFile()
        mediaRecorder.setOutputFile(fileOutput.toString())

        var isRecord = false

        binding.ibRecord.setOnClickListener {
            isRecord = !isRecord
            if(isRecord) {
                startRecord()
                Toast.makeText(this, "Recording", Toast.LENGTH_SHORT).show()
                binding.ibRecord.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_stop_24))
            } else {
                // stop
                stopRecord()
                Toast.makeText(this, "Stop Recording", Toast.LENGTH_SHORT).show()
                binding.ibRecord.setImageDrawable(resources.getDrawable(R.drawable.ic_mic_white_24))
            }
        }

        mediaPlayer = MediaPlayer()


        var isPlay = false

        binding.ibPlay.setOnClickListener {
            isPlay = !isPlay
            if(isPlay) {
                // play
                Toast.makeText(this, "Play", Toast.LENGTH_SHORT).show()
                binding.ibPlay.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_stop_24))
                play()
            } else {
                // stop
                stop()
                Toast.makeText(this, "Stop", Toast.LENGTH_SHORT).show()
                binding.ibPlay.setImageDrawable(resources.getDrawable(R.drawable.ic_play_arrow_white_24))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (fileOutput.exists()) {
            fileOutput.delete()
        }
    }

    private fun startRecord() {
        try {
            if (fileOutput.exists()) {
                fileOutput.delete()
            }
            mediaRecorder.setOutputFile(fileOutput.toString())
            mediaRecorder.prepare()
            mediaRecorder.start()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun stopRecord() {
        mediaRecorder.stop()
        mediaRecorder.release()
    }

    private fun play() {
        try {
            mediaPlayer.setDataSource(fileOutput.toString())
            mediaPlayer.prepare()
            mediaPlayer.start()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun stop() {
        mediaPlayer.stop()
        mediaPlayer.release()
    }
}