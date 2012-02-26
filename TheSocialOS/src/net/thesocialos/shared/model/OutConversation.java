package net.thesocialos.shared.model;



import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Subclass;


@Subclass
public class OutConversation extends Conversation {

	Key<Lines> lineDesconnected[]; //Lines that have been written offline
	
	public OutConversation(){
		
	}
}
