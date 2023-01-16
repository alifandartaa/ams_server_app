package mii.mcc72.ams_server_app.exception;

import mii.mcc72.ams_server_app.models.dto.ResponseData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class FileUploadExceptionAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ResponseData<Object>> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        ResponseData<Object> responseData = new ResponseData<>();
        responseData.setStatus(true);
        String message = "File too large!";
        responseData.setPayload(message);
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseData);
    }
}
