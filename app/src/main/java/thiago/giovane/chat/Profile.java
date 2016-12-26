package thiago.giovane.chat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by giovane on 25/12/16.
 */

public class Profile extends AppCompatActivity{
    //Widgets
    ImageView ivSwitch;
    EditText etProfileUser;
    Button btnPronto, btnCancel;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //Referências
        ivSwitch = (ImageView) findViewById(R.id.iv_switch);
        etProfileUser = (EditText) findViewById(R.id.et_profile_username);
        btnPronto = (Button) findViewById(R.id.btn_pronto);
        btnCancel = (Button) findViewById(R.id.btn_cancel);

        //isSwitch.alguma coisa
        ivSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galeriaIntent = new Intent(Intent.ACTION_VIEW, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivity(galeriaIntent);
            }
        });

        btnPronto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(Profile.this, Users.class);
                startActivity(it);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
                builder.setMessage("Você deseja cancelar?")
                       .setPositiveButton("Sair", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {
                               Intent it = new Intent(Profile.this, Login.class);
                               startActivity(it);
                               finish();
                           }
                       })
                        .setNegativeButton("Continuar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                AlertDialog alerta;
                alerta = builder.create();
                alerta.show();
            }
        });
    }//fecha onCreate
}//fecha class
