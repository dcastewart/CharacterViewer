package com.sample.characterviewer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sample.characterviewer.models.CharacterData
import com.sample.characterviewer.network.ApiInterface
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class CharacterViewModel : ViewModel() {

    private var _currentCharacter: MutableLiveData<CharacterData> = MutableLiveData<CharacterData>()
    val currentCharacter: LiveData<CharacterData>
        get() = _currentCharacter

    private var _characterList: MutableLiveData<ArrayList<CharacterData>> =
        MutableLiveData<ArrayList<CharacterData>>()
    val characterList: LiveData<ArrayList<CharacterData>>
        get() = _characterList

    init {
        _characterList.value = arrayListOf()
        _currentCharacter.value = CharacterData()
    }

    fun updateCurrentCharacter(character: CharacterData) {
        _currentCharacter.value = character
    }

    fun populateCharacterListFromAPI(url: String) {
        if (url == "simpsons") {
            val interceptor = HttpLoggingInterceptor()
            interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY }
            val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

            val gson: Gson = GsonBuilder().setLenient().create()
            val retrofitBuilder = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("https://example.com")
                .client(client)
                .build()
                .create(ApiInterface::class.java)

            val retrofitData = retrofitBuilder.getSimpsonsData()

            retrofitData.enqueue(object :
                Callback<com.sample.characterviewer.network.response.Response?> {
                override fun onResponse(
                    call: Call<com.sample.characterviewer.network.response.Response?>,
                    response: Response<com.sample.characterviewer.network.response.Response?>
                ) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _characterList.value = ArrayList(
                            responseBody.RelatedTopics.map { character ->
                                CharacterData(
                                    name = character.FirstURL
                                        .removePrefix("https://duckduckgo.com/")
                                        .replace("_", " ")
                                        .replace("%22", "\""),
                                    image = "https://duckduckgo.com" + character.Icon.URL,
                                    description = character.Text
                                )
                            }
                        )
                    }
                }

                override fun onFailure(
                    call: Call<com.sample.characterviewer.network.response.Response?>,
                    t: Throwable
                ) {
                    _characterList.value = ArrayList(listOf(CharacterData(name = "Jimmy")))
                }
            })
        } else if (url == "theWire") {
            val interceptor = HttpLoggingInterceptor()
            interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY }
            val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

            val gson: Gson = GsonBuilder().setLenient().create()
            val retrofitBuilder = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("https://example.com")
                .client(client)
                .build()
                .create(ApiInterface::class.java)

            val retrofitData = retrofitBuilder.getTheWireData()

            retrofitData.enqueue(object :
                Callback<com.sample.characterviewer.network.response.Response?> {
                override fun onResponse(
                    call: Call<com.sample.characterviewer.network.response.Response?>,
                    response: Response<com.sample.characterviewer.network.response.Response?>
                ) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _characterList.value = ArrayList(
                            responseBody.RelatedTopics.map { character ->
                                CharacterData(
                                    name = character.FirstURL
                                        .removePrefix("https://duckduckgo.com/")
                                        .replace("_", " ")
                                        .replace("%22", "\""),
                                    image = "https://duckduckgo.com" + character.Icon.URL,
                                    description = character.Text
                                )
                            }
                        )
                    }
                }

                override fun onFailure(
                    call: Call<com.sample.characterviewer.network.response.Response?>,
                    t: Throwable
                ) {
                    _characterList.value = ArrayList(emptyList())
                }
            })
        }
    }
}
