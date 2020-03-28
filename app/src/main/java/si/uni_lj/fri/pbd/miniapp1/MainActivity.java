package si.uni_lj.fri.pbd.miniapp1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.File;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ContactsFragment.FragmentListener{


    private MessageFragment message;
    private Intent intent;
    private Intent intent2;

    private AppBarConfiguration mAppBarConfiguration;
    private ImageView imageView;
    private File file;
    DrawerLayout drawer;
    private int contact_number = 0;
    private information[] info = new information[10];
    int counter = 0;

    private HomeFragment home;
    private ContactsFragment contacts;
    int check=0;
    int rnum=0;
    int rnum2=0;
    int checkednum[] = new int[100];
    String[] rphone;
    String[] rmail;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            home = new HomeFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    home).commit();
            navigationView.setCheckedItem(R.id.nav_home); //highlight clicked menu
        }
        setNavLogoOnClickListener();

    }

    private void setNavLogoOnClickListener() {
        NavigationView navView = (NavigationView)findViewById(R.id.nav_view);
        View headerView = navView.getHeaderView(0);
        ImageView logo = (ImageView) headerView.findViewById(R.id.imageView);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DrawerLayout) findViewById(R.id.drawer_layout)).closeDrawer(GravityCompat.START); // 레프트메뉴 닫기
                System.out.println("here is imageView");
                sendTakePhotoIntent();
            }
        });
    }

    private void sendTakePhotoIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            //((ImageView)findViewById(R.id.imageView)).setImageBitmap(imageBitmap);
            setImage(imageBitmap);
        }
    }
    private void setImage(Bitmap imageBitmap) {
        NavigationView navView = (NavigationView)findViewById(R.id.nav_view);
        View headerView = navView.getHeaderView(0);
        ImageView logo = (ImageView) headerView.findViewById(R.id.imageView);
        logo.setImageBitmap(imageBitmap);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.nav_home:
                    home=new HomeFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            home).commit();
                break;
            case R.id.nav_contacts:
                    contacts=new ContactsFragment();
                    //intent2 = getIntent();
                    Bundle bundle2=new Bundle();
                    bundle2.putInt("check",check);
                    bundle2.putIntArray("checkednum",checkednum);
                    contacts.setArguments(bundle2);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            contacts).commit();
                break;
            case R.id.nav_message:
                    message=new MessageFragment();
                    //intent = getIntent();
                    Bundle bundle=new Bundle();
                    bundle.putInt("check1", rnum);
                    bundle.putInt("check2", rnum);
                    bundle.putStringArray("rphone",rphone);
                    bundle.putStringArray("rmail",rmail);
                    message.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                             message).commit();

                break;


        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void onFragmentInteraction(String text) {
        System.out.println("here is on FragmentInteraction : "+text);
    }

    @Override
    public void onAttachFragment(Fragment fragment)
    {
        if(fragment instanceof ContactsFragment)
        {
            ContactsFragment contactsFragment = (ContactsFragment)fragment;
            ContactsFragment.setFragmentListener(this);
        }
    }
    @Override
    public void onButtonClick(int check, int num, int num2, int checkednum[], String rphone[], String rmail[]) {
        System.out.println("main : "+num);


        this.rphone = rphone;
        this.rmail = rmail;
        this.rnum = num;
        this.rnum2 = num2;
        this.check = check;
        this.checkednum = checkednum;
    }


static class information implements Parcelable
    {

        public String name;

        public String[] phoneme =new String[10];
        public String[] email=new String[10];
        public int id;

        protected information(Parcel in) {
            name = in.readString();
            phoneme = in.createStringArray();
            email = in.createStringArray();
            id = in.readInt();
        }

        public final Creator<information> CREATOR = new Creator<information>() {
            @Override
            public information createFromParcel(Parcel in) {
                return new information(in);
            }

            @Override
            public information[] newArray(int size) {
                return new information[size];
            }
        };

        public information() {

        }

        String getEmail()
        {
            return email[0];
        }
        String getPhone()
        {
            return phoneme[0];
        }
        String getName()
        {
            return name;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(name);
            parcel.writeInt(id);
            parcel.writeStringArray(phoneme);
            parcel.writeStringArray(email);

        }
    }
}
