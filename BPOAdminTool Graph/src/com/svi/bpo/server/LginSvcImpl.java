package com.svi.bpo.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.prefs.Preferences;

import org.ini4j.Ini;
import org.ini4j.IniPreferences;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.svi.bpo.client.svc.LginSvc;
import com.svi.bpo.constants.BPOCnstnts;
import com.svi.bpo.objects.BpoDtlsObj;
import com.svi.bpo.objects.EndpointObj;
import com.svi.bpo.objects.SSOInfoDto;
import com.svi.bpo.objects.UserAcctObj;

@SuppressWarnings("serial")
public class LginSvcImpl extends RemoteServiceServlet implements LginSvc{

	private String BPO_PROP_PATH = "/config/BPOConfig.properties";

	@Override
	public UserAcctObj validateCrdntls(String user, String pass) {
		UserAcctObj acct = new UserAcctObj();
		
		if (Controller.userMap.containsKey(user)) {
			if (Controller.userMap.get(user).trim().equals(pass)) {
				acct = retrieveAcct(user);
			} else {
				acct.setLoginMsg("Password Invalid");
			}
		} else {
			acct.setLoginMsg("User Invalid");
		}
		
		acct.setUsername(user);
		acct.setPassword(pass);
		
		return acct;
	}
	
	@Override
	public UserAcctObj validateCrdntlsWithSSO(String user) {
		UserAcctObj acct = new UserAcctObj();
		
		if (Controller.userMap.containsKey(user)) {
				acct = retrieveAcct(user);
		} else {
			acct.setLoginMsg("User Invalid");
		}
		
		acct.setUsername(user);
		
		return acct;
	}
	
	private UserAcctObj retrieveAcct(String user) {
		System.out.println("Retrieve Account : " + user + " ...");
		System.out.println("[" + this.getServletContext().getRealPath(Controller.bpoCfgDetails.getBpoAcctPath() + user + ".txt") + "]");
		
		UserAcctObj acct = new UserAcctObj();
		Properties props = new Properties();
		try {
			props.load(new FileInputStream(this.getServletContext().getRealPath(Controller.bpoCfgDetails.getBpoAcctPath() + user + ".txt")));
		
			String[] bits = props.getProperty(BPOCnstnts.USER_ENDPOINTS.getValue()).split(",");
			
			for (String endpnt : bits) {
				acct.getEndpoints().add(new EndpointObj(endpnt, Controller.endpointLabels.get(endpnt)));
			}
			
			acct.setLoginMsg("Valid");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			acct.setLoginMsg("Error on Retrieving User Account");
		} catch (IOException e) {
			e.printStackTrace();
			acct.setLoginMsg("Error on Retrieving User Account");
		}
		
		return acct;
	}

	@Override
	public String getScnToken(String type) {
		
		
		return null;
	}
	
	@Override
	public BpoDtlsObj getBpoDtls() {
		Properties props = new Properties();
		
		try {
			props.load(new FileInputStream(this.getServletContext().getRealPath(BPO_PROP_PATH)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Controller.bpoCfgDetails.setEndpoint(props.getProperty(BPOCnstnts.ENDPOINT.getValue()));
		Controller.bpoCfgDetails.setBpoAcctPath(props.getProperty(BPOCnstnts.BPO_ACCOUNT_PATH.getValue()));
		Controller.bpoCfgDetails.setBpoEndpntPath(props.getProperty(BPOCnstnts.BPO_ENDPOINT_PATH.getValue()));
		Controller.bpoCfgDetails.setMode(props.getProperty(BPOCnstnts.CLIENT_MODE.getValue()));
		
		populateUserMap(props.getProperty(BPOCnstnts.BPO_ACCOUNT_PATH.getValue()));
		populateEndpointMap(props.getProperty(BPOCnstnts.BPO_ENDPOINT_PATH.getValue()));
		
		System.out.println(Controller.userMap);
		System.out.println(Controller.endpointMap);
		System.out.println(Controller.bpoCfgDetails.getBpoEndpntPath());
		
		return Controller.bpoCfgDetails;
	}

//	private void populateEndpointMap(String path) {
//		System.out.println("Populate EndpointMap...");
//		System.out.println("[" + this.getServletContext().getRealPath(path + "Endpoints.map") + "]");
//		try {
//			BufferedReader reader = new BufferedReader(new FileReader(this.getServletContext().getRealPath(path + "Endpoints.map")));
//			String line;
//			while((line = reader.readLine()) != null) {
//				String[] bits = line.trim().split("\\|\\|");
//				Controller.endpointMap.put(bits[0], bits[1]);
//				Controller.endpointLabels.put(bits[0], bits[2]);
//			}
//		
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
	private void populateEndpointMap(String path) {
		System.out.println("Populate EndpointMap...");
		System.out.println("[" + this.getServletContext().getRealPath(path + "Endpoints.map") + "]");
		try {
			Preferences pfrns = new IniPreferences(new Ini(new File(this.getServletContext().getRealPath(path + "Endpoints.map"))));

			for (String node : pfrns.childrenNames()) {
				for (String key : pfrns.node(node).keys()) {
					if (key.equals(BPOCnstnts.ENDPOINT.getValue())) {
						Controller.endpointMap.put(node, pfrns.node(node).get(key, null));
						
					}else if (key.equals(BPOCnstnts.ENDPOINT_LABEL.getValue())) {
						Controller.endpointLabels.put(node, pfrns.node(node).get(key, null));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//@SuppressWarnings("resource")
	private void populateUserMap(String path) {
		System.out.println("Populate UserMap...");
		System.out.println("[" + this.getServletContext().getRealPath(path + "UserAccount.map") + "]");
		try {
			BufferedReader reader = new BufferedReader(new FileReader(this.getServletContext().getRealPath(path + "UserAccount.map")));
			String line;
			while((line = reader.readLine()) != null) {
				String[] bits = line.split("\\|\\|");
				Controller.userMap.put(bits[0], bits[1]);
			}
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public SSOInfoDto getSsoInfo() {
		SSOInfoDto ssoInfo = new SSOInfoDto();
		Properties props = new Properties();
		
		try {
			props.load(new FileInputStream(this.getServletContext().getRealPath(BPO_PROP_PATH)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String isSsoStrng = props.getProperty(BPOCnstnts.SSO.getValue());
		String ssoPrtlUrl = props.getProperty(BPOCnstnts.SSO_PORTAL.getValue());
		String appNm = props.getProperty(BPOCnstnts.APP_NAME.getValue());
		
		if (isSsoStrng.equalsIgnoreCase("Y")) {
			ssoInfo.setSSO(true);
		}else{
			ssoInfo.setSSO(false);
		}
		ssoInfo.setSsoPrtlUrl(ssoPrtlUrl);
		ssoInfo.setAppNm(appNm);
		ssoInfo.setDisconnected(false);
			
		return ssoInfo;
	}
}
