package net.thesocialos.server.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.SerializationPolicy;
import com.google.gwt.user.server.rpc.SerializationPolicyLoader;

public class MergedSerializationPolicy extends SerializationPolicy {
	public static SerializationPolicy createPushSerializationPolicy() {
		
		File[] files = new File("thesocialos").listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".gwt.rpc");
			}
		});
		
		List<SerializationPolicy> policies = new ArrayList<SerializationPolicy>();
		
		for (File f : files)
			try {
				BufferedInputStream input = new BufferedInputStream(new FileInputStream(f));
				policies.add(SerializationPolicyLoader.loadFromStream(input, null));
			} catch (Exception e) {
				throw new RuntimeException("Unable to load a policy file: " + f.getAbsolutePath());
			}
		
		return new MergedSerializationPolicy(policies);
	}
	
	List<SerializationPolicy> policies;
	
	MergedSerializationPolicy(List<SerializationPolicy> policies) {
		this.policies = policies;
	}
	
	@Override
	public boolean shouldDeserializeFields(Class<?> clazz) {
		for (SerializationPolicy p : policies)
			if (p.shouldDeserializeFields(clazz)) return true;
		return false;
	}
	
	@Override
	public boolean shouldSerializeFields(Class<?> clazz) {
		for (SerializationPolicy p : policies)
			if (p.shouldSerializeFields(clazz)) return true;
		return false;
	}
	
	@Override
	public void validateDeserialize(Class<?> clazz) throws SerializationException {
		SerializationException se = null;
		for (SerializationPolicy p : policies)
			try {
				p.validateDeserialize(clazz);
				return;
			} catch (SerializationException e) {
				se = e;
			}
		throw se;
	}
	
	@Override
	public void validateSerialize(Class<?> clazz) throws SerializationException {
		SerializationException se = null;
		for (SerializationPolicy p : policies)
			try {
				p.validateSerialize(clazz);
				return;
			} catch (SerializationException e) {
				se = e;
			}
		throw se;
	}
}
