package com.example.luckyseven

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.luckyseven.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment(), OnBackPressedListener  {
    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.btnStart.setOnClickListener {
            if(binding.etName.text.isNotEmpty()) {
                val name = binding.etName.text.toString()
                val navController = view.findNavController()
                val action = WelcomeFragmentDirections.actionWelcomeFragmentToSpinFragment(name)
                navController.navigate(action)
            } else {
                Toast.makeText(context, "Please enter your name...", Toast.LENGTH_LONG).show()
            }
        }
        return view
    }

    override fun onBackPressed() {
        // Show confirmation dialog or perform any action you want
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