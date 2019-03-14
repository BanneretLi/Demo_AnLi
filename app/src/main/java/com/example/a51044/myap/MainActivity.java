package com.example.a51044.myap;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a51044.myap.adapter.MyAdapter;
import com.example.a51044.myap.bean.MyData;
import com.example.a51044.myap.databinding.AbcBinding;
import com.example.a51044.myap.mvvm.Contact;
import com.example.a51044.myap.mvvm.MyCallBack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity<T> extends AppCompatActivity implements MyCallBack {

    AbcBinding binding;
    private HashMap<String, Object> head = new HashMap<>();
    private HashMap<String, Object> map = new HashMap<>();
    private List<MyData.DataBean>mList=new ArrayList<>();
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.abc);

        ViewModel2 viewModel2 = new ViewModel2(this);
        binding.setHehe(viewModel2);

        viewModel2.postData(Contact.Bas, head, map, MyData.class);

        binding.recy.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new MyAdapter(mList,this);
        binding.recy.setAdapter(myAdapter);

    }


    @Override
    public void sucess(Object data) {
        if (data instanceof MyData) {
            MyData myData= (MyData) data;
            mList.addAll(myData.getData());
            myAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void error(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

}
