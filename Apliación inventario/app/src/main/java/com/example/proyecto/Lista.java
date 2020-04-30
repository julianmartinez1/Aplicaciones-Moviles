package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class Lista extends AppCompatActivity {

    ListView lista;
    Button blista;
    String docu, miurl="";
    String[] elementos;
    String variable1,variable2,variable3,variable4,variable5,variable6;

    JSONArray ja = null;
    int contador=0;
    String IP = "10.0.2.2";
    public static final String sitio = "webservice";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);


        //Poner el icono

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher1);

       lista=(ListView) findViewById(R.id.LISTA);


        init2();
    }
    public void init2(){
        int c=2;
        miurl="http://"+IP+"/"+sitio+"/"+"consultar_perfil4.php?&co="+c;
        new Lista.ConsultarDatos().execute(miurl);
    }
    public void mostrar() {
        ArrayAdapter<String> adapter1 = new ArrayAdapter(Lista.this,android.R.layout.simple_list_item_1, elementos);
        lista.setAdapter(adapter1);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {//Al hacer click en cualquiera de los elementos de la lista
            }
        });
    }private class ConsultarDatos extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            // params comes from the execute() call: params[0] is the url.
            try {
                //String cadena=urls[0];
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            try {
                ja = new JSONArray(result);
                elementos=new String[ja.length()];

                //id.setText(ja.getString(0));// Aqui imprime todo el arreglo asociativo
                if(ja.length()!=0) {
                    for (int i = 0; i < ja.length(); i++) {
                        org.json.JSONObject obj = ja.getJSONObject(i);
                        System.out.println("HSDFKJSFAHASFKJASFKJ");

                        variable1 = obj.getString("codigo");
                        variable2 = obj.getString("nombre");
                        variable3 = obj.getString("apellido");
                        variable4 = obj.getString("area");
                        variable5 = obj.getString("marca_equi");
                        variable6 = obj.getString("serial_equi");
                        System.out.println(variable1);

                        elementos[i] = "Codigo: "+variable1+"\n "+
                                       "Nombre: "+ variable2+"\n "+
                                        "Apellido: "+variable3+"\n "+
                                        "Área: "+variable4+"\n "+
                                        "Marca Equipo: "+variable5+"\n "+
                                        "Serial: "+variable6+"\n ";

                    }
                    mostrar();
                }
                else{
                    Toast.makeText(getApplicationContext(), "No se ha creado ningún asignatura",
                            Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private String downloadUrl(String myurl) throws IOException {
        Log.i("URL", "" + myurl);
        myurl = myurl.replace(" ", "%20");
        InputStream is = null;try {
            java.net.URL url = new java.net.URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d("respuesta", "The response is: " + response);
            is = conn.getInputStream();
            // Convert the InputStream into a string
            String contentAsString = readIt(is);
            return contentAsString;
            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    public String readIt(InputStream stream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
        StringBuilder buffer = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            buffer.append(inputLine);
        }
        return buffer.toString();
    }

}




