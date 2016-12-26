package thiago.giovane.chat;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.TimedMetaData;
import android.opengl.Visibility;
import android.support.v4.app.NavUtils;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.TimerTask;


public class Chat extends AppCompatActivity {
    LinearLayout layout;ImageView sendButton;
    EditText messageArea;
    ScrollView scrollView;
    Firebase reference1, reference2, isTyping, Horario;
    private String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        layout = (LinearLayout) findViewById(R.id.layout1);
        sendButton = (ImageView) findViewById(R.id.sendButton);
        messageArea = (EditText) findViewById(R.id.messageArea);
        scrollView = (ScrollView) findViewById(R.id.scrollView);

        //Criando banco
        Firebase.setAndroidContext(this);
        reference1 = new Firebase("https://chat-3f74e.firebaseio.com/messages/" + UserDetails.username + "_" + UserDetails.chatWith);
        reference2 = new Firebase("https://chat-3f74e.firebaseio.com/messages/" + UserDetails.chatWith + "_" + UserDetails.username);
        isTyping = new Firebase("https://chat-3f74e.firebaseio.com/typing" + addListenerOnTextChange.typing);
        Horario = new Firebase("https://chat-3f74e.firebaseio.com/horario" + Hora.horaCompleta);

        String url = "https://chat-3f74e.firebaseio.com/typing.json";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                doOnSuccess(s);
            }//fechao onResponse
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
            }//fecha onErrorResponse
        });

        RequestQueue rQueue = Volley.newRequestQueue(Chat.this);
        rQueue.add(request);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String messageText = messageArea.getText().toString();
                if(!messageText.equals("")){
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("message", messageText);
                    map.put("user", UserDetails.username);
                    reference1.push().setValue(map);
                    reference2.push().setValue(map);
                }//fecha if

                if (messageArea != null){
                    limparTexto();
                }
            }//fecha onClick
        });//fecha OnClickListener

        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(final DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);
                final String message = map.get("message").toString();
                String userName = map.get("user").toString();

                //Inserindo data na caixa de mensagem
                String data = "dd/MM/yyyy"; String hora = "h:mm - a"; String data1, hora1;
                java.util.Date agora = new java.util.Date();
                SimpleDateFormat formata = new SimpleDateFormat(data);
                data1 = formata.format(agora);
                formata = new SimpleDateFormat(hora);
                hora1 = formata.format(agora);

                /*map.put("hora",hora1);
                Horario.push().setValue(map);*/

                if(userName.equals(UserDetails.username)){
                    addMessageBox("You: \n" + message + "\n" +hora1, 1);
                }else{
                    addMessageBox(UserDetails.chatWith + "\n" + message+ "\n" + hora1, 2);
                }//fecha else

                //Conferir se usuário está digitando...

            //TESTE

            //TESTE
            }//fecha onChildAdded
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
            public void onDataChange(DataSnapshot snapshot){

            }
        });//fecha onChildAdded
        messageArea.addTextChangedListener(new addListenerOnTextChange(this, messageArea));
        setTitle(UserDetails.chatWith);

    }//fecha onCreate

   public void doOnSuccess(String s) {
        try {
            JSONObject obj = new JSONObject(s);

            Iterator i = obj.keys();
            String key = "";
            while (i.hasNext()){
                key = i.next().toString();
                Log.d(TAG, "doOnSuccess: user Typandoooo!");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }//fecha doOnSucess

        public void limparTexto(){
            messageArea.getText().clear();
        }

        public void addMessageBox(String message, int type){

        final TextView textView = new TextView(Chat.this);
        textView.setText(message);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(10, 10, 10, 10);
        textView.setLayoutParams(lp);
        if (type == 1) {
            textView.setBackgroundResource(R.drawable.rounded_corner1);
        }else {
            textView.setBackgroundResource(R.drawable.rounded_corner2);
           /* ALETRAÇÂO
            if (type != 2) {

                messageArea.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("typing", addListenerOnTextChange.typing);
                        isTyping.push().setValue(map);
                    }


                    @Override
                    public void afterTextChanged(Editable editable) {
                    }
                });//fecha addTextChangedListener

                isTyping.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            finish();
                            String type;

                        }//fecha for
                    }//fecha onDataChange

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                }); */
            }//fecha else
        //}
        layout.addView(textView);
        scrollView.fullScroll(View.FOCUS_DOWN);
        //Faz a listview das mensagens rolar para baixo automaticamente
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });//fecha post
        
            
    }//fecha addMessageBox
}//fecha Class
