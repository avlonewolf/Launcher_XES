package backbenchersbeta.xeslauncher;

/**
 * Created by Ankit Vimal on 31-03-2016.
 */

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Conta {

    private static final String LOG_TAG = "AudioCall";
    private static final int BUF_SIZE =2000; //Bytes
    private InetAddress address; // Address to call
    private int port = 50000; // Port the packets are addressed to


    public Conta(InetAddress address) {

        this.address = address;
    }


    public void startMic() {
        // Creates the thread for capturing and transmitting audio
         Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                // Create an instance of the AudioRecord class
                Log.i(LOG_TAG, "Send thread started. Thread id: " + Thread.currentThread().getId());
                int bytes_read = 0;
                int bytes_sent = 0;
                byte[] buf = new byte[BUF_SIZE];
                try {
                    // Create a socket and start recording
                    Log.i(LOG_TAG, "Packet destination: " + address.toString());
                    DatagramSocket socket = new DatagramSocket();
                    audioRecorder.startRecording();
                    while(mic) {
                        // Capture audio from the mic and transmit it
                        bytes_read = audioRecorder.read(buf, 0, BUF_SIZE);
                        DatagramPacket packet = new DatagramPacket(buf, bytes_read, address, port);
                        socket.send(packet);
                        bytes_sent += bytes_read;
                        Log.i(LOG_TAG, "Total bytes sent: " + bytes_sent);
                        Thread.sleep(SAMPLE_INTERVAL, 0);
                    }
                    // Stop recording and release resources
                    audioRecorder.stop();
                    audioRecorder.release();
                    socket.disconnect();
                    socket.close();
                    mic = false;
                    return;
                }
                catch(InterruptedException e) {

                    Log.e(LOG_TAG, "InterruptedException: " + e.toString());
                    mic = false;
                }
                catch(SocketException e) {

                    Log.e(LOG_TAG, "SocketException: " + e.toString());
                    mic = false;
                }
                catch(UnknownHostException e) {

                    Log.e(LOG_TAG, "UnknownHostException: " + e.toString());
                    mic = false;
                }
                catch(IOException e) {

                    Log.e(LOG_TAG, "IOException: " + e.toString());
                    mic = false;
                }
            }
        });
        thread.start();
    }

    public void startSpeakers() {
        // Creates the thread for receiving and playing back audio



            Thread receiveThread = new Thread(new Runnable() {

                @Override
                public void run() {
                    // Create an instance of AudioTrack, used for playing back audio
                    Log.i(LOG_TAG, "Receive thread started. Thread id: " + Thread.currentThread().getId());
                    track.play();
                    try {
                        // Define a socket to receive the audio
                        DatagramSocket socket = new DatagramSocket(port);
                        byte[] buf = new byte[BUF_SIZE];
                        while(speakers) {
                            // Play back the audio received from packets
                            DatagramPacket packet = new DatagramPacket(buf, BUF_SIZE);
                            socket.receive(packet);
                            Log.i(LOG_TAG, "Packet received: " + packet.getLength());
                            track.write(packet.getData(), 0, BUF_SIZE);
                        }
                        // Stop playing back and release resources
                        socket.disconnect();
                        socket.close();
                        track.stop();
                        track.flush();
                        track.release();
                        speakers = false;
                        return;
                    }
                    catch(SocketException e) {

                        Log.e(LOG_TAG, "SocketException: " + e.toString());
                        speakers = false;
                    }
                    catch(IOException e) {

                        Log.e(LOG_TAG, "IOException: " + e.toString());
                        speakers = false;
                    }
                }
            });
            receiveThread.start();

    }

}
