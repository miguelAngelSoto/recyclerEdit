package com.cieep.a06_recyclerviewsyalertsdialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.cieep.a06_recyclerviewsyalertsdialog.adapters.TodosAdapter;
import com.cieep.a06_recyclerviewsyalertsdialog.modelos.ToDo;
import com.google.android.material.snackbar.Snackbar;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;


import com.cieep.a06_recyclerviewsyalertsdialog.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding binding;
    private ArrayList<ToDo> todosList;
    public static ActivityResultLauncher<Intent> launcherEdit;

    private TodosAdapter adapter;
    // Encargado de indicar como se organizaran los elementos en el recycler
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        todosList = new ArrayList<>();
        creaTodos();

        adapter = new TodosAdapter(MainActivity.this, todosList, R.layout.todo_model_view);
        layoutManager = new LinearLayoutManager(MainActivity.this);

        binding.contentMain.contenedor.setAdapter(adapter);
        binding.contentMain.contenedor.setLayoutManager(layoutManager);

        launcherEdit = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == RESULT_OK && result.getData() != null) {
                    //result.getData().getExtras().getString("Carta");
                }
            }
        });

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createToDo().show();
            }
        });
    }

    private AlertDialog createToDo(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Add new To Do");
        builder.setCancelable(false);
        View alertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.todo_model_alert,null);
        TextView txtTitulo = alertView.findViewById(R.id.txtTituloToDoModelAlert);
        TextView txtContenido = alertView.findViewById(R.id.txtContenidoToDoAlert);
        builder.setView(alertView);
        builder.setNegativeButton("Cancelar",null);
        builder.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(!txtTitulo.getText().toString().isEmpty() && !txtContenido.getText().toString().isEmpty()){
                    ToDo toDo = new ToDo(txtTitulo.getText().toString(),txtContenido.getText().toString(),false);
                    todosList.add(toDo);
                    adapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(MainActivity.this, "Faltan Datos",Toast.LENGTH_SHORT);
                }
            }
        });
        return builder.create();
    }

    private void creaTodos() {
        for (int i = 0; i < 10; i++) {
            todosList.add(new ToDo("Tarea "+i, "Contenido "+i, false));
        }
    }
}