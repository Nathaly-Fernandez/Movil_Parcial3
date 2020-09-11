package proyecciones.paquetes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MenuInicial extends AppCompatActivity implements View.OnClickListener {
    Button proyecciones,gastos,reporteActual,reportes,volver,graficos;
    TextView nombre,ced;
    String CedulaPermanente="",nombrePermanente="",apellidoPermanente="";
    String valPrimera="0";
    String consultaFecha="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_inicial);
        Bundle bundle = getIntent().getExtras();
        CedulaPermanente=bundle.getString("cedula");
        nombrePermanente=bundle.getString("nombre");
        apellidoPermanente=bundle.getString("apellido");
        proyecciones = (Button) findViewById(R.id.btnProyecciones);
        gastos=(Button)findViewById(R.id.bntGastosD);
        graficos=(Button)findViewById(R.id.btnGraficos);
        reportes=(Button)findViewById(R.id.btnReportes);
        volver=(Button)findViewById(R.id.btnVolver);
        nombre= (TextView) findViewById(R.id.textView5);
        nombre.setText("Bienvenido: "+nombrePermanente+" "+apellidoPermanente+"");

        proyecciones.setOnClickListener(this);
        gastos.setOnClickListener(this);
        graficos.setOnClickListener(this);
        reportes.setOnClickListener(this);
        volver.setOnClickListener(this);
        Conexion conn = new Conexion();
        Connection conexionMySQL = conn.conectar();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String fechaActual = df.format(c.getTime());
        try {
            conn = new Conexion();
            conexionMySQL = conn.conectar();
            Statement st  = conexionMySQL.createStatement();
            ResultSet rs = st.executeQuery("SELECT ID_INGRESO,FECHA FROM ingresos WHERE CED = "+CedulaPermanente+" LIMIT 1");
            while(rs.next()){
                if(rs.getString(2).equals("")){
                    valPrimera="1";
                }else{
                    consultaFecha=rs.getString(2);
                    String[] parts1 = consultaFecha.split("-");
                    String[] parts2 = fechaActual.split("-");
                    if(parts1[0].equals(parts2[0])) {
                        if (parts1[1].equals(parts2[1])) {
                            valPrimera="1";
                        }
                    }
                }
        }
            if(consultaFecha.equals("")){
                valPrimera="1";
            }
        conexionMySQL.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.btnProyecciones:
                Intent i = new Intent(this, proyecciones.class);
                i.putExtra("cedula", CedulaPermanente);
                i.putExtra("nombre", nombrePermanente);
                i.putExtra("apellido", apellidoPermanente);
                i.putExtra("valiPrimera", valPrimera);
                startActivity(i);
                break;
            case R.id.bntGastosD:
                Intent i1 = new Intent(this, Gastos.class);
                i1.putExtra("cedula", CedulaPermanente);
                i1.putExtra("nombre", nombrePermanente);
                i1.putExtra("apellido", apellidoPermanente);
                i1.putExtra("valiPrimera", valPrimera);
                startActivity(i1);
                break;
            case R.id.btnReportes:
                Intent i2 = new Intent(this, InformeMensual.class);
                i2.putExtra("cedula", CedulaPermanente);
                i2.putExtra("nombre", nombrePermanente);
                i2.putExtra("apellido", apellidoPermanente);
                startActivity(i2);
                break;
            case R.id.btnVolver:
                Intent i4 = new Intent(this, MainActivity.class);
                startActivity(i4);
                break;
            case R.id.btnGraficos:
                Intent i6 = new Intent(this, Grafico.class);
                i6.putExtra("cedula", CedulaPermanente);
                i6.putExtra("nombre", nombrePermanente);
                i6.putExtra("apellido", apellidoPermanente);
                startActivity(i6);
                break;
        }
    }
}
