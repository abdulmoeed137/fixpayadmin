package admin.money.fixshix.com.fixshixmoneyadmin;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lenovo on 8/27/2017.
 */

public class SessionManager {
    private String id, name , email , contact, amount;
    SharedPreferences session ;
    public SessionManager(){}
    public SessionManager(Context c)
    {
        session= c.getSharedPreferences(Constants.SESSION,Context.MODE_PRIVATE);
        this. id = session.getString("id",null);
        this.email= session.getString("email",null);
        this.name = session.getString("name",null);
        this.contact = session.getString("contact",null);
    }


    public SessionManager(Context c , String id, String name , String email, String contact)
        {
            session= c.getSharedPreferences(Constants.SESSION,Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = session.edit();
            editor.putString("id", id);
            editor.putString("name",name);
            editor.putString("email",email);
            editor.putString("contact",contact);
            editor.commit();

            new SessionManager(c);

        }


    public void Logout (){
        SharedPreferences.Editor editor = session.edit();
        editor.clear();
        editor.commit();

        this.id=null;
        this.name=null;
        this.email=null;
        this.contact=null;

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getContact() {
        return contact;
    }
    public boolean CheckIfSessionExist(){
        if (session.contains("id"))
            return true;
        else
            return  false;

    }
}
