package com.example.musicboundservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Music> listMusic;
    private ArrayList<String> listName;
    private ArrayAdapter adapter;
    private MyService myService;
    private boolean isBound = false;
    private ServiceConnection connection;
    private ListView lvListName;
    private TextView tvName;
    private  ImageView img;
    private int mp3=R.raw.trentinhbanduoitinhyeu;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listMusic = new ArrayList<>();

        listMusic.add(new Music("Trên tình bạn dưới tình yêu", R.raw.trentinhbanduoitinhyeu, R.drawable.img1));
        listMusic.add(new Music("Double B ,BINZ-SOOBIN", R.raw.doubleb, R.drawable.img2));
        listMusic.add(new Music("Chẳng thể tìm được em", R.raw.changthetimduocem, R.drawable.img3));
        listMusic.add(new Music("Cô Gái Vàng", R.raw.cogaivang, R.drawable.img4));
        listMusic.add(new Music("Thiên Đàng", R.raw.thiendang, R.drawable.img5));
        listMusic.add(new Music("Tình Yêu Khủng Long", R.raw.tinhyeukhunglomg, R.drawable.img2));
        listMusic.add(new Music("Gặp Nhưng Không Ở Lại", R.raw.gapnhungkhongolai, R.drawable.img1));


        listName=new ArrayList<>();
        for (Iterator<Music> iterator = listMusic.iterator(); iterator.hasNext(); ) {
            Music music =  iterator.next();
            listName.add(music.getName());
        }

        adapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1,listName);
        lvListName=findViewById(R.id.lvListName);
        lvListName.setAdapter(adapter);
        tvName=findViewById(R.id.tvName);
        img=findViewById(R.id.img);

//        intent = new Intent(MainActivity.this, MainActivity.class);


        ///
//        Intent intent = new Intent(MainActivity.this, MainActitivyPlay.class);
//        intent.putExtra("name", listMusic.get(1).getName());
//        intent.putExtra("img", listMusic.get(1).getImage());
//        intent.putExtra("mp3", listMusic.get(1).getMp3());
        ///

        final ImageView imgPLay = findViewById(R.id.imgPlay);
        final ImageView imgNext = findViewById(R.id.imgNext);
        final ImageView imgPrevious = findViewById(R.id.imgPrevious);
        final ImageView imgPause = findViewById(R.id.imgPause);



        // Khởi tạo ServiceConnection
        connection = new ServiceConnection() {

            // Phương thức này được hệ thống gọi khi kết nối tới service bị lỗi
            @Override
            public void onServiceDisconnected(ComponentName name) {

                isBound = false;
            }

            // Phương thức này được hệ thống gọi khi kết nối tới service thành công
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                MyService.MyBinder binder = (MyService.MyBinder) service;
                myService = binder.getService(); // lấy đối tượng MyService
                isBound = true;
            }
        };
        // Khởi tạo intent
        final Intent intent2 =
                new Intent(MainActivity.this,
                        MyService.class);
        intent2.putExtra("mp3", mp3);
//        System.out.println(un);
        bindService(intent2, connection,
                Context.BIND_AUTO_CREATE);

        imgPLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Bắt đầu một service sủ dụng bind
                myService.play();
                imgPLay.setVisibility(View.INVISIBLE);
                imgPause.setVisibility(View.VISIBLE);
                System.out.println("play");
                isBound = true;
            }
        });

        imgPause.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Nếu Service đang hoạt động
                if(isBound){
                    myService.pause();
                    imgPLay.setVisibility(View.VISIBLE);
                    imgPause.setVisibility(View.INVISIBLE);
                    System.out.println("pause");
                }
            }
        });


        imgNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // nếu service đang hoạt động
                if(isBound){
                    // tua bài hát
                    myService.fastForward();
                }else{
                    Toast.makeText(MainActivity.this,
                            "Service chưa hoạt động", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imgPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isBound){
                    // tua nguoc bài hát
                    myService.fastBack();
                }else{
                    Toast.makeText(MainActivity.this,
                            "Service chưa hoạt động", Toast.LENGTH_SHORT).show();
                }
            }
        });
        lvListName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tvName.setText(listMusic.get(position).getName());
                img.setImageResource(listMusic.get(position).getImage());
                mp3=listMusic.get(position).getMp3();
                index=position;
                if(isBound) {
                    // Tắt Service
                    unbindService(connection);
                    isBound = false;
                    intent2.putExtra("mp3", mp3);
                    bindService(intent2, connection,
                            Context.BIND_AUTO_CREATE);
                    imgPLay.setVisibility(View.VISIBLE);
                    imgPause.setVisibility(View.INVISIBLE );
                }
            }
        });

    }


}