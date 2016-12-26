package thiago.giovane.chat;

import android.content.Context;
import android.nfc.Tag;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;

/**
 * Created by Alunos on 22/12/2016.
 */

public class addListenerOnTextChange implements TextWatcher{
    private Context mContext;
    EditText mEditText;
    static String typing;

    public addListenerOnTextChange(Context context, EditText editText){
        super();
        this.mContext = context;
        this.mEditText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Log.d(TAG, "onTextChanged: Usuário Digitando");
        typing = new String();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
