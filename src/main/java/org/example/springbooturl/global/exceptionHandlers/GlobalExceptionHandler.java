package org.example.springbooturl.global.exceptionHandlers;

import lombok.RequiredArgsConstructor;
import org.example.springbooturl.domain.standard.dto.Empty;
import org.example.springbooturl.global.exception.GlobalException;
import org.example.springbooturl.global.rq.Rq;
import org.example.springbooturl.global.rsData.RsData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final Rq rq;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        // if (!rq.isApi()) throw ex;

        return handleApiException(ex);
    }

    // 자연스럽게 발생시킨 예외처리
    private ResponseEntity<Object> handleApiException(Exception ex) {
        Map<String, Object> body = new LinkedHashMap<>();

        if (ex instanceof AccessDeniedException) {
            body.put("resultCode", "403-1");
            body.put("statusCode", 403);
        } else {
            body.put("resultCode", "500-1");
            body.put("statusCode", 500);
        }
        body.put("msg", ex.getLocalizedMessage());

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        body.put("data", data);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        data.put("trace", sw.toString().replace("\t", "    ").split("\\r\\n"));

        String path = rq.getCurrentUrlPath();
        data.put("path", path);

        body.put("success", false);
        body.put("fail", true);

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 개발자가 명시적으로 발생시킨 예외처리
    @ExceptionHandler(GlobalException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 참고로 이 코드의 역할은 error 내용의 스키마를 타입스크립트화 하는데 있다.
    @RequestMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<RsData<Empty>> handle(GlobalException ex) {
        HttpStatus status = HttpStatus.valueOf(ex.getRsData().getStatusCode());
        rq.setStatusCode(ex.getRsData().getStatusCode());

        return new ResponseEntity<>(ex.getRsData(), status);
    }
}
