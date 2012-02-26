package net.thesocialos.shared.model;

import java.sql.Date;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Parent;

public class Lines {

	@Id Long id;
	Date date; //The date that has been written
	String text; //The text of the line
	@Parent Key<Conversation> converOwner;
	
	public Lines(){
		
	}
}
