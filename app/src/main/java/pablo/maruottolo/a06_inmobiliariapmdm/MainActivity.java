package pablo.maruottolo.a06_inmobiliariapmdm;

import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;

import pablo.maruottolo.a06_inmobiliariapmdm.databinding.ActivityMainBinding;
import pablo.maruottolo.a06_inmobiliariapmdm.modelo.Inmueble;

import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ActivityResultLauncher<Intent> addInmueblesLaucher;
    private ActivityResultLauncher<Intent> editInmuebleLaucher;
    private ArrayList<Inmueble> listaInmueble;
    private Inmueble inmuebleOrigen;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        //Inicializamos la lista de Inmuebles
        listaInmueble = new ArrayList<>();

        inicializarLaucher();

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //abro la actividad AddInmueble
                addInmueblesLaucher.launch(new Intent(MainActivity.this, AddInmuebleActivity1.class));
            }
        });

    }
    private void inicializarLaucher() {

        addInmueblesLaucher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                       if (result.getResultCode() == RESULT_OK) {
                            if (result.getData() != null
                                    && result.getData().getExtras() != null) {

                                Inmueble inmueble = (Inmueble) result.getData().getExtras().getSerializable("INMUEBLE");
                                listaInmueble.add(inmueble);
                                mostrarInmueble();
                               // Toast.makeText(MainActivity.this,inmueble.toString(),Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "NO HAY INFORMACIÓN", Toast.LENGTH_SHORT).show();
                            }
                        } else {

                            Toast.makeText(MainActivity.this, "ACCIÓN CANCELADA", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        editInmuebleLaucher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        //Que hago a la vuelta de Editar Inmueble
                        if(result.getResultCode() == RESULT_OK){
                            if(result.getData() != null && result.getData().getExtras() != null){
                                //modifico el inmueble
                                Inmueble inmueble = (Inmueble) result.getData().getExtras().getSerializable("INMUEBLE");
                                int posicion = result.getData().getExtras().getInt("POSICION");
                                listaInmueble.set(posicion, inmueble);
                                mostrarInmueble();
                            }else{
                                //elimino el inmueble
                                listaInmueble.remove(inmuebleOrigen);
                                mostrarInmueble();
                            }
                        }else{
                            Toast.makeText(MainActivity.this, "ACCIÓN CANCELADA", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

  private void mostrarInmueble() {
      binding.contentMain.contenedorMain.removeAllViews();


      for (int i = 0; i < listaInmueble.size(); i++) {
          Inmueble inmueble = listaInmueble.get(i);
          View inmuebleView = LayoutInflater.from(MainActivity.this).inflate(R.layout.inmueble_fila_view, null);

          TextView lbDireccion = inmuebleView.findViewById(R.id.lbDireccionInmuView);
          TextView lbNumero = inmuebleView.findViewById(R.id.lbNumeroInmuView);
          TextView lbCiudad = inmuebleView.findViewById(R.id.lbCiudadInmuView);
          RatingBar rbValoracion = inmuebleView.findViewById(R.id.ratingBarFila);


          int posicion = i;

          inmuebleView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent intent = new Intent(MainActivity.this,EditInmuebleActivity.class);
                  Bundle bundle  = new Bundle();
                  bundle.putSerializable("INMUEBLE",inmueble);
                  bundle.putInt("POSICION",posicion);
                  intent.putExtras(bundle);
                  inmuebleOrigen = inmueble;
                  editInmuebleLaucher.launch(intent);

              }
          });

          lbDireccion.setText(inmueble.getDireccion());
          lbNumero.setText(String.valueOf(inmueble.getNumero()));
          lbCiudad.setText(inmueble.getCiudad());
          rbValoracion.setRating(inmueble.getValoracion());

          binding.contentMain.contenedorMain.addView(inmuebleView);

      }


    }


}

