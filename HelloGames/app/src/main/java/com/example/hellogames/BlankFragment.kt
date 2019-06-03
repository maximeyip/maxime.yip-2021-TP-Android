package com.example.hellogames

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_blank.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class BlankFragment : Fragment() {

    interface WebServiceInterface2 {
        @GET("game/details")
        fun Game(@Query("game_id") game_id: Int): retrofit2.Call<Game>
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false)
    }

    var gameid : Int = 0

    fun setGameId(id : Int)
    {
        gameid = id
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // A List to store or objects
        val data = arrayListOf<Game>()
        // The base URL where the WebService is located
        val baseURL = "https://androidlessonsapi.herokuapp.com/api/"
        // Use GSON library to create our JSON parser
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        // Create a Retrofit client object targeting the provided URL
        // and add a JSON converter (because we are expecting json responses)
        val retrofit = Retrofit.Builder().baseUrl(baseURL).addConverterFactory(jsonConverter).build()
        // Use the client to create a service:
        // an object implementing the interface to the WebService
        val service: BlankFragment.WebServiceInterface2 = retrofit.create(BlankFragment.WebServiceInterface2::class.java)
        val wsCallback: retrofit2.Callback<Game> = object : retrofit2.Callback<Game> {

            override fun onFailure(call: Call<Game>, t: Throwable) {
                // Code here what happens if calling the WebService fails
                Log.w("TAG", "WebService call failed")
            }

            override fun onResponse(call: Call<Game>, response: Response<Game>) {
                if (response.code() == 200) {
                    // We got our data !
                    val responseData = response.body()
                    if (responseData != null) {
                        Glide.with(requireActivity()).load(responseData.picture).into(image_game)
                        val t : TextView = view.findViewById(game_name.id) as TextView
                        t.text = responseData.name
                        val t2 = view.findViewById(game_type.id) as TextView
                        t2.text = responseData.type
                        val t3 = view.findViewById(nb_player.id) as TextView
                        t3.text = responseData.players.toString()
                        val t4 = view.findViewById(year_game.id) as TextView
                        t4.text = responseData.year.toString()
                        val t5 = view.findViewById(resume_game.id) as TextView
                        t5.text = responseData.description_en

                        url_more.setOnClickListener {
                            val browzer = Intent(Intent.ACTION_VIEW, Uri.parse(responseData.url))
                            startActivity(browzer)
                        }

                    }
                }
            }
        }

        service.Game(gameid).enqueue(wsCallback)
    }

}
