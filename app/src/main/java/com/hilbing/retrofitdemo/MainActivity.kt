package com.hilbing.retrofitdemo

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Response
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy


class MainActivity : AppCompatActivity() {

    private lateinit var retrofitService: AlbumService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        retrofitService = RetrofitInstance
            .getRetrofitInstance()
            .create(AlbumService::class.java)

        getRequestWithPathParameters()
        getRequestWithQueryParameter()



    }

    private fun getRequestWithQueryParameter(){
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

    private fun getRequestWithPathParameters(){
        //path parameter example
        val pathResponse: LiveData<Response<AlbumItem>> = liveData {
            val response = retrofitService.getAlbum(3)
            emit(response)
        }

        pathResponse.observe(this, Observer {
            val title = it.body()?.title
            Toast.makeText(applicationContext, title, Toast.LENGTH_SHORT).show()
        })
    }
}

