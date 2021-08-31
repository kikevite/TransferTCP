package cat.kikevite.transfertcp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    int port = 9700;

    boolean borrarMissatgeEdit = true;
    List<String> conversacio = new ArrayList<>();
    List<Boolean> conversacioEnviat = new ArrayList<>();

    EditText ipEdit, missatgeEdit, portEdit;
    RecyclerViewAdapter adapter;
    RecyclerView myRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ipEdit = findViewById(R.id.ipEdit);
        missatgeEdit = findViewById(R.id.missatgeEdit);
        portEdit = findViewById(R.id.portEdit);
        myRecyclerView = findViewById(R.id.conversaRv);

        myRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager myLayoutManager;
        myLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        myRecyclerView.setLayoutManager(myLayoutManager);
        adapter = new RecyclerViewAdapter(conversacio, conversacioEnviat);
        myRecyclerView.setAdapter(adapter);

        String ip = getIPAddress(true);
        String[] parts = ip.split("\\.");
        if (parts.length == 4) {
            ipEdit.setText(parts[0] + "." + parts[1] + "." + parts[2] + ".");
            this.setTitle("IP Origen: " + ip + ":" + port);
        } else {
            ipEdit.setText("");
            this.setTitle("IP Origen: No conectat");
        }

        Thread thread = new Thread(new MyServer());
        thread.start();
    }

    //////// REBRE MISSATGES ///////////////
    class MyServer implements Runnable {

        ServerSocket ss;
        Socket s;
        DataInputStream dis;
        String msg;
        Handler handler = new Handler();

        @Override
        public void run() {
            try {
                ss = new ServerSocket(port);
                while (true) {
                    s = ss.accept();
                    dis = new DataInputStream(s.getInputStream());
                    msg = dis.readUTF();
                    handler.post(() -> {
                        Inet4Address in4addr = (Inet4Address) s.getInetAddress();
                        String ipOrigen = in4addr.toString();
                        String newMissatge = ipOrigen + ": " + msg;
                        conversacio.add(newMissatge);
                        conversacioEnviat.add(false);
                        adapter.notifyDataSetChanged();
                        myRecyclerView.scrollToPosition(conversacio.size() - 1);
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    //////// ENVIAR MISSATGES //////////////
    public void enviar(View view) {
        BackgroundTask b = new BackgroundTask();
        String ipDestino = ipEdit.getText().toString();
        String[] parts = ipDestino.split("\\.");
        boolean rangOk = true;
        for (String s : parts) {
            if (Integer.parseInt(s) < 0 || Integer.parseInt(s) > 255) {
                rangOk = false;
            }
        }
        if (parts.length == 4 && rangOk) {
            String portEnviarString = portEdit.getText().toString();
            b.execute(ipDestino, portEnviarString, missatgeEdit.getText().toString());
            String newMissatge = "Tu a " + ipDestino + ": " + missatgeEdit.getText().toString();
            conversacio.add(newMissatge);
            conversacioEnviat.add(true);
            adapter.notifyDataSetChanged();
            myRecyclerView.scrollToPosition(conversacio.size() - 1);
            if (borrarMissatgeEdit) {
                missatgeEdit.setText("");
            }
        } else if (parts.length != 4 && !rangOk) {
            Toast.makeText(getApplicationContext(), "bad rang & length IP", Toast.LENGTH_LONG).show();
        } else if (parts.length != 4) {
            Toast.makeText(getApplicationContext(), "bad length IP", Toast.LENGTH_LONG).show();
        } else  {
            Toast.makeText(getApplicationContext(), "bad rang IP (1...255)", Toast.LENGTH_LONG).show();
        }
    }

    class BackgroundTask extends AsyncTask<String, Void, Boolean> {

        String msg;

        @Override
        protected Boolean doInBackground(String... params) {
            boolean error = false;
            String ip = params[0];
            int portEnv = Integer.parseInt(params[1]);
            msg = params[2];
            try {
                Socket s = new Socket(ip, portEnv);
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                dos.writeUTF(msg);
                dos.close();
            } catch (IOException e) {
                e.printStackTrace();
                error = true;
            }
            return error;
        }

        @Override
        protected void onPostExecute(Boolean error) {
            super.onPostExecute(error);
            if (error) {
                Toast.makeText(getApplicationContext(), "Error enviant missatge: " + msg, Toast.LENGTH_LONG).show();
            }
        }
    }

    public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        boolean isIPv4 = sAddr.indexOf(':') < 0;
                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                                return delim < 0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ignored) {
        }
        return "";
    }
}