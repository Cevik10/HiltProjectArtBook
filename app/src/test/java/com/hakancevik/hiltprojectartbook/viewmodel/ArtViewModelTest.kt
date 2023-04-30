package com.hakancevik.hiltprojectartbook.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.hakancevik.hiltprojectartbook.MainCoroutineRule
import com.hakancevik.hiltprojectartbook.getOrAwaitValueTest
import com.hakancevik.hiltprojectartbook.repo.FakeArtRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.net.ssl.SSLEngineResult.Status

@ExperimentalCoroutinesApi
class ArtViewModelTest {


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    private lateinit var viewModel: ArtViewModel

    @Before
    fun setup() {
        // Test Doubles
        viewModel = ArtViewModel(FakeArtRepository())
    }

    @Test
    fun `insert art without year returns error`() {

        viewModel.makeArt("The Thing", "John Carpenter", "")
        val value = viewModel.inserArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(com.hakancevik.hiltprojectartbook.util.Status.ERROR)

    }

    @Test
    fun `insert art without name returns error`() {
        viewModel.makeArt("The Thing", "John Carpenter", "")
        val value = viewModel.inserArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(com.hakancevik.hiltprojectartbook.util.Status.ERROR)
    }

    @Test
    fun `insert art without artist name returns error`() {
        viewModel.makeArt("The Thing", "", "1981")
        val value = viewModel.inserArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(com.hakancevik.hiltprojectartbook.util.Status.ERROR)
    }


}