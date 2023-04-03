package com.example.foodapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationfood.databinding.ActivityMainBinding
import com.example.myapplicationfood.databinding.AddNewItemBinding
import com.example.myapplicationfood.databinding.DeletItemBinding
import com.example.myapplicationfood.databinding.UpdateItemBinding

class MainActivity : AppCompatActivity() ,FoodAdapter.FoodEvent{
    lateinit var binding: ActivityMainBinding
    lateinit var  myAdapter:FoodAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val foodList = arrayListOf(
            Food( "Hamburger" , "15" , "3" , "Isfahan, Iran" , "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food1.jpg" ,  20 , 4.5f ) ,
            Food( "Grilled fish" , "20" , "2.1" , "Tehran, Iran" , "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food2.jpg" ,  10 , 4f ) ,
            Food( "Lasania" , "40" , "1.4" , "Isfahan, Iran" , "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food3.jpg" ,  30 , 2f ) ,
            Food( "pizza" , "10" , "2.5" , "Zahedan, Iran" , "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food4.jpg" ,  80 , 1.5f ) ,
            Food( "Sushi" , "20" , "3.2" , "Mashhad, Iran" , "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food5.jpg" ,  200 , 3f ) ,
            Food( "Roasted Fish" , "40" , "3.7" , "Jolfa, Iran" , "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food6.jpg" ,  50 , 3.5f ) ,
            Food( "Fried chicken" , "70" , "3.5" , "NewYork, USA" , "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food7.jpg" ,  70 , 2.5f ) ,
            Food( "Vegetable salad" , "12" , "3.6" , "Berlin, Germany" , "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food8.jpg" ,  40 , 4.5f ) ,
            Food( "Grilled chicken" , "10" , "3.7" , "Beijing, China" , "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food9.jpg" ,  15 , 5f ) ,
            Food( "Baryooni" , "16" , "10" , "Ilam, Iran" , "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food10.jpg" ,  28 , 4.5f ) ,
            Food( "Ghorme Sabzi" , "11.5" , "7.5" , "Karaj, Iran" , "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food11.jpg" ,  27 , 5f ) ,
            Food( "Rice" , "12.5" , "2.4" , "Shiraz, Iran" , "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food12.jpg" ,  35 , 2.5f ) ,
        )

        myAdapter = FoodAdapter(foodList.clone() as ArrayList<Food>,this )
        binding.recyclerMain.adapter=myAdapter
        binding.recyclerMain.layoutManager=LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        binding.addicon.setOnClickListener{
            val dialog=AlertDialog.Builder(this).create()
            val dialogbinding= AddNewItemBinding.inflate(layoutInflater)
            dialog.setView(dialogbinding.root)
            dialog.setCancelable(true)
            dialog.show()
            dialogbinding.dialogBtnDone.setOnClickListener{
                if(
                    dialogbinding.dialogEdtNameFood.length()>0 && dialogbinding.dialogEdtCity.length()>0 && dialogbinding.dialogEdtPrice.length()>0 &&
                            dialogbinding.dialogEdtDistance.length()>0
                ) {
                    val txtfood = dialogbinding.dialogEdtNameFood.text.toString()
                    val txtprice = dialogbinding.dialogEdtPrice.text.toString()
                    val txtcity = dialogbinding.dialogEdtCity.text.toString()
                    val txtdistance = dialogbinding.dialogEdtDistance.text.toString()
                    val txtratingnumber:Int=(1..150).random()
                    val ratingbarstar:Float=(1 until 6).random().toFloat()
                    val urlrandom=(0 until 12).random()
                    val urlpic=foodList[urlrandom].urlImage
                    val newfood=Food(txtfood,txtprice,txtdistance,txtcity,urlpic,txtratingnumber,ratingbarstar)
                    myAdapter.addfood(newfood)
                    binding.recyclerMain.scrollToPosition(0)
                    dialog.dismiss()

                }else{
                    Toast.makeText(this,"همه مقادیر را وارد کنید",Toast.LENGTH_SHORT).show()
                }

            }
        }
        binding.edtSearch.addTextChangedListener{ edittextinput ->
            if (edittextinput !!.isNotEmpty()){
                val clonelist=foodList.clone() as ArrayList<Food>
                val filterlisted=clonelist.filter { food->
                    food.txtSubject.contains(edittextinput) }
                myAdapter.setdata(ArrayList(filterlisted))


            }else{
                myAdapter.setdata(foodList.clone() as ArrayList<Food>)
            }
        }


    }

    override fun onfoodclicked(food:Food,position:Int) {
        val dialog1=AlertDialog.Builder(this).create()
        val dialogupdate= UpdateItemBinding.inflate(layoutInflater)
        dialog1.setView(dialogupdate.root)
        dialog1.show()
        dialogupdate.dialogEdtNameFood.setText(food.txtSubject)
        dialogupdate.dialogEdtCity.setText(food.txtCity)
        dialogupdate.dialogEdtDistance.setText(food.txtDistance)
        dialogupdate.dialogEdtPrice.setText(food.txtPrice)
        dialogupdate.updateBtnCancel.setOnClickListener{
            dialog1.dismiss()
        }
        dialogupdate.updateBtnDone.setOnClickListener{
            if(
                dialogupdate.dialogEdtNameFood.length()>0 && dialogupdate.dialogEdtCity.length()>0 && dialogupdate.dialogEdtPrice.length()>0 &&
                dialogupdate.dialogEdtDistance.length()>0
            ) {

                val txtfood = dialogupdate.dialogEdtNameFood.text.toString()
                val txtprice = dialogupdate.dialogEdtPrice.text.toString()
                val txtcity = dialogupdate.dialogEdtCity.text.toString()
                val txtdistance = dialogupdate.dialogEdtDistance.text.toString()
                val newfood=Food(txtfood,txtprice,txtdistance,txtcity,food.urlImage,food.numOfRating,food.rating)
                myAdapter.updatefood(newfood,position)
                dialog1.dismiss()

            }else{
                Toast.makeText(this,"همه مقادیر را وارد کنید",Toast.LENGTH_SHORT).show()

            }

        }
    }

    override fun onfoodlongcliked(food: Food, pos: Int) {
        val dialog=AlertDialog.Builder(this).create()
        val dialogdelete= DeletItemBinding.inflate(layoutInflater)
        dialog.setView(dialogdelete.root)
        dialog.setCancelable(true)
        dialog.show()
        dialogdelete.buttonCancel.setOnClickListener{
            dialog.dismiss()
        }
        dialogdelete.buttonSure.setOnClickListener{
            myAdapter.removefood(food,pos)
        }    }


}