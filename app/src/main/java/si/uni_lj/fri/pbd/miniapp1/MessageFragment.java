package si.uni_lj.fri.pbd.miniapp1;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import si.uni_lj.fri.pbd.miniapp1.R;

public class MessageFragment extends Fragment {

    private Button messagebutton1;
    private Button emailbutton1;
    String[] rphone;
    String[] rmail;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_message, container, false);
        messagebutton1 = root.findViewById(R.id.message_button);
        messagebutton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {

                int num = getArguments().getInt("check1");
                if(num!=0) {
                    StringBuilder sb = new StringBuilder("");

                    rphone = getArguments().getStringArray("rphone");
                    for(int i=0;i<num;i++)
                    {
                        sb = sb.append(rphone[i]+";");
                    }
                    System.out.println(sb);
                    Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + sb));
                    intent.putExtra("sms_body", "SMS TEXT");
                    startActivity(intent);
                }else
                {
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(),"no clicked member",Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }
            }

        });

        emailbutton1 = root.findViewById(R.id.mail_button);
        emailbutton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {

                int num = getArguments().getInt("check2");
                if (num!=0) {
                    rmail = getArguments().getStringArray("rmail");
                    Intent email = new Intent(Intent.ACTION_SEND);
                    email.setType("plain/text");

                    email.putExtra(Intent.EXTRA_EMAIL, rmail);

                    startActivity(email);
                }
                else
                {
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(),"no clicked member",Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }

            }

        });

        return root;
    }
}
