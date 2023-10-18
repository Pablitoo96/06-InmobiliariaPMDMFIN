package pablo.maruottolo.a06_inmobiliariapmdm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import pablo.maruottolo.a06_inmobiliariapmdm.databinding.ActivityEditInmuebleBinding;
import pablo.maruottolo.a06_inmobiliariapmdm.modelo.Inmueble;

public class EditInmuebleActivity extends AppCompatActivity {
    private ActivityEditInmuebleBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEditInmuebleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Inmueble inmueble = (Inmueble) bundle.getSerializable("INMUEBLE");
        int posicion = bundle.getInt("POSICION");

        rellenarDatos(inmueble);

        binding.btnEditInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Inmueble inmueble1 = crearInmueble();
                if(inmueble1 != null){
                    Intent intent1 = new Intent();
                    Bundle bundle1 = new Bundle();
                    bundle1.putSerializable("INMUEBLE",inmueble1);
                    bundle1.putInt("POSICION",posicion);
                    intent1.putExtras(bundle1);
                    setResult(RESULT_OK,intent1);
                    finish();
                }else{
                    Toast.makeText(EditInmuebleActivity.this, "Faltan Datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.btnBorrarInmuebleEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });

    }

    private Inmueble crearInmueble() {
        if (binding.txtDireccionInmuEdit.getText().toString().isEmpty()) {
            return null;
        }
        if (binding.txtNumeroInmuEdit.getText().toString().isEmpty()) {
            return null;
        }
        if (binding.txtCiudadInmuEdit.getText().toString().isEmpty()) {
            return null;
        }
        if (binding.txtProvinciaInmuEdit.getText().toString().isEmpty()) {
            return null;
        }
        if (binding.txtCodigoPosInmuEdit.getText().toString().isEmpty()) {
            return null;
        }
        if (binding.rbValoracionInmuebleEdit.getRating() < 0.5) {
            return null;
        }

        Inmueble inmueble = new Inmueble(
                binding.txtDireccionInmuEdit.getText().toString(),
                Integer.parseInt(binding.txtNumeroInmuEdit.getText().toString()),
                binding.txtCiudadInmuEdit.getText().toString(),
                binding.txtProvinciaInmuEdit.getText().toString(),
                binding.txtCodigoPosInmuEdit.getText().toString(),
                binding.rbValoracionInmuebleEdit.getRating()
        );

        return inmueble;

    }


    private void rellenarDatos(Inmueble inmueble){
        binding.txtDireccionInmuEdit.setText(inmueble.getDireccion());
        binding.txtNumeroInmuEdit.setText(String.valueOf (inmueble.getNumero()));
        binding.txtCiudadInmuEdit.setText(inmueble.getCiudad());
        binding.txtProvinciaInmuEdit.setText(inmueble.getProvincia());
        binding.txtCodigoPosInmuEdit.setText(inmueble.getCp());
        binding.rbValoracionInmuebleEdit.setRating(inmueble.getValoracion());

    }
}