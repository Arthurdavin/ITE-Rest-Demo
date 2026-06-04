package co.istad.productapisimpledemo.advisor;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestControllerAdvisor {

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public Map<String, String> handleMethodValidException(MethodArgumentNotValidException exception) {
//        Map<String, String> errors = new HashMap<>();
//        exception.getBindingResult().getFieldErrors().forEach(
//                error ->
//                        errors.put(error.getField(), error.getDefaultMessage())
//
//        );
//        return errors;
//    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleMethodValiException(MethodArgumentNotValidException e){
        Map<String,String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(
                error->errors.put(error.getField(),error.getDefaultMessage())
        );
        return errors;
    }

}
