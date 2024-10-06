package com.example.roomdatabase

import android.app.Person
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.roomdatabase.databinding.FragmentAddEditPersonBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch

class AddEditPersonFragment : BottomSheetDialogFragment() {
    private lateinit var mBinding : FragmentAddEditPersonBinding
    private lateinit var dao : PersonDAO
    private var person : PersonEntity?= null

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentAddEditPersonBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dao = AppDatabase.getDatabase(requireContext()).personDAO()
        person = arguments?.getParcelable("person")

        if (person != null) {
            setExistingData(person!!)
        }
        addingNewPerson()
    }

    private fun addingNewPerson() {
        mBinding.saveBtn.setOnClickListener {
            val name = mBinding.name.text.toString().trim()
            val age = mBinding.age.text.toString().trim()
            val city = mBinding.city.text.toString().trim()

            if (name.isEmpty() || age.isEmpty() || city.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill all the details", Toast.LENGTH_SHORT).show()
            } else {
                lifecycleScope.launch {
                    if (person == null) {
                        val newPerson = PersonEntity(0, name, age.toInt(), city)
                        dao.addPerson(newPerson)
                    } else {
                        val updatedPerson = person!!.copy(name = name, age = age.toInt(), city = city)
                        dao.updatePerson(updatedPerson)
                    }
                }
                dismiss()
            }
        }
    }

    private fun setExistingData(personEntity: PersonEntity) {
        mBinding.name.setText(personEntity.name)
        mBinding.age.setText(personEntity.age.toString())
        mBinding.city.setText(personEntity.city)
        mBinding.saveBtn.text = "Update"
    }
}