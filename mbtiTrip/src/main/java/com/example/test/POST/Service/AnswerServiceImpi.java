package com.example.test.POST.Service;





import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.test.POST.DAO.AnswerDAO;
import com.example.test.POST.DTO.AnswerDTO;
import com.example.test.POST.DTO.PostDTO;
import com.example.test.User.DTO.UserDTO;


@Service
public  class AnswerServiceImpi implements AnswerService {

	@Autowired
	AnswerDAO answerDAO;

	@Override
	public AnswerDTO create(PostDTO post, String content, UserDTO writer) {
		 	AnswerDTO answerDto = new AnswerDTO();
	        answerDto.setContent(content);
	        answerDto.setUpdateDate(LocalDateTime.now());
	        answerDto.setPostID(post);;
	        answerDto.setWriter(writer);
	        
	        return this.answerDAO.save(answerDto);
	}

	@Override
	public AnswerDTO getAnswer(Integer answerID) {
		Optional<AnswerDTO> answer = this.answerDAO.findById(answerID);
		if(answer.isPresent()) {
			return answer.get();
		}
		return null;
	}

	@Override
	public AnswerDTO modify(AnswerDTO answerDto, String content) {
		answerDto.setContent(content);
		answerDto.setModifyDate(LocalDateTime.now());
		return this.answerDAO.save(answerDto);
	}

	@Override
	public void delete(AnswerDTO answerDto) {
		this.answerDAO.delete(answerDto);
	}


	
	
	
}
