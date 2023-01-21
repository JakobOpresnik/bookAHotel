package com.example.hotelutils

import android.location.Location

class Hotel(
    private val name: String,
    private val address: String,
    private val pricePerNight: Double,
    private val location: Location,
    private val distance: Double,
    private val available: Boolean,
    private val rating: Int,
    private val numRatings: Int,
): Comparable<Hotel> {

    override fun compareTo(other: Hotel): Int {
        val price = this.pricePerNight
        val price2 = other.pricePerNight
        return if (price > price2) {
            0
        } else if (price2 > price) {
            1
        } else {
            -1
        }
    }

    fun getPricePerNight(): Double {
        return pricePerNight
    }

    fun getDistance(): Double {
        return distance
    }

    fun getAvailability(): Boolean {
        return available
    }

    fun getRating(): Int {
        return rating
    }

    fun getNumRatings(): Int {
        return numRatings
    }

}