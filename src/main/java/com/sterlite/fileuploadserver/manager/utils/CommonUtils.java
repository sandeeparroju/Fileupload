package com.sterlite.fileuploadserver.manager.utils;

import java.security.SecureRandom;
import java.util.TreeMap;

public class CommonUtils {

	public static TreeMap<String, String> CUST_STATUS_MAP = null;
	public static TreeMap<String, String> CG_STATUS_MAP = null;
	public static TreeMap<String, String> ENTITY_STATUS_MAP = null;	
	public static TreeMap<String, String> STATUS_MAP = null;	
	public static TreeMap<String, String> PERMISSION_MAP = null;	
	public static TreeMap<String, String> ACCTPROFILE_STATUS_MAP = null;
    public static TreeMap<String,String> PLAN_TYPE_MAP=null;
	static final String NUMERIC = "0123456789";
	static final String UPPERCASE="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	static final String LOWERCASE= "abcdefghijklmnopqrstuvwxyz";
	public static TreeMap<String,String> AUTHDRIVER_TYPE_MAP=null;

	public static TreeMap<String,String> LDAP_AUTHTYPE_MAP=null;

	public static TreeMap<String, String> IP_TYPE_MAP = null;
	public static String getResponse(String flag1,String flag2,String flag3,Integer length)
	{
		String flag="";
		if(flag1!=null)
		{
			flag+=NUMERIC;
		}
		if(flag2!=null)
		{
			flag+=UPPERCASE;
		}
		if(flag3!=null)
		{
			flag+=LOWERCASE;
		}

		return randomString(length,flag);
	}
	static SecureRandom secureRnd = new SecureRandom();
	public static String randomString(int length,String flag) {
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++)
			sb.append(flag.charAt(secureRnd.nextInt(flag.length())));
		return sb.toString();
	}
	public static TreeMap<String, String> getCustStatusMap(){
		if(CUST_STATUS_MAP==null){
			CUST_STATUS_MAP = new TreeMap<>();
			CUST_STATUS_MAP.put("ACTIVE", "Active");
			CUST_STATUS_MAP.put("INACTIVE", "In Active");
			CUST_STATUS_MAP.put("BLOCK", "Blocked");
		}
		return CUST_STATUS_MAP;
	}

    public static TreeMap<String, String> getPlanTypeMap(){
        if(PLAN_TYPE_MAP==null){
            PLAN_TYPE_MAP = new TreeMap<>();
            PLAN_TYPE_MAP.put("TIME", "Time");
            PLAN_TYPE_MAP.put("VOLUME", "Volume");
        }
        return PLAN_TYPE_MAP;
    }
	
	public static TreeMap<String, String> getCGStatusMap(){
		if(CG_STATUS_MAP==null){
			CG_STATUS_MAP = new TreeMap<>();
			CG_STATUS_MAP.put("ACTIVE", "Active");
			CG_STATUS_MAP.put("INACTIVE", "In Active");
		}
		return CG_STATUS_MAP;
	}

	
	public static TreeMap<String, String> getIpTypeMap(){
		if(IP_TYPE_MAP==null){
			IP_TYPE_MAP = new TreeMap<>();
			IP_TYPE_MAP.put("IPv4", "IPv4");
			IP_TYPE_MAP.put("SUBNET", "SUBNET");
			IP_TYPE_MAP.put("IPv6", "IPv6");
		}
		return IP_TYPE_MAP;
	}
	
	public static TreeMap<String, String> getEntityStatusMap(){
		if(ENTITY_STATUS_MAP==null){
			ENTITY_STATUS_MAP = new TreeMap<>();
			ENTITY_STATUS_MAP.put("ACTIVE", "Active");
			ENTITY_STATUS_MAP.put("INACTIVE", "In Active");
		}
		return ENTITY_STATUS_MAP;
	}
	

	public static TreeMap<String, String> getPermissionMap(){
		if(PERMISSION_MAP==null){
			PERMISSION_MAP = new TreeMap<>();
			PERMISSION_MAP.put(CommonConstants.PERMISSION_NONE, "None");
			PERMISSION_MAP.put(CommonConstants.PERMISSION_READ, "Read");
			PERMISSION_MAP.put(CommonConstants.PERMISSION_WRITE, "Write");
			PERMISSION_MAP.put(CommonConstants.PERMISSION_DELETE, "Delete");			
		}
		return PERMISSION_MAP;
	}
	public static TreeMap<String, String> getStatusMap(){
		if(STATUS_MAP==null){
			STATUS_MAP = new TreeMap<>();
			STATUS_MAP.put("1", "Active");
			STATUS_MAP.put("0", "In Active");
		}
		return STATUS_MAP;
	}
	

	
}
