package si.eestec.challenge.georgeai

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import androidx.core.os.bundleOf
import java.util.Calendar
import si.eestec.challenge.georgeai.databinding.FragmentAddNewTaskBinding

class AddNewTaskFragment : Fragment() {


    private var _binding: FragmentAddNewTaskBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddNewTaskBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnShowDatePicker()
        btnShowTimePicker();


    }

    private fun btnShowTimePicker(){
        binding.timePickerBtn.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar[Calendar.HOUR_OF_DAY]
            val minute = calendar[Calendar.MINUTE]
            val timePickerDialog = TimePickerDialog(requireContext(),
                { view, hourOfDay, minute ->
                    val time = String.format("%02d:%02d", hourOfDay, minute)
                    binding.timeInputEditText.setText("$time")
//                    binding.timeInputEditText.visibility = View.VISIBLE // Make TextView visible
                }, 0, 0, true
            )
            timePickerDialog.show()
        }
    }

    private fun btnShowDatePicker(){
        binding.datePicketBtn.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->

                    val selectedDate = "$dayOfMonth/${month + 1}/$year"
                    binding.dateInputEditText.setText(selectedDate)
                },
                year,
                month,
                dayOfMonth
            )

            // Show the DatePickerDialog
            datePickerDialog.show()
        }

    }

    companion object {

    }
}