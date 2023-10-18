package pablo.maruottolo.a06_inmobiliariapmdm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

import pablo.maruottolo.a06_inmobiliariapmdm.databinding.ActivityAddInmueble1Binding;

import pablo.maruottolo.a06_inmobiliariapmdm.modelo.Inmueble;

public class AddInmuebleActivity1 extends AppCompatActivity {
    private ActivityAddInmueble1Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Declaramos el binding para cargar los objetos e indetificadores
        binding = ActivityAddInmueble1Binding.inflate(getLayoutInflater());
        //Con esto no hace falta declarar variables porque las va reconocer
        setContentView(binding.getRoot());

      binding.btnCancelarInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        binding.btnAddInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Inmueble inmueble = crearInmueble();

                if (inmueble != null) {
                    //hacer el intent
                    Intent intent = new Intent();

                    //poner el bundle
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("INMUEBLE", inmueble);
                    intent.putExtras(bundle);

                    //comunicar el resultado correcto
                    setResult(RESULT_OK, intent);
                    finish();
                    //terminar

                } else {
                    Toast.makeText(AddInmuebleActivity1.this, "FALTAN DATOS", Toast.LENGTH_SHORT).show();
                }
           }
        });
            }

            private Inmueble crearInmueble() {
                if (binding.txtDireccionInmu.getText().toString().isEmpty()) {
                    return null;
                }
                if (binding.txtNumeroInmu.getText().toString().isEmpty()) {
                    return null;
                }
                if (binding.txtCiudadInmu.getText().toString().isEmpty()) {
                    return null;
                }
                if (binding.txtProvinciaInmu.getText().toString().isEmpty()) {
                    return null;
                }
                if (binding.txtCodigoPosInmu.getText().toString().isEmpty()) {
                    return null;
                }
                if (binding.rbValoracionInmueble.getRating() < 0.5) {
                    return null;
                }

                Inmueble inmueble = new Inmueble(
                        binding.txtDireccionInmu.getText().toString(),
                        Integer.parseInt(binding.txtNumeroInmu.getText().toString()),
                        binding.txtCiudadInmu.getText().toString(),
                        binding.txtProvinciaInmu.getText().toString(),
                        binding.txtCodigoPosInmu.getText().toString(),
                        binding.rbValoracionInmueble.getRating()
                );

                return inmueble;

            }

        }
