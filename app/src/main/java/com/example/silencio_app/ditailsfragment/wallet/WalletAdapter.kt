package com.example.silencio_app.ditailsfragment.wallet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.silencio_app.R
import com.example.silencio_app.databinding.LayoutWalletBinding
import com.example.silencio_app.ditailsfragment.wallet.resoruce.FrienListRespons
import com.example.silencio_app.ditailsfragment.wallet.resoruce.FriendList
import com.example.silencio_app.util.DiffUtilExt
import com.example.silencio_app.util.Utils
import kotlin.time.Duration.Companion.days


class WalletAdapter  : RecyclerView.Adapter<WalletAdapter.MyViewHolder>() {

    //private var listData : List<GridViewData>?=null

    private var callList = emptyList<FriendList>()
    class MyViewHolder(private val binding : LayoutWalletBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(currentItem: FriendList){
            binding.variable = currentItem
            binding.imgwalletRec.load(currentItem.profilePic){
                crossfade(true)
                transformations(CircleCropTransformation())
                error(R.drawable.ic_baseline_favorite_24)
            }
            binding.textView9.text = "${currentItem.friendFirstName} ${currentItem.friendLastName} "

            binding.textView11.text = String.format("%,.2f",currentItem.amount)
           // binding.textView10.text = currentItem.timeStamp.toString()
            //binding.textView10.text = String.format("%,.2d",currentItem.timeStamp)
            if(currentItem.friendId?.isActive == true && currentItem.timeStamp != 0L){
                binding.textView10.text = itemView.context.getString(
                    R.string.wallet_time,
                    Utils.timeAgo(currentItem?.timeStamp ?: 0L)
                )
            }
        }
        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutWalletBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }


    override fun getItemCount(): Int {
        return callList.size
    }



    fun setData(Result:List<FriendList>){
//        this.callList= user
//        notifyDataSetChanged()

        val userDiffUtil = DiffUtilExt(callList, Result)
        val userDiffUtilResult = DiffUtil.calculateDiff(userDiffUtil)
        callList = Result
        userDiffUtilResult.dispatchUpdatesTo(this)

    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = callList.getOrNull(position)

        currentItem?.let {
            holder.bind(it)
        }
    }


}