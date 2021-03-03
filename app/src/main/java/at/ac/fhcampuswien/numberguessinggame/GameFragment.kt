package at.ac.fhcampuswien.numberguessinggame

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import at.ac.fhcampuswien.numberguessinggame.databinding.FragmentGameBinding


class GameFragment : Fragment() {

    private lateinit var viewModel: GameViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentGameBinding =
            DataBindingUtil.inflate<FragmentGameBinding>(
                // Inflate the layout for this fragment
                inflater, R.layout.fragment_game, container, false
            )

        // Get the viewmodel
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)
        binding.viewModelGame = viewModel
        binding.lifecycleOwner = this


        /**
         * Observer
         */
        viewModel.message.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        })

        viewModel.eventGameFinish.observe(viewLifecycleOwner, Observer { isFinished ->
            if (isFinished) {
                findNavController().navigate(R.id.action_GameFragment_to_GameWonFragment)
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_guess).setOnClickListener {
            //findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)

            val firstNum = view.findViewById<EditText>(R.id.editTextNumber1).text.toString()
            val secondNum = view.findViewById<EditText>(R.id.editTextNumber2).text.toString()
            val thirdNum = view.findViewById<EditText>(R.id.editTextNumber3).text.toString()
            val fourthNum = view.findViewById<EditText>(R.id.editTextNumber4).text.toString()
            val firstNumInt = firstNum.toInt()
            val secondNumInt = secondNum.toInt()
            val thirdNumInt = thirdNum.toInt()
            val fourthNumInt = fourthNum.toInt()

            if (inputCheck(firstNum, secondNum, thirdNum, fourthNum)) {
                Log.i("GameFragment", "$firstNum, $secondNum, $thirdNum, $fourthNum")

                viewModel.guess(firstNumInt, secondNumInt, thirdNumInt, fourthNumInt)
            }
        }
    }

    private fun inputCheck(
        firstNum: String,
        secondNum: String,
        thirdNum: String,
        fourthNum: String
    ): Boolean {
        return !(TextUtils.isEmpty(firstNum) || TextUtils.isEmpty(secondNum) ||
                TextUtils.isEmpty(thirdNum) || TextUtils.isEmpty(fourthNum))
    }
}