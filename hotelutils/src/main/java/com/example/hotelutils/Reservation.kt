package com.example.hotelutils

import java.util.Date
import java.util.UUID

class Reservation(
    private var id: String,
    private val hotel: Hotel,
    private var arrival: Date,
    private var departure: Date,
    private var people: Int
) {
    init {
        id = UUID.randomUUID().toString()
    }

    fun getID(): String {
        return id
    }

    fun getArrival(): Date {
        return arrival
    }

    fun getDeparture(): Date {
        return departure
    }

    fun getNumPeople(): Int {
        return people
    }

    fun setArrival(arrivalDate: Date) {
        arrival = arrivalDate
    }

    fun setDeparture(departureDate: Date) {
        departure = departureDate
    }

    fun setNumPeople(n: Int) {
        people = n
    }

}