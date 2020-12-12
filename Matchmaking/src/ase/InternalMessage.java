package ase;
import jade.core.AID;
public class InternalMessage {
	public static AID sender;
	public static AID receiver;
	public  String content;
	
	public InternalMessage(AID s, AID r, String c) {
		sender = s;
		receiver = r;
		content = c;
	}
}
