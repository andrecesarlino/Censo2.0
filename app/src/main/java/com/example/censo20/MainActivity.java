package com.example.censo20;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Button btn1;
    private Button btn2;
    private TextView TextNome;
    private TextView TextEndereco;
    private TextView TextTelefone;
    private String name;
    private String address;
    private String phone;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = findViewById(R.id.btnGet);
        btn2 = findViewById(R.id.btnPost);
        TextNome = findViewById(R.id.ViewNome);
        TextEndereco = findViewById(R.id.ViewEndereco);
        TextTelefone = findViewById(R.id.ViewTelefone);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CensoService censoService = CensoService.retrofit.create(CensoService.class);
                final Call<List<Coletor>> call = censoService.repoCenso("1005");

                call.enqueue(new Callback<List<Coletor>>() {
                    @Override
                    public void onResponse(Call<List<Coletor>> call, Response<List<Coletor>> response) {
                        final TextView textView = (TextView) findViewById(R.id.textView);
                        textView.setText(response.body().toString());

                    }

                    @Override
                    public void onFailure(Call<List<Coletor>> call, Throwable t) {
                        final TextView textView = (TextView) findViewById(R.id.textView);
                        textView.setText(""+t.getMessage());

                    }
                });
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://whispering-headland-07022.herokuapp.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                Coletor cen = new Coletor();
                cen.coletor = "1005";
                name = TextNome.getText().toString();
                address = TextEndereco.getText().toString();
                phone = TextTelefone.getText().toString();
                cen.dados="{\"Nome Completo\":\""+name+"\"\"endereco\":\""+address+"\"\"Telefone\":\""+phone+"\"}";

                CensoService service = retrofit.create(CensoService.class);
                Call<Coletor> request = service.PostCenso(cen);

                request.enqueue(new Callback<Coletor>() {
                    @Override
                    public void onResponse(Call<Coletor> call, Response<Coletor> response) {
                        //SetList();
                    }

                    @Override
                    public void onFailure(Call<Coletor> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Erro ao enviar censo. " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}
