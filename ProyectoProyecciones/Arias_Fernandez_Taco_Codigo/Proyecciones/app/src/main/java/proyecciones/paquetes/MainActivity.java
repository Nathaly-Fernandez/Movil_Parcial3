package proyecciones.paquetes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button ingreso,registro;
    TextView usua,clave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ingreso=(Button) findViewById(R.id.btnIngreso);
        registro=(Button) findViewById(R.id.btnRegistro);
        usua=(TextView) findViewById(R.id.txtUsuario);
        clave=(TextView) findViewById(R.id.txtClave);
        ingreso.setOnClickListener(this);
        registro.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        int id=v.getId();

        switch (id){
            case R.id.btnIngreso:
                String usuario="";
                String pass="";
                String nombre="";
                String apellido="";
                Conexion conn = new Conexion();
                Connection conexionMySQL = conn.conectar();
                if(usua.getText().toString().equals("")){
                    usua.setError("Ingrese Usuario");
                }else{
                    if(usua.getText().toString().equals("")){
                        usua.setError("Ingrese Contraseña");
                    }else{
                        try {
                            Statement st  = conexionMySQL.createStatement();
                            ResultSet rs = st.executeQuery("SELECT * FROM usuarios WHERE CED="+usua.getText().toString());
                            while(rs.next()){
                                usuario=rs.getString(1);
                                nombre=rs.getString(2);
                                apellido=rs.getString(3);
                                pass=rs.getString(4);
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        if(usua.getText().toString().equals(usuario)){
                            if(clave.getText().toString().equals(pass)){
                                Intent i = new Intent(this, MenuInicial.class);
                                i.putExtra("cedula", usuario);
                                i.putExtra("nombre", nombre);
                                i.putExtra("apellido", apellido);
                                startActivity(i);
                            }else{
                                AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
                                alerta.setMessage("Clave Incorrecta");
                                AlertDialog titulo = alerta.create();
                                titulo.setTitle("ERROR");
                                titulo.show();
                            }
                        }else{
                            AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
                            alerta.setMessage("No existe el Usuario");
                            AlertDialog titulo = alerta.create();
                            titulo.setTitle("ERROR");
                            titulo.show();
                        }
                    }
                }
                try {
                    conexionMySQL.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnRegistro:
                Intent intent = new Intent (v.getContext(), Registro.class);
                startActivityForResult(intent, 0);
                break;
        }
    }
}
