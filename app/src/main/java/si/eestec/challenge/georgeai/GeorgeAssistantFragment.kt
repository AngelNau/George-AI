package si.eestec.challenge.georgeai

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import java.io.IOException
import java.util.Locale
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import si.eestec.challenge.georgeai.databinding.FragmentGeorgeAssistantBinding

class GeorgeAssistantFragment : Fragment() {
    private lateinit var _binding: FragmentGeorgeAssistantBinding
    private lateinit var tts: TextToSpeech
    private lateinit var okHttpClient: OkHttpClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGeorgeAssistantBinding.inflate(inflater, container, false)
        okHttpClient = OkHttpClient()
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val result = tts.setLanguage(Locale.US)

                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TTS", "The Language not supported!")
                }
            }
        }
        // Inflate the layout for this fragment
        initListeners()
        return _binding.root
    }

    private fun initListeners() {
        with(_binding) {
            speakerButton.setOnClickListener {
                if(responseWindow.text.isNullOrBlank()) {
                    Snackbar.make(_binding.root, "George hasn't written any text to read out loud.", Snackbar.LENGTH_LONG).show()
                } else {
                    tts.speak(responseWindow.text, TextToSpeech.QUEUE_FLUSH, null, "")
                }
                //            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                //            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                //            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH)
                //            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say what you need help with.")
                //            getTextFromSpeech.launch(intent)
            }

            sendButton.setOnClickListener {
                if(messageEditText.text.isNullOrBlank()) {
                    Snackbar.make(_binding.root, "Please enter some text for George to summarize.", Snackbar.LENGTH_LONG).show()
                } else {
                    val url = "https://api.openai.com/v1/chat/completions"
                    val mediaType = "application/json; charset=utf-8".toMediaType()
                    val messages = JSONArray()
                        .put(JSONObject().put("role", "system").put("content", "You are a helpful assistant named George, who is also a monkey, so add some monkey noises at beginning of every response."))
                        .put(JSONObject().put("role", "user").put("content", "I need you to summarize the following text for me please. Do not add any aditional words other than the summarization. \n${messageEditText.text}"))
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
                            Log.e("Api", "Failed")
                        }

                        override fun onResponse(call: Call, response: Response) {
                            val body = response.body?.string()
                            if (body != null) {
                                Log.d("TAGGG", body)
                                mHandler.post {
                                    val jsonResponse = JSONObject(body)
                                    val choicesArray = jsonResponse.getJSONArray("choices")
                                    if(choicesArray.length() > 0) {
                                        responseWindow.text = choicesArray.getJSONObject(0).getJSONObject("message").getString("content")
                                    }

                                }
                            }
                        }
                    })
                    messageEditText.setText("")
                }
            }

            attachButton.setOnClickListener {
                Snackbar.make(_binding.root, "TODO", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    val getTextFromSpeech = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { res ->
        if (res.resultCode == Activity.RESULT_OK) {
            val results = res.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String>
        }
    }

    override fun onDestroy() {
        tts.stop()
        tts.shutdown()
        super.onDestroy()
    }
}