import android.content.Context;
import android.widget.Toast;

public class Toautils {



    public static  void showMsg(Context context,String msg){

        if (null!=msg)
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();


    }

}
