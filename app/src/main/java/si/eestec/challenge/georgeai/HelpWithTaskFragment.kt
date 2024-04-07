package si.eestec.challenge.georgeai

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import java.io.IOException
import java.util.concurrent.TimeUnit
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import si.eestec.challenge.georgeai.databinding.FragmentHelpWithTaskBinding

class HelpWithTaskFragment : Fragment() {
    private lateinit var _binding: FragmentHelpWithTaskBinding
    private lateinit var okHttpClient: OkHttpClient
    private val args by navArgs<HelpWithTaskFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHelpWithTaskBinding.inflate(inflater, container, false)
        okHttpClient = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
        getHelp()
        return _binding.root
    }

    private fun getHelp() {
        Log.d("DESC", args.description)
        val url = "https://api.openai.com/v1/chat/completions"
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val messages = JSONArray()
            .put(
                JSONObject().put("role", "system").put(
                    "content",
                    "You are a helpful assistant named George, who is also a monkey, so add some monkey noises at beginning of every response."
                )
            )
            .put(
                JSONObject().put("role", "user").put(
                    "content",
                    "I need you to help me with my homework assignment. Write me a step by step guide on how I can go about completing it. Do not add any aditional words other than the text that is supposed to help me. \nAssignment description: ${args.description}"
                )
            )
        val requestBody = JSONObject()
            .put("model", "gpt-4")
            .put("messages", messages)

        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer ${BuildConfig.apiKey}")
            .post(requestBody.toString().toRequestBody(mediaType))
            .build()

        val mHandler = Handler(Looper.getMainLooper())
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("Api", e.toString())
                mHandler.post {
                    Snackbar.make(_binding.root, "George is currently working on something, so he missed your call. Please try again.", Snackbar.LENGTH_LONG)
                        .setAction("OK") {
                            findNavController().navigate(HelpWithTaskFragmentDirections.toTaskFragment())
                        }.show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                if (body != null) {
                    Log.d("TAGGG", body)
                    mHandler.post {
                        val jsonResponse = JSONObject(body)
                        val choicesArray = jsonResponse.getJSONArray("choices")
                        if (choicesArray.length() > 0) {
                            _binding.chatGPTGenerateInstructionsView.text = choicesArray.getJSONObject(0).getJSONObject("message").getString("content")
                        }

                    }
                }
            }
        })
    }

}