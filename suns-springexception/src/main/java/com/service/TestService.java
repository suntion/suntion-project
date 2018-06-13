package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.TestDao;

@Service
public class TestService{

	@Autowired  
    private TestDao testDao;
      
    public void exception(Integer id) throws Exception {  
    	
   
    }  
  
    public void dao(Integer id) throws Exception {  
        testDao.exception(id);  
    }

}
