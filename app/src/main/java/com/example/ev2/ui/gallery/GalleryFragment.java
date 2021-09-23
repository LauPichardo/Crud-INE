package com.example.ev2.ui.gallery;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.ev2.MapsActivity;
import com.example.ev2.R;
import com.example.ev2.SQL.SQLite;
import com.example.ev2.ui.gallery.GalleryViewModel;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;

    private int dia, mes,ano;
    private int diadenacimiento, mesdenacimiento,anodenacimiento;



    //Variables globales
    Spinner nversion, estado, municipio, seccion, entidadnacimimiento;
    EditText nombre, ap, am, calle, numext, numint, colonia, cp, fechanacimiento, claveelectoral,  anoregistro, anoemision, vigencia,claveadicional;
    TextView curp,localidad;
    String a,b,sex,imagen="",currentPhotoPath;
    Button btnGuardar, btnLimpiar, btnFecha, btnMapa;
    ImageView iVFoto;
    Uri photoURI;
    RadioGroup sexo;
    RadioButton masculino, femenino;
    public SQLite sqlite;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        ap = root.findViewById(R.id.altaApellidoPaterno);
        am = root.findViewById(R.id.altaApellidoMaterno);
        nombre= root.findViewById(R.id.altaNombre);
        calle = root.findViewById(R.id.altaCalle);
        numext = root.findViewById(R.id.altaNumExt);
        numint = root.findViewById(R.id.altaNumInt);
        colonia = root.findViewById(R.id.altaColonia);
        cp = root.findViewById(R.id.altaCP);
        fechanacimiento = root.findViewById(R.id.fechaIngresoCrear);
        claveelectoral= root.findViewById(R.id.altaClaveElectoral);
        curp = root.findViewById(R.id.altacurp);
        anoregistro = root.findViewById(R.id.altaanoregistro);
        nversion = root.findViewById(R.id.spnumerodeversion);
        estado= root.findViewById(R.id.spEstado);
        municipio= root.findViewById(R.id.spMunicipio);
        seccion = root.findViewById(R.id.spSeccion);
        entidadnacimimiento = root.findViewById(R.id.spentidadnacimiento);
        localidad = root.findViewById(R.id.txtLocalidad);
        anoemision = root.findViewById(R.id.altaanioemision);
        vigencia = root.findViewById(R.id.altavigencia);
        btnGuardar =root.findViewById(R.id.btn_crear_guardar);
        btnLimpiar =root.findViewById(R.id.btn_crear_limpiar);
        btnFecha = root.findViewById(R.id.btnDateCrear);
        iVFoto = root.findViewById(R.id.ivFotoCrear);
        sexo= root.findViewById(R.id.altaopciones_sexo);
        masculino= root.findViewById(R.id.radio_masculino);
        femenino= root.findViewById(R.id.radio_femenino);
        claveadicional = root.findViewById(R.id.altaClaveAdicionalCurp);
        btnMapa = root.findViewById(R.id.btn_altamapa);

        sqlite = new SQLite(getContext());
        sqlite.abrir();

        // se le pone al spinner Estado
        Spinners();




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

                    if (sqlite.addregistroPaciente(nombre.getText().toString(), ap.getText().toString(), am.getText().toString(),
                            calle.getText().toString(), Integer.parseInt(numext.getText().toString()), Integer.parseInt(numint.getText().toString()), colonia.getText().toString(), Integer.parseInt(cp.getText().toString()),
                            fechanacimiento.getText().toString(), sexoescogido(sexo),claveelectoral.getText().toString(),curp.getText().toString(),
                            Integer.parseInt(anoregistro.getText().toString()),obtenernumerodeversion(nversion),estado.getSelectedItem().toString(),municipio.getSelectedItem().toString(),Integer.parseInt(seccion.getSelectedItem().toString()),
                            localidad.getText().toString(),Integer.parseInt(anoemision.getText().toString()),Integer.parseInt(vigencia.getText().toString()),imagen))
                    {
                        Toast.makeText(getContext(), "Se dio de alta registro ",Toast.LENGTH_LONG).show();
                    }
                }else
                {
                    Toast.makeText(getContext(),
                            "Error: no puede haber campos vacios",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        anoemision.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==4)
                {
                    int edad= 2020-anodenacimiento;
                    int aniotope= anodenacimiento+18;
                    if(Integer.parseInt(anoemision.getText().toString())<aniotope)
                    {
                        anoemision.setText("");
                        vigencia.setText("");
                        Toast.makeText(getContext(),"No se puede tener IFE con edad menor de 18 años", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        vigencia.setText(String.valueOf(Integer.parseInt(anoemision.getText().toString())+10));

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        claveadicional.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() ==2)
                {
                    claveadicional.setEnabled(false);
                    String c = crearCURP(ap.getText().toString(),am.getText().toString(),nombre.getText().toString(),diadenacimiento,mesdenacimiento,anodenacimiento,entidadnacimimiento,sexo);
                    curp.setText(c);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MapsActivity.class);
                startActivity(intent);
            }
        });

        return root;
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
        if (sexo.getCheckedRadioButtonId() == R.id.radio_masculino){
            sex="H";
        }else if(sexo.getCheckedRadioButtonId() == R.id.radio_femenino)
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

    public String municipio(Spinner municipio){
        String mun = "";
        String opcion= String.valueOf(municipio.getSelectedItemId());
        int op= Integer.parseInt(opcion);
        switch (op)
        {
            case 1:
                mun = "Metepec";
                break;
            case 2:
                mun = "San Mateo Atenco";
                break;
            case 3:
                mun = "Tultepec";
                break;
            case 4:
                mun = "Ocoyoacac";
                break;
            case 5:
                mun = "Tianguistenco";
                break;
        }

        return mun;

    }

}
