package com.pindemo

import com.pindemo.pin.utils.PinBlockUtils
import com.pindemo.pin.utils.nibsToString
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.test.assertFailsWith


class GeneratePinTest {

    @Test
    fun encodePinBlock() {
        val encoder = PinBlockUtils()
        val pin = "1234"
        val block = encoder.generatePinBlock(pin).nibsToString()
        val expected = "341216123412341234"
        assertTrue(expected.take(1 + pin.length) == block.take(1 + pin.length))
    }

    @Test
    fun encodeLongPinBlock() {
        val encoder = PinBlockUtils()
        val pin = "123456789"
        val block = encoder.generatePinBlock(pin).nibsToString()
        val expected = "391216744BA"
        assertTrue(expected.take(1 + pin.length) == block.take(1 + pin.length))
    }

    @Test
    fun encodeTooLongPinBlock() {
        assertFailsWith<PinBlockUtils.InvalidPinException> {
            val encoder = PinBlockUtils()
            val pin = "123456789101112"
            encoder.generatePinBlock(pin)
        }
    }

    @Test
    fun encodeTooShortPinBlock() {
        assertFailsWith<PinBlockUtils.InvalidPinException> {
            val encoder = PinBlockUtils()
            val pin = "19"
            encoder.generatePinBlock(pin)
        }
    }

    @Test
    fun encodeBadPinBlock() {
        assertFailsWith<PinBlockUtils.InvalidPinException> {
            val encoder = PinBlockUtils()
            val pin = "PIN!"
            encoder.generatePinBlock(pin)
        }
    }
}