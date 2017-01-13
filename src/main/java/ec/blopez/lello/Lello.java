package ec.blopez.lello;

import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created by benjilopez on 12/01/2017.
 */
@RestController
@RequestMapping(value = "/api/v1.0", produces = APPLICATION_JSON_VALUE)
public class Lello {

    @RequestMapping(value = "/competences", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> getCompetences(final RequestEntity<JSONObject> request){
        final JSONObject result = new JSONObject();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/competences/{id}", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> getCompetence(@PathVariable final String id, final RequestEntity<JSONObject> request){
        final JSONObject result = new JSONObject();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
