package proyecciones.paquetes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class proyecciones extends AppCompatActivity implements View.OnClickListener {
    TextView sueldo,otrosIngresos,vivienda,alimentacion,medicina,educacion,personal;
    TextView transporte,gVarios,deudas,entretenimiento, disponible,ahorro;
    Button guardar,volver;
    String CedulaPermanente="",nombrePermanente,apellidoPermanente,valiPrimera;
    float total=0;
    float totalSalida=0;
    float totalEntrada=0;
    float ahorroMes=0;
    float ahorroGeneral=0;
    String sueldoAntes="";
    String sueldoDespues="";
    String cedula="1727349076";
    String antessueldo="",antesotrosIngresos="",antesvivienda="",antesalimentacion="",antesmedicina="",anteseducacion="",antespersonal="",antestransporte="",antesgVarios="",antesdeudas="",antesentretenimiento="",antesahorro="";
    String despuessueldo="",despuesotrosIngresos="",despuesvivienda="",despuesalimentacion="",despuesmedicina="",despueseducacion="",despuespersonal="",despuestransporte="",despuesgVarios="",despuesdeudas="",despuesentretenimiento="",despuesahorro="";
    String idIngreso="";
    String Ingresoalimentacion="",Ingresovivienda="",Ingresoeducacion="",Ingresomedicos="",Ingresodeudas="",Ingresopersonal="",Ingresotransporte="",Ingresogastos_varios="",Ingresoentretenimiento="";
    Calendar c = Calendar.getInstance();
    String consultaFecha="",consultaAhorro="",consultaSueldo="",consultaOtrosIngresos="",consultaVivienda="",consultaAlimentacion="",consultaEducacion="",consultaTransporte="",consultaMedicos="",consultaEntretenimiento="",consultapersonal="",consultaDeudas="",consultaGastosVarios="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proyecciones);
        Bundle bundle = getIntent().getExtras();
        CedulaPermanente=bundle.getString("cedula");
        nombrePermanente=bundle.getString("nombre");
        apellidoPermanente=bundle.getString("apellido");
        valiPrimera=bundle.getString("valiPrimera");
        ahorro=(TextView)findViewById(R.id.txtAhorro);
        disponible =(TextView)findViewById(R.id.txtDisponible);
        sueldo=(TextView)findViewById(R.id.txtSueldo);
        otrosIngresos=(TextView)findViewById(R.id.txtOtros);
        vivienda=(TextView)findViewById(R.id.txtIngresoVivienda);
        alimentacion=(TextView)findViewById(R.id.txtIngresoAlimentacion);
        medicina=(TextView)findViewById(R.id.txtIngresoMedicina);
        educacion=(TextView)findViewById(R.id.txtIngresoEducacion);
        personal=(TextView)findViewById(R.id.txtIngresoPersonal);
        transporte=(TextView)findViewById(R.id.txtIngresoTransporte);
        gVarios=(TextView)findViewById(R.id.txtIngresoGastosVarios);
        deudas=(TextView)findViewById(R.id.txtIngresoDeudas);
        entretenimiento=(TextView)findViewById(R.id.txtIngresoEntretenimiento);
        guardar=(Button) findViewById(R.id.btnIngresoGuardar);
        volver=(Button) findViewById(R.id.btnIngresoVolver);
        guardar.setOnClickListener(this);
        volver.setOnClickListener(this);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c.getTime());

        Conexion conn = new Conexion();
        Connection conexionMySQL = conn.conectar();
        try {
            Statement st  = conexionMySQL.createStatement();

            ResultSet rs = st.executeQuery("SELECT ingresos.FECHA,ingresos.AHORRO_MES, ingresos.SUELDOS,ingresos.OTROS_INGRE, gastos_proy.VIVIENDA,gastos_proy.ALIMENTACION ,gastos_proy.EDUCACION,gastos_proy.TRANSPORTE , gastos_proy.MEDICOS,gastos_proy.ENTRETENIMIENTO, gastos_proy.PERSONAL,gastos_proy.DEUDAS ,gastos_proy.GASTOS_VARIOS FROM ingresos INNER JOIN gastos_proy on gastos_proy.ID_INGRESO=ingresos.ID_INGRESO WHERE ingresos.CED="+CedulaPermanente);
            while(rs.next()){
                consultaFecha=rs.getString(1);
                consultaAhorro=rs.getString(2);
                consultaSueldo=rs.getString(3);
                consultaOtrosIngresos=rs.getString(4);
                consultaVivienda=rs.getString(5);
                consultaAlimentacion=rs.getString(6);
                consultaEducacion=rs.getString(7);
                consultaTransporte=rs.getString(8);
                consultaMedicos=rs.getString(9);
                consultaEntretenimiento=rs.getString(10);
                consultapersonal=rs.getString(11);
                consultaDeudas=rs.getString(12);
                consultaGastosVarios=rs.getString(13);
                String[] parts1 = consultaFecha.split("-");
                String[] parts2 = formattedDate.split("-");
                if(parts1[0].equals(parts2[0])){
                    if(parts1[1].equals(parts2[1])){
                        sueldo.setEnabled(false);
                        otrosIngresos.setEnabled(false);
                        vivienda.setEnabled(false);
                        alimentacion.setEnabled(false);
                        medicina.setEnabled(false);
                        educacion.setEnabled(false);
                        personal.setEnabled(false);
                        transporte.setEnabled(false);
                        gVarios.setEnabled(false);
                        deudas.setEnabled(false);
                        entretenimiento.setEnabled(false);
                        guardar.setEnabled(false);
                        sueldo.setText(consultaSueldo+"");
                        otrosIngresos.setText(consultaOtrosIngresos+"");
                        vivienda.setText(consultaVivienda+"");
                        alimentacion.setText(consultaAlimentacion+"");
                        medicina.setText(consultaMedicos+"");
                        educacion.setText(consultaEducacion+"");
                        personal.setText(consultapersonal+"");
                        transporte.setText(consultaTransporte+"");
                        gVarios.setText(consultaGastosVarios+"");
                        deudas.setText(consultaDeudas+"");
                        entretenimiento.setText(consultaEntretenimiento+"");
                    }
                }else{
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            conexionMySQL.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(valiPrimera.equals("1")){
            AlertDialog.Builder alerta = new AlertDialog.Builder(proyecciones.this);
            alerta.setMessage("Bienvenido el primer mes únicamente registre sus ingresos.");
            AlertDialog titulo = alerta.create();
            titulo.setTitle("Alerta");
            titulo.show();
            vivienda.setEnabled(false);
            alimentacion.setEnabled(false);
            medicina.setEnabled(false);
            educacion.setEnabled(false);
            personal.setEnabled(false);
            transporte.setEnabled(false);
            gVarios.setEnabled(false);
            deudas.setEnabled(false);
            entretenimiento.setEnabled(false);
            vivienda.setText(0+"");
            alimentacion.setText(0+"");
            medicina.setText(0+"");
            educacion.setText(0+"");
            personal.setText(0+"");
            transporte.setText(0+"");
            gVarios.setText(0+"");
            deudas.setText(0+"");
            entretenimiento.setText(0+"");
        }


        sueldo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence sequence, int start, int before, int count) {
                sueldoDespues = sequence.subSequence(0, start + count).toString();
                if(sueldoDespues.equals("")){
                    if (otrosIngresos.getText().toString().equals("")) {
                        disponible.setText(0+"");
                    }else{
                        totalEntrada = Float.parseFloat(otrosIngresos.getText().toString());
                        total=totalEntrada-totalSalida;
                        disponible.setText(total+"");
                    }
                } else{
                    if(otrosIngresos.getText().toString().equals("")){
                        totalEntrada = Float.parseFloat(sueldoDespues);
                        total=totalEntrada-totalSalida;
                        disponible.setText(total + "");
                    }else {
                        totalEntrada = Float.parseFloat(sueldoDespues) + Float.parseFloat(otrosIngresos.getText().toString());
                        total=totalEntrada-totalSalida;
                        disponible.setText(total + "");
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        otrosIngresos.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence sequence, int start, int before, int count) {

                sueldoDespues = sequence.subSequence(0, start + count).toString();
                if(sueldoDespues.equals("")){
                    if (sueldo.getText().toString().equals("")) {
                        disponible.setText(0+"");
                    }else{
                        totalEntrada = Float.parseFloat(sueldo.getText().toString());
                        total=totalEntrada-totalSalida;
                        disponible.setText(total+"");
                    }
                } else{
                    if(sueldo.getText().toString().equals("")){
                        totalEntrada = Float.parseFloat(sueldoDespues);
                        total=totalEntrada-totalSalida;
                        disponible.setText(total + "");
                    }else {
                        totalEntrada = Float.parseFloat(sueldoDespues) + Float.parseFloat(sueldo.getText().toString());
                        total=totalEntrada-totalSalida;
                        disponible.setText(total + "");
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        vivienda.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence sequence, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence sequence, int start, int before, int count) {
                despuesvivienda = antesvivienda;
            }
            @Override
            public void afterTextChanged(Editable s) {
                float ant=0;
                float desp=0;
                antesvivienda=s.toString();
                if(antesvivienda.equals("")){
                    ant=0;
                    if(despuesvivienda.equals("")){
                        desp=0;
                    }else{
                        desp=Float.parseFloat(despuesvivienda);
                        totalSalida=totalSalida-desp;
                    }
                }else{
                    ant=Float.parseFloat(antesvivienda);
                    if(despuesvivienda.equals("")){
                        desp=0;
                        totalSalida=totalSalida+ant;
                    }else{
                        desp=Float.parseFloat(despuesvivienda);
                        if(desp<ant){
                            totalSalida=totalSalida+(ant-desp);
                        }else{
                            totalSalida=totalSalida-(desp-ant);
                        }

                    }
                }
                total=totalEntrada-totalSalida;
                disponible.setText(total+"");
            }
        });
        alimentacion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence sequence, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence sequence, int start, int before, int count) {
                despuesalimentacion = antesalimentacion;
            }
            @Override
            public void afterTextChanged(Editable s) {
                float ant=0;
                float desp=0;
                antesalimentacion=s.toString();
                if(antesalimentacion.equals("")){
                    ant=0;
                    if(despuesalimentacion.equals("")){
                        desp=0;
                    }else{
                        desp=Float.parseFloat(despuesalimentacion);
                        totalSalida=totalSalida-desp;
                    }
                }else{
                    ant=Float.parseFloat(antesalimentacion);
                    if(despuesalimentacion.equals("")){
                        desp=0;
                        totalSalida=totalSalida+ant;
                    }else{
                        desp=Float.parseFloat(despuesalimentacion);
                        if(desp<ant){
                            totalSalida=totalSalida+(ant-desp);
                        }else{
                            totalSalida=totalSalida-(desp-ant);
                        }

                    }
                }
                total=totalEntrada-totalSalida;
                disponible.setText(total+"");
            }
        });
        medicina.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence sequence, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence sequence, int start, int before, int count) {
                despuesmedicina = antesmedicina;
            }
            @Override
            public void afterTextChanged(Editable s) {
                float ant=0;
                float desp=0;
                antesmedicina=s.toString();
                if(antesmedicina.equals("")){
                    ant=0;
                    if(despuesmedicina.equals("")){
                        desp=0;
                    }else{
                        desp=Float.parseFloat(despuesmedicina);
                        totalSalida=totalSalida-desp;
                    }
                }else{
                    ant=Float.parseFloat(antesmedicina);
                    if(despuesmedicina.equals("")){
                        desp=0;
                        totalSalida=totalSalida+ant;
                    }else{
                        desp=Float.parseFloat(despuesmedicina);
                        if(desp<ant){
                            totalSalida=totalSalida+(ant-desp);
                        }else{
                            totalSalida=totalSalida-(desp-ant);
                        }

                    }
                }
                total=totalEntrada-totalSalida;
                disponible.setText(total+"");
            }
        });
        educacion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence sequence, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence sequence, int start, int before, int count) {
                despueseducacion = anteseducacion;
            }
            @Override
            public void afterTextChanged(Editable s) {
                float ant=0;
                float desp=0;
                anteseducacion=s.toString();
                if(anteseducacion.equals("")){
                    ant=0;
                    if(despueseducacion.equals("")){
                        desp=0;
                    }else{
                        desp=Float.parseFloat(despueseducacion);
                        totalSalida=totalSalida-desp;
                    }
                }else{
                    ant=Float.parseFloat(anteseducacion);
                    if(despueseducacion.equals("")){
                        desp=0;
                        totalSalida=totalSalida+ant;
                    }else{
                        desp=Float.parseFloat(despueseducacion);
                        if(desp<ant){
                            totalSalida=totalSalida+(ant-desp);
                        }else{
                            totalSalida=totalSalida-(desp-ant);
                        }
                    }
                }
                total=totalEntrada-totalSalida;
                disponible.setText(total+"");
            }
        });
        personal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence sequence, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence sequence, int start, int before, int count) {
                despuespersonal = antespersonal;
            }
            @Override
            public void afterTextChanged(Editable s) {
                float ant=0;
                float desp=0;
                antespersonal=s.toString();
                if(antespersonal.equals("")){
                    ant=0;
                    if(despuespersonal.equals("")){
                        desp=0;
                    }else{
                        desp=Float.parseFloat(despuespersonal);
                        totalSalida=totalSalida-desp;
                    }
                }else{
                    ant=Float.parseFloat(antespersonal);
                    if(despuespersonal.equals("")){
                        desp=0;
                        totalSalida=totalSalida+ant;
                    }else{
                        desp=Float.parseFloat(despuespersonal);
                        if(desp<ant){
                            totalSalida=totalSalida+(ant-desp);
                        }else{
                            totalSalida=totalSalida-(desp-ant);
                        }
                    }
                }
                total=totalEntrada-totalSalida;
                disponible.setText(total+"");
            }
        });
        transporte.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence sequence, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence sequence, int start, int before, int count) {
                despuestransporte = antestransporte;
            }
            @Override
            public void afterTextChanged(Editable s) {
                float ant=0;
                float desp=0;
                antestransporte=s.toString();
                if(antestransporte.equals("")){
                    ant=0;
                    if(despuestransporte.equals("")){
                        desp=0;
                    }else{
                        desp=Float.parseFloat(despuestransporte);
                        totalSalida=totalSalida-desp;
                    }
                }else{
                    ant=Float.parseFloat(antestransporte);
                    if(despuestransporte.equals("")){
                        desp=0;
                        totalSalida=totalSalida+ant;
                    }else{
                        desp=Float.parseFloat(despuestransporte);
                        if(desp<ant){
                            totalSalida=totalSalida+(ant-desp);
                        }else{
                            totalSalida=totalSalida-(desp-ant);
                        }
                    }
                }
                total=totalEntrada-totalSalida;
                disponible.setText(total+"");
            }
        });
        gVarios.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence sequence, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence sequence, int start, int before, int count) {
                despuesgVarios = antesgVarios;
            }
            @Override
            public void afterTextChanged(Editable s) {
                float ant=0;
                float desp=0;
                antesgVarios=s.toString();
                if(antesgVarios.equals("")){
                    ant=0;
                    if(despuesgVarios.equals("")){
                        desp=0;
                    }else{
                        desp=Float.parseFloat(despuesgVarios);
                        totalSalida=totalSalida-desp;
                    }
                }else{
                    ant=Float.parseFloat(antesgVarios);
                    if(despuesgVarios.equals("")){
                        desp=0;
                        totalSalida=totalSalida+ant;
                    }else{
                        desp=Float.parseFloat(despuesgVarios);
                        if(desp<ant){
                            totalSalida=totalSalida+(ant-desp);
                        }else{
                            totalSalida=totalSalida-(desp-ant);
                        }
                    }
                }
                total=totalEntrada-totalSalida;
                disponible.setText(total+"");
            }
        });
        deudas.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence sequence, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence sequence, int start, int before, int count) {
                despuesdeudas = antesdeudas;
            }
            @Override
            public void afterTextChanged(Editable s) {
                float ant=0;
                float desp=0;
                antesdeudas=s.toString();
                if(antesdeudas.equals("")){
                    ant=0;
                    if(despuesdeudas.equals("")){
                        desp=0;
                    }else{
                        desp=Float.parseFloat(despuesdeudas);
                        totalSalida=totalSalida-desp;
                    }
                }else{
                    ant=Float.parseFloat(antesdeudas);
                    if(despuesdeudas.equals("")){
                        desp=0;
                        totalSalida=totalSalida+ant;
                    }else{
                        desp=Float.parseFloat(despuesdeudas);
                        if(desp<ant){
                            totalSalida=totalSalida+(ant-desp);
                        }else{
                            totalSalida=totalSalida-(desp-ant);
                        }
                    }
                }
                total=totalEntrada-totalSalida;
                disponible.setText(total+"");
            }
        });
        entretenimiento.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence sequence, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence sequence, int start, int before, int count) {
                despuesentretenimiento = antesentretenimiento;
            }
            @Override
            public void afterTextChanged(Editable s) {
                float ant=0;
                float desp=0;
                antesentretenimiento=s.toString();
                if(antesentretenimiento.equals("")){
                    ant=0;
                    if(despuesentretenimiento.equals("")){
                        desp=0;
                    }else{
                        desp=Float.parseFloat(despuesentretenimiento);
                        totalSalida=totalSalida-desp;
                    }
                }else{
                    ant=Float.parseFloat(antesentretenimiento);
                    if(despuesentretenimiento.equals("")){
                        desp=0;
                        totalSalida=totalSalida+ant;
                    }else{
                        desp=Float.parseFloat(despuesentretenimiento);
                        if(desp<ant){
                            totalSalida=totalSalida+(ant-desp);
                        }else{
                            totalSalida=totalSalida-(desp-ant);
                        }

                    }
                }
                total=totalEntrada-totalSalida;
                disponible.setText(total+"");
            }
        });
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnIngresoGuardar:
                idIngreso="";
                ahorroMes=0;
                if(totalEntrada<totalSalida){
                    AlertDialog.Builder alerta = new AlertDialog.Builder(proyecciones.this);
                    alerta.setMessage("El valor proyectado excede los ingresos.");
                    AlertDialog titulo = alerta.create();
                    titulo.setTitle("ERROR");
                    titulo.show();
                }else {
                    AlertDialog dialogo = new AlertDialog
                            .Builder(proyecciones.this) // NombreDeTuActividad.this, o getActivity() si es dentro de un fragmento
                            .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    sueldo.setEnabled(false);
                                    otrosIngresos.setEnabled(false);
                                    vivienda.setEnabled(false);
                                    alimentacion.setEnabled(false);
                                    medicina.setEnabled(false);
                                    educacion.setEnabled(false);
                                    personal.setEnabled(false);
                                    transporte.setEnabled(false);
                                    gVarios.setEnabled(false);
                                    deudas.setEnabled(false);
                                    entretenimiento.setEnabled(false);
                                    guardar.setEnabled(false);
                                    SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
                                    String formattedDate1 = df1.format(c.getTime());
                                    Conexion conn = new Conexion();
                                    Connection conexionMySQL = conn.conectar();
                                    try {
                                        ahorroMes=totalEntrada-totalSalida;
                                        ahorroGeneral=ahorroMes+totalSalida;
                                        float oIngresos=0;
                                        float sueldoIngresar=0;
                                        if(otrosIngresos.getText().toString().equals("")){
                                            oIngresos=0;
                                        }else{
                                            oIngresos=Float.parseFloat(otrosIngresos.getText().toString());
                                        }
                                        if(sueldo.getText().toString().equals("")){
                                            sueldoIngresar=0;
                                        }else{
                                            sueldoIngresar=Float.parseFloat(sueldo.getText().toString().toString());
                                        }
                                        String query = "INSERT INTO ingresos (ID_INGRESO,AHORRO_MES,SUELDOS,OTROS_INGRE,FECHA,CED,ID_GASTOS) VALUES (0,'"+ahorroMes+"',"+sueldoIngresar+","+oIngresos+",'"+formattedDate1+"','"+CedulaPermanente+"',NULL)";
                                        Statement st1 = conexionMySQL.createStatement();
                                        st1.executeUpdate(query);
                                        conexionMySQL.close();
                                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                                        String formattedDate = df.format(c.getTime());
                                        conn = new Conexion();
                                        conexionMySQL = conn.conectar();
                                        try {
                                            Statement st  = conexionMySQL.createStatement();
                                            ResultSet rs = st.executeQuery("SELECT ID_INGRESO,FECHA FROM ingresos WHERE CED = "+CedulaPermanente);
                                            while(rs.next()){
                                                consultaFecha=rs.getString(2);
                                                ahorro.setText(consultaFecha+"");
                                                String[] parts1 = consultaFecha.split("-");
                                                String[] parts2 = formattedDate.split("-");
                                                if(parts1[0].equals(parts2[0])){
                                                    if(parts1[1].equals(parts2[1])){
                                                        idIngreso=rs.getString(1);
                                                    }
                                                }else{
                                                }
                                            }
                                            conexionMySQL.close();
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                        conn = new Conexion();
                                        conexionMySQL = conn.conectar();
                                        if(alimentacion.getText().toString().equals("")){
                                            Ingresoalimentacion="0";
                                        }else{
                                            Ingresoalimentacion=alimentacion.getText().toString();
                                        }
                                        if(vivienda.getText().toString().equals("")){
                                            Ingresovivienda="0";
                                        }else{
                                            Ingresovivienda=vivienda.getText().toString();
                                        }
                                        if(educacion.getText().toString().equals("")){
                                            Ingresoeducacion="0";
                                        }else{
                                            Ingresoeducacion=educacion.getText().toString();
                                        }
                                        if(medicina.getText().toString().equals("")){
                                            Ingresomedicos="0";
                                        }else{
                                            Ingresomedicos=medicina.getText().toString();
                                        }
                                        if(deudas.getText().toString().equals("")){
                                            Ingresodeudas="0";
                                        }else{
                                            Ingresodeudas=deudas.getText().toString();
                                        }
                                        if(personal.getText().toString().equals("")){
                                            Ingresopersonal="0";
                                        }else{
                                            Ingresopersonal=personal.getText().toString();
                                        }
                                        if(transporte.getText().toString().equals("")){
                                            Ingresotransporte="0";
                                        }else{
                                            Ingresotransporte=transporte.getText().toString();
                                        }
                                        if(gVarios.getText().toString().equals("")){
                                            Ingresogastos_varios="0";
                                        }else{
                                            Ingresogastos_varios=gVarios.getText().toString();
                                        }
                                        if(entretenimiento.getText().toString().equals("")){
                                            Ingresoentretenimiento="0";
                                        }else{
                                            Ingresoentretenimiento=entretenimiento.getText().toString();
                                        }
                                        String query1 = "INSERT INTO gastos_proy (ID_GASTOS,ID_INGRESO,FECHA,ID_VIVIENDA,ID_ALIMENTACION,ID_EDUCACION,ID_TRANS,ID_PERSONAL,ID_MEDICOS,ID_ENTRETENIMIETO,ID_DEUDAS,ID_GASTOSV,VIVIENDA,ALIMENTACION,EDUCACION,TRANSPORTE,MEDICOS,ENTRETENIMIENTO,PERSONAL,DEUDAS,GASTOS_VARIOS,AHORRO,TOTAL_GASTOSPRO) VALUES (NULL, '"+idIngreso+"', '"+formattedDate1+"', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,NULL, '"+Ingresovivienda+"', '"+Ingresoalimentacion+"', '"+Ingresoeducacion+"', '"+Ingresotransporte+"', '"+Ingresomedicos+"', '"+Ingresoentretenimiento+"', '"+Ingresopersonal+"', '"+Ingresodeudas+"', '"+Ingresogastos_varios+"', '"+ahorroGeneral+"', '"+totalSalida+"')";
                                        Statement st2 = conexionMySQL.createStatement();
                                        st2.executeUpdate(query1);
                                        conexionMySQL.close();

                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                                    try {
                                        conn = new Conexion();
                                        conexionMySQL = conn.conectar();
                                        String query1 = "UPDATE ingresos SET ingresos.ID_GASTOS=(SELECT gastos_proy.ID_GASTOS FROM gastos_proy WHERE gastos_proy.ID_INGRESO="+idIngreso+") WHERE ingresos.ID_INGRESO="+idIngreso;
                                        Statement st2 = conexionMySQL.createStatement();
                                        st2.executeUpdate(query1);
                                        conexionMySQL.close();
                                        AlertDialog.Builder alerta1 = new AlertDialog.Builder(proyecciones.this);
                                        alerta1.setMessage("Proyecciones registradas con éxito");
                                        AlertDialog titulo1 = alerta1.create();
                                        titulo1.setTitle("Confirmación");
                                        titulo1.show();
                                    }catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }
                            })
                            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Hicieron click en el botón negativo, no confirmaron
                                    // Simplemente descartamos el diálogo
                                    dialog.dismiss();
                                }
                            })
                            .setTitle("Confirmar") // El título
                            .setMessage("Los datos no podrán ser modificados, Desea continuar") // El mensaje
                            .create();// No olvides llamar a Create, ¡pues eso crea el AlertDialog!
                    dialogo.show();
                }
                break;
            case R.id.btnIngresoVolver:
                Intent i = new Intent(this, MenuInicial.class);
                i.putExtra("cedula", CedulaPermanente);
                i.putExtra("nombre", nombrePermanente);
                i.putExtra("apellido", apellidoPermanente);
                startActivity(i);
                break;
        }
    }
}
