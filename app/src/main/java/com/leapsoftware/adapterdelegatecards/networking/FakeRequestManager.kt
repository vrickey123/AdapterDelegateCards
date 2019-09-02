package com.leapsoftware.adapterdelegatecards.networking

import android.content.Context
import com.leapsoftware.adapterdelegatecards.data.FeedItem
import com.leapsoftware.adapterdelegatecards.utils.AssetUtils
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class FakeRequestManager(private val context: Context) {

    companion object {
        @Volatile
        private var instance: FakeRequestManager? = null

        fun getInstance(context: Context): FakeRequestManager {
            return FakeRequestManager.instance ?: synchronized(this) {
                FakeRequestManager.instance ?: FakeRequestManager(context)
                    .also { FakeRequestManager.instance = it }
            }
        }

        fun buildMoshi() : Moshi {
            return Moshi.Builder()
                .add(KotlinJsonAdapterFactory()) // Order matters! Place Kotlin adapter last.
                .build()
        }
    }

    private val moshiKotlin = buildMoshi()

    fun getFeedItems() : List<FeedItem> {
        val listType = Types.newParameterizedType(List::class.java, FeedItem::class.java)
        val adapter: JsonAdapter<List<FeedItem>> = moshiKotlin.adapter(listType)
        val jsonString = AssetUtils.getJsonFromAsset(context, AssetUtils.FILENAME_SAMPLE_FEED_ITEMS)
        return adapter.fromJson(jsonString)!!
    }
}