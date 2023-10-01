package com.aram.weatherapp

import org.junit.Before
import org.mockito.MockitoAnnotations

open class BaseUnitTest {

    protected val mockDouble: Double = 123.45
    protected val mockString: String = "MOCKED_STRING"

    @Before
    open fun setUp() {
        MockitoAnnotations.openMocks(this)
    }
}