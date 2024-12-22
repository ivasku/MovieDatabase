package com.tms.moviedatabase.data

import com.tms.moviedatabase.data.remote.TMDBApi
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class NetworkModuleTest {

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `test Retrofit instance creation`() {
        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(TMDBApi::class.java)
        assertNotNull(api)
    }
}
