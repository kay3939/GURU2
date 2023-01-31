package com.example.swuvoca.dic

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.swuvoca.MyDBHelper
import com.example.swuvoca.MyViewModel
import com.example.swuvoca.Voca
import com.example.swuvoca.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {
    lateinit var binding: FragmentSearchBinding
    lateinit var adapter:MyRecyclerAdapter
    lateinit var myDBHelper: MyDBHelper

    val myViewModel: MyViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myDBHelper = MyDBHelper(requireContext())
        binding.searchRecyclerView.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false)


        adapter = MyRecyclerAdapter(myDBHelper.getAllRecord())
        adapter.itemClickListener = object: MyRecyclerAdapter.OnItemClickListener{
            override fun OnStarChecked(holder: RecyclerView.ViewHolder,data:Voca, pos:Int, star:Int) {
                myDBHelper.updateStar(data, star)
            }
        }
        binding.searchRecyclerView.adapter = adapter

        binding.searchEdit.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                val str = s.toString()
                adapter.filter.filter(str)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        myViewModel.addList.observe(viewLifecycleOwner, Observer<Voca>{
            adapter.addVoca(it)
        })
    }

}