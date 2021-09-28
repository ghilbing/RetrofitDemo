package com.hilbing.retrofitdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val retrofitService = RetrofitInstance
            .getRetrofitInstance()
            .create(AlbumService::class.java)
        val responseLiveData: LiveData<Response<Albums>> = liveData {
            val response = retrofitService.getSortedAlbums(3)
            emit(response)
        }
        responseLiveData.observe(this, Observer {
            val albumsList: MutableListIterator<AlbumItem>? = it.body()?.listIterator()
            if(albumsList != null){
                while(albumsList.hasNext()){
                    val albumItem = albumsList.next()
                    val result = " " + "Album title :  ${albumItem.title}" + "\n"+
                            " " + "Album id : ${albumItem.id}"+ "\n"+
                            " " + "User id : ${albumItem.userId}"+"\n\n\n"
                    text_view.append(result)
                }
            }

        })
    }
}

