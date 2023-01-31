package com.example.swuvoca.quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.swuvoca.MyDBHelper
import com.example.swuvoca.R
import com.example.swuvoca.Voca
import com.example.swuvoca.databinding.ActivityQuizBinding
import com.example.swuvoca.databinding.PagerrowBinding

class QuizActivity : AppCompatActivity() {
    val items = arrayListOf("day1","day2","day3","day4","day5","day6")

    lateinit var binding: ActivityQuizBinding
    lateinit var quizList: ArrayList<Voca>
    lateinit var spinnerAdapter:ArrayAdapter<String>
    lateinit var myDBHelper: MyDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        initView()
    }
    private fun init(){
        myDBHelper = MyDBHelper(this)
        quizList = arrayListOf()
        spinnerAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item,items)
        spinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        binding.quizSpinner.adapter = spinnerAdapter

    }
    private fun initView(){
        binding.apply {
            viewPager.adapter = RecyclerViewAdapter()
            viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            viewPager.offscreenPageLimit = 3
            //viewPager2.currentItem = 1000
            val pageMargin = resources.getDimensionPixelOffset(R.dimen.pageMargin).toFloat()
            val pageOffset = resources.getDimensionPixelOffset(R.dimen.offset).toFloat()
            viewPager.setPageTransformer { page, position ->
                val myOffset = position * -(2 * pageOffset + pageMargin)
                if (position < -1) {
                    page.alpha = 0f
                    page.translationX = -myOffset
                } else if (position <= 1) {
                    val scaleFactor = Math.max(0.7f, 1 - Math.abs(position - 0.14285715f))
                    page.translationX = myOffset
                    page.scaleY = scaleFactor
                    page.alpha = scaleFactor
                } else {
                    page.alpha = 0f
                    page.translationX = myOffset
                }
            }
            quizSpinner.onItemSelectedListener = object:AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    quizList = myDBHelper.getDayVoca(position+1, false) as ArrayList<Voca>
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }

        }
    }
    inner class RecyclerViewAdapter() : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {
        val items = arrayListOf<String>("Mutliple Choice Quiz", "Short Answer Quiz", "Listening Quiz", "Puzzle Quiz")
        val images = arrayListOf(R.drawable.ic_baseline_menu_24,R.drawable.ic_baseline_menu_book_24,
            R.drawable.ic_baseline_volume_up_24, R.drawable.ic_baseline_search_24
        )
        val itemInfos = arrayListOf("객관식", "주관식", "듣기", "퍼즐")
        val colors = arrayListOf(android.R.color.holo_green_light,
            android.R.color.holo_blue_light, R.color.third, R.color.grey
        )

        inner class MyViewHolder(val binding: PagerrowBinding): RecyclerView.ViewHolder(binding.root){
            init {
                binding.itemview.setOnClickListener {
                    when(adapterPosition){
                        0->{
                            if(quizList.size==0)
                                quizList = myDBHelper.getDayVoca(1, false) as ArrayList<Voca>
                            val intent = Intent(this@QuizActivity, QuizMutlipleActivity::class.java)
                            intent.putExtra("quiz", quizList)
                            startActivity(intent)
                        }
                        1->{
                            if(quizList.size==0)
                                quizList = myDBHelper.getDayVoca(1, false) as ArrayList<Voca>
                            val intent = Intent(this@QuizActivity, QuizEssayActivity::class.java)
                            intent.putExtra("quiz", quizList)
                            startActivity(intent)
                        }
                        2->{
                            if(quizList.size==0)
                                quizList = myDBHelper.getDayVoca(1, false) as ArrayList<Voca>
                            val intent = Intent(this@QuizActivity, QuizListeningActivity::class.java)
                            intent.putExtra("quiz", quizList)
                            startActivity(intent)
                        }
                        3->{
                            if(quizList.size==0)
                                quizList = myDBHelper.getDayVoca(1, false) as ArrayList<Voca>
                            val intent = Intent(this@QuizActivity, QuizPuzzleActivity::class.java)
                            intent.putExtra("quiz", quizList)
                            startActivity(intent)
                        }

                    }
                }

            }
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewAdapter.MyViewHolder {
            val view = PagerrowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return MyViewHolder(view)
        }

        override fun onBindViewHolder(holder: RecyclerViewAdapter.MyViewHolder, position: Int) {
            holder.binding.apply {
                imageView.setImageDrawable(getDrawable(images[position]))
                itemview.setBackgroundColor(getColor(colors[position]))
                textView.text = items[position]
                textView2.text = itemInfos[position]
            }
        }

        override fun getItemCount(): Int = items.size

    }

}