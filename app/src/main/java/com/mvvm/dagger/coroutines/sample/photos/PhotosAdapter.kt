package com.mvvm.dagger.coroutines.sample.photos

import com.mvvm.dagger.coroutines.sample.R
import com.mvvm.dagger.coroutines.sample.base.BaseRecyclerViewAdapter
import com.mvvm.dagger.coroutines.sample.data.room.Photo

class PhotosAdapter(photoList: List<Photo>?,listener: BaseRecyclerViewAdapter.OnItemClickListener) : BaseRecyclerViewAdapter(photoList,listener) {

    override fun getItemLayoutId(): Int = R.layout.item_photo

}