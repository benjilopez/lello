package ec.blopez.lello.controllers;

import com.google.common.base.CharMatcher;
import ec.blopez.lello.domain.Competence;
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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created by Benjamin Lopez on 12/01/2017.
 */
@RestController
@RequestMapping(value = "/api/v1.0/competences", produces = APPLICATION_JSON_VALUE)
public class CompetenceController {

    @Autowired
    private CompetenceServiceImpl<Competence> competenceService;

    private final static int DEFAULT_LIMIT = 50;
    private final static int DEFAULT_OFFSET = 0;
    private final static Logger LOG = LoggerFactory.getLogger(CompetenceController.class);

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<JSONObject> getCompetences(final HttpServletRequest request){
        final JSONObject result = new JSONObject();
        final String limitAsString = request.getParameter("limit");
        final String offSetAsString = request.getParameter("offset");
        int limit = isOnlyDigits(limitAsString) ? Integer.parseInt(limitAsString) : DEFAULT_LIMIT;
        int offset = isOnlyDigits(offSetAsString) ? Integer.parseInt(offSetAsString) : DEFAULT_OFFSET;
        final List<Competence> competences = competenceService.get(limit, offset);
        result.put(ResponseKeys.COMPETENCES, competences);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Object> createCompetence(final RequestEntity<Competence> request){
        return return404IfNull(competenceService.create(request.getBody()));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getCompetence(@PathVariable final String id){
        return return404IfNull(competenceService.get(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteCompetence(@PathVariable final String id){
        return return404IfNull(competenceService.delete(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateCompetence(@PathVariable final String id, final RequestEntity<Competence> request){
        return return404IfNull(competenceService.update(id, request.getBody()));
    }

    @RequestMapping(value = "/search/{query}", method = RequestMethod.PUT)
    public ResponseEntity<Object> searchCompetences(@PathVariable final String query, final HttpServletRequest request){
        final JSONObject result = new JSONObject();
        final String limitAsString = request.getParameter("limit");
        final String offSetAsString = request.getParameter("offset");
        final int limit =  isOnlyDigits(limitAsString) ? Integer.parseInt(limitAsString) : DEFAULT_LIMIT;
        final int offset = isOnlyDigits(offSetAsString) ? Integer.parseInt(offSetAsString) : DEFAULT_OFFSET;
        final List<Competence> competences = competenceService.search(query, limit, offset);
        result.put(ResponseKeys.COMPETENCES, competences);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    private boolean isOnlyDigits(final String string){
        return (string != null) && CharMatcher.DIGIT.matchesAllOf(string);
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
