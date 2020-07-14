package mx.edu.tesoem.isc.riclr.basedatossqlite;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import mx.edu.tesoem.isc.riclr.basedatossqlite.BaseDatos.DatosConexion;
import mx.edu.tesoem.isc.riclr.basedatossqlite.DTO.DatosDTO;
import mx.edu.tesoem.isc.riclr.basedatossqlite.DTO.DatosParcelable;

public class ListarInformacionActivity extends AppCompatActivity {

    GridView gvDatos;
    AdaptadorBase adaptadorBase;
    private final int detalles =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_informacion);

        gvDatos = findViewById(R.id.gvDatos);
        cargardatos();

        gvDatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DatosDTO datos = (DatosDTO)adaptadorBase.getItem(position);
                DatosParcelable datosParcelable = new DatosParcelable(datos.getId(), datos.getNombre(),
                        datos.getEdad(), datos.getSexo());

                Intent intent = new Intent(ListarInformacionActivity.this, DetallesActivity.class);
                intent.putExtra("datosParcelable", datosParcelable);
                startActivityForResult(intent, detalles);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        cargardatos();
    }

    private void cargardatos(){
        DatosConexion conexion= new DatosConexion(this);
        List<DatosDTO> listaDatos = new ArrayList<>();

        conexion.open();
        if(conexion.ListarTodos()){
            listaDatos = conexion.getListadatos();
            adaptadorBase = new AdaptadorBase(listaDatos, this);
            gvDatos.setAdapter(adaptadorBase);
        }
        conexion.close();
    }
}