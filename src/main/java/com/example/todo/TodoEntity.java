package com.example.todo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Builder            // 즉 클래스 레벨에서 @Builder 어노테이션을 붙이면 모든 요소를 받는 package-private 생성자가 자동으로 생성되며 이 생성자에 @Builder 어노테이션을 붙인 것과 동일하게 동작한다고 한다. 즉 클래스 레벨도 결국은 중간 단계를 거쳐 생성자 레벨로 변환되어 동작한다는 것이다.
@NoArgsConstructor  //파라미터가 없는 기본 생성자 생성
@AllArgsConstructor //전체 변수를 생성하는 생성자를 만들어준다.
@Data               //@Getter, @Setter, @ToString등이 있는 것
@Entity
public class TodoEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)     //ID를 자동으로 생성하겠다는 뜻
        private String id;      //이 오브젝트의 아이디
        private String userId;  //이 오브젝트를 생성한 유저의 아이디
        private String title;   //예) 운동하기
        private boolean done;   //할일을 완료한 경우 checked



}
