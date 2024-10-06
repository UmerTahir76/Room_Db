package com.example.roomdatabase

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdatabase.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(),MyAdapter.UserEvents {
    private lateinit var mBinding : ActivityMainBinding
    private lateinit var dao: PersonDAO
    private lateinit var adapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        dao = AppDatabase.getDatabase(this).personDAO()
        mBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MyAdapter(mutableListOf(),this)
        mBinding.recyclerView.adapter = adapter

        mBinding.floatingActionButton.setOnClickListener{
            val fragment = AddEditPersonFragment()
            fragment.show(supportFragmentManager,fragment.tag)
        }

        // Fetching Data
        lifecycleScope.launch {
            dao.getAllData().collect{ personList->
                adapter.updateList(personList.toMutableList())
            }
        }

        mBinding.searchView.setOnQueryTextListener(object : OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                lifecycleScope.launch {
                    val searchQuery = newText
                    if (searchQuery!!.isNotEmpty()){
                        dao.searchPerson(searchQuery).collect{
                            adapter.updateList(it.toMutableList())
                        }
                    }
                    else{
                        dao.getAllData().collect{
                            adapter.updateList(it.toMutableList())
                        }
                    }
                }
                return true
            }

        })
    }

    override fun edit(personEntity: PersonEntity) {
        val fragment = AddEditPersonFragment()
        val bundle = Bundle()
        bundle.putParcelable("person", personEntity)
        fragment.arguments = bundle
        fragment.show(supportFragmentManager, fragment.tag)
    }

    override fun delete(personEntity: PersonEntity) {
        lifecycleScope.launch {
            dao.deletePerson(personEntity)
        }
        Toast.makeText(this@MainActivity,"Deleted: ${personEntity.name}",Toast.LENGTH_SHORT).show()

    }
}