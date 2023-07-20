package com.green.todotestapp;

import com.green.todotestapp.model.TodoInsDto;
import com.green.todotestapp.model.TodoUpdDto;
import com.green.todotestapp.model.TodoVo;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
@ActiveProfiles("test") //yaml 파일에 test로 이름을 줬기 때문에 test로 해야함
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TodoMapperTest {

    @Autowired
    private TodoMapper mapper;

    //2차 프로젝트때 부터는 하나하나 다 꼼꼼하게 작성해라
    @Test
    void insTodo() {
        TodoInsDto p1 = new TodoInsDto();
        p1.setCtnt("테스트1");
        p1.setPic("main.jpg");

        int r1 = mapper.insTodo(p1);
        assertEquals(1, r1);
        assertEquals(5, p1.getItodo());

        // -------------------

        TodoInsDto p2 = new TodoInsDto();
        p2.setCtnt("테스트2");

        int r2 = mapper.insTodo(p2);
        assertEquals(1, r2);
        assertEquals(6, p2.getItodo());

        List<TodoVo> list = mapper.selTodo();
        assertEquals(6, list.size());

        TodoVo vo3 = list.get(4);
        assertEquals(p1.getCtnt(), vo3.getCtnt());
        assertEquals(p1.getPic(), vo3.getPic());

        TodoVo vo4 = list.get(5);
        assertEquals(p2.getCtnt(), vo4.getCtnt());

    }

    @Test
    public void selTodo() {
        List<TodoVo> list = mapper.selTodo();
        assertEquals(4, list.size());


        TodoVo vo = list.get(0);
        assertEquals(1, vo.getItodo());

        assertNotNull(vo.getCtnt());
        assertEquals("마트가서 아이스크림 사기", vo.getCtnt());

        assertNotNull(vo.getCreatedAt());
        assertEquals("2023-06-13T16:51:30", vo.getCreatedAt().toString());
        // 날짜와 시간을 구분해 주기 위해서 중간에 T가 들어간다

        // ----------------------

        TodoVo vo1 = list.get(1);
        assertEquals(2, vo1.getItodo());

        assertNotNull(vo1.getCtnt());
        assertEquals("땅콩이 놀아주기", vo1.getCtnt());

        assertNotNull(vo.getCreatedAt());
        assertEquals("2023-06-13T16:51:39", vo1.getCreatedAt().toString());
    }


    @Test
    public void updTodo() {
        TodoUpdDto dto = new TodoUpdDto();
        dto.setItodo(1L);
        dto.setCtnt("수정수정");

        int result = mapper.updTodo(dto);
        assertEquals(1, result);

        List<TodoVo> list = mapper.selTodo();
        TodoVo vo = list.get(0);

        assertEquals(dto.getCtnt(), vo.getCtnt());




    }
}