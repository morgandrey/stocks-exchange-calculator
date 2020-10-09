package com.example.stockexchangecalculator

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.stockexchangecalculator.databinding.FragmentTitleBinding

class TitleFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentTitleBinding>(inflater,
            R.layout.fragment_title,container,false)

        setHasOptionsMenu(true)

        binding.stockReturnsButton.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_titleFragment_to_stockReturnsFragment)
        }
        binding.dividendButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_titleFragment_to_dividendFragment)
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.
        onNavDestinationSelected(item,requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }
}