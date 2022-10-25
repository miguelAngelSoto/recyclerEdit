package com.cieep.a06_recyclerviewsyalertsdialog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cieep.a06_recyclerviewsyalertsdialog.databinding.ActivityEditBinding;
import com.cieep.a06_recyclerviewsyalertsdialog.modelos.ToDo;

public class EditActivity extends AppCompatActivity {
    private ActivityEditBinding binding;
    ToDo toDo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        binding = ActivityEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        toDo = (ToDo) bundle.getSerializable("Carta");

        cargarVista();
    }

    private void cargarVista(){
        binding.inputCiudadEdit.setText(piso.getCiudad());
        binding.inputNumEdit.setText(piso.getNumero());
        binding.inputCpEdit.setText(piso.getCp());
        binding.inputProvinciaEdit.setText(piso.getProvincia());
        binding.inputDirEdit.setText(piso.getDireccion());
        binding.ratingBarEdit.setRating(piso.getValoracion());

        binding.btnEditEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Piso pisoEdit = new Piso(binding.inputDirEdit.getText().toString()
                        ,binding.inputNumEdit.getText().toString(),binding.inputCiudadEdit.getText().toString(),
                        binding.inputProvinciaEdit.getText().toString(),binding.inputCpEdit.getText().toString(),
                        binding.ratingBarEdit.getRating());
                if( (piso = pisoOk())!= null){
                    Bundle bundle = new Bundle();
                    bundle.putString("OPERACION","EDITAR");
                    bundle.putSerializable("EDIT", pisoEdit);
                    bundle.putSerializable("ID", id);
                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    setResult(RESULT_OK,intent);
                    finish();
                }else{
                    Toast.makeText(editActivty.this,"Faltan Datos",Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
    }

}