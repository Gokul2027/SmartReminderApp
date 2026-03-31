package com.smartreminder.app

import com.smartreminder.app.ai.TaskCategorizer
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class TaskCategorizerTest {

    @Test
    fun `buy milk maps to grocery`() {
        assertEquals("Grocery", TaskCategorizer.categorize("Buy milk"))
    }

    @Test
    fun `pick up medicine maps to pharmacy`() {
        assertEquals("Pharmacy", TaskCategorizer.categorize("Pick up medicine"))
    }

    @Test
    fun `withdraw cash maps to bank`() {
        assertEquals("Bank", TaskCategorizer.categorize("Withdraw cash from ATM"))
    }

    @Test
    fun `unknown text defaults to general`() {
        assertEquals("General", TaskCategorizer.categorize("xyzabc unknown"))
    }

    @Test
    fun `category list includes general`() {
        assertTrue(TaskCategorizer.getAllCategories().contains("General"))
    }
}
