package com.example.hellogames

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_hello.*
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call
import kotlin.random.Random

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */

class HelloFragment : Fragment() {

    interface WebServiceInterface {
        @GET("game/list")
        fun listGame(@Query("userId") userId: Int): retrofit2.Call<List<GameList>>
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hello, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // A List to store or objects
        val data = arrayListOf<GameList>()
        // The base URL where the WebService is located
        val baseURL = "https://androidlessonsapi.herokuapp.com/api/"
        // Use GSON library to create our JSON parser
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        // Create a Retrofit client object targeting the provided URL
        // and add a JSON converter (because we are expecting json responses)
        val retrofit = Retrofit.Builder().baseUrl(baseURL) .addConverterFactory(jsonConverter) .build()
        // Use the client to create a service:
        // an object implementing the interface to the WebService
        val service: WebServiceInterface = retrofit.create(WebServiceInterface::class.java)

        val wsCallback: retrofit2.Callback<List<GameList>> = object : retrofit2.Callback<List<GameList>> {
            override fun onFailure(call: Call<List<GameList>>, t: Throwable) {
                // Code here what happens if calling the WebService fails
                Log.w("TAG", "WebService call failed")
            }

            override fun onResponse(call: Call<List<GameList>>, response: Response<List<GameList>>) {
                if (response.code() == 200) {
                    // We got our data !
                    val responseData = response.body()
                    if (responseData != null) {
                        Log.d("TAG", "WebService success : " + responseData.size)
                        var randint1 = Random.nextInt(responseData.size)
                        var randint2 = Random.nextInt(responseData.size)
                        while (randint2 == randint1)
                            randint2 = Random.nextInt(responseData.size)
                        var randint3 = Random.nextInt(responseData.size)
                        while (randint3 == randint1 || randint3 == randint2)
                            randint3 = Random.nextInt(responseData.size)
                        var randint4 = Random.nextInt(responseData.size)
                        while (randint4 == randint3 || randint4 == randint2 || randint1 == randint4)
                            randint4 = Random.nextInt(responseData.size)

                        Glide.with(requireActivity()).load(responseData[randint1].picture).into(image1)
                        Glide.with(requireActivity()).load(responseData[randint2].picture).into(image2)
                        Glide.with(requireActivity()).load(responseData[randint3].picture).into(image3)
                        Glide.with(requireActivity()).load(responseData[randint4].picture).into(image4)

                        val  image1View : ImageView = view.findViewById(R.id.image1)
                        val  image2View : ImageView = view.findViewById(R.id.image2)
                        val  image3View : ImageView = view.findViewById(R.id.image3)
                        val  image4View : ImageView = view.findViewById(R.id.image4)

                        image1View.setOnClickListener {
                            (activity as MainActivity).GoToFragmentBlank(responseData[randint1].id)
                        }

                        image2View.setOnClickListener {
                            (activity as MainActivity).GoToFragmentBlank(responseData[randint2].id)
                        }

                        image3View.setOnClickListener {
                            (activity as MainActivity).GoToFragmentBlank(responseData[randint3].id)
                        }

                        image4View.setOnClickListener {
                            (activity as MainActivity).GoToFragmentBlank(responseData[randint4].id)
                        }
                    }
                }
            }
        }
        service.listGame(2).enqueue(wsCallback)
    }
}