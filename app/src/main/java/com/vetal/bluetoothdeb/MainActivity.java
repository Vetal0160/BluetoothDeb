package com.vetal.bluetoothdeb;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import static android.R.layout.simple_list_item_1;


public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 1;
    private static final int MAX_IDX_BUFF_FIFO = 100;
    private static final int MAX_IDX_BUFF_MES = 8;
    private static final int MAX_IDX_ARAYBATTERY = 161;
    private UUID myUUID;

    public short[] Buffer_FIFO = new short[MAX_IDX_BUFF_FIFO];
    public short[] Buff_Mes = new short[MAX_IDX_BUFF_MES];
    public static float[][] ArrayBattery = new float[MAX_IDX_ARAYBATTERY][MAX_IDX_BUFF_MES];
    public int Idx_Buf_In, IdxWrite_Buf_Fifo, IdxRead_Buf_Fifo, Idx_Buf_Mes, Size_Buf_In = 0;
    public int delay;

    BluetoothAdapter bluetoothAdapter;
    ArrayList<String> pairedDeviceArrayList;
    ListView listViewPairedDevice;
    TextView myTextView;
    Button BtnScan, BtnClear, BtnStop, BtnGraph;
    ArrayAdapter<String> pairedDeviceAdapter;
    ThreadConnectBTdevice myThreadConnectBTdevice;
    ThreadConnected myThreadConnected;
    Thread myThreadRead,myThreadViewData;
    Timer t = new Timer();
    public ViewDataLog mMyTimerTask;
    boolean Pause,Pause2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Pause = false;
        final String UUID_STRING_WELL_KNOWN_SPP = "00001101-0000-1000-8000-00805F9B34FB";

        BtnScan = (Button) findViewById(R.id.btnScan);
        BtnClear = (Button) findViewById(R.id.btnClear);
        BtnStop = (Button) findViewById(R.id.btnStop);
        BtnGraph = (Button) findViewById(R.id.btnGraph);
        listViewPairedDevice = (ListView) findViewById(R.id.pairedlist);
        myTextView = (TextView) findViewById(R.id.textView);
        myTextView.setMovementMethod(new ScrollingMovementMethod());

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH)) {
            Toast.makeText(this, "BLUETOOTH NOT support", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        myUUID = UUID.fromString(UUID_STRING_WELL_KNOWN_SPP);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not supported on this hardware platform", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        View.OnClickListener oclBtnScan = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setup();
            }
        };
        View.OnClickListener oclBtnClear = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myTextView.setText("");
            }
        };
        View.OnClickListener oclBtnStop = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Pause) {
                    Pause = true;
                    BtnStop.setText(getString(R.string.play));
                } else {
                    Pause = false;
                    BtnStop.setText(getString(R.string.stop));
                }
            }
        };
        View.OnClickListener oclBtnGraph = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GraphView.class));
                Pause2 = true;
            }
        };
        BtnScan.setOnClickListener(oclBtnScan);
        BtnClear.setOnClickListener(oclBtnClear);
        BtnStop.setOnClickListener(oclBtnStop);
        BtnGraph.setOnClickListener(oclBtnGraph);

    } // END onCreate

    void appendLog(final String Nmb, final String Voltage, final String Tmp) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                StringBuilder msg = new StringBuilder();
                // Построение строки
                msg.append("№:").append(Nmb).append(" ").append("U:").append(Voltage).append(" ").append("t:").append(Tmp).append("\n");
                //  Вывод на экран
                myTextView.append(msg);
            }
        });
    }
    void EndLine() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                StringBuilder end = new StringBuilder();
                end.append(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime())).append("\n");
                myTextView.append(end);
            }
        });
    }

    @Override
    protected void onStart() { // Запрос на включение Bluetooth
        super.onStart();

    }

    private void setup() { // Создание списка сопряжённых Bluetooth-устройств

        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

        if (pairedDevices.size() > 0) { // Если есть сопряжённые устройства

            pairedDeviceArrayList = new ArrayList<>();

            for (BluetoothDevice device : pairedDevices) { // Добавляем сопряжённые устройства - Имя + MAC-адресс
                pairedDeviceArrayList.add(device.getName() + "\n" + device.getAddress());
            }

            pairedDeviceAdapter = new ArrayAdapter<>(this, simple_list_item_1, pairedDeviceArrayList);
            listViewPairedDevice.setAdapter(pairedDeviceAdapter);

            listViewPairedDevice.setOnItemClickListener(new AdapterView.OnItemClickListener() { // Клик по нужному устройству

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    listViewPairedDevice.setVisibility(View.GONE); // После клика скрываем список

                    String itemValue = (String) listViewPairedDevice.getItemAtPosition(position);
                    String MAC = itemValue.substring(itemValue.length() - 17); // Вычленяем MAC-адрес

                    BluetoothDevice device2 = bluetoothAdapter.getRemoteDevice(MAC);

                    myThreadConnectBTdevice = new ThreadConnectBTdevice(device2);
                    myThreadConnectBTdevice.start();  // Запускаем поток для подключения Bluetooth
                }
            });
        }
    }

    @Override
    protected void onDestroy() { // Закрытие приложения
        super.onDestroy();
        if (myThreadConnectBTdevice != null) myThreadConnectBTdevice.cancel();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ENABLE_BT) { // Если разрешили включить Bluetooth, тогда void setup()

            if (resultCode == Activity.RESULT_OK) {
                setup();
            } else { // Если не разрешили, тогда закрываем приложение

                Toast.makeText(this, "BlueTooth не включён", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    // ============================================================================
    private class ThreadConnectBTdevice extends Thread { // Поток для коннекта с Bluetooth

        private BluetoothSocket bluetoothSocket = null;

        private ThreadConnectBTdevice(BluetoothDevice device) {

            try {
                bluetoothSocket = device.createRfcommSocketToServiceRecord(myUUID);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        @Override
        public void run() { // Коннект

            boolean success = false;

            try {
                bluetoothSocket.connect();
                success = true;
            } catch (IOException e) {
                e.printStackTrace();

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "Нет коннекта, проверьте Bluetooth-устройство с которым хотите соединиться!", Toast.LENGTH_LONG).show();
                        listViewPairedDevice.setVisibility(View.VISIBLE);
                    }
                });

                try {
                    bluetoothSocket.close();
                } catch (IOException e1) {

                    e1.printStackTrace();
                }
            }

            if (success) {  // Если законнектились, тогда открываем панель с кнопками и запускаем поток приёма и отправки данных

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        myTextView.setVisibility(View.VISIBLE);
                    }
                });

                myThreadConnected = new ThreadConnected(bluetoothSocket);
                myThreadConnected.start(); // запуск потока приёма данных
                //Создание потока
                myThreadRead = new ReadData();
                myThreadRead.start();
                myThreadViewData = new ViewData();
                myThreadViewData.start();
            }
        }


        public void cancel() {

            Toast.makeText(getApplicationContext(), "Close - BluetoothSocket", Toast.LENGTH_LONG).show();

            try {
                bluetoothSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    private class ThreadConnected extends Thread {    // Поток - приём  данных

        private final InputStream connectedInputStream;

        private ThreadConnected(BluetoothSocket socket) {

            InputStream in = null;
            try {
                in = socket.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            connectedInputStream = in;
        }

        @Override
        public void run() { // Приём данных

            while (true) {
                Size_Buf_In = 0;
                byte[] buffer = new byte[100];
                try {
                    int data = connectedInputStream.read(buffer);
                    WriteData(buffer, data);
                } catch (IOException e) {
                    break;
                }
            }

        }

        private void WriteData(byte[] b, int Size_Buf_In) {
            Idx_Buf_In = 0;
            do {
                Buffer_FIFO[IdxWrite_Buf_Fifo] = (b[Idx_Buf_In] < 0) ? ((short) ((b[Idx_Buf_In] & 127) | 128)) : ((short) (b[Idx_Buf_In]));// Переводим signet байт в short и записываем в кольцевой массив
                Idx_Buf_In++;
                IdxWrite_Buf_Fifo++;
                if (IdxWrite_Buf_Fifo == MAX_IDX_BUFF_FIFO)
                    IdxWrite_Buf_Fifo = 0;
                if (IdxWrite_Buf_Fifo == IdxRead_Buf_Fifo) {
                    /*runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "Ошибка!!!индекс считывания равен индексу записи!", Toast.LENGTH_LONG).show();
                        }
                    });*/
                }
            }
            while (Idx_Buf_In < Size_Buf_In);
        }
    }

    private class ReadData extends Thread {
        @Override
        public void run() {
            while (true) {
                while (IdxRead_Buf_Fifo == IdxWrite_Buf_Fifo) {
                   /* runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "Ждем Данные", Toast.LENGTH_LONG).show(); //Ловушка
                        }
                    });*/
                }
                switch (Idx_Buf_Mes) {
                    case 0: {
                        if (Buffer_FIFO[IdxRead_Buf_Fifo] == 0xaa) {
                            Idx_Buf_Mes = 1;
                        } else Idx_Buf_Mes = 0;
                    }
                    //Проверка первого байта
                    break;
                    case 1: {
                        if (Buffer_FIFO[IdxRead_Buf_Fifo] == 0x55) {
                            Idx_Buf_Mes = 2;
                        } else Idx_Buf_Mes = 0;
                    }
                    //Проверка второго байта
                    break;
                    case 7: {
                        delay = 1700 - (Buff_Mes[3] * 10);
                        Buff_Mes[Idx_Buf_Mes] = Buffer_FIFO[IdxRead_Buf_Fifo];
                        Idx_Buf_Mes = 0;
                       /* if (!Pause) {
                            appendLog(String.valueOf(Buff_Mes[3]), String.valueOf(ParsingVoltage(Buff_Mes[4])), String.valueOf(ParsingTmp(Buff_Mes[5]))); //Отправка данных
                        }*/
                        //Запись данных в массив
                        ArrayBattery[Buff_Mes[3]][0] = Buff_Mes[3];
                        ArrayBattery[Buff_Mes[3]][1] = (float) ParsingVoltage(Buff_Mes[4]);
                        ArrayBattery[Buff_Mes[3]][2] = ParsingTmp(Buff_Mes[5]);
                        ArrayBattery[Buff_Mes[3]][3] = 4;

                    }
                    break;
                    default: {
                        if (Idx_Buf_Mes >= 2 && Idx_Buf_Mes < 7) {
                            Buff_Mes[Idx_Buf_Mes] = Buffer_FIFO[IdxRead_Buf_Fifo]; // запись байтов после проверки
                            Idx_Buf_Mes++;
                        }
                    }
                    break;
                }
                IdxRead_Buf_Fifo++;
                if (IdxRead_Buf_Fifo == MAX_IDX_BUFF_FIFO)
                    IdxRead_Buf_Fifo = 0;
                // Обнуление индекса считывания
            }
        }
    }

    public static double ParsingVoltage(short Voltage) {

        return (Math.round((float) (Voltage * 1.07386 + 200))) / 100.;// Формула получения напряжения
    }

    public static short ParsingTmp(short Tmp) {

        return (short) (Math.abs(Tmp) - 82);// Формула получения темпрературы
    }
    public class ViewDataLog extends TimerTask {
        @Override
        public void run() {
            if (!Pause) {
                for (int i = 1; i < 161; i++) {
                    if (ArrayBattery[i][0] > 0) {
                        appendLog(String.valueOf((int) ArrayBattery[i][0]), String.valueOf(ArrayBattery[i][1]), String.valueOf((int) ArrayBattery[i][2]));
                    }
                }
                EndLine();
            }
        }
    }
    public class ViewData extends Thread {

        @Override
        public void run () {
            while (true) {
                    mMyTimerTask = new ViewDataLog();
                    t.schedule(mMyTimerTask, delay);
                try {
                    Thread.sleep(2300 + delay);
                } catch (InterruptedException e) {
                    //Error
                }
            }
        }

    }

} // END
