package thiago.giovane.chat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Chat extends AppCompatActivity {
    LinearLayout layout;
    ImageView sendButton;
    EditText messageArea;
    ScrollView scrollView;
    Firebase reference1, reference2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        layout = (LinearLayout) findViewById(R.id.layout1);
        sendButton = (ImageView) findViewById(R.id.sendButton);
        messageArea = (EditText) findViewById(R.id.messageArea);
        scrollView = (ScrollView) findViewById(R.id.scrollView);

        Firebase.setAndroidContext(this);
        reference1 = new Firebase("https://chat-3f74e.firebaseio.com/messages/" + UserDetails.username + "_" + UserDetails.chatWith);
        reference2 = new Firebase("https://chat-3f74e.firebaseio.com/messages/" + UserDetails.chatWith + "_" + UserDetails.username);

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
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);
                String message = map.get("message").toString();
                String userName = map.get("user").toString();

                //Inserindo data na caixa de mensagem
                String data = "dd/MM/yyyy";
                String hora = "h:mm - a";
                String data1, hora1;
                java.util.Date agora = new java.util.Date();;
                SimpleDateFormat formata = new SimpleDateFormat(data);
                data1 = formata.format(agora);
                formata = new SimpleDateFormat(hora);
                hora1 = formata.format(agora);
                String esse = hora1;

                if(userName.equals(UserDetails.username)){
                    addMessageBox("You\n" + message + "\n" + esse, 1);
                }else{
                    addMessageBox(UserDetails.chatWith + "\n" + esse, 2);
                }//fecha else

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
        });//fecha onChildAdded

    }//fecha onCreate

    public void limparTexto(){
        messageArea.getText().clear();
    }

    public void addMessageBox(String message, int type){
    final TextView textView = new TextView(Chat.this);
    textView.setText(message);
    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    lp.setMargins(0, 0, 0, 10);
    textView.setLayoutParams(lp);

    if (type == 1) {
        textView.setBackgroundResource(R.drawable.rounded_corner1);
    }else{
        textView.setBackgroundResource(R.drawable.rounded_corner2);
    }//fecha else



    layout.addView(textView);
    scrollView.fullScroll(View.FOCUS_DOWN);
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });

    }//fecha addMessageBox
}//fecha Class
