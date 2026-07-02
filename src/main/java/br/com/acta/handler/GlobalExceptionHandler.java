package br.com.acta.handler;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.nio.file.AccessDeniedException;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponse> handleValidation(MethodArgumentNotValidException manve){
        List<String> mensagens = manve.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErroResponse(mensagens, 400));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErroResponse> handleConstraint(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErroResponse(List.of("Algum dos parâmetros informados é inválido"), 400));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErroResponse> handleValidation(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErroResponse(List.of("O corpo da requisição está inválido ou mal formado"), 400));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErroResponse> handleMissingParam(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErroResponse(List.of("Um parâmetro obrigatório não foi informado"), 400));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErroResponse> handleMethodMismatch(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErroResponse(List.of("Um parâmetro foi informado com o tipo inválido"), 400));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErroResponse> handleNoResourceFound(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErroResponse(List.of("O recurso solicitado não foi encontrado"), 404));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErroResponse> handleHttpRequest(){
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new ErroResponse(List.of("O método HTTP utilizado não é permitido para esta rota"), 405));
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErroResponse> handleHttpMedia(){
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .body(new ErroResponse(List.of("O tipo de conteúdo enviado é suportado"), 415));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErroResponse> handleDataIntegrity(){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErroResponse(List.of("Não foi possível realizar a operação por conflito com os dados existentes"), 409));
    }

    @ExceptionHandler(OptimisticLockingFailureException.class)
    public ResponseEntity<ErroResponse> handleOptimisticLocking(){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErroResponse(List.of("Este registro foi alterado por outra operação, tente novamente"), 409));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErroResponse> handleAccessDenied(){
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ErroResponse(List.of("Você não tem permissão de acesso para este recurso"), 403));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErroResponse> handleRuntime(){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErroResponse(List.of("Ocorreu um erro inesperado durante a execução"), 500));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResponse> handleException(){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErroResponse(List.of("Ocorreu um erro interno inesperado"), 500));
    }
}
