package ec.blopez.lello.services.impl;

import com.google.common.collect.Lists;
import ec.blopez.lello.domain.Competence;
import ec.blopez.lello.services.CompetencesService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Benjamin Lopez on 14/01/2017.
 */
@Service
public class CompetencesServiceImpl implements CompetencesService {

    @Override
    public Competence get(final String id) {
        return null;
    }

    @Override
    public List<Competence> get(final List<String> ids) {
        final List<Competence> result = Lists.newArrayList();
        return result;
    }

    @Override
    public List<Competence> get() {
        final List<Competence> result = Lists.newArrayList();
        return result;
    }

    @Override
    public Competence update(final String id, final Competence competence) {
        return competence;
    }

    @Override
    public Competence delete(final String id) {
        return null;
    }

    @Override
    public Competence create(final Competence competence) {
        return competence;
    }

    @Override
    public List<Competence> search(final String query) {
        final List<Competence> result = Lists.newArrayList();
        return result;
    }
}
