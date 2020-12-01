package com.sterlite.fileuploadserver.manager.controller.base;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.sterlite.fileuploadserver.manager.utils.CommonConstants;

@Controller
public class BaseController<T> {

	public void setPaginationParameters(String entity,String flashMsg,String search, Model model,Page<T> page){
		Integer dbPageSize = CommonConstants.DB_PAGE_SIZE;
		Integer dispPageSize = CommonConstants.DISP_PAGE_SIZE;		
		setPaginationParameters( entity, flashMsg, search,  model,page, dbPageSize,  dispPageSize);
		
	}
	public void setPaginationParameters(String entity,String flashMsg,String search, Model model,Page<T> page,Integer databasePageSize, Integer displayPageSize){
		
		Integer dbPageSize = databasePageSize;
		Integer dispPageSize = displayPageSize;

		int current = page.getNumber() + 1;
        int begin = Math.max(1, current - dispPageSize+1);
        int end = Math.min(begin +dispPageSize-1, page.getTotalPages());
        int next=-1;
        int prev=-1;
        
        if(end < page.getTotalPages()){
        	next=current+1;
        }        
        if(current >1){
        	prev=current-1;
        }
        
        if(end < begin){
        	end=begin;
        }
        
        long recordStart=(((current-1)*dbPageSize)+1) ;
        long recordEnd=current*dbPageSize;
        if(recordEnd > page.getTotalElements())
        	recordEnd=page.getTotalElements();
        
        String recordMsg="Showing " + recordStart + " to " + recordEnd + " Entities of "+ page.getTotalElements(); 
        model.addAttribute("list", page);
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);
        model.addAttribute("nextIndex", next);
        model.addAttribute("prevIndex", prev);        
        model.addAttribute("lastIndex", ((page.getTotalPages()==0)?1:page.getTotalPages()));
        model.addAttribute("recordMsg", recordMsg);
        model.addAttribute("searchtext", search);
        if(page.getTotalElements()==0){
        	model.addAttribute("norecords", "false");
        	model.addAttribute("noRecordMsg", "No Records Found.");
        }
        
        if(search!=null && !"".equalsIgnoreCase(search)){
        	model.addAttribute("search","?s="+search);	
    	}else{
    		model.addAttribute("search","");
    	}

        
        //flashMsg="error";
        
        if(flashMsg !=null && !"".equalsIgnoreCase(flashMsg)){
        	if(flashMsg.equalsIgnoreCase("AddSuccess")){
                model.addAttribute("successFlash", entity+ " Added Successfully");
        	}else if (flashMsg.equalsIgnoreCase("DelSuccess")){
                model.addAttribute("successFlash", entity+" Deleted Successfully");
        	}else if (flashMsg.equalsIgnoreCase("EditSuccess")){
                model.addAttribute("successFlash", entity+" Updated Successfully");
        	}else if (flashMsg.equalsIgnoreCase("sucess")){
        		model.addAttribute("successFlash", "Opereation Performed Successfully");
        	}else if (flashMsg.equalsIgnoreCase("error")){
                model.addAttribute("errorFlash", "Error performing operation, Please try again later");

        	}
        }
	}
}
