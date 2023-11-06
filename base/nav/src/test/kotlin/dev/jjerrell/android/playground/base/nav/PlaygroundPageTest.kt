/* (C) 2023 Jacob Jerrell */
package dev.jjerrell.android.playground.base.nav

import org.junit.Test

class PlaygroundPageTest {
    private val mockDefaultPlaygroundPage =
        object : PlaygroundPage {
            override val titleRes: Int
                get() = 42

            override val path: String
                get() = "mockPage/{key1}?key2={key2}"
        }

    @Test
    fun `test Default Implementation - Deeplink`() {
        assert(mockDefaultPlaygroundPage.deeplink == null)
    }

    @Test
    fun `test Default Implementation - HasTitle`() {
        assert(mockDefaultPlaygroundPage.titleRes >= 0)
    }

    @Test
    fun `test Default Implementation - routeBuilder`() {
        val routeMap = mapOf("key1" to "theUltimateQuestion", "key2" to "theAnswer")
        val route = mockDefaultPlaygroundPage.buildPath(routeMap)
        val expectedRoute = "mockPage/theUltimateQuestion?key2=theAnswer"
        assert(route == expectedRoute)
    }
}
