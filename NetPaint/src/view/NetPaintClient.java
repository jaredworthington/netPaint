package view;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import model.PaintObj;
import view.View.MListener;

public class NetPaintClient implements Observer {

	Socket socket;
	public static final String ADDRESS = "localhost";
	ObjectOutputStream oos;
	ObjectInputStream ois;
	NetPaintGUI npg;
	MListener ml;
	Vector<PaintObj> obj;
	
	@SuppressWarnings("unchecked")
	private NetPaintClient(){
		npg = new NetPaintGUI();
		openConnection();
		try {
			obj = (Vector<PaintObj>) ois.readObject();
			npg.canvas.list = obj;
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}

		
		npg.canvas.callPaintComponent();
		new ServerListener().start();
		npg.canvas.ml.addObserver(this);
	}
	
	public static void main(String args[]) {
	    NetPaintClient c = new NetPaintClient();
	    
	  }
	
	
	public void openConnection(){
		try {
			socket = new Socket(ADDRESS, NetPaintServer.SERVER_PORT );
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	private class ServerListener extends Thread{
		
		@SuppressWarnings("unchecked")
		public void run() {
			try {
				while(true){
					obj = (Vector<PaintObj>) ois.readObject();

					npg.canvas.list = obj;
					npg.canvas.callPaintComponent();
			
				}
			} catch (ClassNotFoundException | IOException e) {

				e.printStackTrace();
			}
		}
	}


	@Override
	public void update(Observable o, Object arg) {
		
		ml = (MListener) o;		
		try {
			oos.reset();
			oos.writeObject(arg);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
