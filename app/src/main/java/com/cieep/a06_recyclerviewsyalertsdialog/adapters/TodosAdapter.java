package com.cieep.a06_recyclerviewsyalertsdialog.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cieep.a06_recyclerviewsyalertsdialog.EditActivity;
import com.cieep.a06_recyclerviewsyalertsdialog.MainActivity;
import com.cieep.a06_recyclerviewsyalertsdialog.R;
import com.cieep.a06_recyclerviewsyalertsdialog.modelos.ToDo;

import java.util.ArrayList;

public class TodosAdapter extends RecyclerView.Adapter<TodosAdapter.TodoVH> {

    // Elementos para que funcione el Recycler
    // Activity que contiene el RecyclerView
    private Context context;
    // Los datos a mostrar
    private ArrayList<ToDo> objects;
    // La plantilla para los datos
    private int cardLayout;


    public TodosAdapter(Context context, ArrayList<ToDo> objects, int cardLayout) {
        this.context = context;
        this.objects = objects;
        this.cardLayout = cardLayout;
    }

    /**
     * Se llama de forma automática, para se creen nuevos elementos de la plantilla
     * @param parent
     * @param viewType
     * @return Un Objeto CARD ya LISTO PARA ASIGNAR DATOS
     */
    @NonNull
    @Override
    public TodoVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View toDoView = LayoutInflater.from(context).inflate(cardLayout, null);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        toDoView.setLayoutParams(layoutParams);
        return new TodoVH(toDoView);
    }

    /**
     * Asignara los valores a los elementos de la vista  del Card
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull TodoVH holder, int position) {
        ToDo toDo = objects.get(position);
        holder.lblTitulo.setText(toDo.getTitulo());
        holder.lblContenido.setText(toDo.getContenido());
        holder.lblFecha.setText(toDo.getFecha().toString());
        if (toDo.isCompletado()) {
            holder.btnCompletado.setImageResource(android.R.drawable.checkbox_on_background);
        }
        else {
            holder.btnCompletado.setImageResource(android.R.drawable.checkbox_off_background);
        }
        holder.cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Carta",toDo);
                intent.putExtras(bundle);
                MainActivity.launcherEdit.launch(intent);
            }
        });

        holder.btnCompletado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmaUser("Estás seguro de cambiar el estado", toDo).show();
            }
        });
        holder.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                objects.remove(toDo);
                notifyDataSetChanged();
            }
        });
    }

    /**
     * Retornar la cantidad de elementos que hay que instanciar
     * @return
     */
    @Override
    public int getItemCount() {
        return objects.size();
    }

    private AlertDialog confirmaUser(String mensaje, ToDo toDo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(mensaje);
        builder.setCancelable(false);

        builder.setNegativeButton("NO", null);
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                toDo.setCompletado(!toDo.isCompletado());
                notifyDataSetChanged();
            }
        });

        return builder.create();
    }

    public class TodoVH extends RecyclerView.ViewHolder {

        CardView cd;
        TextView lblTitulo, lblContenido, lblFecha, btnDel;
        ImageButton btnCompletado;
        public TodoVH(@NonNull View itemView) {
            super(itemView);
            lblTitulo = itemView.findViewById(R.id.lblTituloToDoViewModel);
            lblContenido = itemView.findViewById(R.id.lblContenidoTodoViewModel);
            lblFecha = itemView.findViewById(R.id.lblFechaTodoViewModel);
            btnCompletado = itemView.findViewById(R.id.btnCompletadoToDoViewModel);
            btnDel = itemView.findViewById(R.id.btnDeleteModel);
            cd = itemView.findViewById(R.id.cdModel);
        }
    }
}
