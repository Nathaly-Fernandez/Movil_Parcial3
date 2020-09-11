package proyecciones.paquetes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InformeMensual extends AppCompatActivity implements View.OnClickListener {
    Spinner opciones1, opciones2;
    TextView proyAlimentacion,proyVivienda,proyTransporte,proyEducacion,proyEntrete,proyMedic,proyGastoVar,proyDeuda,proyPersona,totalIngresos;
    TextView gastAlimentacion,gastVivienda,gastTransporte,gastEducacion,gastEntrete,gastMedic,gastyGastoVar,gastDeuda,gastPersona,ahorroInfor;
    String MesConsulta="",anioConsulta="";
    Button consultarInf,volverinf;
    String CedulaPermanente="",nombrePermanente,apellidoPermanente;
    String consultaFecha="";
    String id_gastos="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informe_mensual);
        opciones1=(Spinner)findViewById(R.id.spnAnio);
        opciones2=(Spinner)findViewById(R.id.spnMes);
        gastAlimentacion=(TextView) findViewById(R.id.txtInfGastAlimentacio);
        gastVivienda=(TextView) findViewById(R.id.txtInfGastVivie);;
        gastTransporte=(TextView) findViewById(R.id.txtInfGastTransp);
        gastEducacion=(TextView) findViewById(R.id.txtInfGastEduca);
        gastEntrete=(TextView) findViewById(R.id.txtInfGastEntrete);
        gastMedic=(TextView) findViewById(R.id.txtInfGastMedic);
        gastyGastoVar=(TextView) findViewById(R.id.txtInfGastGastVari);
        gastDeuda=(TextView) findViewById(R.id.txtInfGastDeuda);
        gastPersona=(TextView) findViewById(R.id.txtInfGastPerso);

        proyAlimentacion=(TextView) findViewById(R.id.txtInfProyAlimentacion);
        proyVivienda=(TextView) findViewById(R.id.txtInfProyVivie);
        proyTransporte=(TextView) findViewById(R.id.txtInfProyTransp);
        proyEducacion=(TextView) findViewById(R.id.txtInfProyEduca);
        proyEntrete=(TextView) findViewById(R.id.txtInfProyEntret);
        proyMedic=(TextView) findViewById(R.id.txtInfProyMedic);
        proyGastoVar=(TextView) findViewById(R.id.txtInfProyGastVar);
        proyDeuda=(TextView) findViewById(R.id.txtInfProyDeuda);
        proyPersona=(TextView) findViewById(R.id.txtInfProyPers);
        volverinf=(Button) findViewById(R.id.btnInfVolver);
        consultarInf=(Button) findViewById(R.id.btnInfConsulta) ;
        consultarInf.setOnClickListener(this);
        volverinf.setOnClickListener(this);
        totalIngresos=(TextView)findViewById(R.id.txtInfTotIngresos);
        ahorroInfor=(TextView)findViewById(R.id.txtInfAhorro);


        Bundle bundle = getIntent().getExtras();
        CedulaPermanente=bundle.getString("cedula");
        nombrePermanente=bundle.getString("nombre");
        apellidoPermanente=bundle.getString("apellido");
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.anios,android.R.layout.simple_spinner_item);
        opciones1.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,R.array.meses,android.R.layout.simple_spinner_item);
        opciones2.setAdapter(adapter1);


        opciones2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String op1 = parent.getItemAtPosition(position).toString();
                switch (op1){
                    case "Enero":
                        MesConsulta="01";
                        break;
                    case "Febrero":
                        MesConsulta="02";
                        break;
                    case "Marzo":
                        MesConsulta="03";
                        break;
                    case "Abril":
                        MesConsulta="04";
                        break;
                    case "Mayo":
                        MesConsulta="05";
                        break;
                    case "Junio":
                        MesConsulta="06";
                        break;
                    case "Julio":
                        MesConsulta="07";
                        break;
                    case "Agosto":
                        MesConsulta="08";
                        break;
                    case "Septiembre":
                        MesConsulta="09";
                        break;
                    case "Octubre":
                        MesConsulta="10";
                        break;
                    case "Noviembre":
                        MesConsulta="11";
                        break;
                    case "Diciembre":
                        MesConsulta="12";
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        opciones1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String op2 = parent.getItemAtPosition(position).toString();
                anioConsulta=op2;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnInfConsulta:
                float total=0;
                float otrosIngresos=0;
                ahorroInfor.setText(0+"");
                totalIngresos.setText(0+"");
                proyAlimentacion.setText(0+"");
                proyVivienda.setText(0+"");
                proyEducacion.setText(0+"");
                proyMedic.setText(0+"");
                proyDeuda.setText(0+"");
                proyPersona.setText(0+"");
                proyTransporte.setText(0+"");
                proyGastoVar.setText(0+"");
                proyEntrete.setText(0+"");
                gastAlimentacion.setText(0+"");
                gastVivienda.setText(0+"");
                gastTransporte.setText(0+"");
                gastEducacion.setText(0+"");
                gastEntrete.setText(0+"");
                gastMedic.setText(0+"");
                gastyGastoVar.setText(0+"");
                gastDeuda.setText(0+"");
                gastPersona.setText(0+"");
                id_gastos="";
                Conexion conn = new Conexion();
                Connection conexionMySQL = conn.conectar();
                try {
                    Statement st0  = conexionMySQL.createStatement();
                    ResultSet rs0 = st0.executeQuery("SELECT DISTINCT ingresos.ID_INGRESO , ingresos.FECHA , gastos_proy.ID_GASTOS,gastos_proy.VIVIENDA,gastos_proy.ALIMENTACION,gastos_proy.EDUCACION, gastos_proy.TRANSPORTE,gastos_proy.MEDICOS,gastos_proy.ENTRETENIMIENTO,gastos_proy.PERSONAL,gastos_proy.DEUDAS,gastos_proy.GASTOS_VARIOS FROM ingresos INNER JOIN gastos_proy on gastos_proy.ID_INGRESO=ingresos.ID_INGRESO WHERE ingresos.CED="+CedulaPermanente);
                    while(rs0.next()){
                        consultaFecha=rs0.getString(2);
                        String[] parts1 = consultaFecha.split("-");
                        if(parts1[0].equals(anioConsulta)){
                            if(parts1[1].equals(MesConsulta)){
                                id_gastos=rs0.getString(3);
                                if(rs0.getString(5).equals("")){
                                    proyAlimentacion.setText(0+"");
                                }else{
                                    proyAlimentacion.setText(rs0.getString(5)+"");
                                }
                                if(rs0.getString(4).equals("")){
                                    proyVivienda.setText(0+"");
                                }else{
                                    proyVivienda.setText(rs0.getString(4)+"");
                                }
                                if(rs0.getString(6).equals("")){
                                    proyEducacion.setText(0+"");
                                }else{
                                    proyEducacion.setText(rs0.getString(6)+"");
                                }
                                if(rs0.getString(8).equals("")){
                                    proyMedic.setText(0+"");
                                }else{
                                    proyMedic.setText(rs0.getString(8)+"");
                                }
                                if(rs0.getString(11).equals("")){
                                    proyDeuda.setText(0+"");
                                }else{
                                    proyDeuda.setText(rs0.getString(8)+"");
                                }
                                if(rs0.getString(10).equals("")){
                                    proyPersona.setText(0+"");
                                }else{
                                    proyPersona.setText(rs0.getString(10)+"");
                                }
                                if(rs0.getString(7).equals("")){
                                    proyTransporte.setText(0+"");
                                }else{
                                    proyTransporte.setText(rs0.getString(7)+"");
                                }
                                if(rs0.getString(12).equals("")){
                                    proyGastoVar.setText(0+"");
                                }else{
                                    proyGastoVar.setText(rs0.getString(12)+"");
                                }
                                if(rs0.getString(9).equals("")){
                                    proyEntrete.setText(0+"");
                                }else{
                                    proyEntrete.setText(rs0.getString(9)+"");
                                }
                            }
                        }else{
                        }
                    }
                    conexionMySQL.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                if(id_gastos.equals("")){
                    AlertDialog.Builder alerta = new AlertDialog.Builder(InformeMensual.this);
                    alerta.setMessage("No existen datos para la fecha consultada");
                    AlertDialog titulo = alerta.create();
                    titulo.setTitle("Alerta");
                    titulo.show();
                }else {
                    try {
                        conn = new Conexion();
                        conexionMySQL = conn.conectar();
                        Statement st = conexionMySQL.createStatement();
                        ResultSet rs = st.executeQuery("SELECT TOTAL_ALIMENTACION FROM alimentacion  WHERE ID_GASTOS = " + id_gastos);
                        while (rs.next()) {
                            if (rs.getString(1).equals("")) {
                                gastAlimentacion.setText(0 + "");
                            } else {
                                gastAlimentacion.setText(rs.getString(1));
                            }
                        }
                        conexionMySQL.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    try {
                        conn = new Conexion();
                        conexionMySQL = conn.conectar();
                        Statement st = conexionMySQL.createStatement();
                        ResultSet rs = st.executeQuery("SELECT TOT_VIVIENDA FROM vivienda  WHERE ID_GASTOS = " + id_gastos);

                        while (rs.next()) {
                            if (rs.getString(1).equals("")) {
                                gastVivienda.setText(0 + "");
                            } else {
                                gastVivienda.setText(rs.getString(1));
                            }
                        }
                        conexionMySQL.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    try {
                        conn = new Conexion();
                        conexionMySQL = conn.conectar();
                        Statement st = conexionMySQL.createStatement();
                        ResultSet rs = st.executeQuery("SELECT TOTAL_EDU FROM educacion  WHERE ID_GASTOS = " + id_gastos);

                        while (rs.next()) {
                            if (rs.getString(1).equals("")) {
                                gastEducacion.setText(0 + "");
                            } else {
                                gastEducacion.setText(rs.getString(1));
                            }
                        }
                        conexionMySQL.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    try {
                        conn = new Conexion();
                        conexionMySQL = conn.conectar();
                        Statement st = conexionMySQL.createStatement();
                        ResultSet rs = st.executeQuery("SELECT TOTAL_DEUD FROM deudas  WHERE ID_GASTOS = " + id_gastos);

                        while (rs.next()) {
                            if (rs.getString(1).equals("")) {
                                gastDeuda.setText(0 + "");
                            } else {
                                gastDeuda.setText(rs.getString(1));
                            }
                        }
                        conexionMySQL.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    try {
                        conn = new Conexion();
                        conexionMySQL = conn.conectar();
                        Statement st = conexionMySQL.createStatement();
                        ResultSet rs = st.executeQuery("SELECT TOTAL_MED FROM medicos  WHERE ID_GASTOS = " + id_gastos);

                        while (rs.next()) {
                            if (rs.getString(1).equals("")) {
                                gastMedic.setText(0 + "");
                            } else {
                                gastMedic.setText(rs.getString(1));
                            }
                        }
                        conexionMySQL.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    try {
                        conn = new Conexion();
                        conexionMySQL = conn.conectar();
                        Statement st = conexionMySQL.createStatement();
                        ResultSet rs = st.executeQuery("SELECT TOT_PERSONAL FROM personal  WHERE ID_GASTOS = " + id_gastos);

                        while (rs.next()) {
                            if (rs.getString(1).equals("")) {
                                gastPersona.setText(0 + "");
                            } else {
                                gastPersona.setText(rs.getString(1));
                            }
                        }
                        conexionMySQL.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    try {
                        conn = new Conexion();
                        conexionMySQL = conn.conectar();
                        Statement st = conexionMySQL.createStatement();
                        ResultSet rs = st.executeQuery("SELECT TOTAL_TRANS FROM transporte  WHERE ID_GASTOS = " + id_gastos);

                        while (rs.next()) {
                            if (rs.getString(1).equals("")) {
                                gastTransporte.setText(0 + "");
                            } else {
                                gastTransporte.setText(rs.getString(1));
                            }
                        }
                        conexionMySQL.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    try {
                        conn = new Conexion();
                        conexionMySQL = conn.conectar();
                        Statement st = conexionMySQL.createStatement();
                        ResultSet rs = st.executeQuery("SELECT TOTAL_GASTV FROM gastos_varios  WHERE ID_GASTOS = " + id_gastos);

                        while (rs.next()) {
                            if (rs.getString(1).equals("")) {
                                gastyGastoVar.setText(0 + "");
                            } else {
                                gastyGastoVar.setText(rs.getString(1));
                            }
                        }
                        conexionMySQL.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    try {
                        conn = new Conexion();
                        conexionMySQL = conn.conectar();
                        Statement st = conexionMySQL.createStatement();
                        ResultSet rs = st.executeQuery("SELECT TOTAL_ENTRE FROM entretenimieto  WHERE ID_GASTOS = " + id_gastos);

                        while (rs.next()) {
                            if (rs.getString(1).equals("")) {
                                gastEntrete.setText(0 + "");
                            } else {
                                gastEntrete.setText(rs.getString(1));
                            }
                        }
                        conexionMySQL.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    try {
                        conn = new Conexion();
                        conexionMySQL = conn.conectar();
                        Statement st = conexionMySQL.createStatement();
                        ResultSet rs = st.executeQuery("SELECT SUELDOS,OTROS_INGRE FROM ingresos  WHERE ID_GASTOS = " + id_gastos);
                        while (rs.next()) {
                            if (rs.getString(1).equals("")) {
                                total = 0;
                            } else {
                                total = Float.parseFloat(rs.getString(1));
                            }
                            if (rs.getString(2).equals("")) {
                                otrosIngresos = 0;
                            } else {
                                otrosIngresos = Float.parseFloat(rs.getString(2));
                            }
                        }
                        conexionMySQL.close();
                        total = total + otrosIngresos;
                        totalIngresos.setText(total + "");

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    try {
                        conn = new Conexion();
                        conexionMySQL = conn.conectar();
                        Statement st = conexionMySQL.createStatement();
                        ResultSet rs = st.executeQuery("SELECT AHORRO FROM gastos_proy  WHERE ID_GASTOS = " + id_gastos);
                        while (rs.next()) {
                            if (rs.getString(1).equals("")) {
                                ahorroInfor.setText(0 + "");
                            } else {
                                ahorroInfor.setText(rs.getString(1));
                            }
                        }
                        conexionMySQL.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                break;

            case R.id.btnInfVolver:
                Intent i = new Intent(this, MenuInicial.class);
                i.putExtra("cedula", CedulaPermanente);
                i.putExtra("nombre", nombrePermanente);
                i.putExtra("apellido", apellidoPermanente);
                startActivity(i);
                break;

        }

    }
}
