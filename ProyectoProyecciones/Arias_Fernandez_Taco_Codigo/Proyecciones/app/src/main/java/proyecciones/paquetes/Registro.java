package proyecciones.paquetes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Registro extends AppCompatActivity implements View.OnClickListener {
    TextView txtcedula,nombre,apellido,clave1,clave2;
    Button volver,registro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        txtcedula=(TextView)findViewById(R.id.txtRegistroCedula);
        nombre=(TextView)findViewById(R.id.txtRegistroNombre);
        apellido=(TextView)findViewById(R.id.txtRegistroApellido);
        clave1=(TextView)findViewById(R.id.txtRegistroClave1);
        clave2=(TextView)findViewById(R.id.txtRegistroClave2);
        volver=(Button) findViewById(R.id.btnRegistroVolver);
        registro=(Button) findViewById(R.id.btnRegistroGuardar);
        volver.setOnClickListener(this);
        registro.setOnClickListener(this);


    }

    public boolean validadorDeCedula(String cedula) {
        boolean cedulaCorrecta = false;

        try {

            if (cedula.length() == 10)
            {
                int tercerDigito = Integer.parseInt(cedula.substring(2, 3));
                if (tercerDigito < 6) {
                    int[] coefValCedula = { 2, 1, 2, 1, 2, 1, 2, 1, 2 };
                    int verificador = Integer.parseInt(cedula.substring(9,10));
                    int suma = 0;
                    int digito = 0;
                    for (int i = 0; i < (cedula.length() - 1); i++) {
                        digito = Integer.parseInt(cedula.substring(i, i + 1))* coefValCedula[i];
                        suma += ((digito % 10) + (digito / 10));
                    }

                    if ((suma % 10 == 0) && (suma % 10 == verificador)) {
                        cedulaCorrecta = true;
                    }
                    else if ((10 - (suma % 10)) == verificador) {
                        cedulaCorrecta = true;
                    } else {
                        cedulaCorrecta = false;
                    }
                } else {
                    cedulaCorrecta = false;
                }
            } else {
                cedulaCorrecta = false;
            }
        } catch (NumberFormatException nfe) {
            cedulaCorrecta = false;
        } catch (Exception err) {

            cedulaCorrecta = false;
        }

        if (!cedulaCorrecta) {
            txtcedula.setError("Cedula Incorrecta");
        }
        return cedulaCorrecta;
    }




    @Override
    public void onClick(View v) {
        String usuario="";

        switch (v.getId()){
            case R.id.btnRegistroGuardar:
                if(txtcedula.getText().toString().equals("")){
                    txtcedula.setError("Ingrese Numero Cedula");
                }else{
                    if(validadorDeCedula(txtcedula.getText().toString())){
                        if(nombre.getText().toString().equals("")){
                            nombre.setError("Ingrese Nombre");
                        }else{
                            if(apellido.getText().toString().equals("")){
                                apellido.setError("Ingrese Apellido");
                            }else{
                                if(clave1.getText().toString().equals("")){
                                    clave1.setError("Ingrese Contraseña");
                                }else{
                                    if(clave2.getText().toString().equals("")){
                                        clave2.setError("Verifique Contraseña");
                                    }else{
                                        if(clave1.getText().toString().equals(clave2.getText().toString())){
                                            Conexion conn = new Conexion();
                                            Connection conexionMySQL = conn.conectar();
                                            try {

                                                Statement st  = conexionMySQL.createStatement();
                                                ResultSet rs = st.executeQuery("SELECT NOMBRE FROM usuarios WHERE CED ="+txtcedula.getText().toString());
                                                while(rs.next()){
                                                    usuario=rs.getString(1);
                                                }
                                                conexionMySQL.close();
                                                conexionMySQL = conn.conectar();

                                                if(usuario.equals("")){
                                                    String query="INSERT INTO usuarios (CED,NOMBRE,APELLIDO,CONTRASENA) VALUES ('"+txtcedula.getText().toString() +"', '"+nombre.getText().toString()+"','"+apellido.getText().toString()+"','"+clave1.getText().toString()+"')";
                                                    Statement st1 = conexionMySQL.createStatement();
                                                    st1.executeUpdate(query);
                                                    AlertDialog.Builder alerta1 = new AlertDialog.Builder(Registro.this);
                                                    alerta1.setMessage("Ususario registrado con éxito");
                                                    AlertDialog titulo1 = alerta1.create();
                                                    titulo1.setTitle("Confirmación");
                                                    titulo1.show();
                                                }else{
                                                    txtcedula.setError("Usuario ya existe");
                                                }


                                            } catch (SQLException e) {
                                                e.printStackTrace();
                                            }


                                        }else{
                                            clave1.setText("");
                                            clave2.setText("");
                                            clave1.setError("No coinciden las contraseñas");
                                            clave2.setError("No coinciden las contraseñas");
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
                break;
            case R.id.btnRegistroVolver:
                Intent intent = new Intent (v.getContext(), MainActivity.class);
                startActivityForResult(intent, 0);
                break;


        }
    }


}
