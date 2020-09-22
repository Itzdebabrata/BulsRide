package com.example.bulsride.ViewPager

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.bulsride.R


class FragmentOne : Fragment(){
    lateinit var next1:TextView
   lateinit var onNextClick: onNextClick
    override fun onAttach(context: Context) {
        super.onAttach(context)
        onNextClick=context as onNextClick
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_one, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        next1=view.findViewById(R.id.next1)
        clicklistener()
    }
    private fun clicklistener(){
        next1.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                onNextClick.onClick()
            }
        })
    }
}
interface onNextClick{
    fun onClick()
}