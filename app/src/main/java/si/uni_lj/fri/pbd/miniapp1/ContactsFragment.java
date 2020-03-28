package si.uni_lj.fri.pbd.miniapp1;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.widget.Toast;
import java.util.ArrayList;

public class ContactsFragment extends Fragment {

    private static FragmentListener rcallback;
    private Context context;
    private GridListAdapter adapter;
    private ListView listView;
    private ArrayList<String> arrayList;
    private ArrayList<String> arrayList2;
    private ArrayList<String> arrayList3;
    private int contact_number = 0;
    private int rcontact_number = 0;
    private MainActivity.information[] info = new MainActivity.information[100];

    int check=0;
    int checkednum[] = new int[100];


    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
            rcallback = (FragmentListener) activity;
        } catch(ClassCastException e){
            throw new ClassCastException(activity.toString() + "must implement FragmentListener");
        }

    }

    public ContactsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        check = getArguments().getInt("check");
        checkednum = getArguments().getIntArray("checkednum");
        return inflater.inflate(R.layout.fragment_contacts, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GetUserContactsList();
        loadListView(view);
        System.out.println("check : "+check);
        System.out.print("checkednum : ");
        for(int i=0;i<rcontact_number;i++)
        {
            System.out.print(checkednum[i] +" ");
        }
        System.out.println("");


    }

    private void loadListView(View view)
    {
        listView = (ListView) view.findViewById(R.id.listview1);
        arrayList = new ArrayList<>();
        arrayList2 = new ArrayList<>();
        arrayList3 = new ArrayList<>();
        for (int i = 0; i < contact_number; i++)
        {
            if(info[i]!=null) {
                arrayList.add(info[i].getName());
                if(info[i].getPhone()!=null) {
                    arrayList2.add(info[i].getPhone());
                }
                else
                    arrayList2.add("null");
                if(info[i].getEmail()!=null) {
                    arrayList3.add(info[i].getEmail());
                }
                else
                    arrayList3.add("null");
                rcontact_number++;
            }
        }

        adapter = new GridListAdapter(context, arrayList,arrayList2, arrayList3 ,true);
        listView.setAdapter(adapter);
    }



    public class GridListAdapter extends BaseAdapter
    {
        private Context context;
        private ArrayList<String> arrayList;
        private ArrayList<String> arrayList2;
        private ArrayList<String> arrayList3;
        private LayoutInflater inflater;
        private boolean isListView;
        private SparseBooleanArray mSelectedItemsIds;

        public GridListAdapter(Context context, ArrayList<String> arrayList,  ArrayList<String> arrayList2, ArrayList<String> arrayList3,boolean isListView) {
            this.context = context;
            this.arrayList = arrayList;
            this.arrayList2 = arrayList2;
            this.arrayList3 = arrayList3;
            this.isListView = isListView;
            inflater = LayoutInflater.from(context);
            mSelectedItemsIds = new SparseBooleanArray();
        }

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int i) {
            return arrayList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (view == null) {
                viewHolder = new ViewHolder();

                //inflate the layout on basis of boolean
                if (isListView)
                    view = inflater.inflate(R.layout.listview_contacts, viewGroup, false);


                viewHolder.label = (TextView) view.findViewById(R.id.textTitle);
                viewHolder.label2 = (TextView) view.findViewById(R.id.phone_text);
                viewHolder.label3 = (TextView) view.findViewById(R.id.email_text);

                viewHolder.checkBox = (CheckBox) view.findViewById(R.id.check1);
                view.setTag(viewHolder);
            } else
                viewHolder = (ViewHolder) view.getTag();

            viewHolder.label.setText(arrayList.get(i));
            viewHolder.label2.setText(arrayList2.get(i));
            viewHolder.label3.setText(arrayList3.get(i));
            System.out.println(i+" "+checkednum[i]);
            if(checkednum[i]==1) {
                viewHolder.checkBox.setChecked(true);
               // checkednum[i]=0;
            }
            else
            {
                viewHolder.checkBox.setChecked(false);
            }


            viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkCheckBox(i, !mSelectedItemsIds.get(i));
                }
            });
            viewHolder.label.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkCheckBox(i, !mSelectedItemsIds.get(i));
                }
            });
            viewHolder.label2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkCheckBox(i, !mSelectedItemsIds.get(i));
                }
            });
            viewHolder.label3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkCheckBox(i, !mSelectedItemsIds.get(i));
                }
            });

            return view;
        }
        private class ViewHolder {
            private TextView label;
            private TextView label2;
            private TextView label3;
            private CheckBox checkBox;
        }

        public void removeSelection() {
            mSelectedItemsIds = new SparseBooleanArray();
            notifyDataSetChanged();
        }

        public void checkCheckBox(int position, boolean value) {
            if (value)
            {
                mSelectedItemsIds.put(position, true);

                TextView tv2 =(TextView) listView.getChildAt(position).findViewById(R.id.phone_text);
                TextView tv3 =(TextView) listView.getChildAt(position).findViewById(R.id.email_text);
                if (tv2.getText().toString()=="null")
                {
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(),"This contact has\nno registered phone number",Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }
                if(tv3.getText().toString()=="null")
                {
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(),"This contact has\nno registered email address",Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER,3,0);
                    toast.show();
                }
            }
            else
                mSelectedItemsIds.delete(position);

            int check1=0;
            int check2=0;
            check = 0;
            String rphone[] = new String[100];
            String rmail[] = new String[100];


            for (int i = 0; i < rcontact_number; i++)
            {
                try{
                    CheckBox cb = (CheckBox) (listView.getChildAt(i).findViewById(R.id.check1));
                    TextView tv =(TextView) listView.getChildAt(i).findViewById(R.id.textTitle);
                    TextView tv2 =(TextView) listView.getChildAt(i).findViewById(R.id.phone_text);
                    TextView tv3 =(TextView) listView.getChildAt(i).findViewById(R.id.email_text);
                    if (cb.isChecked()) {
                        checkednum[i] = 1;

                        if( tv2.getText().toString()!="null") {
                            rphone[check1++] = tv2.getText().toString();
                        }
                        if( tv3.getText().toString()!="null") {
                            rmail[check2++] = tv3.getText().toString();
                        }

                    }
                    else
                        checkednum[i] = 0;
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            rcallback.onButtonClick(rcontact_number,check1,check2,checkednum,rphone,rmail);

            notifyDataSetChanged();
        }

        public SparseBooleanArray getSelectedIds() {
            return mSelectedItemsIds;
        }

    }

    public void GetUserContactsList()
    {
        String[] arrProjection = {ContactsContract.Contacts._ID,ContactsContract.Contacts.DISPLAY_NAME};
        String[] arrPhoneProjection = {ContactsContract.CommonDataKinds.Phone.NUMBER};
        String[] arrEmailProjection = {ContactsContract.CommonDataKinds.Email.DATA};

        Cursor clsCursor;
        clsCursor = context.getContentResolver().query
                (ContactsContract.Contacts.CONTENT_URI,arrProjection,null,null,null);

        while(clsCursor.moveToNext())
        {

            String strContactId = clsCursor.getString(0);
            //Log.d("Unity","user ID : "+clsCursor.getString(0));

            contact_number = Integer.parseInt(clsCursor.getString(0));
            System.out.println(contact_number-1);
            info[contact_number-1] = new MainActivity.information();
            info[contact_number-1].name = clsCursor.getString(1);
            //Log.d("Unity","user Name : "+clsCursor.getString(1));

            Cursor clsPhoneCursor = context.getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,arrPhoneProjection   ,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"="+strContactId,null,null);
            int phonenum=0;
            while(clsPhoneCursor.moveToNext())
            {
                info[contact_number-1].phoneme[phonenum++] = clsPhoneCursor.getString(0);
                //Log.d("Unity","User Phone Number : "+clsPhoneCursor.getString(0));
            }
            clsPhoneCursor.close();
            int emailnum=0;
            Cursor clsEmailCursor = context.getContentResolver().query(
                    ContactsContract.CommonDataKinds.Email.CONTENT_URI,arrEmailProjection,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"="+strContactId,null,null);
            while(clsEmailCursor.moveToNext())
            {
                info[contact_number-1].email[emailnum++] = clsEmailCursor.getString(0);
                //Log.d("Unity","User Email : "+clsEmailCursor.getString(0));
            }
            clsEmailCursor.close();
        }

    }
    public static void setFragmentListener(FragmentListener callback)
    {
        rcallback = callback;
    }
    public interface FragmentListener{

        public void onButtonClick(int check, int num, int num2,int checkednum[],String rphone[], String rmail[]);

    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}