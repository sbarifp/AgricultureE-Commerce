package com.sbarifp.pemasaranproduk.data.hawkstorage

import android.content.Context
import com.orhanobut.hawk.Hawk
import com.sbarifp.pemasaranproduk.data.model.auth.LoginResponse

class HawkStorage {
    companion object{
        private const val USER_KEY = "user_key"
        private val hawkStorage = HawkStorage()

        fun instance(context: Context?): HawkStorage{
            Hawk.init(context).build()
            return hawkStorage
        }
    }

    fun setUser(user: LoginResponse){
        Hawk.put(USER_KEY, user)
    }

    fun getUser(): LoginResponse{
        return Hawk.get(USER_KEY)
    }

    fun isLogin(): Boolean{
        if (Hawk.contains(USER_KEY)){
            return true
        }
        return false
    }

    fun deleteAll(){
        Hawk.deleteAll()
    }
}