package ec.blopez.lello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Benjamin Lopez on 12.01.17.
 */
@RestController
@RequestMapping(produces = "text/html")
public class LelloError implements ErrorController {

    private static final Logger LOG = LoggerFactory.getLogger(LelloError.class);

    private final static String ERROR_PATH = "/error";

    /**
     * Supports the HTML Error View
     * @param request
     * @return
     */
    @RequestMapping(value = ERROR_PATH)
    public String errorHtml(final HttpServletResponse response, final HttpServletRequest request) {
        response.setStatus(404);
        return "404";
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

}
