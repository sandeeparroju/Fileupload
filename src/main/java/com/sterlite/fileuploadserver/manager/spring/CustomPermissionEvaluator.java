package com.sterlite.fileuploadserver.manager.spring;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class CustomPermissionEvaluator implements PermissionEvaluator{

	@Override
	public boolean hasPermission(Authentication auth, Object targetDomainObject, Object permission) {
		if ((auth == null) || (targetDomainObject == null) || !(permission instanceof String)){
				return false;
		}
		String targetType = targetDomainObject.toString();
		return hasPrivilege(auth,targetType,permission.toString().toUpperCase());
	}
	@Override
	public boolean hasPermission(Authentication auth, Serializable targetId, String targetType, Object permission) {
		if ((auth == null) || (targetType == null) || !(permission instanceof String)) {
			return false;
		}
		return hasPrivilege(auth, targetType.toUpperCase(), permission.toString().toUpperCase());
	}
	
	private boolean hasPrivilege(Authentication auth, String targetType, String permission) {
		
		boolean isPriviledged=false;
		boolean isUnmappedModule=false;
		CacheManager cacheManager = CacheManager.getInstance();
		Cache aclCache = cacheManager.getCache("aclCache");			
		if(aclCache !=null){
			Element element = aclCache.get(targetType);
			if(element !=null){
				ConcurrentHashMap<String, Integer> rolePermitMap=(ConcurrentHashMap<String, Integer>) element.getObjectValue();
				if(rolePermitMap.size() > 0){
					for (GrantedAuthority grantedAuth : auth.getAuthorities()) {
						if (rolePermitMap.containsKey(grantedAuth.getAuthority())) {
							Integer permit = rolePermitMap.get(grantedAuth.getAuthority());
							if (permit >= Integer.parseInt(permission)) {
								System.out.print("Authoirzed against role:" +grantedAuth.getAuthority() + ",Required:" +permission + ",Granted:"+ permit);
								isPriviledged=true;
								break;
							}
						}
					}
				}
			}else{
				isUnmappedModule=true;
			}
		}		
		if(!isPriviledged && isUnmappedModule){
			//This is newly added module for which there are no mapping hence full access given.
			isPriviledged=true;
		}
		return isPriviledged;
	}
}



