package admin.money.fixshix.com.fixshixmoneyadmin.model;

/**
 * Created by lenovo on 9/18/2017.
 */

public class ScheduleModel {
    private  String id,amount, status , time , method, user_id;
    public  ScheduleModel(String id,String amount, String status, String time, String method,String user_id)
    {
        this.id = id; this.amount = amount; this.status = status;this.time=time;this.method=method;this.user_id= user_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
