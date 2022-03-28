package com.example.groceryapp

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() , GroceryRVAdapter.Groceryitemclickedinterface{
    lateinit var itemsRv: RecyclerView
    lateinit var ViewModel: GroceryViewModel
    lateinit var list: List<GroceryItems>
    lateinit var groceryRVAdapter: GroceryRVAdapter
    lateinit var groceryViewModel: GroceryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        itemsRv=findViewById(R.id.idRVitems)


        list= ArrayList<GroceryItems>()
        groceryRVAdapter= GroceryRVAdapter(list,this)

        itemsRv.layoutManager=LinearLayoutManager(this)
        itemsRv.adapter=groceryRVAdapter
        val groceryRepository=GroceryRepository(GroceryDatabase(this))
        val factory=GroceryViewModelFactory(groceryRepository)
        groceryViewModel= ViewModelProvider(this,factory).get(GroceryViewModel::class.java)
        groceryViewModel.allGroceryItems().observe(this, Observer {
            groceryRVAdapter.list=it
            groceryRVAdapter.notifyDataSetChanged()
        })

        var addFab=findViewById<FloatingActionButton>(R.id.floatingActionButton)
        addFab.setOnClickListener{
            opendialog()
        }

    }
    fun opendialog(){

        val dialog=Dialog(this)
        dialog.setContentView(R.layout.grocery_add_dialog)
        val cancel=dialog.findViewById<Button>(R.id.idbtncancel)
        val add=dialog.findViewById<Button>(R.id.idbtnadd)
        val itemedit=dialog.findViewById<EditText>(R.id.idedititemname)
        val itemprice=dialog.findViewById<EditText>(R.id.idedititemprice)
        val itemquanity=dialog.findViewById<EditText>(R.id.idedititemquantity)

        cancel.setOnClickListener{
            dialog.dismiss()
        }
        add.setOnClickListener{
            val itemnamee: String= itemedit.text.toString()
            val itempricee: String= itemprice.text.toString()
            val itemquantityy: String= itemquanity.text.toString()
            val qty : Int= itemquantityy.toInt()
            val pr: Int= itempricee.toInt()

            if(itemnamee.isNotEmpty()&& itempricee.isNotEmpty()&& itemquantityy.isNotEmpty()){

                val items=GroceryItems(itemnamee,qty,pr)
                groceryViewModel.insert(items)
                Toast.makeText(applicationContext,"Item Inserted",Toast.LENGTH_SHORT).show()
                groceryRVAdapter.notifyDataSetChanged()
                dialog.dismiss()

            }
            else
            {
                Toast.makeText(applicationContext,"Please Enter all data",Toast.LENGTH_SHORT).show()

            }

        }
        dialog.show()





    }

    override fun onItemClick(groceryitems: GroceryItems) {
        groceryViewModel.delete(groceryitems)
        groceryRVAdapter.notifyDataSetChanged()
        Toast.makeText(applicationContext,"Item Deleted..",Toast.LENGTH_SHORT).show()

    }
}