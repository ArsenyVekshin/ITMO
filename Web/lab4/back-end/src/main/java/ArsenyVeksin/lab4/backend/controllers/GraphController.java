package ArsenyVeksin.lab4.backend.controllers;


import ArsenyVeksin.lab4.backend.entities.Point;
import ArsenyVeksin.lab4.backend.exceptions.OutOfBoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ArsenyVeksin.lab4.backend.db.AuthTokenRepository;
import ArsenyVeksin.lab4.backend.db.PointRepository;
import ArsenyVeksin.lab4.backend.entities.AuthToken;
import static ArsenyVeksin.lab4.backend.utils.JsonUtil.*;

@RestController
public class GraphController {
    @Autowired
    private AuthTokenRepository authTokenRepository;

    @Autowired
    private PointRepository pointRepository;

    @GetMapping(value = "/point", produces = "application/json")
    public ResponseEntity<String> getPointsTable(HttpServletRequest request) {
        AuthToken token = authTokenRepository.findByToken(request.getHeader("auth-token"));

        if (token == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(jsonMessage("wrong auth token"));
        if (token.isExpired())
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(jsonMessage("auth token expired"));

        return ResponseEntity.ok(gson.toJson(pointRepository.findByUserId(token.getId())));
    }

    @GetMapping(value = "/point", produces = "application/json")
    public ResponseEntity<String> addPoint( @RequestParam double x,
                                            @RequestParam double y,
                                            @RequestParam double r,
                                            HttpServletRequest request) {

        AuthToken token = authTokenRepository.findByToken(request.getHeader("auth-token"));

        if (token == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(jsonMessage("wrong auth token"));
        if (token.isExpired())
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(jsonMessage("auth token expired"));

        try {
            Point point = new Point(x, y, r, token.getUser());
            pointRepository.save(point);
        } catch (OutOfBoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonMessage("entered point incorrect"));
        }

        return ResponseEntity.ok(gson.toJson(pointRepository.findByUserId(token.getId())));
    }

}
