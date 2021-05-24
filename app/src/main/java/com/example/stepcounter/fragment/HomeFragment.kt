package com.example.stepcounter.fragment

import android.content.Intent
import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stepcounter.GoalsReachedActivity
import com.example.stepcounter.R
import com.example.stepcounter.adapter.HistoryAdapter
import com.example.stepcounter.databinding.FragmentHomeBinding
import com.example.stepcounter.db.StoryHelper
import com.example.stepcounter.entity.History
import com.example.stepcounter.helper.MappingHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private lateinit var adapter: HistoryAdapter
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val mFragmentManager = fragmentManager

        binding.rvHistory.layoutManager = LinearLayoutManager(context)
        binding.rvHistory.setHasFixedSize(true)
        adapter = HistoryAdapter(activity!!)
        binding.rvHistory.adapter = adapter

        binding.btnRun.setOnClickListener {
            val mFragmentAdd = AddFragment()

            mFragmentManager?.beginTransaction()?.apply {
                replace(R.id.frame_container, mFragmentAdd, AddFragment::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }
        binding.btnSetting.setOnClickListener {
            val mFragmentSetting = SettingFragment()

            mFragmentManager?.beginTransaction()?.apply {
                replace(R.id.frame_container, mFragmentSetting, SettingFragment::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }

        if (savedInstanceState == null){
            loadHistoryAsync()
        }else{
            val list = savedInstanceState.getParcelableArrayList<History>(EXTRA_STATE)
            if(list!=null){
                adapter.listNotes = list
            }
        }

        return binding.root
    }

    private fun loadHistoryAsync() {
        GlobalScope.launch(Dispatchers.Main){
            val historyHelper = StoryHelper.getInstance(activity!!.applicationContext)
            historyHelper.open()
            val defferedHistory = async(Dispatchers.IO){
                val cursor = historyHelper.queryAll()
                MappingHelper.mapCursorToArrayList(cursor)
            }
//            historyHelper.close()
            val history = defferedHistory.await()
            if(history.size > 0){
                adapter.listNotes = history
            }else{
                adapter.listNotes = ArrayList()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data!= null){
            when (requestCode){
                GoalsReachedActivity.REQUEST_ADD-> if(resultCode == GoalsReachedActivity.RESULT_ADD){
                    val history = data.getParcelableExtra<History>(GoalsReachedActivity.EXTRA_HISTORY) as History
                    adapter.addItem(history)
                    binding.rvHistory.smoothScrollToPosition(adapter.itemCount - 1)

                }

            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listNotes)
    }

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }
}