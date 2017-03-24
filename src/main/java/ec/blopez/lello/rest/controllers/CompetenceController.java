package ec.blopez.lello.rest.controllers;

import com.google.common.base.CharMatcher;
import ec.blopez.lello.domain.Competence;
import ec.blopez.lello.domain.CompetenceSearchResult;
import ec.blopez.lello.services.impl.CompetenceServiceImpl;
import ec.blopez.lello.rest.ResponseKeys;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created by Benjamin Lopez on 12/01/2017.
 */
@RestController
@RequestMapping(value = "/api/v1.0/competences", produces = APPLICATION_JSON_VALUE)
public class CompetenceController {

    @Autowired
    private CompetenceServiceImpl competenceService;

    private final static int DEFAULT_LIMIT = 50;
    private final static int DEFAULT_OFFSET = 0;
    private final static Logger LOG = LoggerFactory.getLogger(CompetenceController.class);

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<CompetenceSearchResult> get(final HttpServletRequest request){
        final String limitAsString = request.getParameter("limit");
        final String offSetAsString = request.getParameter("offset");
        final String topAsString = request.getParameter(ResponseKeys.TOP);
        final String typeAsString = request.getParameter(ResponseKeys.TYPE);
        final String frameworkAsString = request.getParameter(ResponseKeys.FRAMEWORK);
        int limit = isOnlyDigits(limitAsString) ? Integer.parseInt(limitAsString) : DEFAULT_LIMIT;
        int offset = isOnlyDigits(offSetAsString) ? Integer.parseInt(offSetAsString) : DEFAULT_OFFSET;
        final Boolean top = (StringUtils.isEmpty(topAsString)) ? null : Boolean.parseBoolean(topAsString);
        final String type = (StringUtils.isEmpty(typeAsString)) ? null : typeAsString.toUpperCase();
        final String framework = (StringUtils.isEmpty(frameworkAsString)) ? null : frameworkAsString.toUpperCase();
        return new ResponseEntity<>(competenceService.get(limit, offset, type, framework, top), getHeaders(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Object> create(final RequestEntity<Competence> request){
        return return404IfNull(competenceService.create(request.getBody()));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> get(@PathVariable final String id){
        return return404IfNull(competenceService.get(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable final String id){
        return return404IfNull(competenceService.delete(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> update(@PathVariable final String id, final RequestEntity<Competence> request){
        return return404IfNull(competenceService.update(id, request.getBody()));
    }

    @RequestMapping(value = "/search/{query}", method = RequestMethod.GET)
    public ResponseEntity<CompetenceSearchResult> search(@PathVariable final String query, final HttpServletRequest request){
        final String limitAsString = request.getParameter("limit");
        final String offSetAsString = request.getParameter("offset");
        final String topAsString = request.getParameter(ResponseKeys.TOP);
        final String typeAsString = request.getParameter(ResponseKeys.TYPE);
        final String frameworkAsString = request.getParameter(ResponseKeys.FRAMEWORK);
        final String localeAsString = request.getHeader("Accept-Language");
        final int limit =  isOnlyDigits(limitAsString) ? Integer.parseInt(limitAsString) : DEFAULT_LIMIT;
        final int offset = isOnlyDigits(offSetAsString) ? Integer.parseInt(offSetAsString) : DEFAULT_OFFSET;
        final Boolean top = (StringUtils.isEmpty(topAsString)) ? null : Boolean.parseBoolean(topAsString);
        final String type = (StringUtils.isEmpty(typeAsString)) ? null : typeAsString.toUpperCase();
        final String framework = (StringUtils.isEmpty(frameworkAsString)) ? null : frameworkAsString.toUpperCase();
        final Locale locale = (StringUtils.isEmpty(localeAsString)) ? Locale.UK : new Locale(localeAsString);
        return new ResponseEntity<>(competenceService.search(query, limit, offset, locale, type, framework, top),
                getHeaders(), HttpStatus.OK);
    }

    private boolean isOnlyDigits(final String string){
        return (string != null) && CharMatcher.DIGIT.matchesAllOf(string);
    }

    private ResponseEntity<Object> return404IfNull(final Competence competence){
        final JSONObject result = new JSONObject();
        if(competence != null){
            return new ResponseEntity<>(competence, getHeaders(), HttpStatus.OK);
        }
        result.put(ResponseKeys.MESSAGE, "Invalid request");
        return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    }

    private MultiValueMap<String, String> getHeaders(){
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "http://localhost:3000");
        return headers;
    }
}
