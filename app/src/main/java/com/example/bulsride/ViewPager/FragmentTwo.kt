package com.example.bulsride.ViewPager

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.bulsride.R

class FragmentTwo : Fragment() {
    lateinit var next2:TextView
    lateinit var back:TextView
    lateinit var onNextTwo: onNextTwo
    override fun onAttach(context: Context) {
        super.onAttach(context)
        onNextTwo=context as onNextTwo
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_two, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        next2=view.findViewById(R.id.next2)
        back=view.findViewById(R.id.back)
        clickelistener()
    }
    private fun clickelistener(){
        back.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                onNextTwo.onClikBack()
            }
        })
        next2.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                onNextTwo.onClickNext()
            }
        })
    }

}
interface onNextTwo{
    fun onClickNext()
    fun onClikBack()
}