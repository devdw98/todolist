package com.example.todolist

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import io.realm.Realm
import io.realm.Sort
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {
    val realm = Realm.getDefaultInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        // 전체 할 일 정보를 가져와서 날짜순으로 내림차순 정렬
        val realmResult = realm.where<Todo>()
            .findAll()
            .sort("date", Sort.DESCENDING)
        val adapter = TodoListAdapter(realmResult)
        listView.adapter = adapter

        // 데이터가 변경되면 어댑터에 적용
        realmResult.addChangeListener { _ -> adapter.notifyDataSetChanged() }

        listView.setOnItemClickListener { parent, view, position, id ->
            // 할 일 수정
            startActivity<EditActivity>("id" to id)
        }

        // 새 할 일 추가
        fab.setOnClickListener {
            startActivity<EditActivity>()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}