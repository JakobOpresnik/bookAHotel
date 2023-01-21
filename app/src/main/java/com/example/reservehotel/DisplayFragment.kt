package com.example.reservehotel

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reservehotel.databinding.FragmentDisplayBinding
import com.example.reservehotel.hotelUtils.Reservation
import com.example.reservehotel.hotelUtils.ReservationsList
import com.google.firebase.firestore.*

class DisplayFragment : Fragment() {

    private lateinit var binding: FragmentDisplayBinding
    private lateinit var adapter: RecyclerAdapter
    //private var app = MyApplication()
    private lateinit var firebase: FirebaseFirestore
    private var data: ReservationsList = ReservationsList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDisplayBinding.inflate(inflater, container, false)

        firebase = FirebaseFirestore.getInstance()

        try {
            binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
            adapter = RecyclerAdapter(data, object: RecyclerAdapter.myOnClick {
                @SuppressLint("NotifyDataSetChanged")
                override fun onClick(p0: View?, position: Int) {
                    Log.i("deleting", "past reservation")
                    val builder = AlertDialog.Builder(this@DisplayFragment.requireContext())
                    builder.setTitle("DELETE RESERVATION")
                    builder.setMessage("Are you sure you want to delete one of your previously saved reservations?")
                    builder.setIcon(android.R.drawable.ic_dialog_alert)
                    builder.setPositiveButton("Yes") { dialogInterface, which ->
                        Toast.makeText(context, "deleting reservation...", Toast.LENGTH_LONG).show()
                        val documentId = data.pastReservations[position].getID()
                        data.pastReservations.removeAt(position)
                        adapter.notifyDataSetChanged()


                        firebase = FirebaseFirestore.getInstance()
                        firebase.collection("reservations").document(documentId)
                            .delete()
                            .addOnSuccessListener {
                                //Toast.makeText(activity?.applicationContext, "successfully deleted reservation from firebase", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener {
                                //Toast.makeText(activity?.applicationContext, "failed to delete reservation from firebase", Toast.LENGTH_SHORT).show()
                            }

                    }
                    builder.setNeutralButton("Cancel") { dialogInterface, which ->
                        Toast.makeText(context, "deletion cancelled", Toast.LENGTH_LONG).show()
                    }
                    builder.setNegativeButton("No") { dialogInterface, which ->
                        Toast.makeText(context, "deletion rejected", Toast.LENGTH_LONG).show()
                    }
                    val alertDialog: AlertDialog = builder.create()
                    alertDialog.setCancelable(false)
                    alertDialog.show()
                }
            })
            binding.recyclerView.adapter = adapter
            eventChangeListener()

        } catch (err: Error) {
            Log.i("error", "error regarding dialog box (${err})")
        }

        val backButton = binding.backButton
        backButton.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }

        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    fun eventChangeListener() {
        firebase.collection("reservations").orderBy("people", Query.Direction.DESCENDING)
            .addSnapshotListener { querySnapshot: QuerySnapshot?, firebaseFirestoreException: FirebaseFirestoreException? ->
                if (firebaseFirestoreException != null) {
                    Log.i("firebase exception", firebaseFirestoreException.toString() + "exception thrown")
                }
                if (querySnapshot != null) {
                    for (docChange in querySnapshot.documentChanges) {
                        if (docChange.type == DocumentChange.Type.ADDED) {
                            data.addReservation(docChange.document.toObject(Reservation::class.java))
                        }
                        else if (docChange.type == DocumentChange.Type.REMOVED) {
                            data.pastReservations.remove(docChange.document.toObject(Reservation::class.java))
                            firebase.collection("reservations").document(docChange.document.id).delete()
                                .addOnSuccessListener {
                                    Toast.makeText(context, "reservation successfully deleted", Toast.LENGTH_LONG).show()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(context, "railed to delete reservation", Toast.LENGTH_LONG).show()
                                }
                        }
                        adapter.notifyDataSetChanged()
                    }
                }
            }
    }
}