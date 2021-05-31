package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.rsschool.android2021.interfaces.sendMinMaxValuesListener

class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null
    private var minEditText: EditText? = null
    private var maxEditText: EditText? = null
    private var listener: sendMinMaxValuesListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as sendMinMaxValuesListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)
        minEditText = view.findViewById(R.id.min_value)
        maxEditText = view.findViewById(R.id.max_value)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = "Previous result: ${result.toString()}"

        generateButton?.setOnClickListener {
            if (isMinFieldEmpty() || isMaxFieldEmpty()) {
                Toast.makeText(this.context, "one or two boxes are empty", Toast.LENGTH_SHORT).show()
            } else {

                val min = minEditText?.text.toString().toIntOrNull()
                val max = maxEditText?.text.toString().toIntOrNull()
                if (min != null && max != null && max >= 0 && min >= 0) {
                    if (isValidValues(min, max)) {
                        listener?.onSendMinMaxValues(min, max)
                    } else {
                        Toast.makeText(this.context,
                            "the value of the \"min\" field must be less than or equal to the \"max\" field value\"",
                            Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this.context,
                        "max value = ${Int.MAX_VALUE}, min value = 0",
                        Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun isMinFieldEmpty(): Boolean {
        return minEditText?.text.toString() == ""
    }

    private fun isMaxFieldEmpty(): Boolean {
        return maxEditText?.text.toString() == ""
    }

    private fun isValidValues(min: Int, max: Int): Boolean {
        return min <= max
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }
}