package com.example.plantapp.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.plantapp.viewmodel.PlantViewModel
import com.example.plantapp.R
import com.example.plantapp.databinding.FragmentPlantDetailsBinding
import com.example.plantapp.model.local.PlantData
import kotlinx.coroutines.launch

class PlantDetailsFragment : Fragment() {

    private lateinit var mBinding : FragmentPlantDetailsBinding
    private val viewModel : PlantViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentPlantDetailsBinding.inflate(inflater,container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val plantId = arguments?.getInt("plantId",-1) ?: -1
        var plantDetails : PlantData? = null

        viewLifecycleOwner.lifecycleScope.launch {
            plantDetails = viewModel.getOnePlant(plantId)
            val imgUrl : String
            if (plantDetails != null){
                mBinding.tvName.text = plantDetails?.name
                mBinding.tvDescription.text = plantDetails?.description
                mBinding.tvType.text = getString(R.string.plant_type,plantDetails?.type)
                mBinding.tbPlant.title = getString(R.string.inside_item,plantDetails?.name)
                imgUrl = plantDetails?.imgSrc.toString()
                Glide.with(mBinding.root)
                    .load(imgUrl)
                    .fitCenter()
                    .placeholder(R.drawable.ic_photo)
                    .error(R.drawable.ic_no_photo)
                    .into(mBinding.imgDetails)

            }
        }

        mBinding.fabBack.setOnClickListener {   parentFragmentManager.popBackStack()  }

        mBinding.fabEmail.setOnClickListener {
            if (plantDetails != null){
                val subject = getString(
                    R.string.consulta_subject,
                    plantDetails?.name,plantDetails?.id.toString())

                val message = getString(
                    R.string.consulta_message,
                    plantDetails?.name)

                val builder = AlertDialog.Builder(requireContext())

                builder.setTitle(getString(R.string.dialog_title_message_mail))
                builder.setIcon(R.drawable.ic_question)
                builder.setMessage(getString(R.string.dialog_message_email))
                builder.setPositiveButton(getString(R.string.btn_positive_text)){ _, _ ->
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type =getString(R.string.email_format)
                    intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.email_to_send)))
                    intent.putExtra(Intent.EXTRA_SUBJECT,subject)
                    intent.putExtra(Intent.EXTRA_TEXT,message)
                    startActivity(Intent.createChooser(intent,getString(R.string.intent_name)))
                }
                builder.setNegativeButton(getString(R.string.btn_negative_text)) { dialog, _ ->
                    dialog.dismiss()
                }
                builder.show()


            }

        }
    }


}