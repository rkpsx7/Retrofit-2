package com.example.retrofit_2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private EditText mEtPostId;
    private Button mBtnFetch;
    private PostAdapter adapter;
    private RecyclerView mRecyclerView;
    private ArrayList<ResponseModel> responseModelArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        mEtPostId = findViewById(R.id.etPostId);
        mBtnFetch = findViewById(R.id.btnFetch);
        mRecyclerView = findViewById(R.id.recyclerView);
        mBtnFetch.setOnClickListener(v -> callApi());
    }

    private void callApi() {
        ApiService apiService = NetWork.getInstance().create(ApiService.class);
        int postId = Integer.parseInt(mEtPostId.getText().toString());
        apiService.getPosts(postId).enqueue(new Callback<List<ResponseModel>>() {
            @Override
            public void onResponse(Call<List<ResponseModel>> call, Response<List<ResponseModel>> response) {
                if (response.body() != null) {
                    responseModelArrayList = (ArrayList<ResponseModel>) response.body();
                    setRecyclerView();
                }

            }

            @Override
            public void onFailure(Call<List<ResponseModel>> call, Throwable t) {

            }
        });
    }

    private void setRecyclerView() {
        adapter = new PostAdapter(responseModelArrayList);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(layoutManager);
    }
}