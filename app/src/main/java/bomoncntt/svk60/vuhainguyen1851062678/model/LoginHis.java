package bomoncntt.svk60.vuhainguyen1851062678.model;

import android.util.Log;



public class LoginHis {
    private String username;
    private String status;

    public LoginHis(String username, String status) {
        this.username = username;
        this.status = status;
    }
    public LoginHis(String str) {
        try{
            String arr[]=str.split(",");
            this.username = arr[0];
            this.status = arr[2];

        }catch (Exception e){
            e.printStackTrace();
            Log.d("errorstring", str);
        }
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return this.username+','+this.status+";\n";
    }
}
