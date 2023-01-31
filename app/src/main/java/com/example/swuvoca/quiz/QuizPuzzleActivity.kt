package com.example.swuvoca.quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.view.ViewCompat
import com.example.swuvoca.MyDBHelper
import com.example.swuvoca.R
import com.example.swuvoca.Voca
import com.example.swuvoca.VocaInfo
import com.example.swuvoca.databinding.ActivityQuizPuzzleBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class QuizPuzzleActivity : AppCompatActivity() {
    lateinit var binding: ActivityQuizPuzzleBinding
    lateinit var myDBHelper: MyDBHelper

    lateinit var currentWord:String
    var currentPos:Int = 0 //현재 답의 word안에서 한글자 위치
    var viewList = mutableListOf<Int>()
    var answer:Int = 0 // 현재 답의 view에서의 위치
    var quizList = ArrayList<Voca>()
    var arraylist = ArrayList<Int>()
    var insert = ""
    val random = Random()
    var pos:Int = 0 //현재 문제 position
    val scope = CoroutineScope(Dispatchers.Main)
    val alphabetList = arrayListOf('a', 'b', 'c', 'd', 'e','f', 'g', 'h', 'i', 'j',
        'k', 'l','m','n','o','p','q','r','s','t','u','v','w','x','y','z')

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizPuzzleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initData()
        initView()
        playQuiz()
    }
    private fun initData(){
        myDBHelper = MyDBHelper(this)
        quizList = intent.getSerializableExtra("quiz") as ArrayList<Voca>
    }
    private fun initView(){
        binding.apply {
            view1.setOnClickListener {
                onClick(it as TextView)
            }
            view2.setOnClickListener {
                onClick(it as TextView)
            }
            view3.setOnClickListener {
                onClick(it as TextView)
            }
            view4.setOnClickListener {
                onClick(it as TextView)
            }
            view5.setOnClickListener {
                onClick(it as TextView)
            }
            view6.setOnClickListener {
                onClick(it as TextView)
            }
            view7.setOnClickListener {
                onClick(it as TextView)
            }
            view8.setOnClickListener {
                onClick(it as TextView)
            }

        }
    }
    private fun onClick(view:TextView){
        insert += view.text
        Log.i("hhh", "currentPos : $currentPos, viewList size: ${viewList.size}")
        findViewById<TextView>(viewList[currentPos]).text = view.text
        currentPos++
        if(currentPos==currentWord.length){
            if(insert==quizList[pos].word.replace(" ",""))
                quizResult(true)
            else
                quizResult(false)
        }
        else
            mixPuzzle()
    }
    private fun quizResult(res:Boolean){
        if(res){
            binding.wordLayout.setBackgroundResource(R.drawable.card_blue)
            binding.wordView.setTextColor(getColor(R.color.hit))
        }
        else{
            binding.wordLayout.setBackgroundResource(R.drawable.card_red)
            binding.wordView.setTextColor(getColor(R.color.nohit))
            myDBHelper.insertWrong(VocaInfo(quizList[pos].word, quizList[pos].mean, 4, insert))
        }
        binding.wordView.visibility = View.VISIBLE

        scope.launch {
            delay(3000L)
            binding.wordView.visibility = View.INVISIBLE
            delay(500L)
            binding.wordLayout.setBackgroundResource(R.drawable.card_layout)
            pos++
            if(pos<quizList.size){
                playQuiz()
            }
            else{
                finish()
            }
        }

    }
    private fun mixPuzzle(){
        answer = random.nextInt(8)
        var rand = 0
        arraylist.clear()
        for(i in 0 until 8){
            do {
                rand = random.nextInt(quizList.size)
            }while (arraylist.contains(rand)||alphabetList[rand]==currentWord[currentPos])
            arraylist.add(rand)
        }
        arraylist[answer] = currentWord[currentPos]-'a'
        binding.apply {
            view1.text = alphabetList[arraylist[0]].toString()
            view2.text = alphabetList[arraylist[1]].toString()
            view3.text = alphabetList[arraylist[2]].toString()
            view4.text = alphabetList[arraylist[3]].toString()
            view5.text = alphabetList[arraylist[4]].toString()
            view6.text = alphabetList[arraylist[5]].toString()
            view7.text = alphabetList[arraylist[6]].toString()
            view8.text = alphabetList[arraylist[7]].toString()
        }
    }
    private fun playQuiz(){
        binding.apply {
            wordView.text = quizList[pos].word
            meanView.text = quizList[pos].mean
            quizPos.text = "$pos/${quizList.size}"
            currentWord = quizList[pos].word.replace(" ", "")
            //Toast.makeText(this@QuizPuzzleActivity, currentWord, Toast.LENGTH_LONG).show()
            currentPos = 0
            insert = ""
            insertWord.removeAllViews()
            viewList.clear()
            for(i in currentWord.toCharArray()){
                val nView = layoutInflater.inflate(R.layout.one_word, insertWord, false)
                nView.id = ViewCompat.generateViewId()
                viewList.add(nView.id)
                insertWord.addView(nView)
            }
            mixPuzzle()
        }

    }
}