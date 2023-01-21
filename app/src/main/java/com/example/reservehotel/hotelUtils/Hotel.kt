package com.example.reservehotel.hotelUtils

import android.location.Location

data class Hotel(
    private var name: String? = "",
    private var address: String? = "",
    private var pricePerNight: Double? = 0.0,
    private var available: Boolean? = true,
    private var rating: Double? = 0.0,
    private var numRatings: Int? = 0,
): Comparable<Hotel> {

    override fun toString(): String {
        return "hotel name: $name\n" +
                "address: $address\n" +
                "price per night: $pricePerNight\n" +
                "average rating: $rating/5\n" +
                "number of ratings received: $numRatings\n"
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getAddress(): String? {
        return address
    }

    fun setAddress(address: String) {
        this.address = address
    }

    fun getPricePerNight(): Double? {
        return pricePerNight
    }

    fun setPricePerNight(pricePerNight: Double?) {
        this.pricePerNight = pricePerNight
    }

    fun getAvailable(): Boolean? {
        return available
    }

    fun setAvailable(available: Boolean?) {
        this.available = available
    }

    fun getRating(): Double? {
        return rating
    }

    fun setRating(rating: Double?) {
        this.rating = rating
    }

    fun getNumRatings(): Int? {
        return numRatings
    }

    fun setNumRatings(numRatings: Int?) {
        this.numRatings = numRatings
    }

    override fun compareTo(other: Hotel): Int {
        TODO("Not yet implemented")
    }

}