package si.eestec.challenge.georgeai

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import okhttp3.OkHttpClient
import si.eestec.challenge.georgeai.databinding.FragmentTaskBinding

class TaskFragment : Fragment() {
    private lateinit var _binding: FragmentTaskBinding
    private lateinit var okHttpClient: OkHttpClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTaskBinding.inflate(inflater, container, false)
        okHttpClient = OkHttpClient()
        initListeners()
        return _binding.root
    }

    private fun initListeners() {
        with(_binding) {
            newTask.setOnClickListener {
                findNavController().navigate(TaskFragmentDirections.toCreateTaskFragment())
            }

            assistents.setOnClickListener {
                findNavController().navigate(TaskFragmentDirections.toGeorgeFragment())
            }
        }
    }
}