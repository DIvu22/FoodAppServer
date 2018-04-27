package serverside.example.com.serverside;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import serverside.example.com.serverside.Common.Common;
import serverside.example.com.serverside.Model.User;

public class SignIn extends AppCompatActivity {
    MaterialEditText edtPhone,edtPswrd;
    Button sign_In;

    FirebaseDatabase db;
    DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edtPhone=(MaterialEditText) findViewById(R.id.editPhone);
        edtPswrd=(MaterialEditText) findViewById(R.id.password);
        sign_In=(Button)findViewById(R.id.btn_SignIn);

        db=FirebaseDatabase.getInstance();
        users=db.getReference("User");

        sign_In.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInUser(edtPhone.getText().toString(),edtPswrd.getText().toString());
                
            }
        });


    }

    private void signInUser(String Phone, String Password) {

      final ProgressDialog mDialog = new ProgressDialog(SignIn.this);
      mDialog.setMessage("Loading.......");
      mDialog.show();
      final  String localPhone=Phone;
      final String localPassword=Password;

      users.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
              if(dataSnapshot.child(localPhone).exists())
              {
               mDialog.dismiss();
               User user= dataSnapshot.child(localPhone).getValue(User.class);
               user.setPhone(localPhone);
               if (Boolean.parseBoolean(user.getIsStaff()))
               {
                   if(user.getPassword().equals(localPassword))
                   {
                       Intent login=new Intent(SignIn.this,Home.class);
                       Common.currentUser = user;
                       startActivity(login);
                       finish();
                   }
                   else
                       Toast.makeText(SignIn.this,"Wrong Password",Toast.LENGTH_SHORT).show();


               }

               else
                   Toast.makeText(SignIn.this,"Login with staff account",Toast.LENGTH_SHORT).show();

              }
              else
              {
                  mDialog.dismiss();
                  Toast.makeText(SignIn.this,"User doesnot exists",Toast.LENGTH_SHORT).show();
              }

          }

          @Override
          public void onCancelled(DatabaseError databaseError) {

          }

      });


    }
}
