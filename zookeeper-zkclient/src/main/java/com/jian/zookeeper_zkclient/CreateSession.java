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

public class CreateSession {

	public static void main(String[] args) throws InterruptedException, NoSuchAlgorithmException {
		
		ZkClient zc=new ZkClient("192.168.3.242:2181",10000,10000,new SerializableSerializer());
		System.out.println("connected ok!");
		
		//创建节点
		User user=new User();
		user.setId(1);
		user.setName("test");
		String path= zc.create("/node21", user, CreateMode.PERSISTENT);
		System.out.println("created path:"+path);
		
		//获得数据
		Stat stat=new Stat();
		User user1=zc.readData("/node21",stat);
		System.out.println(user1.getName());
		System.out.println(stat.toString());
		
		//创建子节点
		String subPath= zc.create("/node21/node21_1", "sub_21_1", CreateMode.PERSISTENT);
		System.out.println("created path:"+subPath);
		
		//获取子节点
		List<String> clist=zc.getChildren("/node21");
		System.out.println(clist.toString());
		
		//检测节点是否存在
		boolean blzc= zc.exists("/node21");
		System.out.println(blzc);
		
		//节点删除
		boolean blde= zc.delete("/node21");
		System.out.println(blde);
		
		//节点删除及其下面的子节点
		boolean bldr= zc.deleteRecursive("/node21");
		System.out.println(bldr);
		
		//数据修改
		User user2=new User();
		user2.setId(2);
		user2.setName("test2");
		zc.writeData("/node21", user2);
		
		//子节点变化事件订阅
		zc.subscribeChildChanges("/node21", new ZkChildListener());
		Thread.sleep(Integer.MAX_VALUE);
		
		ZkClient zcb=new ZkClient("121.40.190.76:2181",10000,10000,new BytesPushThroughSerializer());
		//数据变化事件订阅
		zcb.subscribeDataChanges("/node21", new ZkDataListener());
		Thread.sleep(Integer.MAX_VALUE);
		
		//权限设置
		User user3=new User();
		user3.setId(1);
		user3.setName("test");
		ACL aclDigest=new ACL(Perms.READ|Perms.WRITE|Perms.DELETE,new Id("digest",DigestAuthenticationProvider.generateDigest("jianxijun:123456")));
		List<ACL> acls=new ArrayList<ACL>();
		acls.add(aclDigest);
		String pathc= zc.create("/node21", user3,acls,CreateMode.PERSISTENT);
		System.out.println("created path:"+pathc);
		
		//权限登录
		zc.addAuthInfo("digest", "jianxijun:123456".getBytes());
		boolean blec= zc.exists("/node21");
		System.out.println(blec);
	}
	
	
	
	public static class ZkChildListener implements IZkChildListener {

		public void handleChildChange(String parentPath,
				List<String> currentChilds) throws Exception {
			System.out.println(parentPath);
			System.out.println(currentChilds.toString());
		}
	}
	
	public static class ZkDataListener implements IZkDataListener {

		public void handleDataChange(String dataPath, Object data)
				throws Exception {
			System.out.println(dataPath+":"+data.toString());
		}

		public void handleDataDeleted(String dataPath) throws Exception {
			System.out.println(dataPath);
		}
	}
	
}
