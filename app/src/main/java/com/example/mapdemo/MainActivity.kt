package com.example.mapdemo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class MainActivity : AppCompatActivity() {

    var hashMapList = ArrayList<TreeMap<String, Any>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val amountValues = listOf(100.100, 78.90, 123.783, 46.90, 498.56)
        val nameValues = listOf("Ashok", "Ramdev", "Binal", "Ram", "Zebra")
        for ((item) in amountValues.withIndex()) {
            val map = TreeMap<String, Any>()
            map.put("amount", amountValues.get(item))
            map.put("name", nameValues.get(item))
            hashMapList.add(map)
        }

        val jsonArray = JSONArray(hashMapList)
        Log.d("TAG", "onCreate: " + jsonArray)

        val jsonArry1 = getSortedList(jsonArray)
        Log.d("TAG", "onCreate: " + jsonArry1)

        if (jsonArry1 != null) {
            hashMapList = ArrayList()
            for (i in 0 until jsonArry1.length()) {
                val jsonobject: JSONObject = jsonArry1.getJSONObject(i)
                val map = TreeMap<String, Any>()
                map.put("amount", jsonobject.getDouble("amount"))
                map.put("name", jsonobject.getString("name"))
                hashMapList.add(map)
            }
        }

        Log.d("TAG", "onCreate: " + hashMapList)
    }

    @Throws(JSONException::class)
    fun getSortedList(array: JSONArray): JSONArray? {
        val list: MutableList<JSONObject?> = ArrayList()
        for (i in 0 until array.length()) {
            list.add(array.getJSONObject(i))
        }
        Collections.sort(list, SortBasedOnAmount())
        return JSONArray(list)
    }

    class SortBasedOnAmount : Comparator<JSONObject?> {
        override fun compare(lhs: JSONObject?, rhs: JSONObject?): Int {
            try {
                if (lhs != null && rhs != null) {
                    return if (lhs.getInt("amount") > rhs.getInt("amount"))
                        1
                    else if (lhs.getInt("amount") < rhs.getInt("amount"))
                        -1
                    else
                        0
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            return 0
        }
    }
}