package application.controllers.user;

import application.dao.game.ScoreDaoJpa;
import application.exceptions.user.UserException;
import application.models.game.field.Score;
import application.models.user.UserSignIn;
import application.models.user.UserSignUp;
import application.models.user.UserUpdate;
import application.services.user.UserService;
import application.services.user.UserServiceJpa;
import application.views.user.UserFail;
import application.views.user.UserSuccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;


@RestController
@RequestMapping(path = "/user")
@CrossOrigin(origins = "*")
public class UserHttpController {

    private final UserService userService;
    private final ScoreDaoJpa scoreService;
    private final Logger httpLogger = LoggerFactory.getLogger(UserHttpController.class);

    public UserHttpController(UserService userService,
                              ScoreDaoJpa scoreService) {
        this.userService = userService;
        this.scoreService = scoreService;
    }

    @GetMapping(path = "/whoisit")
    public ResponseEntity whoisit(HttpSession httpSession) {
        final Long id = (Long) httpSession.getAttribute("ID");
        if (id == null) {
            return new ResponseEntity<>(
                    new UserFail("Need to sign up"),
                    HttpStatus.UNAUTHORIZED
            );
        }
        return new ResponseEntity<>(
                new UserSuccess(userService.getUserById(id)),
                HttpStatus.OK
        );
    }

    @GetMapping(path = "/exit")
    public ResponseEntity exit(HttpSession httpSession) {
        httpSession.invalidate();
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        return new ResponseEntity(httpHeaders, HttpStatus.OK);
    }

    @PostMapping(path = "/update", consumes = "application/json")
    public ResponseEntity settings(
            @RequestBody UserUpdate userUpdate,
            HttpSession httpSession) {

        final Long id = (Long) httpSession.getAttribute("ID");
        if (id == null) {
            return new ResponseEntity<>(
                    new UserFail("Need to sign up"),
                    HttpStatus.UNAUTHORIZED
            );
        }
        final UserSignUp userUpdated = userService.updateUserProfile(userUpdate, id);
        httpSession.setAttribute("ID", userUpdated.getId());
        return new ResponseEntity<>(
                new UserSuccess(userUpdated),
                HttpStatus.OK
        );
    }

    @PostMapping(path = "/sign_up", consumes = "application/json")
    public ResponseEntity signUp(
            @RequestBody UserSignUp user,
            HttpSession httpSession) {

        httpSession.setAttribute("ID", userService.addUser(user));
        return new ResponseEntity<>(
                new UserSuccess(user),
                HttpStatus.CREATED
        );
    }

    @PostMapping(path = "/sign_in", consumes = "application/json")
    public ResponseEntity signIn(
            @RequestBody UserSignIn parseBody,
            HttpSession httpSession) {

        final UserSignUp user = userService.signInByLogin(
                parseBody.getLogin(),
                parseBody.getPassword()
        );
        httpSession.setAttribute("ID", user.getId());
        return new ResponseEntity<>(
                new UserSuccess(user),
                HttpStatus.OK
        );
    }

    @GetMapping(path = "/score")
    public ResponseEntity score(@RequestParam(
            name = "limit", required = false, defaultValue = "10") Integer limit) {
        return new ResponseEntity<>(
                scoreService.getBestScores(limit),
                HttpStatus.OK
        );
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<UserFail> handleUserServiceError(UserException exception) {
        exception.printStackTrace();
        httpLogger.error(exception.getErrorMessage());
        return new ResponseEntity<>(
                new UserFail(exception.getErrorMessage()),
                exception.getErrorCode()
        );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<UserFail> handleUnexpectedException(RuntimeException exception) {
        exception.printStackTrace();
        httpLogger.error("Unexpected error:" + exception.getMessage());
        return new ResponseEntity<>(
                new UserFail("Unexpected error"),
                HttpStatus.I_AM_A_TEAPOT
        );
    }
}