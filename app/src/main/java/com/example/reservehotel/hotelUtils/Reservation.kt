package com.example.reservehotel.hotelUtils

import java.sql.Time
import java.util.Date
import java.util.Timer
import java.util.UUID

class Reservation(
    private var hotel: Hotel? = null,
    private var date: String? = "",
    private var time: String? = "",
    private var people: Int? = 0
) {
    private var id: String = UUID.randomUUID().toString()

    override fun toString(): String {
        return "$hotel:\n" +
                "date of arrival: $date\n" +
                "time of arrival: $time\n" +
                "number of guests: $people\n"
    }

    fun getID(): String {
        return id
    }

    fun getHotel(): Hotel? {
        return hotel
    }

    fun setHotel(hotel: Hotel) {
        this.hotel = hotel
    }

    fun getDate(): String? {
        return date
    }

    fun setDate(arrivalDate: String) {
        date = arrivalDate
    }

    fun getTime(): String? {
        return time
    }

    fun setTime(arrivalTime: String) {
        time = arrivalTime
    }

    fun getPeople(): Int? {
        return people
    }

    fun setPeople(n: Int) {
        people = n
    }

}