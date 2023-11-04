package com.example.todo;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class TodoService {

    @Autowired
    private  TodoRepository todoRepository;




    public String testService(){
        //TodoEntity 생성
        TodoEntity entity = TodoEntity.builder().title("My first todo item").build();
        //TodoEntity 저장
        todoRepository.save(entity);
        //TodoEntity 검색
        TodoEntity savedEntity = todoRepository.findById(entity.getId()).get();

        return savedEntity.getTitle();
    }

    @Transactional
    public List<TodoEntity> create(final TodoEntity entity){
        //validations
        if(entity == null){
            log.warn("Entity cannot be null");
            throw new RuntimeException("Entity cannot be null");
        }

        if(entity.getUserId() == null){
            log.warn("Unkown User");
            throw new RuntimeException("UnKown User");
        }

        todoRepository.save(entity);

        log.info("Entity Id : {} is saved.", entity.getId());

        return todoRepository.findByUserId(entity.getUserId());
    }

    private void validate(final TodoEntity entity){
        if(entity == null){
            log.warn("Entity cannot be null");
            throw new RuntimeException("Entity cannot be null.");
        }

        if(entity.getUserId() == null){
            log.warn("Unkown User");
            throw new RuntimeException("Unkown User");
        }
    }

    public List<TodoEntity> retrieve (final String userId){
        return todoRepository.findByUserId(userId);
    }

    public List<TodoEntity> retrieveName (final String name) {return todoRepository.findByName(name);}


}
