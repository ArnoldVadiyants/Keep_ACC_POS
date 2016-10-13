package com.keepaccpos.activities;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.support.v4.view.MotionEventCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.keepaccpos.R;
import com.keepaccpos.models.Model;
import com.keepaccpos.network.http.HttpStatus;

import java.nio.charset.Charset;
import java.util.SortedMap;

public class SettingsPrintActivity extends Activity {
    private static int REQUEST_ENABLE_BT;
    private static String[] standardCodeTables;
    private static int[] standardCodeTablesIndexMapping;
    private final int REQ_CODE_PICK_IMAGE;

    /* renamed from: at.smartlab.tshop.SettingsPrintActivity.1 */
    class C03021 implements OnClickListener {
        C03021() {
        }

        public void onClick(View v) {
            //Callback.onClick_ENTER(v);
            SettingsPrintActivity.this.selectFromPairedBluetooth();
            //Callback.onClick_EXIT();
        }
    }

    /* renamed from: at.smartlab.tshop.SettingsPrintActivity.2 */
    class C03032 implements OnItemSelectedListener {
        final /* synthetic */ String[] val$charEncodings;

        C03032(String[] strArr) {
            this.val$charEncodings = strArr;
        }

        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            //Callback.onItemSelected_ENTER(view, position);
            if (this.val$charEncodings != null && this.val$charEncodings.length >= position) {
                Model.getInstance().getSettings().setCharacterEncoding(this.val$charEncodings[position]);
            }
            //Callback.onItemSelected_EXIT();
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    /* renamed from: at.smartlab.tshop.SettingsPrintActivity.3 */
    class C03043 implements OnItemSelectedListener {
        final /* synthetic */ EditText val$charTableText;

        C03043(EditText editText) {
            this.val$charTableText = editText;
        }

        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            //Callback.onItemSelected_ENTER(view, position);
            Model.getInstance().getSettings().setEscPosCharTable(SettingsPrintActivity.standardCodeTablesIndexMapping[position]);
            this.val$charTableText.setText(Integer.toString(Model.getInstance().getSettings().getEscPosCharTable()));
            //Callback.onItemSelected_EXIT();
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    /* renamed from: at.smartlab.tshop.SettingsPrintActivity.4 */
    class C03054 implements OnCheckedChangeListener {
        final /* synthetic */ CheckBox val$escPos;

        C03054(CheckBox checkBox) {
            this.val$escPos = checkBox;
        }

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            Model.getInstance().getSettings().setEscPos(this.val$escPos.isChecked());
            SettingsPrintActivity.this.checkESCOptionsVisibility();
        }
    }

    /* renamed from: at.smartlab.tshop.SettingsPrintActivity.5 */
    class C03065 implements OnCheckedChangeListener {
        final /* synthetic */ CheckBox val$printTableOnInvoice;

        C03065(CheckBox checkBox) {
            this.val$printTableOnInvoice = checkBox;
        }

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            Model.getInstance().getSettings().setPrintTableOnInvoice(this.val$printTableOnInvoice.isChecked());
        }
    }

    /* renamed from: at.smartlab.tshop.SettingsPrintActivity.6 */
    class C03076 implements OnCheckedChangeListener {
        final /* synthetic */ CheckBox val$printCallNumberOnInvoice;

