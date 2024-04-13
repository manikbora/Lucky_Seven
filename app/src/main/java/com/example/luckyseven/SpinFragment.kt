package com.example.luckyseven

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import com.example.luckyseven.databinding.FragmentSpinBinding
import java.util.Random

interface OnBackPressedListener {
    fun onBackPressed()
}

class SpinFragment : Fragment(), OnBackPressedListener {

    private var _binding: FragmentSpinBinding? = null
    private val binding get() = _binding!!

    private val images = arrayOf(
        R.drawable.zero,
        R.drawable.one,
        R.drawable.two,
        R.drawable.three,
        R.drawable.four,
        R.drawable.five,
        R.drawable.six,
        R.drawable.seven,
        R.drawable.eight,
        R.drawable.nine,
        R.drawable.ten
    )

    private var currentIndex = 0
    private val handler = Handler()
    private var randomNumber = 0
    var name = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSpinBinding.inflate(inflater, container, false)
        val view = binding.root

        name = SpinFragmentArgs.fromBundle(requireArguments()).name

        binding.btnSpin.setOnClickListener {
            currentIndex = 0
            displayNextImage()
        }

        binding.btnCheckGift.setOnClickListener {
            val navController = view.findNavController()
            navController.navigate(R.id.action_spinFragment_to_prizeFragment)
        }
        return view
    }

    private fun displayNextImage() {
        if(currentIndex < images.size) {
            val randomIndex = Random().nextInt(images.size)
            randomNumber = randomIndex
            binding.ivRandomNumber.setImageResource(images[randomIndex])
            currentIndex++
            binding.btnSpin.isEnabled = false
            handler.postDelayed({displayNextImage()}, 250)
        } else {
            checkResult()
        }

    }

    private fun checkResult() {
        if(randomNumber == 7) {
            binding.tvResultStatus.isVisible = true
            binding.tvResultStatus.text = "Congrats $name...\nYou have won the game.\nPlease check your gift..."
            binding.ivWinner.isVisible = true
            binding.btnCheckGift.isVisible = true
            binding.btnSpin.isEnabled = false
        } else {
            binding.tvResultStatus.isVisible = false
            binding.ivWinner.isVisible = false
            binding.btnCheckGift.isVisible = false
            binding.btnSpin.isEnabled = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }

    override fun onBackPressed() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setTitle("Confirmation")
        alertDialogBuilder.setMessage("Are you sure you want to exit the app?")
        alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
            requireActivity().finish()
        }
        alertDialogBuilder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

}