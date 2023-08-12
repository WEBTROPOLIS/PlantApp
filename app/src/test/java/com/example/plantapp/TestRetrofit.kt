package com.example.plantapp

import com.example.plantapp.model.remote.PlantApi
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import junit.framework.TestCase.assertEquals

class TestRetrofit {

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testRetrofit() {
        mockWebServer.start()
        val baseUrl = mockWebServer.url("/")

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val plantApi: PlantApi = retrofit.create(PlantApi::class.java)

        assertEquals(baseUrl.toString(), mockWebServer.url("/").toString())
        mockWebServer.shutdown()
    }
}

