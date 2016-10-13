package com.keepaccpos.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.media.TransportMediator;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.keepaccpos.R;
import com.keepaccpos.helpers.Ascii;
import com.keepaccpos.helpers.KeepAccHelper;
import com.keepaccpos.helpers.SessionManager;
import com.keepaccpos.network.FactoryApi;
import com.keepaccpos.network.PacketPost;
import com.keepaccpos.network.data.DataPost;
import com.keepaccpos.network.model.Packet;
import com.keepaccpos.network.model.TableData;

import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final char ESC_CHAR = '\u001b';
    public static final byte[] FONT_SIZES;
    private static final char GS = '\u001d';
    private static final byte[] LINE_FEED;
    private static final byte[] SELECT_BIT_IMAGE_MODE;
    private static final byte[] SET_LINE_SPACE_24;
    private static final byte[] SET_LINE_SPACE_30;

    static {
        LINE_FEED = new byte[]{(byte) 10};
        SELECT_BIT_IMAGE_MODE = new byte[]{Ascii.ESC, (byte) 42, (byte) 33};
        SET_LINE_SPACE_24 = new byte[]{Ascii.ESC, (byte) 51, Ascii.CAN};
        SET_LINE_SPACE_30 = new byte[]{Ascii.ESC, (byte) 51, Ascii.RS};
        FONT_SIZES = new byte[]{(byte) 0, Ascii.XON, (byte) 34, (byte) 51, (byte) 68, (byte) 85, (byte) 102, (byte) 119};
    }
    private SessionManager mSessionManager;
    private static String TAG = "LoginActivity";
    private Button mBtnLogin;
    private Button mBtnRegistered;

    //  private Button mBtnHelp;
   // private CheckBox mCBoxRemember;
  //  private EditText mTextPassword;
    private EditText mTextLogin;
