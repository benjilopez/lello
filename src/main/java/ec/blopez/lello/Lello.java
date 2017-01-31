package ec.blopez.lello;

import ec.blopez.lello.domain.Competence;
import ec.blopez.lello.services.CompetencesService;
import ec.blopez.lello.utils.ResponseKeys;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created by Benjamin Lopez on 12/01/2017.
 */
@RestController
@RequestMapping(value = "/api/v1.0", produces = APPLICATION_JSON_VALUE)
public class Lello {

    @Autowired
    CompetencesService competencesService;

    @RequestMapping(value = "/competences", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> getCompetences(final RequestEntity<JSONObject> request){
        final JSONObject result = new JSONObject();
        final List<Competence> competences = competencesService.get();
        result.put(ResponseKeys.COMPETENCES, competences);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/competences/{id}", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> getCompetence(@PathVariable final String id, final RequestEntity<JSONObject> request){
        final JSONObject result = new JSONObject();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/competences/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<JSONObject> deleteCompetence(@PathVariable final String id, final RequestEntity<JSONObject> request){
        final JSONObject result = new JSONObject();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/competences", method = RequestMethod.PUT)
    public ResponseEntity<JSONObject> createCompetence(final RequestEntity<JSONObject> request){
        final JSONObject result = new JSONObject();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/competences/{id}", method = RequestMethod.PUT)
    public ResponseEntity<JSONObject> updateCompetence(@PathVariable final String id, final RequestEntity<JSONObject> request){
        final JSONObject result = new JSONObject();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/competences/search/{query}", method = RequestMethod.PUT)
    public ResponseEntity<JSONObject> searchCompetences(@PathVariable final String query, final RequestEntity<JSONObject> request){
        final JSONObject result = new JSONObject();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
