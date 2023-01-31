package com.example.swuvoca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.swuvoca.databinding.ActivityMainBinding
import com.example.swuvoca.dic.DicActivity
import com.example.swuvoca.quiz.QuizActivity
import com.example.swuvoca.check.StarCheckActivity
import com.example.swuvoca.study.StudyActivity
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var myDBHelper: MyDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init(){
        myDBHelper = MyDBHelper(this)
        binding.apply {
            dicView.setOnClickListener {
                val intent = Intent(this@MainActivity, DicActivity::class.java)
                startActivity(intent)
            }
            studyView.setOnClickListener {
                val intent = Intent(this@MainActivity, StudyActivity::class.java)
                startActivity(intent)
            }
            quizView.setOnClickListener {
                val intent = Intent(this@MainActivity, QuizActivity::class.java)
                startActivity(intent)
            }
            starView.setOnClickListener {
                val intent = Intent(this@MainActivity, StarCheckActivity::class.java)
                startActivity(intent)
            }
            }
        }
    }
