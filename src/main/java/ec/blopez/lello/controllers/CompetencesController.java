package ec.blopez.lello.controllers;

import ec.blopez.lello.domain.Competence;
import ec.blopez.lello.domain.Skill;
import ec.blopez.lello.services.CompetenceService;
import ec.blopez.lello.utils.ResponseKeys;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping(value = "/api/v1.0/competences", produces = APPLICATION_JSON_VALUE)
public class CompetencesController {

    @Autowired
    CompetenceService competencesService;

    private final static Logger LOG = LoggerFactory.getLogger(CompetencesController.class);

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<JSONObject> getCompetences(final RequestEntity<JSONObject> request){
        final JSONObject result = new JSONObject();
        final List<Competence> competences = competencesService.get();
        result.put(ResponseKeys.COMPETENCES, competences);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Object> createCompetence(final RequestEntity<Skill> request){
        return return404IfNull(competencesService.create(request.getBody()));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getCompetence(@PathVariable final String id, final RequestEntity<JSONObject> request){
        return return404IfNull(competencesService.get(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteCompetence(@PathVariable final String id, final RequestEntity<JSONObject> request){
        return return404IfNull(competencesService.delete(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateCompetence(@PathVariable final String id, final RequestEntity<Skill> request){
        return return404IfNull(competencesService.update(id, request.getBody()));
    }

    @RequestMapping(value = "/search/{query}", method = RequestMethod.PUT)
    public ResponseEntity<Object> searchCompetences(@PathVariable final String query, final RequestEntity<JSONObject> request){
        final JSONObject result = new JSONObject();
        final List<Competence> competences = competencesService.search(query);
        result.put(ResponseKeys.COMPETENCES, competences);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    private ResponseEntity<Object> return404IfNull(final Competence competence){
        final JSONObject result = new JSONObject();
        if(competence != null){
            return new ResponseEntity<>(competence, HttpStatus.OK);
        }
        result.put(ResponseKeys.MESSAGE, "Invalid request");
        return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    }
}
