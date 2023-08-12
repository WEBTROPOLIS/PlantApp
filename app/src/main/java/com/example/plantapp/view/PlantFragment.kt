package com.example.plantapp.view


import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.plantapp.viewmodel.PlantAdapter
import com.example.plantapp.viewmodel.PlantViewModel
import com.example.plantapp.R
import com.example.plantapp.databinding.FragmentPlantBinding
import kotlinx.coroutines.launch


class PlantFragment : Fragment(), OnClickListenerPlant {

    private lateinit var mBinding : FragmentPlantBinding
    private val viewModel : PlantViewModel by viewModels()
    private lateinit var adapter: PlantAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentPlantBinding.inflate(inflater,container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = PlantAdapter(mutableListOf(),this,this )
        mBinding.rvPlant.adapter = adapter
        mBinding.rvPlant.layoutManager = GridLayoutManager(requireContext(),2)

        viewModel.getAllPlant().observe(viewLifecycleOwner) {plantList ->
            adapter.updateCount(plantList)
        }
        mBinding.fabExit.setOnClickListener { showDialogExit() }

        mBinding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                    val searchString = it.toString()
                    searchPlants(searchString)
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

    }

    private fun searchPlants(query: String) {
            viewLifecycleOwner.lifecycleScope.launch {
                val plantList = viewModel.searchPlants(query)
                adapter.updateCount(plantList)
            }

        if (query.isEmpty()){
            hideKeyboard()
        }


    }

    private fun hideKeyboard() {
        val inputMethodManager =
            mBinding.root.context.
            getSystemService(Context.INPUT_METHOD_SERVICE)
                    as? InputMethodManager

        val view = requireActivity().currentFocus
        view?.let {
            inputMethodManager?.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    private fun showDialogExit() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.title_exit_dialog))
        builder.setIcon(R.drawable.ic_info)
        builder.setMessage(getString(R.string.message_exit_dialog))
        builder.setPositiveButton(getString(R.string.action_exit_positive)) { dialog, _ ->

            requireActivity().finish()
            dialog.dismiss()
        }
        builder.setNegativeButton(getString(R.string.action_exit_negative)) { dialog, _ ->

            dialog.dismiss()
        }
        builder.show()
    }

    fun updateCount(message : String){
        mBinding.tvCountItem.text = message
    }

    override fun onClick(plantId: Int) {
        val fragment = PlantDetailsFragment()
        val bundle =Bundle()
        bundle.putInt("plantId",plantId)
        fragment.arguments = bundle
        parentFragmentManager.beginTransaction()
            .replace(R.id.frame,fragment)
            .addToBackStack(null)
            .commit()
    }
}