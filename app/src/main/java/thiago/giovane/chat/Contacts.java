package thiago.giovane.chat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class Contacts extends AppCompatActivity {
    //Widgets
    ListView contactsList;
    TextView noContacts;
    ArrayList<String> contact = new ArrayList<>();
    int totalContacts = 0;
    private ArrayAdapter adapter;
    ProgressDialog pd;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        //Referências
        contactsList = (ListView) findViewById(R.id.contactsList);
        noContacts = (TextView) findViewById(R.id.ct_noContacts);

        pd = new ProgressDialog(Contacts.this);
        pd.setMessage("Loading...");
        pd.show();

        String url = "https://chat-3f74e.firebaseio.com/users.json/";

        /*O objetivo desta classe é fazer uma busca no banco de dados para adicionar um usuário específico que já
        * esteja cadastrado no sistema. A intenção é uma lista de contatos */

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

        RequestQueue rQueue = Volley.newRequestQueue(Contacts.this);
        rQueue.add(request);

        contactsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserDetails.chatWith = contact.get(position);
                startActivity(new Intent(Contacts.this, Chat.class));
            }//fecha onItemClick
        });//fecha OnItemClick
    }//fecha onCreate

    public void doOnSuccess(String s) {
        try {
            JSONObject obj = new JSONObject(s);

            Iterator i = obj.keys();
            String key = "";

            while (i.hasNext()){
                key = i.next().toString();

                if (!key.equals(UserDetails.username)) {
                    contact.add(key);
                }
                totalContacts++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (totalContacts <=1){
            noContacts.setVisibility(View.VISIBLE);
            contactsList.setVisibility(View.GONE);
        }else{
            noContacts.setVisibility(View.GONE);
            contactsList.setVisibility(View.VISIBLE);
            contactsList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contact));
        }//fecha else
        pd.dismiss();
    }//fecha doOnSucess
}//fecha classe