        C03076(CheckBox checkBox) {
            this.val$printCallNumberOnInvoice = checkBox;
        }

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            Model.getInstance().getSettings().setPrintCallNumberOnInvoice(this.val$printCallNumberOnInvoice.isChecked());
        }
    }

    /* renamed from: at.smartlab.tshop.SettingsPrintActivity.7 */
    class C03087 implements OnClickListener {
        C03087() {
        }

        public void onClick(View arg0) {
            //Callback.onClick_ENTER(arg0);
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction("android.intent.action.PICK");
            SettingsPrintActivity.this.startActivityForResult(Intent.createChooser(intent, "Select Picture"), 23423555);
            //Callback.onClick_EXIT();
        }
    }

    /* renamed from: at.smartlab.tshop.SettingsPrintActivity.8 */
    class C03098 implements OnClickListener {
        final /* synthetic */ ImageView val$productImage;
        final /* synthetic */ ImageButton val$trashProductImage;

        C03098(ImageButton imageButton, ImageView imageView) {
            this.val$trashProductImage = imageButton;
            this.val$productImage = imageView;
        }

        public void onClick(View arg0) {
            //Callback.onClick_ENTER(arg0);
            Model.getInstance().getSettings().setLogo(null, SettingsPrintActivity.this);
            this.val$trashProductImage.setVisibility(View.GONE);
            this.val$productImage.setVisibility(View.GONE);
            //Callback.onClick_EXIT();
        }
    }

    /* renamed from: at.smartlab.tshop.SettingsPrintActivity.9 */
    class C03109 implements DialogInterface.OnClickListener {
        final /* synthetic */ BluetoothDevice[] val$devices;

        C03109(BluetoothDevice[] bluetoothDeviceArr) {
            this.val$devices = bluetoothDeviceArr;
        }

        public void onClick(DialogInterface dialog, int which) {
            ((TextView) SettingsPrintActivity.this.findViewById(R.id.printerBluetooth)).setText(this.val$devices[which].getName());
            Model.getInstance().getSettings().setBluetoothPrinterAddress(this.val$devices[which].getAddress());
            Model.getInstance().getSettings().setBluetoothPrinterName(this.val$devices[which].getName());
        }
    }

    public void onPause() {
        //Callback.onPause(this);
        super.onPause();
    }

    public void onPostCreate(Bundle bundle) {
        //Callback.onPostCreate(this);
        super.onPostCreate(bundle);
    }

    public void onPostResume() {
        //Callback.onPostResume(this);
        super.onPostResume();
    }

    public void onRestart() {
        //Callback.onRestart(this);
        super.onRestart();
    }

    public void onResume() {
        //Callback.onResume(this);
        super.onResume();
    }

    public void onStart() {
        //Callback.onStart(this);
        super.onStart();
    }

    public void onStop() {
        //Callback.onStop(this);
        super.onStop();
    }

    public SettingsPrintActivity() {
        this.REQ_CODE_PICK_IMAGE = 23423555;
    }

    static {
        standardCodeTables = new String[]{"OEM437 (USA, Standard Europe)", "Katakana", "OEM850 (Multilingual)", "OEM860 (Portuguese)", "OEM863 (Canadian)", "OEM865 (Nordic)", "West Europe", "Greek", "Hebrew", "East Europe", "Iran", "WPC1252", "OEM866 (Cyrillic 2)", "OEM852 (Latin 2)", "OEM858", "Iran 2", "Latvian", "Arabic", "PT151, 1251", "OEM747", "WPC1257", "Vietnam", "OEM864", "Hebrew", "WPC1255 (Israel)", "Thai", "OEM437 (Std. Europe)", "OEM437 (Std. Europe)", "OEM858 (Multilingual)", "OEM852 (Latin 2)", "OEM860 (Portuguese)", "OEM861 (Icelandic)", "OEM863 (Canadian)", "OEM865 (Nordic)", "OEM866 (Russian)", "OEM855 (Bulgarian)", "OEM857 (Turkey)", "OEM862 (Hebrew)", "OEM864 (Arabic)", "OEM737 (Greek)", "OEM851 (Greek)", "OEM869 (Greek)", "OEM772 (Lithuanian)", "OEM774 (Lithuanian)", "WPC1252 (Latin 1)", "WPC1250 (Latin 2)", "WPC1251 (Cyrillic)", "PC3840 (IBM Russian)", "PC3842 (Polish)", "PC3844 (CS2)", "PC3845 (Hungarian)", "PC3846 (Turkish)", "PC3847 (Brazil-ABNT)", "PC3848 (Brazil-ABICOMP)", "PC2001 (Lithuanian-KBL)", "PC3001 (Estonian-1)", "PC3002 (Estonian-2)", "PC3011 (Latvian-1)", "PC3012 (Latvian-2)", "PC3021 (Bulgarian)", "PC3041 (Maltese)", "WPC1253 (Greek)", "WPC1254 (Turkish)", "WPC1256 (Arabic)", "OEM720 (Arabic)", "WPC1258 (Vietnam)", "OEM775 (Latvian)", "Thai2"};
        standardCodeTablesIndexMapping = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 27, 28, 31, 32, MotionEventCompat.ACTION_MASK, 50, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 68, 69, 71, 72, 73, 74, 76, 77, 78, 79, 80, 81, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96};
        REQUEST_ENABLE_BT = 76489292;
    }

    protected void onCreate(Bundle savedInstanceState) {
        //Callback.onCreate((Activity) this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_print);
        ImageButton selectBT = (ImageButton) findViewById(R.id.printerBluetoothQuery);
        if (selectBT != null) {
            selectBT.setOnClickListener(new C03021());
        }
        ((TextView) findViewById(R.id.printerBluetooth)).setText(Model.getInstance().getSettings().getBluetoothPrinterName());
        SortedMap<String, Charset> c = Charset.availableCharsets();
        String[] charEncodings = new String[c.size()];
        int i = 0;
        int selected = 0;
        for (String name : c.keySet()) {
            charEncodings[i] = name;
            if (name.equals(Model.getInstance().getSettings().getCharacterEncoding())) {
                selected = i;
            }
            i++;
        }
        Spinner sp = (Spinner) findViewById(R.id.characterEncoding);
        sp.setAdapter(new ArrayAdapter(this, R.layout.simple_list_item_1, charEncodings));
        sp.setSelection(selected);
        sp.setOnItemSelectedListener(new C03032(charEncodings));
        EditText invoiceFooter = (EditText) findViewById(R.id.invoiceFooter);
        if (invoiceFooter != null) {
            invoiceFooter.setText(Model.getInstance().getSettings().getInvoiceFooter());
        }
        EditText printerIPText = (EditText) findViewById(R.id.printerIP);
        if (printerIPText != null) {
            printerIPText.setText(Model.getInstance().getSettings().getPrinter());
        }
        EditText charTableText = (EditText) findViewById(R.id.printerCharTable);
        if (charTableText != null) {
            charTableText.setText(Integer.toString(Model.getInstance().getSettings().getEscPosCharTable()));
        }
        Spinner cT = (Spinner) findViewById(R.id.characterTableChooser);
        cT.setAdapter(new ArrayAdapter(this, R.layout.simple_list_item_1, standardCodeTables));
        int ct = Model.getInstance().getSettings().getEscPosCharTable();
        int sel = 0;
        int x = 0;
        while (true) {
            if (x >= standardCodeTablesIndexMapping.length) {
                break;
            }
            if (standardCodeTablesIndexMapping[x] == ct) {
                sel = x;
            }
            x++;
        }
        cT.setOnItemSelectedListener(new C03043(charTableText));
        cT.setSelection(sel);
        EditText printerMaxColText = (EditText) findViewById(R.id.printerMaxColumns);
        if (printerMaxColText != null) {
            printerMaxColText.setText(Integer.toString(Model.getInstance().getSettings().getMaxPrintColumnNr()));
        }
        EditText printerMultipleText = (EditText) findViewById(R.id.printerMultiple);
        if (printerMultipleText != null) {
            printerMultipleText.setText(Integer.toString(Model.getInstance().getSettings().getInvoicePrintMultiply()));
        }
        CheckBox escPos = (CheckBox) findViewById(R.id.escPos);
        if (escPos != null) {
            escPos.setChecked(Model.getInstance().getSettings().isEscPos());
            checkESCOptionsVisibility();
            escPos.setOnCheckedChangeListener(new C03054(escPos));
        }
        CheckBox printTableOnInvoice = (CheckBox) findViewById(R.id.printTableOnInvoice);
        if (printTableOnInvoice != null) {
            printTableOnInvoice.setChecked(Model.getInstance().getSettings().isPrintTableOnInvoice());
            printTableOnInvoice.setOnCheckedChangeListener(new C03065(printTableOnInvoice));
        }
        CheckBox printCallNumberOnInvoice = (CheckBox) findViewById(R.id.printCallNumberOnInvoice);
        if (printCallNumberOnInvoice != null) {
            printCallNumberOnInvoice.setChecked(Model.getInstance().getSettings().isPrintCallNumberOnInvoice());
            printCallNumberOnInvoice.setOnCheckedChangeListener(new C03076(printCallNumberOnInvoice));
        }
        EditText drawerCmds = (EditText) findViewById(R.id.printerCashdrawerCmd);
        if (drawerCmds != null) {
            drawerCmds.setText(Model.getInstance().getSettings().getDrawerCmds());
        }
        EditText cutterCmds = (EditText) findViewById(R.id.printerCutterCmd);
        if (cutterCmds != null) {
            cutterCmds.setText(Model.getInstance().getSettings().getCutterCmds());
        }
        ((Spinner) findViewById(R.id.charSizeSpinner)).setSelection(Model.getInstance().getSettings().getEscPosCharSize());
        ImageView productImage = (ImageView) findViewById(R.id.logoImage);
        ImageButton selectImageButton = (ImageButton) findViewById(R.id.logo_image);
        ImageButton trashProductImage = (ImageButton) findViewById(R.id.trash_logo_image);
        if (Model.getInstance().getSettings().getLogo(HttpStatus.SC_OK, 150) != null) {
            productImage.setImageBitmap(Model.getInstance().getSettings().getLogo(HttpStatus.SC_OK, 150));
            productImage.setMinimumWidth(HttpStatus.SC_OK);
            trashProductImage.setVisibility(View.VISIBLE);;
        } else {
            productImage.setVisibility(View.GONE);
            trashProductImage.setVisibility(View.GONE);
        }
        selectImageButton.setOnClickListener(new C03087());
        trashProductImage.setOnClickListener(new C03098(trashProductImage, productImage));
    }

    private void checkESCOptionsVisibility() {
        LinearLayout l = (LinearLayout) findViewById(R.id.escPosOptions);
        if (!Model.getInstance().getSettings().isEscPos() || l == null) {
            l.setVisibility(View.GONE);
        } else {
            l.setVisibility(View.VISIBLE);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 23423555 && resultCode == -1) {
            try {
                Model.getInstance().getSettings().setLogo(Media.getBitmap(getContentResolver(), intent.getData()), getApplicationContext());
                ImageView ib = (ImageView) findViewById(R.id.logoImage);
                ib.setVisibility(View.VISIBLE);;
                ib.setImageBitmap(Model.getInstance().getSettings().getLogo(HttpStatus.SC_OK, 100));
                ((ImageButton) findViewById(R.id.trash_logo_image)).setVisibility(View.VISIBLE);;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_settings_print, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        //Callback.onOptionsItemSelected_ENTER(item);
        switch (item.getItemId()) {
            case R.id.menu_help /*2131493272*/:
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setData(Uri.parse("https://www.youtube.com/playlist?list=PLaF9sQLFlVTiZ0x5fACIREMlz5MZZy-XR"));
                startActivity(intent);
                break;
            case R.id.menu_finish /*2131493273*/:
                Model.getInstance().getSettings().setEscPosCharSize(((Spinner) findViewById(R.id.charSizeSpinner)).getSelectedItemPosition());
                Model.getInstance().getSettings().setInvoiceFooter(((EditText) findViewById(R.id.invoiceFooter)).getText().toString());
                Model.getInstance().getSettings().setPrinter(((EditText) findViewById(R.id.printerIP)).getText().toString());
                EditText charTableText = (EditText) findViewById(R.id.printerCharTable);
                if (charTableText != null) {
                    try {
                        Model.getInstance().getSettings().setEscPosCharTable(Integer.parseInt(charTableText.getText().toString()));
                    } catch (NumberFormatException e) {
                    }
                }
                try {
                    int maxCol = Integer.parseInt(((EditText) findViewById(R.id.printerMaxColumns)).getText().toString());
                    if (maxCol > 15) {
                        Model.getInstance().getSettings().setMaxPrintColumnNr(maxCol);
                    }
                } catch (NumberFormatException e2) {
                }
                EditText printerMultipleText = (EditText) findViewById(R.id.printerMultiple);
                if (printerMultipleText != null) {
                    try {
                        Model.getInstance().getSettings().setInvoicePrintMultiply(Integer.parseInt(printerMultipleText.getText().toString()));
                    } catch (NumberFormatException e3) {
                    }
                }
                EditText drawerCmds = (EditText) findViewById(R.id.printerCashdrawerCmd);
                if (drawerCmds != null) {
                    Model.getInstance().getSettings().setDrawerCmds(drawerCmds.getText().toString());
                }
                EditText cutterCmds = (EditText) findViewById(R.id.printerCutterCmd);
                if (cutterCmds != null) {
                    Model.getInstance().getSettings().setCutterCmds(cutterCmds.getText().toString());
                }
                Model.getInstance().getSettings().storeSettings(getSharedPreferences("Preferences", 0));
                finish();
                break;
        }
        //Callback.onOptionsItemSelected_EXIT();
        return true;
    }

    private void selectFromPairedBluetooth() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter != null) {
            if (!mBluetoothAdapter.isEnabled()) {
                startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), REQUEST_ENABLE_BT);
            }
            CharSequence[] items = new CharSequence[mBluetoothAdapter.getBondedDevices().size()];
            BluetoothDevice[] devices = new BluetoothDevice[mBluetoothAdapter.getBondedDevices().size()];
            int i = 0;
            for (BluetoothDevice d : mBluetoothAdapter.getBondedDevices()) {
                items[i] = d.getName();
                devices[i] = d;
                i++;
            }
            Builder builder = new Builder(this);
            builder.setTitle("Select Paired Printer");
            builder.setItems(items, new C03109(devices));
            builder.create().show();
        }
    }
}
