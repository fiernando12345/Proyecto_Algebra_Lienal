package com.tec.fernandoalberto.proyecto_algebra_lineal.Unidad_4;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tec.fernandoalberto.proyecto_algebra_lineal.AdapterDatosTabla;
import com.tec.fernandoalberto.proyecto_algebra_lineal.R;
import com.tec.fernandoalberto.proyecto_algebra_lineal.Unidad_2.U2_Rango;

import java.util.ArrayList;

public class U4_Combinacion_Lineal extends AppCompatActivity {

    private ArrayList<String> listaDatos;
    private RecyclerView recycler1;
    private EditText txtFilas, txtResultado;
    private AdapterDatosTabla adapter;
    private Button btnCrear, btnObtener;
    private int filas, columnas, cuadricula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_u4__combinacion__lineal);
        txtFilas= findViewById(R.id.txtCOMBINACIONFila);
        btnCrear= findViewById(R.id.btnCOMBINACIONCrear);
        btnObtener= findViewById(R.id.btnObtenerCOMBINACION);
        recycler1= findViewById(R.id.COMBINACIONRecycler);
        txtResultado= findViewById(R.id.txtResultadoCOMBINACION);
        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtResultado.setText("");
                if(txtFilas.getText().toString().length()==0){
                    btnObtener.setEnabled(false);
                    Toast.makeText(U4_Combinacion_Lineal.this, "Campos no validos", Toast.LENGTH_SHORT).show();
                }else{
                    btnObtener.setEnabled(true);
                    filas= Integer.parseInt(txtFilas.getText().toString());
                    columnas= filas+1;
                    cuadricula= filas * columnas;
                    listaDatos= new ArrayList<>();
                    for (int i=0; i<cuadricula; i++){
                        listaDatos.add("");
                    }
                    recycler1.setLayoutManager(new GridLayoutManager(U4_Combinacion_Lineal.this, columnas));
                    adapter= new AdapterDatosTabla(listaDatos, filas, columnas);
                    recycler1.setAdapter(adapter);

                }
            }
        });

        btnObtener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int contador = 0;
                String[][] result = adapter.getData();
                double[][] matriz = new double[filas][filas];
                for (int i = 0; i < filas; i++) {
                    for (int j = 0; j < filas; j++) {
                        ConstraintLayout rootView = (ConstraintLayout) recycler1.getChildAt(contador++);
                        matriz[i][j] = Double.parseDouble(((EditText)rootView.findViewById(R.id.txtListaRecycler)).getText().toString());
                    }
                }
                try {
                    Jama.Matrix matrix = new Jama.Matrix(matriz);
                    int rango= matrix.rank();
                    if(rango==filas){
                        txtResultado.setText("Combinación Lineal");
                    }else{
                        txtResultado.setText("No es Combinación Lineal");
                    }

                }catch (Exception e){
                    Toast.makeText(U4_Combinacion_Lineal.this, "No se puede calcular esta matriz", Toast.LENGTH_SHORT).show();}
            }
        });
    }
}
