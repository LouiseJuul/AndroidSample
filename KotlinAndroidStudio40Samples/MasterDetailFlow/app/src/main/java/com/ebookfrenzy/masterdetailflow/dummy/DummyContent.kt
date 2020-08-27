package com.ebookfrenzy.masterdetailflow.dummy

import java.util.ArrayList
import java.util.HashMap

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    val ITEMS: MutableList<DummyItem> = ArrayList()



    /**
     * A map of sample (dummy) items, by ID.
     */
    val ITEM_MAP: MutableMap<String, DummyItem> = HashMap()

    init {
        // Add 3 sample items.
        addItem(DummyItem("1", "eBookFrenzy",
            "https://www.ebookfrenzy.com"))
        addItem(DummyItem("2", "Amazon",
            "https://www.amazon.com"))
        addItem(DummyItem("3", "New York Times",
            "https://www.nytimes.com"))
    }

    private fun addItem(item: DummyItem) {
        ITEMS.add(item)
        ITEM_MAP.put(item.id, item)
    }

    /**
     * A dummy item representing a piece of content.
     */
    data class DummyItem(val id: String, val website_name: String,
                         val website_url: String) {
        override fun toString(): String = website_name
    }
}