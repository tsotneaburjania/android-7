package com.example.android_7

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.android_7.data.ReqResApi
import com.example.android_7.data.request.CreateUserModel
import com.example.android_7.data.response.CreatedUserModel
import com.example.android_7.data.response.PageModel
import com.example.android_7.data.response.UpdatedUserModel
import com.example.android_7.data.response.UserModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var userDataTv: TextView
    private lateinit var postResponseTv: TextView
    private lateinit var putResponseTv: TextView
    private lateinit var nameCrtEt: EditText
    private lateinit var jobCrtEt: EditText
    private lateinit var nameUpdEt: EditText
    private lateinit var jobUpdEt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://reqres.in/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val reqResApi: ReqResApi = retrofit.create(ReqResApi::class.java)

        userDataTv = findViewById(R.id.getUserDataTv)
        val getBtn: Button = findViewById<View>(R.id.getBtn) as Button
        val closeGetBtn: Button = findViewById<View>(R.id.closeGetBtn) as Button

        postResponseTv = findViewById(R.id.postCreateUserResponseTv)
        nameCrtEt = findViewById((R.id.nameCrtEt))
        jobCrtEt = findViewById(R.id.jobCrtEt)
        val postBtn: Button = findViewById<View>(R.id.postBtn) as Button
        val closePostBtn: Button = findViewById<View>(R.id.closePostBtn) as Button

        putResponseTv = findViewById(R.id.putUpdateUserResponseTv)
        nameUpdEt = findViewById((R.id.nameUpdEt))
        jobUpdEt = findViewById(R.id.jobUpdEt)
        val putBtn: Button = findViewById<View>(R.id.putBtn) as Button
        val closePutBtn: Button = findViewById<View>(R.id.closePutBtn) as Button

        closeGetBtn.setOnClickListener {
            userDataTv.text = ""
            getBtn.visibility = View.VISIBLE
            closeGetBtn.visibility = View.INVISIBLE
        }

        getBtn.setOnClickListener {
            getBtn.visibility = View.INVISIBLE
            closeGetBtn.visibility = View.VISIBLE


            val call: Call<PageModel> = reqResApi.getUsers()
            call.enqueue(object : Callback<PageModel> {
                override fun onFailure(call: Call<PageModel>, t: Throwable) {
                    userDataTv.text = t.message
                }

                @SuppressLint("SetTextI18n")
                override fun onResponse(call: Call<PageModel>, response: Response<PageModel>) {
                    if (!response.isSuccessful) {
                        userDataTv.text = "Code: " + response.code()
                        return
                    }
                    println(response.body())
                    val users: PageModel? = response.body()

                    var x = 0
                    if (users != null) {
                        for (user: UserModel? in users.data) {
                            x++
                            var content: String = ""
                            content += "ID: " + user?.id + "\n"
                            content += "Email: " + user?.email + "\n"
                            content += "First Name: " + user?.firstName + "\n"
                            content += "Last Name: " + user?.lastName + "\n"

                            userDataTv.append(content)
                            if (x == 10) {
                                break
                            }
                        }

                    }
                }

            })
        }

        closePostBtn.setOnClickListener {
            postResponseTv.text = ""
            postBtn.visibility = View.VISIBLE
            closePostBtn.visibility = View.INVISIBLE
        }

        postBtn.setOnClickListener {
            postBtn.visibility = View.INVISIBLE
            closePostBtn.visibility = View.VISIBLE

            val userToBeAdded = CreateUserModel()
            userToBeAdded.name = nameCrtEt.text.toString()
            userToBeAdded.job = jobCrtEt.text.toString()

            val call: Call<CreatedUserModel> = reqResApi.createUser(userToBeAdded)
            call.enqueue(object : Callback<CreatedUserModel> {
                @SuppressLint("SetTextI18n")
                override fun onResponse(call: Call<CreatedUserModel>, response: Response<CreatedUserModel>) {
                    if (!response.isSuccessful) {
                        postResponseTv.text = "Code: " + response.code()
                        return
                    }
                    val createdUser: CreatedUserModel? = response.body()
                    postResponseTv.append(createdUser?.name + "\n")
                    postResponseTv.append(createdUser?.job + "\n")
                    postResponseTv.append(createdUser?.createdAt + "\n")

                }

                override fun onFailure(call: Call<CreatedUserModel>, t: Throwable) {
                    userDataTv.text = t.message
                }

            })
        }

        closePutBtn.setOnClickListener {
            putResponseTv.text = ""
            putBtn.visibility = View.VISIBLE
            closePutBtn.visibility = View.INVISIBLE
        }

        putBtn.setOnClickListener {
            putBtn.visibility = View.INVISIBLE
            closePutBtn.visibility = View.VISIBLE

            val userToBeUpdated = CreateUserModel()
            userToBeUpdated.name = nameUpdEt.text.toString()
            userToBeUpdated.job = jobUpdEt.text.toString()

            println(userToBeUpdated.name)
            println(userToBeUpdated.job)

            val call: Call<UpdatedUserModel> = reqResApi.updateUser(userToBeUpdated)
            call.enqueue(object : Callback<UpdatedUserModel> {
                @SuppressLint("SetTextI18n")
                override fun onResponse(call: Call<UpdatedUserModel>, response: Response<UpdatedUserModel>) {
                    if (!response.isSuccessful) {
                        putResponseTv.text = "Code: " + response.code()
                        return
                    }
                    println("EEEEEEEEEEEEEEEEE" + response.code())
                    val updatedUser: UpdatedUserModel? = response.body()

                    putResponseTv.append(updatedUser?.name + "\n")
                    putResponseTv.append(updatedUser?.job + "\n")
                    putResponseTv.append(updatedUser?.updatedAt + "\n")

                }

                override fun onFailure(call: Call<UpdatedUserModel>, t: Throwable) {
                    userDataTv.text = t.message
                }

            })
        }

    }
}