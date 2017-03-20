package ec.blopez.lello.controllers;

import ec.blopez.lello.domain.Esco;
import ec.blopez.lello.domain.Skill;
import ec.blopez.lello.services.impl.CompetenceServiceImpl;
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
@RequestMapping(value = "/api/v1.0/esco", produces = APPLICATION_JSON_VALUE)
public class EscoController {

    @Autowired
    CompetenceServiceImpl<Esco> competenceService;

    private final static Logger LOG = LoggerFactory.getLogger(EscoController.class);

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<JSONObject> getCompetences(){
        final JSONObject result = new JSONObject();
        final List<Esco> escos = competenceService.get(Esco.class);
        result.put(ResponseKeys.COMPETENCES, escos);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Object> createCompetence(final RequestEntity<Skill> request){
        return return404IfNull(competenceService.create(request.getBody()));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getCompetence(@PathVariable final String id){
        return return404IfNull(competenceService.get(id, Esco.class));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteCompetence(@PathVariable final String id){
        return return404IfNull(competenceService.delete(id, Esco.class));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateCompetence(@PathVariable final String id, final RequestEntity<Skill> request){
        return return404IfNull(competenceService.update(id, request.getBody(), Esco.class));
    }

    @RequestMapping(value = "/search/{query}", method = RequestMethod.PUT)
    public ResponseEntity<Object> searchCompetences(@PathVariable final String query){
        final JSONObject result = new JSONObject();
        final List<Esco> escos = competenceService.search(query);
        result.put(ResponseKeys.COMPETENCES, escos);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    private ResponseEntity<Object> return404IfNull(final Esco esco){
        final JSONObject result = new JSONObject();
        if(esco != null){
            return new ResponseEntity<>(esco, HttpStatus.OK);
        }
        result.put(ResponseKeys.MESSAGE, "Invalid request");
        return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    }
}
