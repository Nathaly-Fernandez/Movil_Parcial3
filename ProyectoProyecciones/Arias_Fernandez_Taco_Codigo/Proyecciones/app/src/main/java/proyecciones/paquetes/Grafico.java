package proyecciones.paquetes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Grafico extends AppCompatActivity {
    BarChart graficoBarras;
    Spinner opciones1, opciones2;
    TextView proyAlimentacion,proyVivienda,proyTransporte,proyEducacion,proyEntrete,proyMedic,proyGastoVar,proyDeuda,proyPersona,totalIngresos;
    TextView gastAlimentacion,gastVivienda,gastTransporte,gastEducacion,gastEntrete,gastMedic,gastyGastoVar,gastDeuda,gastPersona,ahorroInfor;
    String MesConsulta="09",anioConsulta="2020";
    Button consultarInf,volverinf;
    String CedulaPermanente="",nombrePermanente,apellidoPermanente;
    String consultaFecha="";
    String id_gastos="";
    float var1,var2,var3,var4,var5,var6,var7,var8,var9;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafico);
        graficoBarras = findViewById(R.id.graficaBarras);
        Bundle bundle = getIntent().getExtras();
        CedulaPermanente=bundle.getString("cedula");
        nombrePermanente=bundle.getString("nombre");
        apellidoPermanente=bundle.getString("apellido");

        List<BarEntry> entradas=new ArrayList<>();



        float total=0;
        float otrosIngresos=0;
        Conexion conn = new Conexion();
        Connection conexionMySQL = conn.conectar();
        try {
            Statement st0  = conexionMySQL.createStatement();
            ResultSet rs0 = st0.executeQuery("SELECT DISTINCT ingresos.ID_INGRESO , ingresos.FECHA , gastos_proy.ID_GASTOS,gastos_proy.VIVIENDA,gastos_proy.ALIMENTACION,gastos_proy.EDUCACION, gastos_proy.TRANSPORTE,gastos_proy.MEDICOS,gastos_proy.ENTRETENIMIENTO,gastos_proy.PERSONAL,gastos_proy.DEUDAS,gastos_proy.GASTOS_VARIOS FROM ingresos INNER JOIN gastos_proy on gastos_proy.ID_INGRESO=ingresos.ID_INGRESO WHERE ingresos.CED="+CedulaPermanente);
            while(rs0.next()){
                consultaFecha=rs0.getString(2);
                String[] parts1 = consultaFecha.split("-");
                if("2020".equals(anioConsulta)){
                    if("08".equals(MesConsulta)){
                        id_gastos=rs0.getString(3);
                        if(rs0.getString(5).equals("")){
                            proyAlimentacion.setText(0+"");
                        }else{
                            proyAlimentacion.setText(rs0.getString(5)+"");
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
            AlertDialog.Builder alerta = new AlertDialog.Builder(Grafico.this);
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
                        var1=0;
                    } else {
                        var1=Float.parseFloat(rs.getString(1));
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
                        var2=0;
                    } else {
                        var2=Float.parseFloat(rs.getString(1));
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
                        var3=0;
                    } else {
                        var3=Float.parseFloat(rs.getString(1));
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
                        var4=0;
                    } else {
                        var4=Float.parseFloat(rs.getString(1));
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
                        var5=0;
                    } else {
                        var5=Float.parseFloat(rs.getString(1));
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
                        var6=0;
                    } else {
                        var6=Float.parseFloat(rs.getString(1));
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
                        var7=0;
                    } else {
                        var7=Float.parseFloat(rs.getString(1));
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
                        var8=0;
                    } else {
                        var8=Float.parseFloat(rs.getString(1));
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
                        var9=0;
                    } else {
                        var9=Float.parseFloat(rs.getString(1));
                    }
                }
                conexionMySQL.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }









        entradas.add(new BarEntry(0f,var1));
        entradas.add(new BarEntry(1f,var2));
        entradas.add(new BarEntry(2f,var3));
        entradas.add(new BarEntry(3f,var4));
        entradas.add(new BarEntry(4f,var5));
        entradas.add(new BarEntry(5f,var6));
        entradas.add(new BarEntry(6f,var7));
        entradas.add(new BarEntry(7f,var8));
        entradas.add(new BarEntry(7f,var9));

        BarDataSet datos =  new BarDataSet(entradas, "Grafico de barras");

        BarData data = new BarData(datos);

        datos.setColors(ColorTemplate.COLORFUL_COLORS);

        data.setBarWidth(0.9f);

        graficoBarras.setData(data);

        graficoBarras.setFitBars(true);

        graficoBarras.invalidate();

    }
}
