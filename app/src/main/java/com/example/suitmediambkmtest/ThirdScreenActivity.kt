package com.example.suitmediambkmtest

import adapter.UserAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.suitmediambkmtest.databinding.ActivityThirdScreenBinding
import com.google.gson.Gson
import model.user
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class ThirdScreenActivity : AppCompatActivity() {
    private var adapter: UserAdapter? = null
    private var queue: RequestQueue? = null
    private var srUserList: SwipeRefreshLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityThirdScreenBinding = ActivityThirdScreenBinding.inflate(layoutInflater)

        setContentView(binding.root)

        queue = Volley.newRequestQueue(this)

        srUserList = findViewById(R.id.sr_user_list)
        srUserList?.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener { allUser(binding) })


//        val rvUser = binding.rvUser
//        adapter = UserAdapter(ArrayList(), this@ThirdScreenActivity)
//        rvUser.layoutManager = LinearLayoutManager(this@ThirdScreenActivity)
//        rvUser.adapter = adapter
        allUser(binding)
    }

    private fun allUser(binding: ActivityThirdScreenBinding){
        srUserList!!.isRefreshing = true
        val stringRequest: StringRequest = object :
            StringRequest(Method.GET, "https://reqres.in/api/users?page=1&per_page=10", Response.Listener { response ->
                val gson = Gson()
                val jsonObject = JSONObject(response)
                val userList = gson.fromJson(
                    jsonObject.getJSONArray("data").toString(), Array<user>::class.java
                )

                val rvUser = binding.rvUser
                adapter = UserAdapter(userList.toList(), this@ThirdScreenActivity)
                rvUser.layoutManager = LinearLayoutManager(this@ThirdScreenActivity)
                rvUser.adapter = adapter
                srUserList!!.isRefreshing = false

                if(!userList.isEmpty()) {
                    Toast.makeText(
                        this,
                        "Retrieve data success!",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }else {
                    Toast.makeText(this, "Retrieve data failed!", Toast.LENGTH_SHORT)
                        .show()
                }
            }, Response.ErrorListener { error ->
                srUserList!!.isRefreshing = false
                try{
                    val responseBody =
                        String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(
                        this,
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                }catch (e: Exception){
                    Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                return headers
            }
        }
        queue!!.add(stringRequest)
    }
}