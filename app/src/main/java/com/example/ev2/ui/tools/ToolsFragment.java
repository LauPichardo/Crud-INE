package com.example.ev2.ui.tools;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.ev2.R;
import com.example.ev2.SQL.SQLite;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class ToolsFragment extends Fragment {

    private ToolsViewModel toolsViewModel;
    private int dia, mes,ano;
    private int diadenacimiento, mesdenacimiento,anodenacimiento;



    //Variables globales
    Spinner nversion, estado, municipio, seccion, entidadnacimimiento;
    EditText nombre, ap, am, calle, numext, numint, colonia, cp, fechanacimiento, claveelectoral,  anoregistro, anoemision, vigencia,claveadicional;
    TextView curp,localidad;
    String a,b,sex,imagen="",currentPhotoPath;
    Button btnGuardar, btnLimpiar, btnFecha;
    ImageView iVFoto;
    Uri photoURI;
    RadioGroup sexo;
    RadioButton masculino, femenino;
    public SQLite sqlite;

    //Consulta
    Button btnconsulta;
    EditText consulta;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of(this).get(ToolsViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_tools, container, false);

        toolsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
    root.findViewById(R.id.linerarmodificar).setVisibility(View.INVISIBLE);
        ap = root.findViewById(R.id.modiApellidoPaterno);
        am = root.findViewById(R.id.modiApellidoMaterno);
        nombre= root.findViewById(R.id.modiNombre);
        calle = root.findViewById(R.id.modiCalle);
        numext = root.findViewById(R.id.modiNumExt);
        numint = root.findViewById(R.id.modiNumInt);
        colonia = root.findViewById(R.id.modiColonia);
        cp = root.findViewById(R.id.modiCP);
        fechanacimiento = root.findViewById(R.id.fechaIngresoModi);
        claveelectoral= root.findViewById(R.id.modiClaveElectoral);
        curp = root.findViewById(R.id.modicurp);
        anoregistro = root.findViewById(R.id.modianoregistro);
        nversion = root.findViewById(R.id.spnumerodeversion_modi);
        estado= root.findViewById(R.id.spEstado_modi);
        municipio= root.findViewById(R.id.spMunicipio_modi);
        seccion = root.findViewById(R.id.spSeccion_modi);
        entidadnacimimiento = root.findViewById(R.id.spentidadnacimiento_modi);
        localidad = root.findViewById(R.id.txtLocalidad_modi);
        anoemision = root.findViewById(R.id.modianioemision);
        vigencia = root.findViewById(R.id.modivigencia);
        btnGuardar =root.findViewById(R.id.btn_modi_guardar);
        btnLimpiar =root.findViewById(R.id.btn_modi_limpiar);
        btnFecha = root.findViewById(R.id.btnDateModi);
        iVFoto = root.findViewById(R.id.ivFotoModi);
        sexo= root.findViewById(R.id.modiopciones_sexo);
        masculino= root.findViewById(R.id.radio_masculino_modi);
        femenino= root.findViewById(R.id.radio_femenino_modi);
        claveadicional=root.findViewById(R.id.modiClaveAdicionalCurp);

        consulta=root.findViewById(R.id.editext_consultacurp);
        btnconsulta=root.findViewById(R.id.btn_consultacurp);
        btnconsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c=sqlite.getCant(consulta.getText().toString());
                root.findViewById(R.id.linerarmodificar).setVisibility(View.VISIBLE);
                if(c.moveToFirst())
                {

                    c.moveToFirst();
                    nombre.setText(c.getString(0).toString());
                    ap.setText(c.getString(1).toString());
                    am.setText(c.getString(2).toString());
                    calle.setText(c.getString(3).toString());
                    numext.setText(c.getString(4).toString());
                    numint.setText(c.getString(5).toString());
                    colonia.setText(c.getString(6).toString());
                    cp.setText(c.getString(7).toString());
                    fechanacimiento.setText(c.getString(8).toString());

                    claveelectoral.setText(c.getString(10).toString());

                    curp.setText(c.getString(11).toString());
                    claveadicional.setText(getclaveadicional(c.getString(11).toString()));
                    anoregistro.setText(c.getString(12).toString());
                    localidad.setText(c.getString(17).toString());
                    anoemision.setText(c.getString(18).toString());
                    vigencia.setText(c.getString(19).toString());
                    imagen=c.getString(20);
                    cargarImagen(imagen,iVFoto);
                    String sex,vers,state,muni,secc;
                    sex=c.getString(9);
                    vers=c.getString(13);
                    state=c.getString(14);
                    muni=c.getString(15).toString();
                    secc=String.valueOf(c.getInt(16)).toString();

                        int pos = buscarPosicion(R.array.numerodeversion, vers);
                        if (pos != -1) {
                            nversion.setSelection(pos);
                        }
                        pos = buscarPosicion(R.array.estados, state);
                        if (pos != -1) {
                            estado.setSelection(pos);
                        }
                        pos = buscarPosicion(R.array.municipios, muni);

                        if (pos != -1) {
                            municipio.setSelection(pos);
                        }
                        pos = buscarPosicion(R.array.seccion, secc);
                        if (pos != -1) {
                            seccion.setSelection(pos);
                        }


                }else
                {
                    Toast.makeText(getContext(),"Error"+c.getCount(),Toast.LENGTH_SHORT).show();
                }
            }
        });

        sqlite = new SQLite(getContext());
        sqlite.abrir();

        // se le pone al spinner Estado
        Spinners();



        //buscar elemento en arreglo



        //Tomar fotografia
        iVFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File photoFile = null;
                try {
                    photoFile = createImageFile(photoFile);
                } catch (IOException e) {
                    Toast.makeText(getContext(), "Ocurrio un error mientras se generaba el archivo", Toast.LENGTH_SHORT).show();
                }

                if (photoFile != null)
                {
                    photoURI = FileProvider.getUriForFile(getContext(), "com.example.ev2",photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, 0);
                }
            }
        });



        // datepicker

        btnFecha.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                final Calendar c= Calendar.getInstance();
                dia=c.get(Calendar.DAY_OF_MONTH);
                mes=c.get(Calendar.MONTH);
                ano = c.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear , int dayOfMonth) {
                        //     sFecha=Integer.toString(2019-year);
                        fechanacimiento.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                        diadenacimiento=dayOfMonth;
                        mesdenacimiento= monthOfYear+1;
                        anodenacimiento=year;
                    }
                },ano,mes,dia);
                datePickerDialog.show();

            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ap.getText().toString().equals("") && !am.getText().toString().equals("") && !nombre.getText().toString().equals("") &&
                        !calle.getText().toString().equals("") && !numext.getText().toString().equals("") && !colonia.getText().toString().equals("") && !cp.getText().toString().equals("") &&
                        !fechanacimiento.getText().toString().equals("") && !claveelectoral.getText().toString().equals("") && !anoregistro.getText().toString().equals("") &&
                        nversion.getSelectedItemId()!=0 && estado.getSelectedItemId()!=0 && municipio.getSelectedItemId() !=0 && seccion.getSelectedItemId()!=0 &&
                        !anoemision.getText().toString().equals("") && !vigencia.getText().toString().equals("") && !imagen.equals(""))
                {




                        String c = crearCURP(ap.getText().toString(), am.getText().toString(), nombre.getText().toString(), getDia(fechanacimiento.getText().toString()),getMes(fechanacimiento.getText().toString()),getAño(fechanacimiento.getText().toString()), entidadnacimimiento, sexo);

                        curp.setText(c);
                    try {

                            int cant=sqlite.updatePaciente(nombre.getText().toString(), ap.getText().toString(), am.getText().toString(),
                                    calle.getText().toString(), Integer.parseInt(numext.getText().toString()), Integer.parseInt(numint.getText().toString()), colonia.getText().toString(), Integer.parseInt(cp.getText().toString()),
                                    fechanacimiento.getText().toString(), sexoescogido(sexo), claveelectoral.getText().toString(), c, consulta.getText().toString(),
                                    Integer.parseInt(anoregistro.getText().toString()), obtenernumerodeversion(nversion), estado.getSelectedItem().toString(), municipio.getSelectedItem().toString(), Integer.parseInt(seccion.getSelectedItem().toString()),
                                    localidad.getText().toString(), Integer.parseInt(anoemision.getText().toString()), Integer.parseInt(vigencia.getText().toString()), imagen);
                        if ( cant== 1) {
                            Toast.makeText(getContext(), "Se actualizo info con CURP: " + c, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getContext(), "No se modifico" + c+"   "+cant, Toast.LENGTH_LONG).show();

                        }
                    }catch (Exception ex)
                    {
                        Toast.makeText(getContext(),ex.getMessage().toString(),Toast.LENGTH_SHORT).show();
                    }


                }else
                {
                    Toast.makeText(getContext(),
                            "Error: no puede haber campos vacios",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });




        return root;
    }
    public int buscarPosicion( int arreglo, String elemento){
        int posicion=-1;
        elemento = elemento.toUpperCase();
        String tem;
        String elementos[]=getResources().getStringArray(arreglo);
        for (int i=0; i<elementos.length;i++){
            tem=elementos[i].toUpperCase();
            if(tem.equals(elemento)){
                posicion=i;
                break;
            }
        }
        return posicion;
    }

    //Crear archivo de imagen
    private File createImageFile(File f) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        f = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = f.getAbsolutePath();
        return f;
    }

    //Obtener foto y mostrarla en el imageView

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        iVFoto.setImageURI(photoURI);
        imagen = currentPhotoPath;
        Toast.makeText(getContext(), "Foto guardada en " + imagen, Toast.LENGTH_SHORT).show();


    }
    //cargar imagen
    public void cargarImagen(String imagen, ImageView iv){
        try{
            File filePhoto=new File(imagen);
            Uri uriPhoto = FileProvider.getUriForFile(getContext(),"com.example.ev2",filePhoto);
            iv.setImageURI(uriPhoto);
        }catch (Exception ex){
            Toast.makeText(getContext(), "Ocurrio un error al cargar la imagen", Toast.LENGTH_SHORT).show();
            Log.d("Cargar Imagen","Error al cargar imagen: "+imagen+"\nMensaje: "+ex.getMessage()+"\nCausa: "+ex.getCause());
        }
    }

    public String crearCURP(String ap, String am, String nombre, int dian,int mesn, int anio, Spinner entid, RadioGroup sexo){
        String curp="";

        char primeraletradelprimerapellido= ap.charAt(0);
        char primeravocaldelprimerapelido=' ';
        char[] apaterno= ap.toCharArray();
        for (int i= 0; i<apaterno.length-1; i++)
        {
            if (String.valueOf(apaterno[i]).equals("a") || String.valueOf(apaterno[i]).equals("A") || String.valueOf(apaterno[i]).equals("e") || String.valueOf(apaterno[i]).equals("E") || String.valueOf(apaterno[i]).equals("i") || String.valueOf(apaterno[i]).equals("I") || String.valueOf(apaterno[i]).equals("o") || String.valueOf(apaterno[i]).equals("O") || String.valueOf(apaterno[i]).equals("u") || String.valueOf(apaterno[i]).equals("U"))
            {
                primeravocaldelprimerapelido = apaterno[i];
                break;
            }
        }
        char primerletradelsegundoapellido= am.charAt(0);
        String inicialnombre= inicialnombre(nombre);
        String nacimiento= sacarnacimiento(anio,mesn,dian);
        String sexoc=sexoescogido(sexo);
        String entdenaci= entidaddenacimiento(entid);
        String consonanteapellido1 = consonanteinternaap(ap);
        String consonanteapellido2 = consonanteinternaap(am);
        String consonantenombre = consonanteinternaap(nombre);
        String primerdigito = String.valueOf(generateRandomNumbers());
        String segundodigito = String.valueOf(generateRandomNumbers());


        curp = String.valueOf(primeraletradelprimerapellido).toUpperCase()
                +String.valueOf(primeravocaldelprimerapelido).toUpperCase()
                +String.valueOf(primerletradelsegundoapellido).toUpperCase()
                +inicialnombre.toUpperCase()
                +nacimiento
                +sexoc.toUpperCase()
                +entdenaci.toUpperCase()
                +consonanteapellido1.toUpperCase()
                +consonanteapellido2.toUpperCase()
                +consonantenombre.toUpperCase()
                +claveadicional.getText().toString();

        return curp;
    }

    public String entidaddenacimiento(Spinner entidad){
        String ent = "";
        String opcion= String.valueOf(entidad.getSelectedItemId());
        int op= Integer.parseInt(opcion);
        switch (op)
        {
            case 0:
                ent ="AS";
                break;
            case 1:
                ent = "BC";
                break;
            case 2:
                ent = "BS";
                break;
            case 3:
                ent = "CC";
                break;
            case 4:
                ent = "CL";
                break;
            case 5:
                ent = "CM";
                break;
            case 6:
                ent = "CS";
                break;
            case 7:
                ent = "CH";
                break;
            case 8:
                ent = "DF";
                break;
            case 9:
                ent = "DG";
                break;
            case 10:
                ent = "GT";
                break;
            case 11:
                ent = "GR";
                break;
            case 12:
                ent = "HG";
                break;
            case 13:
                ent = "JC";
                break;
            case 14:
                ent = "MC";
                break;
            case 15:
                ent = "MN";
                break;
            case 16:
                ent = "MS";
                break;
            case 17:
                ent = "NT";
                break;
            case 18:
                ent = "NL";
                break;
            case 19:
                ent = "OC";
                break;
            case 20:
                ent = "PL";
                break;
            case 21:
                ent = "QT";
                break;
            case 22:
                ent = "QR";
                break;
            case 23:
                ent = "SP";
                break;
            case 24:
                ent = "SL";
                break;
            case 25:
                ent = "SR";
                break;
            case 26:
                ent = "TC";
                break;
            case 27:
                ent = "TS";
                break;
            case 28:
                ent = "TL";
                break;
            case 29:
                ent = "VZ";
                break;
            case 30:
                ent = "YN";
                break;
            case 31:
                ent = "ZS";
                break;
            case 32:
                ent = "SM";
                break;
            case 33:
                ent = "NE";
                break;
        }

        return ent;

    }

    public void Spinners(){
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(),R.array.estados,
                android.R.layout.simple_spinner_item
        );

        estado.setAdapter(adapter);
        estado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // se le pondra al spinner municipio
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String opcion = String.valueOf(estado.getSelectedItemId());
                int op = Integer.parseInt(opcion);
                if (op == 0){ //Seleccion estado
                    //no
                    final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                            getContext(),R.array.municipios,
                            android.R.layout.simple_spinner_item
                    );
                    municipio.setAdapter(adapter);
                }else if(op==1) //seleccion Estado de Mexico
                {
                    final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                            getContext(),R.array.municipiosEdoMex,
                            android.R.layout.simple_spinner_item
                    );
                    municipio.setAdapter(adapter);
                    municipio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        //se le pondra seccion segun municipio elegido
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String opcion = String.valueOf(municipio.getSelectedItemId());
                            int op = Integer.parseInt(opcion);
                            switch (op)
                            {
                                case 0: //vacio
                                    final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                                            getContext(),R.array.seccion,
                                            android.R.layout.simple_spinner_item
                                    );
                                    seccion.setAdapter(adapter);
                                    break;
                                case 1: //seccion para metepec
                                    final ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
                                            getContext(), R.array.seccionMetepec,
                                            android.R.layout.simple_spinner_item
                                    );
                                    seccion.setAdapter(adapter1);
                                    seccion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            String opcion= String.valueOf(seccion.getSelectedItemId());
                                            int op= Integer.parseInt(opcion);
                                            switch (op)
                                            {
                                                case 0:
                                                    localidad.setText("Debe seleccionar una sección");
                                                    break;
                                                case 1:
                                                    localidad.setText("Barrio Santiaguito");
                                                    break;
                                                case 2:
                                                    localidad.setText("Barrio La Asunción");
                                                    break;
                                                case 3:
                                                    localidad.setText("Las Minas");
                                                    break;
                                                case 4:
                                                    localidad.setText("Alvaro Obregon");
                                                    break;
                                                case 5:
                                                    localidad.setText("San Francisco Coaxusco");
                                                    break;
                                            }
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }
                                    });

                                    break;
                                case 2: //seccion para sanmateoatenco
                                    final ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
                                            getContext(), R.array.seccionSanMateo,
                                            android.R.layout.simple_spinner_item
                                    );
                                    seccion.setAdapter(adapter2);
                                    seccion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            String opcion= String.valueOf(seccion.getSelectedItemId());
                                            int op= Integer.parseInt(opcion);
                                            switch (op)
                                            {
                                                case 0:
                                                    localidad.setText("Debe seleccionar una sección");
                                                    break;
                                                case 1:
                                                    localidad.setText("La Concepción");
                                                    break;
                                                case 2:
                                                    localidad.setText("Santa María La Asunción");
                                                    break;
                                                case 3:
                                                    localidad.setText("San Pedro");
                                                    break;
                                                case 4:
                                                    localidad.setText("San Isidro");
                                                    break;
                                                case 5:
                                                    localidad.setText("Santa Elena");
                                                    break;
                                            }
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }
                                    });
                                    break;
                                case 3: //seccion para tultepec
                                    final ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(
                                            getContext(), R.array.seccionTultepec,
                                            android.R.layout.simple_spinner_item
                                    );
                                    seccion.setAdapter(adapter3);
                                    seccion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            String opcion= String.valueOf(seccion.getSelectedItemId());
                                            int op= Integer.parseInt(opcion);
                                            switch (op)
                                            {
                                                case 0:
                                                    localidad.setText("Debe seleccionar una sección");
                                                    break;
                                                case 1:
                                                    localidad.setText("Rancho Cuquio");
                                                    break;
                                                case 2:
                                                    localidad.setText("San Antonio Xahuento");
                                                    break;
                                                case 3:
                                                    localidad.setText("Rancho San Joaquín");
                                                    break;
                                                case 4:
                                                    localidad.setText("Rancho La Virgen");
                                                    break;
                                                case 5:
                                                    localidad.setText("Las Golondrinas");
                                                    break;
                                            }
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }
                                    });
                                    break;
                                case 4: //seccion para ocoyoacac
                                    final ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(
                                            getContext(), R.array.seccionOcoyoacac,
                                            android.R.layout.simple_spinner_item
                                    );
                                    seccion.setAdapter(adapter4);
                                    seccion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            String opcion= String.valueOf(seccion.getSelectedItemId());
                                            int op= Integer.parseInt(opcion);
                                            switch (op)
                                            {
                                                case 0:
                                                    localidad.setText("Debe seleccionar una sección");
                                                    break;
                                                case 1:
                                                    localidad.setText("Colonia La Joya");
                                                    break;
                                                case 2:
                                                    localidad.setText("Guadalupe Victoria");
                                                    break;
                                                case 3:
                                                    localidad.setText("San Isidro Tehualtepec");
                                                    break;
                                                case 4:
                                                    localidad.setText("Colonia Juarez");
                                                    break;
                                                case 5:
                                                    localidad.setText("Valle del Silencio");
                                                    break;
                                            }
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }
                                    });
                                    break;
                                case 5: //seccion para tianguistenco
                                    final ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(
                                            getContext(), R.array.seccionTianguistenco,
                                            android.R.layout.simple_spinner_item
                                    );
                                    seccion.setAdapter(adapter5);
                                    seccion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            String opcion= String.valueOf(seccion.getSelectedItemId());
                                            int op= Integer.parseInt(opcion);
                                            switch (op)
                                            {
                                                case 0:
                                                    localidad.setText("Debe seleccionar una sección");
                                                    break;
                                                case 1:
                                                    localidad.setText("Tlacomulco");
                                                    break;
                                                case 2:
                                                    localidad.setText("Ahuatenco");
                                                    break;
                                                case 3:
                                                    localidad.setText("Metztitla");
                                                    break;
                                                case 4:
                                                    localidad.setText("El Buen Suceso");
                                                    break;
                                                case 5:
                                                    localidad.setText("Colonia San Miguel");
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public String inicialnombre(String nombre)
    {
        String inicialnombr="";
        String primernombre,segundonombre;
        String[] nombres= nombre.split(" ");
        if (nombres.length > 1) //tiene 2 nombres
        {
            primernombre=nombres[0];
            segundonombre=nombres[1];
            if(primernombre.equals("Maria") || segundonombre.equals("Jose"))
            {
                inicialnombr = String.valueOf(segundonombre.charAt(0));
            }else
            {
                inicialnombr = String.valueOf(primernombre.charAt(0));
            }
        }else //tiene un nombre
        {
            primernombre = nombres[0];
            inicialnombr = String.valueOf(primernombre.charAt(0));
        }
        return  inicialnombr;
    }

    public String sexoescogido(RadioGroup sexo){
        String sex="";
        if (sexo.getCheckedRadioButtonId() == R.id.radio_masculino_modi){
            sex="H";
        }else if(sexo.getCheckedRadioButtonId() == R.id.radio_femenino_modi)
        {
            sex= "M";
        }
        return  sex;
    }

    public int generateRandomNumbers() {
        Random r = new Random();
        return r.nextInt((9 - 0) + 1) + 0;
    }

    public String sacarnacimiento(int anio, int mes, int dia)
    {
        String fechacurp = "";
        Toast.makeText(getContext(),anio+"/"+mes+"/"+dia,Toast.LENGTH_SHORT).show();
        char uno= String.valueOf(anio).charAt(2);
        char dos = String.valueOf(anio).charAt(3);
        String month="",day ="";
        if(mes <=10)
        {
            month = String.valueOf(0) + String.valueOf(mes);
        }else
        {
            month = String.valueOf(mes);
        }
        if(dia <=10)
        {
            day = String.valueOf(0) + String.valueOf(dia);
        }else
        {
            day = String.valueOf(dia);
        }
        fechacurp = String.valueOf(uno)+ String.valueOf(dos)+ month+day;

        return fechacurp;
    }

    public String consonanteinternaap(String n)
    {
        String letra ="";
        for (int i= 1; i<n.length(); i++)
        {
            String l = String.valueOf(n.charAt(i));
            if(!l.equals("a") && !l.equals("A") &&
                    !l.equals("e") && !l.equals("E") &&
                    !l.equals("i") && !l.equals("I") &&
                    !l.equals("o") && !l.equals("O") &&
                    !l.equals("u") && !l.equals("U")
            )
            {
                letra = l;
                break;
            }
        }
        return letra;
    }

    public int obtenernumerodeversion(Spinner nversion)
    {
        int version =0;

        int op = (int) nversion.getSelectedItemId();
        switch (op)
        {
            case 1:
                version = 1;
                break;
            case 2:
                version = 2;
                break;
            case 3:
                version = 3;
                break;
        }
        return version;
    }
    public String getclaveadicional(String curp)
    {
        int inicio=curp.length()-2;
        String clave="";
        for(int i=inicio;i<curp.length();i++)
        {
            clave=clave+curp.charAt(i);
        }
        return clave;
    }
    public int getDia(String fecha)
    {
        String dia="";
        int i=0;
        while(fecha.charAt(i)!='/'){
            dia=dia+fecha.charAt(i);
            i++;
    }
        return Integer.parseInt(dia);
    }
    public int getMes(String fecha)
    {
        String mes="";
        int i=0;
        while(fecha.charAt(i)!='/'&&i<fecha.length()){
            i++;

        }
        i++;
        while (fecha.charAt(i)!='/'&&i<fecha.length())
        {
            mes=mes+fecha.charAt(i);
            i++;
        }
        return Integer.parseInt(mes);
    }
    public int getAño(String fecha)
    {
        String año="";
       for(int i=fecha.length()-4;i<fecha.length();i++)
       {
           año=año+fecha.charAt(i);

       }
       return Integer.parseInt(año);
    }

}