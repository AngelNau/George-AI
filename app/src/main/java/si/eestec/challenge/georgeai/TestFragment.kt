package si.eestec.challenge.georgeai

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import kotlinx.serialization.json.JsonObject
import org.json.JSONArray
import org.json.JSONObject
import si.eestec.challenge.georgeai.databinding.FragmentTaskBinding
import si.eestec.challenge.georgeai.databinding.FragmentTestBinding

class TestFragment : Fragment() {
    private lateinit var _binding: FragmentTestBinding
    private val args by navArgs<TestFragmentArgs>()
    private lateinit var answer1: String
    private lateinit var answer2: String
    private lateinit var answer3: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTestBinding.inflate(inflater, container, false)
        setData()
        initListeners()
        return _binding.root
    }

    private fun initListeners() {
        with(_binding) {
            checkButton.setOnClickListener {
                val first = _binding.root.findViewById<RadioButton>(radioGroup1.checkedRadioButtonId)
                val second = _binding.root.findViewById<RadioButton>(radioGroup2.checkedRadioButtonId)
                val third = _binding.root.findViewById<RadioButton>(radioGroup3.checkedRadioButtonId)
                var correct = ""
                var incorrect = ""
                if(first.text == answer1) {
                    correct += " 1 "
                } else {
                    incorrect += " 1 "
                }
                if(second.text == answer2) {
                    correct += " 2 "
                } else {
                    incorrect += " 2 "
                }
                if(third.text == answer3) {
                    correct += " 3 "
                } else {
                    incorrect += " 3 "
                }
                Snackbar.make(_binding.root, "Correct: $correct \n Incorrect: $incorrect", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun setData() {
        Log.d("Test", args.dataForTest)
        val test = JSONObject(args.dataForTest).getJSONArray("test")
        with(_binding) {
            var question = JSONObject(test[0].toString())
            var answers = question.getJSONArray("answers")
            question1.text = question.getString("question")
            q1ans1.text = answers[0].toString()
            q1ans2.text = answers[1].toString()
            q1ans3.text = answers[2].toString()
            answer1 = question.getString("correct_answer")
            question = JSONObject(test[1].toString())
            answers = question.getJSONArray("answers")
            question2.text = question.getString("question")
            q2ans1.text = answers[0].toString()
            q2ans2.text = answers[1].toString()
            q2ans3.text = answers[2].toString()
            answer2 = question.getString("correct_answer")
            question = JSONObject(test[2].toString())
            answers = question.getJSONArray("answers")
            question3.text = question.getString("question")
            q3ans1.text = answers[0].toString()
            q3ans2.text = answers[1].toString()
            q3ans3.text = answers[2].toString()
            answer3 = question.getString("correct_answer")
        }
    }
}