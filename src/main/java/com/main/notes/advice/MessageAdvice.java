package com.main.notes.advice;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.main.notes.exception.MessageNotFoundException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.view.RedirectView;

@ControllerAdvice
public class MessageAdvice {
    @ExceptionHandler
    public ResponseEntity<String> onMessageNotFound(MessageNotFoundException message)
    {
        return new ResponseEntity<String>(message.getError(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public RedirectView onUserExists(MySQLIntegrityConstraintViolationException ex)
    {
        return new RedirectView("/notes/register?userExists");
    }

    @ExceptionHandler
    public RedirectView onTransactionRollback(UnexpectedRollbackException ex)
    {
        System.out.println(ex.getMessage());
        return new RedirectView("/notes/error");
    }

    @ExceptionHandler
    public RedirectView onFileSizeExceeded(SizeLimitExceededException ex)
    {
        return new RedirectView("/notes/panel?fileSizeExceeded");
    }

    @ExceptionHandler
    public RedirectView onFileSizeExceeded(MaxUploadSizeExceededException ex)
    {
        return new RedirectView("/notes/panel?fileSizeExceeded");
    }
}
