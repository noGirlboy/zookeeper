package com.jian.zookeeper_zkclient;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.BytesPushThroughSerializer;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Perms;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

public class Password {

	public static void main(String[] args) throws InterruptedException, NoSuchAlgorithmException {
		
		String passwords=DigestAuthenticationProvider.generateDigest("jianxijun:123456");
		System.out.println(passwords);
		
		
	}
	
	
	
}
