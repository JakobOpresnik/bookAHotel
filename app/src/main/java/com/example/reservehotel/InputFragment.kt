package com.example.reservehotel

import android.annotation.SuppressLint
import android.app.*
import android.app.PendingIntent.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.*
import androidx.navigation.fragment.findNavController
import com.example.reservehotel.databinding.FragmentInputBinding
import com.example.reservehotel.hotelUtils.Hotel
import com.example.reservehotel.hotelUtils.Reservation
import com.google.firebase.firestore.FirebaseFirestore
import java.sql.Time
import java.util.*
import kotlin.collections.HashMap

lateinit var arrivalDate: Date
lateinit var arrivalTime: Time
lateinit var departureDate: Date
lateinit var departureTime: Time
lateinit var selectedHotel: String


class InputFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: FragmentInputBinding
    private lateinit var arrivalTimeButton: Button
    private lateinit var arrivalDateButton: Button
    private lateinit var departureTimeButton: Button
    private lateinit var departureDateButton: Button

    private lateinit var firebase: FirebaseFirestore


    @RequiresApi(Build.VERSION_CODES.S)
    @SuppressLint("ResourceType", "UnspecifiedImmutableFlag")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentInputBinding.inflate(inflater, container, false)

        createNotificationChannel()

        val hotelName = arguments?.getString("hotel_name")
        Log.i("picked hotel", hotelName.toString())

        when (hotelName) {
            "Hotel City Maribor" -> {
                val hotelsSpinner = binding.hotelPicker
                val adapter: ArrayAdapter<CharSequence> =
                    context?.let { ArrayAdapter.createFromResource(it, R.array.hotel_city_maribor_array, android.R.layout.simple_spinner_item) } as ArrayAdapter<CharSequence>
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                hotelsSpinner.adapter = adapter
                hotelsSpinner.onItemSelectedListener = this
            }
            "Hotel Orel" -> {
                val hotelsSpinner = binding.hotelPicker
                val adapter: ArrayAdapter<CharSequence> =
                    context?.let { ArrayAdapter.createFromResource(it, R.array.hotel_orel_array, android.R.layout.simple_spinner_item) } as ArrayAdapter<CharSequence>
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                hotelsSpinner.adapter = adapter
                hotelsSpinner.onItemSelectedListener = this
            }
            "S Hotel" -> {
                val hotelsSpinner = binding.hotelPicker
                val adapter: ArrayAdapter<CharSequence> =
                    context?.let { ArrayAdapter.createFromResource(it, R.array.s_hotel_array, android.R.layout.simple_spinner_item) } as ArrayAdapter<CharSequence>
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                hotelsSpinner.adapter = adapter
                hotelsSpinner.onItemSelectedListener = this
            }
            "Garden Rooms" -> {
                val hotelsSpinner = binding.hotelPicker
                val adapter: ArrayAdapter<CharSequence> =
                    context?.let { ArrayAdapter.createFromResource(it, R.array.garden_rooms_array, android.R.layout.simple_spinner_item) } as ArrayAdapter<CharSequence>
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                hotelsSpinner.adapter = adapter
                hotelsSpinner.onItemSelectedListener = this
            }
            "Hotel Lent" -> {
                val hotelsSpinner = binding.hotelPicker
                val adapter: ArrayAdapter<CharSequence> =
                    context?.let { ArrayAdapter.createFromResource(it, R.array.hotel_lent_array, android.R.layout.simple_spinner_item) } as ArrayAdapter<CharSequence>
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                hotelsSpinner.adapter = adapter
                hotelsSpinner.onItemSelectedListener = this
            }
            "Garni Hotel" -> {
                val hotelsSpinner = binding.hotelPicker
                val adapter: ArrayAdapter<CharSequence> =
                    context?.let { ArrayAdapter.createFromResource(it, R.array.garni_hotel_array, android.R.layout.simple_spinner_item) } as ArrayAdapter<CharSequence>
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                hotelsSpinner.adapter = adapter
                hotelsSpinner.onItemSelectedListener = this
            }
            "Hotel Piramida" -> {
                val hotelsSpinner = binding.hotelPicker
                val adapter: ArrayAdapter<CharSequence> =
                    context?.let { ArrayAdapter.createFromResource(it, R.array.hotel_piramida_array, android.R.layout.simple_spinner_item) } as ArrayAdapter<CharSequence>
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                hotelsSpinner.adapter = adapter
                hotelsSpinner.onItemSelectedListener = this
            }
            "Roomers" -> {
                val hotelsSpinner = binding.hotelPicker
                val adapter: ArrayAdapter<CharSequence> =
                    context?.let { ArrayAdapter.createFromResource(it, R.array.roomers_array, android.R.layout.simple_spinner_item) } as ArrayAdapter<CharSequence>
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                hotelsSpinner.adapter = adapter
                hotelsSpinner.onItemSelectedListener = this
            }
            "B&B Hotel Maribor" -> {
                val hotelsSpinner = binding.hotelPicker
                val adapter: ArrayAdapter<CharSequence> =
                    context?.let { ArrayAdapter.createFromResource(it, R.array.B_B_hotel_array, android.R.layout.simple_spinner_item) } as ArrayAdapter<CharSequence>
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                hotelsSpinner.adapter = adapter
                hotelsSpinner.onItemSelectedListener = this
            }
            "Hotel Tabor" -> {
                val hotelsSpinner = binding.hotelPicker
                val adapter: ArrayAdapter<CharSequence> =
                    context?.let { ArrayAdapter.createFromResource(it, R.array.tabor_hotel_array, android.R.layout.simple_spinner_item) } as ArrayAdapter<CharSequence>
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                hotelsSpinner.adapter = adapter
                hotelsSpinner.onItemSelectedListener = this
            }
            else -> {
                // dropdown menu (using Spinner)
                val hotelsSpinner = binding.hotelPicker
                val adapter: ArrayAdapter<CharSequence> =
                    context?.let { ArrayAdapter.createFromResource(it, R.array.hotels_array, android.R.layout.simple_spinner_item) } as ArrayAdapter<CharSequence>
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
                hotelsSpinner.adapter = adapter
                hotelsSpinner.onItemSelectedListener = this
            }
        }

        // get date & time of arrival & departure
        /*arrivalTimeButton = binding.arrivalTimeButton
        arrivalTimeButton.setOnClickListener {
            showArrivalTimePickerDialog()
        }

        departureTimeButton = binding.departureTimeButton
        departureTimeButton.setOnClickListener {
            showDepartureTimePickerDialog()
        }

        arrivalDateButton = binding.arrivalDateButton
        arrivalDateButton.setOnClickListener {
            showArrivalDatePickerDialog()
        }

        departureDateButton = binding.departureDateButton
        departureDateButton.setOnClickListener {
            showDepartureDatePickerDialog()
        }*/

        val confirmButton = binding.confirmButton
        confirmButton.setOnClickListener {

            // get number of people from EditText
            val input = binding.inputPeople

            if (input.text.toString() != "") {

                val intent = Intent(context, NotificationReceiver::class.java)
                val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, FLAG_IMMUTABLE)
                val manager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager

                // testing notifications
                val time = System.currentTimeMillis()
                val deltaTime = 1000*3  // 3 seconds
                val notificationTime = time + deltaTime
                manager.set(AlarmManager.RTC_WAKEUP, notificationTime, pendingIntent)


                // actual notifications code

                //val time = getReservationTime()
                //val deltaTime = 172800*1000     // receive notification 2 days prior to your reservation
                //val notificationTime = time - deltaTime
                //manager.set(AlarmManager.RTC_WAKEUP, notificationTime, pendingIntent)

                // pop up to notify you the notification has been set
                val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    time,
                    pendingIntent
                )
                showAlert(notificationTime, "Hotel Reservation", "Your reminder has been set to 2 days prior")

                val numPeople = input.text.toString().toInt()

                val hotel = Hotel()
                hotel.setName(selectedHotel)

                when(selectedHotel) {
                    "S Hotel" -> {
                        hotel.setAddress("Smetanova ulica 20")
                        hotel.setPricePerNight(73.toDouble())
                        hotel.setAvailable(true)
                        hotel.setRating(3.8)
                        hotel.setNumRatings(54)
                    }
                    "Hotel Orel" -> {
                        hotel.setAddress("Volkmerjev prehod")
                        hotel.setPricePerNight(67.toDouble())
                        hotel.setAvailable(true)
                        hotel.setRating(4.2)
                        hotel.setNumRatings(542)
                    }
                    "Hotel City Maribor" -> {
                        hotel.setAddress("Ulica kneza Koclja 22")
                        hotel.setPricePerNight(117.toDouble())
                        hotel.setAvailable(true)
                        hotel.setRating(4.6)
                        hotel.setNumRatings(1275)
                    }
                    "Garden Rooms" -> {
                        hotel.setAddress("Vojašniški trg 8")
                        hotel.setPricePerNight(97.toDouble())
                        hotel.setAvailable(true)
                        hotel.setRating(4.8)
                        hotel.setNumRatings(32)
                    }
                    "Hotel Lent" -> {
                        hotel.setAddress("Dravska ulica 9")
                        hotel.setPricePerNight(95.toDouble())
                        hotel.setAvailable(true)
                        hotel.setRating(3.9)
                        hotel.setNumRatings(257)
                    }
                    "Garni Hotel" -> {
                        hotel.setAddress("Macunova ulica 1")
                        hotel.setPricePerNight(91.toDouble())
                        hotel.setAvailable(true)
                        hotel.setRating(4.4)
                        hotel.setNumRatings(166)
                    }
                    "Hotel Piramida" -> {
                        hotel.setAddress("Ulica heroja Šlandra 10")
                        hotel.setPricePerNight(97.toDouble())
                        hotel.setAvailable(true)
                        hotel.setRating(4.1)
                        hotel.setNumRatings(602)
                    }
                    "Roomers" -> {
                        hotel.setAddress("Gledališka ulica 4")
                        hotel.setPricePerNight(65.toDouble())
                        hotel.setAvailable(true)
                        hotel.setRating(4.9)
                        hotel.setNumRatings(22)
                    }
                    "B&B Hotel Maribor" -> {
                        hotel.setAddress("Ulica Vita Kraigherja 3")
                        hotel.setPricePerNight(34.toDouble())
                        hotel.setAvailable(true)
                        hotel.setRating(4.5)
                        hotel.setNumRatings(96)
                    }
                    "Hotel Tabor" -> {
                        hotel.setAddress("Ulica heroja Zidanška 18")
                        hotel.setPricePerNight(80.toDouble())
                        hotel.setAvailable(true)
                        hotel.setRating(4.2)
                        hotel.setNumRatings(472)
                    }
                }

                // date & time format conversion
                val date = Date(time)
                val dateFormat = android.text.format.DateFormat.getLongDateFormat(context)
                val timeFormat = android.text.format.DateFormat.getTimeFormat(context)

                val reservation = Reservation(hotel, dateFormat.format(date), timeFormat.format(date), numPeople)
                val reservationId = reservation.getID()

                // get firebase connection
                firebase = FirebaseFirestore.getInstance()

                // save reservation to database
                firebase.collection("reservations").document(reservationId).set(reservation).addOnSuccessListener {
                    Toast.makeText(activity?.applicationContext, "successfully added reservation to firebase", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(activity?.applicationContext, "failed reservation to firebase", Toast.LENGTH_SHORT).show()
                }

                // navigate to home fragment
                findNavController().navigate(R.id.homeFragment)
            }
            else {
                Toast.makeText(activity?.applicationContext, "missing input", Toast.LENGTH_SHORT).show()
            }
        }

        val backButton = binding.backButton
        backButton.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)     // navigate to home screen/fragment
        }

        val resetButton = binding.resetButton
        resetButton.setOnClickListener {
            binding.inputPeople.text.clear()
        }

        return binding.root
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectedHotel = parent?.getItemAtPosition(position) as String
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "MyTestChannel"
            val descriptionText = "Testing notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // register the channel with the system
            val notificationManager: NotificationManager = requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        Log.i("action", "notification channel created")
    }

    private fun getReservationTime(): Long {
        val datePicker = binding.datePicker
        val timePicker = binding.timePicker

        val year = datePicker.year
        val month = datePicker.month
        val day = datePicker.dayOfMonth

        val hour = timePicker.hour
        val minute = timePicker.minute

        val calendar = Calendar.getInstance()
        calendar.set(year, month, day, hour, minute)
        return calendar.timeInMillis
    }

    private fun showAlert(time: Long, title: String, message: String) {
        val date = Date(time)
        val dateFormat = android.text.format.DateFormat.getLongDateFormat(context)
        val timeFormat = android.text.format.DateFormat.getTimeFormat(context)

        context?.let {
            AlertDialog.Builder(it)
                .setTitle("Notification Scheduled")
                .setMessage(
                            title +
                            "\n" + message +
                            "\nAt: "+ dateFormat.format(date) + " " + timeFormat.format(date)
                ).setPositiveButton("Okay"){_,_->}
                .show()
        }
    }

    companion object {
        const val CHANNEL_ID = "0"
        private var notificationId = 0
        fun getNotificationUniqueID(): Int {
            return notificationId++
        }
    }


    /*private fun showArrivalTimePickerDialog() {
        ArrivalTimePickerFragment().show(parentFragmentManager, "arrivalTimePicker")
    }

    private fun showArrivalDatePickerDialog() {
        ArrivalDatePickerFragment().show(parentFragmentManager, "arrivalDatePicker")
    }

    private fun showDepartureTimePickerDialog() {
        DepartureTimePickerFragment().show(parentFragmentManager, "departureTimePicker")
    }

    private fun showDepartureDatePickerDialog() {
        DepartureDatePickerFragment().show(parentFragmentManager, "departureDatePicker")
    }*/

}

// fragment class for arrival time picker dialog
/*class ArrivalTimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val cal = Calendar.getInstance()
        val hour = cal.get(Calendar.HOUR_OF_DAY)
        val minute = cal.get(Calendar.MINUTE)
        return TimePickerDialog(activity, this, hour, minute, true)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        arrivalTime = Time(hourOfDay, minute, 0)
    }
}

// fragment class for arrival date picker dialog
class ArrivalDatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)
        return DatePickerDialog(requireContext(), this, year, month, day)
    }

    @SuppressLint("SetTextI18n")
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        arrivalDate = Date(year, month, dayOfMonth)
    }
}

// fragment class for departure time picker dialog
class DepartureTimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val cal = Calendar.getInstance()
        val hour = cal.get(Calendar.HOUR_OF_DAY)
        val minute = cal.get(Calendar.MINUTE)
        return TimePickerDialog(activity, this, hour, minute, true)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        departureTime = Time(hourOfDay, minute, 0)
    }
}


// fragment class for departure date picker dialog
class DepartureDatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)
        return DatePickerDialog(requireContext(), this, year, month, day)
    }

    @SuppressLint("SetTextI18n")
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        departureDate = Date(year, month, dayOfMonth)
    }
}*/