private BitSet dots;
    private int mWidth;
    private int mHeight;
            private String mStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mSessionManager = new SessionManager(this);
        if(mSessionManager.isLoggedIn()&&mSessionManager.isRemember())
        {
            startActivity(new Intent(LoginActivity.this,PinCodeActivity.class ));
            finish();
            return;
        }
        mBtnRegistered = (Button)findViewById(R.id.activity_login_registered_btn);
        mBtnLogin = (Button)findViewById(R.id.activity_login_sign_in_btn);
     //   mTextPassword = (EditText)findViewById(R.id.activity_login_password);
        mTextLogin = (EditText)findViewById(R.id.activity_login_email);
        //    mBtnHelp = (Button)findViewById(R.id.activity_login_help_btn);
     //   mCBoxRemember = (CheckBox)findViewById(R.id.activity_login_remember_checkBox);
        mBtnRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisteredLink();
            }
        });
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = mTextLogin.getText().toString();
              //  String password = mTextPassword.getText().toString();
                String password = login;
                if(login.isEmpty())
                {
                    Toast.makeText(LoginActivity.this, R.string.msg_input_login,Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.isEmpty())
                {
                    Toast.makeText(LoginActivity.this, R.string.msg_input_password,Toast.LENGTH_SHORT).show();
                    return;
                }

           /*    WifiP2pManager manager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
             WifiP2pManager.Channel channel = manager.initialize(LoginActivity.this, getMainLooper(), null);

                manager.requestPeers(channel, new WifiP2pManager.PeerListListener() {
                    @Override
                    public void onPeersAvailable(WifiP2pDeviceList wifiP2pDeviceList) {
                        Log.d(TAG, "size = "+  wifiP2pDeviceList.getDeviceList().size());
                        for (WifiP2pDevice device : wifiP2pDeviceList.getDeviceList())
                        {
                            Log.d(TAG, "IP = "+ device.deviceAddress);
                            Log.d(TAG, "NAME = "+ device.deviceName);
                            Log.d(TAG, "TYPE_PRIMARY = "+ device.primaryDeviceType);
                            Log.d(TAG, "TYPE_SECONDARY = "+ device.secondaryDeviceType);
                            Log.d(TAG, "------------");
                            // device.deviceName
                        }
                    }
                });*/
              /*  new AsyncTask<String, String, String>() {
                    @Override
                    protected String doInBackground(String... params) {
                        String myIpAdd= getIPAddress(true);
                        ArrayList<InetAddress>  inetAddresses=getConnectedDevices(myIpAdd);
                        for(InetAddress inetAddress : inetAddresses)
                        {
                            Log.d(TAG, "toString = "+ inetAddress.toString());
                            Log.d(TAG, "IP = "+ inetAddress.getHostAddress());
                            Log.d(TAG, "NAME = "+ inetAddress.getHostName());
                            Log.d(TAG, "CAN_NAME = "+ inetAddress.getCanonicalHostName());

                        }

                        return "true";
                    }
                    }.execute();*/

        /*        Intent printIntent = new Intent(LoginActivity.this, PrintDialogActivity.class);
                Uri uri = Uri.parse(PrintDialogActivity.DOCUMENT_PRINT);
                printIntent.setDataAndType(uri, "application/pdf");
                printIntent.putExtra("title", "Doc title");
                startActivity(printIntent);

*/
//create a file to write bitmap data
                View view =  getLayoutInflater().inflate(R.layout.fragment_check, null);
                final Bitmap mBitmap= getViewAsBitmap();
           /*     File f = new File(getCacheDir(), "print.txt ");
                try {
                    f.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/

//Convert bitmap to byte array
           /*     Bitmap bitmap = mBitmap;
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 0 *//*ignored for PNG*//*, bos);
                byte[] bitmapdata = bos.toByteArray();*/

//write the bytes in file
            /*    FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(f);
                    fos.write(bitmapdata);
                    fos.flush();
                    fos.close();
                } catch (IOException exp) {
                    exp.printStackTrace();
                }


                byte[] mbyteArray =getBitmapAsByteArray(mBitmap);*/

     /*           new AsyncTask<Bitmap, Void, Void>() {


                    @Override
                    protected Void doInBackground(Bitmap... params) {
                        try
                        {
                            if(params == null &&  params[0] == null)
                            {
                                Log.e(TAG,"params = null");
                                return null;
                            }

                            Bitmap bmp = params[0];
                            Socket sock = new Socket("192.168.0.101", 9100);*/

/*    String str = '\n'+"HI,test from Android Device"
                                    + '\n'+"This is first check by Arnold" + '\n'+" Hello world"
                                    +" Hello world"+" Hello world"+" Hello world"+" Hello world"+'\n'+'\n'+'\n';*/
/*
                            DataOutputStream writer = new DataOutputStream(sock.getOutputStream());
                          //  OutputStream outStream = getContentResolver().openOutputStream(uri);

                         printImage(bmp,writer) ;*/
                         //   File fl = new File("print.txt");
                        //    fl.mkdirs();
                         //   if (fl.exists()) {
                           //     Bitmap bmp = BitmapFactory.decodeFile("print.txt");
                         /*   byte[] mbyteArray =getBitmapAsByteArray(mBitmap);
                           for(int i =0; i<mbyteArray.length; i++)
                           {
                               writer.write(PrinterCommands.SELECT_BIT_IMAGE_MODE);
                               writer.write(mbyteArray[i]);
                           }*/
                          //  writer.write(mbyteArray);
                           /*
                            convertBitmap(bmp);

                              writer.write(PrinterCommands.SET_LINE_SPACING_24);

                                int offset = 0;
                                while (offset < bmp.getHeight()) {
                                    writer.write(PrinterCommands.SELECT_BIT_IMAGE_MODE);
                                    for (int x = 0; x < bmp.getWidth(); ++x) {

                                        for (int k = 0; k < 3; ++k) {

                                            byte slice = 0;
                                            for (int b = 0; b < 8; ++b) {
                                                int y = (((offset / 8) + k) * 8) + b;
                                                int i = (y * bmp.getWidth()) + x;
                                                boolean v = false;
                                                if (i < dots.length()) {
                                                    v = dots.get(i);
                                                }
                                                slice |= (byte) ((v ? 1 : 0) << (7 - b));
                                            }
                                            writer.write(slice);
                                        }
                                    }
                                    offset += 24;
                                    writer.write(PrinterCommands.FEED_LINE);
                                    writer.write(PrinterCommands.FEED_LINE);
                                    writer.write(PrinterCommands.FEED_LINE);
                                    writer.write(PrinterCommands.FEED_LINE);
                                    writer.write(PrinterCommands.FEED_LINE);
                                    writer.write(PrinterCommands.FEED_LINE);
                                }
                                writer.write(PrinterCommands.SET_LINE_SPACING_30);
                                writer.flush();*/

                             /*   writer.write(toBytes("1B,64,05,1D,56,01"));
                                writer.flush();
                                sock.close();
*/
                          //  } else {
                              //  Log.e(TAG,"File not found")
                         //   }



                      //      mBitmap.compress(Bitmap.CompressFormat.PNG, 50, writer);
                     //      outStream.flush();
                      //      outStream.close();
                          //  writer.write(str.getBytes("US-ASCII"));
                         //   writer.write(bytes);

/*
                        } catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                        return null;
                    }
                }.execute(mBitmap);*/



              //  startActivity(new Intent(LoginActivity.this,PinCodeActivity.class ));

          login(login,password);

            }
        });
    }
    public  void openRegisteredLink()
    {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(KeepAccHelper.LOGIN_LINK));
        startActivity(browserIntent);
    }

    public void printImage(Bitmap bmp,DataOutputStream writter) {
        if (bmp != null) {
            int[][] pixels = (int[][]) Array.newInstance(Integer.TYPE, new int[]{bmp.getHeight(), bmp.getWidth()});
            for (int h = 0; h < bmp.getHeight(); h++) {
                for (int w = 0; w < bmp.getWidth(); w++) {
                    pixels[h][w] = bmp.getPixel(w, h);
                }
            }
            printImage(pixels, writter);
        }
    }

    private void printImage(int[][] pixels, DataOutputStream writter) {
        try {
            writter.write(SET_LINE_SPACE_24);
          //  writter.flush();
            for (int y = 0; y < pixels.length; y += 24) {
                writter.write(SELECT_BIT_IMAGE_MODE);
                writter.write(new byte[]{(byte) (pixels[y].length & MotionEventCompat.ACTION_MASK), (byte) ((MotionEventCompat.ACTION_POINTER_INDEX_MASK & pixels[y].length) >> 8)});
                for (int x = 0; x < pixels[y].length; x++) {
                    writter.write(recollectSlice(y, x, pixels));
                }
                writter.write(LINE_FEED);
            }
            writter.write(SET_LINE_SPACE_30);
            writter.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private byte[] recollectSlice(int y, int x, int[][] img) {
        byte[] slices = new byte[]{(byte) 0, (byte) 0, (byte) 0};
        int yy = y;
        int i = 0;
        while (yy < y + 24 && i < 3) {
            byte slice = (byte) 0;
            for (int b = 0; b < 8; b++) {
                int yyy = yy + b;
                if (yyy < img.length) {
                    slice = (byte) (((byte) ((shouldPrintColor(img[yyy][x]) ? 1 : 0) << (7 - b))) | slice);
                }
            }
            slices[i] = slice;
            yy += 8;
            i++;
        }
        return slices;
    }

    private boolean shouldPrintColor(int col) {
        if (((col >> 24) & MotionEventCompat.ACTION_MASK) != MotionEventCompat.ACTION_MASK) {
            return false;
        }
        if (((int) (((0.299d * ((double) ((col >> 16) & MotionEventCompat.ACTION_MASK))) + (0.587d * ((double) ((col >> 8) & MotionEventCompat.ACTION_MASK)))) + (0.114d * ((double) (col & MotionEventCompat.ACTION_MASK))))) < TransportMediator.KEYCODE_MEDIA_PAUSE) {
            return true;
        }
        return false;
    }


    private void convertArgbToGrayscale(Bitmap bmpOriginal, int width,
                                        int height) {
        int pixel;
        int k = 0;
        int B = 0, G = 0, R = 0;
        dots = new BitSet();
        try {

            for (int x = 0; x < height; x++) {
                for (int y = 0; y < width; y++) {
                    // get one pixel color
                    pixel = bmpOriginal.getPixel(y, x);

                    // retrieve color of all channels
                    R = Color.red(pixel);
                    G = Color.green(pixel);
                    B = Color.blue(pixel);
                    // take conversion up to one single value by calculating
                    // pixel intensity.
                    R = G = B = (int) (0.299 * R + 0.587 * G + 0.114 * B);
                    // set bit into bitset, by calculating the pixel's luma
                    if (R < 55) {
                        dots.set(k);//this is the bitset that i'm printing
                    }
                    k++;

                }


            }


        } catch (Exception e) {
            // TODO: handle exception
            Log.e(TAG, e.toString());
        }
    }
    public String convertBitmap(Bitmap inputBitmap) {

        mWidth = inputBitmap.getWidth();
        mHeight = inputBitmap.getHeight();

        convertArgbToGrayscale(inputBitmap, mWidth, mHeight);
        mStatus = "ok";
        return mStatus;

    }
    public Bitmap getViewAsBitmap(/*View view*/) {

    //   view.setDrawingCacheEnabled(true);
     //   view.layout(0, 0, 100, 100);
    //   view.buildDrawingCache();
    //   Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ivan);
   //     view.setDrawingCacheEnabled(false);
        return bitmap;
    }
    public static byte[] toBytes(String s) {
        if (s == null || s.trim().equals(StringUtils.EMPTY)) {
            return new byte[0];
        }
        String[] parts = s.split(",");
        byte[] res = new byte[parts.length];
        int i = 0;
        while (i < parts.length) {
            try {
                res[i] = (byte) Integer.parseInt(parts[i], 16);
                i++;
            } catch (Exception e) {
                e.printStackTrace();
                return res;
            }
        }
        return res;
    }
    public byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }
    public static Bitmap getByteArrayAsBitmap(byte[] bitmap) {
        Bitmap bm = null;
        try {
            bm = BitmapFactory.decodeStream(new ByteArrayInputStream(bitmap));
        } catch (Exception e) {
        }
        return bm;
    }
    private void login(final String login, final String password)
    {
        HashMap<String,String> tableDataMap = new HashMap<>();
        tableDataMap.put("cId","");
        tableDataMap.put("email",login);
        tableDataMap.put("password",password);
        Packet packet = PacketPost.getPacket(this,PacketPost.PacketType.Login,new TableData(tableDataMap));

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.msg_authorization));
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<DataPost> call = FactoryApi.getInstance(LoginActivity.this).packetPost(packet);
        call.enqueue(new Callback<DataPost>() {
            @Override
            public void onResponse(Call<DataPost> call, Response<DataPost> response) {
                progressDialog.dismiss();
            DataPost dataPost =  response.body();
                Log.d(TAG,dataPost.getResult());
                if(dataPost.getResult().equals(KeepAccHelper.RESULT_SUCCESS))
                {

                    mSessionManager.addDashboardKey();

                    if(mSessionManager.getDashboardKey() != null)
                    {
                     /*   if(mCBoxRemember.isChecked())
                        {
                            mSessionManager.setRemember(true);
                        }
                        else
                        {
                            mSessionManager.setRemember(false);
                        }*/
                        mSessionManager.setLoggedIn(true);
                        mSessionManager.setLogin(login);
                        mSessionManager.setPassword(password);
                        startActivity(new Intent(LoginActivity.this,PinCodeActivity.class ));
                        finish();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Dashboard key is null",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(LoginActivity.this,dataPost.getTitle() + " : "+ dataPost.getDescription(),Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<DataPost> call, Throwable t) {

                Toast.makeText(LoginActivity.this, KeepAccHelper.RESULT_FAIL + " : "+t.getMessage(),Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
    public ArrayList<InetAddress> getConnectedDevices(String YourPhoneIPAddress) {
        ArrayList<InetAddress> ret = new ArrayList<InetAddress>();

       int  LoopCurrentIP = 0;

        String IPAddress = "";
        String[] myIPArray = YourPhoneIPAddress.split("\\.");
        InetAddress currentPingAddr;

        for (int i = 0; i <= 255; i++) {
            try {

                // build the next IP address
                currentPingAddr = InetAddress.getByName(myIPArray[0] + "." +
                        myIPArray[1] + "." +
                        myIPArray[2] + "." +
                        Integer.toString(LoopCurrentIP));

                // 50ms Timeout for the "ping"
                if (currentPingAddr.isReachable(50)) {

                    ret.add(currentPingAddr);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            LoopCurrentIP++;
        }
        return ret;
    }

    /**
     * Get IP address from first non-localhost interface
     * @param useIPv4  true=return ipv4, false=return ipv6
     * @return  address or empty string
     */
    public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress().toUpperCase();
                        boolean isIPv4 = false;
                        if (addr instanceof Inet4Address)
                            isIPv4 = true;
                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 port suffix
                                return delim < 0 ? sAddr : sAddr.substring(0, delim);
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } // for now eat exceptions
        return "";
    }
}
