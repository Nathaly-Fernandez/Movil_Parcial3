package proyecciones.paquetes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toolbar;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Gastos extends AppCompatActivity implements View.OnClickListener {
    Spinner opciones1, opciones2;
    String CedulaPermanente="",nombrePermanente,apellidoPermanente,valiPrimera;
    Button guardar,volver;
    TextView valor;
    String id_tabla="",id_gastos,itemIgreso="",tabla="";
    String consultaFecha="";
    float total_comparar=0,total_alimentacion=0,total_vivienda=0,total_educacion=0,total_medicos=0,total_deudas=0,total_personal=0,total_transporte=0,total_gastos_varios=0,total_entretenimiento=0;
    float proy_alimentacion=0,proy_vivienda=0,proy_educacion=0,proy_medicos=0,proy_deudas=0,proy_personal=0,proy_transporte=0,proy_gastos_varios=0,proy_entretenimiento=0;
    float totalComparar=0;
    float proycComparar=0;
    float totalAcumulado=0;
    String totalTabla="";
    float porcentaje=0,ahorro=0,validador1=0;
    String alimentacion="",vivienda="",educacion="",medicos="",deudas="",personal="",transporte="",gastos_varios="",entretenimiento="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gastos);
        opciones1=(Spinner)findViewById(R.id.spn1);
        opciones2=(Spinner)findViewById(R.id.spn2);
        valor = (TextView) findViewById(R.id.txtValorGasto);
        volver = (Button) findViewById(R.id.btnVolverGastos);
        volver.setOnClickListener(this);
        guardar = (Button) findViewById(R.id.bntGuardarGasto);
        guardar.setOnClickListener(this);
        Bundle bundle = getIntent().getExtras();
        CedulaPermanente=bundle.getString("cedula");
        nombrePermanente=bundle.getString("nombre");
        apellidoPermanente=bundle.getString("apellido");
        valiPrimera=bundle.getString("valiPrimera");

        Conexion conn1 = new Conexion();
        Connection conexionMySQL1 = conn1.conectar();
        Calendar c1 = Calendar.getInstance();
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
        String fechaActual = df1.format(c1.getTime());
        try {
            conn1 = new Conexion();
            conexionMySQL1 = conn1.conectar();
            Statement st1  = conexionMySQL1.createStatement();
            ResultSet rs1 = st1.executeQuery("SELECT ID_INGRESO,FECHA FROM ingresos WHERE CED = "+CedulaPermanente);
            while(rs1.next()){
                if(rs1.getString(2).equals("")){
                    AlertDialog.Builder alerta = new AlertDialog.Builder(Gastos.this);
                    alerta.setMessage("Realice el registro de sus ingresos para poder ingresar gastos");
                    AlertDialog titulo = alerta.create();
                    titulo.setTitle("Alerta");
                    titulo.show();
                    guardar.setEnabled(false);
                }else{
                    consultaFecha=rs1.getString(2);
                    String[] parts1 = consultaFecha.split("-");
                    String[] parts2 = fechaActual.split("-");
                    if(parts1[0].equals(parts2[0])) {
                        if (parts1[1].equals(parts2[1])) {
                            guardar.setEnabled(true);
                        }
                    }
                }
            }
            if(consultaFecha.equals("")){
                AlertDialog.Builder alerta = new AlertDialog.Builder(Gastos.this);
                alerta.setMessage("Realice el registro de sus ingresos para poder ingresar gastos");
                AlertDialog titulo = alerta.create();
                titulo.setTitle("Alerta");
                titulo.show();
                guardar.setEnabled(false);
            }
            conexionMySQL1.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.opcionesGastos,android.R.layout.simple_spinner_item);
        opciones1.setAdapter(adapter);
        opciones1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String opspiner1 = parent.getItemAtPosition(position).toString();
                switch (opspiner1){
                    case "Alimentacion":
                            id_tabla="ID_ALIMENTACION";
                            tabla="alimentacion";
                        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(parent.getContext(),R.array.alimentacion,android.R.layout.simple_spinner_item);
                        opciones2.setAdapter(adapter1);
                        opciones2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String op = parent.getItemAtPosition(position).toString();
                            switch (op){
                                case "Compras de Víveres":
                                    itemIgreso="COMPRAS_VIVERES";
                                    break;
                                case "Gastos Diarios":
                                    itemIgreso="GASTOS_DIARIOS";
                                    break;
                                case "Comida Restaurantes":
                                    itemIgreso="COMIDA_RESTAURANTE";
                                    break;
                            }
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                        break;
                    case "Vivienda":
                        id_tabla="ID_VIVIENDA";
                        tabla="vivienda";
                        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(parent.getContext(),R.array.vivienda,android.R.layout.simple_spinner_item);
                        opciones2.setAdapter(adapter2);
                        opciones2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                String op = parent.getItemAtPosition(position).toString();
                                switch (op){
                                    case "Arriendo":
                                        itemIgreso="ARRIENDO";
                                        break;
                                    case "Luz":
                                        itemIgreso="LUZ";
                                        break;
                                    case "Agua":
                                        itemIgreso="AGUA";
                                        break;
                                    case "Telefono":
                                        itemIgreso="TELEFONO";
                                        break;
                                    case "Plan Celular":
                                        itemIgreso="PLAN_CELULAR";
                                        break;
                                    case "Internet":
                                        itemIgreso="INTERNET";
                                        break;
                                    case "Tv Cable":
                                        itemIgreso="TV_CABLE";
                                        break;
                                    case "Mantenimientos Hogar":
                                        itemIgreso="MANTENIMIENTOS_HOGAR";
                                        break;
                                }
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });
                        break;
                    case "Educacion":
                        id_tabla="ID_EDUCACION";
                        tabla="educacion";
                        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(parent.getContext(),R.array.educacion,android.R.layout.simple_spinner_item);
                        opciones2.setAdapter(adapter3);
                        opciones2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                String op = parent.getItemAtPosition(position).toString();
                                switch (op){
                                    case "Pensiones":
                                        itemIgreso="PENSIONES";
                                        break;
                                    case "Colaciones":
                                        itemIgreso="COLACION";
                                        break;
                                    case "Utiles Escolares":
                                        itemIgreso="UTILES_ESCOLARES";
                                        break;
                                    case "Recorrido":
                                        itemIgreso="RECORRIDO";
                                        break;
                                }
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });
                        break;
                    case "Medicos":
                        id_tabla="ID_MEDICOS";
                        tabla="medicos";
                        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(parent.getContext(),R.array.medicos,android.R.layout.simple_spinner_item);
                        opciones2.setAdapter(adapter4);
                        opciones2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                String op = parent.getItemAtPosition(position).toString();
                                switch (op){
                                    case "Gastos Médicos":
                                        itemIgreso="GASTOS_MEDICOS";
                                        break;
                                }
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });
                        break;
                    case "Deudas":
                        id_tabla="ID_DEUDAS";
                        tabla="deudas";
                        ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(parent.getContext(),R.array.deudas,android.R.layout.simple_spinner_item);
                        opciones2.setAdapter(adapter5);
                        opciones2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                String op = parent.getItemAtPosition(position).toString();
                                switch (op){
                                    case "Prestamos":
                                        itemIgreso="PRESTAMOS";
                                        break;
                                    case "Otas Deudas":
                                        itemIgreso="OTRAS_DEUDAS";
                                        break;
                                }
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });
                        break;
                    case "Personal":
                        id_tabla="ID_PERSONAL";
                        tabla="personal";
                        ArrayAdapter<CharSequence> adapter6 = ArrayAdapter.createFromResource(parent.getContext(),R.array.personal,android.R.layout.simple_spinner_item);
                        opciones2.setAdapter(adapter6);
                        opciones2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                String op = parent.getItemAtPosition(position).toString();
                                switch (op){
                                    case "Ropa":
                                        itemIgreso="ROPA";
                                        break;
                                    case "Cuidado Persona":
                                        itemIgreso="CUIDADO_PERSONA";
                                        break;
                                    case "Productos de Higiene":
                                        itemIgreso="PRODUCTOS_HIGIENE";
                                        break;
                                }
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });
                        break;
                    case "Transporte":
                        id_tabla="ID_TRANS";
                        tabla="transporte";
                        ArrayAdapter<CharSequence> adapter7 = ArrayAdapter.createFromResource(parent.getContext(),R.array.transporte,android.R.layout.simple_spinner_item);
                        opciones2.setAdapter(adapter7);
                        opciones2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                String op = parent.getItemAtPosition(position).toString();
                                switch (op){
                                    case "Pasaje o Gasolina":
                                        itemIgreso="PASAJE_GAS";
                                        break;
                                    case "Mantenimiento Auto":
                                        itemIgreso="MANTENIMIETO_AUTO";
                                        break;
                                }
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });
                        break;
                    case "Gastos Varios":
                        id_tabla="ID_GASTOSV";
                        tabla="gastos_varios";
                        ArrayAdapter<CharSequence> adapter8 = ArrayAdapter.createFromResource(parent.getContext(),R.array.gastosVarios,android.R.layout.simple_spinner_item);
                        opciones2.setAdapter(adapter8);
                        opciones2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                String op = parent.getItemAtPosition(position).toString();
                                switch (op){
                                    case "Gastos Hogar":
                                        itemIgreso="GASTOS_HOGAR";
                                        break;
                                    case "Gastos Personales":
                                        itemIgreso="GASTOS_PERSONALES";
                                        break;
                                }

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });
                        break;
                    case "Entretenimiento":
                        id_tabla="ID_ENTRETENIMIETO";
                        tabla="entretenimieto";
                        ArrayAdapter<CharSequence> adapter9 = ArrayAdapter.createFromResource(parent.getContext(),R.array.entretenimiento,android.R.layout.simple_spinner_item);
                        opciones2.setAdapter(adapter9);
                        opciones2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                String op = parent.getItemAtPosition(position).toString();
                                switch (op){
                                    case "Paseo":
                                        itemIgreso="PASEOS";
                                        break;
                                    case "Otros":
                                        itemIgreso="OTROS";
                                        break;
                                }
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bntGuardarGasto:

                if(valor.getText().toString().equals("") || Float.parseFloat(valor.getText().toString())<=0 ){
                    valor.setError("Valor Incorrecto");
                }else {

                    validador1 = 0;
                    id_gastos = "";
                    proy_alimentacion = 0;
                    proy_vivienda = 0;
                    proy_educacion = 0;
                    proy_medicos = 0;
                    proy_deudas = 0;
                    proy_personal = 0;
                    proy_transporte = 0;
                    proy_gastos_varios = 0;
                    proy_entretenimiento = 0;
                    proycComparar = 0;
                    totalComparar = 0;
                    porcentaje = 0;
                    String queryTabla = "";
                    String validador = "";
                    totalAcumulado = 0;
                    id_gastos = "";
                    Conexion conn = new Conexion();
                    Connection conexionMySQL = conn.conectar();
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    String formattedDate = df.format(c.getTime());
                    try {
                        Statement st0 = conexionMySQL.createStatement();
                        ResultSet rs0 = st0.executeQuery("SELECT DISTINCT ingresos.ID_INGRESO , ingresos.FECHA , gastos_proy.ID_GASTOS,gastos_proy.VIVIENDA,gastos_proy.ALIMENTACION,gastos_proy.EDUCACION, gastos_proy.TRANSPORTE,gastos_proy.MEDICOS,gastos_proy.ENTRETENIMIENTO,gastos_proy.PERSONAL,gastos_proy.DEUDAS,gastos_proy.GASTOS_VARIOS,gastos_proy.AHORRO FROM ingresos INNER JOIN gastos_proy on gastos_proy.ID_INGRESO=ingresos.ID_INGRESO WHERE ingresos.CED=" + CedulaPermanente);
                        while (rs0.next()) {
                            consultaFecha = rs0.getString(2);
                            String[] parts1 = consultaFecha.split("-");
                            String[] parts2 = formattedDate.split("-");
                            if (parts1[0].equals(parts2[0])) {
                                if (parts1[1].equals(parts2[1])) {
                                    id_gastos = rs0.getString(3);
                                    if (rs0.getString(13).equals("")) {
                                        ahorro = 0;
                                    } else {
                                        ahorro = Float.parseFloat(rs0.getString(13));
                                    }
                                    if (rs0.getString(5).equals("")) {
                                        proy_alimentacion = 0;
                                    } else {
                                        proy_alimentacion = Float.parseFloat(rs0.getString(5));
                                    }
                                    if (rs0.getString(4).equals("")) {
                                        proy_vivienda = 0;
                                    } else {
                                        proy_vivienda = Float.parseFloat(rs0.getString(4));
                                    }
                                    if (rs0.getString(6).equals("")) {
                                        proy_educacion = 0;
                                    } else {
                                        proy_educacion = Float.parseFloat(rs0.getString(6));
                                    }
                                    if (rs0.getString(8).equals("")) {
                                        proy_medicos = 0;
                                    } else {
                                        proy_medicos = Float.parseFloat(rs0.getString(8));
                                    }
                                    if (rs0.getString(11).equals("")) {
                                        proy_deudas = 0;
                                    } else {
                                        proy_deudas = Float.parseFloat(rs0.getString(11));
                                    }
                                    if (rs0.getString(10).equals("")) {
                                        proy_personal = 0;
                                    } else {
                                        proy_personal = Float.parseFloat(rs0.getString(10));
                                    }
                                    if (rs0.getString(7).equals("")) {
                                        proy_transporte = 0;
                                    } else {
                                        proy_transporte = Float.parseFloat(rs0.getString(7));
                                    }
                                    if (rs0.getString(12).equals("")) {
                                        proy_gastos_varios = 0;
                                    } else {
                                        proy_gastos_varios = Float.parseFloat(rs0.getString(12));
                                    }
                                    if (rs0.getString(9).equals("")) {
                                        proy_entretenimiento = 0;
                                    } else {
                                        proy_entretenimiento = Float.parseFloat(rs0.getString(9));
                                    }

                                }
                            } else {
                            }
                        }
                        conexionMySQL.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    try {
                        conn = new Conexion();
                        conexionMySQL = conn.conectar();
                        Statement st1 = conexionMySQL.createStatement();
                        ResultSet rs1 = st1.executeQuery("SELECT " + id_tabla + " FROM " + tabla + " WHERE ID_GASTOS=" + id_gastos);
                        while (rs1.next()) {
                            validador = rs1.getString(1);
                        }
                        conexionMySQL.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    switch (tabla) {
                        case "alimentacion":
                            queryTabla = "INSERT INTO alimentacion (ID_ALIMENTACION, FECHA, COMPRAS_VIVERES, GASTOS_DIARIOS,COMIDA_RESTAURANTE, TOTAL_ALIMENTACION, ID_GASTOS) VALUES (0,'" + formattedDate + "', 0, 0, 0, 0," + id_gastos + ")";
                            totalTabla = "TOTAL_ALIMENTACION";
                            proycComparar = proy_alimentacion;
                            break;
                        case "vivienda":
                            queryTabla = "INSERT INTO vivienda (ID_VIVIENDA,ID_GASTOS,FECHA,LUZ,AGUA,TELEFONO,PLAN_CELULAR,INTERNET,TV_CABLE,MANTENIMIENTOS_HOGAR,TOT_VIVIENDA,ARRIENDO) VALUES(0," + id_gastos + ", '" + formattedDate + "', 0, 0, 0, 0, 0, 0, 0, 0,0)";
                            totalTabla = "TOT_VIVIENDA";
                            proycComparar = proy_vivienda;
                            break;
                        case "educacion":
                            queryTabla = "INSERT INTO educacion (ID_EDUCACION,ID_GASTOS,FECHA,PENSIONES,COLACION,UTILES_ESCOLARES,RECORRIDO,TOTAL_EDU) VALUES (0," + id_gastos + ", '" + formattedDate + "',0, 0, 0, 0, 0)";
                            totalTabla = "TOTAL_EDU";
                            proycComparar = proy_educacion;
                            break;
                        case "medicos":
                            queryTabla = "INSERT INTO medicos (ID_MEDICOS, ID_GASTOS, FECHA, GASTOS_MEDICOS, TOTAL_MED) VALUES (0," + id_gastos + ", '" + formattedDate + "', 0, 0)";
                            totalTabla = "TOTAL_MED";
                            proycComparar = proy_medicos;
                            break;
                        case "deudas":
                            queryTabla = "INSERT INTO deudas (ID_DEUDAS, ID_GASTOS, FECHA, PRESTAMOS, OTRAS_DEUDAS, TOTAL_DEUD) VALUES (0, '" + id_gastos + "', '" + formattedDate + "', 0, 0, 0)";
                            totalTabla = "TOTAL_DEUD";
                            proycComparar = proy_deudas;
                            break;
                        case "personal":
                            queryTabla = "INSERT INTO personal (ID_PERSONAL, FECHA, ROPA, CUIDADO_PERSONA, PRODUCTOS_HIGIENE, ID_GASTOS) VALUES (0, '" + formattedDate + "', 0, 0, 0, " + id_gastos + ")";
                            totalTabla = "TOT_PERSONAL";
                            proycComparar = proy_personal;
                            break;
                        case "transporte":
                            queryTabla = "INSERT INTO transporte (ID_TRANS,ID_GASTOS,FECHA,PASAJE_GAS,MANTENIMIETO_AUTO,TOTAL_TRANS) VALUES (0, " + id_gastos + ", '" + formattedDate + "', 0,0, 0);";
                            totalTabla = "TOTAL_TRANS";
                            proycComparar = proy_transporte;
                            break;
                        case "gastos_varios":
                            queryTabla = "INSERT INTO gastos_varios (ID_GASTOSV, ID_GASTOS, FECHA, GASTOS_HOGAR, GASTOS_PERSONALES, TOTAL_GASTV) VALUES (0," + id_gastos + ", '" + formattedDate + "', 0,0,0)";
                            totalTabla = "TOTAL_GASTV";
                            proycComparar = proy_gastos_varios;
                            break;
                        case "entretenimieto":
                            queryTabla = "INSERT INTO entretenimieto (ID_ENTRETENIMIETO,ID_GASTOS, FECHA, PASEOS, OTROS, TOTAL_ENTRE) VALUES (0, " + id_gastos + ", '" + formattedDate + "', 0, 0, 0)";
                            totalTabla = "TOTAL_ENTRE";
                            proycComparar = proy_entretenimiento;
                            break;
                    }
                    porcentaje = (float) (proycComparar * 0.80);

                    try {
                        conn = new Conexion();
                        conexionMySQL = conn.conectar();
                        Statement st = conexionMySQL.createStatement();
                        ResultSet rs = st.executeQuery("SELECT " + totalTabla + " FROM " + tabla + "  WHERE ID_GASTOS = " + id_gastos);

                        while (rs.next()) {
                            if (rs.getString(1).equals("")) {
                                totalComparar = 0;
                            } else {
                                totalComparar = Float.parseFloat(rs.getString(1));
                            }
                        }
                        conexionMySQL.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if (valiPrimera.equals("0")) {
                        if (totalComparar >= porcentaje) {
                            AlertDialog.Builder alerta = new AlertDialog.Builder(Gastos.this);
                            alerta.setMessage("Está al límite del porcentaje de gastos");
                            AlertDialog titulo = alerta.create();
                            titulo.setTitle("Alerta");
                            titulo.show();
                        }
                    }
                    if (valiPrimera.equals("1")) {
                        totalAcumulado = Float.parseFloat(valor.getText().toString());
                        validador1 = ahorro;
                    } else {
                        totalAcumulado = totalComparar + Float.parseFloat(valor.getText().toString());
                        validador1 = totalComparar;
                    }
                    if (validador.equals("")) {
                        try {
                            conn = new Conexion();
                            conexionMySQL = conn.conectar();
                            Statement st2 = conexionMySQL.createStatement();
                            st2.executeUpdate(queryTabla);
                            conexionMySQL.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        if (Float.parseFloat(valor.getText().toString()) <= validador1) {
                            if (totalAcumulado <= validador1) {
                                if (valiPrimera.equals("0")) {
                                    if (totalAcumulado >= porcentaje) {
                                        AlertDialog.Builder alerta = new AlertDialog.Builder(Gastos.this);
                                        alerta.setMessage("Está al límite del porcentaje de gastos");
                                        AlertDialog titulo = alerta.create();
                                        titulo.setTitle("Alerta");
                                        titulo.show();
                                    }
                                    if (totalAcumulado > proycComparar) {
                                        AlertDialog.Builder alerta = new AlertDialog.Builder(Gastos.this);
                                        alerta.setMessage("Usted ha exedido ");
                                        AlertDialog titulo = alerta.create();
                                        titulo.setTitle("Alerta");
                                        titulo.show();
                                    }

                                }

                                try {
                                    conn = new Conexion();
                                    conexionMySQL = conn.conectar();
                                    String query1 = "UPDATE " + tabla + " SET " + itemIgreso + " = " + valor.getText() + ", " + totalTabla + "=" + totalAcumulado + "  WHERE ID_GASTOS =" + id_gastos;
                                    Statement st2 = conexionMySQL.createStatement();
                                    st2.executeUpdate(query1);
                                    conexionMySQL.close();
                                    AlertDialog.Builder alerta1 = new AlertDialog.Builder(Gastos.this);
                                    alerta1.setMessage("Gasto registrado con éxito");
                                    AlertDialog titulo1 = alerta1.create();
                                    titulo1.setTitle("Confirmación");
                                    titulo1.show();
                                    valor.setText(0 + "");
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                ahorro = ahorro - totalAcumulado;
                                try {
                                    conn = new Conexion();
                                    conexionMySQL = conn.conectar();
                                    String query1 = "UPDATE gastos_proy SET AHORRO=" + ahorro + "  WHERE ID_GASTOS =" + id_gastos;
                                    Statement st2 = conexionMySQL.createStatement();
                                    st2.executeUpdate(query1);
                                    conexionMySQL.close();

                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                if (valiPrimera.equals("0")) {
                                    if (totalAcumulado >= porcentaje) {
                                        AlertDialog.Builder alerta = new AlertDialog.Builder(Gastos.this);
                                        alerta.setMessage("Usted está llegando al límite de gastos proyectados");
                                        AlertDialog titulo = alerta.create();
                                        titulo.setTitle("Alerta");
                                        titulo.show();
                                    }
                                }
                            } else {
                                AlertDialog.Builder alerta = new AlertDialog.Builder(Gastos.this);
                                float dispo = proycComparar - totalComparar;
                                alerta.setMessage("Valor ingresado supera el disponible de ingresos");
                                AlertDialog titulo = alerta.create();
                                titulo.setTitle("Alerta");
                                titulo.show();
                            }
                        } else {
                            AlertDialog.Builder alerta = new AlertDialog.Builder(Gastos.this);
                            float dispo = proycComparar - totalComparar;
                            alerta.setMessage("Valor ingresado supera el disponible de ingresos");
                            AlertDialog titulo = alerta.create();
                            titulo.setTitle("Alerta");
                            titulo.show();
                        }
                    } else {
                        if (Float.parseFloat(valor.getText().toString()) <= validador1) {
                            if (totalAcumulado <= validador1) {
                                if (valiPrimera.equals("0")) {
                                    if (totalAcumulado >= porcentaje) {
                                        AlertDialog.Builder alerta = new AlertDialog.Builder(Gastos.this);
                                        alerta.setMessage("Está al límite del porcentaje de gastos");
                                        AlertDialog titulo = alerta.create();
                                        titulo.setTitle("Alerta");
                                        titulo.show();
                                    }
                                    if (totalAcumulado > proycComparar) {
                                        AlertDialog.Builder alerta = new AlertDialog.Builder(Gastos.this);
                                        alerta.setMessage("Usted ha exedido ");
                                        AlertDialog titulo = alerta.create();
                                        titulo.setTitle("Alerta");
                                        titulo.show();
                                    }

                                }
                                try {
                                    conn = new Conexion();
                                    conexionMySQL = conn.conectar();
                                    String query1 = "UPDATE " + tabla + " SET " + itemIgreso + " = " + valor.getText() + ", " + totalTabla + "=" + totalAcumulado + "  WHERE ID_GASTOS =" + id_gastos;
                                    Statement st2 = conexionMySQL.createStatement();
                                    st2.executeUpdate(query1);
                                    conexionMySQL.close();
                                    AlertDialog.Builder alerta1 = new AlertDialog.Builder(Gastos.this);
                                    alerta1.setMessage("Gasto registrado con éxito");
                                    AlertDialog titulo1 = alerta1.create();
                                    titulo1.setTitle("Confirmación");
                                    titulo1.show();
                                    valor.setText(0 + "");
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                ahorro = ahorro - totalAcumulado;
                                try {
                                    conn = new Conexion();
                                    conexionMySQL = conn.conectar();
                                    String query1 = "UPDATE gastos_proy SET AHORRO=" + ahorro + "  WHERE ID_GASTOS =" + id_gastos;
                                    Statement st2 = conexionMySQL.createStatement();
                                    st2.executeUpdate(query1);
                                    conexionMySQL.close();

                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                if (valiPrimera.equals("0")) {
                                    if (totalAcumulado >= porcentaje) {
                                        AlertDialog.Builder alerta = new AlertDialog.Builder(Gastos.this);
                                        alerta.setMessage("Usted esta llegando al limite de gastos proyectados");
                                        AlertDialog titulo = alerta.create();
                                        titulo.setTitle("Alerta");
                                        titulo.show();
                                    }
                                }
                            } else {
                                AlertDialog.Builder alerta = new AlertDialog.Builder(Gastos.this);
                                alerta.setMessage("Valor ingresado supera el disponible de ingresos");
                                AlertDialog titulo = alerta.create();
                                titulo.setTitle("Alerta");
                                titulo.show();
                            }
                        } else {
                            AlertDialog.Builder alerta = new AlertDialog.Builder(Gastos.this);
                            alerta.setMessage("Valor ingresado supera el disponible de ingresos");
                            AlertDialog titulo = alerta.create();
                            titulo.setTitle("Alerta");
                            titulo.show();
                        }
                    }
                }
                break;
            case R.id.btnVolverGastos:
                Intent i = new Intent(this, MenuInicial.class);
                i.putExtra("cedula", CedulaPermanente);
                i.putExtra("nombre", nombrePermanente);
                i.putExtra("apellido", apellidoPermanente);
                startActivity(i);
                break;
        }
    }
}
