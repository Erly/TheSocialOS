package net.thesocialos.shared.model;

import java.sql.Date;
import javax.persistence.Id;
import com.googlecode.objectify.annotation.Entity;

@Entity
public class Conversation {

	@Id Long id;
	Date date; //The date of the conversation
	
	public Conversation(){
		
	}
}
