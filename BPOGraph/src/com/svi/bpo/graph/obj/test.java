package com.svi.bpo.graph.obj;

import com.svi.bpo.graph.BPO;

public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
BPO bpo = new BPO("http://localhost:7474");
//System.out.println(bpo.getNodeFunctions().buildFlow("fsdf", "node","node2").toString());
	
//for(int i = 0;i < 200;i++){
//	System.out.println(bpo.getElementFunctions().insertElement("ELEMS-" + i, 0, "New Nodex", "ELEM" +i + "filePointer", "ELEM" +i +"extra1", "ELEM" +i +"extra2", "ELEM" +i +"extra3", i*100, i*900));
//}
//System.out.println(bpo.getElementFunctions().getElement("new nud", "New Nodex","work"));
//System.out.println(bpo.getExceptionNodeFunctions().moveElementToException("new nud", "New Nodex", "code"));
	//bpo.getExceptionNodeFunctions().moveElementToException("the node", "New Nodex", "code");
//System.out.println(bpo
//		.getExceptionNodeFunctions()
//		.insertExceptionNode("New Nodex","code8", "exceptionname7",90,90));
int i = 0;
System.out.println(bpo.getElementFunctions().insertElement("kk", 0, "New Nodex", "ELEM" +i + "filePointer", "ELEM" +i +"extra1", "ELEM" +i +"extra2", "ELEM" +i +"extra3", i*100, i*900));

	
	}

}
