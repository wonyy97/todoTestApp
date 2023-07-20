package com.green.todotestapp;

import com.green.todotestapp.model.TodoInsDto;
import com.green.todotestapp.model.TodoInsParam;
import com.green.todotestapp.model.TodoRes;
import com.green.todotestapp.model.TodoVo;
import com.green.todotestapp.utils.MyFileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Slf4j
@Service
public class TodoServiceImpl implements TodoService { //메소드 오버라이딩 하지 않으면 빨간줄 뜬다 부모는 부모역할만 할 수 있기 때문에?????

    private final TodoMapper MAPPER;
    private final String FILE_DIR;

    @Autowired
    public TodoServiceImpl(TodoMapper MAPPER, @Value("${file.dir}") String fileDir) {
        this.MAPPER = MAPPER;
        this.FILE_DIR = MyFileUtils.getAbsolutePath(fileDir);
    }

    @Override
    public TodoRes insTodo(TodoInsParam p) {
//        File tempDic = new File(FILE_DIR + "/temp"); 밑에꺼랑 같은 말
        File tempDic = new File(FILE_DIR, "/temp");
        if (!tempDic.exists()) {
            tempDic.mkdirs();
        }

        //저장용 파일명
        String savedFileNm = MyFileUtils.makeRandomFileNm(p.getPic().getOriginalFilename());

        File tempFile = new File(tempDic.getPath(), savedFileNm);
        try {
            p.getPic().transferTo(tempFile);
        } catch(Exception e) {
            e.printStackTrace();
        }


        TodoInsDto dto = new TodoInsDto();
        dto.setCtnt(p.getCtnt());
        dto.setPic(savedFileNm);

        int result = MAPPER.insTodo(dto);
        if (result == 1) {
            //파일 이동
            String targetDicPath = FILE_DIR + "/todo/" + dto.getItodo();
            File targetDic = new File(targetDicPath);
            if (!targetDic.exists()) {
                targetDic.mkdirs();
            }

            File targetFile = new File(targetDicPath, savedFileNm);
            tempFile.renameTo(targetFile); //이동
            return new TodoRes(dto);

        }
        return null;
    }

    @Override
    public List<TodoVo> selTodo() {
        return MAPPER.selTodo();
    }
}
