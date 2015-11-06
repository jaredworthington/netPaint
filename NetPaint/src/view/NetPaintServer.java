package view;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import model.PaintObj;

public class NetPaintServer {
	
	public static final int SERVER_PORT = 9001;

	private static ServerSocket socket;
	private static List<ObjectOutputStream> clients = new ArrayList<>();
	private static Vector<PaintObj> vList = new Vector<PaintObj>();
	
	
	public static void main(String[] args) throws IOException {
		socket = new ServerSocket(SERVER_PORT);
		while (true) {
			
			Socket cs = socket.accept();
			
			ObjectInputStream ois = new ObjectInputStream(cs.getInputStream());
			ObjectOutputStream oos = new ObjectOutputStream(cs.getOutputStream());
			
			oos.writeObject(vList);
			clients.add(oos);
			
			ClientHandler ch = new ClientHandler(ois, clients);
			ch.start();
				
		}
	}
	
	static class ClientHandler extends Thread{
		ObjectInputStream in;
		List<ObjectOutputStream> clients;
		
		ClientHandler(ObjectInputStream inp, List<ObjectOutputStream> l){
			in = inp;
			clients = l;
		}
		
		public void run(){
			while(true){
				
				PaintObj obj;
				
				try {
					obj = (PaintObj) in.readObject();

					vList.add(obj);
				} catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					try {
						in.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					return;
				}
				
				sendToClients(vList);
			
			}
			
		}
		public void sendToClients(Vector<PaintObj> l){
			synchronized(clients){
				Set<ObjectOutputStream> closed = new HashSet<>();
				for(ObjectOutputStream client : clients){
					try {
						client.writeObject(l);
						client.reset();
					} catch (IOException e) {
						closed.add(client);				}
				}
				clients.removeAll(closed);
			}
		}
	}
}
