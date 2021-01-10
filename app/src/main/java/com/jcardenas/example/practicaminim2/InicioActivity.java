package com.jcardenas.example.practicaminim2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import com.jcardenas.example.practicaminim2.models.User;

public class InicioActivity extends AppCompatActivity {

    private EditText usr;
    private Button explore;
    private GithubAPI APIinterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        usr = findViewById(R.id.userEditText);
        explore = findViewById(R.id.exploreButton);

        //Start retrofit
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //Attaching Interceptor to a client
        OkHttpClient client = new OkHttpClient().newBuilder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        APIinterface = retrofit.create(GithubAPI.class);

        loadUser();

    }

    public void loadUser(){
        explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("click", "ok");

                Call<User> call = APIinterface.infoUser(usr.getText().toString());
                Log.d("User",usr.getText().toString());
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Log.d("Info","tenemos respuesta");
                        if(response.isSuccessful()){
                            Log.d("onResponse", "tenemos user");
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("user", usr.getText().toString());
                            startActivity(intent);
                        }
                        else{
                            Log.d("Info:", "user not found");
                            Toast.makeText(getApplicationContext(),"User not found: " + response.code(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Error : " + t.getMessage(), Toast.LENGTH_LONG);
                    }

                });
            }
        });
    }
}