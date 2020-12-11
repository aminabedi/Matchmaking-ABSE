package ase;
import jade.core.AID;
public class Message {
	public static AID sender;
	public static AID receiver;
	public static String content;
	
	public Message(AID s, AID r, String c) {
		sender = s;
		receiver = r;
		content = c;
	}
}
