package ukim.mk.finki.konstantin.bogdanoski.wp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class UnauthorizedAdminDeletionException extends RuntimeException {
}
