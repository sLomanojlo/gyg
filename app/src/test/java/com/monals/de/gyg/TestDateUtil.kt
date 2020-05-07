package com.monals.de.gyg

import com.monals.de.gyg.util.INVALID_DATE
import com.monals.de.gyg.util.parseDatePostO
import com.monals.de.gyg.util.parseDatePreO
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized


@RunWith(Parameterized::class)
class TestDateUtil(
    private val created: String,
    private val expected : String,
    private val scenario: String
) {

    companion object {

        @JvmStatic
        @Parameterized.Parameters(name = "parseDate:{2}")
        fun rounds() = listOf(
            arrayOf("ERROR", INVALID_DATE, "Invalid string" ),
            arrayOf("2020-02-19", INVALID_DATE, "Missing time part" ),
            arrayOf("09:05:32", INVALID_DATE, "Missing date part" ),

            arrayOf("2020-01-11T16:06:06+01:00", "Saturday, January 11, 2020", "Valid January date" ),
            arrayOf("2020-01-19T18:19:54+01:00", "Sunday, January 19, 2020", "Valid January date" ),

            arrayOf("2020-02-08T15:20:02+01:00", "Saturday, February 08, 2020", "Valid February date" ),
            arrayOf("2020-02-10T10:30:42+01:00", "Monday, February 10, 2020", "Valid February date" ),
            arrayOf("2020-02-16T14:51:32+01:00", "Sunday, February 16, 2020", "Valid February date" ),
            arrayOf("2020-02-19T15:50:36+01:00", "Wednesday, February 19, 2020", "Valid February date" ),
            arrayOf("2020-02-29T08:36:10+01:00", "Saturday, February 29, 2020", "Valid February date" ),

            arrayOf("2020-03-02T22:04:43+01:00", "Monday, March 02, 2020", "Valid March date" ),
            arrayOf("2020-03-07T01:05:16+01:00", "Saturday, March 07, 2020", "Valid March date" )
        )
    }


    @Test
    fun test_parseDatePreO() {

        val actual = parseDatePreO(created)
        val expected = expected

        assertEquals(expected, actual)

    }

    @Test
    fun test_parseDatePostO() {

        val actual = parseDatePostO(created)
        val expected = expected

        assertEquals(expected, actual)

    }
}