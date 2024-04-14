package com.example.test.Adventure.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.test.POST.DTO.PostDTO;
import com.example.test.POST.Service.DataNotFoundException;
import com.example.test.User.DTO.AdminDTO;
import com.example.test.User.DTO.UserDTO;
import com.example.test.User.Service.UserHistoryService;
import com.example.test.item.ItemType;
import com.example.test.item.DAO.ItemDAO;
import com.example.test.item.DTO.ItemDTO;
import com.example.test.paging.Criteria;
import com.example.test.paging.Page;
import com.example.testExcepion.Item.ItemException;
import com.example.testExcepion.Item.ItemExceptionEnum;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import java.util.regex.Pattern;

@Service
public class AdventureServiceImpl implements AdventureService{

	@Autowired
	ItemDAO itemDAO;

	@Autowired
	UserHistoryService userHistoryService;
	
	
	@Override
	public List<ItemDTO> list(Page page) throws Exception {
		// TODO Auto-generated method stub
		return itemDAO.adventureList(page);
	}

	@Override
	public void create(ItemDTO post) throws Exception {
		validationItem(post);
		itemDAO.create(post);
	}

	@Override
	public ItemDTO getPost(Integer itemId) throws Exception {
		Optional<ItemDTO> adventure = this.itemDAO.findById(itemId);
		  if (adventure.isPresent()) {
	        	ItemDTO itemDto = adventure.get();        	
	        	itemDto.setView(itemDto.getView()+1);        	
	        	this.itemDAO.create(itemDto);
	        	
	            	return itemDto;
	        } else {
	            throw new DataNotFoundException("question not found");
	        }	
	}

	@Override
	public void modify(ItemDTO post) throws Exception {
		// TODO Auto-generated method stub
		itemDAO.update(post);
	}

	@Override
	public void remove(Integer itemId) throws Exception {
		// TODO Auto-generated method stub
		itemDAO.deleteItem(itemId);
	}

	@Override
	public List<ItemDTO> search(String keyword) {
		// TODO Auto-generated method stub
		return itemDAO.search(keyword);
	}
	
	

	@Override
	public List<ItemDTO> search(Page page) throws Exception {
		// TODO Auto-generated method stub
		return itemDAO.search(page);
	}

	@Override
	public Integer totalCount() throws Exception {
		// TODO Auto-generated method stub
		return itemDAO.getTotal();
	}

	@Override
	public void suggestion(ItemDTO item, UserDTO user) {
		item.getUprating().add(user);
        
        this.itemDAO.create(item);
	}

	public void validationItem(ItemDTO itemDTO) {
		//TITLE ck
		if(itemDTO.getItemName() == null) {
			throw new ItemException(ItemExceptionEnum.ITEM_TITLE_MISMATCH);
		}
		if(itemDTO.getItemName().length()>20) {
			throw new ItemException(ItemExceptionEnum.ITEM_TITLE_SIZEMISS);
		}
		if(TitleCk(itemDTO)) {
			throw new ItemException(ItemExceptionEnum.ITEM_TITLE_VALIDTION);
		}
		
		if(itemDTO.getType() == null) {
			throw new ItemException(ItemExceptionEnum.ITEM_INFORMATION_MISSING);	
		}
		if(itemDTO.getUsername() == null) {
			throw new ItemException(ItemExceptionEnum.ITEM_USER_NOT_FOUND);
		}
		if(itemDTO.getPrice() == null) {
			throw new ItemException(ItemExceptionEnum.ITEM_INFORMATION_MISSING);	
		}
		if(itemDTO.getLocation() == null) {
			throw new ItemException(ItemExceptionEnum.ITEM_INFORMATION_MISSING);	
		}
		if(itemDTO.getTel() == null) {
			throw new ItemException(ItemExceptionEnum.ITEM_INFORMATION_MISSING);	
		}
		if(itemDTO.getContents() == null) {
			throw new ItemException(ItemExceptionEnum.ITEM_INFORMATION_MISSING);	
		}
		if(itemDTO.getContents().length() > 1000) {
			throw new ItemException(ItemExceptionEnum.ITEM_CONTENTS_SIZEMISS);
		}
	}
	
	
	public boolean TitleCk(ItemDTO itemDTO) {
		String itemName = itemDTO.getItemName();
		boolean valid = Pattern.matches("^[a-zA-Z0-9가-힣]*$", itemName);
		
		return !valid;
		
	}

//			// 게시물을 조회하고 조회수 증가
//			@Transactional
//			public ItemDTO detail(Integer itemID, HttpServletRequest request, HttpServletResponse response) {
//				 Cookie oldCookie = null;
//				    Cookie[] cookies = request.getCookies();
//				    if (cookies != null)
//				        for (Cookie cookie : cookies)
//				            if (cookie.getName().equals("ReplaceView"))
//				                oldCookie = cookie;
//
//				    if (oldCookie != null) {
//				        if (!oldCookie.getValue().contains("[" + itemID.toString() + "]")) {
//				            itemDAO.updateCount(itemID);
//				            oldCookie.setValue(oldCookie.getValue() + "_[" + itemID + "]");
//				            oldCookie.setPath("/");
//				            oldCookie.setMaxAge(60 * 60 * 24);
//				            response.addCookie(oldCookie);
//				        }
//				    }
//				    else {
//				        itemDAO.updateCount(itemID);
//				        Cookie newCookie = new Cookie("ReplaceView","[" + itemID + "]");
//				        newCookie.setPath("/");
//				        newCookie.setMaxAge(60 * 60 * 24);
//				        response.addCookie(newCookie);
//				    }
//
//				    return itemDAO.findById(itemID).orElseThrow(() -> {
//				        return new IllegalArgumentException("글 상세보기 실패: 아이디를 찾을 수 없습니다.");
//				    });
//				}

			

		
		

			
	
	
}
