package thiago.giovane.chat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.client.Firebase;

import org.json.JSONException;
import org.json.JSONObject;

public class Register extends AppCompatActivity {
    //Widgets
    EditText username, password, passwordConfirm;
    Button registerButton;
    String user, pass, passConfirm;
    TextView login;
    CheckBox checkBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //referências
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        registerButton = (Button) findViewById(R.id.registerButton);
        login = (TextView) findViewById(R.id.login);
        checkBox = (CheckBox) findViewById(R.id.ch_password_reg);
        passwordConfirm = (EditText) findViewById(R.id.password_confirm);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean selected) {
                if (!selected){
                    password.setTransformationMethod(new PasswordTransformationMethod());
                    passwordConfirm.setTransformationMethod(new PasswordTransformationMethod());
                }else{
                    password.setTransformationMethod(null);
                    passwordConfirm.setTransformationMethod(null);
                }
            }
        });

        Firebase.setAndroidContext(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this, Login.class));
            }//fecha onClick
        });//fecha OnClickListener

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = username.getText().toString();
                pass = password.getText().toString();
                passConfirm = passwordConfirm.getText().toString();
                if (user.equals("")){
                    username.setError("Preencha este campo");
                }else if(pass.equals("")){
                    password.setError("Preencha este campo");
                }else if(!user.matches("[A-Za-z0-9]+")){
                    username.setError("Somente letras ou numeros permitidos");
                }else if(user.length()<3){
                    username.setError("No mínimo 3 digitos");
                }else if(pass.length()<6 && pass.length()>=20){
                    password.setError("No mínimo 6 digitos");
                }else if(!(pass.equals(passConfirm))){
                    passwordConfirm.setError("Senhas não correspondem");
                }
                else{
                    final ProgressDialog pd = new ProgressDialog(Register.this);
                    pd.setMessage("Loading...");
                    pd.show();
                    String url = "https://chat-3f74e.firebaseio.com/users.json";

                    StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){

                        @Override
                        public void onResponse(String s) {
                                  Firebase reference = new Firebase("https://chat-3f74e.firebaseio.com/users");

                            if(s.equals("null")) {
                                reference.child(user).child("password").setValue(pass);
                                Toast.makeText(Register.this, "Registrado com sucesso", Toast.LENGTH_LONG).show();
                            }else{
                                try {
                                    JSONObject obj = new JSONObject(s);

                                    if (!obj.has(user)) {
                                        reference.child(user).child("password").setValue(pass);
                                        Toast.makeText(Register.this, "registrado com sucesso", Toast.LENGTH_LONG).show();
                                    }else{
                                        Toast.makeText(Register.this, "Username já existente", Toast.LENGTH_LONG).show();
                                    }//fecha else
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }//fecha catch
                            }//fecha else
                            pd.dismiss();
                        }//fecha onResponse
                    },new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            System.out.println("" + volleyError);
                            pd.dismiss();
                        }//fecha onErrorResponse
                    });//fecha Response.ErrosListener
                    RequestQueue rQueue = Volley.newRequestQueue(Register.this);
                    rQueue.add(request);
                }
            }
        });
    }//fecha onCreate
}//fecha classe
