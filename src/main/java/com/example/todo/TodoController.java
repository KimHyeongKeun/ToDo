package com.example.todo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("todo")
public class TodoController {

    @Autowired
    private TodoService service;

    @Autowired
    private TodoRepository todoRepository;

    @GetMapping("/test")
    public ResponseEntity<?> TestTodo(){
       String str = service.testService();  //테스트 서비
        List<String> list = new ArrayList<>();
        list.add(str);
        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO dto){
        try{
            String tempoararyId = "temporory-user";     //temporary user id

            TodoEntity entity = TodoDTO.toEntity(dto);

            // (2) id를 null로 초기화한다. 생성 당시에는 id가 없어야한다.
            entity.setId(null);

            //  (3) 임시 유저 아이디를 설정해준다. 이 부분은 인증과 인가에서 수정할 예정. 지금은 인증과 인가 기능이 없으므로 한 유저만 로그인 없이 사용 가능한 애플리케이션인 셈이다.
            entity.setUserId(tempoararyId);

            List<TodoEntity> entities = service.create(entity);

            //  (5) 자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDto 리스트로 변환한다.
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

            //  (6) 변환된 TodoDTO 리스트를 이용해 ResponseDto를 초기화한다.
            ResponseDTO<TodoDTO> responseDTO = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            //  (7) ResponseDTO를 리턴한다.
            return ResponseEntity.ok().body(responseDTO);

//            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            //  (8)혹시 예외가 나는 경우 dto 대신 error에 메세지를 넣어서 리턶나다.
            String error = e.getMessage();
            ResponseDTO<TodoDTO> responseDTO = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }


    @GetMapping
    public ResponseEntity<?> retrieveTodoList(){
        String temporaryId = "tmeporory-user";

        List<TodoEntity> entities = service.retrieve(temporaryId);

        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

        return ResponseEntity.ok().body(response);
    }

}
