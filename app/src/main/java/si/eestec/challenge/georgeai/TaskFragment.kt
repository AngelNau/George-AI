package si.eestec.challenge.georgeai

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.Task
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import si.eestec.challenge.georgeai.databinding.FragmentTaskBinding
import si.eestec.challenge.georgeai.model.TaskModel

class TaskFragment : Fragment() {
    companion object {
        const val SHARED_PREF = "SHARED_PREFERENCES"
    }
    private lateinit var _binding: FragmentTaskBinding
    private val binding get() = _binding!!

    private lateinit var okHttpClient: OkHttpClient

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerAdapter: RecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTaskBinding.inflate(inflater, container, false)
        okHttpClient = OkHttpClient()
        initListeners()
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        recyclerAdapter = RecyclerAdapter(loadTasks()){}
        recyclerView.adapter = recyclerAdapter
    }

    override fun onResume() {
        super.onResume()
        recyclerAdapter.updateTasks(loadTasks())
    }

    private fun initListeners() {
        _binding.newTask.setOnClickListener {
            findNavController().navigate(TaskFragmentDirections.toAddNewTaskFragment())
        }
    }

    private fun loadTasks(): List<TaskModel> {
        val taskList: MutableList<TaskModel>
        val taskString = sharedPreferences.getString("tasks", null)
        if(taskString.isNullOrBlank()) {
            taskList = emptyList<TaskModel>().toMutableList()
        } else {
            taskList = Json.decodeFromString(taskString)
        }

        return taskList
    }
}
