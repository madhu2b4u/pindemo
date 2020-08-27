package com.pindemo.pin.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.pindemo.R
import com.pindemo.pin.viewmodel.PinViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_pin.*


class PinFragment : DaggerFragment() {

    private lateinit var mPinViewModel: PinViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenStarted {
            mPinViewModel = ViewModelProviders.of(this@PinFragment).get(
                PinViewModel::class.java)

            setLiveData()

            btnGeneratePin.setOnClickListener { mPinViewModel.computeBlock(etPin.text.toString()) }
        }
    }


    private fun setLiveData() {

        //apply result on the view
        mPinViewModel.pinBlockEntryLiveData.observe(viewLifecycleOwner, Observer {
            tvPinBlock.text = it
        })

        //shows progress bar
        mPinViewModel.progressLiveData.observe(viewLifecycleOwner, Observer {
            pbLoadPin.visibility = if (it == true) View.VISIBLE else View.GONE
        })

        //returns error or exception thrown
        mPinViewModel.pinErrorLiveData.observe(viewLifecycleOwner, Observer {
            if (it != 0) {
                Snackbar.make(main_layout, getString(it), Snackbar.LENGTH_LONG).show()
                tvPinBlock.text = ""
            }
        })

    }


}
