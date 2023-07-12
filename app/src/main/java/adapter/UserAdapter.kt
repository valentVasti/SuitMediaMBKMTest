package adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.suitmediambkmtest.R
import com.example.suitmediambkmtest.SecondScreenActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.squareup.picasso.Picasso
import model.user
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class UserAdapter(private var userList: List<user>, context: Context?):
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private var filteredUserList: MutableList<user>
    private val context: Context

    init{
        filteredUserList = ArrayList(userList)
        this.context = context!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_user, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int{
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList[position]

        Glide.with(context)
            .load(user.avatar)
            .fitCenter()
            .into(holder.userImg)

//        Picasso.get().load(user.avatar).into(holder.userImg)
        holder.userImg.setImageURI(user.avatar.toUri())
        holder.tvUserName.text = user.first_name + " " + user.last_name
        holder.tvUserEmail.text = user.email

        holder.mainLayout.setOnClickListener{
            val intent: Intent
            val bundle = Bundle()

            bundle.putString("user_name", user.first_name + " " + user.last_name)
            intent = Intent(context, SecondScreenActivity::class.java)
            intent.putExtras(bundle)

            context.startActivity(intent)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var userImg: ImageView
        var tvUserName: TextView
        var tvUserEmail: TextView

        var mainLayout: LinearLayout

        init {
            userImg = itemView.findViewById(R.id.imageUserList)
            tvUserName = itemView.findViewById(R.id.userNameTextView)
            tvUserEmail = itemView.findViewById(R.id.emailTextView)
            mainLayout = itemView.findViewById(R.id.mainLayout)
        }
    }

    private fun formatDate(date: Date): String {
        val format = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        return format.format(date)
    }
}