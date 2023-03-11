package cc.wecando.idchanger.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import cc.wecando.idchanger.LiveDataState
import cc.wecando.idchanger.adapter.AppListAdapter
import cc.wecando.idchanger.databinding.FragmentAppListBinding

class AppListFragment : Fragment() {
    private lateinit var binding: FragmentAppListBinding
    private val viewModel: LoadViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAppListBinding.inflate(inflater, container, false)
        initList()
        return binding.root
    }

    private fun initList() {
        val appListAdapter = AppListAdapter()
        binding.rvAppList.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            this.adapter = appListAdapter
        }
        viewModel.liveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is LiveDataState.Error<*> -> Log.d("AppListFragment", "loading")
                is LiveDataState.Loading<*> -> Log.d("AppListFragment", "error")
                is LiveDataState.Success -> appListAdapter.submitList(state.data)
            }
        }
        viewModel.loadAndroidId()

    }
}