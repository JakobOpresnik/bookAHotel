package com.example.reservehotel.hotelUtils

class ReservationsList {
    var pastReservations: MutableList<Reservation> = ArrayList()

    fun addReservation(reservation: Reservation) {
        pastReservations.add(reservation)
    }

    fun updateAtIndex(index: Int, updated: Reservation) {
        pastReservations[index] = updated
    }
}