package com.nopyjf.projectscbexplorer.fragment


import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nopyjf.projectscbexplorer.R
import com.nopyjf.projectscbexplorer.service.ApiManager
import com.nopyjf.projectscbexplorer.vo.Coupon
import com.nopyjf.projectscbexplorer.vo.CustomerProfile
import com.paginate.Paginate
import kotlinx.android.synthetic.main.custom_list.view.*
import kotlinx.android.synthetic.main.custom_loadmore.view.*
import kotlinx.android.synthetic.main.fragment_coupon.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CouponFragment : Fragment() {

    private var mDataArray: ArrayList<Coupon> = ArrayList()
    private lateinit var mAdapter: CustomAdapter

    private var mIsLoading = false
    private var mIsNoMoreData = false

    private var citizenId = ""
    private var isLoadProfile = false

    private val couponCallback = object : Callback<ArrayList<Coupon>> {
        override fun onFailure(call: Call<ArrayList<Coupon>>, t: Throwable) {
            Log.e("Nopy", t.localizedMessage!!)
        }

        override fun onResponse(call: Call<ArrayList<Coupon>>, response: Response<ArrayList<Coupon>>) {

            Log.e("Nopy", response.body().toString())

            val result = response.body()!!

            mDataArray.addAll(result)
            mIsLoading = false
            mIsNoMoreData = true
            mAdapter.notifyDataSetChanged()

            swipeRefresh.isRefreshing = false
        }
    }

    private val customerCallback = object : Callback<CustomerProfile> {
        override fun onFailure(call: Call<CustomerProfile>, t: Throwable) {
            Log.e("Nopy", t.localizedMessage!!)
        }

        override fun onResponse(call: Call<CustomerProfile>, response: Response<CustomerProfile>) {
            Log.e("Nopy", response.body().toString())
            val body = response.body()!!
            citizenId = body.data.profile.citizenID

            mAdapter = CustomAdapter()

            recyclerView.let {
                it.adapter = mAdapter
                it.layoutManager = LinearLayoutManager(activity!!.applicationContext)
            }

            feedData()

            Paginate.with(recyclerView, object : Paginate.Callbacks {
                override fun onLoadMore() {
                    Handler().postDelayed({}, 200)
                }

                override fun isLoading(): Boolean {
                    // Indicate whether new page loading is in progress or not
                    return mIsLoading
                }

                override fun hasLoadedAllItems(): Boolean {
                    // Indicate whether all data (pages) are loaded or not
                    return mIsNoMoreData
                }

            })
                .setLoadingTriggerThreshold(2) // Set the offset from the end of the list at which the load more event needs to be triggered. Default offset if 5.
                .addLoadingListItem(true)
                .setLoadingListItemCreator(CustomLoadingListItemCreator())
                .build()


            swipeRefresh.setOnRefreshListener {

                mIsLoading = true
                mIsNoMoreData = false

                if (mDataArray.isNotEmpty()) {
                    mDataArray.clear()
                }

                feedData()
            }
        }

    }

    private val getCouponCallback = object : Callback<Coupon> {
        override fun onFailure(call: Call<Coupon>, t: Throwable) {
            Log.e("Nopy", t.localizedMessage!!)
        }

        override fun onResponse(call: Call<Coupon>, response: Response<Coupon>) {
            val body = response.body()!!
            var myFragment = CouponDialogFragment(
                body.title_name,
                body.merchant_name,
                body.created_date,
                body.expired_date,
                body.qr_code
            )
            myFragment.show(activity!!.supportFragmentManager, "fragment_coupon_dialog")

        }
    }

    inner class CustomAdapter : RecyclerView.Adapter<CustomHolder>() {
        override fun onBindViewHolder(holder: CustomHolder, position: Int) {
            mDataArray[position].let { coupon ->
                with(holder) {
                    holder.title.text = coupon.title_name
                    holder.subtitle.text = coupon.merchant_name
                    Glide.with(activity!!).load(coupon.image_path).into(holder.avatarImage)

                    this.itemView.setOnClickListener {
                        ApiManager.couponService.getCoupon(coupon.coupon_id).enqueue(getCouponCallback)
                    }
                }
            }
        }

        override fun getItemCount(): Int = mDataArray.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomHolder {
            return CustomHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.custom_list,
                    parent,
                    false
                )
            )
        }
    }

    inner class CustomHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.titleTextView
        val subtitle: TextView = view.subTitleTextView
        val avatarImage: ImageView = view.avatarImage
    }

    inner class CustomLoadingListItemCreator : com.paginate.recycler.LoadingListItemCreator {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return FooterHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.custom_loadmore,
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val tmp = holder as FooterHolder
            tmp.desText.text = "Loading"
        }
    }

    inner class FooterHolder(view: View) : RecyclerView.ViewHolder(view) {
        val desText: TextView = view.desText
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        getCustomerProfile()
        return inflater.inflate(R.layout.fragment_coupon, container, false)
    }

    private fun getCustomerProfile() {
        val prefs = activity!!.getSharedPreferences("SCBExplorerSharedPref", MODE_PRIVATE)
        val accessToken = prefs.getString("accessToken", null)
        ApiManager.customerService.profile(
            "Bearer $accessToken",
            "64847ab5-7be8-4fdc-bde1-5957d2f8017f"
        ).enqueue(customerCallback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getCustomerProfile()
    }

    private fun feedData() {
        ApiManager.couponService.getCoupons(citizenId).enqueue(couponCallback)
    }


}
