package si.eestec.challenge.georgeai

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.edit
import androidx.navigation.fragment.findNavController
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.Calendar
import si.eestec.challenge.georgeai.databinding.FragmentAddNewTaskBinding
import si.eestec.challenge.georgeai.model.TaskModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class AddNewTaskFragment : Fragment() {


    private var _binding: FragmentAddNewTaskBinding? = null
    private lateinit var sharedPreferences: SharedPreferences

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

        sharedPreferences = requireActivity().getSharedPreferences(TaskFragment.SHARED_PREF, Context.MODE_PRIVATE)

        val submitButton: Button = binding.submitBtn
        submitButton.setOnClickListener {
            // Retrieve text from EditText fields
            val title = binding.titleInputEditText.text.toString()
            val description = binding.descriptionInputEditText.text.toString()
            val date = binding.dateInputEditText.text.toString()
            val time = binding.timeInputEditText.text.toString()

            Log.d("SavedData", "Title: $title, Description: $description, Date: $date, Time: $time")

            // Save data to SharedPreferences
            val task = TaskModel(title, description, date, time)
            saveToSharedPref(task)

            // Navigate to TaskFragment
            findNavController().navigate(R.id.action_add_new_task_fragment_to_task_fragment)
        }


        return binding.root

    }

    private fun saveToSharedPref(task: TaskModel) {
        val tasksString = sharedPreferences.getString("tasks", null)
        val tasksList: MutableList<TaskModel>
        if (tasksString.isNullOrBlank()) {
            tasksList = emptyList<TaskModel>().toMutableList()
        } else {
            tasksList = Json.decodeFromString(tasksString)
        }
        val timeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy HH:mm")
        tasksList.add(task)
        tasksList.sortBy {
            LocalDate.parse("${it.date} ${it.time}", timeFormatter)
        }
        sharedPreferences.edit {
            putString("tasks", Json.encodeToString(tasksList))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnShowDatePicker()
        btnShowTimePicker();


    }

    private fun btnShowTimePicker() {
        binding.timePickerBtn.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar[Calendar.HOUR_OF_DAY]
            val minute = calendar[Calendar.MINUTE]
            val timePickerDialog = TimePickerDialog(
                requireContext(),
                { view, hourOfDay, minute ->
                    val time = String.format("%02d:%02d", hourOfDay, minute)
                    binding.timeInputEditText.setText("$time")
//                    binding.timeInputEditText.visibility = View.VISIBLE // Make TextView visible
                }, 0, 0, true
            )
            timePickerDialog.show()
        }
    }

    private fun btnShowDatePicker() {
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