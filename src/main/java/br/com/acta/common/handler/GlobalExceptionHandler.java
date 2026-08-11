package br.com.acta.common.handler;

import br.com.acta.common.handler.exception.*;
import com.google.i18n.phonenumbers.NumberParseException;
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
    @ExceptionHandler(CircularDependencyException.class)
    public ResponseEntity<ErroResponse> handleCircularDependency(CircularDependencyException cde){
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT)
                .body(new ErroResponse(List.of(cde.getMessage()), 422));
    }

    @ExceptionHandler(ForbiddenOperationException.class)
    public ResponseEntity<ErroResponse> handleForbiddenOperation(ForbiddenOperationException foe){
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ErroResponse(List.of(foe.getMessage()), 403));
    }

    @ExceptionHandler(InvalidRelationshipException.class)
    public ResponseEntity<ErroResponse> handleInvalidRelationship(InvalidRelationshipException ire){
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT)
                .body(new ErroResponse(List.of(ire.getMessage()), 422));
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ErroResponse> handleInvalidRequest(InvalidRequestException ire){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErroResponse(List.of(ire.getMessage()), 400));
    }

    @ExceptionHandler(InvalidResourceStatusException.class)
    public ResponseEntity<ErroResponse> handleInvalidResourceStatus(InvalidResourceStatusException irse){
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT)
                .body(new ErroResponse(List.of(irse.getMessage()), 422));
    }

    @ExceptionHandler(PrerequisiteNotMetException.class)
    public ResponseEntity<ErroResponse> handlePrerequisiteNotMet(PrerequisiteNotMetException pnme){
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT)
                .body(new ErroResponse(List.of(pnme.getMessage()), 422));
    }

    @ExceptionHandler(ResourceInUseException.class)
    public ResponseEntity<ErroResponse> handleResourceInUse(ResourceInUseException riue){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErroResponse(List.of(riue.getMessage()), 409));
    }

    @ExceptionHandler(ImmutableFieldException.class)
    public ResponseEntity<ErroResponse> handleImmutableField(ImmutableFieldException ife){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErroResponse(List.of(ife.getMessage()), 400));
    }

    @ExceptionHandler(ModelNotFoundException.class)
    public ResponseEntity<ErroResponse> handleModelNotFound(ModelNotFoundException mnfe){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErroResponse(List.of(mnfe.getMessage()), 404));
    }

    @ExceptionHandler(RegexException.class)
    public ResponseEntity<ErroResponse> handleRegex(RegexException re){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErroResponse(List.of(re.getMessage()), 400));
    }

    @ExceptionHandler(InexistentFieldException.class)
    public ResponseEntity<ErroResponse> handleInexistentField(InexistentFieldException ife){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErroResponse(List.of(ife.getMessage()), 400));
    }

    @ExceptionHandler(ActiveEntityDeletionException.class)
    public ResponseEntity<ErroResponse> handleActiveEntityDeletion(ActiveEntityDeletionException ad){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErroResponse(List.of(ad.getMessage()), 400));
    }

    @ExceptionHandler(NumberParseException.class)
    public ResponseEntity<ErroResponse> handleNumberParseException(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErroResponse(List.of("O número informado é inválido"), 400));
    }

    @ExceptionHandler(UniqueViolationException.class)
    public ResponseEntity<ErroResponse> handleUniqueViolation(UniqueViolationException uv){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErroResponse(List.of(uv.getMessage()), 400));
    }

    @ExceptionHandler(StatusUpdateException.class)
    public ResponseEntity<ErroResponse> handleStatusUpdate(StatusUpdateException sue){
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT)
                .body(new ErroResponse(List.of(sue.getMessage()), 422));
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<ErroResponse> handleBusinessRule(BusinessRuleException bre){
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT)
                .body(new ErroResponse(List.of(bre.getMessage()), 422));
    }

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

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErroResponse> handleIllegalState(IllegalStateException ise){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErroResponse(List.of(ise.getMessage()), 400));
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
                .body(new ErroResponse(List.of("O tipo de conteúdo enviado não é suportado"), 415));
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
